package cn.cf.model;

import java.util.Date;

/**
 *
 * @author
 * @version 1.0
 * @since 1.0
 * */
public class B2bSxCertificate implements java.io.Serializable{
    private static final long serialVersionUID = 1L;


    private String pk;
    private String name;
    private String storePk;
    private  String companyPk;
    private  String companyName;
    private Date insertTime;
    private Integer isDelete;

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

    public String getStorePk() {
        return storePk;
    }

    public void setStorePk(String storePk) {
        this.storePk = storePk;
    }

    public String getCompanyPk() {
        return companyPk;
    }

    public void setCompanyPk(String companyPk) {
        this.companyPk = companyPk;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

}