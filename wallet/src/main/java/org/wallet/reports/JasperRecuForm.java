package org.wallet.reports;

import org.hibernate.validator.constraints.NotEmpty;

public class JasperRecuForm {

	@NotEmpty
	private String idOperation;
	private String rptFmt="Html";
	
	public String getIdOperation() {
		return idOperation;
	}
	public void setIdOperation(String idOperation) {
		this.idOperation = idOperation;
	}
	public String getRptFmt() {
		return rptFmt;
	}
	public void setRptFmt(String rptFmt) {
		this.rptFmt = rptFmt;
	}
}
