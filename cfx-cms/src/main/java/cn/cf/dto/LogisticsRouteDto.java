package cn.cf.dto;

import java.util.Date;
import java.util.List;

public class LogisticsRouteDto implements java.io.Serializable{

    private static final long serialVersionUID = 5454155825314635342L;
    private String pk;
    private String name;
    private String companyPk;
    private String companyName;
    private Integer status;
    private String productPk;
    private String productName;
    private String gradePk;
    private String gradeName;
    private String fromProvicePk;
    private String fromProviceName;
    private String fromCityPk;
    private String fromCityName;
    private String fromAreaName;
    private String fromAreaPk;
    private String fromTownPk;
    private String fromTownName;
    private String toProvicePk;
    private String toProviceName;
    private String toCityName;
    private String toCityPk;
    private String toAreaName;
    private String toAreaPk;
    private String toTownPk;
    private String toTownName;
    private Double basicPrice;//内部结算价
    private Double leastWeight;
    private Double freight;//对外结算运费
    private Integer isDelete;
    private Date insertTime;
    private Date updateTime;
    private Integer companyIsDelete;
    private Integer companyIsVisable;
    private Integer companyAuditStatus;
    private Double weight;//货物实际重量
    
//	private Double settleWeight;
//	private Double presentTotalFreight;
//

    private List<LogisticsLinePriceDto> list;
    public String getPk() {
        return pk;
    }
    public void setPk(String pk) {
        this.pk = pk;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCompanyPk() {
        return companyPk;
    }
    public void setCompanyPk(String companyPk) {
        this.companyPk = companyPk;
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    public String getProductPk() {
        return productPk;
    }
    public void setProductPk(String productPk) {
        this.productPk = productPk;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public String getGradePk() {
        return gradePk;
    }
    public void setGradePk(String gradePk) {
        this.gradePk = gradePk;
    }
    public String getGradeName() {
        return gradeName;
    }
    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }
    public String getFromProvicePk() {
        return fromProvicePk;
    }
    public void setFromProvicePk(String fromProvicePk) {
        this.fromProvicePk = fromProvicePk;
    }
    public String getFromProviceName() {
        return fromProviceName;
    }
    public void setFromProviceName(String fromProviceName) {
        this.fromProviceName = fromProviceName;
    }
    public String getFromCityPk() {
        return fromCityPk;
    }
    public void setFromCityPk(String fromCityPk) {
        this.fromCityPk = fromCityPk;
    }
    public String getFromCityName() {
        return fromCityName;
    }
    public void setFromCityName(String fromCityName) {
        this.fromCityName = fromCityName;
    }
    public String getFromAreaName() {
        return fromAreaName;
    }
    public void setFromAreaName(String fromAreaName) {
        this.fromAreaName = fromAreaName;
    }
    public String getFromAreaPk() {
        return fromAreaPk;
    }
    public void setFromAreaPk(String fromAreaPk) {
        this.fromAreaPk = fromAreaPk;
    }
    public String getToProvicePk() {
        return toProvicePk;
    }
    public void setToProvicePk(String toProvicePk) {
        this.toProvicePk = toProvicePk;
    }
    public String getToProviceName() {
        return toProviceName;
    }
    public void setToProviceName(String toProviceName) {
        this.toProviceName = toProviceName;
    }
    public String getToCityName() {
        return toCityName;
    }
    public void setToCityName(String toCityName) {
        this.toCityName = toCityName;
    }
    public String getToCityPk() {
        return toCityPk;
    }
    public void setToCityPk(String toCityPk) {
        this.toCityPk = toCityPk;
    }
    public String getToAreaName() {
        return toAreaName;
    }
    public void setToAreaName(String toAreaName) {
        this.toAreaName = toAreaName;
    }
    public String getToAreaPk() {
        return toAreaPk;
    }
    public void setToAreaPk(String toAreaPk) {
        this.toAreaPk = toAreaPk;
    }
    public static long getSerialversionuid() {
        return serialVersionUID;
    }
    public List<LogisticsLinePriceDto> getList() {
        return list;
    }
    public void setList(List<LogisticsLinePriceDto> list) {
        this.list = list;
    }

    public Double getBasicPrice() {
        return basicPrice;
    }
    public void setBasicPrice(Double basicPrice) {
        this.basicPrice = basicPrice;
    }
    public Double getLeastWeight() {
        return leastWeight;
    }
    public void setLeastWeight(Double leastWeight) {
        this.leastWeight = leastWeight;
    }
    public Double getFreight() {
        return freight;
    }
    public void setFreight(Double freight) {
        this.freight = freight;
    }
    public String getCompanyName() {
        return companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    public Integer getIsDelete() {
        return isDelete;
    }
    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }
    public Date getInsertTime() {
        return insertTime;
    }
    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }
    public Date getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    public String getFromTownPk() {
        return fromTownPk;
    }
    public void setFromTownPk(String fromTownPk) {
        this.fromTownPk = fromTownPk;
    }
    public String getFromTownName() {
        return fromTownName;
    }
    public void setFromTownName(String fromTownName) {
        this.fromTownName = fromTownName;
    }
    public String getToTownPk() {
        return toTownPk;
    }
    public void setToTownPk(String toTownPk) {
        this.toTownPk = toTownPk;
    }
    public String getToTownName() {
        return toTownName;
    }
    public void setToTownName(String toTownName) {
        this.toTownName = toTownName;
    }
    public Integer getCompanyIsDelete() {
        return companyIsDelete;
    }
    public void setCompanyIsDelete(Integer companyIsDelete) {
        this.companyIsDelete = companyIsDelete;
    }
    public Integer getCompanyIsVisable() {
        return companyIsVisable;
    }
    public void setCompanyIsVisable(Integer companyIsVisable) {
        this.companyIsVisable = companyIsVisable;
    }
    public Integer getCompanyAuditStatus() {
        return companyAuditStatus;
    }
    public void setCompanyAuditStatus(Integer companyAuditStatus) {
        this.companyAuditStatus = companyAuditStatus;
    }
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}


}
