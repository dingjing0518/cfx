package cn.cf.dto;

public class B2bEconomicsCreditcustomerDtoEx extends B2bEconomicsCreditcustomerDto{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String insertStartTime;
	private String insertEndTime;
	private String updateStartTime;
	private String updateEndTime;
	
	private String roleName;
	
	private String accountPk;
	
	
	public String getAccountPk() {
        return accountPk;
    }
    public void setAccountPk(String accountPk) {
        this.accountPk = accountPk;
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
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	
	
	
}
