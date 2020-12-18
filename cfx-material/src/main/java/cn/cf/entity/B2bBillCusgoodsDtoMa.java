package cn.cf.entity;

import cn.cf.dto.B2bBillCusgoodsDto;

public class B2bBillCusgoodsDtoMa extends B2bBillCusgoodsDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String bankAccount;
	private String bankName;
	private String imgUrl;
	public String getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	
	
	
}
