package cn.cf.entity;

import java.util.List;

import cn.cf.dto.B2bOrderDto;
import cn.cf.json.JsonUtils;


public class B2bOrderDtoMa extends B2bOrderDto{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private AddressInfo address;
	
	private PurchaserInfo purchaser;
	
	private SupplierInfo supplier;
	
	private List<B2bOrderGoodsDtoMa> orderGoodsList;
	
	public B2bOrderDtoMa() {
		
	}
	
	public AddressInfo getAddress() {
		if(null == this.address){
			if(null != this.getAddressInfo() && !"".equals(this.getAddressInfo())){
				this.address = JsonUtils.toBean(this.getAddressInfo(),AddressInfo.class);
			}
		}
		return address;
	}

	public void setAddress(AddressInfo address) {
		this.address = address;
	}

	public PurchaserInfo getPurchaser() {
		if(null == this.purchaser){
			if(null != this.getPurchaserInfo() && !"".equals(this.getPurchaserInfo())){
				this.purchaser =JsonUtils.toBean(this.getPurchaserInfo(),PurchaserInfo.class);
			}
		}
		return purchaser;
	}

	public void setPurchaser(PurchaserInfo purchaser) {
		this.purchaser = purchaser;
	}

	public SupplierInfo getSupplier() {
		if(null == this.supplier){
			if(null != this.getSupplierInfo() && !"".equals(this.getSupplierInfo())){
				this.supplier = JsonUtils.toBean(this.getSupplierInfo(),SupplierInfo.class);
			}
		}
		return supplier;
	}

	public void setSupplier(SupplierInfo supplier) {
		this.supplier = supplier;
	}

	public List<B2bOrderGoodsDtoMa> getOrderGoodsList() {
		return orderGoodsList;
	}

	public void setOrderGoodsList(List<B2bOrderGoodsDtoMa> orderGoodsList) {
		this.orderGoodsList = orderGoodsList;
	}

	
	
	
	
	
	 
	
	
	
}
