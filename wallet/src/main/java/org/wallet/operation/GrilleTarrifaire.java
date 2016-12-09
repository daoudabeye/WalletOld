package org.wallet.operation;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the grille_tarrifaire database table.
 * 
 */
@Entity
@Table(name="grille_tarrifaire")
@NamedQuery(name="GrilleTarrifaire.findAll", query="SELECT g FROM GrilleTarrifaire g")
public class GrilleTarrifaire implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private GrilleTarrifairePK id;

	private double frais;

	@Column(name="valeur_max")
	private double valeurMax;

	@Column(name="valeur_min")
	private double valeurMin;

	public GrilleTarrifaire() {
	}

	public GrilleTarrifairePK getId() {
		return this.id;
	}

	public void setId(GrilleTarrifairePK id) {
		this.id = id;
	}

	public double getFrais() {
		return this.frais;
	}

	public void setFrais(double frais) {
		this.frais = frais;
	}

	public double getValeurMax() {
		return this.valeurMax;
	}

	public void setValeurMax(double valeurMax) {
		this.valeurMax = valeurMax;
	}

	public double getValeurMin() {
		return this.valeurMin;
	}

	public void setValeurMin(double valeurMin) {
		this.valeurMin = valeurMin;
	}

}