package org.wallet.phone;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the commission_param database table.
 * 
 */
@Entity
@Table(name="commission_param")
@NamedQuery(name="CommissionParam.findAll", query="SELECT c FROM CommissionParam c")
public class CommissionParam implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CommissionParamPK id;

	private int ratio;
	
	private int direct;
	
	private int indirect;

	private int parrain;
	
	//bi-directional many-to-one association to Role
	@ManyToOne
	@JoinColumn(name="role_id", insertable=false, updatable=false)
	private Role role;

	public CommissionParam() {
	}

	public CommissionParamPK getId() {
		return this.id;
	}

	public void setId(CommissionParamPK id) {
		this.id = id;
	}

	public int getRatio() {
		return this.ratio;
	}

	public void setRatio(int ratio) {
		this.ratio = ratio;
	}

	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public int getDirect() {
		return direct;
	}

	public void setDirect(int direct) {
		this.direct = direct;
	}

	public int getIndirect() {
		return indirect;
	}

	public void setIndirect(int indirect) {
		this.indirect = indirect;
	}

	public int getParrain() {
		return parrain;
	}

	public void setParrain(int parrain) {
		this.parrain = parrain;
	}

}