package cn.cf.entity;

import javax.persistence.Id;
import java.math.BigDecimal;

public class ProductPriceIndexEntry {

    @Id
    private String id;

    private String loanNumber;

    private String repaymentTime;

    private String purchaserName;

    private String contacts;

    private String contactsTel;

    private Integer loanStatus;

    private String loanStatusName;

    private BigDecimal weightShipped;

    private BigDecimal presentPrice;//商品成交单价

    private String productPk;

    private String goodsInfo;

    private String childOrderNumber;

    private Integer priceIndex;//成交价格指数

    private BigDecimal depositMount;//已缴纳保证金

    private Integer nowPriceIndex;//当日价格指数

    private Integer isConfirm;//1未确认，2已确认

    private String isConfirmName;

    private String repaymentTimeStart;//查询条件

    private String repaymentTimeEnd;//查询条件

    private String insertTime;

    private String updateTime;

    private double riseAndFall;//涨跌幅

    private double dueofpayMount;//本次应缴保证金

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoanNumber() {
        return loanNumber;
    }

    public void setLoanNumber(String loanNumber) {
        this.loanNumber = loanNumber;
    }

    public String getRepaymentTime() {
        return repaymentTime;
    }

    public void setRepaymentTime(String repaymentTime) {
        this.repaymentTime = repaymentTime;
    }

    public String getPurchaserName() {
        return purchaserName;
    }

    public void setPurchaserName(String purchaserName) {
        this.purchaserName = purchaserName;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getContactsTel() {
        return contactsTel;
    }

    public void setContactsTel(String contactsTel) {
        this.contactsTel = contactsTel;
    }

    public Integer getLoanStatus() {
        return loanStatus;
    }

    public void setLoanStatus(Integer loanStatus) {
        this.loanStatus = loanStatus;
    }

    public BigDecimal getWeightShipped() {
        return weightShipped;
    }

    public void setWeightShipped(BigDecimal weightShipped) {
        this.weightShipped = weightShipped;
    }

    public String getProductPk() {
        return productPk;
    }

    public void setProductPk(String productPk) {
        this.productPk = productPk;
    }

    public String getGoodsInfo() {
        return goodsInfo;
    }

    public void setGoodsInfo(String goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

    public Integer getPriceIndex() {
        return priceIndex;
    }

    public void setPriceIndex(Integer priceIndex) {
        this.priceIndex = priceIndex;
    }

    public Integer getIsConfirm() {
        return isConfirm;
    }

    public void setIsConfirm(Integer isConfirm) {
        this.isConfirm = isConfirm;
    }

    public String getRepaymentTimeStart() {
        return repaymentTimeStart;
    }

    public void setRepaymentTimeStart(String repaymentTimeStart) {
        this.repaymentTimeStart = repaymentTimeStart;
    }

    public String getRepaymentTimeEnd() {
        return repaymentTimeEnd;
    }

    public void setRepaymentTimeEnd(String repaymentTimeEnd) {
        this.repaymentTimeEnd = repaymentTimeEnd;
    }

    public String getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(String insertTime) {
        this.insertTime = insertTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getNowPriceIndex() {
        return nowPriceIndex;
    }

    public void setNowPriceIndex(Integer nowPriceIndex) {
        this.nowPriceIndex = nowPriceIndex;
    }

    public BigDecimal getPresentPrice() {
        return presentPrice;
    }

    public void setPresentPrice(BigDecimal presentPrice) {
        this.presentPrice = presentPrice;
    }

    public BigDecimal getDepositMount() {
        return depositMount;
    }

    public void setDepositMount(BigDecimal depositMount) {
        this.depositMount = depositMount;
    }

    public double getRiseAndFall() {
        return riseAndFall;
    }

    public void setRiseAndFall(double riseAndFall) {
        this.riseAndFall = riseAndFall;
    }

    public double getDueofpayMount() {
        return dueofpayMount;
    }

    public void setDueofpayMount(double dueofpayMount) {
        this.dueofpayMount = dueofpayMount;
    }

    public String getLoanStatusName() {
        return loanStatusName;
    }

    public void setLoanStatusName(String loanStatusName) {
        this.loanStatusName = loanStatusName;
    }

    public String getIsConfirmName() {
        return isConfirmName;
    }

    public void setIsConfirmName(String isConfirmName) {
        this.isConfirmName = isConfirmName;
    }

	public String getChildOrderNumber() {
		return childOrderNumber;
	}

	public void setChildOrderNumber(String childOrderNumber) {
		this.childOrderNumber = childOrderNumber;
	}
    
    
}
