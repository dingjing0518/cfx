package cn.cf.dto;

import java.util.List;

public class SysSupplierRecommedDtoEx extends SysSupplierRecommedDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4741933723156907453L;
	private String provinceName;
	private String cityName;
	private String areaName;

	public String getContactsTel() {
		return contactsTel;
	}

	public void setContactsTel(String contactsTel) {
		this.contactsTel = contactsTel;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	private String storePk;
	private  String companyPk;
	private  String contactsTel;
	private  String contacts;

	public String getCompanyPk() {
		return companyPk;
	}

	public void setCompanyPk(String companyPk) {
		this.companyPk = companyPk;
	}

	public String getShopNotices() {
		return shopNotices;
	}

	public void setShopNotices(String shopNotices) {
		this.shopNotices = shopNotices;
	}

	private Integer isCollection = 1;//1未收藏 2已收藏
	private  String shopNotices;
	private List<SxHomeSecondnavigationDtoEx> sxHomeSecondnavigationDtoExes;
	private List<SysSupplierRecommedDto> sysSupplierRecommedDtos;

	public List<SysSupplierRecommedDto> getSysSupplierRecommedDtos() {
		return sysSupplierRecommedDtos;
	}

	public void setSysSupplierRecommedDtos(List<SysSupplierRecommedDto> sysSupplierRecommedDtos) {
		this.sysSupplierRecommedDtos = sysSupplierRecommedDtos;
	}

	public List<SxHomeSecondnavigationDtoEx> getSxHomeSecondnavigationDtoExes() {
		return sxHomeSecondnavigationDtoExes;
	}

	public void setSxHomeSecondnavigationDtoExes(List<SxHomeSecondnavigationDtoEx> sxHomeSecondnavigationDtoExes) {
		this.sxHomeSecondnavigationDtoExes = sxHomeSecondnavigationDtoExes;
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
	public String getStorePk() {
		return storePk;
	}
	public void setStorePk(String storePk) {
		this.storePk = storePk;
	}
	public Integer getIsCollection() {
		return isCollection;
	}
	public void setIsCollection(Integer isCollection) {
		this.isCollection = isCollection;
	}
	
	
	
	
}
