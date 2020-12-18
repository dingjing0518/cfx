package cn.cf.entity;

import java.util.List;
import com.alibaba.fastjson.JSON;
import cn.cf.dto.B2bContractDto;


public class B2bContractDtoMa extends B2bContractDto{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private AddressInfo address;
	
	private PurchaserInfo purchaser;
	
	private SupplierInfo supplier;
	
	private List<B2bContractGoodsDtoMa> contractGoodsList;
	
	public B2bContractDtoMa() {
		
	}
	
	/**
	 * 获取地址信息对象
	 * @return
	 */
	public AddressInfo getAddress() {
		if(null == this.address){
			if(null != this.getAddressInfo() && !"".equals(this.getAddressInfo())){
				this.address = JSON.parseObject(this.getAddressInfo(),AddressInfo.class);
			}
		}
		return address;
	}

	public void setAddress(AddressInfo address) {
		this.address = address;
	}

	/**
	 * 获取采购商信息对象
	 * @return
	 */
	public PurchaserInfo getPurchaser() {
		if(null == this.purchaser){
			if(null != this.getPurchaserInfo() && !"".equals(this.getPurchaserInfo())){
				this.purchaser = JSON.parseObject(this.getPurchaserInfo(),PurchaserInfo.class);
			}
		}
		return purchaser;
	}

	public void setPurchaser(PurchaserInfo purchaser) {
		this.purchaser = purchaser;
	}

	/**
	 * 获取供应商信息对象
	 * @return
	 */
	public SupplierInfo getSupplier() {
		if(null == this.supplier){
			if(null != this.getSupplierInfo() && !"".equals(this.getSupplierInfo())){
				this.supplier = JSON.parseObject(this.getSupplierInfo(),SupplierInfo.class);
			}
		}
		return supplier;
	}

	public void setSupplier(SupplierInfo supplier) {
		this.supplier = supplier;
	}

	public List<B2bContractGoodsDtoMa> getOrderGoodsList() {
		return contractGoodsList;
	}

	public void setOrderGoodsList(List<B2bContractGoodsDtoMa> contractGoodsList) {
		this.contractGoodsList = contractGoodsList;
	}

	
	
	
	
	
	 
	
	
	
}
