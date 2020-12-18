package cn.cf;

public enum MessageEnum {
	
	FAIL("操作失败"),SUCCESS("操作成功！");

	private String message;
	
	public String getMessage() {
		return message;
	}
	
	private MessageEnum(String msg){
		this.message = msg;
	}
	
}
