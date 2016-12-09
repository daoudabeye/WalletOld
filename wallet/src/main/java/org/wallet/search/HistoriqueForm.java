package org.wallet.search;

public class HistoriqueForm {
	
	private String keywords;
	
	private String dateDebut;
	
	private String dateFin;

	public String getKeywords() {
		return keywords;
	}
	
	public String getDebut(){
		String date = dateDebut.substring(0, 10);
		System.out.println(date);
		return date.trim();
	}
	
	public String getFin(){
		String date = dateDebut.substring(13, 23);
		System.out.println(date);
		return date.trim();
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(String dateDebut) {
		this.dateDebut = dateDebut;
	}

	public String getDateFin() {
		return dateFin;
	}

	public void setDateFin(String dateFin) {
		this.dateFin = dateFin;
	}
}
