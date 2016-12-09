package org.wallet.operation;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.wallet.commission.Commission;
import org.wallet.commission.CommissionRepository;
import org.wallet.phone.CommissionParam;
import org.wallet.phone.CommissionParamRepository;

@Component
public class CommissionFlow {

	@Autowired
	CommissionRepository commissionRepository;

	@Autowired
	VMdrTelRoleRepository mdrRepo;

	@Autowired
	CommissionParamRepository commissionParamRepository;

	@Transactional
	public void commissionJob(Operation operation){

		VUtilisateurMdrTelRole userDetail = mdrRepo.findOneByUserId(operation.getAgent());
		CommissionParam commissionParam = commissionParamRepository.findByIdOperationAndIdRoleId(operation.getTypeOperation(), 
				userDetail.getRoleId());

		if(commissionParam != null){
			Double commDirect = commissionParam.getDirect() > 0 ? (operation.getFrais() * commissionParam.getDirect()) / 100 : 0;
			Double commIndirect  = commissionParam.getIndirect() > 0 ? (operation.getFrais() * commissionParam.getIndirect()) / 100 : 0;

			Commission cd = new Commission(new Date(), "details", commDirect, "DIRECT", operation.getAgent(), 
					operation.getTypeOperation(), operation.getIdOperation(), operation.getAgent());
			Commission ci = new Commission(new Date(), "details", commIndirect, "INDIRECT", operation.getAgent(), 
					operation.getTypeOperation(), operation.getIdOperation(), userDetail.getAgregateur());

			cd.setCode("OE");
			ci.setCode("OE");
			commissionRepository.save(cd);
			commissionRepository.save(ci);
		}
	}

	@Transactional
	public void cancelJob(Operation operation){
		List<Commission> commissions = commissionRepository.findByIdOperation(operation.getIdOperation());

		for(Commission commission : commissions){
			commissionRepository.updateCode(commission.getIdOperation(), "OA");
		}
	}
}
