package cn.cf.dto;

public class B2bOrderGoodsDtoEx extends B2bOrderGoodsDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Double tareWeight;
	private Double finalPrice;
	private Double finalTotalPrice;
	private Double finalFreight;
	private Double finalTotalFreight;
	private Double totalPrice;
	private Double totalFreight;
	private Double discountPrice;
	
	public Double getFinalPrice() {
		return finalPrice;
	}
	public void setFinalPrice(Double finalPrice) {
		this.finalPrice = finalPrice;
	}
	public Double getFinalTotalPrice() {
		return finalTotalPrice;
	}
	public void setFinalTotalPrice(Double finalTotalPrice) {
		this.finalTotalPrice = finalTotalPrice;
	}
	public Double getFinalFreight() {
		return finalFreight;
	}
	public void setFinalFreight(Double finalFreight) {
		this.finalFreight = finalFreight;
	}
	public Double getFinalTotalFreight() {
		return finalTotalFreight;
	}
	public void setFinalTotalFreight(Double finalTotalFreight) {
		this.finalTotalFreight = finalTotalFreight;
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
	
	
	

}
