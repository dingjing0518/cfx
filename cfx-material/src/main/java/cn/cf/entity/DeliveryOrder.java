package cn.cf.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Id;

public class DeliveryOrder {

	@Id
	private String id;
	private String orderNumber;
	private Date date;
	private String deliveryNumber;
	private String deliveryGoodsInfo;
	private Integer status;//1待确认 2财务待确认 3已确认  4已驳回
	private Double deliveryAmount;//提货金额
	private List<DeliveryGoods> deliveryGoods;
	private List<B2bPayVoucher> payVoucherList;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getDeliveryNumber() {
		return deliveryNumber;
	}
	public void setDeliveryNumber(String deliveryNumber) {
		this.deliveryNumber = deliveryNumber;
	}
	public String getDeliveryGoodsInfo() {
		return deliveryGoodsInfo;
	}
	public void setDeliveryGoodsInfo(String deliveryGoodsInfo) {
		this.deliveryGoodsInfo = deliveryGoodsInfo;
	}
	public List<DeliveryGoods> getDeliveryGoods() {
		return deliveryGoods;
	}
	public void setDeliveryGoods(List<DeliveryGoods> deliveryGoods) {
		this.deliveryGoods = deliveryGoods;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Double getDeliveryAmount() {
		return deliveryAmount;
	}
	public void setDeliveryAmount(Double deliveryAmount) {
		this.deliveryAmount = deliveryAmount;
	}
	public List<B2bPayVoucher> getPayVoucherList() {
		return payVoucherList;
	}
	public void setPayVoucherList(List<B2bPayVoucher> payVoucherList) {
		this.payVoucherList = payVoucherList;
	}
	
	

	
}
