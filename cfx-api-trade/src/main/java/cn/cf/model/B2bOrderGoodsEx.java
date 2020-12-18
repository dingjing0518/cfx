package cn.cf.model;

import cn.cf.constant.Block;
import cn.cf.dto.B2bAuctionOfferDto;
import cn.cf.dto.B2bContractGoodsDto;
import cn.cf.entity.B2bGoodsDtoMa;
import cn.cf.entity.Pgoods;
import cn.cf.util.ArithUtil;
import cn.cf.util.KeyUtils;

import com.alibaba.fastjson.JSON;

public class B2bOrderGoodsEx extends B2bOrderGoods{
	
	private Double totalPrice;
	
	private Double adminFee;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public B2bOrderGoodsEx(Pgoods pg, String orderNumber,Integer orderStatus,B2bAuctionOfferDto offer,Integer type, int childCount) {
		this.setOrderNumber(orderNumber);
		this.setChildOrderNumber(KeyUtils.getchildOrderNumber(orderNumber, childCount));
		this.setOrderStatus(orderStatus);
		B2bGoodsDtoMa gdto = pg.getGdto();
		//商家承运无自提管理费
		if(null!= gdto.getCfGoods() ){
			if( null!=type&&type==2){
				gdto.getCfGoods().setAdminFee(0d);
			}
			adminFee = (null==gdto.getCfGoods().getAdminFee()?0d:gdto.getCfGoods().getAdminFee());
			this.setGoodsInfo(JSON.toJSONString(gdto.getCfGoods()));
		}else{
			this.setGoodsInfo(JSON.toJSONString(gdto.getSxGoods()));
		}
		this.setBoxes(pg.getBoxes());
		if(pg.getWeight()>0d){
			this.setWeight(pg.getWeight());
		}else{
			this.setWeight(
					ArithUtil.mul(gdto.getTareWeight(), pg.getBoxes()));//吨
			if(Block.CF.getValue().equals(gdto.getBlock())){
				this.setWeight(ArithUtil.div(this.getWeight(),1000));
			}
		}
		this.setOriginalPrice(gdto.getSalePrice());
		//竞拍商品
		if(null != offer){
			this.setPresentPrice(offer.getAuctionPrice());
		}else{
			this.setPresentPrice(this.getOriginalPrice());// 默认为原单价
		}
		//有阶梯运价
		if(null != pg.getFreight()&& pg.getFreight() >0d){
			this.setOriginalFreight(pg.getFreight());// 运费原单价
			if(Block.CF.getValue().equals(gdto.getBlock())){
				this.setOriginalTotalFreight(ArithUtil.mul(pg.getFreight(), this.getWeight()));
			}else{
				this.setOriginalTotalFreight(ArithUtil.mul(pg.getFreight(), ArithUtil.div(this.getWeight(), 1000)));
			}
		//无阶梯运价
		}else{
			this.setOriginalFreight(0d);// 运费原单价
			this.setOriginalTotalFreight(null==pg.getTotalFreight()?0d:pg.getTotalFreight());
		}
		this.setPresentFreight(this.getOriginalFreight());// 运费成交单价
		this.setPresentTotalFreight(this.getOriginalTotalFreight());
		this.setLogisticsPk(pg.getLogisticsPk());
		this.setLogisticsStepInfoPk(pg.getLogisticsStepInfoPk());
		this.setGoodsType(gdto.getType());
		
		if(1 == orderStatus){
			this.setAfterBoxes(this.getBoxes());
			this.setAfterWeight(this.getWeight());
		}else{
			this.setAfterBoxes(0);
			this.setAfterWeight(0d);
		}
		this.setLogisticsCarrierPk(pg.getLogisticsCarrierPk());
		this.setLogisticsCarrierName(pg.getLogisticsCarrierName());
		this.setGoodsPk(pg.getGdto().getPk());
		this.setBlock(pg.getGdto().getBlock());
		
	}
	
 

	public B2bOrderGoodsEx(String orderNumber, B2bContractGoodsDto cm,B2bContractGoods c,String block) {
		this.setOrderNumber(orderNumber);
		this.setChildOrderNumber(Block.SX.getValue().equals(block)?Block.SX.getOrderType()+KeyUtils.getOrderNumber()
				:Block.CF.getOrderType()+KeyUtils.getOrderNumber());
		this.setOrderStatus(0);
		this.setChildContractNo(cm.getChildOrderNumber());
		//b2b_goods值
		//b2b_contract 值
		this.setGoodsPk(cm.getGoodsPk());
		this.setGoodsType(cm.getGoodsType());
		this.setLogisticsCarrierPk(cm.getCarrierPk());
		this.setLogisticsCarrierName(cm.getCarrier());
		//parm
		this.setBoxes(c.getBoxesShipped());
		this.setWeight(c.getWeightShipped());//吨
		this.setAfterBoxes(0);
		this.setAfterWeight(0.0);
		this.setOriginalPrice(cm.getBasicPrice());//商品原单价
		this.setPresentPrice(cm.getContractPrice());// 合同单价
		
			this.setLogisticsPk(cm.getLogisticsPk());
			this.setLogisticsStepInfoPk(cm.getLogisticsStepInfoPk());
			this.setOriginalFreight(cm.getFreight()==null|| "".equals(cm.getFreight())?0:cm.getFreight());// 运费原单价
			if(Block.CF.getValue().equals(block)){
				this.setOriginalTotalFreight(ArithUtil.mul(cm.getFreight(),this.getWeight()));// 运费原总价
			}else{
				this.setOriginalTotalFreight(ArithUtil.mul(cm.getFreight(),ArithUtil.div(this.getWeight(), 1000)));// 运费原总价
			}
			this.setPresentFreight(this.getOriginalFreight());// 运费成交单价
			this.setPresentTotalFreight(this.getOriginalTotalFreight());// 运费实际总价
			this.setGoodsInfo(cm.getGoodsInfo());
			this.setBlock(block);
			this.setGoodsType(cm.getGoodsType());
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Double getAdminFee() {
		return adminFee;
	}

	public void setAdminFee(Double adminFee) {
		this.adminFee = adminFee;
	}
	
	
}
