package cn.cf.common;

public enum RiskControlType {

	WZSY("wzsy"),//微众税银
	RAYX("rayx");//融安易信
	
	private String value;
	
	private RiskControlType(String value){
		this.value = value;
	}
	
	public String getValue(){
		return value;
	}
}
