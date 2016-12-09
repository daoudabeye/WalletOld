package org.wallet.cash;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;


/**
 * The persistent class for the cash database table.
 * 
 */
@Entity
@NamedQuery(name="Cash.findAll", query="SELECT c FROM Cash c")
public class Cash implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CashPK id;

	private Boolean alert;

	private Date date;

	private BigDecimal montant;

	@Column(name="r_adresse")
	private String rAdresse;

	@Column(name="r_idcard")
	private String rIdcard;

	@Column(name="r_nom")
	private String rNom;

	@Column(name="r_numero")
	private String rNumero;

	@Column(name="r_pays")
	private String rPays;

	@Column(name="r_prenom")
	private String rPrenom;

	@Column(name="r_ville")
	private String rVille;

	@Column(name="s_adresse")
	private String sAdresse;

	@Column(name="s_idcard")
	private String sIdcard;

	@Column(name="s_nom")
	private String sNom;

	@Column(name="s_numero")
	private String sNumero;

	@Column(name="s_pays")
	private String sPays;

	@Column(name="s_prenom")
	private String sPrenom;

	@Column(name="s_ville")
	private String sVille;

	private String status;

	public Cash() {
	}

	public Cash(String code, Boolean alert, Date date, BigDecimal montant, String motDePasse, String rAdresse,
			String rIdcard, String rNom, String rNumero, String rPays, String rPrenom, String rVille, String sAdresse,
			String sIdcard, String sNom, String sNumero, String sPays, String sPrenom, String sVille, String status) {
		super();
		this.id = new CashPK();
		id.setCode(code);
		id.setMotDePasse(motDePasse);
		this.alert = alert;
		this.date = date;
		this.montant = montant;
		this.rAdresse = rAdresse;
		this.rIdcard = rIdcard;
		this.rNom = rNom;
		this.rNumero = rNumero;
		this.rPays = rPays;
		this.rPrenom = rPrenom;
		this.rVille = rVille;
		this.sAdresse = sAdresse;
		this.sIdcard = sIdcard;
		this.sNom = sNom;
		this.sNumero = sNumero;
		this.sPays = sPays;
		this.sPrenom = sPrenom;
		this.sVille = sVille;
		this.status = status;
	}

	public CashPK getId() {
		return this.id;
	}

	public void setId(CashPK id) {
		this.id = id;
	}

	public Boolean getAlert() {
		return this.alert;
	}

	public void setAlert(Boolean alert) {
		this.alert = alert;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public BigDecimal getMontant() {
		return this.montant;
	}

	public void setMontant(BigDecimal montant) {
		this.montant = montant;
	}

	public String getRAdresse() {
		return this.rAdresse;
	}

	public void setRAdresse(String rAdresse) {
		this.rAdresse = rAdresse;
	}

	public String getRIdcard() {
		return this.rIdcard;
	}

	public void setRIdcard(String rIdcard) {
		this.rIdcard = rIdcard;
	}

	public String getRNom() {
		return this.rNom;
	}

	public void setRNom(String rNom) {
		this.rNom = rNom;
	}

	public String getRNumero() {
		return this.rNumero;
	}

	public void setRNumero(String rNumero) {
		this.rNumero = rNumero;
	}

	public String getRPays() {
		return this.rPays;
	}

	public void setRPays(String rPays) {
		this.rPays = rPays;
	}

	public String getRPrenom() {
		return this.rPrenom;
	}

	public void setRPrenom(String rPrenom) {
		this.rPrenom = rPrenom;
	}

	public String getRVille() {
		return this.rVille;
	}

	public void setRVille(String rVille) {
		this.rVille = rVille;
	}

	public String getSAdresse() {
		return this.sAdresse;
	}

	public void setSAdresse(String sAdresse) {
		this.sAdresse = sAdresse;
	}

	public String getSIdcard() {
		return this.sIdcard;
	}

	public void setSIdcard(String sIdcard) {
		this.sIdcard = sIdcard;
	}

	public String getSNom() {
		return this.sNom;
	}

	public void setSNom(String sNom) {
		this.sNom = sNom;
	}

	public String getSNumero() {
		return this.sNumero;
	}

	public void setSNumero(String sNumero) {
		this.sNumero = sNumero;
	}

	public String getSPays() {
		return this.sPays;
	}

	public void setSPays(String sPays) {
		this.sPays = sPays;
	}

	public String getSPrenom() {
		return this.sPrenom;
	}

	public void setSPrenom(String sPrenom) {
		this.sPrenom = sPrenom;
	}

	public String getSVille() {
		return this.sVille;
	}

	public void setSVille(String sVille) {
		this.sVille = sVille;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}