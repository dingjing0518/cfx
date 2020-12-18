package cn.cf.entity;

import java.util.Date;
import java.util.List;

public class ContractSync {
	private String contractNo;
	private Integer approvalstatus;
	
	private String contractSource;
	private String saleDepartment;
	private String saleCompany;
	private String startTime;
	private String endTime;
	
	private Integer days;
	
	private String covenant;
	private String supplementary;
	private String priceType;
	private String salesman;
	private String salesmanNumber;
	private String requireAccount;
	private String contractRate;
	
	private Double contractCount;
	private Double contractAmount;
	
	private String logisticsModel;
	private String telephone;
	private String carrier;
	private String toAddress;
	private String remark;
	private String provinceName;
	private String cityName;
	private String areaName;
	private String townName;
	private String plantName;
	private String paymentPk;
	private String paymentName;
	private Integer paymentType;
	private Date paymentTime;

	private List<SubContractSync> items;

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public Integer getApprovalstatus() {
		return approvalstatus;
	}

	public void setApprovalstatus(Integer approvalstatus) {
		this.approvalstatus = approvalstatus;
	}

	public String getContractSource() {
		return contractSource;
	}

	public void setContractSource(String contractSource) {
		this.contractSource = contractSource;
	}

	public String getSaleDepartment() {
		return saleDepartment;
	}

	public void setSaleDepartment(String saleDepartment) {
		this.saleDepartment = saleDepartment;
	}

	public String getSaleCompany() {
		return saleCompany;
	}

	public void setSaleCompany(String saleCompany) {
		this.saleCompany = saleCompany;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public String getCovenant() {
		return covenant;
	}

	public void setCovenant(String covenant) {
		this.covenant = covenant;
	}

	public String getSupplementary() {
		return supplementary;
	}

	public void setSupplementary(String supplementary) {
		this.supplementary = supplementary;
	}

	public String getPriceType() {
		return priceType;
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}

	public String getSalesman() {
		return salesman;
	}

	public void setSalesman(String salesman) {
		this.salesman = salesman;
	}

	public String getSalesmanNumber() {
		return salesmanNumber;
	}

	public void setSalesmanNumber(String salesmanNumber) {
		this.salesmanNumber = salesmanNumber;
	}

	public String getRequireAccount() {
		return requireAccount;
	}

	public void setRequireAccount(String requireAccount) {
		this.requireAccount = requireAccount;
	}

	public String getContractRate() {
		return contractRate;
	}

	public void setContractRate(String contractRate) {
		this.contractRate = contractRate;
	}

	public Double getContractCount() {
		return contractCount;
	}

	public void setContractCount(Double contractCount) {
		this.contractCount = contractCount;
	}

	public Double getContractAmount() {
		return contractAmount;
	}

	public void setContractAmount(Double contractAmount) {
		this.contractAmount = contractAmount;
	}

	public String getLogisticsModel() {
		return logisticsModel;
	}

	public void setLogisticsModel(String logisticsModel) {
		this.logisticsModel = logisticsModel;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getCarrier() {
		return carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	public String getToAddress() {
		return toAddress;
	}

	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public List<SubContractSync> getItems() {
		return items;
	}

	public void setItems(List<SubContractSync> items) {
		this.items = items;
	}

	public String getPlantName() {
		return plantName;
	}

	public void setPlantName(String plantName) {
		this.plantName = plantName;
	}

	public String getPaymentPk() {
		return paymentPk;
	}

	public void setPaymentPk(String paymentPk) {
		this.paymentPk = paymentPk;
	}

	public String getPaymentName() {
		return paymentName;
	}

	public void setPaymentName(String paymentName) {
		this.paymentName = paymentName;
	}


	public Integer getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(Integer paymentType) {
		this.paymentType = paymentType;
	}

	public Date getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
	}
	
}
