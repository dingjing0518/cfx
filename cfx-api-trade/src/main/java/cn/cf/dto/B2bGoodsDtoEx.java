package cn.cf.dto;



public class B2bGoodsDtoEx  extends B2bGoodsDto{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/*
	 * 商品列表
	 */
	private Integer isOpen;//店铺是否开业
	
	private Integer showPricebfOpen;//开店之前是否显示价格(1是 2否)
	
	private Double upperWeight;//超过X吨显示有货
	
	private String startTime;//店铺开业时间
	
	private String endTime ; //店铺歇业时间
	
	private String unitName;
	
	private String upDown;
	
	/*
	 * 商品详情
	 */
	private String qq;//店铺qq
	
	private String contactsTel;//客服电话
	
	private String wareAddress;//仓库地址
	
	private Integer collectCompanysNum;//公司被收藏数量
	
	private Long collectGoodsNum;//商品被收藏次数
	
	private Integer isCollected;//是否已被收藏：1未收藏  2已收藏
	
	private Integer isOffered;//1未出价 2已出价
	
	private String headPortrait;//公司头像
	
	private	B2bAuctionGoodsDto auctionGood; //竞拍
	
	/*
	 * 上下架商品数量
	 */
	private Integer counts;
	
	private String activityTime;
	
	private Double startingPrice;
	
	private String auctionPk;//竞拍pk
	private String bindPk;//团购pk

	private java.lang.String chineseName;//b2b_grade 
	private String wareCode ;//b2b_ware
	
	public String getWareCode() {
		return wareCode;
	}

	public void setWareCode(String wareCode) {
		this.wareCode = wareCode;
	}

	public java.lang.String getChineseName() {
		return chineseName;
	}

	public void setChineseName(java.lang.String chineseName) {
		this.chineseName = chineseName;
	}

	public String getAuctionPk() {
		return auctionPk;
	}

	public void setAuctionPk(String auctionPk) {
		this.auctionPk = auctionPk;
	}

	public String getBindPk() {
		return bindPk;
	}

	public void setBindPk(String bindPk) {
		this.bindPk = bindPk;
	}

	public String getActivityTime() {
		return activityTime;
	}

	public void setActivityTime(String activityTime) {
		this.activityTime = activityTime;
	}

	public Double getStartingPrice() {
		return startingPrice;
	}

	public void setStartingPrice(Double startingPrice) {
		this.startingPrice = startingPrice;
	}

	public Integer getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(Integer isOpen) {
		this.isOpen = isOpen;
	}

	public Integer getShowPricebfOpen() {
		return showPricebfOpen;
	}

	public void setShowPricebfOpen(Integer showPricebfOpen) {
		this.showPricebfOpen = showPricebfOpen;
	}

	public Double getUpperWeight() {
		return upperWeight;
	}

	public void setUpperWeight(Double upperWeight) {
		this.upperWeight = upperWeight;
	}

	public String getStartTime() {
		return startTime;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	

	public String getContactsTel() {
		return contactsTel;
	}

	public void setContactsTel(String contactsTel) {
		this.contactsTel = contactsTel;
	}

	public String getWareAddress() {
		return wareAddress;
	}

	public void setWareAddress(String wareAddress) {
		this.wareAddress = wareAddress;
	}

	public Integer getCollectCompanysNum() {
		return collectCompanysNum;
	}

	public void setCollectCompanysNum(Integer collectCompanysNum) {
		this.collectCompanysNum = collectCompanysNum;
	}

	public Integer getIsCollected() {
		return isCollected;
	}

	public void setIsCollected(Integer isCollected) {
		this.isCollected = isCollected;
	}

	public Integer getIsOffered() {
		return isOffered;
	}

	public void setIsOffered(Integer isOffered) {
		this.isOffered = isOffered;
	}

	public Long getCollectGoodsNum() {
		return collectGoodsNum;
	}

	public void setCollectGoodsNum(Long collectGoodsNum) {
		this.collectGoodsNum = collectGoodsNum;
	}

	public String getHeadPortrait() {
		return headPortrait;
	}

	public void setHeadPortrait(String headPortrait) {
		this.headPortrait = headPortrait;
	}

	public B2bAuctionGoodsDto getAuctionGood() {
		return auctionGood;
	}

	public void setAuctionGood(B2bAuctionGoodsDto auctionGood) {
		this.auctionGood = auctionGood;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getUpDown() {
		return upDown;
	}

	public void setUpDown(String upDown) {
		this.upDown = upDown;
	}

	public Integer getCounts() {
		return counts;
	}

	public void setCounts(Integer counts) {
		this.counts = counts;
	}


	
}
