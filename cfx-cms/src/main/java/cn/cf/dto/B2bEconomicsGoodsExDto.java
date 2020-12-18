package cn.cf.dto;

public class B2bEconomicsGoodsExDto extends B2bEconomicsGoodsDto{

	private String updateStartTime;
	private String updateEndTime;
	private String expireStartTime;
	private String expireEndTime;
	private String storeNames;
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
	public String getExpireStartTime() {
		return expireStartTime;
	}
	public void setExpireStartTime(String expireStartTime) {
		this.expireStartTime = expireStartTime;
	}
	public String getExpireEndTime() {
		return expireEndTime;
	}
	public void setExpireEndTime(String expireEndTime) {
		this.expireEndTime = expireEndTime;
	}
	public String getStoreNames() {
		return storeNames;
	}
	public void setStoreNames(String storeNames) {
		this.storeNames = storeNames;
	}
	
	
}
