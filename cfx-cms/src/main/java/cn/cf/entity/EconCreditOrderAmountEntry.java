package cn.cf.entity;

/**
 * 供应商金融产品交易每月月份合计;
 * 
 * @author hxh
 *
 */
public class EconCreditOrderAmountEntry {
	
	private String storeName;
	
	private String storePk;
	
	private Double amounts;
	
	private Integer counts;
	
	private Integer goodsType;

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getStorePk() {
		return storePk;
	}

	public void setStorePk(String storePk) {
		this.storePk = storePk;
	}

	public Double getAmounts() {
		return amounts;
	}

	public void setAmounts(Double amounts) {
		this.amounts = amounts;
	}

	public Integer getCounts() {
		return counts;
	}

	public void setCounts(Integer counts) {
		this.counts = counts;
	}

	public Integer getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(Integer goodsType) {
		this.goodsType = goodsType;
	}
	
}
