package cn.cf.dto;


import java.io.Serializable;
import java.util.Date;

public class LogisticsLinePriceDto  implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String pk;
    private String linePk;
    private Double  fromWeight;
    private Double toWeight;
    private Double basisPrice;
    private Double salePrice;
    private Integer isDelete;
    private Date insertTime;
    private Date updateTime;
    public String getPk() {
        return pk;
    }
    public void setPk(String pk) {
        this.pk = pk;
    }
    public String getLinePk() {
        return linePk;
    }
    public void setLinePk(String linePk) {
        this.linePk = linePk;
    }
    public Double getFromWeight() {
        return fromWeight;
    }
    public void setFromWeight(Double fromWeight) {
        this.fromWeight = fromWeight;
    }
    public Double getToWeight() {
        return toWeight;
    }
    public void setToWeight(Double toWeight) {
        this.toWeight = toWeight;
    }
    public Double getBasisPrice() {
        return basisPrice;
    }
    public void setBasisPrice(Double basisPrice) {
        this.basisPrice = basisPrice;
    }
    public Double getSalePrice() {
        return salePrice;
    }
    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }
    public static long getSerialversionuid() {
        return serialVersionUID;
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
}

