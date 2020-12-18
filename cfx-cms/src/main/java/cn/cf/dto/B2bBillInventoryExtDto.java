package cn.cf.dto;

public class B2bBillInventoryExtDto extends B2bBillInventoryDto {
	
	private String statusName;
	
	private String payStatusName;

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getPayStatusName() {
		return payStatusName;
	}

	public void setPayStatusName(String payStatusName) {
		this.payStatusName = payStatusName;
	}
	
	

}
