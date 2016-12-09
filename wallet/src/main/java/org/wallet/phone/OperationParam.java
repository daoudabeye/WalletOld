package org.wallet.phone;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the operation_param database table.
 * 
 */
@Entity
@Table(name="operation_param")
@NamedQuery(name="OperationParam.findAll", query="SELECT o FROM OperationParam o")
public class OperationParam implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;

	@Column(name="montant_max")
	private BigDecimal montantMax;
	
	@Column(name="montant_jours")
	private BigDecimal montantJours;

	@Column(name="montant_min")
	private BigDecimal montantMin;

	@Column(name="nbr_max")
	private Integer nbrMax;

	@Column(name="nbr_min")
	private Integer nbrMin;
	
	@Column(name="solde_min")
	private Double soldeMin;

	@Column(name="type_operation")
	private String typeOperation;
	
	@Column(name="periode_limit")
	private String periodeLimit;
	
	Boolean frais;

	//bi-directional one-to-one association to Role
	@ManyToOne
	@JoinColumn(name="role_id")
	private Role role;

	public OperationParam() {
	}

	public String check(Long nbr, Double daymontant, Double montantOp, String profile)throws Exception{
		if(nbr != null)
		if(nbr > nbrMax)
			return profile+" :Vous avez atteint la limit autorisée en nombre";
		if(nbrMin == 0)
			return profile+" :Vous n'etes pas autorisé en montant à effectuer cette operation";
		
		if(daymontant != null)
		if(daymontant > montantJours.doubleValue() )
			return profile+" :Le montant Max par jour pour cette operation est "+montantJours+" cfa.";
		
		if(montantOp <  montantMin.doubleValue() )
			return profile+" :Le montant minimun pour cette operation est "+montantMin+" cfa.";
		return null;
	}
	public Role getRole() {
		return this.role;
	}
	
	public void setRole(Role roleId) {
		this.role = roleId;
	}

	public BigDecimal getMontantMax() {
		return this.montantMax;
	}

	public void setMontantMax(BigDecimal montantMax) {
		this.montantMax = montantMax;
	}

	public BigDecimal getMontantMin() {
		return this.montantMin;
	}

	public void setMontantMin(BigDecimal montantMin) {
		this.montantMin = montantMin;
	}

	public Integer getNbrMax() {
		return this.nbrMax;
	}

	public void setNbrMax(Integer nbrMax) {
		this.nbrMax = nbrMax;
	}

	public Integer getNbrMin() {
		return this.nbrMin;
	}

	public void setNbrMin(Integer nbrMin) {
		this.nbrMin = nbrMin;
	}

	public String getTypeOperation() {
		return this.typeOperation;
	}

	public void setTypeOperation(String typeOperation) {
		this.typeOperation = typeOperation;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPeriodeLimit() {
		return periodeLimit;
	}

	public void setPeriodeLimit(String periodeLimit) {
		this.periodeLimit = periodeLimit;
	}

	public BigDecimal getMontantJours() {
		return montantJours;
	}

	public void setMontantJours(BigDecimal montantJours) {
		this.montantJours = montantJours;
	}

	public Boolean getFrais() {
		return frais;
	}

	public void setFrais(Boolean frais) {
		this.frais = frais;
	}

	public Double getSoldeMin() {
		return soldeMin;
	}

	public void setSoldeMin(Double soldeMin) {
		this.soldeMin = soldeMin;
	}

}