package cn.cf.dto;


/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class CustomerInfoDto extends BaseDTO implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	private String companyId;
	private String companyName;
	private String regPhone;
	private String regAddress;
	private String provinceName;
	private String cityName;
	private String areaName;
	private String taxidNumber;
	private String bankName;
	private String bankAccount;
	private Integer source;
	private String blUrl;
	private String organizationCode;
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getRegPhone() {
		return regPhone;
	}
	public void setRegPhone(String regPhone) {
		this.regPhone = regPhone;
	}
	public String getRegAddress() {
		return regAddress;
	}
	public void setRegAddress(String regAddress) {
		this.regAddress = regAddress;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getTaxidNumber() {
		return taxidNumber;
	}
	public void setTaxidNumber(String taxidNumber) {
		this.taxidNumber = taxidNumber;
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
	public Integer getSource() {
		return source;
	}
	public void setSource(Integer source) {
		this.source = source;
	}
	public String getBlUrl() {
		return blUrl;
	}
	public void setBlUrl(String blUrl) {
		this.blUrl = blUrl;
	}
	public String getOrganizationCode() {
		return organizationCode;
	}
	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}
	
	
}