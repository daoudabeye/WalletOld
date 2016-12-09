package org.wallet.phone;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigInteger;


/**
 * The persistent class for the web_user database table.
 * 
 */
@Entity
@Table(name="web_user")
@NamedQuery(name="WebUser.findAll", query="SELECT w FROM WebUser w")
public class WebUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="telephone_id")
	private BigInteger telephoneId;

	private int compteur;

	@Column(name="first_login")
	private Timestamp firstLogin;

	@Column(name="last_login")
	private Timestamp lastLogin;
	private String location;

	private byte lock;

	@Column(name="nbr_bad_login")
	private String nbrBadLogin;

	@Column(name="nbr_blackliste")
	private int nbrBlackliste;

	private String password;

	//bi-directional one-to-one association to Telephone
	@OneToOne
	@JoinColumn(insertable=false, updatable=false)
	private Telephone telephone;

	public WebUser() {
	}

	public BigInteger getTelephoneId() {
		return this.telephoneId;
	}

	public void setTelephoneId(BigInteger telephoneId) {
		this.telephoneId = telephoneId;
	}

	public int getCompteur() {
		return this.compteur;
	}

	public void setCompteur(int compteur) {
		this.compteur = compteur;
	}

	public Timestamp getFirstLogin() {
		return this.firstLogin;
	}

	public void setFirstLogin(Timestamp firstLogin) {
		this.firstLogin = firstLogin;
	}

	public Timestamp getLastLogin() {
		return this.lastLogin;
	}

	public void setLastLogin(Timestamp lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public byte getLock() {
		return this.lock;
	}

	public void setLock(byte lock) {
		this.lock = lock;
	}

	public String getNbrBadLogin() {
		return this.nbrBadLogin;
	}

	public void setNbrBadLogin(String nbrBadLogin) {
		this.nbrBadLogin = nbrBadLogin;
	}

	public int getNbrBlackliste() {
		return this.nbrBlackliste;
	}

	public void setNbrBlackliste(int nbrBlackliste) {
		this.nbrBlackliste = nbrBlackliste;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Telephone getTelephone() {
		return this.telephone;
	}

	public void setTelephone(Telephone telephone) {
		this.telephone = telephone;
	}

}