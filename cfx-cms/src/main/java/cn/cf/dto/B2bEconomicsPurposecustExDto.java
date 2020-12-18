package cn.cf.dto;

public class B2bEconomicsPurposecustExDto extends B2bEconomicsPurposecustDto {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String updateStartTime;
	private String updateEndTime;
	private String insertStartTime;
	private String insertEndTime;
	private String accountPk;


	private String goodsName;
	
	
	
	public String getAccountPk() {
        return accountPk;
    }

    public void setAccountPk(String accountPk) {
        this.accountPk = accountPk;
    }

    public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
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
	

	

}
