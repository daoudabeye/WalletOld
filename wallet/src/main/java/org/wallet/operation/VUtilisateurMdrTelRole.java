package org.wallet.operation;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the v_utilisateur_mdr_tel_role database table.
 * 
 */
@Entity
@Table(name="v_utilisateur_mdr_tel_role")
@NamedQuery(name="VUtilisateurMdrTelRole.findAll", query="SELECT v FROM VUtilisateurMdrTelRole v")
public class VUtilisateurMdrTelRole implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String identifiant;
	
	@Column(name="user_id")
	private BigInteger userId;
	
	private BigInteger agent;

	private BigInteger agregateur;

	@Column(name="role_id")
	private Integer roleId;
	
	private Integer cParrain;

	private Integer indirectdepot;

	private Integer indirectretrait;

	private String nom;

	private BigInteger parrain;

	private String prenom;

	private Integer ratiodepot;

	private Integer ratioretrait;

	private String role;

	public VUtilisateurMdrTelRole() {
	}

	public BigInteger getAgent() {
		return this.agent;
	}

	public void setAgent(BigInteger agent) {
		this.agent = agent;
	}

	public BigInteger getAgregateur() {
		return this.agregateur;
	}

	public void setAgregateur(BigInteger agregateur) {
		this.agregateur = agregateur;
	}

	public Integer getCParrain() {
		return this.cParrain;
	}

	public void setCParrain(Integer cParrain) {
		this.cParrain = cParrain;
	}

	public String getIdentifiant() {
		return this.identifiant;
	}

	public void setIdentifiant(String identifiant) {
		this.identifiant = identifiant;
	}

	public Integer getIndirectdepot() {
		return this.indirectdepot;
	}

	public void setIndirectdepot(Integer indirectdepot) {
		this.indirectdepot = indirectdepot;
	}

	public Integer getIndirectretrait() {
		return this.indirectretrait;
	}

	public void setIndirectretrait(Integer indirectretrait) {
		this.indirectretrait = indirectretrait;
	}

	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public BigInteger getParrain() {
		return this.parrain;
	}

	public void setParrain(BigInteger parrain) {
		this.parrain = parrain;
	}

	public String getPrenom() {
		return this.prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public Integer getRatiodepot() {
		return this.ratiodepot;
	}

	public void setRatiodepot(Integer ratiodepot) {
		this.ratiodepot = ratiodepot;
	}

	public Integer getRatioretrait() {
		return this.ratioretrait;
	}

	public void setRatioretrait(Integer ratioretrait) {
		this.ratioretrait = ratioretrait;
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public BigInteger getUserId() {
		return userId;
	}

	public void setUserId(BigInteger userId) {
		this.userId = userId;
	}

	public Integer getcParrain() {
		return cParrain;
	}

	public void setcParrain(Integer cParrain) {
		this.cParrain = cParrain;
	}

}