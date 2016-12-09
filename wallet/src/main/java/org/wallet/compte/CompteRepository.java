package org.wallet.compte;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CompteRepository extends JpaRepository<Compte, BigInteger> {

	Compte findByUserId(BigInteger userId);
	
	@Modifying
	@Query(value = "update compte c set c.solduv = ?1 where c.user_id = ?2 ", nativeQuery = true)
	int setSolduv(BigDecimal sold, BigInteger userId);
	
	@Modifying
	@Query(value = "update compte set solduv=solduv + ?1 where user_id= ?2 ", nativeQuery = true)
	int crediterSolduv(BigDecimal montant, BigInteger userId);
	
	@Modifying
	@Query(value = "update compte set solduv=solduv - ?1 where user_id= ?2 ", nativeQuery = true)
	int debiterSolduv(BigDecimal montant, BigInteger userId);
}
