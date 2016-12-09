package org.wallet.utilisateur;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigInteger;


/**
 * The persistent class for the user_status database table.
 * 
 */
@Entity
@Table(name="user_status")
@NamedQuery(name="UserStatus.findAll", query="SELECT u FROM UserStatus u")
public class UserStatus implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="user_id")
	private BigInteger userId;

	@Column(name="agent_update")
	private BigInteger agentUpdate;

	private Boolean blocked;

	private String commentaire;

	@Column(name="date_inscription")
	private Timestamp dateInscription;

	@Column(name="end_blackliste")
	private String endBlackliste;

	@Column(name="last_update")
	private Timestamp lastUpdate;

	private String status;

	public UserStatus() {
	}

	public BigInteger getUserId() {
		return this.userId;
	}

	public void setUserId(BigInteger userId) {
		this.userId = userId;
	}

	public BigInteger getAgentUpdate() {
		return this.agentUpdate;
	}

	public void setAgentUpdate(BigInteger agentUpdate) {
		this.agentUpdate = agentUpdate;
	}

	public Boolean getBlocked() {
		return this.blocked;
	}

	public void setBlocked(Boolean blocked) {
		this.blocked = blocked;
	}

	public String getCommentaire() {
		return this.commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	public Timestamp getDateInscription() {
		return this.dateInscription;
	}

	public void setDateInscription(Timestamp dateInscription) {
		this.dateInscription = dateInscription;
	}

	public String getEndBlackliste() {
		return this.endBlackliste;
	}

	public void setEndBlackliste(String endBlackliste) {
		this.endBlackliste = endBlackliste;
	}

	public Timestamp getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}