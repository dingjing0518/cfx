package cn.cf.dto;

public class LogisticsLinePriceDto   extends BaseDTO implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private  String pk;
	private  Double fromWeight; 
	private  Double  toWeight;
	private  Double basisPrice;
	private  Double salePrice;
	public String getPk() {
		return pk;
	}
	public void setPk(String pk) {
		this.pk = pk;
	}
	public Double getFromWeight() {
		return fromWeight;
	}
	public void setFromWeight(Double fromWeight) {
		this.fromWeight = fromWeight;
	}
	public Double getToWeight() {
		return toWeight;
	}
	public void setToWeight(Double toWeight) {
		this.toWeight = toWeight;
	}
	public Double getBasisPrice() {
		return basisPrice;
	}
	public void setBasisPrice(Double basisPrice) {
		this.basisPrice = basisPrice;
	}
	public Double getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
	}
	
	

}
