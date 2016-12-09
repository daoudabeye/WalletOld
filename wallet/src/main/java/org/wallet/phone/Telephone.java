package org.wallet.phone;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.wallet.utilisateur.Utilisateur;


/**
 * The persistent class for the telephone database table.
 * 
 */
@Entity
@Table(name="telephone")
@NamedQuery(name="Telephone.findAll", query="SELECT t FROM Telephone t")
public class Telephone implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private BigInteger id;

	private String operateur;

	@Column(unique = true, name="phone_nb")
	private String phone;

	private Boolean status;

	//bi-directional many-to-one association to Role
	@ManyToOne
	@JoinColumn(name="role_id")
	private Role role;

	//bi-directional many-to-one association to Utilisateur
	@ManyToOne
	@JoinColumn(name="user_id")
	private Utilisateur utilisateur;

	//bi-directional one-to-one association to WebUser
	@OneToOne(mappedBy="telephone")
	private WebUser webUser;

	public Telephone() {
	}

	
	public Telephone(String operateur, String phone, Boolean status, Role role, Utilisateur utilisateur) {
		super();
		this.operateur = operateur;
		this.phone = phone;
		this.status = status;
		this.role = role;
		this.utilisateur = utilisateur;
	}


	public BigInteger getId() {
		return this.id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getOperateur() {
		return this.operateur;
	}

	public void setOperateur(String operateur) {
		this.operateur = operateur;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phoneNb) {
		this.phone = phoneNb;
	}

	public Boolean getStatus() {
		return this.status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Utilisateur getUtilisateur() {
		return this.utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public WebUser getWebUser() {
		return this.webUser;
	}

	public void setWebUser(WebUser webUser) {
		this.webUser = webUser;
	}

}