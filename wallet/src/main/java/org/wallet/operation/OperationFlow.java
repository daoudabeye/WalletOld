package org.wallet.operation;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.wallet.compte.CompteService;

@Component
public class OperationFlow {

	private static final Logger lOGGER = LoggerFactory.getLogger(OperationFlow.class);
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	@Autowired
	OperationService operationService;

	@Autowired
	CompteService compteService;

	@Autowired
	CommissionFlow commissionFlow;

	List<Operation> operations;

	@Scheduled(fixedDelay = 5000)
	public void tcJob(){
	}

	@Scheduled(fixedDelay = 3000)
	public void ocJob(){
		getOcOperations();
	}

	@Scheduled(fixedDelay = 12000)
	public void acJob(){
		getAcOperations();
	}

	@Scheduled(fixedDelay = 240000)
	public void aoJob(){
		getAoOperations();
	}

	@Transactional
	public void getAcOperations(){

		try {
			List<Operation> acOperations = operationService.getOperation("AC");

			for(Operation op : acOperations){
				Long minutes = OperationService.getDateDiff(op.getDateOperation(),new Date(),TimeUnit.MINUTES);
				lOGGER.info("AC JOB {} : minutes "+minutes, dateFormat.format(new Date()));
				if(minutes > 2)
					operationService.updateStatutAndCode(op.getIdOperation(), "DD", "ANNULER");
			}
		} catch (Exception e) {
			lOGGER.info("AC JOB {} : "+e.getMessage(), dateFormat.format(new Date()));
			e.printStackTrace();
		}
	}

	@Transactional
	public void getOcOperations(){
	
	}

	@Transactional
	public void getAoOperations(){
		try {
			List<Operation> ocOperations = operationService.getOperation("AO");

			for(Operation op : ocOperations){
				Long heurs = OperationService.getDateDiff(op.getDateOperation(),new Date(),TimeUnit.HOURS);

				if(heurs > 24)
					operationService.updateStatutAndCode(op.getIdOperation(), "AR", "EFFECTUER");
				else{
					operationService.updateStatutAndCode(op.getIdOperation(), "OA", "ANNULER");
					compteService.debiterCrediter(op.getClient(), op.getMontant(), op.getAgent(), op.getMontant());
				}
			}
		} catch (Exception e) {
			lOGGER.info("OC JOB {} : "+e.getMessage(), dateFormat.format(new Date()));
			e.printStackTrace();
		}
	}
}
