package cn.cf.dto;

public class B2bFinanceRecordsDtoEx extends B2bFinanceRecordsDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String transactionName;
	
	private String statusName;
	
	private String descriptionEx;

	public String getTransactionName() {
		return transactionName;
	}

	public void setTransactionName(String transactionName) {
		this.transactionName = transactionName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getDescriptionEx() {
		return descriptionEx;
	}

	public void setDescriptionEx(String descriptionEx) {
		this.descriptionEx = descriptionEx;
	}
	
	
	

}
