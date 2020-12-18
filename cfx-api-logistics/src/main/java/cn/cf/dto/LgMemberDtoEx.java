package cn.cf.dto;

import java.util.Date;
import java.util.List;

public class LgMemberDtoEx extends LgMemberDto {

	private static final long serialVersionUID = 1L;
	private String name;
	private String city;
	private String cityName;
	private String area;
	private String areaName;
	private Date auditTime;
	private String province;
	private String provinceName;
	private String contacts;
	private String contactsTel;
	private String businessLicense;
	private String blUrl;

	private String roleName;// 业务员所属角色
	private String rolePk;

	private List<LgRoleDtoEx> list;

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
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

	public String getBusinessLicense() {
		return businessLicense;
	}

	public void setBusinessLicense(String businessLicense) {
		this.businessLicense = businessLicense;
	}

	public String getBlUrl() {
		return blUrl;
	}

	public void setBlUrl(String blUrl) {
		this.blUrl = blUrl;
	}

	public String getRolePk() {
		return rolePk;
	}

	public void setRolePk(String rolePk) {
		this.rolePk = rolePk;
	}

	public List<LgRoleDtoEx> getList() {
		return list;
	}

	public void setList(List<LgRoleDtoEx> list) {
		this.list = list;
	}

}
