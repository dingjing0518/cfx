package cn.cf.model;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class B2bAuctionOffer  extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.String auctionPk;
	private java.lang.Double auctionPrice;
	private java.lang.Double thanStartingPrice;
	private java.lang.Integer boxes;
	private java.lang.Double weight;
	private java.lang.Integer provideBoxes;
	private java.lang.Double provideWeight;
	private java.lang.String companyPk;
	private java.lang.String companyName;
	private java.lang.String contacts;
	private java.lang.String contactsTel;
	private java.lang.String memberPk;
	private java.lang.String employeeName;
	private java.lang.String employeeNumber;
	private java.lang.String saleManPk;
	private java.lang.String saleManContacts;
	private java.lang.String saleManContactsTel;
	private java.util.Date insertTime;
	private java.util.Date bidTime;
	private java.util.Date orderTime;
	private Date overDateTime;
	private java.lang.Integer bidStatus;
	private java.lang.Integer isFinished;
	private java.lang.Integer type;
	//columns END

	public void setPk(java.lang.String pk) {
		this.pk = pk;
	}
	
	public java.lang.String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(java.lang.String employeeName) {
		this.employeeName = employeeName;
	}

	public java.lang.String getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(java.lang.String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public java.lang.String getSaleManPk() {
		return saleManPk;
	}

	public void setSaleManPk(java.lang.String saleManPk) {
		this.saleManPk = saleManPk;
	}

	public java.lang.String getSaleManContacts() {
		return saleManContacts;
	}

	public void setSaleManContacts(java.lang.String saleManContacts) {
		this.saleManContacts = saleManContacts;
	}

	public java.lang.String getSaleManContactsTel() {
		return saleManContactsTel;
	}

	public void setSaleManContactsTel(java.lang.String saleManContactsTel) {
		this.saleManContactsTel = saleManContactsTel;
	}

	public Date getOverDateTime() {
		return overDateTime;
	}

	public void setOverDateTime(Date overDateTime) {
		this.overDateTime = overDateTime;
	}

	public java.lang.String getPk() {
		return this.pk;
	}
	public void setAuctionPk(java.lang.String auctionPk) {
		this.auctionPk = auctionPk;
	}
	
	public java.lang.String getAuctionPk() {
		return this.auctionPk;
	}
	public void setAuctionPrice(java.lang.Double auctionPrice) {
		this.auctionPrice = auctionPrice;
	}
	
	public java.lang.Double getAuctionPrice() {
		return this.auctionPrice;
	}
	
	public java.lang.Double getThanStartingPrice() {
		return thanStartingPrice;
	}

	public void setThanStartingPrice(java.lang.Double thanStartingPrice) {
		this.thanStartingPrice = thanStartingPrice;
	}

	public void setBoxes(java.lang.Integer boxes) {
		this.boxes = boxes;
	}
	
	public java.lang.Integer getBoxes() {
		return this.boxes;
	}
	public void setWeight(java.lang.Double weight) {
		this.weight = weight;
	}
	
	public java.lang.Double getWeight() {
		return this.weight;
	}
	public void setProvideBoxes(java.lang.Integer provideBoxes) {
		this.provideBoxes = provideBoxes;
	}
	
	public java.lang.Integer getProvideBoxes() {
		return this.provideBoxes;
	}
	public void setProvideWeight(java.lang.Double provideWeight) {
		this.provideWeight = provideWeight;
	}
	
	public java.lang.Double getProvideWeight() {
		return this.provideWeight;
	}
	public void setCompanyPk(java.lang.String companyPk) {
		this.companyPk = companyPk;
	}
	
	public java.lang.String getCompanyPk() {
		return this.companyPk;
	}
	public void setCompanyName(java.lang.String companyName) {
		this.companyName = companyName;
	}
	
	public java.lang.String getCompanyName() {
		return this.companyName;
	}
	public void setContacts(java.lang.String contacts) {
		this.contacts = contacts;
	}
	
	public java.lang.String getContacts() {
		return this.contacts;
	}
	public void setContactsTel(java.lang.String contactsTel) {
		this.contactsTel = contactsTel;
	}
	
	public java.lang.String getContactsTel() {
		return this.contactsTel;
	}
	public void setMemberPk(java.lang.String memberPk) {
		this.memberPk = memberPk;
	}
	
	public java.lang.String getMemberPk() {
		return this.memberPk;
	}
	public void setInsertTime(java.util.Date insertTime) {
		this.insertTime = insertTime;
	}
	
	public java.util.Date getInsertTime() {
		return this.insertTime;
	}
	public void setBidTime(java.util.Date bidTime) {
		this.bidTime = bidTime;
	}
	
	public java.util.Date getBidTime() {
		return this.bidTime;
	}
	public void setOrderTime(java.util.Date orderTime) {
		this.orderTime = orderTime;
	}
	
	public java.util.Date getOrderTime() {
		return this.orderTime;
	}
	public void setBidStatus(java.lang.Integer bidStatus) {
		this.bidStatus = bidStatus;
	}
	
	public java.lang.Integer getBidStatus() {
		return this.bidStatus;
	}
	public void setIsFinished(java.lang.Integer isFinished) {
		this.isFinished = isFinished;
	}
	
	public java.lang.Integer getIsFinished() {
		return this.isFinished;
	}
	public void setType(java.lang.Integer type) {
		this.type = type;
	}
	
	public java.lang.Integer getType() {
		return this.type;
	}


	

}