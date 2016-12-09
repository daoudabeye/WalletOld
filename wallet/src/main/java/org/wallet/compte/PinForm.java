package org.wallet.compte;

public class PinForm {
	
	private Boolean self = false;
	
	private Boolean generer = true;
	
	private Boolean reset;
	
	private Boolean sms = true;
	
	private String ancien;
	
	private String nouveau;
	
	private String numero;

	public Boolean getReset() {
		return reset;
	}

	public void setReset(Boolean reset) {
		this.reset = reset;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getAncien() {
		return ancien;
	}

	public void setAncien(String ancien) {
		this.ancien = ancien;
	}

	public String getNouveau() {
		return nouveau;
	}

	public void setNouveau(String nouveau) {
		this.nouveau = nouveau;
	}

	public Boolean getSelf() {
		return self;
	}

	public void setSelf(Boolean self) {
		this.self = self;
	}

	public Boolean getGenerer() {
		return generer;
	}

	public void setGenerer(Boolean generer) {
		this.generer = generer;
	}

	public Boolean getSms() {
		return sms;
	}

	public void setSms(Boolean sms) {
		this.sms = sms;
	}

	
}
