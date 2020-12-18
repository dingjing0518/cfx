package cn.cf.constant;

public enum Block {
	CF("cf","HXO","HXC"),
	SX("sx","SXO","SXC");
	
	private String value;
	private String orderType;
	private String contractType;
	
	private Block(String value,String orderType,String contractType){
		this.value = value;
		this.orderType = orderType;
		this.contractType = contractType;
	}
	
	public String getValue() {
		return value;
	}
	
	public String getOrderType() {
		return orderType;
	}
	
	public String getContractType() {
		return contractType;
	}
	
}
