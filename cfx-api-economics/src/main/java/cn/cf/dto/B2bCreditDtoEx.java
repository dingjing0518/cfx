package cn.cf.dto;

import java.util.List;

import cn.cf.entity.B2bCreditGoodsDtoMa;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class B2bCreditDtoEx extends B2bCreditDto {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer isAmount;//1已授额 2未授额
	private String beInvitedCode;//受邀码
	private String productPks;
	private String goodsNames;
	private List<B2bCreditGoodsDtoMa> creditGoods;
	public Integer getIsAmount() {
		return isAmount;
	}
	public void setIsAmount(Integer isAmount) {
		this.isAmount = isAmount;
	}
	public String getBeInvitedCode() {
		return beInvitedCode;
	}
	public void setBeInvitedCode(String beInvitedCode) {
		this.beInvitedCode = beInvitedCode;
	}
	public String getProductPks() {
		return productPks;
	}
	public void setProductPks(String productPks) {
		this.productPks = productPks;
	}
	public List<B2bCreditGoodsDtoMa> getCreditGoods() {
		return creditGoods;
	}
	public void setCreditGoods(List<B2bCreditGoodsDtoMa> creditGoods) {
		this.creditGoods = creditGoods;
	}
	public String getGoodsNames() {
		return goodsNames;
	}
	public void setGoodsNames(String goodsNames) {
		this.goodsNames = goodsNames;
	}
	
}