package cn.cf.entity;

import java.util.List;

public class ErpUpdateSubOrderType  {
	
	private String orderNumber;
	
	private List<ErpUpdateSubOrderGoodsType> subOrderDetails;

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public List<ErpUpdateSubOrderGoodsType> getSubOrderDetails() {
		return subOrderDetails;
	}

	public void setSubOrderDetails(List<ErpUpdateSubOrderGoodsType> subOrderDetails) {
		this.subOrderDetails = subOrderDetails;
	}
	
	
}
