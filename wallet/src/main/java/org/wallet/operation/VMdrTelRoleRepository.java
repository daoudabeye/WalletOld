package org.wallet.operation;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VMdrTelRoleRepository extends JpaRepository<VUtilisateurMdrTelRole, BigInteger>{

	public VUtilisateurMdrTelRole findOneByIdentifiant(String numero);
	
	public VUtilisateurMdrTelRole findOneByUserId(BigInteger userId);
}
