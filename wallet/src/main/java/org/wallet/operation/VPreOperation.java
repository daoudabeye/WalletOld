package org.wallet.operation;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;


/**
 * The persistent class for the v_pre_operation database table.
 * 
 */
@Entity
@Table(name="v_pre_operation")
@NamedQuery(name="VPreOperation.findAll", query="SELECT v FROM VPreOperation v")
public class VPreOperation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String numero;
	
	private Boolean blocked;

	private Boolean geler;

	private String pin;

	private String role;

	private BigDecimal sold;

	@Column(name="user_id")
	private BigInteger userId;
	
	@Column(name="role_id")
	private Integer roleId;

	public VPreOperation() {
	}

	public Boolean check(String profile) throws Exception{
		if(blocked)
			throw new Exception("Erreur 102 : compte "+profile+" bloquer");
		if(geler)
			throw new Exception("Erreur 102 : compte "+profile+" geler");
		if(pin == null)
			throw new Exception("Erreur 104 : code  pin "+profile+" non-defini");
		if(role == null)
			throw new Exception("Erreur 105 : profile "+profile+" non-defini");
			
		return true;
	}
	public Boolean getBlocked() {
		return this.blocked;
	}

	public void setBlocked(Boolean blocked) {
		this.blocked = blocked;
	}

	public Boolean getGeler() {
		return this.geler;
	}

	public void setGeler(Boolean geler) {
		this.geler = geler;
	}

	public String getNumero() {
		return this.numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getPin() {
		return this.pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public BigDecimal getSold() {
		return this.sold;
	}

	public void setSold(BigDecimal sold) {
		this.sold = sold;
	}

	public BigInteger getUserId() {
		return this.userId;
	}

	public void setUserId(BigInteger userId) {
		this.userId = userId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

}