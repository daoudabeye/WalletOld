package org.wallet.commission;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigInteger;


/**
 * The persistent class for the mdr database table.
 * 
 */
@Entity
@Table(name="mdr")
@NamedQuery(name="Mdr.findAll", query="SELECT m FROM Mdr m")
public class Mdr implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="user_id")
	private BigInteger userId;

	@Column(name="agent_id")
	private BigInteger agentId;

	@Column(name="agregateur_id")
	private BigInteger agregateurId;

	private int niveau;

	@Column(name="parrain_id")
	private BigInteger parrainId;

	public Mdr() {
	}

	public Mdr(BigInteger userId, BigInteger agentId, BigInteger agregateurId, int niveau, BigInteger parrainId) {
		super();
		this.userId = userId;
		this.agentId = agentId;
		this.agregateurId = agregateurId;
		this.niveau = niveau;
		this.parrainId = parrainId;
	}

	public BigInteger getUserId() {
		return this.userId;
	}

	public void setUserId(BigInteger userId) {
		this.userId = userId;
	}

	public BigInteger getAgentId() {
		return this.agentId;
	}

	public void setAgentId(BigInteger agentId) {
		this.agentId = agentId;
	}

	public BigInteger getAgregateurId() {
		return this.agregateurId;
	}

	public void setAgregateurId(BigInteger agregateurId) {
		this.agregateurId = agregateurId;
	}

	public int getNiveau() {
		return this.niveau;
	}

	public void setNiveau(int niveau) {
		this.niveau = niveau;
	}

	public BigInteger getParrainId() {
		return this.parrainId;
	}

	public void setParrainId(BigInteger parrainId) {
		this.parrainId = parrainId;
	}

}