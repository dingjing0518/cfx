package cn.cf.entity;

import cn.cf.constant.Block;
import cn.cf.dto.B2bOrderGoodsDto;
import cn.cf.json.JsonUtils;

public class B2bOrderGoodsDtoMa extends B2bOrderGoodsDto{

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
	private CfGoods cfGoods;
	private SxGoods sxGoods;
	
	public B2bOrderGoodsDtoMa(){
		
	}
	
	
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
	public SxGoods getSxGoods() {
		if(null == this.sxGoods){
			if(null !=this.getGoodsInfo() && !"".equals(this.getGoodsInfo()) &&
					Block.SX.getValue().equals(this.getBlock())){
				this.sxGoods = JsonUtils.toBean(this.getGoodsInfo(),SxGoods.class);
			}	
		}
		return sxGoods;
	}
	public void setSxGoods(SxGoods sxGoods) {
		this.sxGoods = sxGoods;
	}
	
	
	

}
