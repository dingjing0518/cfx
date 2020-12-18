package cn.cf.entity;

public class ProcessEntity implements Comparable<ProcessEntity> {
	private int key;
	private String user;
	private String time;
	
	 public int compareTo(ProcessEntity o) {  
	        int i = this.getKey() - o.getKey();//按照key排序  
	        return i;  
	    }
	public int getKey() {
		return key;
	}
	public void setKey(int key) {
		this.key = key;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}

	

}
