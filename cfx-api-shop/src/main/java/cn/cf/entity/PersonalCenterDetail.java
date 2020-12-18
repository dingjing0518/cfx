package cn.cf.entity;

import cn.cf.dto.B2bCompanyDto;


public class PersonalCenterDetail {

	// 左侧菜单
//	private String leftMenus;
	// 公司
	private String companyName;
//	// 授信总金额
//	private Double totalAmount;
//	// 授信可用金额
//	private Double availableAmount;
//	// 授信开始时间
//	private String creditStartTime;
//	// 授信结束时间
//	private String creditEndTime;
//	// 订单数量
//	private Map<String, Object> orderCount;
//	// 消息已读数
//	private Integer readCounts;
//	// 消息未读数
//	private Integer unReadCounts;
	//销售中的商品
	private Integer saleGoodCounts;
	//审核中的商品
//	private Integer auditGoodCounts;
//	//总订单数
//	private Integer orderCounts;
//	//总成交量
//	private Double	volumes;
//	//总金额
//	private Double allAmounts;
	private B2bCompanyDto company;

	public B2bCompanyDto getCompany() {
		return company;
	}

	public void setCompany(B2bCompanyDto company) {
		this.company = company;
	}

//	public String getLeftMenus() {
//		return leftMenus;
//	}
//
//	public void setLeftMenus(String leftMenus) {
//		this.leftMenus = leftMenus;
//	}
//
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
//
//	public Double getTotalAmount() {
//		return totalAmount;
//	}
//
//	public void setTotalAmount(Double totalAmount) {
//		this.totalAmount = totalAmount;
//	}
//
//	public Double getAvailableAmount() {
//		return availableAmount;
//	}
//
//	public void setAvailableAmount(Double availableAmount) {
//		this.availableAmount = availableAmount;
//	}
//
//	
//
//	public String getCreditStartTime() {
//		return creditStartTime;
//	}
//
//	public void setCreditStartTime(String creditStartTime) {
//		this.creditStartTime = creditStartTime;
//	}
//
//	public String getCreditEndTime() {
//		return creditEndTime;
//	}
//
//	public void setCreditEndTime(String creditEndTime) {
//		this.creditEndTime = creditEndTime;
//	}
//
//	public Map<String, Object> getOrderCount() {
//		return orderCount;
//	}
//
//	public void setOrderCount(Map<String, Object> orderCount) {
//		this.orderCount = orderCount;
//	}
//
//	public int getReadCounts() {
//		return readCounts;
//	}
//
//	public void setReadCounts(Integer readCounts) {
//		this.readCounts = readCounts;
//	}
//
//	public int getUnReadCounts() {
//		return unReadCounts;
//	}
//
//	public void setUnReadCounts(Integer unReadCounts) {
//		this.unReadCounts = unReadCounts;
//	}

	public Integer getSaleGoodCounts() {
		return saleGoodCounts;
	}

	public void setSaleGoodCounts(Integer saleGoodCounts) {
		this.saleGoodCounts = saleGoodCounts;
	}

//	public Integer getAuditGoodCounts() {
//		return auditGoodCounts;
//	}
//
//	public void setAuditGoodCounts(Integer auditGoodCounts) {
//		this.auditGoodCounts = auditGoodCounts;
//	}
//
//	public Integer getOrderCounts() {
//		return orderCounts;
//	}
//
//	public void setOrderCounts(Integer orderCounts) {
//		this.orderCounts = orderCounts;
//	}
//
//	public Double getVolumes() {
//		return volumes;
//	}
//
//	public void setVolumes(Double volumes) {
//		this.volumes = volumes;
//	}
//
//	public Double getAllAmounts() {
//		return allAmounts;
//	}
//
//	public void setAllAmounts(Double allAmounts) {
//		this.allAmounts = allAmounts;
//	}
	
	
}
