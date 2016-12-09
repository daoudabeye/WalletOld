package org.wallet.search;

import org.hibernate.validator.constraints.NotBlank;

public class SearchForm {

	@NotBlank(message = "definissez un critere de recherche")
	private String criteria;
	
	private String keywords;

	public String getCriteria() {
		return criteria;
	}

	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	
	
}
