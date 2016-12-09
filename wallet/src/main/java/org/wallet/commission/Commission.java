package org.wallet.commission;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the commissions database table.
 * 
 */
@Entity
@Table(name="commissions")
@NamedQuery(name="Commission.findAll", query="SELECT c FROM Commission c")
public class Commission implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_commission")
	private Integer idCommission;

	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	private String details;

	private double montant;

	private String type;
	
	private BigInteger agent;
	
	private String operation;
	
	private String code;
	
	@Column(name="id_operation")
	private BigInteger idOperation;

	//bi-directional many-to-one association to Utilisateur
	@Column(name="user_id")
	private BigInteger utilisateur;

	public Commission() {
	}

	public Commission(Date date, String details, double montant, String type,
			BigInteger utilisateur) {
		super();
		this.date = date;
		this.details = details;
		this.montant = montant;
		this.type = type;
		this.utilisateur = utilisateur;
	}

	public Commission(Date date, String details, double montant, String type, BigInteger agent, String operation,
			BigInteger idOperation, BigInteger utilisateur) {
		super();
		this.date = date;
		this.details = details;
		this.montant = montant;
		this.type = type;
		this.agent = agent;
		this.operation = operation;
		this.idOperation = idOperation;
		this.utilisateur = utilisateur;
	}

	public Integer getIdCommission() {
		return this.idCommission;
	}

	public void setIdCommission(Integer idCommission) {
		this.idCommission = idCommission;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDetails() {
		return this.details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public double getMontant() {
		return this.montant;
	}

	public void setMontant(double montant) {
		this.montant = montant;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BigInteger getUtilisateur() {
		return this.utilisateur;
	}

	public void setUtilisateur(BigInteger utilisateur) {
		this.utilisateur = utilisateur;
	}



	public BigInteger getAgent() {
		return agent;
	}



	public void setAgent(BigInteger agent) {
		this.agent = agent;
	}



	public String getOperation() {
		return operation;
	}



	public void setOperation(String operation) {
		this.operation = operation;
	}



	public BigInteger getIdOperation() {
		return idOperation;
	}



	public void setIdOperation(BigInteger idOperation) {
		this.idOperation = idOperation;
	}



	public String getCode() {
		return code;
	}



	public void setCode(String code) {
		this.code = code;
	}

}