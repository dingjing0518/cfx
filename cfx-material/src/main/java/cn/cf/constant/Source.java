package cn.cf.constant;

public enum Source {

	PC("pc",1,3600)
	,WAP("wap",2,3600)
	,APP("app",3,3600*24*30)
	,ERP("erp",4,3600*24);
	
	private String sourceType;
	private Integer source;
	private Integer sessionTime;
	
	private Source(String sourceType,Integer source,Integer sessionTime){
		this.sourceType = sourceType;
		this.source = source;
		this.sessionTime = sessionTime;
	}
	
	public static Source getBySource(Integer type){
		switch (type) {
		case 1:
			return Source.PC;
		case 2:
			return Source.WAP;
		case 3:
			return Source.APP;
		case 4:
			return Source.ERP;
		default:
			return Source.PC;
		}
	}
	public static Source getByTokenType(String tokenType){
		switch (tokenType) {
		case "/b2b/pc":
			return Source.PC;
		case "/b2b/wap":
			return Source.WAP;
		case "/b2b/app":
			return Source.APP;
		case "/erp/":
			return Source.ERP;
		default:
			return Source.PC;
		}
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public Integer getSessionTime() {
		return sessionTime;
	}

	public void setSessionTime(Integer sessionTime) {
		this.sessionTime = sessionTime;
	}

	
	
	
	
}
