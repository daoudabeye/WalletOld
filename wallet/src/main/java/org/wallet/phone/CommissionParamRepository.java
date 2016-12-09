package org.wallet.phone;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommissionParamRepository extends JpaRepository<CommissionParam, CommissionParamPK> {

	CommissionParam findByIdOperationAndIdRoleId(String operation, Integer roleId);
	
	List<CommissionParam> findByIdRoleId(Integer roleId);
}
