package cn.cf.entity;

import java.math.BigDecimal;

import javax.persistence.Id;

public class TransactionDataReportForm {
	
	@Id
	private String id;
	//交易店铺数
	private Integer storeAllCount = 0;
	private Integer storeBlankCount = 0;
	private Integer storeLoanCount = 0;
	//交易采购商数
	private Integer purchaserCount = 0;
	private Integer purchBlankCount = 0;
	private Integer purchLoanCount = 0;
	//订单数
	private Integer orderCount = 0;
	private Integer orderBlankCount = 0;
	private Integer orderLoanCount = 0;
	private Integer orderSelfCount = 0;
	private Integer orderWapCount = 0;
	//成交量（吨）
	private BigDecimal transWeight = new BigDecimal(0.0);
	private BigDecimal transBlankWeight = new BigDecimal(0.0);
	private BigDecimal transLoanWeight = new BigDecimal(0.0);
	//交易额（元）
	private BigDecimal transAmount = new BigDecimal(0.0);
	private BigDecimal transBlankAmount = new BigDecimal(0.0);
	private BigDecimal transLoanAmount = new BigDecimal(0.0);
	
	//历史累计订单数
	private Integer addUpOrderCount = 0;
	private Integer addUpOrderBlankCount = 0;
	private Integer addUpOrderLoanCount = 0;
	
	//历史累计成交量（吨）
	private BigDecimal addUpTransWeight = new BigDecimal(0.0);
	private BigDecimal addUpTransBlankWeight = new BigDecimal(0.0);
	private BigDecimal addUpTransLoanWeight = new BigDecimal(0.0);
	
	//历史累计交易额（元）
	private BigDecimal addUpTransAmount = new BigDecimal(0.0);
	private BigDecimal addUpTransBlankAmount = new BigDecimal(0.0);
	private BigDecimal addUpTransLoanAmount = new BigDecimal(0.0);
	
	private String countTime;//数据统计时间
	private String insertTime;//添加时间
	//查询条件
	private String countTimeStart;
	private String countTimeEnd;
	//导出勾选数据条件
	private String ids;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getStoreAllCount() {
		return storeAllCount;
	}
	public void setStoreAllCount(Integer storeAllCount) {
		this.storeAllCount = storeAllCount;
	}
	public Integer getStoreBlankCount() {
		return storeBlankCount;
	}
	public void setStoreBlankCount(Integer storeBlankCount) {
		this.storeBlankCount = storeBlankCount;
	}
	public Integer getStoreLoanCount() {
		return storeLoanCount;
	}
	public void setStoreLoanCount(Integer storeLoanCount) {
		this.storeLoanCount = storeLoanCount;
	}
	public Integer getPurchaserCount() {
		return purchaserCount;
	}
	public void setPurchaserCount(Integer purchaserCount) {
		this.purchaserCount = purchaserCount;
	}
	public Integer getPurchBlankCount() {
		return purchBlankCount;
	}
	public void setPurchBlankCount(Integer purchBlankCount) {
		this.purchBlankCount = purchBlankCount;
	}
	public Integer getPurchLoanCount() {
		return purchLoanCount;
	}
	public void setPurchLoanCount(Integer purchLoanCount) {
		this.purchLoanCount = purchLoanCount;
	}
	public Integer getOrderCount() {
		return orderCount;
	}
	public void setOrderCount(Integer orderCount) {
		this.orderCount = orderCount;
	}
	public Integer getOrderBlankCount() {
		return orderBlankCount;
	}
	public void setOrderBlankCount(Integer orderBlankCount) {
		this.orderBlankCount = orderBlankCount;
	}
	public Integer getOrderLoanCount() {
		return orderLoanCount;
	}
	public void setOrderLoanCount(Integer orderLoanCount) {
		this.orderLoanCount = orderLoanCount;
	}
	public Integer getOrderSelfCount() {
		return orderSelfCount;
	}
	public void setOrderSelfCount(Integer orderSelfCount) {
		this.orderSelfCount = orderSelfCount;
	}
	public Integer getOrderWapCount() {
		return orderWapCount;
	}
	public void setOrderWapCount(Integer orderWapCount) {
		this.orderWapCount = orderWapCount;
	}
	public BigDecimal getTransWeight() {
		return transWeight;
	}
	public void setTransWeight(BigDecimal transWeight) {
		this.transWeight = transWeight;
	}
	public BigDecimal getTransBlankWeight() {
		return transBlankWeight;
	}
	public void setTransBlankWeight(BigDecimal transBlankWeight) {
		this.transBlankWeight = transBlankWeight;
	}
	public BigDecimal getTransLoanWeight() {
		return transLoanWeight;
	}
	public void setTransLoanWeight(BigDecimal transLoanWeight) {
		this.transLoanWeight = transLoanWeight;
	}
	public BigDecimal getTransAmount() {
		return transAmount;
	}
	public void setTransAmount(BigDecimal transAmount) {
		this.transAmount = transAmount;
	}
	public BigDecimal getTransBlankAmount() {
		return transBlankAmount;
	}
	public void setTransBlankAmount(BigDecimal transBlankAmount) {
		this.transBlankAmount = transBlankAmount;
	}
	public BigDecimal getTransLoanAmount() {
		return transLoanAmount;
	}
	public void setTransLoanAmount(BigDecimal transLoanAmount) {
		this.transLoanAmount = transLoanAmount;
	}
	public Integer getAddUpOrderCount() {
		return addUpOrderCount;
	}
	public void setAddUpOrderCount(Integer addUpOrderCount) {
		this.addUpOrderCount = addUpOrderCount;
	}
	public Integer getAddUpOrderBlankCount() {
		return addUpOrderBlankCount;
	}
	public void setAddUpOrderBlankCount(Integer addUpOrderBlankCount) {
		this.addUpOrderBlankCount = addUpOrderBlankCount;
	}
	public Integer getAddUpOrderLoanCount() {
		return addUpOrderLoanCount;
	}
	public void setAddUpOrderLoanCount(Integer addUpOrderLoanCount) {
		this.addUpOrderLoanCount = addUpOrderLoanCount;
	}
	public BigDecimal getAddUpTransWeight() {
		return addUpTransWeight;
	}
	public void setAddUpTransWeight(BigDecimal addUpTransWeight) {
		this.addUpTransWeight = addUpTransWeight;
	}
	public BigDecimal getAddUpTransBlankWeight() {
		return addUpTransBlankWeight;
	}
	public void setAddUpTransBlankWeight(BigDecimal addUpTransBlankWeight) {
		this.addUpTransBlankWeight = addUpTransBlankWeight;
	}
	public BigDecimal getAddUpTransLoanWeight() {
		return addUpTransLoanWeight;
	}
	public void setAddUpTransLoanWeight(BigDecimal addUpTransLoanWeight) {
		this.addUpTransLoanWeight = addUpTransLoanWeight;
	}
	public BigDecimal getAddUpTransAmount() {
		return addUpTransAmount;
	}
	public void setAddUpTransAmount(BigDecimal addUpTransAmount) {
		this.addUpTransAmount = addUpTransAmount;
	}
	public BigDecimal getAddUpTransBlankAmount() {
		return addUpTransBlankAmount;
	}
	public void setAddUpTransBlankAmount(BigDecimal addUpTransBlankAmount) {
		this.addUpTransBlankAmount = addUpTransBlankAmount;
	}
	public BigDecimal getAddUpTransLoanAmount() {
		return addUpTransLoanAmount;
	}
	public void setAddUpTransLoanAmount(BigDecimal addUpTransLoanAmount) {
		this.addUpTransLoanAmount = addUpTransLoanAmount;
	}
	public String getCountTime() {
		return countTime;
	}
	public void setCountTime(String countTime) {
		this.countTime = countTime;
	}
	public String getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}
	public String getCountTimeStart() {
		return countTimeStart;
	}
	public void setCountTimeStart(String countTimeStart) {
		this.countTimeStart = countTimeStart;
	}
	public String getCountTimeEnd() {
		return countTimeEnd;
	}
	public void setCountTimeEnd(String countTimeEnd) {
		this.countTimeEnd = countTimeEnd;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	
}
