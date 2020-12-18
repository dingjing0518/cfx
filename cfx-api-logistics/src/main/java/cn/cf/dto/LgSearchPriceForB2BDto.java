package cn.cf.dto;

public class LgSearchPriceForB2BDto  extends BaseDTO implements java.io.Serializable {
	
	private static final long serialVersionUID = 5454155825314635342L;
	
	private Double weight;//重量
	private String fromProvicePk;//提货地址	
	private String fromCityPk;
	private String fromAreaPk;
	private String fromTownPk;
	private String toProvicePk;//收货地址
	private String toCityPk;
	private String toAreaPk;
	private String toTownPk;
	private String productPk;//货物pk
	private String gradePk;// 等级pk
	private Integer type;//合同下单的标识，不取最低运费
	
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public String getFromProvicePk() {
		return fromProvicePk;
	}
	public void setFromProvicePk(String fromProvicePk) {
		this.fromProvicePk = fromProvicePk;
	}
	public String getFromCityPk() {
		return fromCityPk;
	}
	public void setFromCityPk(String fromCityPk) {
		this.fromCityPk = fromCityPk;
	}
	public String getFromAreaPk() {
		return fromAreaPk;
	}
	public void setFromAreaPk(String fromAreaPk) {
		this.fromAreaPk = fromAreaPk;
	}
	public String getFromTownPk() {
		return fromTownPk;
	}
	public void setFromTownPk(String fromTownPk) {
		this.fromTownPk = fromTownPk;
	}
	public String getToProvicePk() {
		return toProvicePk;
	}
	public void setToProvicePk(String toProvicePk) {
		this.toProvicePk = toProvicePk;
	}
	public String getToCityPk() {
		return toCityPk;
	}
	public void setToCityPk(String toCityPk) {
		this.toCityPk = toCityPk;
	}
	public String getToAreaPk() {
		return toAreaPk;
	}
	public void setToAreaPk(String toAreaPk) {
		this.toAreaPk = toAreaPk;
	}
	public String getToTownPk() {
		return toTownPk;
	}
	public void setToTownPk(String toTownPk) {
		this.toTownPk = toTownPk;
	}
	public String getProductPk() {
		return productPk;
	}
	public void setProductPk(String productPk) {
		this.productPk = productPk;
	}
	public String getGradePk() {
		return gradePk;
	}
	public void setGradePk(String gradePk) {
		this.gradePk = gradePk;
	}
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
