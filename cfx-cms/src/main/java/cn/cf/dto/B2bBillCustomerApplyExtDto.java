package cn.cf.dto;

import java.util.List;

public class B2bBillCustomerApplyExtDto  extends  B2bBillCustomerApplyDto{
	
	private String accountPk;
	
	private String updateStartTime;
	
	private String updateEndTime;
	
	private String InsertStartTime;
	
	private String InsertEndTime;
	
	private String goodsName ;
	
	private String roleName;
	
	private int isExitHistory;
	
	private String isReApply;
	
	private String TaskId;
	
	private String remarks;
	private String limits;
	private String pks;

	
	private List<B2bBillCusgoodsApplyDto> cusgoodsDtos;
	
	public String getAccountPk() {
		return accountPk;
	}

	public void setAccountPk(String accountPk) {
		this.accountPk = accountPk;
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
		return InsertStartTime;
	}

	public void setInsertStartTime(String insertStartTime) {
		InsertStartTime = insertStartTime;
	}

	public String getInsertEndTime() {
		return InsertEndTime;
	}

	public void setInsertEndTime(String insertEndTime) {
		InsertEndTime = insertEndTime;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}



	public int getIsExitHistory() {
		return isExitHistory;
	}

	public void setIsExitHistory(int isExitHistory) {
		this.isExitHistory = isExitHistory;
	}

	public String getIsReApply() {
		return isReApply;
	}

	public void setIsReApply(String isReApply) {
		this.isReApply = isReApply;
	}

	public String getTaskId() {
		return TaskId;
	}

	public void setTaskId(String taskId) {
		TaskId = taskId;
	}

	public List<B2bBillCusgoodsApplyDto> getCusgoodsDtos() {
		return cusgoodsDtos;
	}

	public void setCusgoodsDtos(List<B2bBillCusgoodsApplyDto> cusgoodsDtos) {
		this.cusgoodsDtos = cusgoodsDtos;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getLimits() {
		return limits;
	}

	public void setLimits(String limits) {
		this.limits = limits;
	}

	public String getPks() {
		return pks;
	}

	public void setPks(String pks) {
		this.pks = pks;
	}

	
	
	
	

}
