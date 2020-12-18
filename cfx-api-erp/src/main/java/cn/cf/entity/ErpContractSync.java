package cn.cf.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.cf.common.KeyWordUtil;
import cn.cf.dto.B2bContractGoodsDto;
import cn.cf.util.ArithUtil;
import cn.cf.util.DateUtil;

public class ErpContractSync {
	private String contractNo;
	private String approvalstatus;
	private String contractSource;
	private String saleDepartment;
	private String saleCompany;
	private String startTime;
	private String endTime;
	private Integer days;
	private String provinceName;
	private String cityName;
	private String areaName;
	private String townName;
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
	private String toAddress;
	private String plantName;
	private String remark;
	private String  logisticsModelPk;
	private String carrier;
	private String organizationCode;
	
	
	public String getLogisticsModelPk() {
		return logisticsModelPk;
	}

	public void setLogisticsModelPk(String logisticsModelPk) {
		this.logisticsModelPk = logisticsModelPk;
	}
	
	
	public String getCarrier() {
		return carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}


	private List<ErpContractGoodsSync> items;

	public ErpContractSync() {

	}

	public ErpContractSync(B2bContractDtoEx dtoEx, List<B2bContractGoodsDto> list) {
		if (dtoEx != null) {
			B2bContractDtoMa b2bContractDtoMa = new B2bContractDtoMa();
			b2bContractDtoMa.UpdateMine(dtoEx);
			AddressInfo addressInfo = b2bContractDtoMa.getAddress();
			PurchaserInfo purchaserInfo = b2bContractDtoMa.getPurchaser();
			SupplierInfo supplierInfo = b2bContractDtoMa.getSupplier();
			
			this.contractNo = dtoEx.getContractNo()== null?"":dtoEx.getContractNo();
			//this.approvalstatus = "待付款";
			String contractSourceStr = "";
			if (dtoEx.getContractSource() != null) {
				if (dtoEx.getContractSource() == 1) {
					contractSourceStr = "B2B创建";
				}
				if (dtoEx.getContractSource() == 2) {
					contractSourceStr = "CRM同步";
				}
			}
			this.contractSource = contractSourceStr;
			this.saleDepartment = dtoEx.getSaleDepartment()== null?"":dtoEx.getSaleDepartment();
			this.saleCompany = supplierInfo.getSupplierName()==null?"":supplierInfo.getSupplierName();
			this.startTime = dtoEx.getStartTime() == null ? "" : DateUtil.formatYearMonthDay(dtoEx.getStartTime());
			this.endTime = dtoEx.getEndTime() == null ? "" : DateUtil.formatYearMonthDay(dtoEx.getEndTime());
			this.days = dtoEx.getDays()==null?0:dtoEx.getDays();
			if (null!=addressInfo) {
				if (addressInfo.getProvinceName() != null && !"".equals(addressInfo.getProvinceName())) {
					Set<String> wordSet = new HashSet<String>();
					wordSet.add("省");
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
				this.cityName = addressInfo.getCityName()==null?"":addressInfo.getCityName();
				this.areaName = addressInfo.getAreaName()==null?"":addressInfo.getAreaName();
				this.townName = addressInfo.getTownName()==null?"":addressInfo.getTownName();
				this.telephone = addressInfo.getContactsTel()==null?"":addressInfo.getContactsTel();
				this.toAddress = addressInfo.getAddress()==null?"":addressInfo.getAddress();
			}else {
				this.provinceName = "";
				this.cityName ="";
				this.areaName = "";
				this.townName = "";
				this.telephone = "";
				this.toAddress = "";
			}
		
			this.covenant = dtoEx.getCovenant()==null?"":dtoEx.getCovenant();
			this.supplementary = dtoEx.getSupplementary()==null?"":dtoEx.getSupplementary();
			this.priceType = dtoEx.getPriceType()==null?"":dtoEx.getPriceType();
			this.salesman = dtoEx.getSalesman()==null?"":dtoEx.getSalesman();
			this.salesmanNumber = dtoEx.getSalesmanNumber()==null?"":dtoEx.getSalesmanNumber();
			this.requireAccount = purchaserInfo.getInvoiceName()==null?"":purchaserInfo.getInvoiceName();
			this.contractRate = dtoEx.getContractRate()==null?"":dtoEx.getContractRate();
			this.contractCount = dtoEx.getContractCount()==null?0d:dtoEx.getContractCount();
			this.contractAmount = dtoEx.getTotalAmount()==null?0d:dtoEx.getTotalAmount();
			this.logisticsModel = dtoEx.getLogisticsModel()==null?"":dtoEx.getLogisticsModel();
			this.logisticsModelPk=dtoEx.getLogisticsModelPk()==null?"":dtoEx.getLogisticsModelPk();
			this.carrier = dtoEx.getCarrier() == null?"":dtoEx.getCarrier();
			this.organizationCode = purchaserInfo.getOrganizationCode() == null?"":purchaserInfo.getOrganizationCode();
			this.plantName = dtoEx.getPlantName()== null?"":dtoEx.getPlantName();
			if (list != null && list.size() > 0) {
				items = new ArrayList<ErpContractGoodsSync>();
				for (B2bContractGoodsDto contractGoods : list) {
					B2bContractGoodsDtoMa contractGoodsDtoMa = new B2bContractGoodsDtoMa();
					contractGoodsDtoMa.UpdateMine(contractGoods);
					CfGoods cfGoods = contractGoodsDtoMa.getCfGoods();
					ErpContractGoodsSync erpContractGoodsSync = new ErpContractGoodsSync();
					Double totalPrice = 0d;
					if (null!=cfGoods) {
						erpContractGoodsSync.setAdminFee(cfGoods.getAdminFee()==null?0d:cfGoods.getAdminFee());
						erpContractGoodsSync.setBatchNumber(cfGoods.getBatchNumber()==null?"":cfGoods.getBatchNumber());
						erpContractGoodsSync.setPackageNumber(cfGoods.getPackNumber()==null?"":cfGoods.getPackNumber());
						erpContractGoodsSync.setDistinctNumber(cfGoods.getDistinctNumber()==null?"":cfGoods.getDistinctNumber());
						erpContractGoodsSync.setLevel(cfGoods.getGradeName()==null?"":cfGoods.getGradeName());
						erpContractGoodsSync.setLoadFee(cfGoods.getLoadFee()==null?0d:cfGoods.getLoadFee());
						erpContractGoodsSync.setPackageFee(cfGoods.getPackageFee()==null?0d:cfGoods.getPackageFee());
						erpContractGoodsSync.setWarehouseNumber(cfGoods.getWarehouseNumber()==null?"":cfGoods.getWarehouseNumber());
						erpContractGoodsSync.setWarehouseType(cfGoods.getWarehouseType()==null?"":cfGoods.getWarehouseType());
						erpContractGoodsSync.setPlantName(cfGoods.getPlantName()==null?"":cfGoods.getPlantName());
						totalPrice =ArithUtil.mul(contractGoods.getContractPrice()+cfGoods.getLoadFee()+cfGoods.getAdminFee()+cfGoods.getPackageFee()+contractGoods.getFreight(),contractGoods.getWeight());
					}else {
						erpContractGoodsSync.setAdminFee(0d);
						erpContractGoodsSync.setBatchNumber("");
						erpContractGoodsSync.setPackageNumber("");
						erpContractGoodsSync.setDistinctNumber("");
						erpContractGoodsSync.setLevel("");
						erpContractGoodsSync.setLoadFee(0d);
						erpContractGoodsSync.setPackageFee(0d);
						erpContractGoodsSync.setWarehouseNumber("");
						erpContractGoodsSync.setWarehouseType("");
						erpContractGoodsSync.setPlantName("");
						totalPrice =ArithUtil.mul(contractGoods.getContractPrice()+contractGoods.getFreight(),contractGoods.getWeight());
					}
					erpContractGoodsSync.setBasicPrice(contractGoods.getBasicPrice()==null?0d:contractGoods.getBasicPrice());
					erpContractGoodsSync.setBoxes(contractGoods.getBoxes()==null?0:contractGoods.getBoxes());
					erpContractGoodsSync.setCarrier(contractGoods.getCarrier()==null?"":contractGoods.getCarrier());
					erpContractGoodsSync.setContractPrice(contractGoods.getContractPrice()==null?0d:contractGoods.getContractPrice());
					erpContractGoodsSync.setDetailNumber(contractGoods.getDetailNumber() == null ? "": String.valueOf(contractGoods.getDetailNumber()));
					erpContractGoodsSync.setDiscount(contractGoods.getDiscount()==null?0d:contractGoods.getDiscount());
					erpContractGoodsSync.setFreight(contractGoods.getFreight()==null?0d:contractGoods.getFreight());
					erpContractGoodsSync.setTotalPrice(totalPrice);
					erpContractGoodsSync.setWeight(contractGoods.getWeight()==null?0d:contractGoods.getWeight());
					erpContractGoodsSync.setLogisticsPk(contractGoods.getLogisticsPk()==null?"":contractGoods.getLogisticsPk());
					erpContractGoodsSync.setLogisticsStepInfoPk(contractGoods.getLogisticsStepInfoPk()==null?"":contractGoods.getLogisticsStepInfoPk());
					items.add(erpContractGoodsSync);
				}
			}
		}
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getApprovalstatus() {
		return approvalstatus;
	}

	public void setApprovalstatus(String approvalstatus) {
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

	public List<ErpContractGoodsSync> getItems() {
		return items;
	}

	public void setItems(List<ErpContractGoodsSync> items) {
		this.items = items;
	}

	public String getPlantName() {
		return plantName;
	}

	public void setPlantName(String plantName) {
		this.plantName = plantName;
	}

	public String getOrganizationCode() {
		return organizationCode;
	}

	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

}
