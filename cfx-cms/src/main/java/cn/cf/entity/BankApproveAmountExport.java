package cn.cf.entity;

import java.math.BigDecimal;

/**
 * 银行授信额度
 * @author xht
 *
 * 2018年7月17日
 */
public class BankApproveAmountExport  implements Comparable<BankApproveAmountExport>{
	
	private String title;
	
	/**======= 白条 ========*/
	private String sBTAmount="0.0";
	private String xBTAmount="0.0";
	private String yBTAmount="0.0";
	private String qBTAmount="0.0";
	private String pBTAmount="0.0";
	private String BTAmount="0.0";
	
	/**======= 化纤贷 ========*/
	private String sDAmount="0.0";
	private String xDAmount="0.0";
	private String yDAmount="0.0";
	private String qDAmount="0.0";
	private String pDAmount="0.0";
	private String DAmount="0.0";
	
	/**=======累计白条 ========*/
	private String sBTTotalAmount="0.0";
	private String xBTTotalAmount="0.0";
	private String yBTTotalAmount="0.0";
	private String qBTTotalAmount="0.0";
	private String pBTTotalAmount="0.0";
	private String BTTotalAmount="0.0";
	
	/**=======累计化纤贷 ========*/
	private String sDTotalAmount="0.0";
	private String xDTotalAmount="0.0";
	private String yDTotalAmount="0.0";
	private String qDTotalAmount="0.0";
	private String pDTotalAmount="0.0";
	private String DTotalAmount="0.0";
	
	private Integer flag;//1:昨日 ;2:当周;3:上周;4:年
	private String year ;
	private Integer month;
	private String bankPk;
	
	public int compareTo(BankApproveAmountExport o) {
		int i = this.flag - o.getFlag();//先按照标识排序
		if(i == 0){
			return this.month - o.getMonth();//如果标识相等了再用月份进行排序
		}
		return i;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getsBTAmount() {
		return sBTAmount;
	}

	public void setsBTAmount(String sBTAmount) {
		this.sBTAmount = sBTAmount;
	}

	public String getxBTAmount() {
		return xBTAmount;
	}

	public void setxBTAmount(String xBTAmount) {
		this.xBTAmount = xBTAmount;
	}

	public String getyBTAmount() {
		return yBTAmount;
	}

	public void setyBTAmount(String yBTAmount) {
		this.yBTAmount = yBTAmount;
	}

	public String getqBTAmount() {
		return qBTAmount;
	}

	public void setqBTAmount(String qBTAmount) {
		this.qBTAmount = qBTAmount;
	}

	public String getpBTAmount() {
		return pBTAmount;
	}

	public void setpBTAmount(String pBTAmount) {
		this.pBTAmount = pBTAmount;
	}

	public void setsDTotalAmount(String sDTotalAmount) {
		this.sDTotalAmount = sDTotalAmount;
	}

	public String getBTAmount() {
		return BTAmount;
	}

	public void setBTAmount(String bTAmount) {
		BTAmount = bTAmount;
	}

	public String getsDAmount() {
		return sDAmount;
	}

	public void setsDAmount(String sDAmount) {
		this.sDAmount = sDAmount;
	}

	public String getxDAmount() {
		return xDAmount;
	}

	public void setxDAmount(String xDAmount) {
		this.xDAmount = xDAmount;
	}

	public String getyDAmount() {
		return yDAmount;
	}

	public void setyDAmount(String yDAmount) {
		this.yDAmount = yDAmount;
	}

	public String getqDAmount() {
		return qDAmount;
	}

	public void setqDAmount(String qDAmount) {
		this.qDAmount = qDAmount;
	}

	public String getpDAmount() {
		return pDAmount;
	}

	public void setpDAmount(String pDAmount) {
		this.pDAmount = pDAmount;
	}

	public String getDAmount() {
		return DAmount;
	}

	public void setDAmount(String dAmount) {
		DAmount = dAmount;
	}

	public String getsBTTotalAmount() {
		return sBTTotalAmount;
	}

	public void setsBTTotalAmount(String sBTTotalAmount) {
		this.sBTTotalAmount = sBTTotalAmount;
	}

	public String getxBTTotalAmount() {
		return xBTTotalAmount;
	}

	public void setxBTTotalAmount(String xBTTotalAmount) {
		this.xBTTotalAmount = xBTTotalAmount;
	}

	public String getyBTTotalAmount() {
		return yBTTotalAmount;
	}

	public void setyBTTotalAmount(String yBTTotalAmount) {
		this.yBTTotalAmount = yBTTotalAmount;
	}

	public String getqBTTotalAmount() {
		return qBTTotalAmount;
	}

	public void setqBTTotalAmount(String qBTTotalAmount) {
		this.qBTTotalAmount = qBTTotalAmount;
	}

	public String getpBTTotalAmount() {
		return pBTTotalAmount;
	}

	public void setpBTTotalAmount(String pBTTotalAmount) {
		this.pBTTotalAmount = pBTTotalAmount;
	}

	public String getBTTotalAmount() {
		return BTTotalAmount;
	}

	public void setBTTotalAmount(String bTTotalAmount) {
		BTTotalAmount = bTTotalAmount;
	}

	public String getsDTotalAmount() {
		return sDTotalAmount;
	}

	
	public String getxDTotalAmount() {
		return xDTotalAmount;
	}

	public void setxDTotalAmount(String xDTotalAmount) {
		this.xDTotalAmount = xDTotalAmount;
	}

	public String getyDTotalAmount() {
		return yDTotalAmount;
	}

	public void setyDTotalAmount(String yDTotalAmount) {
		this.yDTotalAmount = yDTotalAmount;
	}

	public String getqDTotalAmount() {
		return qDTotalAmount;
	}

	public void setqDTotalAmount(String qDTotalAmount) {
		this.qDTotalAmount = qDTotalAmount;
	}

	public String getpDTotalAmount() {
		return pDTotalAmount;
	}

	public void setpDTotalAmount(String pDTotalAmount) {
		this.pDTotalAmount = pDTotalAmount;
	}

	public String getDTotalAmount() {
		return DTotalAmount;
	}

	public void setDTotalAmount(String dTotalAmount) {
		DTotalAmount = dTotalAmount;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public String getBankPk() {
		return bankPk;
	}

	public void setBankPk(String bankPk) {
		this.bankPk = bankPk;
	}



}
