package org.wallet.utilisateur;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.wallet.compte.Compte;
import org.wallet.phone.Telephone;


/**
 * The persistent class for the utilisateur database table.
 * 
 */
@Entity
@Table(name="utilisateur")
@NamedQuery(name="Utilisateur.findAll", query="SELECT u FROM Utilisateur u")
public class Utilisateur implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="user_id")
	private BigInteger userId;

	private String adresse;

	@Temporal(TemporalType.DATE)
	@Column(name="date_naissance")
	private Date dateNaissance;

	private String email;

	private String genre;

	@Column(name="mot_de_passe")
	private String motDePasse;

	private String nom;

	private String pays;

	@Column(name="piece_identite")
	private String pieceIdentite;

	@Column(name="numero_piece")
	private String numeroPiece;
	
	private String prenom;

	private String ville;

	//bi-directional one-to-one association to Compte
	@OneToOne(mappedBy="utilisateur")
	private Compte compte;

	//bi-directional many-to-one association to Telephone
	@OneToMany(mappedBy="utilisateur")
	private List<Telephone> telephones;

	//bi-directional one-to-one association to UserStatus
	//@OneToOne(mappedBy="utilisateur")
	//private UserStatus userStatus;

	public Utilisateur() {
	}
	
	

	public Utilisateur( String adresse, Date dateNaissance, String email, String genre,
			String motDePasse, String nom, String pays, String pieceIdentite, String numeroPiece, String prenom, String ville) {
		super();
		this.adresse = adresse;
		this.dateNaissance = dateNaissance;
		this.email = email;
		this.genre = genre;
		this.motDePasse = motDePasse;
		this.nom = nom;
		this.pays = pays;
		this.pieceIdentite = pieceIdentite;
		this.numeroPiece = numeroPiece;
		this.prenom = prenom;
		this.ville = ville;
	}



	public BigInteger getUserId() {
		return this.userId;
	}

	public void setUserId(BigInteger userId) {
		this.userId = userId;
	}

	public String getAdresse() {
		return this.adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public Date getDateNaissance() {
		return this.dateNaissance;
	}

	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGenre() {
		return this.genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getMotDePasse() {
		return this.motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPays() {
		return this.pays;
	}

	public void setPays(String pays) {
		this.pays = pays;
	}

	public String getPieceIdentite() {
		return this.pieceIdentite;
	}

	public void setPieceIdentite(String pieceIdentite) {
		this.pieceIdentite = pieceIdentite;
	}

	public String getPrenom() {
		return this.prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getVille() {
		return this.ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public Compte getCompte() {
		return this.compte;
	}

	public void setCompte(Compte compte) {
		this.compte = compte;
	}

	public List<Telephone> getTelephones() {
		return this.telephones;
	}

	public void setTelephones(List<Telephone> telephones) {
		this.telephones = telephones;
	}

	public Telephone addTelephone(Telephone telephone) {
		getTelephones().add(telephone);
		telephone.setUtilisateur(this);

		return telephone;
	}

	public Telephone removeTelephone(Telephone telephone) {
		getTelephones().remove(telephone);
		telephone.setUtilisateur(null);

		return telephone;
	}
/*
	public UserStatus getUserStatus() {
		return this.userStatus;
	}

	public void setUserStatus(UserStatus userStatus) {
		this.userStatus = userStatus;
	}
*/
	public String getNumeroPiece() {
		return numeroPiece;
	}

	public void setNumeroPiece(String numeroPiece) {
		this.numeroPiece = numeroPiece;
	}

}