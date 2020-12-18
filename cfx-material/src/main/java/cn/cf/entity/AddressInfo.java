package cn.cf.entity;

public class AddressInfo {

	private String addressPk;
	private String provinceName;
	private String cityName;
	private String areaName;
	private String townName;
	private String address;
	private String contacts;
	private String contactsTel;
	private String signingCompany;
	
	public AddressInfo() {
		// TODO Auto-generated constructor stub
	}
	
	
	public AddressInfo(String addressPk, String provinceName, String cityName,
			String areaName, String townName, String address, String contacts,
			String contactsTel, String signingCompany) {
		super();
		this.addressPk = addressPk;
		this.provinceName = provinceName;
		this.cityName = cityName;
		this.areaName = areaName;
		this.townName = townName;
		this.address = address;
		this.contacts = contacts;
		this.contactsTel = contactsTel;
		this.signingCompany = signingCompany;
	}


	public String getAddressPk() {
		return addressPk;
	}
	public void setAddressPk(String addressPk) {
		this.addressPk = addressPk;
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
	public String getTownName() {
		return townName;
	}
	public void setTownName(String townName) {
		this.townName = townName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
	public String getSigningCompany() {
		return signingCompany;
	}
	public void setSigningCompany(String signingCompany) {
		this.signingCompany = signingCompany;
	}
	
	
}
