package cn.cf.entity;

import java.io.Serializable;

/**
 * 
 * @author xht
 * @describe 会员分贝相关
 * 2018年5月22日
 */
public class MangoMemberPoint implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 维度类型
	 */
	private String dimenType;
	/**
	 * 基础值/经验
	 */
	private Double expValue;
	/**
	 * 基础值/纤贝
	 */
	private Double fiberValue;
	private String memberPk;
	private String companyPk;
	private String parentCompanyPk;
	private String insertTime;
	private String orderNumber;
	private Integer flag;//采购商1 供应商2

	
	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public String getParentCompanyPk() {
		return parentCompanyPk;
	}

	public void setParentCompanyPk(String parentCompanyPk) {
		this.parentCompanyPk = parentCompanyPk;
	}

	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getDimenType() {
		return dimenType;
	}
	public void setDimenType(String dimenType) {
		this.dimenType = dimenType;
	}
	
	public Double getExpValue() {
		return expValue;
	}
	public void setExpValue(Double expValue) {
		this.expValue = expValue;
	}
	public Double getFiberValue() {
		return fiberValue;
	}
	public void setFiberValue(Double fiberValue) {
		this.fiberValue = fiberValue;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getMemberPk() {
		return memberPk;
	}
	public void setMemberPk(String memberPk) {
		this.memberPk = memberPk;
	}
	public String getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}
	public String getCompanyPk() {
		return companyPk;
	}
	public void setCompanyPk(String companyPk) {
		this.companyPk = companyPk;
	}
	
	

}
