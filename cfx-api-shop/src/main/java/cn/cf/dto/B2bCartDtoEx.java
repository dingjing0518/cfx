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
		private String productName;
		private String varietiesName;
		private String seedvarietyName;
		private String specName;
		private String seriesName;
		private String packNumber;
		private String gradeName;
		private String tareWeight;
		private String salePrice;
		private String customerTel;
		private String brandName;
		public Integer counts;
		private String batchNumber;
		private String specifications;
		private String plantName;
		private Double totalWeight;
		private Double totalPrice;
		private Integer unit;
		private String startTime;
		private String endTime;
		private Integer isOpen;
		private Integer showPricebfOpen;
		private String storePk;
	    private Double weight;
	    private Integer totalBoxes;
	    private String bindName;
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
		public String getSeedvarietyName() {
			return seedvarietyName;
		}
		public void setSeedvarietyName(String seedvarietyName) {
			this.seedvarietyName = seedvarietyName;
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
		public String getPackNumber() {
			return packNumber;
		}
		public void setPackNumber(String packNumber) {
			this.packNumber = packNumber;
		}
		public String getGradeName() {
			return gradeName;
		}
		public void setGradeName(String gradeName) {
			this.gradeName = gradeName;
		}
		public String getTareWeight() {
			return tareWeight;
		}
		public void setTareWeight(String tareWeight) {
			this.tareWeight = tareWeight;
		}
		public String getSalePrice() {
			return salePrice;
		}
		public void setSalePrice(String salePrice) {
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
		public String getBatchNumber() {
			return batchNumber;
		}
		public void setBatchNumber(String batchNumber) {
			this.batchNumber = batchNumber;
		}
		public String getSpecifications() {
			return specifications;
		}
		public void setSpecifications(String specifications) {
			this.specifications = specifications;
		}
		public String getPlantName() {
			return plantName;
		}
		public void setPlantName(String plantName) {
			this.plantName = plantName;
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
		public Integer getUnit() {
			return unit;
		}
		public void setUnit(Integer unit) {
			this.unit = unit;
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
		public Double getWeight() {
			return weight;
		}
		public void setWeight(Double weight) {
			this.weight = weight;
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
