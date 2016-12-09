package org.wallet.commission;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MdrRepository extends JpaRepository<Mdr, BigInteger> {

	Mdr findOneByUserId(BigInteger userId);
	
	@Query("select count(m) from Mdr m where m.agregateurId = :agregateuId")
	Long countByAgregateurId(@Param("agregateuId") BigInteger agregateuId);
	
	@Query("select count(m) from Mdr m where m.parrainId = :parrainId")
	Long countByParrainId(@Param("parrainId") BigInteger parrainId);
	
	@Query("select count(m) from Mdr m where m.agentId = :agentId")
	Long countByAgentId(@Param("agentId") BigInteger agentId);
	
	@Query(value = "SELECT COUNT(*) FROM v_utilisateur_mdr_tel_role WHERE agregateur = ?1 and role= ?2", nativeQuery = true)
	Long countProfileByAgergateurId(BigInteger agregateuId, String profile);
	
	@Query(value = "SELECT COUNT(*) FROM v_utilisateur_mdr_tel_role WHERE parrain = ?1 and role= ?2", nativeQuery = true)
	Long countProfileByParrainId(BigInteger parrainId, String profile);
	
	@Query(value = "SELECT COUNT(*) FROM v_utilisateur_mdr_tel_role WHERE agent = ?1 and role= ?2", nativeQuery = true)
	Long countProfileByAgentId(BigInteger agentId, String profile);
}
