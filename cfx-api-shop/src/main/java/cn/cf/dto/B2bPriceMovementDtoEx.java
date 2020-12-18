package cn.cf.dto;

import java.util.Date;

public class B2bPriceMovementDtoEx extends B2bPriceMovementDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String productName;
	private String varietiesName;
	private String specName;
	private String seriesName;
	private String specifications;
	private String gradeName;
	private Double tonPrice;
	private String goodsUpdateTime;
	private String goodsInfo;
	private Date platformUpdateTime;
	public String getGoodsInfo() {
		return goodsInfo;
	}
	public void setGoodsInfo(String goodsInfo) {
		this.goodsInfo = goodsInfo;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getVarietiesName() {
		return varietiesName;
	}
	public void setVarietiesName(String varietiesName) {
		this.varietiesName = varietiesName;
	}
	public String getSpecName() {
		return specName;
	}
	public void setSpecName(String specName) {
		this.specName = specName;
	}
	public String getSeriesName() {
		return seriesName;
	}
	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
	}
	public String getSpecifications() {
		return specifications;
	}
	public void setSpecifications(String specifications) {
		this.specifications = specifications;
	}
	public String getGradeName() {
		return gradeName;
	}
	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}
	
	public Double getTonPrice() {
		return tonPrice;
	}
	public void setTonPrice(Double tonPrice) {
		this.tonPrice = tonPrice;
	}
	public String getGoodsUpdateTime() {
		return goodsUpdateTime;
	}
	public void setGoodsUpdateTime(String goodsUpdateTime) {
		this.goodsUpdateTime = goodsUpdateTime;
	}

	public Date getPlatformUpdateTime() {
		return platformUpdateTime;
	}

	public void setPlatformUpdateTime(Date platformUpdateTime) {
		this.platformUpdateTime = platformUpdateTime;
	}
}
