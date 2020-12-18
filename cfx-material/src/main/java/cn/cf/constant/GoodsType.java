package cn.cf.constant;

public enum GoodsType {
	NORMAL("normal"),
	AUCTION("auction"),
	SALE("sale"),
	PRESALE("presale"),
	RARE("rare"),
	BINDING("binding");
	private String value;
	
	private GoodsType(String value){
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
