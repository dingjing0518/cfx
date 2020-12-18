package cn.cf.dto;


import java.io.Serializable;
import java.util.Date;

public class   LgOrderDetailDto implements Serializable {
    private static final long serialVersionUID = 5454155825314635342L;
    private String pk;
    private String orderPk;// 订单编号

    private Integer orderStatus;// 订单状态
    private Date orderTime;
    private String logisticsCompanyName;// 物流承运商
    private String fromCompanyName;// 提货公司
    private String fromAddress;// 提货地址
    private String fromContacts;// 提货公司联系人
    private String fromContactsTel;//
    private String toAddress;// 收货公司地址
    private String toContacts;// 收货公司联系人
    private String toContactsTel;
    private String toCompanyName;
    private String orderStatusName;
    private String goodsPk;
    private String deliveryPk;
    private String deliveryNumber;//提货单号
    private String productName;
    private String varietiesName;
    private String seedvarietyName;// 子品种
    private String specName;
    private String seriesName;// 规格系列
    private String gradeName;
    private String batchNumber;// 批号
    private Integer boxes;
    private Double weight;
    private Double originalFreight;
    private Double basisLinePrice;// 运费对内单价
    private Double  presentFreight;//运费对外单价
    private Double originalTotalFreight;//运费对外原总价
    private Double presentTotalFreight;// 运输总价
    private String goodsName;
    private String paymentName;
    private String abnormalRemark;
    private String remark;
    private String invoiceTitle; // 发票抬头
    private String taxidNumber;// 纳税人识别号
    private String regAddress;// 注册地区
    private String bankName;// 开户行
    private String bankAccount;// 银行账号
    private String memberPk;//下单用户
    private String logisticsCompanyPk;
    private String supplierName;
    private String unit;//单位(1:箱 2:锭 3:件 4:粒)
    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getOrderPk() {
        return orderPk;
    }

    public void setOrderPk(String orderPk) {
        this.orderPk = orderPk;
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

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public String getLogisticsCompanyName() {
        return logisticsCompanyName;
    }

    public void setLogisticsCompanyName(String logisticsCompanyName) {
        this.logisticsCompanyName = logisticsCompanyName;
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

    public String getOrderStatusName() {
        return orderStatusName;
    }

    public void setOrderStatusName(String orderStatusName) {
        this.orderStatusName = orderStatusName;
    }

    public String getGoodsPk() {
        return goodsPk;
    }

    public void setGoodsPk(String goodsPk) {
        this.goodsPk = goodsPk;
    }

    public String getDeliveryPk() {
        return deliveryPk;
    }

    public void setDeliveryPk(String deliveryPk) {
        this.deliveryPk = deliveryPk;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getVarietiesName() {
        return varietiesName;
    }

    public void setVarietiesName(String varietiesName) {
        this.varietiesName = varietiesName;
    }

    public String getSeedvarietyName() {
        return seedvarietyName;
    }

    public void setSeedvarietyName(String seedvarietyName) {
        this.seedvarietyName = seedvarietyName;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public Integer getBoxes() {
        return boxes;
    }

    public void setBoxes(Integer boxes) {
        this.boxes = boxes;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getOriginalFreight() {
        return originalFreight;
    }

    public void setOriginalFreight(Double originalFreight) {
        this.originalFreight = originalFreight;
    }


    public Double getBasisLinePrice() {
        return basisLinePrice;
    }

    public void setBasisLinePrice(Double basisLinePrice) {
        this.basisLinePrice = basisLinePrice;
    }

    public Double getOriginalTotalFreight() {
        return originalTotalFreight;
    }

    public void setOriginalTotalFreight(Double originalTotalFreight) {
        this.originalTotalFreight = originalTotalFreight;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }

    public String getAbnormalRemark() {
        return abnormalRemark;
    }

    public void setAbnormalRemark(String abnormalRemark) {
        this.abnormalRemark = abnormalRemark;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    public String getTaxidNumber() {
        return taxidNumber;
    }

    public void setTaxidNumber(String taxidNumber) {
        this.taxidNumber = taxidNumber;
    }

    public String getRegAddress() {
        return regAddress;
    }

    public void setRegAddress(String regAddress) {
        this.regAddress = regAddress;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getToCompanyName() {
        return toCompanyName;
    }

    public void setToCompanyName(String toCompanyName) {
        this.toCompanyName = toCompanyName;
    }

    public String getMemberPk() {
        return memberPk;
    }

    public void setMemberPk(String memberPk) {
        this.memberPk = memberPk;
    }

    public String getLogisticsCompanyPk() {
        return logisticsCompanyPk;
    }

    public void setLogisticsCompanyPk(String logisticsCompanyPk) {
        this.logisticsCompanyPk = logisticsCompanyPk;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getDeliveryNumber() {
        return deliveryNumber;
    }

    public void setDeliveryNumber(String deliveryNumber) {
        this.deliveryNumber = deliveryNumber;
    }

    public Double getPresentFreight() {
        return presentFreight;
    }

    public void setPresentFreight(Double presentFreight) {
        this.presentFreight = presentFreight;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }



}