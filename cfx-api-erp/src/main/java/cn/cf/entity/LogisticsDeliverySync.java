package cn.cf.entity;
import java.util.List;

public class LogisticsDeliverySync {
	private String pk;
	private String orderPk;
	private String deliveryNumber;
	private String logisticsCompanyName;
	private String toCompanyName;
	private String toProvinceName;
	private String toCityName;
	private String toAreaName;
	private String toTownName;
	private String toAddress;
	private String fromCompanyName;
	private String fromProvinceName;
	private String fromCityName;
	private String fromAreaName;
	private String fromTownName;
	private String fromAddress;
	private String remark;
	private String carPlate;
	private String carType;
	private String driver;
	private String driverContact;
	private Integer orderStatus;
	private String statusTime;
	private Double originalTotalFreight;
	private String storePk;
	private List<GoodsDeliverySync> goods;
	
	

	public String getToTownName() {
		return toTownName;
	}

	public void setToTownName(String toTownName) {
		this.toTownName = toTownName;
	}

	public String getToAddress() {
		return toAddress;
	}

	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public String getOrderPk() {
		return orderPk;
	}

	public void setOrderPk(String orderPk) {
		this.orderPk = orderPk;
	}

	public String getDeliveryNumber() {
		return deliveryNumber;
	}

	public void setDeliveryNumber(String deliveryNumber) {
		this.deliveryNumber = deliveryNumber;
	}

	public String getLogisticsCompanyName() {
		return logisticsCompanyName;
	}

	public void setLogisticsCompanyName(String logisticsCompanyName) {
		this.logisticsCompanyName = logisticsCompanyName;
	}

	public String getToCompanyName() {
		return toCompanyName;
	}

	public void setToCompanyName(String toCompanyName) {
		this.toCompanyName = toCompanyName;
	}

	public String getToProvinceName() {
		return toProvinceName;
	}

	public void setToProvinceName(String toProvinceName) {
		this.toProvinceName = toProvinceName;
	}

	public String getToCityName() {
		return toCityName;
	}

	public void setToCityName(String toCityName) {
		this.toCityName = toCityName;
	}

	public String getToAreaName() {
		return toAreaName;
	}

	public void setToAreaName(String toAreaName) {
		this.toAreaName = toAreaName;
	}

	public String getFromCompanyName() {
		return fromCompanyName;
	}

	public void setFromCompanyName(String fromCompanyName) {
		this.fromCompanyName = fromCompanyName;
	}

	public String getFromProvinceName() {
		return fromProvinceName;
	}

	public void setFromProvinceName(String fromProvinceName) {
		this.fromProvinceName = fromProvinceName;
	}

	public String getFromCityName() {
		return fromCityName;
	}

	public void setFromCityName(String fromCityName) {
		this.fromCityName = fromCityName;
	}

	public String getFromAreaName() {
		return fromAreaName;
	}

	public void setFromAreaName(String fromAreaName) {
		this.fromAreaName = fromAreaName;
	}

	public String getFromTownName() {
		return fromTownName;
	}

	public void setFromTownName(String fromTownName) {
		this.fromTownName = fromTownName;
	}

	public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCarPlate() {
		return carPlate;
	}

	public void setCarPlate(String carPlate) {
		this.carPlate = carPlate;
	}

	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getDriverContact() {
		return driverContact;
	}

	public void setDriverContact(String driverContact) {
		this.driverContact = driverContact;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}


	
	public String getStatusTime() {
		return statusTime;
	}

	public void setStatusTime(String statusTime) {
		this.statusTime = statusTime;
	}


	public Double getOriginalTotalFreight() {
		return originalTotalFreight;
	}

	public void setOriginalTotalFreight(Double originalTotalFreight) {
		this.originalTotalFreight = originalTotalFreight;
	}

	public List<GoodsDeliverySync> getGoods() {
		return goods;
	}

	public void setGoods(List<GoodsDeliverySync> goods) {
		this.goods = goods;
	}

	public String getStorePk() {
		return storePk;
	}

	public void setStorePk(String storePk) {
		this.storePk = storePk;
	}
	
}
