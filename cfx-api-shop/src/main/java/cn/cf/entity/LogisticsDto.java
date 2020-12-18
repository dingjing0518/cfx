package cn.cf.entity;

import java.util.List;

import javax.persistence.Id;

import cn.cf.dto.BaseDTO;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class LogisticsDto extends BaseDTO implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	@Id
	private String pk;
	private String name;
	private String provinceName;
	private String province;
	private String cityName;
	private String city;
	private String areaName;
	private String area;
	private String town;
	private String townName;
	private Integer status;
	private String plantPk;
	private String plantName;
	private String gradePk;
	private String gradeName;
	private String productPk;
	private String productName;
	private Integer isStep;
	private Double minPrice;
	private String storePk;
	private Integer isDelete;
	private List<LogisticsStepInfoDto> stepInfos;
	private String stepPrices;
	private String priceStr;
	private String logisticssetpinfoPk;
	private Double price;
	//columns END

	public String getName() {
		return name;
	}


	public String getPriceStr() {
		return priceStr;
	}


	public void setPriceStr(String priceStr) {
		this.priceStr = priceStr;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}


	public void setPk(String pk) {
		this.pk = pk;
	}


	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public List<LogisticsStepInfoDto> getStepInfos() {
		return stepInfos;
	}

	public void setStepInfos(List<LogisticsStepInfoDto> stepInfos) {
		this.stepInfos = stepInfos;
	}

	public String getPk() {
		return this.pk;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	
	public String getProvinceName() {
		return this.provinceName;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	
	public String getProvince() {
		return this.province;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
	public String getCityName() {
		return this.cityName;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getCity() {
		return this.city;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	
	public String getAreaName() {
		return this.areaName;
	}
	public void setArea(String area) {
		this.area = area;
	}
	
	public String getArea() {
		return this.area;
	}
	public void setTown(String town) {
		this.town = town;
	}
	
	public String getTown() {
		return this.town;
	}
	public void setTownName(String townName) {
		this.townName = townName;
	}
	
	public String getTownName() {
		return this.townName;
	}

	public void setPlantPk(String plantPk) {
		this.plantPk = plantPk;
	}
	
	public String getPlantPk() {
		return this.plantPk;
	}
	public void setPlantName(String plantName) {
		this.plantName = plantName;
	}
	
	public String getPlantName() {
		return this.plantName;
	}
	public void setGradePk(String gradePk) {
		this.gradePk = gradePk;
	}
	
	public String getGradePk() {
		return this.gradePk;
	}
	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}
	
	public String getGradeName() {
		return this.gradeName;
	}
	public void setProductPk(String productPk) {
		this.productPk = productPk;
	}
	
	public String getProductPk() {
		return this.productPk;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public String getProductName() {
		return this.productName;
	}
	public void setIsStep(Integer isStep) {
		this.isStep = isStep;
	}
	
	public Integer getIsStep() {
		return this.isStep;
	}
	public void setMinPrice(Double minPrice) {
		this.minPrice = minPrice;
	}
	
	public Double getMinPrice() {
		return this.minPrice;
	}
	public void setStorePk(String storePk) {
		this.storePk = storePk;
	}
	
	public String getStorePk() {
		return this.storePk;
	}


	public String getLogisticssetpinfoPk() {
		return logisticssetpinfoPk;
	}


	public void setLogisticssetpinfoPk(String logisticssetpinfoPk) {
		this.logisticssetpinfoPk = logisticssetpinfoPk;
	}


	public Double getPrice() {
		return price;
	}


	public void setPrice(Double price) {
		this.price = price;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public String getStepPrices() {
		return stepPrices;
	}


	public void setStepPrices(String stepPrices) {
		this.stepPrices = stepPrices;
	}
	

}