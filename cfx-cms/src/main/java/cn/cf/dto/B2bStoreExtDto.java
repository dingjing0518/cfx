package cn.cf.dto;

import java.math.BigDecimal;

public class B2bStoreExtDto extends B2bStoreDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2309538840031077061L;
	
	private String storePk;
	
	private String storeName;
	
	
	private String startStime;
	private String startEtime;
	private String endStime;
	private String endEtime;
	private String startTimeBegin;
	private String starteTimeEnd;
	
	private String sometimes;
	
	private BigDecimal weight = new BigDecimal("0.0");
	
	private String accountPk;
	
	
	public String getAccountPk() {
        return accountPk;
    }

    public void setAccountPk(String accountPk) {
        this.accountPk = accountPk;
    }

    public String getSometimes() {
		return sometimes;
	}

	public void setSometimes(String sometimes) {
		this.sometimes = sometimes;
	}

	public String getStartStime() {
		return startStime;
	}

	public void setStartStime(String startStime) {
		this.startStime = startStime;
	}

	public String getStartEtime() {
		return startEtime;
	}

	public void setStartEtime(String startEtime) {
		this.startEtime = startEtime;
	}

	public String getEndStime() {
		return endStime;
	}

	public void setEndStime(String endStime) {
		this.endStime = endStime;
	}

	public String getEndEtime() {
		return endEtime;
	}

	public void setEndEtime(String endEtime) {
		this.endEtime = endEtime;
	}

	public String getStartTimeBegin() {
		return startTimeBegin;
	}

	public void setStartTimeBegin(String startTimeBegin) {
		this.startTimeBegin = startTimeBegin;
	}

	public String getStarteTimeEnd() {
		return starteTimeEnd;
	}

	public void setStarteTimeEnd(String starteTimeEnd) {
		this.starteTimeEnd = starteTimeEnd;
	}

	public String getStorePk() {
		return storePk;
	}

	public void setStorePk(String storePk) {
		this.storePk = storePk;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}
	
	
	
}
