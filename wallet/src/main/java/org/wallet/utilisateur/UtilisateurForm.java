package org.wallet.utilisateur;

import java.math.BigInteger;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.wallet.account.Account;
import org.wallet.phone.Role;
import org.wallet.phone.Telephone;

public class UtilisateurForm {
	
	@NotBlank(message = "Seletionner le profile")
	private String profile;
	
	@NotBlank(message = "Choisissez le Genre")
	private String genre;
	
	@Email
	private String email;
	
	@Size(min=2, max=30, message = "Le nom doit contenir au moins 3 caracteres et 30 Max")
	private String nom;
	
	@Size(min=2, max=30, message = "Le prenom doit contenir au moins 3 caracteres et 30 Max")
	private String prenom;
	
	@NotNull
	@Size(min=2, max=8, message = "doit contenir au moin 2 chiffres et max 8 chiffres")
	private String numero;
	
	@Size(min=2, max=100, message="Adresse incorrect")
	private String adresse;
	
	@NotBlank(message = "Choisissez une region")
	private String ville;
	
	@NotNull
	private String operateur;
	
	@NotNull
	private String mobile;
	
	@Pattern(regexp="^[0-9]([0-9]*)$", message = "Le montant du depot est incorrect")
	private String depot;
	
	@NotNull
	private String dateNaissance;
	
	@NotNull
	private String typePiece;
	
	@NotBlank(message = "Le numero de la piece doit etre fourni")
	private String numeroPiece;
	
	private Utilisateur utilisateur;

	public UtilisateurForm(){
		super();
	}
	
    public UtilisateurForm(String profile, String genre, String email, String nom, String prenom, String numero,
			String adresse, String ville, String operateur, String mobile, String depot, String dateNaissance,
			String typePiece, String numeroPiece) {
		super();
		this.profile = profile;
		this.genre = genre;
		this.email = email;
		this.nom = nom;
		this.prenom = prenom;
		this.numero = numero;
		this.adresse = adresse;
		this.ville = ville;
		this.operateur = operateur;
		this.mobile = mobile;
		this.depot = depot;
		this.dateNaissance = dateNaissance;
		this.typePiece = typePiece;
		this.numeroPiece = numeroPiece;
	}

	public Utilisateur createAccount() { 
        utilisateur = new Utilisateur(adresse, new Date(), email,
        		genre, "", nom, "MALI", typePiece, numeroPiece, 
        		prenom, ville);
        
        return utilisateur;
	}
    
    public Telephone createTelephone(Role role){
    	Boolean status = mobile.equals("Actif") ? true : false;
    	return new Telephone(operateur, numero, status, role, utilisateur);
    }
    
    public Account createWebAccount(BigInteger userId, Role role){
    	Account compte = new Account(getNumero(), "lemonway", role.getRoleName());
    	compte.setUserId(userId);
    	return compte;
    }

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public String getOperateur() {
		return operateur;
	}

	public void setOperateur(String operateur) {
		this.operateur = operateur;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getDepot() {
		return depot;
	}

	public void setDepot(String depot) {
		this.depot = depot;
	}

	public String getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(String dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public String getTypePiece() {
		return typePiece;
	}

	public void setTypePiece(String typePiece) {
		this.typePiece = typePiece;
	}

	public String getNumeroPiece() {
		return numeroPiece;
	}

	public void setNumeroPiece(String numeroPiece) {
		this.numeroPiece = numeroPiece;
	}
}
