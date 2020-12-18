package cn.cf.entity;

public class LogisticsCart {

	private String cartPk;
	private String goodsPk;
	private Integer boxes;
	private String logisticsPk;
	private String logisticsStepInfoPk;
	private Double price;
	private Double lowPrice;
	 

	public Double getLowPrice() {
		return lowPrice;
	}

	public void setLowPrice(Double lowPrice) {
		this.lowPrice = lowPrice;
	}

	// 平台承运参数
	private Double originalTotalFreight;// 对外总价
	private Double basisLineTotalPrice;// 对内总价

	
 

	public Double getOriginalTotalFreight() {
		return originalTotalFreight;
	}

	public void setOriginalTotalFreight(Double originalTotalFreight) {
		this.originalTotalFreight = originalTotalFreight;
	}

	public Double getBasisLineTotalPrice() {
		return basisLineTotalPrice;
	}

	public void setBasisLineTotalPrice(Double basisLineTotalPrice) {
		this.basisLineTotalPrice = basisLineTotalPrice;
	}

	public String getCartPk() {
		return cartPk;
	}

	public void setCartPk(String cartPk) {
		this.cartPk = cartPk;
	}

	public String getLogisticsPk() {
		return logisticsPk;
	}

	public void setLogisticsPk(String logisticsPk) {
		this.logisticsPk = logisticsPk;
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

	public String getGoodsPk() {
		return goodsPk;
	}

	public void setGoodsPk(String goodsPk) {
		this.goodsPk = goodsPk;
	}

	public Integer getBoxes() {
		return boxes;
	}

	public void setBoxes(Integer boxes) {
		this.boxes = boxes;
	}

}
