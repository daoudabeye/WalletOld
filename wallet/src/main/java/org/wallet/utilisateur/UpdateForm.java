package org.wallet.utilisateur;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

public class UpdateForm {
	
	@NotBlank(message = "Choisissez le Genre")
	private String genre;
	
	@Email
	private String email;
	
	@Size(min=2, max=30, message = "Le nom doit contenir au moins 3 caracteres et 30 Max")
	private String nom;
	
	@Size(min=2, max=30, message = "Le prenom doit contenir au moins 3 caracteres et 30 Max")
	private String prenom;
	
	@Size(min=2, max=100, message="Adresse incorrect")
	private String adresse;
	
	@NotBlank(message = "Choisissez une region")
	private String ville;
	
	@NotNull
	private String typePiece;
	
	@NotBlank(message = "Le numero de la piece doit etre fourni")
	private String numeroPiece;
	

	public UpdateForm(){
		super();
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
