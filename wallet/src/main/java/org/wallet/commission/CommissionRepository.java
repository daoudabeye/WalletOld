package org.wallet.commission;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CommissionRepository extends JpaRepository<Commission, BigInteger>{
	
	@Query(value = "SELECT montant FROM v_total_operations WHERE agent = ?1 and type= ?2", nativeQuery = true)
	Double totalByUtilisateurAndType(BigInteger userId, String type);
	
	@Query(value = "SELECT sum(montant) as montant FROM commissions WHERE user_id = ?1", nativeQuery = true)
	Double totalByUtilisateur(BigInteger userId);
	
	List<Commission> findByIdOperation(BigInteger idOperation);
	
	@Modifying
	@Query(value = "update commissions set code= ?2, where id_operation = ?1 ", nativeQuery = true)
	int updateCode(BigInteger idOperation, String code);
}
