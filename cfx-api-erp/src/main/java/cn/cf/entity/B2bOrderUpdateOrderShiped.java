package cn.cf.entity;

import java.util.List;

public class B2bOrderUpdateOrderShiped {
	
	
	private String orderNumber;
	
	private List<B2bOrderGoodsUpdateOrderShiped> items;

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public List<B2bOrderGoodsUpdateOrderShiped> getItems() {
		return items;
	}

	public void setItems(List<B2bOrderGoodsUpdateOrderShiped> items) {
		this.items = items;
	}
	
}
