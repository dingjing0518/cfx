package cn.cf.entity;

public class B2bEconomicsImprovingdataEntity {

	private String pk;
	private String econCustmerPk;
	private String companyPk;
	private Integer customerType;
	private String establishingYear;
	private Integer establishingTime = 0;
	private Double establishingTimeScore = 0d;
	private Double lastTaxSales = 0d;
	private Double taxSalesScore = 0d;
	private Double releLastTaxSales = 0d;
	private Double amountGuara = 0d;
	private Double releAmountGuara = 0d;
	private Double guaraTaxSalesPer = 0d;
	private Double guaraTaxSalesPerScore = 0d;
	private Double releGuaraTaxSalesPer = 0d;
	private Double releGuaraTaxSalesPerScore = 0d;
	private Double finanAmount = 0d;
	private Double finanAmountScore = 0d;
	private Double releFinanAmount = 0d;
	private Double finanAmountTaxSales = 0d;
	private Double finanAmountTaxSalesScore = 0d;
	private Double releFinanAmountTaxSales = 0d;
	private Double releFinanAmountTaxSalesScore = 0d;
	private Integer finanInstitution;
	private Double finanInstitutionScore = 0d;
	private String deviceStatus;
	private Double deviceStatusScore = 0d;
	private String otherDeviceStatus;
	private Double transAmountOnline = 0d;
	private Double transAmountOnlineScore = 0d;
	private String rawPurchaseAmount;
	private Double rawPurchaseAmountScore = 0d;
	private Double rawPurchaseIncrePer = 0d;
	private Double rawPurchaseIncrePerScore = 0d;
	private Double transOnlineRawPurchPer = 0d;
	private Double transOnlineRawPurchPerScore = 0d;
	private Integer useMonthsNum = 0;
	private Double useMonthsNumScore = 0d;
	private Integer econUseMonthsNum = 0;
	private Double econUseMonthsNumScore = 0d;
	private Double econUseMonthsAmount = 0d;
	private Double econUseMonthsAmountScore = 0d;
	private Integer econOverdueNum = 0;
	private Double econOverdueNumScore = 0d;
	private Double bankAnnualizAmount = 0d;
	private Double annualizTaxSalesPer = 0d;
	private Double annualizTaxSalesPerScore = 0d;
	private Double annualizTaxAmount = 0d;
	private Double annualizTaxSalesAmount = 0d;
	private String annualizTaxSalesTime;
	private Double annualizTaxAmountScore = 0d;
	private String rawBrandPk;
	private String rawBrand;
	private Double rawBrandScore = 0d;
	private Double rawAnnualizAmount = 0d;
	private Double rawAnnualizSalesAmount = 0d;
	private String rawAnnualizSalesTime;
	private Double rawAnnualizAmountScore = 0d;
	private Double workshopOwnSquare = 0d;
	private Double workshopRentSquare = 0d;
	private Double workshopAllSquare = 0d;
	private Double workshopSquareScore = 0d;
	private Integer flowOfProduction;
	private Double flowOfProductionScore = 0d;
	private Integer managementModel;
	private Double managementModelScore = 0d;
	private Integer businessShopNum = 0;
	private Double businessShopNumScore = 0d;
	private Integer shareChangeNum = 0;
	private Double shareChangeNumScore = 0d;
	private Integer enforcedNum = 0;
	private Double enforcedNumScore = 0d;
	private Integer zxEnforcedNum = 0;
	private Double zxEnforcedNumScore = 0d;
	private Integer ecomCooperateTime = 0;
	private Double ecomCooperateTimeScore = 0d;
	private Integer supplierCooperateTime = 0;
	private Double supplierCooperateTimeScore = 0d;
	private Double purcherVariety = 0d;
	private Double purcherVarietyScore = 0d;
	private Integer manageStable = 0;
	private Double manageStableScore = 0d;
	private String consumPerMonth;
	private Double consumAddup = 0d;
	private Double averageConsum = 0d;
	private Double consumStable = 0d;
	private Double consumStableScore = 0d;
	private Double industryAverageConsum = 0d;
	private Double averageIndustryConsumPer = 0d;
	private Double averageIndustryConsumPerScore = 0d;
	private Integer upstreamCustomerNum = 0;
	private Integer upstreamCustomerBaseNum = 0;
	private Double upstreamCustomerStable = 0d;
	private Double upstreamCustStableScore = 0d;
	private Integer downstreamCustomerNum = 0;
	private Integer downstreamCustomerBaseNum = 0;
	private Double downstreamCustomerStable = 0d;
	private Double downstreamCustStableScore = 0d;
	private Integer vipCustPurchaserAddUp = 0;
	private Integer inputTaxAmount = 0;
	private Double upstreamVipCustStable = 0d;
	private Double upstreamVipCustStableScore = 0d;
	private Double vipCustomerSales = 0d;
	private Double downstreamCustSalesStable = 0d;
	private Double downstreamCustSalesStableScore = 0d;
	private Integer taxLevel;
	private Double taxLevelScore = 0d;
	private Double platformSales = 0d;
	private Double lineOfCredit = 0d;
	private Double platformProfitPer = 0d;
	private Double allScore = 0d;
	private String insertTime;
	private String updateTime;

	private String companyName;
	private String contactsTel;
	private String contacts;
	private String flowOfProductionName;
	private String managementModelName;
	private String purcherVarietyName;
	private String taxLevelName;

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public String getEconCustmerPk() {
		return econCustmerPk;
	}

	public void setEconCustmerPk(String econCustmerPk) {
		this.econCustmerPk = econCustmerPk;
	}

	public String getCompanyPk() {
		return companyPk;
	}

	public void setCompanyPk(String companyPk) {
		this.companyPk = companyPk;
	}

	public Integer getCustomerType() {
		return customerType;
	}

	public void setCustomerType(Integer customerType) {
		this.customerType = customerType;
	}

	public String getEstablishingYear() {
		return establishingYear;
	}

	public void setEstablishingYear(String establishingYear) {
		this.establishingYear = establishingYear;
	}

	public Integer getEstablishingTime() {
		return establishingTime;
	}

	public void setEstablishingTime(Integer establishingTime) {
		this.establishingTime = establishingTime;
	}

	public Double getEstablishingTimeScore() {
		return establishingTimeScore;
	}

	public void setEstablishingTimeScore(Double establishingTimeScore) {
		this.establishingTimeScore = establishingTimeScore;
	}

	public Double getLastTaxSales() {
		return lastTaxSales;
	}

	public void setLastTaxSales(Double lastTaxSales) {
		this.lastTaxSales = lastTaxSales;
	}

	public Double getTaxSalesScore() {
		return taxSalesScore;
	}

	public void setTaxSalesScore(Double taxSalesScore) {
		this.taxSalesScore = taxSalesScore;
	}

	public Double getReleLastTaxSales() {
		return releLastTaxSales;
	}

	public void setReleLastTaxSales(Double releLastTaxSales) {
		this.releLastTaxSales = releLastTaxSales;
	}

	public Double getAmountGuara() {
		return amountGuara;
	}

	public void setAmountGuara(Double amountGuara) {
		this.amountGuara = amountGuara;
	}

	public Double getReleAmountGuara() {
		return releAmountGuara;
	}

	public void setReleAmountGuara(Double releAmountGuara) {
		this.releAmountGuara = releAmountGuara;
	}

	public Double getGuaraTaxSalesPer() {
		return guaraTaxSalesPer;
	}

	public void setGuaraTaxSalesPer(Double guaraTaxSalesPer) {
		this.guaraTaxSalesPer = guaraTaxSalesPer;
	}

	public Double getGuaraTaxSalesPerScore() {
		return guaraTaxSalesPerScore;
	}

	public void setGuaraTaxSalesPerScore(Double guaraTaxSalesPerScore) {
		this.guaraTaxSalesPerScore = guaraTaxSalesPerScore;
	}

	public Double getReleGuaraTaxSalesPer() {
		return releGuaraTaxSalesPer;
	}

	public void setReleGuaraTaxSalesPer(Double releGuaraTaxSalesPer) {
		this.releGuaraTaxSalesPer = releGuaraTaxSalesPer;
	}

	public Double getReleGuaraTaxSalesPerScore() {
		return releGuaraTaxSalesPerScore;
	}

	public void setReleGuaraTaxSalesPerScore(Double releGuaraTaxSalesPerScore) {
		this.releGuaraTaxSalesPerScore = releGuaraTaxSalesPerScore;
	}

	public Double getFinanAmount() {
		return finanAmount;
	}

	public void setFinanAmount(Double finanAmount) {
		this.finanAmount = finanAmount;
	}

	public Double getFinanAmountScore() {
		return finanAmountScore;
	}

	public void setFinanAmountScore(Double finanAmountScore) {
		this.finanAmountScore = finanAmountScore;
	}

	public Double getReleFinanAmount() {
		return releFinanAmount;
	}

	public void setReleFinanAmount(Double releFinanAmount) {
		this.releFinanAmount = releFinanAmount;
	}

	public Double getFinanAmountTaxSales() {
		return finanAmountTaxSales;
	}

	public void setFinanAmountTaxSales(Double finanAmountTaxSales) {
		this.finanAmountTaxSales = finanAmountTaxSales;
	}

	public Double getFinanAmountTaxSalesScore() {
		return finanAmountTaxSalesScore;
	}

	public void setFinanAmountTaxSalesScore(Double finanAmountTaxSalesScore) {
		this.finanAmountTaxSalesScore = finanAmountTaxSalesScore;
	}

	public Double getReleFinanAmountTaxSales() {
		return releFinanAmountTaxSales;
	}

	public void setReleFinanAmountTaxSales(Double releFinanAmountTaxSales) {
		this.releFinanAmountTaxSales = releFinanAmountTaxSales;
	}

	public Double getReleFinanAmountTaxSalesScore() {
		return releFinanAmountTaxSalesScore;
	}

	public void setReleFinanAmountTaxSalesScore(Double releFinanAmountTaxSalesScore) {
		this.releFinanAmountTaxSalesScore = releFinanAmountTaxSalesScore;
	}

	public Integer getFinanInstitution() {
		return finanInstitution;
	}

	public void setFinanInstitution(Integer finanInstitution) {
		this.finanInstitution = finanInstitution;
	}

	public Double getFinanInstitutionScore() {
		return finanInstitutionScore;
	}

	public void setFinanInstitutionScore(Double finanInstitutionScore) {
		this.finanInstitutionScore = finanInstitutionScore;
	}

	public String getDeviceStatus() {
		return deviceStatus;
	}

	public void setDeviceStatus(String deviceStatus) {
		this.deviceStatus = deviceStatus;
	}

	public Double getDeviceStatusScore() {
		return deviceStatusScore;
	}

	public void setDeviceStatusScore(Double deviceStatusScore) {
		this.deviceStatusScore = deviceStatusScore;
	}

	public String getOtherDeviceStatus() {
		return otherDeviceStatus;
	}

	public void setOtherDeviceStatus(String otherDeviceStatus) {
		this.otherDeviceStatus = otherDeviceStatus;
	}

	public Double getTransAmountOnline() {
		return transAmountOnline;
	}

	public void setTransAmountOnline(Double transAmountOnline) {
		this.transAmountOnline = transAmountOnline;
	}

	public Double getTransAmountOnlineScore() {
		return transAmountOnlineScore;
	}

	public void setTransAmountOnlineScore(Double transAmountOnlineScore) {
		this.transAmountOnlineScore = transAmountOnlineScore;
	}

	public String getRawPurchaseAmount() {
		return rawPurchaseAmount;
	}

	public void setRawPurchaseAmount(String rawPurchaseAmount) {
		this.rawPurchaseAmount = rawPurchaseAmount;
	}

	public Double getRawPurchaseAmountScore() {
		return rawPurchaseAmountScore;
	}

	public void setRawPurchaseAmountScore(Double rawPurchaseAmountScore) {
		this.rawPurchaseAmountScore = rawPurchaseAmountScore;
	}

	public Double getRawPurchaseIncrePer() {
		return rawPurchaseIncrePer;
	}

	public void setRawPurchaseIncrePer(Double rawPurchaseIncrePer) {
		this.rawPurchaseIncrePer = rawPurchaseIncrePer;
	}

	public Double getRawPurchaseIncrePerScore() {
		return rawPurchaseIncrePerScore;
	}

	public void setRawPurchaseIncrePerScore(Double rawPurchaseIncrePerScore) {
		this.rawPurchaseIncrePerScore = rawPurchaseIncrePerScore;
	}

	public Double getTransOnlineRawPurchPer() {
		return transOnlineRawPurchPer;
	}

	public void setTransOnlineRawPurchPer(Double transOnlineRawPurchPer) {
		this.transOnlineRawPurchPer = transOnlineRawPurchPer;
	}

	public Double getTransOnlineRawPurchPerScore() {
		return transOnlineRawPurchPerScore;
	}

	public void setTransOnlineRawPurchPerScore(Double transOnlineRawPurchPerScore) {
		this.transOnlineRawPurchPerScore = transOnlineRawPurchPerScore;
	}

	public Integer getUseMonthsNum() {
		return useMonthsNum;
	}

	public void setUseMonthsNum(Integer useMonthsNum) {
		this.useMonthsNum = useMonthsNum;
	}

	public Double getUseMonthsNumScore() {
		return useMonthsNumScore;
	}

	public void setUseMonthsNumScore(Double useMonthsNumScore) {
		this.useMonthsNumScore = useMonthsNumScore;
	}

	public Integer getEconUseMonthsNum() {
		return econUseMonthsNum;
	}

	public void setEconUseMonthsNum(Integer econUseMonthsNum) {
		this.econUseMonthsNum = econUseMonthsNum;
	}

	public Double getEconUseMonthsNumScore() {
		return econUseMonthsNumScore;
	}

	public void setEconUseMonthsNumScore(Double econUseMonthsNumScore) {
		this.econUseMonthsNumScore = econUseMonthsNumScore;
	}

	public Double getEconUseMonthsAmount() {
		return econUseMonthsAmount;
	}

	public void setEconUseMonthsAmount(Double econUseMonthsAmount) {
		this.econUseMonthsAmount = econUseMonthsAmount;
	}

	public Double getEconUseMonthsAmountScore() {
		return econUseMonthsAmountScore;
	}

	public void setEconUseMonthsAmountScore(Double econUseMonthsAmountScore) {
		this.econUseMonthsAmountScore = econUseMonthsAmountScore;
	}

	public Integer getEconOverdueNum() {
		return econOverdueNum;
	}

	public void setEconOverdueNum(Integer econOverdueNum) {
		this.econOverdueNum = econOverdueNum;
	}

	public Double getEconOverdueNumScore() {
		return econOverdueNumScore;
	}

	public void setEconOverdueNumScore(Double econOverdueNumScore) {
		this.econOverdueNumScore = econOverdueNumScore;
	}

	public Double getBankAnnualizAmount() {
		return bankAnnualizAmount;
	}

	public void setBankAnnualizAmount(Double bankAnnualizAmount) {
		this.bankAnnualizAmount = bankAnnualizAmount;
	}

	public Double getAnnualizTaxSalesPer() {
		return annualizTaxSalesPer;
	}

	public void setAnnualizTaxSalesPer(Double annualizTaxSalesPer) {
		this.annualizTaxSalesPer = annualizTaxSalesPer;
	}

	public Double getAnnualizTaxSalesPerScore() {
		return annualizTaxSalesPerScore;
	}

	public void setAnnualizTaxSalesPerScore(Double annualizTaxSalesPerScore) {
		this.annualizTaxSalesPerScore = annualizTaxSalesPerScore;
	}

	public Double getAnnualizTaxAmount() {
		return annualizTaxAmount;
	}

	public void setAnnualizTaxAmount(Double annualizTaxAmount) {
		this.annualizTaxAmount = annualizTaxAmount;
	}

	public Double getAnnualizTaxSalesAmount() {
		return annualizTaxSalesAmount;
	}

	public void setAnnualizTaxSalesAmount(Double annualizTaxSalesAmount) {
		this.annualizTaxSalesAmount = annualizTaxSalesAmount;
	}

	public String getAnnualizTaxSalesTime() {
		return annualizTaxSalesTime;
	}

	public void setAnnualizTaxSalesTime(String annualizTaxSalesTime) {
		this.annualizTaxSalesTime = annualizTaxSalesTime;
	}

	public Double getAnnualizTaxAmountScore() {
		return annualizTaxAmountScore;
	}

	public void setAnnualizTaxAmountScore(Double annualizTaxAmountScore) {
		this.annualizTaxAmountScore = annualizTaxAmountScore;
	}

	public String getRawBrandPk() {
		return rawBrandPk;
	}

	public void setRawBrandPk(String rawBrandPk) {
		this.rawBrandPk = rawBrandPk;
	}

	public String getRawBrand() {
		return rawBrand;
	}

	public void setRawBrand(String rawBrand) {
		this.rawBrand = rawBrand;
	}

	public Double getRawBrandScore() {
		return rawBrandScore;
	}

	public void setRawBrandScore(Double rawBrandScore) {
		this.rawBrandScore = rawBrandScore;
	}

	public Double getRawAnnualizAmount() {
		return rawAnnualizAmount;
	}

	public void setRawAnnualizAmount(Double rawAnnualizAmount) {
		this.rawAnnualizAmount = rawAnnualizAmount;
	}

	public Double getRawAnnualizSalesAmount() {
		return rawAnnualizSalesAmount;
	}

	public void setRawAnnualizSalesAmount(Double rawAnnualizSalesAmount) {
		this.rawAnnualizSalesAmount = rawAnnualizSalesAmount;
	}

	public String getRawAnnualizSalesTime() {
		return rawAnnualizSalesTime;
	}

	public void setRawAnnualizSalesTime(String rawAnnualizSalesTime) {
		this.rawAnnualizSalesTime = rawAnnualizSalesTime;
	}

	public Double getRawAnnualizAmountScore() {
		return rawAnnualizAmountScore;
	}

	public void setRawAnnualizAmountScore(Double rawAnnualizAmountScore) {
		this.rawAnnualizAmountScore = rawAnnualizAmountScore;
	}

	public Double getWorkshopOwnSquare() {
		return workshopOwnSquare;
	}

	public void setWorkshopOwnSquare(Double workshopOwnSquare) {
		this.workshopOwnSquare = workshopOwnSquare;
	}

	public Double getWorkshopRentSquare() {
		return workshopRentSquare;
	}

	public void setWorkshopRentSquare(Double workshopRentSquare) {
		this.workshopRentSquare = workshopRentSquare;
	}

	public Double getWorkshopAllSquare() {
		return workshopAllSquare;
	}

	public void setWorkshopAllSquare(Double workshopAllSquare) {
		this.workshopAllSquare = workshopAllSquare;
	}

	public Double getWorkshopSquareScore() {
		return workshopSquareScore;
	}

	public void setWorkshopSquareScore(Double workshopSquareScore) {
		this.workshopSquareScore = workshopSquareScore;
	}

	public Integer getFlowOfProduction() {
		return flowOfProduction;
	}

	public void setFlowOfProduction(Integer flowOfProduction) {
		this.flowOfProduction = flowOfProduction;
	}

	public Double getFlowOfProductionScore() {
		return flowOfProductionScore;
	}

	public void setFlowOfProductionScore(Double flowOfProductionScore) {
		this.flowOfProductionScore = flowOfProductionScore;
	}

	public Integer getManagementModel() {
		return managementModel;
	}

	public void setManagementModel(Integer managementModel) {
		this.managementModel = managementModel;
	}

	public Double getManagementModelScore() {
		return managementModelScore;
	}

	public void setManagementModelScore(Double managementModelScore) {
		this.managementModelScore = managementModelScore;
	}

	public Integer getBusinessShopNum() {
		return businessShopNum;
	}

	public void setBusinessShopNum(Integer businessShopNum) {
		this.businessShopNum = businessShopNum;
	}

	public Double getBusinessShopNumScore() {
		return businessShopNumScore;
	}

	public void setBusinessShopNumScore(Double businessShopNumScore) {
		this.businessShopNumScore = businessShopNumScore;
	}

	public Integer getShareChangeNum() {
		return shareChangeNum;
	}

	public void setShareChangeNum(Integer shareChangeNum) {
		this.shareChangeNum = shareChangeNum;
	}

	public Double getShareChangeNumScore() {
		return shareChangeNumScore;
	}

	public void setShareChangeNumScore(Double shareChangeNumScore) {
		this.shareChangeNumScore = shareChangeNumScore;
	}

	public Integer getEnforcedNum() {
		return enforcedNum;
	}

	public void setEnforcedNum(Integer enforcedNum) {
		this.enforcedNum = enforcedNum;
	}

	public Double getEnforcedNumScore() {
		return enforcedNumScore;
	}

	public void setEnforcedNumScore(Double enforcedNumScore) {
		this.enforcedNumScore = enforcedNumScore;
	}

	public Integer getZxEnforcedNum() {
		return zxEnforcedNum;
	}

	public void setZxEnforcedNum(Integer zxEnforcedNum) {
		this.zxEnforcedNum = zxEnforcedNum;
	}

	public Double getZxEnforcedNumScore() {
		return zxEnforcedNumScore;
	}

	public void setZxEnforcedNumScore(Double zxEnforcedNumScore) {
		this.zxEnforcedNumScore = zxEnforcedNumScore;
	}

	public Integer getEcomCooperateTime() {
		return ecomCooperateTime;
	}

	public void setEcomCooperateTime(Integer ecomCooperateTime) {
		this.ecomCooperateTime = ecomCooperateTime;
	}

	public Double getEcomCooperateTimeScore() {
		return ecomCooperateTimeScore;
	}

	public void setEcomCooperateTimeScore(Double ecomCooperateTimeScore) {
		this.ecomCooperateTimeScore = ecomCooperateTimeScore;
	}

	public Integer getSupplierCooperateTime() {
		return supplierCooperateTime;
	}

	public void setSupplierCooperateTime(Integer supplierCooperateTime) {
		this.supplierCooperateTime = supplierCooperateTime;
	}

	public Double getSupplierCooperateTimeScore() {
		return supplierCooperateTimeScore;
	}

	public void setSupplierCooperateTimeScore(Double supplierCooperateTimeScore) {
		this.supplierCooperateTimeScore = supplierCooperateTimeScore;
	}

	public Double getPurcherVariety() {
		return purcherVariety;
	}

	public void setPurcherVariety(Double purcherVariety) {
		this.purcherVariety = purcherVariety;
	}

	public Double getPurcherVarietyScore() {
		return purcherVarietyScore;
	}

	public void setPurcherVarietyScore(Double purcherVarietyScore) {
		this.purcherVarietyScore = purcherVarietyScore;
	}

	public Integer getManageStable() {
		return manageStable;
	}

	public void setManageStable(Integer manageStable) {
		this.manageStable = manageStable;
	}

	public Double getManageStableScore() {
		return manageStableScore;
	}

	public void setManageStableScore(Double manageStableScore) {
		this.manageStableScore = manageStableScore;
	}

	public String getConsumPerMonth() {
		return consumPerMonth;
	}

	public void setConsumPerMonth(String consumPerMonth) {
		this.consumPerMonth = consumPerMonth;
	}

	public Double getConsumAddup() {
		return consumAddup;
	}

	public void setConsumAddup(Double consumAddup) {
		this.consumAddup = consumAddup;
	}

	public Double getAverageConsum() {
		return averageConsum;
	}

	public void setAverageConsum(Double averageConsum) {
		this.averageConsum = averageConsum;
	}

	public Double getConsumStable() {
		return consumStable;
	}

	public void setConsumStable(Double consumStable) {
		this.consumStable = consumStable;
	}

	public Double getConsumStableScore() {
		return consumStableScore;
	}

	public void setConsumStableScore(Double consumStableScore) {
		this.consumStableScore = consumStableScore;
	}

	public Double getIndustryAverageConsum() {
		return industryAverageConsum;
	}

	public void setIndustryAverageConsum(Double industryAverageConsum) {
		this.industryAverageConsum = industryAverageConsum;
	}

	public Double getAverageIndustryConsumPer() {
		return averageIndustryConsumPer;
	}

	public void setAverageIndustryConsumPer(Double averageIndustryConsumPer) {
		this.averageIndustryConsumPer = averageIndustryConsumPer;
	}

	public Double getAverageIndustryConsumPerScore() {
		return averageIndustryConsumPerScore;
	}

	public void setAverageIndustryConsumPerScore(Double averageIndustryConsumPerScore) {
		this.averageIndustryConsumPerScore = averageIndustryConsumPerScore;
	}

	public Integer getUpstreamCustomerNum() {
		return upstreamCustomerNum;
	}

	public void setUpstreamCustomerNum(Integer upstreamCustomerNum) {
		this.upstreamCustomerNum = upstreamCustomerNum;
	}

	public Integer getUpstreamCustomerBaseNum() {
		return upstreamCustomerBaseNum;
	}

	public void setUpstreamCustomerBaseNum(Integer upstreamCustomerBaseNum) {
		this.upstreamCustomerBaseNum = upstreamCustomerBaseNum;
	}

	public Double getUpstreamCustomerStable() {
		return upstreamCustomerStable;
	}

	public void setUpstreamCustomerStable(Double upstreamCustomerStable) {
		this.upstreamCustomerStable = upstreamCustomerStable;
	}

	public Double getUpstreamCustStableScore() {
		return upstreamCustStableScore;
	}

	public void setUpstreamCustStableScore(Double upstreamCustStableScore) {
		this.upstreamCustStableScore = upstreamCustStableScore;
	}

	public Integer getDownstreamCustomerNum() {
		return downstreamCustomerNum;
	}

	public void setDownstreamCustomerNum(Integer downstreamCustomerNum) {
		this.downstreamCustomerNum = downstreamCustomerNum;
	}

	public Integer getDownstreamCustomerBaseNum() {
		return downstreamCustomerBaseNum;
	}

	public void setDownstreamCustomerBaseNum(Integer downstreamCustomerBaseNum) {
		this.downstreamCustomerBaseNum = downstreamCustomerBaseNum;
	}

	public Double getDownstreamCustomerStable() {
		return downstreamCustomerStable;
	}

	public void setDownstreamCustomerStable(Double downstreamCustomerStable) {
		this.downstreamCustomerStable = downstreamCustomerStable;
	}

	public Double getDownstreamCustStableScore() {
		return downstreamCustStableScore;
	}

	public void setDownstreamCustStableScore(Double downstreamCustStableScore) {
		this.downstreamCustStableScore = downstreamCustStableScore;
	}

	public Integer getVipCustPurchaserAddUp() {
		return vipCustPurchaserAddUp;
	}

	public void setVipCustPurchaserAddUp(Integer vipCustPurchaserAddUp) {
		this.vipCustPurchaserAddUp = vipCustPurchaserAddUp;
	}

	public Integer getInputTaxAmount() {
		return inputTaxAmount;
	}

	public void setInputTaxAmount(Integer inputTaxAmount) {
		this.inputTaxAmount = inputTaxAmount;
	}

	public Double getUpstreamVipCustStable() {
		return upstreamVipCustStable;
	}

	public void setUpstreamVipCustStable(Double upstreamVipCustStable) {
		this.upstreamVipCustStable = upstreamVipCustStable;
	}

	public Double getUpstreamVipCustStableScore() {
		return upstreamVipCustStableScore;
	}

	public void setUpstreamVipCustStableScore(Double upstreamVipCustStableScore) {
		this.upstreamVipCustStableScore = upstreamVipCustStableScore;
	}

	public Double getVipCustomerSales() {
		return vipCustomerSales;
	}

	public void setVipCustomerSales(Double vipCustomerSales) {
		this.vipCustomerSales = vipCustomerSales;
	}

	public Double getDownstreamCustSalesStable() {
		return downstreamCustSalesStable;
	}

	public void setDownstreamCustSalesStable(Double downstreamCustSalesStable) {
		this.downstreamCustSalesStable = downstreamCustSalesStable;
	}

	public Double getDownstreamCustSalesStableScore() {
		return downstreamCustSalesStableScore;
	}

	public void setDownstreamCustSalesStableScore(Double downstreamCustSalesStableScore) {
		this.downstreamCustSalesStableScore = downstreamCustSalesStableScore;
	}

	public Integer getTaxLevel() {
		return taxLevel;
	}

	public void setTaxLevel(Integer taxLevel) {
		this.taxLevel = taxLevel;
	}

	public Double getTaxLevelScore() {
		return taxLevelScore;
	}

	public void setTaxLevelScore(Double taxLevelScore) {
		this.taxLevelScore = taxLevelScore;
	}

	public Double getPlatformSales() {
		return platformSales;
	}

	public void setPlatformSales(Double platformSales) {
		this.platformSales = platformSales;
	}

	public Double getLineOfCredit() {
		return lineOfCredit;
	}

	public void setLineOfCredit(Double lineOfCredit) {
		this.lineOfCredit = lineOfCredit;
	}

	public Double getPlatformProfitPer() {
		return platformProfitPer;
	}

	public void setPlatformProfitPer(Double platformProfitPer) {
		this.platformProfitPer = platformProfitPer;
	}

	public Double getAllScore() {
		return allScore;
	}

	public void setAllScore(Double allScore) {
		this.allScore = allScore;
	}

	public String getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getFlowOfProductionName() {
		return flowOfProductionName;
	}
	public void setFlowOfProductionName(String flowOfProductionName) {
		this.flowOfProductionName = flowOfProductionName;
	}
	public String getManagementModelName() {
		return managementModelName;
	}
	public void setManagementModelName(String managementModelName) {
		this.managementModelName = managementModelName;
	}
	public String getPurcherVarietyName() {
		return purcherVarietyName;
	}
	public void setPurcherVarietyName(String purcherVarietyName) {
		this.purcherVarietyName = purcherVarietyName;
	}
	public String getTaxLevelName() {
		return taxLevelName;
	}
	public void setTaxLevelName(String taxLevelName) {
		this.taxLevelName = taxLevelName;
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
	
	
	
}
