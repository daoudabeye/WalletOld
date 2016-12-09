package org.wallet.operation.paiement;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wallet.operation.Operation;
import org.wallet.operation.OperationRepository;
import org.wallet.operation.PreOperationRepository;
import org.wallet.operation.VPreOperation;
import org.wallet.phone.OperationParam;
import org.wallet.phone.OperationParamRepository;


@Service
public class PaiementService {

	@Autowired
	OperationRepository operationRepository;
	
	@Autowired
	OperationParamRepository operationParamRepository;

	@Autowired
	PreOperationRepository preOperation;

	/**
	 * Methode Paiement
	 * @param numeroClient numero de telephone client
	 * @param numeroAgent numero de telephone agent
	 * @param montantTransfert montant du depot
	 * @param pin code secret agent
	 * @return un object operation en cas de du succes
	 * @throws Exception leve une exception en cas d'erreur.
	 */
	@Transactional
	public Operation paiement(String numeroClient, String numeroMarchand, String montantTransfert, String pin, String infos) throws Exception{

		BigDecimal montant = BigDecimal.valueOf(Double.valueOf(montantTransfert));

		VPreOperation detailsMarchand 	= preOperation.findByNumero(numeroMarchand);
		VPreOperation detailsClient = preOperation.findByNumero(numeroClient);

		OperationParam paramMarcahand = null;
		OperationParam paramClient = null;
		paramMarcahand	=	operationParamRepository.findByRoleAndTypeOperation(detailsMarchand.getRoleId(), "PAIEMENT");
		paramClient = operationParamRepository.findByRoleAndTypeOperation(detailsClient.getRoleId(), "PAIEMENT");

		if(detailsMarchand == null || detailsClient == null)
			throw new Exception("Erreur 100 : numero incorrect");
		if(paramMarcahand == null || paramClient == null)
			throw new Exception("Erreur 101 : Vous n'etes pas autorisé à effectuer cette operation");

		detailsMarchand.check("MARCHAND");
		detailsClient.check("CLIENT");

		LocalDate today = LocalDate.now();

		//Limitation par jours des operations en nombre et en montant côté Agent
		Long nbrTransactionMarchand = operationRepository.dailyAgentOperation("PAIEMENT",today.getDayOfMonth(), 
				today.getMonthValue(), today.getYear(), detailsMarchand.getUserId());
		Double daymontantMarchand = operationRepository.dailyAgentMontantOperation("PAIEMENT", today.getDayOfMonth(), 
				today.getMonthValue(), today.getYear(), detailsMarchand.getUserId());
		String result1 = paramMarcahand.check(nbrTransactionMarchand, daymontantMarchand, montant.doubleValue(), "CLIENT");
		if(result1 != null)
			throw new Exception("Erreur "+result1);


		//Limitation par jours des operations en nombre et en montant côté Agent
		Long nbrTransactionClient = operationRepository.dailyClientOperation("PAIEMENT",today.getDayOfMonth(), 
				today.getMonthValue(), today.getYear(), detailsClient.getUserId());
		Double daymontantClient = operationRepository.dailyClientMontantOperation("PAIEMENT", today.getDayOfMonth(), 
				today.getMonthValue(), today.getYear(), detailsClient.getUserId());
		String result2 = paramClient.check(nbrTransactionClient, daymontantClient, montant.doubleValue(), "MARCHAND");
		if(result2 != null)
			throw new Exception("Erreur "+result2);


		if(detailsMarchand.getSold().compareTo(montant) < 0)
			throw new Exception("Erreur 109 : Sold agent inssufisant");

		if(!detailsClient.getPin().equals(pin) && pin != null)
			throw new Exception("Erreur 110 : code pin incorrect");

		Operation depot = new Operation();
		depot.setTypeOperation("PAIEMENT");
		depot.setAgent(detailsClient.getUserId());
		depot.setClient(detailsMarchand.getUserId());
		depot.setDateOperation(new Date());
		depot.setFrais(0.0);
		depot.setMontant(montant.doubleValue());
		depot.setStatus("CONFIRMER");
		depot.setDetail(infos);
		depot.setCommission(0.0);

		Operation op =  operationRepository.save(depot);


		return op;
	}

}
