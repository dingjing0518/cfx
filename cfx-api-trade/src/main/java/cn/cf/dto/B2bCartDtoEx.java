package cn.cf.dto;

import java.util.List;

public class B2bCartDtoEx extends B2bCartDto implements Cloneable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		// columns END
		private List<B2bCartDtoEx> childList;
		private String companyPk;
		private String address;
		private String companyName;
		private Double tareWeight;
		private Double salePrice;
		private String customerTel;
		private String brandName;
		public Integer counts;
		private Double totalWeight;
		private Double totalPrice;
		private String startTime;
		private String endTime;
		private Integer isOpen;
		private Integer showPricebfOpen;
		private String storePk;
	    private Integer totalBoxes;
	    private String bindName;
	    private Integer isCollected;//是否已收藏商品：1未收藏  2已收藏
	    private Integer isUpdown;
	    private Double collectionPrice;
	    private String goodsType;
	    private String goodsInfo;
        private String block;

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

	 
		public String getBindName() {
			return bindName;
		}
		public void setBindName(String bindName) {
			this.bindName = bindName;
		}
		public Integer getTotalBoxes() {
			return totalBoxes;
		}
		public void setTotalBoxes(Integer totalBoxes) {
			this.totalBoxes = totalBoxes;
		}
		public String getCompanyPk() {
			return companyPk;
		}
		public void setCompanyPk(String companyPk) {
			this.companyPk = companyPk;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public String getCompanyName() {
			return companyName;
		}
		public void setCompanyName(String companyName) {
			this.companyName = companyName;
		}
	 
		
		
		public Double getTareWeight() {
			return tareWeight;
		}

		public void setTareWeight(Double tareWeight) {
			this.tareWeight = tareWeight;
		}

		public Double getSalePrice() {
			return salePrice;
		}

		public void setSalePrice(Double salePrice) {
			this.salePrice = salePrice;
		}

		public String getCustomerTel() {
			return customerTel;
		}
		public void setCustomerTel(String customerTel) {
			this.customerTel = customerTel;
		}
		public String getBrandName() {
			return brandName;
		}
		public void setBrandName(String brandName) {
			this.brandName = brandName;
		}
		public Integer getCounts() {
			return counts;
		}
		public void setCounts(Integer counts) {
			this.counts = counts;
		}
		 
		public Double getTotalWeight() {
			return totalWeight;
		}
		public void setTotalWeight(Double totalWeight) {
			this.totalWeight = totalWeight;
		}
		public Double getTotalPrice() {
			return totalPrice;
		}
		public void setTotalPrice(Double totalPrice) {
			this.totalPrice = totalPrice;
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
		public String getStorePk() {
			return storePk;
		}
		public void setStorePk(String storePk) {
			this.storePk = storePk;
		}
		public List<B2bCartDtoEx> getChildList() {
			return childList;
		}
		public void setChildList(List<B2bCartDtoEx> childList) {
			this.childList = childList;
		}
		public Integer getIsCollected() {
			return isCollected;
		}
		public void setIsCollected(Integer isCollected) {
			this.isCollected = isCollected;
		}
		public Integer getIsUpdown() {
			return isUpdown;
		}
		public void setIsUpdown(Integer isUpdown) {
			this.isUpdown = isUpdown;
		}
		
		public Double getCollectionPrice() {
			return collectionPrice;
		}
		public void setCollectionPrice(Double collectionPrice) {
			this.collectionPrice = collectionPrice;
		}
		
		public String getGoodsType() {
			return goodsType;
		}
		public void setGoodsType(String goodsType) {
			this.goodsType = goodsType;
		}
		@Override  
	    public Object clone()    
	    {  
	        try {
				return super.clone();
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;  
	    } 
		
}
