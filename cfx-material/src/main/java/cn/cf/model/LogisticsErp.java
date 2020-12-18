package cn.cf.model;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class LogisticsErp extends BaseModel  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	
	private java.lang.String pk;
	private java.lang.String provinceName;
	private java.lang.String province;
	private java.lang.String cityName;
	private java.lang.String city;
	private java.lang.String areaName;
	private java.lang.String area;
	private java.lang.String town;
	private java.lang.String townName;
	private java.lang.String plantPk;
	private java.lang.String plantName;
	private java.lang.Integer isDelete;
	private java.lang.Integer status;
	private java.lang.String storePk;
	private java.lang.Double lowPrice;
	private java.lang.String name;
	private java.lang.String lgCompanyPk;
	private java.lang.String lgCompanyName;


	//columns END
private  String memo;
	
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	 
	public void setPk(java.lang.String pk) {
		this.pk = pk;
	}
	
	public java.lang.String getPk() {
		return this.pk;
	}
	public void setProvinceName(java.lang.String provinceName) {
		this.provinceName = provinceName;
	}
	
	public java.lang.String getProvinceName() {
		return this.provinceName;
	}
	public void setProvince(java.lang.String province) {
		this.province = province;
	}
	
	public java.lang.String getProvince() {
		return this.province;
	}
	public void setCityName(java.lang.String cityName) {
		this.cityName = cityName;
	}
	
	public java.lang.String getCityName() {
		return this.cityName;
	}
	public void setCity(java.lang.String city) {
		this.city = city;
	}
	
	public java.lang.String getCity() {
		return this.city;
	}
	public void setAreaName(java.lang.String areaName) {
		this.areaName = areaName;
	}
	
	public java.lang.String getAreaName() {
		return this.areaName;
	}
	public void setArea(java.lang.String area) {
		this.area = area;
	}
	
	public java.lang.String getArea() {
		return this.area;
	}
	public void setTown(java.lang.String town) {
		this.town = town;
	}
	
	public java.lang.String getTown() {
		return this.town;
	}
	public void setTownName(java.lang.String townName) {
		this.townName = townName;
	}
	
	public java.lang.String getTownName() {
		return this.townName;
	}
	public void setPlantPk(java.lang.String plantPk) {
		this.plantPk = plantPk;
	}
	
	public java.lang.String getPlantPk() {
		return this.plantPk;
	}
	public void setPlantName(java.lang.String plantName) {
		this.plantName = plantName;
	}
	
	public java.lang.String getPlantName() {
		return this.plantName;
	}
	public void setIsDelete(java.lang.Integer isDelete) {
		this.isDelete = isDelete;
	}
	
	public java.lang.Integer getIsDelete() {
		return this.isDelete;
	}
	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}
	
	public java.lang.Integer getStatus() {
		return this.status;
	}

	public java.lang.String getStorePk() {
		return storePk;
	}

	public void setStorePk(java.lang.String storePk) {
		this.storePk = storePk;
	}

	public java.lang.Double getLowPrice() {
		return lowPrice;
	}

	public void setLowPrice(java.lang.Double lowPrice) {
		this.lowPrice = lowPrice;
	}

	public java.lang.String getName() {
		return name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	public java.lang.String getLgCompanyPk() {
		return lgCompanyPk;
	}

	public void setLgCompanyPk(java.lang.String lgCompanyPk) {
		this.lgCompanyPk = lgCompanyPk;
	}

	public java.lang.String getLgCompanyName() {
		return lgCompanyName;
	}

	public void setLgCompanyName(java.lang.String lgCompanyName) {
		this.lgCompanyName = lgCompanyName;
	}

	
}