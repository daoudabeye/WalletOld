package org.wallet.utilisateur;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, BigInteger> {

	Utilisateur findOneByEmail(String id);
	
	Utilisateur findOneByUserId(BigInteger userId);
		
	List<Utilisateur> findByNomOrPrenom(String nom, String prenom);
	
	List<Utilisateur> findByVille(String ville);
	
	List<Utilisateur> findByPays(String pays);
	
	List<Utilisateur> findByAdresse(String adresse);
	
	List<Utilisateur> findByGenre(String genre);
	
	List<Utilisateur> findByEmail(String email);
	
	@Query(value = "SELECT role FROM v_utilisateur_mdr_tel_role WHERE identifiant = ?1", nativeQuery = true)
	String findRole(String phone_nb);
	
	@Modifying
	@Query("update Utilisateur u set u.nom = ?1, u.prenom = ?2, u.email = ?3, u.adresse = ?4"
			+ ", u.ville = ?5 , u.pieceIdentite = ?6, u.numeroPiece = ?7, u.genre = ?8 "
			+ "where u.userId = ?9")
	void update(String nom, String prenom, String email, String adresse,  String ville
			,  String pieceIdentite, String numeroPiece, String genre, BigInteger userId);
	
	@Query(value = "SELECT blocked FROM user_status WHERE user_id = ?1", nativeQuery = true)
	Boolean isLocked(BigInteger userId);
	
	@Modifying
	@Query("update UserStatus u set u.blocked= 1 where user_id = ?1")
	Integer locked(BigInteger userId);
	
	@Modifying
	@Query("update UserStatus u set u.blocked= 0 where user_id = ?1")
	Integer unlocked(BigInteger userId);
}
