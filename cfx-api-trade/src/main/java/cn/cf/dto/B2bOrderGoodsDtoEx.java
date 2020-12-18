package cn.cf.dto;

public class B2bOrderGoodsDtoEx extends B2bOrderGoodsDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Double tareWeight;
	private Double finalPrice;
	private Double finalFreight;
	private Double totalPrice;
	private Double totalFreight;
	private Double discountPrice;
	private String brandName;
	private Double originalTotalPrice;
	private Double presentTotalPrice;

	private Double otherDiscount;
	
	public Double getOriginalTotalPrice() {
		return originalTotalPrice;
	}
	public void setOriginalTotalPrice(Double originalTotalPrice) {
		this.originalTotalPrice = originalTotalPrice;
	}
	public Double getPresentTotalPrice() {
		return presentTotalPrice;
	}
	public void setPresentTotalPrice(Double presentTotalPrice) {
		this.presentTotalPrice = presentTotalPrice;
	}
	public Double getFinalPrice() {
		return finalPrice;
	}
	public void setFinalPrice(Double finalPrice) {
		this.finalPrice = finalPrice;
	}
	 
	public Double getFinalFreight() {
		return finalFreight;
	}
	public void setFinalFreight(Double finalFreight) {
		this.finalFreight = finalFreight;
	}
	 
	public Double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public Double getTotalFreight() {
		return totalFreight;
	}
	public void setTotalFreight(Double totalFreight) {
		this.totalFreight = totalFreight;
	}
	public Double getTareWeight() {
		return tareWeight;
	}
	public void setTareWeight(Double tareWeight) {
		this.tareWeight = tareWeight;
	}
	public Double getDiscountPrice() {
		return discountPrice;
	}
	public void setDiscountPrice(Double discountPrice) {
		this.discountPrice = discountPrice;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}


	public Double getOtherDiscount() {
		return otherDiscount;
	}

	public void setOtherDiscount(Double otherDiscount) {
		this.otherDiscount = otherDiscount;
	}
}
