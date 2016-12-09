package org.wallet.cash;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the cash database table.
 * 
 */
@Embeddable
public class CashPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String id;

	private String code;

	@Column(name="mot_de_passe")
	private String motDePasse;

	public CashPK() {
	}
	public String getId() {
		return this.id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return this.code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMotDePasse() {
		return this.motDePasse;
	}
	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CashPK)) {
			return false;
		}
		CashPK castOther = (CashPK)other;
		return 
			this.id.equals(castOther.id)
			&& this.code.equals(castOther.code)
			&& this.motDePasse.equals(castOther.motDePasse);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.id.hashCode();
		hash = hash * prime + this.code.hashCode();
		hash = hash * prime + this.motDePasse.hashCode();
		
		return hash;
	}
}