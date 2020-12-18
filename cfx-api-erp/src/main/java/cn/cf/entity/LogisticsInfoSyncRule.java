package cn.cf.entity;

public class LogisticsInfoSyncRule {
	
	private String id;
	
	private Integer startWeight;
	
	private Integer endWeight;
	
	private Double price;
	
	private String productName;
	
	private String packNumber;
	
	private Integer isStore;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public Integer getStartWeight() {
		return startWeight;
	}

	public void setStartWeight(Integer startWeight) {
		this.startWeight = startWeight;
	}

	public Integer getEndWeight() {
		return endWeight;
	}

	public void setEndWeight(Integer endWeight) {
		this.endWeight = endWeight;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getPackNumber() {
		return packNumber;
	}

	public void setPackNumber(String packNumber) {
		this.packNumber = packNumber;
	}

	public Integer getIsStore() {
		return isStore;
	}

	public void setIsStore(Integer isStore) {
		this.isStore = isStore;
	}
	
	

}
