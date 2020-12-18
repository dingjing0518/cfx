package cn.cf.entity;

import java.util.List;

public class TransportFinally {

	private String orderNumber;
	private String orderLineNo;
	List<SubTransportFinally> items;
	
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getOrderLineNo() {
		return orderLineNo;
	}
	public void setOrderLineNo(String orderLineNo) {
		this.orderLineNo = orderLineNo;
	}
	public List<SubTransportFinally> getItems() {
		return items;
	}
	public void setItems(List<SubTransportFinally> items) {
		this.items = items;
	}
	
	
}
