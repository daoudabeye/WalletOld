package org.wallet.operation;

import java.util.List;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

public class OperationForm {

	private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";
	
	@NotBlank(message = OperationForm.NOT_BLANK_MESSAGE)
	private String numero;

	private String nom;
    
	private String prenom;
    
    private List<MultipartFile> file;
    
    @Pattern(regexp="^[0-9]([0-9]*)$", message = "Le montant du depot est incorrect")
	private String montant;
    
    
    public String faireLeDepot() {
    	 
        return null;
	}


	public String getNumero() {
		return numero;
	}


	public void setNumero(String numero) {
		this.numero = numero;
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


	public String getMontant() {
		return montant;
	}


	public void setMontant(String montant) {
		this.montant = montant;
	}


	public List<MultipartFile> getFile() {
		return file;
	}


	public void setFile(List<MultipartFile> file) {
		this.file = file;
	}
      
}
