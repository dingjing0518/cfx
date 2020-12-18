package cn.cf.dto;

import java.util.Date;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class B2bBillCustomerExtDto extends B2bBillCustomerDto{

	private String goodsName;

	private String accountPk;

	private String colCompanyName;

	private String colApplicants;

	private String colContactsTel;

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getAccountPk() {
		return accountPk;
	}

	public void setAccountPk(String accountPk) {
		this.accountPk = accountPk;
	}

	public String getColCompanyName() {
		return colCompanyName;
	}

	public void setColCompanyName(String colCompanyName) {
		this.colCompanyName = colCompanyName;
	}

	public String getColApplicants() {
		return colApplicants;
	}

	public void setColApplicants(String colApplicants) {
		this.colApplicants = colApplicants;
	}

	public String getColContactsTel() {
		return colContactsTel;
	}

	public void setColContactsTel(String colContactsTel) {
		this.colContactsTel = colContactsTel;
	}
}