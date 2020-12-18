package cn.cf.constant;

public enum ApiServer {
	MEMBER("/member/"),
	SHOP("/shop/"),
	LOGISTICS("/logistics/"),
	PASSPORT("/passport/");
	
	private String value;
	
	private ApiServer(String value){
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
