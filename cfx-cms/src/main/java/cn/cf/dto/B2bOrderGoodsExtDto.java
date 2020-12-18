package cn.cf.dto;

import cn.cf.entity.CfGoods;
import cn.cf.entity.SxGoods;

public class B2bOrderGoodsExtDto extends B2bOrderGoodsDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5187059530201630829L;
	
	private String storeName;

	private Double originalTotalPrice;
	private Double presentTotalPrice;
	private String accountPk;
	
	private CfGoods cfGoods;
	private SxGoods sxGoods;

	private String block;
	
	private Integer flag ;//1纱线订单，2.营销中心
	private  String  stampDutyName ;//是否印花税

	
	private Integer economicsGoodsType;
	public String getBlock() {
		return block;
	}

	public void setBlock(String block) {
		this.block = block;
	}

	public SxGoods getSxGoods() {
		return sxGoods;
	}

	public void setSxGoods(SxGoods sxGoods) {
		this.sxGoods = sxGoods;
	}

	public CfGoods getCfGoods() {
		return cfGoods;
	}

	public void setCfGoods(CfGoods cfGoods) {
		this.cfGoods = cfGoods;
	}

	public String getAccountPk() {
		return accountPk;
	}

	public void setAccountPk(String accountPk) {
		this.accountPk = accountPk;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public Double getOriginalTotalPrice() {
		return originalTotalPrice;
	}

	public void setOriginalTotalPrice(Double originalTotalPrice) {
		this.originalTotalPrice = originalTotalPrice;
	}

	public Double getPresentTotalPrice() {
		return presentTotalPrice;
	}

	public void setPresentTotalPrice(Double presentTotalPrice) {
		this.presentTotalPrice = presentTotalPrice;
	}

	public String getStampDutyName() {
		return stampDutyName;
	}

	public void setStampDutyName(String stampDutyName) {
		this.stampDutyName = stampDutyName;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public Integer getEconomicsGoodsType() {
		return economicsGoodsType;
	}

	public void setEconomicsGoodsType(Integer economicsGoodsType) {
		this.economicsGoodsType = economicsGoodsType;
	}
 
}
