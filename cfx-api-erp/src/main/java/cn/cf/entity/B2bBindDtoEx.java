package cn.cf.entity;

import java.util.Set;

import cn.cf.dto.B2bBindDto;
import cn.cf.dto.B2bBindGoodsDtoEx;

public class B2bBindDtoEx extends B2bBindDto{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private java.lang.String bindProcuctID;
	private java.lang.Integer bindProcuctCount;
	private java.lang.String bindProcuctDetails;
	private java.lang.Double bindProcuctPrice;
	private Set<B2bBindGoodsDtoEx> items;
	


	
	public Set<B2bBindGoodsDtoEx> getItems() {
		return items;
	}

	public void setItems(Set<B2bBindGoodsDtoEx> items) {
		this.items = items;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public java.lang.String getBindProcuctID() {
		return bindProcuctID;
	}

	public void setBindProcuctID(java.lang.String bindProcuctID) {
		this.bindProcuctID = bindProcuctID;
	}

	public java.lang.Integer getBindProcuctCount() {
		return bindProcuctCount;
	}

	public void setBindProcuctCount(java.lang.Integer bindProcuctCount) {
		this.bindProcuctCount = bindProcuctCount;
	}

	public java.lang.String getBindProcuctDetails() {
		return bindProcuctDetails;
	}

	public void setBindProcuctDetails(java.lang.String bindProcuctDetails) {
		this.bindProcuctDetails = bindProcuctDetails;
	}

	public java.lang.Double getBindProcuctPrice() {
		return bindProcuctPrice;
	}

	public void setBindProcuctPrice(java.lang.Double bindProcuctPrice) {
		this.bindProcuctPrice = bindProcuctPrice;
	}
	
	
}
