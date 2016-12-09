package org.wallet.phone;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OperationParamRepository extends JpaRepository<OperationParam, Integer>{

	@Query("select o from OperationParam o where o.role.roleId = :roleId AND o.typeOperation = :type")
	OperationParam findByRoleAndTypeOperation(@Param("roleId") Integer roleId, @Param("type") String type);
	
	List<OperationParam> findByRoleRoleId(Integer roleId);
}
