package cn.cf.dto;

import java.util.List;

/**
 *
 * @author
 * @version 1.0
 * @since 1.0
 */
public class B2bEconomicsCustomerExtDto extends B2bEconomicsCustomerDto
		implements Comparable<B2bEconomicsCustomerExtDto> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7118011165961372487L;

	private String taskId;
	private String remarks;

	private String startTimeStr;
	private String endTimeStr;

	private String approveName;
	private String datumType;// 材料类型

	private String roleName;
	private String economicsBankPksStr;
	private String customerGoodsPksStr;
	private String economicsBankNamesStr;

	private Integer isExitHistory; // 是否存在历史记录：1 存在；0 不存在
	private Integer isHaveBank; // 是否已存在银行：1 存在；0 不存在

	private String econGoodsPkD;
	private String effectStartTimesD;
	private String effectEndTimesD;
	private String limitsD;
	private String totalRateD;
	private String bankRateD;
	private String termD;
	private String sevenRateD;
	private String econGoodsPkBt;
	private String effectStartTimesBt;
	private String effectEndTimesBt;
	private String limitsBt;
	private String totalRateBt;
	private String bankRateBt;
	private String termBt;

	private Integer source;
	private Integer auditStatus;
	private String productType;
	private String bankPk;

	private String accountPk;

	@Override
	public int compareTo(B2bEconomicsCustomerExtDto o) {
		return this.getBankPk().compareTo(o.getBankPk());
	}

	public String getAccountPk() {
		return accountPk;
	}

	public void setAccountPk(String accountPk) {
		this.accountPk = accountPk;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getBankPk() {
		return bankPk;
	}

	public void setBankPk(String bankPk) {
		this.bankPk = bankPk;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}

	List<B2bEconomicsCustomerGoodsDto> goodsList;

	public List<B2bEconomicsCustomerGoodsDto> getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(List<B2bEconomicsCustomerGoodsDto> goodsList) {
		this.goodsList = goodsList;
	}

	public Integer getIsHaveBank() {
		return isHaveBank;
	}

	public void setIsHaveBank(Integer isHaveBank) {
		this.isHaveBank = isHaveBank;
	}

	public Integer getIsExitHistory() {
		return isExitHistory;
	}

	public void setIsExitHistory(Integer isExitHistory) {
		this.isExitHistory = isExitHistory;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	private String updateStartTime;
	private String updateEndTime;
	private String insertStartTime;
	private String insertEndTime;

	private String isReApply;

	public String getIsReApply() {
		return isReApply;
	}

	public void setIsReApply(String isReApply) {
		this.isReApply = isReApply;
	}

	public String getUpdateStartTime() {
		return updateStartTime;
	}

	public void setUpdateStartTime(String updateStartTime) {
		this.updateStartTime = updateStartTime;
	}

	public String getUpdateEndTime() {
		return updateEndTime;
	}

	public void setUpdateEndTime(String updateEndTime) {
		this.updateEndTime = updateEndTime;
	}

	public String getInsertStartTime() {
		return insertStartTime;
	}

	public void setInsertStartTime(String insertStartTime) {
		this.insertStartTime = insertStartTime;
	}

	public String getInsertEndTime() {
		return insertEndTime;
	}

	public void setInsertEndTime(String insertEndTime) {
		this.insertEndTime = insertEndTime;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getStartTimeStr() {
		return startTimeStr;
	}

	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}

	public String getEndTimeStr() {
		return endTimeStr;
	}

	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}

	public String getApproveName() {
		return approveName;
	}

	public void setApproveName(String approveName) {
		this.approveName = approveName;
	}

	public String getDatumType() {
		return datumType;
	}

	public void setDatumType(String datumType) {
		this.datumType = datumType;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getEconomicsBankPksStr() {
		return economicsBankPksStr;
	}

	public void setEconomicsBankPksStr(String economicsBankPksStr) {
		this.economicsBankPksStr = economicsBankPksStr;
	}

	public String getCustomerGoodsPksStr() {
		return customerGoodsPksStr;
	}

	public void setCustomerGoodsPksStr(String customerGoodsPksStr) {
		this.customerGoodsPksStr = customerGoodsPksStr;
	}

	public String getEconomicsBankNamesStr() {
		return economicsBankNamesStr;
	}

	public void setEconomicsBankNamesStr(String economicsBankNamesStr) {
		this.economicsBankNamesStr = economicsBankNamesStr;
	}

	public String getEconGoodsPkD() {
		return econGoodsPkD;
	}

	public void setEconGoodsPkD(String econGoodsPkD) {
		this.econGoodsPkD = econGoodsPkD;
	}

	public String getEffectStartTimesD() {
		return effectStartTimesD;
	}

	public void setEffectStartTimesD(String effectStartTimesD) {
		this.effectStartTimesD = effectStartTimesD;
	}

	public String getEffectEndTimesD() {
		return effectEndTimesD;
	}

	public void setEffectEndTimesD(String effectEndTimesD) {
		this.effectEndTimesD = effectEndTimesD;
	}

	public String getLimitsD() {
		return limitsD;
	}

	public void setLimitsD(String limitsD) {
		this.limitsD = limitsD;
	}

	public String getTotalRateD() {
		return totalRateD;
	}

	public void setTotalRateD(String totalRateD) {
		this.totalRateD = totalRateD;
	}

	public String getBankRateD() {
		return bankRateD;
	}

	public void setBankRateD(String bankRateD) {
		this.bankRateD = bankRateD;
	}

	public String getTermD() {
		return termD;
	}

	public void setTermD(String termD) {
		this.termD = termD;
	}

	public String getSevenRateD() {
		return sevenRateD;
	}

	public void setSevenRateD(String sevenRateD) {
		this.sevenRateD = sevenRateD;
	}

	public String getEconGoodsPkBt() {
		return econGoodsPkBt;
	}

	public void setEconGoodsPkBt(String econGoodsPkBt) {
		this.econGoodsPkBt = econGoodsPkBt;
	}

	public String getEffectStartTimesBt() {
		return effectStartTimesBt;
	}

	public void setEffectStartTimesBt(String effectStartTimesBt) {
		this.effectStartTimesBt = effectStartTimesBt;
	}

	public String getEffectEndTimesBt() {
		return effectEndTimesBt;
	}

	public void setEffectEndTimesBt(String effectEndTimesBt) {
		this.effectEndTimesBt = effectEndTimesBt;
	}

	public String getLimitsBt() {
		return limitsBt;
	}

	public void setLimitsBt(String limitsBt) {
		this.limitsBt = limitsBt;
	}

	public String getTotalRateBt() {
		return totalRateBt;
	}

	public void setTotalRateBt(String totalRateBt) {
		this.totalRateBt = totalRateBt;
	}

	public String getBankRateBt() {
		return bankRateBt;
	}

	public void setBankRateBt(String bankRateBt) {
		this.bankRateBt = bankRateBt;
	}

	public String getTermBt() {
		return termBt;
	}

	public void setTermBt(String termBt) {
		this.termBt = termBt;
	}

}