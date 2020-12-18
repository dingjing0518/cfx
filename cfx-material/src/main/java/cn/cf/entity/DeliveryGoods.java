package cn.cf.entity;

import cn.cf.constant.Block;
import cn.cf.json.JsonUtils;


public class DeliveryGoods {
	
	private String orderNumber;//订单号
	private String childOrderNumber;//子订单号
	private Integer deliverBoxes;//提货箱数
	private Double deliverWeight;//提货重量
	private Double presentPrice;//实际成交单价
	private Double principal;//本金
	private Double interest;//利息
	private Double serviceCharge;//服务费
	private Double sevenCharge;//七日服务费
	private String goodsInfo;//商品详情
	private String block;//板块
	
	private Integer boxes;//采购箱数
	private Double weight;//采购重量
	private Integer boxesShipped;//已发箱数
	private Double weightShipped;//已发重量
	private Integer noBoxesShipped;//未发箱数
	private Double noWeightShipped;//未发重量
	private CfGoods cfGoods;
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getChildOrderNumber() {
		return childOrderNumber;
	}
	public void setChildOrderNumber(String childOrderNumber) {
		this.childOrderNumber = childOrderNumber;
	}
	public Integer getDeliverBoxes() {
		return deliverBoxes;
	}
	public void setDeliverBoxes(Integer deliverBoxes) {
		this.deliverBoxes = deliverBoxes;
	}
	public Double getDeliverWeight() {
		return deliverWeight;
	}
	public void setDeliverWeight(Double deliverWeight) {
		this.deliverWeight = deliverWeight;
	}
	public String getBlock() {
		return block;
	}
	public void setBlock(String block) {
		this.block = block;
	}
	public Double getPresentPrice() {
		return presentPrice;
	}
	public void setPresentPrice(Double presentPrice) {
		this.presentPrice = presentPrice;
	}
	public String getGoodsInfo() {
		return goodsInfo;
	}
	public void setGoodsInfo(String goodsInfo) {
		this.goodsInfo = goodsInfo;
	}
	public Integer getBoxes() {
		return boxes;
	}
	public void setBoxes(Integer boxes) {
		this.boxes = boxes;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public Integer getBoxesShipped() {
		return boxesShipped;
	}
	public void setBoxesShipped(Integer boxesShipped) {
		this.boxesShipped = boxesShipped;
	}
	public Double getWeightShipped() {
		return weightShipped;
	}
	public void setWeightShipped(Double weightShipped) {
		this.weightShipped = weightShipped;
	}
	public Integer getNoBoxesShipped() {
		return noBoxesShipped;
	}
	public void setNoBoxesShipped(Integer noBoxesShipped) {
		this.noBoxesShipped = noBoxesShipped;
	}
	public Double getNoWeightShipped() {
		return noWeightShipped;
	}
	public void setNoWeightShipped(Double noWeightShipped) {
		this.noWeightShipped = noWeightShipped;
	}
	public Double getPrincipal() {
		return principal;
	}
	public void setPrincipal(Double principal) {
		this.principal = principal;
	}
	public Double getInterest() {
		return interest;
	}
	public void setInterest(Double interest) {
		this.interest = interest;
	}
	public Double getServiceCharge() {
		return serviceCharge;
	}
	public void setServiceCharge(Double serviceCharge) {
		this.serviceCharge = serviceCharge;
	}
	public CfGoods getCfGoods() {
		if(null == this.cfGoods){
			if(null !=this.getGoodsInfo() && !"".equals(this.getGoodsInfo()) &&
					Block.CF.getValue().equals(this.getBlock())){
				this.cfGoods = JsonUtils.toBean(this.getGoodsInfo(),CfGoods.class);
			}
		}
		return cfGoods;
	}
	public void setCfGoods(CfGoods cfGoods) {
		this.cfGoods = cfGoods;
	}
	public Double getSevenCharge() {
		return sevenCharge;
	}
	public void setSevenCharge(Double sevenCharge) {
		this.sevenCharge = sevenCharge;
	}
	
	
	
	
}
