package cn.cf.entity;

public class B2bOrderGoodsUpdateWeight {
	
	
	private String childOrderNumber ;//子定单编号(行号)
	private String presentFreight;//运费单价
	private String presentPrice ;//商品单价
	private String afterBoxes ;//分货箱数
	private String afterWeight;//分货重量
	private String managementFee;//管理费
	private String loadingFee;//装车费
	private String status;//订单状态
	public String getChildOrderNumber() {
		return childOrderNumber;
	}
	public void setChildOrderNumber(String childOrderNumber) {
		this.childOrderNumber = childOrderNumber;
	}
	public String getPresentFreight() {
		return presentFreight;
	}
	public void setPresentFreight(String presentFreight) {
		this.presentFreight = presentFreight;
	}
	public String getPresentPrice() {
		return presentPrice;
	}
	public void setPresentPrice(String presentPrice) {
		this.presentPrice = presentPrice;
	}
	public String getAfterBoxes() {
		return afterBoxes;
	}
	public void setAfterBoxes(String afterBoxes) {
		this.afterBoxes = afterBoxes;
	}
	public String getAfterWeight() {
		return afterWeight;
	}
	public void setAfterWeight(String afterWeight) {
		this.afterWeight = afterWeight;
	}
	public String getManagementFee() {
		return managementFee;
	}
	public void setManagementFee(String managementFee) {
		this.managementFee = managementFee;
	}
	public String getLoadingFee() {
		return loadingFee;
	}
	public void setLoadingFee(String loadingFee) {
		this.loadingFee = loadingFee;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
