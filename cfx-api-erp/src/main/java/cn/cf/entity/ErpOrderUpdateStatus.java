package cn.cf.entity;

import java.util.ArrayList;
import java.util.List;

import cn.cf.dto.B2bOrderDto;
import cn.cf.dto.B2bOrderGoodsDto;
import cn.cf.dto.B2bOrderGoodsDtoEx;

public class ErpOrderUpdateStatus {
	
	
	private String orderNumber;
	/*private String erpOrderCode ;
	
	private String erpContractNumber;
	
	private Integer status;*/
	
	private String payType;
	
	private List<ErpOrderGoodsUpdateStatus> subOrderDetails;

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public List<ErpOrderGoodsUpdateStatus> getSubOrderDetails() {
		return subOrderDetails;
	}

	public void setSubOrderDetails(List<ErpOrderGoodsUpdateStatus> subOrderDetails) {
		this.subOrderDetails = subOrderDetails;
	}

	public ErpOrderUpdateStatus(B2bOrderDto orderDto,List<B2bOrderGoodsDtoEx> orderGoodsList,String paymentType){
		this.orderNumber = orderDto.getOrderNumber();
		List<ErpOrderGoodsUpdateStatus> subOrderList = new ArrayList<ErpOrderGoodsUpdateStatus>();
		if(orderGoodsList != null && orderGoodsList.size() > 0){
			for (B2bOrderGoodsDto b2bOrderGoodsDto : orderGoodsList) {
				ErpOrderGoodsUpdateStatus orderGoodsUpdateStatus = new ErpOrderGoodsUpdateStatus();
				orderGoodsUpdateStatus.setChildOrderNumber(b2bOrderGoodsDto.getChildOrderNumber());
				orderGoodsUpdateStatus.setStatus(b2bOrderGoodsDto.getOrderStatus() == null?"":String.valueOf(b2bOrderGoodsDto.getOrderStatus()));
				/*if(orderDto.getOrderStatus() == 4 || orderDto.getOrderStatus() == 7){
					orderGoodsUpdateStatus.setBoxesShipped(b2bOrderGoodsDto.getBoxesShipped()==null?"0":String.valueOf(b2bOrderGoodsDto.getBoxesShipped()));
					orderGoodsUpdateStatus.setWeightShipped(b2bOrderGoodsDto.getWeightShipped()==null?"0.0":String.valueOf(b2bOrderGoodsDto.getWeightShipped()));
				}*/
				subOrderList.add(orderGoodsUpdateStatus);
			}
		}
		this.subOrderDetails = subOrderList;
		this.payType = paymentType;
	}
}
