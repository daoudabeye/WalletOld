package org.wallet.phone;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.wallet.utilisateur.Utilisateur;

public interface TelephoneRepository extends JpaRepository<Telephone, BigInteger> {

	Telephone findOneByPhone(String phone);
	
	@Query("select t from Telephone t where t.utilisateur.userId = :userId AND t.status = 1")
	Telephone findFirst1ByStatus(@Param("userId") BigInteger userId);
	
	Telephone findOneByUtilisateur(Utilisateur user);
	
	Telephone findFirst1ByUtilisateurUserIdAndStatus(BigInteger userId, Boolean status);
	
	List<Telephone> findByUtilisateurUserId(BigInteger userId);

	List<Telephone> findByOperateur(String operateur);
	
	List<Telephone> findByStatus(Boolean status);
	
	List<Telephone> findByRole(Role role);
	
	List<Telephone> findByOperateurAndStatus(String operateur, String status);
	
	@Modifying
	@Query("update Telephone t set t.status = ?1 where t.id = ?2")
	Integer updateStatus(Boolean status, BigInteger id);	
}
