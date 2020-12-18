package cn.cf.entity;

import cn.cf.dto.SysRegionsDto;

import java.util.List;

public class B2bInvoiceEntity {


    private String companyPk;
    private String companyName;
    private String taxidNumber;
    private String regPhone;
    private String bankAccount;
    private String bankName;
    private String regAddress;
    private String province;
    private String provinceName;
    private String city;
    private String cityName;
    private String area;
    private String areaName;
    private String success;
    private List<SysRegionsDto> provinceList;

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

    public String getTaxidNumber() {
        return taxidNumber;
    }

    public void setTaxidNumber(String taxidNumber) {
        this.taxidNumber = taxidNumber;
    }

    public String getRegPhone() {
        return regPhone;
    }

    public void setRegPhone(String regPhone) {
        this.regPhone = regPhone;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getRegAddress() {
        return regAddress;
    }

    public void setRegAddress(String regAddress) {
        this.regAddress = regAddress;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<SysRegionsDto> getProvinceList() {
        return provinceList;
    }

    public void setProvinceList(List<SysRegionsDto> provinceList) {
        this.provinceList = provinceList;
    }
}
