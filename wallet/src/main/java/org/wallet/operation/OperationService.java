package org.wallet.operation;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.wallet.compte.CompteRepository;
import org.wallet.compte.CompteService;
import org.wallet.phone.OperationParam;
import org.wallet.phone.OperationParamRepository;

@Service
public class OperationService {

	@Autowired
	CompteRepository compteRepository;

	@Autowired
	CompteService compteService;

	@Autowired
	OperationRepository operationRepository;

	@Autowired
	PreOperationRepository preOperation;

	@Autowired
	OperationParamRepository operationParamRepository;

	@Autowired
	private GrilleRepository grilleRepository;

	@Autowired 
	CommissionFlow commissionFlow;

	public Operation save(Operation operation){
		Operation op =  operationRepository.save(operation);
		return op;
	}

	/**
	 * 
	 * @param numeroClient
	 * @param numeroAgent
	 * @param montantDepot
	 * @param pin
	 * @param note
	 * @return
	 */
	public Operation doOperation(String typeOperation, String numeroAgent, String numeroClient, Double montant, 
			String pin, String note, Boolean confRequired, Boolean retrait) throws Exception{

		VPreOperation detailsAgent 	= preOperation.findByNumero(numeroAgent);
		VPreOperation detailsClient	= preOperation.findByNumero(numeroClient);
		
		Assert.notNull(detailsAgent, "Erreur 100 : numero Agent incorrect.");
		Assert.notNull(detailsClient, "Erreur 100 : numero Client incorrect.");
		OperationParam paramAgent 	= operationParamRepository.findByRoleAndTypeOperation(detailsAgent.getRoleId(), typeOperation);
		OperationParam paramClient	= operationParamRepository.findByRoleAndTypeOperation(detailsClient.getRoleId(), typeOperation);

		GrilleTarrifaire grille  = grilleRepository.find(montant.doubleValue());
		if(grille == null) throw new Exception("Erreur 108 : Aucun frais correspondant");
		Double frais = grille.getFrais();

		check(typeOperation, montant, detailsAgent, paramAgent, retrait ? false : true, frais, pin);
		check(typeOperation, montant, detailsClient, paramClient, retrait ? true : false, frais, pin);

		frais = paramClient.getFrais() ? grille.getFrais() : 0 ;

		Operation operation = new Operation();
		operation.setTypeOperation(typeOperation);
		operation.setAgent(detailsAgent.getUserId());
		operation.setClient(detailsClient.getUserId());
		operation.setDateOperation(new Date());
		operation.setFrais(frais);
		operation.setMontant(montant);
		operation.setStatus("EN COURS");
		operation.setStatutCode(confRequired ? "AC" : "OE");
		operation.setDetail(note);
		operation.setCommission(0.0);
		
		Operation op  = this.save(operation);
		
		if(!confRequired){
			if(!retrait){
				compteService.debiterCrediter(detailsAgent.getUserId(), montant, detailsClient.getUserId(), (montant - frais));
			}else{
				compteService.debiterCrediter(detailsClient.getUserId(), (montant - frais), detailsAgent.getUserId(), montant);
			}
			
			commissionFlow.commissionJob(operation);
		}

		return op;
	}

	public Operation webDepot(String numeroAgent, String numeroClient, Double montant, String message) throws Exception{
		return this.doOperation("DEPOT", numeroAgent, numeroClient, montant, "-1441", message, false, false);
	}
	
	public Operation webTransfert(String numeroAgent, String numeroClient, Double montant, String message) throws Exception{
		return this.doOperation("TRANSFERT", numeroAgent, numeroClient, montant, "-1441", message, false, false);
	}
	
	public Operation webRetrait(String numeroAgent, String numeroClient, Double montant, String message) throws Exception{
		return this.doOperation("RETRAIT", numeroAgent, numeroClient, montant, "-1441", message, true, true);
	}

	public Operation webPaiement(String numeroAgent, String numeroClient, Double montant, String message) throws Exception{
		return this.doOperation("DEPOT", numeroAgent, numeroClient, montant, "-1441", message, false, true);
	}

	/**
	 * 
	 * @param typeOperation
	 * @param montant
	 * @param details
	 * @param param
	 * @param src
	 * @param frais
	 * @param pin
	 * @throws Exception 
	 */
	private void check(String typeOperation, Double montant, VPreOperation details, OperationParam param, Boolean src, Double frais, String pin) throws Exception {
		LocalDate today = LocalDate.now();

		Assert.notNull(details, "Erreur 100 : numero Agent incorrect.");
		Assert.notNull(param, "Erreur 102 : opération non autorisée.");

		if(src){
			Assert.isTrue((details.getSold().doubleValue() >= (montant + (param.getFrais() ? frais : 0))), "Erreur 103 : Solde inssufisant");
			Assert.isTrue(((details.getSold().doubleValue() - (montant + (param.getFrais() ? frais : 0)) >= param.getSoldeMin())), "Erreur 103 : Solde minimum inssufisant");
			if(!pin.equals("-1441"))
				Assert.isTrue(details.getPin().equals(pin), "Erreur 108 : code pin incorrect");
		}

		//nombre de transactions par jour
		Long nbrTransaction = operationRepository.dailyAgentOperation(typeOperation,today.getDayOfMonth(), 
				today.getMonthValue(), today.getYear(), details.getUserId());
		if(nbrTransaction == null) nbrTransaction = Long.valueOf(0);

		//total montant des operations par jour
		Double daymontant = operationRepository.dailyAgentMontantOperation(typeOperation, today.getDayOfMonth(), 
				today.getMonthValue(), today.getYear(), details.getUserId());
		if(daymontant == null) daymontant = 0.0;

		Assert.isTrue((nbrTransaction <= param.getNbrMax()), "Erreur 104 : Vous avez atteint le nombre maximum d'operations par jours.");
		Assert.isTrue((daymontant <= param.getMontantJours().doubleValue()), "Erreur 105 : Vous avez atteint le montant autorisé par jours.");
		Assert.isTrue((montant <= param.getMontantMax().doubleValue()), "Erreur 106 : Le montant de l'opération est superieur au montant autorisé.");
		Assert.isTrue((montant >= param.getMontantMin().doubleValue()), "Erreur 107 : Le montant de l'opération est inferieur au montant autorisé.");

	}

	@Transactional
	public List<Operation> historique(BigInteger userId, String typeOperation, String dateDebut, String dateFin){
		String pattern = "dd/MM/yyyy";
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		Date start = null, end = null;
		try {
			start = format.parse(dateDebut);
			end = format.parse(dateFin);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Operation> histo, histo2;
		histo = operationRepository.findFirst100ByAgentAndTypeOperationAndDateOperationBetween(userId, typeOperation, start, end);
		histo2 = operationRepository.findFirst100ByClientAndTypeOperationAndDateOperationBetween(userId, typeOperation, start, end);

		histo.addAll(histo2);

		return histo;
	}

	public List<Operation> last20Histo(BigInteger userId, String typeOperation){
		List<Operation> opAgent =  operationRepository.findFirst20ByTypeOperationAndAgentOrderByIdOperationDesc(typeOperation, userId);
		List<Operation> opClient =  operationRepository.findFirst20ByTypeOperationAndClientOrderByIdOperationDesc(typeOperation, userId);
		opAgent.addAll(opClient);
		return opAgent;
	}
	
	@Transactional
	public Long nbrOperation(BigInteger userId, String type, LocalDate date){
		int jours = date.getDayOfMonth();
		int mois = date.getMonthValue();
		int annee = date.getYear();

		return operationRepository.dailyUserOperation(type, jours, mois, annee, userId);
	}

	public List<Long> last7DaysOperations(BigInteger userId, String type){
		List<Long> list = new ArrayList<Long>();
		LocalDate today = LocalDate.now();

		for(int i = 1; i <= 7; i++ ){
			Long nbr = nbrOperation(userId, type, today.minusDays(i));
			list.add(nbr);
		}

		return list;
	}

	@Transactional
	public List<Operation> getOperation(String statutCode){
		return operationRepository.findFirst20ByStatutCodeOrderByIdOperationAsc(statutCode);
	}

	@Transactional
	public Boolean updateStatutCode(BigInteger idOperation, String code){
		if(code.length() > 2)
			return false;
		operationRepository.updateStatutCode(idOperation, code);
		return true;
	}

	@Transactional
	public Boolean updateStatutAndCode(BigInteger idOperation, String code, String statut){
		if(code.length() > 2)
			return false;
		operationRepository.updateStatutAndCode(idOperation, code, statut);
		return true;
	}

	@Transactional
	public Operation retraitCheck(String id)  throws Exception{
		BigInteger idOperation = BigInteger.valueOf(Long.valueOf(id));

		Operation op = operationRepository.findOne(idOperation);
		Assert.notNull(op, "Aucune operation en cours");

		if(op.getStatutCode().equals(""))
			throw new Exception("Délais de confrimation expiré...");

		if(op.getStatutCode().equals("OC") || op.getStatutCode().equals("OE"))
			return op;

		return null;
	}

	@Transactional
	public Operation retraitConfirmation(BigInteger userId, String pin)  throws Exception{
		Assert.notNull(pin, "Erreur 100 : code pin incorrect.");

		Operation op = operationRepository.findFirst1ByTypeOperationAndClientAndStatutCodeOrderByIdOperationDesc("RETRAIT", userId, "AC");

		Assert.notNull(op, "Erreur 100 : aucune operation en attente.");

		Date now = new Date();
		Long minute = getDateDiff(op.getDateOperation(),now,TimeUnit.MINUTES);

		if(minute > 2){
			updateStatutAndCode(op.getIdOperation(), "DD", "ANNULER");
			throw new Exception("Erreur 120 : Temps de confirmation depassé");
		}

		updateStatutAndCode(op.getIdOperation(), "OC", "CONFIRMER");
		try {
			compteService.debiterCrediter(op.getClient(), (op.getMontant() + op.getFrais()), op.getAgent(), op.getMontant());
			commissionFlow.commissionJob(op);
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception("Erreur interne :"+e.getMessage());
		}

		return op;
	}

	public void debiterCrediter(BigInteger src, BigInteger dst, Double montant, Double frais){
		compteService.debiterCrediter(src, montant + frais, dst, montant);
	}

	public void crediter(BigInteger userId, Double montant){
		compteService.crediter(userId, montant);
	}

	public void debiter(BigInteger userId, Double montant){
		compteService.debiter(userId, montant);
	}

	/**
	 * Get a diff between two dates
	 * @param date1 the oldest date
	 * @param date2 the newest date
	 * @param timeUnit the unit in which you want the diff
	 * @return the diff value, in the provided unit
	 */
	public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
		long diffInMillies = date2.getTime() - date1.getTime();
		return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
	}
}
