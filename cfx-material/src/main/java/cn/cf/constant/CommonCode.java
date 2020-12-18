package cn.cf.constant;

public enum CommonCode {
	
	ZERO(0),
	ONE(1),
	TWO(2),
	THREE(3),
	FOUR(4),
	FIVE(5),
	SIX(6),
	SEVEN(7);
	
	private Integer value;
	
	private CommonCode(Integer value){
		this.value = value;
	}
	
	public Integer getValue() {
		return value;
	}
	
	
}
