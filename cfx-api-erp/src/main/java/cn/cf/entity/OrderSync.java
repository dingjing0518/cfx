package cn.cf.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.cf.common.KeyWordUtil;
import cn.cf.dto.B2bOrderDtoEx;
import cn.cf.dto.B2bOrderGoodsDtoEx;
import cn.cf.dto.B2bTokenDto;
import cn.cf.util.DateUtil;

public class OrderSync {

	private String orderNumber;
	private Integer orderClassify;
	private String purchaserName;
	private String purchaserId;
	private Double orderAmount;
	private Double actualAmount;
	private String orderTime;
	private String meno;
	private String supplierName;
	private String logisticsModelId;
	private String organizationCode;
	private String employeeName;
	private String employeeNumber;
	private String contacts;
	private String contactsTel;
	private String address;
	private String signingCompany;
	private Integer purchaseType;
	private String provinceName;
	private String cityName;
	private String areaName;
	private String townName;
	private String bindProductId;
	private String contractNo;
	private String deliverDetails;
	private List<OrderGoodsSync> items;

	public OrderSync() {

	}

	/**
	 * 设置传入请求的参数
	 * 
	 * @param orderDto
	 * @param orderGoodsList
	 * @return
	 */
	public OrderSync(B2bOrderDtoEx orderDto, List<B2bOrderGoodsDtoEx> orderGoodsList,B2bTokenDto b2bTokenDto) {
		if (orderDto != null) {
			B2bOrderDtoMa b2bOrderDtoMa = new B2bOrderDtoMa();
			b2bOrderDtoMa.UpdateMine(orderDto);
			PurchaserInfo purchaserInfo = b2bOrderDtoMa.getPurchaser();
			SupplierInfo supplierInfo = b2bOrderDtoMa.getSupplier();
			AddressInfo addressInfo = b2bOrderDtoMa.getAddress();
			this.orderNumber = orderDto.getOrderNumber() == null ? "" : orderDto.getOrderNumber();
			this.orderClassify = orderDto.getOrderClassify() == null ? -1 : orderDto.getOrderClassify();
			//预约单类型当正常订单处理
			if(this.orderClassify == 5){
				this.orderClassify = 1;
			}
			this.purchaserName = purchaserInfo.getPurchaserName() == null ? "" : purchaserInfo.getPurchaserName();
			this.purchaserId = orderDto.getPurchaserPk() == null ? "" : orderDto.getPurchaserPk();
			this.orderAmount = orderDto.getOrderAmount() == null ? 0d : orderDto.getOrderAmount();
			this.actualAmount = orderDto.getActualAmount() == null ? 0d : orderDto.getActualAmount();
			this.orderTime = orderDto.getInsertTime() == null ? "": DateUtil.formatYearMonthDay(orderDto.getInsertTime());
			this.meno = orderDto.getMeno() == null ? "" : orderDto.getMeno();
			this.supplierName = supplierInfo.getSupplierName() == null ? "" : supplierInfo.getSupplierName();
			this.logisticsModelId = orderDto.getLogisticsModelPk() == null ? "" : orderDto.getLogisticsModelPk();
			this.organizationCode = purchaserInfo.getOrganizationCode() == null ? "" : purchaserInfo.getOrganizationCode();
			this.employeeName = orderDto.getEmployeeName() == null ? "" : orderDto.getEmployeeName();
			this.employeeNumber = orderDto.getEmployeeNumber() == null ? "" : orderDto.getEmployeeNumber();
			
			if (null!=addressInfo) {
				this.contacts = addressInfo.getContacts() == null ? "" : addressInfo.getContacts();
				this.contactsTel = addressInfo.getContactsTel() == null ? "" : addressInfo.getContactsTel();
				this.address = addressInfo.getAddress() == null ? "" : addressInfo.getAddress();
				this.signingCompany = addressInfo.getSigningCompany() == null ? "" : addressInfo.getSigningCompany();
				this.purchaseType = orderDto.getPurchaseType() == null ? -1 : orderDto.getPurchaseType();
				if (addressInfo.getProvinceName() != null && !"".equals(addressInfo.getProvinceName())) {
					Set<String> wordSet = new HashSet<String>();
					wordSet.add("省");
					wordSet.add("市");
					KeyWordUtil keyWordUtil = new KeyWordUtil(wordSet);
					String text = addressInfo.getProvinceName();
					int beginIndex = (text.length()) - 1;
					int wordLength = keyWordUtil.checkWord(text, beginIndex);
					if (wordLength > 0) {
						this.provinceName = text.substring(0, (text.length()) - 1);
					} else {
						this.provinceName = text;
					}
				}
				this.cityName = addressInfo.getCityName() == null ? "" : addressInfo.getCityName();
				this.areaName = addressInfo.getAreaName() == null ? "" : addressInfo.getAreaName();
				this.townName = addressInfo.getTownName() == null ? "" : addressInfo.getTownName();
			}else {
				this.contacts = "";
				this.contactsTel = "";
				this.address = "";
				this.signingCompany = "";
				this.purchaseType = orderDto.getPurchaseType() == null ? -1 : orderDto.getPurchaseType();
				this.provinceName ="";
				this.cityName = "";
				this.areaName ="";
				this.townName ="";
			}
			
			if (null!=orderDto.getOrderClassify() && orderDto.getOrderClassify()==2) {
				this.bindProductId = "";
				this.contractNo = orderDto.getOtherNumber() == null ?"":orderDto.getOtherNumber();
			}
			if (null!=orderDto.getOrderClassify() && orderDto.getOrderClassify()==4) {
				this.bindProductId = orderDto.getOtherNumber() == null ?"":orderDto.getOtherNumber();
				this.contractNo = "";
			}
			this.deliverDetails = orderDto.getDeliverDetails() == null ?"":orderDto.getDeliverDetails();
			if (orderGoodsList != null && orderGoodsList.size() > 0) {
				items = new ArrayList<OrderGoodsSync>();
				for (B2bOrderGoodsDtoEx orderGoodsDto : orderGoodsList) {
					B2bOrderGoodsDtoMa orderGoodsDtoMa = new B2bOrderGoodsDtoMa();
					orderGoodsDtoMa.UpdateMine(orderGoodsDto);
					CfGoods cfGoods = orderGoodsDtoMa.getCfGoods();
					OrderGoodsSync orderGoodsSync = new OrderGoodsSync();
					//新凤鸣单独处理子订单号
					if(b2bTokenDto.getAccType()==2){
						orderGoodsSync.setChildOrderNumber(this.subChildOrderString(orderGoodsDto.getChildOrderNumber()));
					}else{
						orderGoodsSync.setChildOrderNumber(
								orderGoodsDto.getChildOrderNumber() == null ? "" : orderGoodsDto.getChildOrderNumber());
					}
					if (null!=cfGoods) {
						orderGoodsSync.setBatchNumber(cfGoods.getBatchNumber() == null ? "" : cfGoods.getBatchNumber());
						orderGoodsSync.setGradeName(cfGoods.getGradeName() == null ? "" : cfGoods.getGradeName());
						orderGoodsSync.setDistinctNumber(cfGoods.getDistinctNumber() == null ? "" : cfGoods.getDistinctNumber());
						orderGoodsSync.setPackNumber(cfGoods.getPackNumber() == null ? "" : cfGoods.getPackNumber());
						orderGoodsSync.setLoadFee(cfGoods.getLoadFee() == null ? 0d : cfGoods.getLoadFee());
						orderGoodsSync.setPackageFee(cfGoods.getPackageFee() == null ? 0d : cfGoods.getPackageFee());
						orderGoodsSync.setAdminFee(cfGoods.getAdminFee() == null ? 0d : cfGoods.getAdminFee());
						orderGoodsSync.setWarehouseNumber(cfGoods.getWarehouseNumber()== null ?"":cfGoods.getWarehouseNumber());
						orderGoodsSync.setWarehouseType(cfGoods.getWarehouseType()== null ?"":cfGoods.getWarehouseType());
					}else{
						orderGoodsSync.setBatchNumber("");
						orderGoodsSync.setGradeName("");
						orderGoodsSync.setDistinctNumber("");
						orderGoodsSync.setPackNumber("");
						orderGoodsSync.setLoadFee(0d);
						orderGoodsSync.setPackageFee(0d);
						orderGoodsSync.setAdminFee(0d);
						orderGoodsSync.setWarehouseNumber("");
						orderGoodsSync.setWarehouseType("");
					}
					orderGoodsSync.setPurchQuantity(orderGoodsDto.getWeight() == null ? 0d : orderGoodsDto.getWeight());
					orderGoodsSync.setPurchWeight(orderGoodsDto.getBoxes() == null ? 0 : orderGoodsDto.getBoxes());
					orderGoodsSync.setAfterBoxes(orderGoodsDto.getAfterBoxes() == null ? 0 : orderGoodsDto.getAfterBoxes());
					orderGoodsSync.setAfterWeight(orderGoodsDto.getAfterWeight() == null ? 0d : orderGoodsDto.getAfterWeight());
					orderGoodsSync.setOriginalPrice(orderGoodsDto.getOriginalPrice() == null ? 0d : orderGoodsDto.getOriginalPrice());
					orderGoodsSync.setPresentPrice(orderGoodsDto.getPresentPrice() == null ? 0d : orderGoodsDto.getPresentPrice());
					orderGoodsSync.setOriginalFreight(orderGoodsDto.getOriginalFreight() == null ? 0d : orderGoodsDto.getOriginalFreight());
					orderGoodsSync.setPresentFreight(orderGoodsDto.getPresentFreight() == null ? 0d : orderGoodsDto.getPresentFreight());
					orderGoodsSync.setLogisticsPk(orderGoodsDto.getLogisticsPk() == null ?"":orderGoodsDto.getLogisticsPk());
					orderGoodsSync.setLogisticsStepInfoPk(orderGoodsDto.getLogisticsStepInfoPk() == null ?"":orderGoodsDto.getLogisticsStepInfoPk());
					orderGoodsSync.setOriginalTotalFreight(orderGoodsDto.getOriginalTotalFreight() == null ?0d:orderGoodsDto.getOriginalTotalFreight());
					orderGoodsSync.setPresentTotalFreight(orderGoodsDto.getPresentTotalFreight() == null ?0d:orderGoodsDto.getPresentTotalFreight());
					
					items.add(orderGoodsSync);
				}
			}
		}
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Integer getOrderClassify() {
		return orderClassify;
	}

	public void setOrderClassify(Integer orderClassify) {
		this.orderClassify = orderClassify;
	}

	public String getPurchaserName() {
		return purchaserName;
	}

	public void setPurchaserName(String purchaserName) {
		this.purchaserName = purchaserName;
	}

	public String getPurchaserId() {
		return purchaserId;
	}

	public void setPurchaserId(String purchaserId) {
		this.purchaserId = purchaserId;
	}

	public Double getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Double orderAmount) {
		this.orderAmount = orderAmount;
	}

	public Double getActualAmount() {
		return actualAmount;
	}

	public void setActualAmount(Double actualAmount) {
		this.actualAmount = actualAmount;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getMeno() {
		return meno;
	}

	public void setMeno(String meno) {
		this.meno = meno;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getLogisticsModelId() {
		return logisticsModelId;
	}

	public void setLogisticsModelId(String logisticsModelId) {
		this.logisticsModelId = logisticsModelId;
	}

	public String getOrganizationCode() {
		return organizationCode;
	}

	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getContactsTel() {
		return contactsTel;
	}

	public void setContactsTel(String contactsTel) {
		this.contactsTel = contactsTel;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSigningCompany() {
		return signingCompany;
	}

	public void setSigningCompany(String signingCompany) {
		this.signingCompany = signingCompany;
	}

	public Integer getPurchaseType() {
		return purchaseType;
	}

	public void setPurchaseType(Integer purchaseType) {
		this.purchaseType = purchaseType;
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

	public List<OrderGoodsSync> getItems() {
		return items;
	}

	public void setItems(List<OrderGoodsSync> items) {
		this.items = items;
	}

	public String getBindProductId() {
		return bindProductId;
	}

	public void setBindProductId(String bindProductId) {
		this.bindProductId = bindProductId;
	}
	
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getDeliverDetails() {
		return deliverDetails;
	}

	public void setDeliverDetails(String deliverDetails) {
		this.deliverDetails = deliverDetails;
	}

	private String subChildOrderString(String str){
		if(null == str){
			str = "";
		}
		if(!"".equals(str) && str.length()>3){
			Integer intStr = Integer.parseInt(str.substring(str.length()-3, str.length()));
			str = intStr+"";
		}
		return str;
	}
}
