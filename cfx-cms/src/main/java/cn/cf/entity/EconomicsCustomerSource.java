package cn.cf.entity;

import java.io.Serializable;

public class EconomicsCustomerSource implements Serializable{

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6077959319071646815L;
	public EconomicsCustomerSource(String pk, String sourceName) {
		this.pk = pk;
		this.sourceName = sourceName;
	}
	private String pk;
	private String sourceName;
	public String getPk() {
		return pk;
	}
	public void setPk(String pk) {
		this.pk = pk;
	}
	public String getSourceName() {
		return sourceName;
	}
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	
	
	
}
