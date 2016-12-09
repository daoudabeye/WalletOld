package org.wallet.phone;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PhoneForm {
	
	@NotNull
	@Size(min=2, max=8, message = "doit contenir au moin 2 chiffres et max 8 chiffres")
	private String numero;
	
	@NotNull
	private String operateur;

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getOperateur() {
		return operateur;
	}

	public void setOperateur(String operateur) {
		this.operateur = operateur;
	}
}
