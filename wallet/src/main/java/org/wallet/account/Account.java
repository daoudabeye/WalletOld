package org.wallet.account;

import java.math.BigInteger;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@SuppressWarnings("serial")
@Entity
@Table(name = "account")
public class Account implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, name="username")
	private String username;
	
	@JsonIgnore
	private String password;

	private String role = "ROLE_USER";

	private Instant created;
	
	@Column(name="user_id")
	private BigInteger userId;

    protected Account() {

	}
	
	public Account(String email, String password, String role) {
		this.username = email;
		this.password = password;
		this.role = role;
		this.created = Instant.now();
	}

	public Long getId() {
		return id;
	}

    public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Instant getCreated() {
		return created;
	}

	public BigInteger getUserId() {
		return userId;
	}

	public void setUserId(BigInteger userId) {
		this.userId = userId;
	}
}
