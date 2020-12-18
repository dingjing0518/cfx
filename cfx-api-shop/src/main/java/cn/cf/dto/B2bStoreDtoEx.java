package cn.cf.dto;

public class B2bStoreDtoEx extends B2bStoreDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String headPortrait;
	private String contacts;
	private String contactsTel;
	private String provinceName;// 公司地址
	private String cityName;
	private String areaName;
	private Integer isCollection;//1未收藏 2已收藏
	private Integer isOperative;
	

	public Integer getIsCollection() {
		return isCollection;
	}

	public void setIsCollection(Integer isCollection) {
		this.isCollection = isCollection;
	}

	public String getHeadPortrait() {
		return headPortrait;
	}

	public void setHeadPortrait(String headPortrait) {
		this.headPortrait = headPortrait;
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

	public Integer getIsOperative() {
		return isOperative;
	}

	public void setIsOperative(Integer isOperative) {
		this.isOperative = isOperative;
	}
	
}
