package org.wallet.phone;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the commission_param database table.
 * 
 */
@Embeddable
public class CommissionParamPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private int id;

	private String operation;

	@Column(name="role_id", insertable=false, updatable=false)
	private int roleId;

	public CommissionParamPK() {
	}
	public int getId() {
		return this.id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOperation() {
		return this.operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public int getRoleId() {
		return this.roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CommissionParamPK)) {
			return false;
		}
		CommissionParamPK castOther = (CommissionParamPK)other;
		return 
			(this.id == castOther.id)
			&& this.operation.equals(castOther.operation)
			&& (this.roleId == castOther.roleId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.id;
		hash = hash * prime + this.operation.hashCode();
		hash = hash * prime + this.roleId;
		
		return hash;
	}
}