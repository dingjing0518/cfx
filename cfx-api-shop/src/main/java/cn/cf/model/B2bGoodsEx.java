package cn.cf.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author xht
 * @version 1.0
 * @since 1.0
 * */
public class B2bGoodsEx extends B2bGoods implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Date auctionTime;// 竞拍时间
	private String auctionScreenpks;// 场次（逗号分隔）
	private Double startingPrice;// 起拍价
	private String auctionPk;//竞拍场次Pk
	private String activityTime;//竞拍活动日期
	private String bindPk;//拼团活动pk

	
	 
	
 

	public Date getAuctionTime() {
		return auctionTime;
	}

	public void setAuctionTime(Date auctionTime) {
		this.auctionTime = auctionTime;
	}

	public String getAuctionScreenpks() {
		return auctionScreenpks;
	}

	public void setAuctionScreenpks(String auctionScreenpks) {
		this.auctionScreenpks = auctionScreenpks;
	}

	public Double getStartingPrice() {
		return startingPrice;
	}

	public void setStartingPrice(Double startingPrice) {
		this.startingPrice = startingPrice;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getAuctionPk() {
		return auctionPk;
	}

	public void setAuctionPk(String auctionPk) {
		this.auctionPk = auctionPk;
	}


	public String getActivityTime() {
		return activityTime;
	}

	public void setActivityTime(String activityTime) {
		this.activityTime = activityTime;
	}

	public String getBindPk() {
		return bindPk;
	}

	public void setBindPk(String bindPk) {
		this.bindPk = bindPk;
	}

}
