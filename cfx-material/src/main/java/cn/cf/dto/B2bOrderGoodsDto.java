package cn.cf.dto;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class B2bOrderGoodsDto extends BaseDTO implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String orderNumber;
	private java.lang.String goodsPk;
	private java.lang.Double weight;
	private java.lang.Integer boxes;
	private java.lang.Double originalPrice;
	private java.lang.Double presentPrice;
	private java.lang.Double originalFreight;
	private java.lang.Double presentFreight;
	private java.lang.Double originalTotalFreight;
	private java.lang.Double presentTotalFreight;
	private java.lang.Double weightShipped;
	private java.lang.Integer boxesShipped;
	private java.lang.Integer orderStatus;
	private java.lang.String childOrderNumber;
	private java.lang.String logisticsPk;
	private java.lang.String logisticsStepInfoPk;
	private java.lang.Integer afterBoxes;
	private java.lang.Double afterWeight;
	private java.lang.String logisticsCarrierPk;
	private java.lang.String logisticsCarrierName;
	private java.lang.String goodsType;
	private java.lang.String goodsInfo;
	private java.lang.String childContractNo;
	private java.lang.String block;
	//columns END

	public void setOrderNumber(java.lang.String orderNumber) {
		this.orderNumber = orderNumber;
	}
	
	public java.lang.String getOrderNumber() {
		return this.orderNumber;
	}
	public void setGoodsPk(java.lang.String goodsPk) {
		this.goodsPk = goodsPk;
	}
	
	public java.lang.String getGoodsPk() {
		return this.goodsPk;
	}
	public void setWeight(java.lang.Double weight) {
		this.weight = weight;
	}
	
	public java.lang.Double getWeight() {
		return this.weight;
	}
	public void setBoxes(java.lang.Integer boxes) {
		this.boxes = boxes;
	}
	
	public java.lang.Integer getBoxes() {
		return this.boxes;
	}
	public void setOriginalPrice(java.lang.Double originalPrice) {
		this.originalPrice = originalPrice;
	}
	
	public java.lang.Double getOriginalPrice() {
		return this.originalPrice;
	}
	public void setPresentPrice(java.lang.Double presentPrice) {
		this.presentPrice = presentPrice;
	}
	
	public java.lang.Double getPresentPrice() {
		return this.presentPrice;
	}
	public void setOriginalFreight(java.lang.Double originalFreight) {
		this.originalFreight = originalFreight;
	}
	
	public java.lang.Double getOriginalFreight() {
		return this.originalFreight;
	}
	public void setPresentFreight(java.lang.Double presentFreight) {
		this.presentFreight = presentFreight;
	}
	
	public java.lang.Double getPresentFreight() {
		return this.presentFreight;
	}
	public void setOriginalTotalFreight(java.lang.Double originalTotalFreight) {
		this.originalTotalFreight = originalTotalFreight;
	}
	
	public java.lang.Double getOriginalTotalFreight() {
		return this.originalTotalFreight;
	}
	public void setPresentTotalFreight(java.lang.Double presentTotalFreight) {
		this.presentTotalFreight = presentTotalFreight;
	}
	
	public java.lang.Double getPresentTotalFreight() {
		return this.presentTotalFreight;
	}
	public void setWeightShipped(java.lang.Double weightShipped) {
		this.weightShipped = weightShipped;
	}
	
	public java.lang.Double getWeightShipped() {
		return this.weightShipped;
	}
	public void setBoxesShipped(java.lang.Integer boxesShipped) {
		this.boxesShipped = boxesShipped;
	}
	
	public java.lang.Integer getBoxesShipped() {
		return this.boxesShipped;
	}
	public void setOrderStatus(java.lang.Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	public java.lang.Integer getOrderStatus() {
		return this.orderStatus;
	}
	public void setChildOrderNumber(java.lang.String childOrderNumber) {
		this.childOrderNumber = childOrderNumber;
	}
	
	public java.lang.String getChildOrderNumber() {
		return this.childOrderNumber;
	}
	public void setLogisticsPk(java.lang.String logisticsPk) {
		this.logisticsPk = logisticsPk;
	}
	
	public java.lang.String getLogisticsPk() {
		return this.logisticsPk;
	}
	public void setLogisticsStepInfoPk(java.lang.String logisticsStepInfoPk) {
		this.logisticsStepInfoPk = logisticsStepInfoPk;
	}
	
	public java.lang.String getLogisticsStepInfoPk() {
		return this.logisticsStepInfoPk;
	}
	public void setAfterBoxes(java.lang.Integer afterBoxes) {
		this.afterBoxes = afterBoxes;
	}
	
	public java.lang.Integer getAfterBoxes() {
		return this.afterBoxes;
	}
	public void setAfterWeight(java.lang.Double afterWeight) {
		this.afterWeight = afterWeight;
	}
	
	public java.lang.Double getAfterWeight() {
		return this.afterWeight;
	}
	public void setLogisticsCarrierPk(java.lang.String logisticsCarrierPk) {
		this.logisticsCarrierPk = logisticsCarrierPk;
	}
	
	public java.lang.String getLogisticsCarrierPk() {
		return this.logisticsCarrierPk;
	}
	public void setLogisticsCarrierName(java.lang.String logisticsCarrierName) {
		this.logisticsCarrierName = logisticsCarrierName;
	}
	
	public java.lang.String getLogisticsCarrierName() {
		return this.logisticsCarrierName;
	}
	public void setGoodsType(java.lang.String goodsType) {
		this.goodsType = goodsType;
	}
	
	public java.lang.String getGoodsType() {
		return this.goodsType;
	}
	public void setGoodsInfo(java.lang.String goodsInfo) {
		this.goodsInfo = goodsInfo;
	}
	
	public java.lang.String getGoodsInfo() {
		return this.goodsInfo;
	}
	public void setChildContractNo(java.lang.String childContractNo) {
		this.childContractNo = childContractNo;
	}
	
	public java.lang.String getChildContractNo() {
		return this.childContractNo;
	}

	public java.lang.String getBlock() {
		return block;
	}

	public void setBlock(java.lang.String block) {
		this.block = block;
	}
	

}