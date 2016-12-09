package org.wallet.operation;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigInteger;


/**
 * The persistent class for the v_operation database table.
 * 
 */
@Entity
@Table(name="v_operation")
@NamedQuery(name="VOperation.findAll", query="SELECT v FROM VOperation v")
public class VOperation implements Serializable {
	private static final long serialVersionUID = 1L;

	private BigInteger agent;

	@Column(name="agent_nb")
	private String agentNb;

	private int annee;

	private BigInteger client;

	@Column(name="client_nb")
	private String clientNb;

	private double frais;

	@Id
	private BigInteger id;

	private int jours;

	private int mois;

	private double montant;

	private String status;

	private String type;

	public VOperation() {
	}

	public BigInteger getAgent() {
		return this.agent;
	}

	public void setAgent(BigInteger agent) {
		this.agent = agent;
	}

	public String getAgentNb() {
		return this.agentNb;
	}

	public void setAgentNb(String agentNb) {
		this.agentNb = agentNb;
	}

	public int getAnnee() {
		return this.annee;
	}

	public void setAnnee(int annee) {
		this.annee = annee;
	}

	public BigInteger getClient() {
		return this.client;
	}

	public void setClient(BigInteger client) {
		this.client = client;
	}

	public String getClientNb() {
		return this.clientNb;
	}

	public void setClientNb(String clientNb) {
		this.clientNb = clientNb;
	}

	public double getFrais() {
		return this.frais;
	}

	public void setFrais(double frais) {
		this.frais = frais;
	}

	public BigInteger getId() {
		return this.id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public int getJours() {
		return this.jours;
	}

	public void setJours(int jours) {
		this.jours = jours;
	}

	public int getMois() {
		return this.mois;
	}

	public void setMois(int mois) {
		this.mois = mois;
	}

	public double getMontant() {
		return this.montant;
	}

	public void setMontant(double montant) {
		this.montant = montant;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

}