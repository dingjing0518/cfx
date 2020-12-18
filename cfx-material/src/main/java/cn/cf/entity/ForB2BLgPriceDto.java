package cn.cf.entity;

public class ForB2BLgPriceDto  implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	
	private String linePk;// 线路pk
	private String logisticsStepInfoPk;// 阶梯价pk
	private Double price;// 阶梯单价
	private Double lowPrice;// 最低起运价
	private Double totalFreight;// 运费总价
	private String logisticsCarrierPk;// 承运商pk
	private String logisticsCarrierName;// 承运商
	
	public String getLinePk() {
		return linePk;
	}
	public void setLinePk(String linePk) {
		this.linePk = linePk;
	}
	public String getLogisticsStepInfoPk() {
		return logisticsStepInfoPk;
	}
	public void setLogisticsStepInfoPk(String logisticsStepInfoPk) {
		this.logisticsStepInfoPk = logisticsStepInfoPk;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getLowPrice() {
		return lowPrice;
	}
	public void setLowPrice(Double lowPrice) {
		this.lowPrice = lowPrice;
	}
	public Double getTotalFreight() {
		return totalFreight;
	}
	public void setTotalFreight(Double totalFreight) {
		this.totalFreight = totalFreight;
	}
	public String getLogisticsCarrierPk() {
		return logisticsCarrierPk;
	}
	public void setLogisticsCarrierPk(String logisticsCarrierPk) {
		this.logisticsCarrierPk = logisticsCarrierPk;
	}
	public String getLogisticsCarrierName() {
		return logisticsCarrierName;
	}
	public void setLogisticsCarrierName(String logisticsCarrierName) {
		this.logisticsCarrierName = logisticsCarrierName;
	}
	
	
	

}
