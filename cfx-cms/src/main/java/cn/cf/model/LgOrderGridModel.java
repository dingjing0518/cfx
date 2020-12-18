package cn.cf.model;

import java.io.Serializable;

public class LgOrderGridModel implements Serializable {
    private static final long serialVersionUID = 5454155825314635342L;
    private String pk;
    /**
     * 订单号
     */
    private String orderPk;
    /**
     * 订单时间
     */
    private String orderTime;
    /**
     * 物流供应商
     */
    private String logisticsCompanyName;
    /**
     * 要求配送时间
     */
    private String arrivedTime;
    /**
     * 商品信息
     */
    private String goodsName;

    /**
     * 采购量(箱)
     */
    private Double boxes;
    /**
     * 重量
     */
    private Double weight;

    private Double settleWeight;
    /**
     * 运费单价
     */
    private Double presentFreight;
    /**
     * 运费总价
     */
    private Double originalTotalFreight;
    /**
     * 运费单价
     */
    private Double originalFreight;
    /**
     * 运费总价
     */
    private Double presentTotalFreight;
    /**
     * 订单状态
     */
    private Integer orderStatus;
    /**
     * 是否异常
     */
    private Integer isAbnormal;
    /**
     * 异常确认
     */
    private Integer isConfirmed;

    private String isConfirmedName;

    private String orderStatusName;
    /**
     * 提货公司抬头
     */
    private String fromCompanyName;
    /**
     * 提货地址
     */
    private String fromAddress;
    /**
     * 提货联系人
     */
    private String fromContacts;
    /**
     * 提货手机号
     */
    private String fromContactsTel;
    /**
     * 收货公司抬头
     */
    private String toCompanyName;

    /**
     * 配送地址
     */
    private String toAddress;

    /**
     * 配送联系人
     */
    private String toContacts;

    /**
     * 配送人手机号
     */
    private String toContactsTel;

    private String insertTime;

    private String isMatched;

    private String supplierName;

    
    public Double getOriginalTotalFreight() {
		return originalTotalFreight;
	}

	public void setOriginalTotalFreight(Double originalTotalFreight) {
		this.originalTotalFreight = originalTotalFreight;
	}

	public Double getOriginalFreight() {
		return originalFreight;
	}

	public void setOriginalFreight(Double originalFreight) {
		this.originalFreight = originalFreight;
	}

	public String getOrderPk() {
        return orderPk;
    }

    public void setOrderPk(String orderPk) {
        this.orderPk = orderPk;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getLogisticsCompanyName() {
        return logisticsCompanyName;
    }

    public void setLogisticsCompanyName(String logisticsCompanyName) {
        this.logisticsCompanyName = logisticsCompanyName;
    }

    public String getArrivedTime() {
        return arrivedTime;
    }

    public void setArrivedTime(String arrivedTime) {
        this.arrivedTime = arrivedTime;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getPresentFreight() {
        return presentFreight;
    }

    public void setPresentFreight(Double presentFreight) {
        this.presentFreight = presentFreight;
    }

    public Double getPresentTotalFreight() {
        return presentTotalFreight;
    }

    public void setPresentTotalFreight(Double presentTotalFreight) {
        this.presentTotalFreight = presentTotalFreight;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getFromCompanyName() {
        return fromCompanyName;
    }

    public void setFromCompanyName(String fromCompanyName) {
        this.fromCompanyName = fromCompanyName;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getFromContacts() {
        return fromContacts;
    }

    public void setFromContacts(String fromContacts) {
        this.fromContacts = fromContacts;
    }

    public String getFromContactsTel() {
        return fromContactsTel;
    }

    public void setFromContactsTel(String fromContactsTel) {
        this.fromContactsTel = fromContactsTel;
    }

    public String getToCompanyName() {
        return toCompanyName;
    }

    public void setToCompanyName(String toCompanyName) {
        this.toCompanyName = toCompanyName;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getToContacts() {
        return toContacts;
    }

    public void setToContacts(String toContacts) {
        this.toContacts = toContacts;
    }

    public String getToContactsTel() {
        return toContactsTel;
    }

    public void setToContactsTel(String toContactsTel) {
        this.toContactsTel = toContactsTel;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(String insertTime) {
        this.insertTime = insertTime;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getOrderStatusName() {
        return orderStatusName;
    }

    public void setOrderStatusName(String orderStatusName) {
        this.orderStatusName = orderStatusName;
    }

    public Integer getIsAbnormal() {
        return isAbnormal;
    }

    public void setIsAbnormal(Integer isAbnormal) {
        this.isAbnormal = isAbnormal;
    }

    public Integer getIsConfirmed() {
        return isConfirmed;
    }

    public void setIsConfirmed(Integer isConfirmed) {
        this.isConfirmed = isConfirmed;
    }

    public String getIsConfirmedName() {
        return isConfirmedName;
    }

    public void setIsConfirmedName(String isConfirmedName) {
        this.isConfirmedName = isConfirmedName;
    }

    public String getIsMatched() {
        return isMatched;
    }

    public void setIsMatched(String isMatched) {
        this.isMatched = isMatched;
    }

    public Double getBoxes() {
        return boxes;
    }

    public void setBoxes(Double boxes) {
        this.boxes = boxes;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Double getSettleWeight() {
        return settleWeight;
    }

    public void setSettleWeight(Double settleWeight) {
        this.settleWeight = settleWeight;
    }



}
