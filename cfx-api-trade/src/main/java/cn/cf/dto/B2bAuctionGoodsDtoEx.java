package cn.cf.dto;

import java.util.Date;

@SuppressWarnings("serial")
public class B2bAuctionGoodsDtoEx extends B2bAuctionGoodsDto{
	private String unit;
	private String unitName;
	private String brandName;
	private String goodsInfo;
	private  String block;
	private Double tareWeight;
	private Integer totalBoxes;
	private Double totalWeight;
	private Double tonPrice;
	private Double salePrice;
	private Integer acStatus;//1：未上线，2：未开始， 3：进行中， 4： 已结束
	private Integer isCollected;//是否收藏(1未收藏  2已收藏)
	private Integer isOffered;//是否出价(1未出价  2已出价)
	
	private Integer allcounts;
	private Integer notBeginningcounts;
	private Integer ongoingcounts;
	private Integer closurecounts;
	private Integer overduecounts;
	private Double startingPrice;//起拍价
	private Double currentPrice;//当前出价
	private Date currentPriceTime;//当前出价时间
	private Double dealPrice;//成交价
	private String acStatusName;//状态名称
	private String auctionPk;//竞拍场次pk
	
	private Integer isUpdown;
	private String isUpdownName;
	private String distinctNumber;
	
	
	public String getBlock() {
		return block;
	}
	public void setBlock(String block) {
		this.block = block;
	}
	public String getGoodsInfo() {
		return goodsInfo;
	}
	public void setGoodsInfo(String goodsInfo) {
		this.goodsInfo = goodsInfo;
	}
	public Integer getIsUpdown() {
		return isUpdown;
	}
	public void setIsUpdown(Integer isUpdown) {
		this.isUpdown = isUpdown;
	}
	public String getIsUpdownName() {
		return isUpdownName;
	}
	public void setIsUpdownName(String isUpdownName) {
		this.isUpdownName = isUpdownName;
	}
	public Date getCurrentPriceTime() {
		return currentPriceTime;
	}
	public void setCurrentPriceTime(Date currentPriceTime) {
		this.currentPriceTime = currentPriceTime;
	}
	public String getAuctionPk() {
		return auctionPk;
	}
	public void setAuctionPk(String auctionPk) {
		this.auctionPk = auctionPk;
	}
	public String getAcStatusName() {
		return acStatusName;
	}
	public void setAcStatusName(String acStatusName) {
		this.acStatusName = acStatusName;
	}
	public Double getTonPrice() {
		return tonPrice;
	}
	public void setTonPrice(Double tonPrice) {
		this.tonPrice = tonPrice;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	 
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	 
	public Integer getIsCollected() {
		return isCollected;
	}
	public void setIsCollected(Integer isCollected) {
		this.isCollected = isCollected;
	}
	public Double getTareWeight() {
		return tareWeight;
	}
	public void setTareWeight(Double tareWeight) {
		this.tareWeight = tareWeight;
	}
	public Integer getTotalBoxes() {
		return totalBoxes;
	}
	public void setTotalBoxes(Integer totalBoxes) {
		this.totalBoxes = totalBoxes;
	}
	public Double getTotalWeight() {
		return totalWeight;
	}
	public void setTotalWeight(Double totalWeight) {
		this.totalWeight = totalWeight;
	}
	public Integer getAcStatus() {
		return acStatus;
	}
	public void setAcStatus(Integer acStatus) {
		this.acStatus = acStatus;
	}
	public Integer getIsOffered() {
		return isOffered;
	}
	public void setIsOffered(Integer isOffered) {
		this.isOffered = isOffered;
	}
	public Integer getAllcounts() {
		return allcounts;
	}
	public void setAllcounts(Integer allcounts) {
		this.allcounts = allcounts;
	}
	public Integer getNotBeginningcounts() {
		return notBeginningcounts;
	}
	public void setNotBeginningcounts(Integer notBeginningcounts) {
		this.notBeginningcounts = notBeginningcounts;
	}
	public Integer getOngoingcounts() {
		return ongoingcounts;
	}
	public void setOngoingcounts(Integer ongoingcounts) {
		this.ongoingcounts = ongoingcounts;
	}
	public Integer getClosurecounts() {
		return closurecounts;
	}
	public void setClosurecounts(Integer closurecounts) {
		this.closurecounts = closurecounts;
	}
	public Integer getOverduecounts() {
		return overduecounts;
	}
	public void setOverduecounts(Integer overduecounts) {
		this.overduecounts = overduecounts;
	}
	public Double getStartingPrice() {
		return startingPrice;
	}
	public void setStartingPrice(Double startingPrice) {
		this.startingPrice = startingPrice;
	}
	public Double getCurrentPrice() {
		return currentPrice;
	}
	public void setCurrentPrice(Double currentPrice) {
		this.currentPrice = currentPrice;
	}
	public Double getDealPrice() {
		return dealPrice;
	}
	public void setDealPrice(Double dealPrice) {
		this.dealPrice = dealPrice;
	}
	public String getDistinctNumber() {
		return distinctNumber;
	}
	public void setDistinctNumber(String distinctNumber) {
		this.distinctNumber = distinctNumber;
	}
	public Double getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
	}
	

}
