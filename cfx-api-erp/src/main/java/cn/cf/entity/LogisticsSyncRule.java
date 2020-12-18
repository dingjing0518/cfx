package cn.cf.entity;

import java.util.List;

public class LogisticsSyncRule {
	
	private String id;
	
	private Integer status;
	
	private String plantName;
	
	private String provinceName;
	
	private String cityName;
	
	private String areaName;
	
	private String townName;
	
	private String lowPrice;
	
	private String  carrierName;
	
	private String name;
	
	private List<LogisticsInfoSyncRule> priceItems;
	
	private List<LogisticsSyncRuleEmployee> manageItems;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getPlantName() {
		return plantName;
	}

	public void setPlantName(String plantName) {
		this.plantName = plantName;
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

	public List<LogisticsInfoSyncRule> getPriceItems() {
		return priceItems;
	}

	public void setPriceItems(List<LogisticsInfoSyncRule> priceItems) {
		this.priceItems = priceItems;
	}

	public List<LogisticsSyncRuleEmployee> getManageItems() {
		return manageItems;
	}

	public void setManageItems(List<LogisticsSyncRuleEmployee> manageItems) {
		this.manageItems = manageItems;
	}

	public String getLowPrice() {
		return lowPrice;
	}

	public void setLowPrice(String lowPrice) {
		this.lowPrice = lowPrice;
	}
	
	public String getCarrierName() {
		return carrierName;
	}

	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
