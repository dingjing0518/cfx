package cn.cf.entity;

import javax.persistence.Id;

/**
 * 流量数据
 * @author xht
 *
 * 2018年10月22日
 */
public class FlowDataReport {
	
	@Id
	private String id;
	private String date;
	
	private Integer pcWithPV = 0;
	
	private Integer wapWithPV = 0;
	
	private Integer pcWithUV = 0;
	
	private Integer wapWithUV = 0;
	
	private Integer pcWithIP = 0;
	
	private Integer wapWithIP = 0;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Integer getPcWithPV() {
		return pcWithPV;
	}

	public void setPcWithPV(Integer pcWithPV) {
		this.pcWithPV = pcWithPV;
	}

	public Integer getWapWithPV() {
		return wapWithPV;
	}

	public void setWapWithPV(Integer wapWithPV) {
		this.wapWithPV = wapWithPV;
	}

	public Integer getPcWithUV() {
		return pcWithUV;
	}

	public void setPcWithUV(Integer pcWithUV) {
		this.pcWithUV = pcWithUV;
	}

	public Integer getWapWithUV() {
		return wapWithUV;
	}

	public void setWapWithUV(Integer wapWithUV) {
		this.wapWithUV = wapWithUV;
	}

	public Integer getPcWithIP() {
		return pcWithIP;
	}

	public void setPcWithIP(Integer pcWithIP) {
		this.pcWithIP = pcWithIP;
	}

	public Integer getWapWithIP() {
		return wapWithIP;
	}

	public void setWapWithIP(Integer wapWithIP) {
		this.wapWithIP = wapWithIP;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	
	

}
