package org.wallet.cash;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wallet.compte.Compte;
import org.wallet.compte.CompteRepository;
import org.wallet.operation.GrilleRepository;
import org.wallet.operation.GrilleTarrifaire;
import org.wallet.operation.Operation;
import org.wallet.operation.OperationService;
import org.wallet.operation.PreOperationRepository;
import org.wallet.operation.VPreOperation;
import org.wallet.phone.OperationParam;
import org.wallet.phone.OperationParamRepository;
import org.wallet.phone.Telephone;
import org.wallet.phone.TelephoneRepository;

@Service
public class CashService {

	private final static BigInteger CASHACCOUNT = BigInteger.valueOf(Long.valueOf(4));
	
	@Autowired
	CashRepository cashRepository;
	
	@Autowired
	private TelephoneRepository telephoneRepository;

	@Autowired
	private CompteRepository compteRepository;

	@Autowired
	private GrilleRepository grilleRepository;
	
	@Autowired
	private OperationService operationService;
	
	@Autowired
	PreOperationRepository preOperation;
	
	@Autowired
	OperationParamRepository operationParamRepository;

	@Transactional
	public Operation envoi(String numeroAgent, CashForm cashForm) throws Exception{
		VPreOperation detailsAgent 	= preOperation.findByNumero(numeroAgent);
		
		if(detailsAgent == null)
			throw new Exception("Erreur 100 : numero incorrect");
		
		OperationParam param = null;
		param	=	operationParamRepository.findByRoleAndTypeOperation(detailsAgent.getRoleId(), "CASHSEND");

		Double montant = Double.valueOf(cashForm.getMontant());
		BigDecimal montantEnvoi = BigDecimal.valueOf(Double.valueOf(montant));
		if(detailsAgent.getSold().compareTo(montantEnvoi) < 0)
			throw new Exception("Erreur 105 : Solde agent inssufisant");
		
		if(param == null)
			throw new Exception("Erreur 101 : Vous n'etes pas autorisé à effectuer cette operation");
		
		GrilleTarrifaire grille  = grilleRepository.find(montant.doubleValue());
		if(grille == null) throw new Exception("Erreur 110 : Aucun frais correspondant");
		Double frais = grille.getFrais();
		
		String code  = RandomStringUtils.randomNumeric(9);
		String pass = RandomStringUtils.randomNumeric(4);
		
		Operation operation = new Operation(0.0, new Date(), code, frais, montant.doubleValue(), "EFFECTUER", "ENVOI",
				detailsAgent.getUserId(), CASHACCOUNT);
		operation.setStatutCode("EE");
		
		operation = operationService.save(operation);
		
		Cash cash = new Cash(code, false, new Date(), montantEnvoi, pass,
				cashForm.getRadresse(), cashForm.getRidcard(), cashForm.getRnom(),
				cashForm.getRnumero(), cashForm.getRpays(),	cashForm.getRprenom(), cashForm.getRville(),
				cashForm.getSadresse(), cashForm.getSidcard(), cashForm.getSnom(),
				cashForm.getSnumero(), cashForm.getSpays(), cashForm.getSprenom(),
				cashForm.getSville(), "");
		
		operationService.debiterCrediter(operation.getAgent(), CASHACCOUNT, montant, frais);
		cashRepository.save(cash);
		
		return operation;
	}
	
	@Transactional
	public Operation recevoir(String numeroAgent, String code, String motDePasse) throws Exception{
		Cash cash = cashRepository.findByIdCodeAndIdMotDePasse(code.trim(), motDePasse.trim());
		Operation operation;
		if(cash == null)
			throw new Exception("Erreur 101 : Code incorrect !");
		else{
			Telephone 	phoneAgent = telephoneRepository.findOneByPhone(numeroAgent); 
			if(phoneAgent == null)
				throw new Exception("Erreur 101 : Numero Agent inconnu");
			Compte 		agent		= compteRepository.findByUserId(phoneAgent.getUtilisateur().getUserId());
			
			operation = new Operation(0.0, new Date(), "details", 0.0, cash.getMontant().doubleValue(), "EFFECTUER", "RECEPTION",
					CASHACCOUNT, agent.getUserId());
			operation.setStatutCode("ER");
			
			operation = operationService.save(operation);
			operationService.debiterCrediter(CASHACCOUNT, agent.getUserId(), operation.getMontant(), 0.0);
		}
		return operation;
	}
}
