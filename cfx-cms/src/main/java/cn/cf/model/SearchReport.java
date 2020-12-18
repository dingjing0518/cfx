package cn.cf.model;

public class SearchReport {
	
	/**
	 * 毛利报表
	 */
	private  String logisticsCompanyName;
	
	private String logisticsContacts;
	
	private Integer orderStatus;
	
	private String orderPk;
	
	private String insertStartTime;
	
	private String insertEndTime;
	
	private Integer reportStatus;
	
	private Integer isSettleFreight;
	
	private Integer isAbnormal;
	
	private String purchasersContacts;
	
	private String purchaserName;
	
	public String getLogisticsCompanyName() {
		return logisticsCompanyName;
	}

	public void setLogisticsCompanyName(String logisticsCompanyName) {
		this.logisticsCompanyName = logisticsCompanyName;
	}

	public String getLogisticsContacts() {
		return logisticsContacts;
	}

	public void setLogisticsContacts(String logisticsContacts) {
		this.logisticsContacts = logisticsContacts;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getOrderPk() {
		return orderPk;
	}

	public void setOrderPk(String orderPk) {
		this.orderPk = orderPk;
	}

	public String getInsertStartTime() {
		return insertStartTime;
	}

	public void setInsertStartTime(String insertStartTime) {
		this.insertStartTime = insertStartTime;
	}

	public String getInsertEndTime() {
		return insertEndTime;
	}

	public void setInsertEndTime(String insertEndTime) {
		this.insertEndTime = insertEndTime;
	}

	public Integer getReportStatus() {
		return reportStatus;
	}

	public void setReportStatus(Integer reportStatus) {
		this.reportStatus = reportStatus;
	}

	public Integer getIsSettleFreight() {
		return isSettleFreight;
	}

	public void setIsSettleFreight(Integer isSettleFreight) {
		this.isSettleFreight = isSettleFreight;
	}

	public Integer getIsAbnormal() {
		return isAbnormal;
	}

	public void setIsAbnormal(Integer isAbnormal) {
		this.isAbnormal = isAbnormal;
	}

	public String getPurchasersContacts() {
		return purchasersContacts;
	}

	public void setPurchasersContacts(String purchasersContacts) {
		this.purchasersContacts = purchasersContacts;
	}

	public String getPurchaserName() {
		return purchaserName;
	}

	public void setPurchaserName(String purchaserName) {
		this.purchaserName = purchaserName;
	}


	
}
