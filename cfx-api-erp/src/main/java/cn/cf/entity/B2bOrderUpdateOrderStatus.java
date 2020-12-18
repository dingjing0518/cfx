package cn.cf.entity;


import java.util.List;

public class B2bOrderUpdateOrderStatus {
	
	
	private String orderNumber;
	
	private List<B2bOrderGoodsUpdateOrderStatus> items;

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public List<B2bOrderGoodsUpdateOrderStatus> getItems() {
		return items;
	}

	public void setItems(List<B2bOrderGoodsUpdateOrderStatus> items) {
		this.items = items;
	}
	
	
}
