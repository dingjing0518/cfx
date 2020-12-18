package cn.cf.entity;

import org.springframework.data.annotation.Id;

public class B2bFutures {
	
	@Id
	private String id;
	private String futuresPk;
	private String futuresName;
	private Double price;
	private String date;
	private String updateTime;
	private String insertTime;
	private String block;//cf(化纤)，sx（纱线）
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFuturesPk() {
		return futuresPk;
	}
	public void setFuturesPk(String futuresPk) {
		this.futuresPk = futuresPk;
	}
	public String getFuturesName() {
		return futuresName;
	}
	public void setFuturesName(String futuresName) {
		this.futuresName = futuresName;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}
	public String getBlock() {
		return block;
	}
	public void setBlock(String block) {
		this.block = block;
	}
	
	
}
