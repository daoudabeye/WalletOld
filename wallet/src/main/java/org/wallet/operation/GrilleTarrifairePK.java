package org.wallet.operation;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * The primary key class for the grille_tarrifaire database table.
 * 
 */
@Embeddable
public class GrilleTarrifairePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private int id;

	private String type;

	public GrilleTarrifairePK() {
	}
	public int getId() {
		return this.id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return this.type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof GrilleTarrifairePK)) {
			return false;
		}
		GrilleTarrifairePK castOther = (GrilleTarrifairePK)other;
		return 
			(this.id == castOther.id)
			&& this.type.equals(castOther.type);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.id;
		hash = hash * prime + this.type.hashCode();
		
		return hash;
	}
}