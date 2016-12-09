package org.wallet.compte;

import java.io.Serializable;
import javax.persistence.*;

import org.wallet.utilisateur.Utilisateur;

import java.math.BigDecimal;
import java.math.BigInteger;


/**
 * The persistent class for the compte database table.
 * 
 */
@Entity
@Table(name="compte")
@NamedQuery(name="Compte.findAll", query="SELECT c FROM Compte c")
public class Compte implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="user_id")
	private BigInteger userId;

	private String etat;

	private Boolean geler;

	private String pin;

	private BigDecimal soldcash;

	private BigDecimal solduv;

	//bi-directional one-to-one association to Utilisateur
	@OneToOne
	@JoinColumn(name="user_id")
	private Utilisateur utilisateur;

	public Compte() {
	}

	
	public Compte(BigInteger userId, String etat, Boolean geler, String pin, 
			BigDecimal soldcash, BigDecimal solduv) {
		super();
		this.userId = userId;
		this.etat = etat;
		this.geler = geler;
		this.pin = pin;
		this.soldcash = soldcash;
		this.solduv = solduv;
	}


	public BigInteger getUserId() {
		return this.userId;
	}

	public void setUserId(BigInteger userId) {
		this.userId = userId;
	}

	public String getEtat() {
		return this.etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}

	public Boolean getGeler() {
		return this.geler;
	}

	public void setGeler(Boolean geler) {
		this.geler = geler;
	}

	public String getPin() {
		return this.pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public BigDecimal getSoldcash() {
		return this.soldcash;
	}

	public void setSoldcash(BigDecimal soldcash) {
		this.soldcash = soldcash;
	}

	public BigDecimal getSolduv() {
		return this.solduv;
	}

	public void setSolduv(BigDecimal solduv) {
		this.solduv = solduv;
	}

	public Utilisateur getUtilisateur() {
		return this.utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

}