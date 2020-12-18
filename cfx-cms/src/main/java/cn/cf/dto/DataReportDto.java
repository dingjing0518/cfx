package cn.cf.dto;

import java.io.Serializable;
import java.util.Date;

public class DataReportDto implements Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	
	private String logisticsCompanyName;

	private String logisticsContacts;

	private String logisticsContactsTel;

	private String orderPk;

	private String purchaserName;

	private String purchaserContacts;

	private String purchaserContactsTel;

	private Date insertTime;

	private Integer orderStatus;

	private String orderStatusName;

	private Double basisLinePrice;//物流供应商线路单价
	
	private Double settleWeight;//物流供应商线路结算重量
	
	private Double lgPresentTotalFreight;//物流供应商线路结算金额

	private Double weight;//订单实际重量

	private Double TotalFreight; 

	private Double presentTotalFreight; //用户结算总价

	private Double profit; //利润
	
	private Integer isSettleFreight;
	
	private String isSettleFreightName;
	
	private String productName;//品名
	
	private String presentFreight;//对外单价
	
	private String fromCompanyName;
	
	private String supplierName;
	
	private String isAbnormal;//是否异常
	
	private Integer isConfirmed;//订单异常是否确认1,已确认，2未确认
	
	private String deliveryNumber;
	
	private String signTime;//签收时间
	
	private String toCompanyName;//收货公司名称
	
	private String transConsumption;//运输时耗
	
	private String toAddress;//收货时间
	
    private String originalFreight;

    private Double goodsPresentFreight;

    private Double goodsOriginalFreight;

    
    

    public String getOriginalFreight() {
        return originalFreight;
    }

    public void setOriginalFreight(String originalFreight) {
        this.originalFreight = originalFreight;
    }

    public Double getGoodsPresentFreight() {
        return goodsPresentFreight;
    }

    public void setGoodsPresentFreight(Double goodsPresentFreight) {
        this.goodsPresentFreight = goodsPresentFreight;
    }

    public Double getGoodsOriginalFreight() {
        return goodsOriginalFreight;
    }

    public void setGoodsOriginalFreight(Double goodsOriginalFreight) {
        this.goodsOriginalFreight = goodsOriginalFreight;
    }

    public void setPresentFreight(String presentFreight) {
		this.presentFreight = presentFreight;
	}

	public String getToAddress() {
		return toAddress;
	}

	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	public String getTransConsumption() {
		return transConsumption;
	}

	public void setTransConsumption(String transConsumption) {
		this.transConsumption = transConsumption;
	}


	public String getToCompanyName() {
		return toCompanyName;
	}

	public void setToCompanyName(String toCompanyName) {
		this.toCompanyName = toCompanyName;
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

	public String getLogisticsContacts() {
		return logisticsContacts;
	}

	public void setLogisticsContacts(String logisticsContacts) {
		this.logisticsContacts = logisticsContacts;
	}

	public String getLogisticsContactsTel() {
		return logisticsContactsTel;
	}

	public void setLogisticsContactsTel(String logisticsContactsTel) {
		this.logisticsContactsTel = logisticsContactsTel;
	}

	public String getOrderPk() {
		return orderPk;
	}

	public void setOrderPk(String orderPk) {
		this.orderPk = orderPk;
	}

	public String getPurchaserName() {
		return purchaserName;
	}

	public void setPurchaserName(String purchaserName) {
		this.purchaserName = purchaserName;
	}

	public String getPurchaserContacts() {
		return purchaserContacts;
	}

	public void setPurchaserContacts(String purchaserContacts) {
		this.purchaserContacts = purchaserContacts;
	}

	public String getPurchaserContactsTel() {
		return purchaserContactsTel;
	}

	public void setPurchaserContactsTel(String purchaserContactsTel) {
		this.purchaserContactsTel = purchaserContactsTel;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getOrderStatusName() {
		return orderStatusName;
	}

	public void setOrderStatusName(String orderStatusName) {
		this.orderStatusName = orderStatusName;
	}

	public Double getBasisLinePrice() {
		return basisLinePrice;
	}

	public void setBasisLinePrice(Double basisLinePrice) {
		this.basisLinePrice = basisLinePrice;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Double getTotalFreight() {
		return TotalFreight;
	}

	public void setTotalFreight(Double totalFreight) {
		TotalFreight = totalFreight;
	}

	public Double getPresentTotalFreight() {
		return presentTotalFreight;
	}

	public void setPresentTotalFreight(Double presentTotalFreight) {
		this.presentTotalFreight = presentTotalFreight;
	}

	public Double getProfit() {
		return profit;
	}

	public void setProfit(Double profit) {
		this.profit = profit;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getIsSettleFreight() {
		return isSettleFreight;
	}

	public void setIsSettleFreight(Integer isSettleFreight) {
		this.isSettleFreight = isSettleFreight;
	}

	public String getIsSettleFreightName() {
		return isSettleFreightName;
	}

	public void setIsSettleFreightName(String isSettleFreightName) {
		this.isSettleFreightName = isSettleFreightName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getFromCompanyName() {
		return fromCompanyName;
	}

	public void setFromCompanyName(String fromCompanyName) {
		this.fromCompanyName = fromCompanyName;
	}

	public Double getSettleWeight() {
		return settleWeight;
	}

	public void setSettleWeight(Double settleWeight) {
		this.settleWeight = settleWeight;
	}

	

	public Double getLgPresentTotalFreight() {
		return lgPresentTotalFreight;
	}

	public void setLgPresentTotalFreight(Double lgPresentTotalFreight) {
		this.lgPresentTotalFreight = lgPresentTotalFreight;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getIsAbnormal() {
		return isAbnormal;
	}

	public void setIsAbnormal(String isAbnormal) {
		this.isAbnormal = isAbnormal;
	}

	public Integer getIsConfirmed() {
		return isConfirmed;
	}

	public void setIsConfirmed(Integer isConfirmed) {
		this.isConfirmed = isConfirmed;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public String getSignTime() {
		return signTime;
	}

	public void setSignTime(String signTime) {
		this.signTime = signTime;
	}

	public String getPresentFreight() {
		return presentFreight;
	}



	
	
}
