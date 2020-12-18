package cn.cf.entity;

public class TransactionDataStoreEntity {
	
	private String storePk;
	
	private String storeName;
	
	private Double amount;
	
	private Integer count;
	
	private String orderNumbers;

	public String getStorePk() {
		return storePk;
	}

	public void setStorePk(String storePk) {
		this.storePk = storePk;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getOrderNumbers() {
		return orderNumbers;
	}

	public void setOrderNumbers(String orderNumbers) {
		this.orderNumbers = orderNumbers;
	}
	
	
	
}
