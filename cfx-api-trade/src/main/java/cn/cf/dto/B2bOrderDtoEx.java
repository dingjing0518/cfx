package cn.cf.dto;

import java.util.List;
import cn.cf.entity.B2bPayVoucher;
import cn.cf.entity.OrderRecord;

public class B2bOrderDtoEx extends B2bOrderDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<B2bPayVoucher> payVoucherList;
	private List<B2bOrderGoodsDtoEx> orderGoodsList;
	private List<OrderRecord> orderRecordList;
	private Integer loanNumberStatus;
	private String purchaserName;
	private String supplierName;
	private String provinceName;
	private String cityName;
	private String areaName;
	private String townName;
	private String address;
	private String goodsInfo;
	private Double originalPrice;
	private Double presentPrice;
	private String childOrderNumber;
	private Double presentTotalFreight;
	private String paymentName;
	private String economicsGoodsName;
	private String sourceName;
	private int boxes;
	private String meno;
	private String orderStatusName;
	private String purchaseTypeName;
	private Double presentTotalPrice;
	private String purchaserInfo;
	private String supplierInfo;
	private Integer onlinePayStatus;
	private Integer billPayStatus;
	private String billGoodsType;
	private Integer showPft;
	private String promnt;//提示语句
	private String bank;
	private Integer economicsGoodsType;

	public String getPromnt() {
		return promnt;
	}

	public void setPromnt(String promnt) {
		this.promnt = promnt;
	}

	@Override
	public String getPurchaserInfo() {
		return purchaserInfo;
	}

	@Override
	public void setPurchaserInfo(String purchaserInfo) {
		this.purchaserInfo = purchaserInfo;
	}

	@Override
	public String getSupplierInfo() {
		return supplierInfo;
	}

	@Override
	public void setSupplierInfo(String supplierInfo) {
		this.supplierInfo = supplierInfo;
	}

	public String getPurchaseTypeName() {
		return purchaseTypeName;
	}

	public void setPurchaseTypeName(String purchaseTypeName) {
		this.purchaseTypeName = purchaseTypeName;
	}

	public String getOrderStatusName() {
		return orderStatusName;
	}

	public void setOrderStatusName(String orderStatusName) {
		this.orderStatusName = orderStatusName;
	}




	public String getChildOrderNumber() {
		return childOrderNumber;
	}

	public void setChildOrderNumber(String childOrderNumber) {
		this.childOrderNumber = childOrderNumber;
	}


	@Override
	public String getPaymentName() {
		return paymentName;
	}

	@Override
	public void setPaymentName(String paymentName) {
		this.paymentName = paymentName;
	}

	@Override
	public String getEconomicsGoodsName() {
		return economicsGoodsName;
	}

	@Override
	public void setEconomicsGoodsName(String economicsGoodsName) {
		this.economicsGoodsName = economicsGoodsName;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public int getBoxes() {
		return boxes;
	}

	public void setBoxes(int boxes) {
		this.boxes = boxes;
	}



	@Override
	public String getMeno() {
		return meno;
	}

	@Override
	public void setMeno(String meno) {
		this.meno = meno;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getTownName() {
		return townName;
	}

	public void setTownName(String townName) {
		this.townName = townName;
	}

	public String getContactsTel() {
		return contactsTel;
	}

	public void setContactsTel(String contactsTel) {
		this.contactsTel = contactsTel;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getPlantName() {
		return plantName;
	}

	public void setPlantName(String plantName) {
		this.plantName = plantName;
	}

	public Double getPresentTotalPrice() {
		return presentTotalPrice;
	}

	public void setPresentTotalPrice(Double presentTotalPrice) {
		this.presentTotalPrice = presentTotalPrice;
	}

	@Override
	public Double getActualAmount() {
		return actualAmount;
	}

	@Override
	public void setActualAmount(Double actualAmount) {
		this.actualAmount = actualAmount;
	}

	private String contactsTel;
	private String contacts;
	private String plantName;
	private Double actualAmount;

	public String getGoodsInfo() {
		return goodsInfo;
	}

	public void setGoodsInfo(String goodsInfo) {
		this.goodsInfo = goodsInfo;
	}

	public String getPurchaserName() {
		return purchaserName;
	}

	public void setPurchaserName(String purchaserName) {
		this.purchaserName = purchaserName;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	private Integer logisticsModelType;

	public Integer getLogisticsModelType() {
		return logisticsModelType;
	}

	public void setLogisticsModelType(Integer logisticsModelType) {
		this.logisticsModelType = logisticsModelType;
	}

	public B2bOrderDtoEx() {
		// TODO Auto-generated constructor stub
	}

	public B2bOrderDtoEx(B2bContractDto cdto) {
		this.setOrderNumber(cdto.getContractNo());
		this.setPurchaserPk(cdto.getPurchaserPk());
		this.setSupplierPk(cdto.getSupplierPk());
		this.setStorePk(cdto.getStorePk());
		this.setEmployeePk(cdto.getEmployeePk());
		this.setEmployeeName(cdto.getSalesman());
		this.setEmployeeNumber(cdto.getSalesmanNumber());
		this.setSupplierInfo(cdto.getSupplierInfo());
		this.setPurchaserInfo(cdto.getPurchaserInfo());
	}

	public List<B2bPayVoucher> getPayVoucherList() {
		return payVoucherList;
	}

	public void setPayVoucherList(List<B2bPayVoucher> payVoucherList) {
		this.payVoucherList = payVoucherList;
	}

	public List<B2bOrderGoodsDtoEx> getOrderGoodsList() {
		return orderGoodsList;
	}

	public void setOrderGoodsList(List<B2bOrderGoodsDtoEx> orderGoodsList) {
		this.orderGoodsList = orderGoodsList;
	}

	public List<OrderRecord> getOrderRecordList() {
		return orderRecordList;
	}

	public void setOrderRecordList(List<OrderRecord> orderRecordList) {
		this.orderRecordList = orderRecordList;
	}

	public Integer getLoanNumberStatus() {
		return loanNumberStatus;
	}

	public void setLoanNumberStatus(Integer loanNumberStatus) {
		this.loanNumberStatus = loanNumberStatus;
	}

	public Double getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(Double originalPrice) {
		this.originalPrice = originalPrice;
	}

	public Double getPresentPrice() {
		return presentPrice;
	}

	public void setPresentPrice(Double presentPrice) {
		this.presentPrice = presentPrice;
	}


	public Double getPresentTotalFreight() {
		return presentTotalFreight;
	}

	public void setPresentTotalFreight(Double presentTotalFreight) {
		this.presentTotalFreight = presentTotalFreight;
	}

	public Integer getOnlinePayStatus() {
		return onlinePayStatus;
	}

	public void setOnlinePayStatus(Integer onlinePayStatus) {
		this.onlinePayStatus = onlinePayStatus;
	}

	public Integer getBillPayStatus() {
		return billPayStatus;
	}

	public void setBillPayStatus(Integer billPayStatus) {
		this.billPayStatus = billPayStatus;
	}

	public String getBillGoodsType() {
		return billGoodsType;
	}

	public void setBillGoodsType(String billGoodsType) {
		this.billGoodsType = billGoodsType;
	}

	public Integer getShowPft() {
		return showPft;
	}

	public void setShowPft(Integer showPft) {
		this.showPft = showPft;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public Integer getEconomicsGoodsType() {
		return economicsGoodsType;
	}

	public void setEconomicsGoodsType(Integer economicsGoodsType) {
		this.economicsGoodsType = economicsGoodsType;
	}
	
	
	
	
}
