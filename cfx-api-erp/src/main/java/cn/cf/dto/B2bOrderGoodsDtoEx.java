package cn.cf.dto;

import java.util.Date;

public class B2bOrderGoodsDtoEx extends B2bOrderGoodsDto{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Double tareWeight;
	private String storePk;
	private Double discountPrice;//优惠单价
	private Date insertTime;
	private Integer disStatus;//分货状态 1未分 2已分
	private Integer totalBoxes;//总箱数
	private Integer afterTotalBoxes;//修改后总箱数
	private String companyName;
	private String contacts;
	private String contactsTel;
	
	//修改后子订单商品实际总价之和
	private Double allOrderGoodsPresentTotalPrice;
	//修改后子订单运费实际总价之和
	private Double allOrderGoodsPresentTotalFreight;
	// columns END
	private Integer isNewProduct;
	
	private Integer purchaseType;
	
	private Double beforePrice;
	private Double beforeFreight;
	private Double beforeAdminFee;
	private Double beforeloadingFee;
	private Double beforeTotalFreight;

	public Double getTareWeight() {
		return tareWeight;
	}

	public void setTareWeight(Double tareWeight) {
		this.tareWeight = tareWeight;
	}

	public String getStorePk() {
		return storePk;
	}

	public void setStorePk(String storePk) {
		this.storePk = storePk;
	}

	public Double getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(Double discountPrice) {
		this.discountPrice = discountPrice;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public Integer getDisStatus() {
		return disStatus;
	}

	public void setDisStatus(Integer disStatus) {
		this.disStatus = disStatus;
	}

	public Integer getTotalBoxes() {
		return totalBoxes;
	}

	public void setTotalBoxes(Integer totalBoxes) {
		this.totalBoxes = totalBoxes;
	}

	public Integer getAfterTotalBoxes() {
		return afterTotalBoxes;
	}

	public void setAfterTotalBoxes(Integer afterTotalBoxes) {
		this.afterTotalBoxes = afterTotalBoxes;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getContactsTel() {
		return contactsTel;
	}

	public void setContactsTel(String contactsTel) {
		this.contactsTel = contactsTel;
	}

	public Double getAllOrderGoodsPresentTotalPrice() {
		return allOrderGoodsPresentTotalPrice;
	}

	public void setAllOrderGoodsPresentTotalPrice(Double allOrderGoodsPresentTotalPrice) {
		this.allOrderGoodsPresentTotalPrice = allOrderGoodsPresentTotalPrice;
	}

	public Double getAllOrderGoodsPresentTotalFreight() {
		return allOrderGoodsPresentTotalFreight;
	}

	public void setAllOrderGoodsPresentTotalFreight(Double allOrderGoodsPresentTotalFreight) {
		this.allOrderGoodsPresentTotalFreight = allOrderGoodsPresentTotalFreight;
	}

	public Integer getIsNewProduct() {
		return isNewProduct;
	}

	public void setIsNewProduct(Integer isNewProduct) {
		this.isNewProduct = isNewProduct;
	}

	public Integer getPurchaseType() {
		return purchaseType;
	}

	public void setPurchaseType(Integer purchaseType) {
		this.purchaseType = purchaseType;
	}

	public Double getBeforePrice() {
		return beforePrice;
	}

	public void setBeforePrice(Double beforePrice) {
		this.beforePrice = beforePrice;
	}

	public Double getBeforeFreight() {
		return beforeFreight;
	}

	public void setBeforeFreight(Double beforeFreight) {
		this.beforeFreight = beforeFreight;
	}

	public Double getBeforeAdminFee() {
		return beforeAdminFee;
	}

	public void setBeforeAdminFee(Double beforeAdminFee) {
		this.beforeAdminFee = beforeAdminFee;
	}

	public Double getBeforeloadingFee() {
		return beforeloadingFee;
	}

	public void setBeforeloadingFee(Double beforeloadingFee) {
		this.beforeloadingFee = beforeloadingFee;
	}

	public Double getBeforeTotalFreight() {
		return beforeTotalFreight;
	}

	public void setBeforeTotalFreight(Double beforeTotalFreight) {
		this.beforeTotalFreight = beforeTotalFreight;
	}

	
	
	
	
}
