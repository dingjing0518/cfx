package cn.cf.model;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class B2bContractGoods extends BaseModel implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String childOrderNumber;
	private java.lang.String contractNo;
	private java.lang.Integer detailNumber;
	private java.lang.String goodsPk;
	private java.lang.String goodsType;
	private java.lang.String brandName;
	private java.lang.Integer boxes;
	private java.lang.Double weight;
	private java.lang.Double basicPrice;
	private java.lang.Double freight;
	private java.lang.Double contractPrice;
	private java.lang.Double discount;
	private java.lang.String carrierPk;
	private java.lang.String carrier;
	private java.lang.Integer isDelete;
	private java.util.Date insertTime;
	private java.util.Date updateTime;
	private java.lang.Double weightShipped;
	private java.lang.Integer boxesShipped;
	private java.lang.Integer contractStatus;
	private java.lang.String logisticsPk;
	private java.lang.String logisticsStepInfoPk;
	private java.lang.String goodsInfo;
	private java.lang.String block;
	//columns END

	public void setChildOrderNumber(java.lang.String childOrderNumber) {
		this.childOrderNumber = childOrderNumber;
	}
	
	public java.lang.String getChildOrderNumber() {
		return this.childOrderNumber;
	}
	public void setContractNo(java.lang.String contractNo) {
		this.contractNo = contractNo;
	}
	
	public java.lang.String getContractNo() {
		return this.contractNo;
	}
	public void setDetailNumber(java.lang.Integer detailNumber) {
		this.detailNumber = detailNumber;
	}
	
	public java.lang.Integer getDetailNumber() {
		return this.detailNumber;
	}
	public void setGoodsPk(java.lang.String goodsPk) {
		this.goodsPk = goodsPk;
	}
	
	public java.lang.String getGoodsPk() {
		return this.goodsPk;
	}
	public void setGoodsType(java.lang.String goodsType) {
		this.goodsType = goodsType;
	}
	
	public java.lang.String getGoodsType() {
		return this.goodsType;
	}
	public void setBrandName(java.lang.String brandName) {
		this.brandName = brandName;
	}
	
	public java.lang.String getBrandName() {
		return this.brandName;
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
	public void setBasicPrice(java.lang.Double basicPrice) {
		this.basicPrice = basicPrice;
	}
	
	public java.lang.Double getBasicPrice() {
		return this.basicPrice;
	}
	public void setFreight(java.lang.Double freight) {
		this.freight = freight;
	}
	
	public java.lang.Double getFreight() {
		return this.freight;
	}
	public void setContractPrice(java.lang.Double contractPrice) {
		this.contractPrice = contractPrice;
	}
	
	public java.lang.Double getContractPrice() {
		return this.contractPrice;
	}
	public void setDiscount(java.lang.Double discount) {
		this.discount = discount;
	}
	
	public java.lang.Double getDiscount() {
		return this.discount;
	}
	public void setCarrierPk(java.lang.String carrierPk) {
		this.carrierPk = carrierPk;
	}
	
	public java.lang.String getCarrierPk() {
		return this.carrierPk;
	}
	public void setCarrier(java.lang.String carrier) {
		this.carrier = carrier;
	}
	
	public java.lang.String getCarrier() {
		return this.carrier;
	}
	public void setIsDelete(java.lang.Integer isDelete) {
		this.isDelete = isDelete;
	}
	
	public java.lang.Integer getIsDelete() {
		return this.isDelete;
	}
	public void setInsertTime(java.util.Date insertTime) {
		this.insertTime = insertTime;
	}
	
	public java.util.Date getInsertTime() {
		return this.insertTime;
	}
	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}
	
	public java.util.Date getUpdateTime() {
		return this.updateTime;
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
	public void setContractStatus(java.lang.Integer contractStatus) {
		this.contractStatus = contractStatus;
	}
	
	public java.lang.Integer getContractStatus() {
		return this.contractStatus;
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
	public void setGoodsInfo(java.lang.String goodsInfo) {
		this.goodsInfo = goodsInfo;
	}
	
	public java.lang.String getGoodsInfo() {
		return this.goodsInfo;
	}

	public java.lang.String getBlock() {
		return block;
	}

	public void setBlock(java.lang.String block) {
		this.block = block;
	}
	

}