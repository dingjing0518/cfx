package cn.cf.entity;

import java.util.List;

public class B2bOrderUpdateWeight {
	
	
	private String orderNumber;
	private List<B2bOrderGoodsUpdateWeight> items;
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public List<B2bOrderGoodsUpdateWeight> getItems() {
		return items;
	}
	public void setItems(List<B2bOrderGoodsUpdateWeight> items) {
		this.items = items;
	}
	
}
