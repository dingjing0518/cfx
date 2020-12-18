package cn.cf.dto;


public class B2bAuctionOfferDtoEx extends B2bAuctionOfferDto {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String supplierName;
	
	private Integer counts;
	private Integer bidCounts;
	private Integer losebidCounts;
	private Integer overdueCounts;
	
	private String goodsPk;
	private String brandName;
	private Double startingPrice;
	private Double maximumPrice;
	private Double tareWeight;
	private Integer totalBoxes;
	private Double totalWeight; 
	private Integer restboxes;
	private Double restweight;
	private Integer acStatus;
	private String activityTime;
	private String startTime;
	private String endTime;
	private String supplierContacts;
	private String supplierContactsTel;
	private String statusName;
	private Integer orderStatus;//订单提交状态
	
	private String packNumber;
	private String distinctNumber;
	private Double dealPrice;//成交价
	private String bidStatusName;//中标状态
	private Double tonPrice;
	private String acStatusName;
	private String goodsInfo;
	private String block;
	
 
	public String getBlock() {
		return block;
	}
	public void setBlock(String block) {
		this.block = block;
	}
	public Integer getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public Integer getCounts() {
		return counts;
	}
	public void setCounts(Integer counts) {
		this.counts = counts;
	}
	public Integer getBidCounts() {
		return bidCounts;
	}
	public void setBidCounts(Integer bidCounts) {
		this.bidCounts = bidCounts;
	}
	public Integer getLosebidCounts() {
		return losebidCounts;
	}
	public void setLosebidCounts(Integer losebidCounts) {
		this.losebidCounts = losebidCounts;
	}
	public Integer getOverdueCounts() {
		return overdueCounts;
	}
	public void setOverdueCounts(Integer overdueCounts) {
		this.overdueCounts = overdueCounts;
	}
	public String getGoodsPk() {
		return goodsPk;
	}
	public void setGoodsPk(String goodsPk) {
		this.goodsPk = goodsPk;
	}
	 
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public Double getStartingPrice() {
		return startingPrice;
	}
	public void setStartingPrice(Double startingPrice) {
		this.startingPrice = startingPrice;
	}
	public Double getMaximumPrice() {
		return maximumPrice;
	}
	public void setMaximumPrice(Double maximumPrice) {
		this.maximumPrice = maximumPrice;
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
	public Integer getRestboxes() {
		return restboxes;
	}
	public void setRestboxes(Integer restboxes) {
		this.restboxes = restboxes;
	}
	public Double getRestweight() {
		return restweight;
	}
	public void setRestweight(Double restweight) {
		this.restweight = restweight;
	}
	public Integer getAcStatus() {
		return acStatus;
	}
	public void setAcStatus(Integer acStatus) {
		this.acStatus = acStatus;
	}
	public String getActivityTime() {
		return activityTime;
	}
	public void setActivityTime(String activityTime) {
		this.activityTime = activityTime;
	}
	public String getStartTime() {
		return startTime;
	}
	public String getGoodsInfo() {
		return goodsInfo;
	}
	public void setGoodsInfo(String goodsInfo) {
		this.goodsInfo = goodsInfo;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getSupplierContacts() {
		return supplierContacts;
	}
	public void setSupplierContacts(String supplierContacts) {
		this.supplierContacts = supplierContacts;
	}
	public String getSupplierContactsTel() {
		return supplierContactsTel;
	}
	public void setSupplierContactsTel(String supplierContactsTel) {
		this.supplierContactsTel = supplierContactsTel;
	}
	public String getPackNumber() {
		return packNumber;
	}
	public void setPackNumber(String packNumber) {
		this.packNumber = packNumber;
	}
	public Double getDealPrice() {
		return dealPrice;
	}
	public void setDealPrice(Double dealPrice) {
		this.dealPrice = dealPrice;
	}
	public String getBidStatusName() {
		return bidStatusName;
	}
	public void setBidStatusName(String bidStatusName) {
		this.bidStatusName = bidStatusName;
	}
	public Double getTonPrice() {
		return tonPrice;
	}
	public void setTonPrice(Double tonPrice) {
		this.tonPrice = tonPrice;
	}
	public String getAcStatusName() {
		return acStatusName;
	}
	public void setAcStatusName(String acStatusName) {
		this.acStatusName = acStatusName;
	}
	public String getDistinctNumber() {
		return distinctNumber;
	}
	public void setDistinctNumber(String distinctNumber) {
		this.distinctNumber = distinctNumber;
	}
	
	

}
