package org.wallet.cash;

import org.hibernate.validator.constraints.NotBlank;

public class CashReceipForm {

	private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";
	
	@NotBlank(message = CashReceipForm.NOT_BLANK_MESSAGE)
	private String code;
	
	@NotBlank(message = CashReceipForm.NOT_BLANK_MESSAGE)
	private String motDePasse;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMotDePasse() {
		return motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	
}
