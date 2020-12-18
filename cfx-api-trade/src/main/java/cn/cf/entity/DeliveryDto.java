package cn.cf.entity;

import java.util.List;

import cn.cf.dto.B2bOrderGoodsDtoEx;

public class DeliveryDto {

	private DeliveryBasic deliveryBasic;
	private List<B2bOrderGoodsDtoEx> ogList;
	private List<B2bPayVoucher> pvList;
	private List<DeliveryOrder> doList;
	public DeliveryBasic getDeliveryBasic() {
		return deliveryBasic;
	}
	public void setDeliveryBasic(DeliveryBasic deliveryBasic) {
		this.deliveryBasic = deliveryBasic;
	}
	public List<DeliveryOrder> getDoList() {
		return doList;
	}
	public void setDoList(List<DeliveryOrder> doList) {
		this.doList = doList;
	}
	
	public List<B2bOrderGoodsDtoEx> getOgList() {
		return ogList;
	}
	public void setOgList(List<B2bOrderGoodsDtoEx> ogList) {
		this.ogList = ogList;
	}
	public List<B2bPayVoucher> getPvList() {
		return pvList;
	}
	public void setPvList(List<B2bPayVoucher> pvList) {
		this.pvList = pvList;
	}
	
}
