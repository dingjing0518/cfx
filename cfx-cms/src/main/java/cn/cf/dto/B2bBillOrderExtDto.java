package cn.cf.dto;

public class B2bBillOrderExtDto extends B2bBillOrderDto{
	
	private String accountPk ;
	
	private  String statusName;
	
	private String insertTimeBegin;
	
	private String insertTimeEnd;

	public String getAccountPk() {
		return accountPk;
	}

	public void setAccountPk(String accountPk) {
		this.accountPk = accountPk;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getInsertTimeBegin() {
		return insertTimeBegin;
	}

	public void setInsertTimeBegin(String insertTimeBegin) {
		this.insertTimeBegin = insertTimeBegin;
	}

	public String getInsertTimeEnd() {
		return insertTimeEnd;
	}

	public void setInsertTimeEnd(String insertTimeEnd) {
		this.insertTimeEnd = insertTimeEnd;
	}
	
	

}
