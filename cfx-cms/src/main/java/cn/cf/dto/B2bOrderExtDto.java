package cn.cf.dto;

import java.util.Date;
import java.util.List;

import cn.cf.entity.B2bPayVoucher;

public class B2bOrderExtDto extends B2bOrderDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7076191949636743651L;

	private java.lang.String insertStartTime;
	private java.lang.String insertEndTime;
	private java.lang.String paymentStartTime;
	private java.lang.String paymentEndTime;
	private String detailAddress;
	private java.lang.String purchaseTypeName;
	private List<B2bPayVoucher> voucherList;
	private List<B2bOrderGoodsExtDto> orderGoods;
	private String[] payUrl;
	private String loginPk;
	private String purPerson;
	private String supPerson;
	private String contractNumber;
	private String iouNumber;
	private Date loanStartTime;
	private Date loanEndTime;
	private String loanStartTimeBegin;
	private String loanStartTimeEnd;
	private String loanEndTimeBegin;
	private String loanEndTimeEnd;
	private Double principal;
	private String mobile;
	private Integer repaymentStatus;
	private String loans;
	private Integer boxes;
	private Integer disType;
	private Integer afterBoxes;
	private Double weight;
	private Double afterWeight;

	private String logisticsModelName;
	private String childOrderNumber;
	private String productName;
	private String varietiesName;
	private String seedvarietyName;
	private String specName;
	private String seriesName;
	private String batchNumber;
	private String gradeName;
	private Double originalPrice;
	private Double presentPrice;
	private Double presentTotalPrice;
	private Double presentTotalFreight;
	private String sourceName;
	private String plantName;
	private Integer isBargaining;
	private String supplierPhone;
	private String purchasePhone;
	private String orderStatusName;
	private String employeeName;
	private String employeeNumber;
	private String economicsGoodsPk;
	private String economicsGoodsName;
	private Integer economicsGoodsType;

	// 订单导出新增
	private Double allWeight;// 总重量
	private Double allPresentTotalPrice;// 商品实际总价
	private Double allPresentTotalFreight;// 运费实际总价

	private String accountPk;
	private String rolePk;

	//列权限显示控制字段
	private String purchaserNameCol;
	private String inviceNameCol;
	private String storeNameCol;
	private String purchEmplCol;
	private String storeEmplCol;
	private String addressCol;
	private String contactNameCol;

	private String supplierNameCol;
	private String plantNameCol;
	private String memberNameCol;
	private String contactTelCol;
	
	private String receivablesStartTime;
	private String receivablesEndTime;

	private String purchaserName;

	private String contactsTel;

	private String invoiceName;

	private String supplierName;

	private String contacts;

	private String goodsInfo;

    private String productType;

    private String materialName;
    
    private String  stampDutyName;//是否含印花税

	
	public String getReceivablesStartTime() {
		return receivablesStartTime;
	}

	public void setReceivablesStartTime(String receivablesStartTime) {
		this.receivablesStartTime = receivablesStartTime;
	}

	public String getReceivablesEndTime() {
		return receivablesEndTime;
	}

	public void setReceivablesEndTime(String receivablesEndTime) {
		this.receivablesEndTime = receivablesEndTime;
	}

	public String getAccountPk() {
        return accountPk;
    }

    public void setAccountPk(String accountPk) {
        this.accountPk = accountPk;
    }

    public Integer getEconomicsGoodsType() {
		return economicsGoodsType;
	}

	public void setEconomicsGoodsType(Integer economicsGoodsType) {
		this.economicsGoodsType = economicsGoodsType;
	}

	public String getOrderStatusName() {
		return orderStatusName;
	}

	public void setOrderStatusName(String orderStatusName) {
		this.orderStatusName = orderStatusName;
	}

	public String getLogisticsModelName() {
		return logisticsModelName;
	}

	public void setLogisticsModelName(String logisticsModelName) {
		this.logisticsModelName = logisticsModelName;
	}

	public String getChildOrderNumber() {
		return childOrderNumber;
	}

	public void setChildOrderNumber(String childOrderNumber) {
		this.childOrderNumber = childOrderNumber;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getVarietiesName() {
		return varietiesName;
	}

	public void setVarietiesName(String varietiesName) {
		this.varietiesName = varietiesName;
	}

	public String getSeedvarietyName() {
		return seedvarietyName;
	}

	public void setSeedvarietyName(String seedvarietyName) {
		this.seedvarietyName = seedvarietyName;
	}

	public String getSpecName() {
		return specName;
	}

	public void setSpecName(String specName) {
		this.specName = specName;
	}

	public String getSeriesName() {
		return seriesName;
	}

	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
	}

	public String getBatchNumber() {
		return batchNumber;
	}

	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
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

	public Double getPresentTotalPrice() {
		return presentTotalPrice;
	}

	public void setPresentTotalPrice(Double presentTotalPrice) {
		this.presentTotalPrice = presentTotalPrice;
	}

	public Double getPresentTotalFreight() {
		return presentTotalFreight;
	}

	public void setPresentTotalFreight(Double presentTotalFreight) {
		this.presentTotalFreight = presentTotalFreight;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getPlantName() {
		return plantName;
	}

	public void setPlantName(String plantName) {
		this.plantName = plantName;
	}

	public Integer getIsBargaining() {
		return isBargaining;
	}

	public void setIsBargaining(Integer isBargaining) {
		this.isBargaining = isBargaining;
	}

	public String getSupplierPhone() {
		return supplierPhone;
	}

	public void setSupplierPhone(String supplierPhone) {
		this.supplierPhone = supplierPhone;
	}

	public String getPurchasePhone() {
		return purchasePhone;
	}

	public void setPurchasePhone(String purchasePhone) {
		this.purchasePhone = purchasePhone;
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

	public Double getAllWeight() {
		return allWeight;
	}

	public void setAllWeight(Double allWeight) {
		this.allWeight = allWeight;
	}

	public Double getAllPresentTotalPrice() {
		return allPresentTotalPrice;
	}

	public void setAllPresentTotalPrice(Double allPresentTotalPrice) {
		this.allPresentTotalPrice = allPresentTotalPrice;
	}

	public Double getAllPresentTotalFreight() {
		return allPresentTotalFreight;
	}

	public void setAllPresentTotalFreight(Double allPresentTotalFreight) {
		this.allPresentTotalFreight = allPresentTotalFreight;
	}

	public java.lang.String getInsertStartTime() {
		return insertStartTime;
	}

	public void setInsertStartTime(java.lang.String insertStartTime) {
		this.insertStartTime = insertStartTime;
	}

	public java.lang.String getInsertEndTime() {
		return insertEndTime;
	}

	public void setInsertEndTime(java.lang.String insertEndTime) {
		this.insertEndTime = insertEndTime;
	}

	public java.lang.String getPaymentStartTime() {
		return paymentStartTime;
	}

	public void setPaymentStartTime(java.lang.String paymentStartTime) {
		this.paymentStartTime = paymentStartTime;
	}

	public java.lang.String getPaymentEndTime() {
		return paymentEndTime;
	}

	public void setPaymentEndTime(java.lang.String paymentEndTime) {
		this.paymentEndTime = paymentEndTime;
	}

	public java.lang.String getPurchaseTypeName() {
		return purchaseTypeName;
	}

	public void setPurchaseTypeName(java.lang.String purchaseTypeName) {
		this.purchaseTypeName = purchaseTypeName;
	}

	public List<B2bPayVoucher> getVoucherList() {
		return voucherList;
	}

	public void setVoucherList(List<B2bPayVoucher> voucherList) {
		this.voucherList = voucherList;
	}


	public List<B2bOrderGoodsExtDto> getOrderGoods() {
		return orderGoods;
	}

	public void setOrderGoods(List<B2bOrderGoodsExtDto> orderGoods) {
		this.orderGoods = orderGoods;
	}

	public String[] getPayUrl() {
		return payUrl;
	}

	public void setPayUrl(String[] payUrl) {
		this.payUrl = payUrl;
	}

	public String getDetailAddress() {
		return detailAddress;
	}

	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}

	public String getLoginPk() {
		return loginPk;
	}

	public void setLoginPk(String loginPk) {
		this.loginPk = loginPk;
	}

	public String getPurPerson() {
		return purPerson;
	}

	public void setPurPerson(String purPerson) {
		this.purPerson = purPerson;
	}

	public String getSupPerson() {
		return supPerson;
	}

	public void setSupPerson(String supPerson) {
		this.supPerson = supPerson;
	}

	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	public String getIouNumber() {
		return iouNumber;
	}

	public void setIouNumber(String iouNumber) {
		this.iouNumber = iouNumber;
	}

	public Date getLoanStartTime() {
		return loanStartTime;
	}

	public void setLoanStartTime(Date loanStartTime) {
		this.loanStartTime = loanStartTime;
	}

	public Date getLoanEndTime() {
		return loanEndTime;
	}

	public void setLoanEndTime(Date loanEndTime) {
		this.loanEndTime = loanEndTime;
	}

	public String getLoanStartTimeBegin() {
		return loanStartTimeBegin;
	}

	public void setLoanStartTimeBegin(String loanStartTimeBegin) {
		this.loanStartTimeBegin = loanStartTimeBegin;
	}

	public String getLoanStartTimeEnd() {
		return loanStartTimeEnd;
	}

	public void setLoanStartTimeEnd(String loanStartTimeEnd) {
		this.loanStartTimeEnd = loanStartTimeEnd;
	}

	public String getLoanEndTimeBegin() {
		return loanEndTimeBegin;
	}

	public void setLoanEndTimeBegin(String loanEndTimeBegin) {
		this.loanEndTimeBegin = loanEndTimeBegin;
	}

	public String getLoanEndTimeEnd() {
		return loanEndTimeEnd;
	}

	public void setLoanEndTimeEnd(String loanEndTimeEnd) {
		this.loanEndTimeEnd = loanEndTimeEnd;
	}

	public Double getPrincipal() {
		return principal;
	}

	public void setPrincipal(Double principal) {
		this.principal = principal;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getRepaymentStatus() {
		return repaymentStatus;
	}

	public void setRepaymentStatus(Integer repaymentStatus) {
		this.repaymentStatus = repaymentStatus;
	}

	public String getLoans() {
		return loans;
	}

	public void setLoans(String loans) {
		this.loans = loans;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getBoxes() {
		return boxes;
	}

	public void setBoxes(Integer boxes) {
		this.boxes = boxes;
	}

	public Integer getDisType() {
		return disType;
	}

	public void setDisType(Integer disType) {
		this.disType = disType;
	}

	public Integer getAfterBoxes() {
		return afterBoxes;
	}

	public void setAfterBoxes(Integer afterBoxes) {
		this.afterBoxes = afterBoxes;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Double getAfterWeight() {
		return afterWeight;
	}

	public void setAfterWeight(Double afterWeight) {
		this.afterWeight = afterWeight;
	}

	public String getEconomicsGoodsPk() {
		return economicsGoodsPk;
	}

	public void setEconomicsGoodsPk(String economicsGoodsPk) {
		this.economicsGoodsPk = economicsGoodsPk;
	}

	public String getEconomicsGoodsName() {
		return economicsGoodsName;
	}

	public void setEconomicsGoodsName(String economicsGoodsName) {
		this.economicsGoodsName = economicsGoodsName;
	}

	public String getPurchaserNameCol() {
		return purchaserNameCol;
	}

	public void setPurchaserNameCol(String purchaserNameCol) {
		this.purchaserNameCol = purchaserNameCol;
	}

	public String getInviceNameCol() {
		return inviceNameCol;
	}

	public void setInviceNameCol(String inviceNameCol) {
		this.inviceNameCol = inviceNameCol;
	}

	public String getStoreNameCol() {
		return storeNameCol;
	}

	public void setStoreNameCol(String storeNameCol) {
		this.storeNameCol = storeNameCol;
	}

	public String getPurchEmplCol() {
		return purchEmplCol;
	}

	public void setPurchEmplCol(String purchEmplCol) {
		this.purchEmplCol = purchEmplCol;
	}

	public String getStoreEmplCol() {
		return storeEmplCol;
	}

	public void setStoreEmplCol(String storeEmplCol) {
		this.storeEmplCol = storeEmplCol;
	}

	public String getAddressCol() {
		return addressCol;
	}

	public void setAddressCol(String addressCol) {
		this.addressCol = addressCol;
	}

	public String getContactNameCol() {
		return contactNameCol;
	}

	public void setContactNameCol(String contactNameCol) {
		this.contactNameCol = contactNameCol;
	}

	public String getSupplierNameCol() {
		return supplierNameCol;
	}

	public void setSupplierNameCol(String supplierNameCol) {
		this.supplierNameCol = supplierNameCol;
	}

	public String getPlantNameCol() {
		return plantNameCol;
	}

	public void setPlantNameCol(String plantNameCol) {
		this.plantNameCol = plantNameCol;
	}

	public String getMemberNameCol() {
		return memberNameCol;
	}

	public void setMemberNameCol(String memberNameCol) {
		this.memberNameCol = memberNameCol;
	}

	public String getContactTelCol() {
		return contactTelCol;
	}

	public void setContactTelCol(String contactTelCol) {
		this.contactTelCol = contactTelCol;
	}

	public String getPurchaserName() {
		return purchaserName;
	}

	public void setPurchaserName(String purchaserName) {
		this.purchaserName = purchaserName;
	}

	public String getContactsTel() {
		return contactsTel;
	}

	public void setContactsTel(String contactsTel) {
		this.contactsTel = contactsTel;
	}

	public String getInvoiceName() {
		return invoiceName;
	}

	public void setInvoiceName(String invoiceName) {
		this.invoiceName = invoiceName;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

    public String getGoodsInfo() {
        return goodsInfo;
    }

    public void setGoodsInfo(String goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

	public String getRolePk() {
		return rolePk;
	}

	public void setRolePk(String rolePk) {
		this.rolePk = rolePk;
	}

	public String getStampDutyName() {
		return stampDutyName;
	}

	public void setStampDutyName(String stampDutyName) {
		this.stampDutyName = stampDutyName;
	}
	
	
	
}
