package org.wallet.operation;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the operations database table.
 * 
 */
@Entity
@Table(name="operations")
@NamedQuery(name="Operation.findAll", query="SELECT o FROM Operation o")
public  class Operation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_operation")
	private BigInteger idOperation;

	private Double commission;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_operation")
	private Date dateOperation;

	private String detail;

	private Double frais;

	private Double montant;

	private String status;

	@Column(name="type_operation")
	private String typeOperation;
	
	@Column(name="code")
	private String statutCode;

	//bi-directional many-to-one association to Utilisateur
	@Column(name="agent")
	private BigInteger agent;

	//bi-directional many-to-one association to Utilisateur
	@Column(name="client")
	private BigInteger client;

	public Operation() {
	}

	public Operation(double commission, Date dateOperation, String detail, double frais, double montant, String status,
			String typeOperation, BigInteger agent, BigInteger client) {
		super();
		this.commission = commission;
		this.dateOperation = dateOperation;
		this.detail = detail;
		this.frais = frais;
		this.montant = montant;
		this.status = status;
		this.typeOperation = typeOperation;
		this.agent = agent;
		this.client = client;
	}

	public Double Commission(int ratio){
		return (this.getFrais() * ratio)/100;
	}
	
	public BigInteger getIdOperation() {
		return this.idOperation;
	}

	public void setIdOperation(BigInteger idOperation) {
		this.idOperation = idOperation;
	}

	public Double getCommission() {
		return this.commission;
	}

	public void setCommission(Double commission) {
		this.commission = commission;
	}

	public Date getDateOperation() {
		return this.dateOperation;
	}

	public void setDateOperation(Date dateOperation) {
		this.dateOperation = dateOperation;
	}

	public String getDetail() {
		return this.detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Double getFrais() {
		return this.frais;
	}

	public void setFrais(Double frais) {
		this.frais = frais;
	}

	public double getMontant() {
		return this.montant;
	}

	public void setMontant(Double montant) {
		this.montant = montant;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTypeOperation() {
		return this.typeOperation;
	}

	public void setTypeOperation(String typeOperation) {
		this.typeOperation = typeOperation;
	}

	public BigInteger getAgent() {
		return this.agent;
	}

	public void setAgent(BigInteger utilisateur1) {
		this.agent = utilisateur1;
	}

	public BigInteger getClient() {
		return this.client;
	}

	public void setClient(BigInteger utilisateur2) {
		this.client = utilisateur2;
	}

	protected Operation getOperation() {
		// TODO Auto-generated method stub
		return new Operation(this.commission, this.dateOperation, this.detail, this.frais,
				this.montant, this.status, this.typeOperation, this.agent, this.client);
	}

	public String getStatutCode() {
		return statutCode;
	}

	public void setStatutCode(String statutCode) {
		this.statutCode = statutCode;
	}

}