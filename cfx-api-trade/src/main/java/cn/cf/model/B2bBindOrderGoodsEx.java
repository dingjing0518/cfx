package cn.cf.model;


import cn.cf.constant.Block;
import cn.cf.dto.B2bBindDto;
import cn.cf.entity.B2bGoodsDtoMa;
import cn.cf.entity.Pgoods;
import cn.cf.util.ArithUtil;
import cn.cf.util.KeyUtils;

import com.alibaba.fastjson.JSON;

public class B2bBindOrderGoodsEx  {
	private static final long serialVersionUID = 1L;
	private java.lang.String childOrderNumber;
	private java.lang.Double weight;
	private java.lang.Integer boxes;
	private java.lang.Double price;
	private java.lang.Double freight;
	private java.lang.Double totalFreight;
	private java.lang.String logisticsPk;
	private java.lang.String logisticsStepInfoPk;
	private java.lang.String goodsType;
	private java.lang.String orderNumber;
	private java.lang.String bindGoodPk;
	private String goodsPk;
	private java.lang.String goodsInfo;
	private Double totalPrice;
	private String block;
	private Double adminFee;
	
public B2bBindOrderGoodsEx (){
		
	}


    public String getBlock() {
	return block;
}


public void setBlock(String block) {
	this.block = block;
}


	public String getGoodsPk() {
	return goodsPk;
}


public void setGoodsPk(String goodsPk) {
	this.goodsPk = goodsPk;
}


	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public java.lang.String getChildOrderNumber() {
		return childOrderNumber;
	}

	public void setChildOrderNumber(java.lang.String childOrderNumber) {
		this.childOrderNumber = childOrderNumber;
	}

 

	public java.lang.Double getWeight() {
		return weight;
	}

	public void setWeight(java.lang.Double weight) {
		this.weight = weight;
	}

	public java.lang.Integer getBoxes() {
		return boxes;
	}

	public void setBoxes(java.lang.Integer boxes) {
		this.boxes = boxes;
	}

	public java.lang.Double getPrice() {
		return price;
	}

	public void setPrice(java.lang.Double price) {
		this.price = price;
	}

	public java.lang.Double getFreight() {
		return freight;
	}

	public void setFreight(java.lang.Double freight) {
		this.freight = freight;
	}

	public java.lang.Double getTotalFreight() {
		return totalFreight;
	}

	public void setTotalFreight(java.lang.Double totalFreight) {
		this.totalFreight = totalFreight;
	}

	public java.lang.String getLogisticsPk() {
		return logisticsPk;
	}

	public void setLogisticsPk(java.lang.String logisticsPk) {
		this.logisticsPk = logisticsPk;
	}

	public java.lang.String getLogisticsStepInfoPk() {
		return logisticsStepInfoPk;
	}

	public void setLogisticsStepInfoPk(java.lang.String logisticsStepInfoPk) {
		this.logisticsStepInfoPk = logisticsStepInfoPk;
	}

	public java.lang.String getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(java.lang.String goodsType) {
		this.goodsType = goodsType;
	}

	public java.lang.String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(java.lang.String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public java.lang.String getBindGoodPk() {
		return bindGoodPk;
	}

	public void setBindGoodPk(java.lang.String bindGoodPk) {
		this.bindGoodPk = bindGoodPk;
	}

	public java.lang.String getGoodsInfo() {
		return goodsInfo;
	}

	public void setGoodsInfo(java.lang.String goodsInfo) {
		this.goodsInfo = goodsInfo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Double getAdminFee() {
		return adminFee;
	}

	public void setAdminFee(Double adminFee) {
		this.adminFee = adminFee;
	}
	public B2bBindOrderGoodsEx(Pgoods pg, String orderNumber, B2bBindDto bind,Integer type) {
		this.setOrderNumber(orderNumber);
        this.setChildOrderNumber(KeyUtils.getOrderNumber());
        B2bGoodsDtoMa gdto = pg.getGdto();
        this.setGoodsPk(gdto.getPk());
      //商家承运无自提管理费
      		if(Block.CF.getValue().equals(gdto.getBlock())){
      			if( null!=type&&type==2){
      				gdto.getCfGoods().setAdminFee(0d);
      			}
      			adminFee = (null==gdto.getCfGoods().getAdminFee()?0d:gdto.getCfGoods().getAdminFee());
      			this.setGoodsInfo(JSON.toJSONString(gdto.getCfGoods()));
      			this.setWeight( ArithUtil.div(
      					ArithUtil.mul(gdto.getTareWeight(), pg.getBoxes()), 1000));//吨
      		}else{
      			this.setGoodsInfo(JSON.toJSONString(gdto.getSxGoods()));
      			this.setWeight(
      					ArithUtil.mul(gdto.getTareWeight(), pg.getBoxes()));//千克
      		}
        this.setBoxes(pg.getBoxes());
        //有阶梯运价
        if(null != pg.getFreight()&& pg.getFreight() >0d){
  			this.setFreight(pg.getFreight());// 运费原单价
  			if(Block.CF.getValue().equals(gdto.getBlock())){
  				this.setTotalFreight(ArithUtil
  						.mul(pg.getFreight(), this.getWeight()));// 运费原总价
  			}else{
  				this.setTotalFreight(ArithUtil
  						.mul(pg.getFreight(), ArithUtil.div(this.getWeight(), 1000)));// 运费原总价
  			}
  		//无阶梯运价
  		}else{
  			this.setFreight(0d);
  			this.setTotalFreight(null==pg.getTotalFreight()?0d:pg.getTotalFreight());// 运费原总价
  		}	
        this.setPrice(ArithUtil.add(bind.getBindProductPrice(), pg.getIncreasePrice()));
        if(Block.SX.getValue().equals(gdto.getBlock())){
        	this.setTotalPrice(ArithUtil.round(ArithUtil.mul(this.getWeight(), this.getPrice()), 2));
		}else{
			this.setTotalPrice(ArithUtil.round(ArithUtil.mul(this.getWeight(), this.getPrice()+
					(null==gdto.getCfGoods().getAdminFee()?0d:gdto.getCfGoods().getAdminFee())+
					(null==gdto.getCfGoods().getLoadFee()?0d:gdto.getCfGoods().getLoadFee())+
					(null==gdto.getCfGoods().getPackageFee()?0d:gdto.getCfGoods().getPackageFee())), 2));
		}
        this.setBlock(gdto.getBlock());
        this.setLogisticsPk(pg.getLogisticsPk());
        this.setLogisticsStepInfoPk(pg.getLogisticsStepInfoPk());
        this.setGoodsType(gdto.getType());
        this.setBindGoodPk(null==pg.getBindGood()?"":pg.getBindGood().getPk());
	}

}
