package cn.cf.dto;

import java.util.List;

public class B2bCompanyDtoEx extends B2bCompanyDto{

	private static final long serialVersionUID = 1L;
	
	private String storePk;
	private String storeName;
	private String parentName;
	private List<B2bCartDto> cartList;

	public String getStorePk() {
		return storePk;
	}

	public void setStorePk(String storePk) {
		this.storePk = storePk;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public List<B2bCartDto> getCartList() {
		return cartList;
	}

	public void setCartList(List<B2bCartDto> cartList) {
		this.cartList = cartList;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
