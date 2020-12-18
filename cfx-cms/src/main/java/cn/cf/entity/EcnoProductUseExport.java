package cn.cf.entity;

import java.util.List;

public class EcnoProductUseExport implements Comparable<EcnoProductUseExport>{
	
	private String title; 
	private Integer flag;//1:昨日 ;2:当周;3:上周;4:年
	private String year ;
	private Integer month;
	private String bankPk;
/*==========提款==============*/
	private String sBTAmount="0.0";
	private String xBTAmount="0.0";
	private String yBTAmount="0.0";
	private String qBTAmount="0.0";
	private String pBTAmount="0.0";
	private String bTAmount = "0.0";
	
	private String sDAmount="0.0";
	private String xDAmount="0.0";
	private String yDAmount="0.0";
	private String qDAmount="0.0";
	private String pDAmount="0.0";
	private String dAmount = "0.0";
	
	private String sBTTotalAmount="0.0";
	private String xBTTotalAmount="0.0";
	private String yBTTotalAmount="0.0";
	private String qBTTotalAmount="0.0";
	private String pBTTotalAmount="0.0";
	private String bTTotalAmount ="0.0";
	
	private String sDTotalAmount="0.0";
	private String xDTotalAmount="0.0";
	private String yDTotalAmount="0.0";
	private String qDTotalAmount="0.0";
	private String pDTotalAmount="0.0";
	private String dTotalAmount="0.0";
	
	/*=========还款==========*/
	private String sPayBTAmount="0.0";
	private String xPayBTAmount="0.0";
	private String yPayBTAmount="0.0";
	private String qPayBTAmount="0.0";
	private String pPayBTAmount="0.0";
	private String payBTAmount="0.0";
	
	private String sPayDAmount="0.0";
	private String xPayDAmount="0.0";
	private String yPayDAmount="0.0";
	private String qPayDAmount="0.0";
	private String pPayDAmount="0.0";
	private String payDAmount="0.0";
	
	private String sPayBTTotalAmount="0.0";
	private String xPayBTTotalAmount="0.0";
	private String yPayBTTotalAmount="0.0";
	private String qPayBTTotalAmount="0.0";
	private String pPayBTTotalAmount="0.0";
	private String payBTTotalAmount="0.0";
	
	private String sPayDTotalAmount="0.0";
	private String xPayDTotalAmount="0.0";
	private String yPayDTotalAmount="0.0";
	private String qPayDTotalAmount="0.0";
	private String pPayDTotalAmount="0.0";
	private String payDTotalAmount="0.0";
	
	/*==========当前余额========*/
	private String sNowBTAmount="0.0";
	private String xNowBTAmount="0.0";
	private String yNowBTAmount="0.0";
	private String qNowBTAmount="0.0";
	private String pNowBTAmount="0.0";
	private String nowBTAmount="0.0";
	
	private String sNowDAmount="0.0";
	private String xNowDAmount="0.0";
	private String yNowDAmount="0.0";
	private String qNowDAmount="0.0";
	private String pNowDAmount="0.0";
	private String nowDAmount="0.0";
	
	public int compareTo(EcnoProductUseExport o) {
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

	public String getbTAmount() {
		return bTAmount;
	}

	public void setbTAmount(String bTAmount) {
		this.bTAmount = bTAmount;
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

	public String getdAmount() {
		return dAmount;
	}

	public void setdAmount(String dAmount) {
		this.dAmount = dAmount;
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

	public String getbTTotalAmount() {
		return bTTotalAmount;
	}

	public void setbTTotalAmount(String bTTotalAmount) {
		this.bTTotalAmount = bTTotalAmount;
	}

	public String getsDTotalAmount() {
		return sDTotalAmount;
	}

	public void setsDTotalAmount(String sDTotalAmount) {
		this.sDTotalAmount = sDTotalAmount;
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

	public String getdTotalAmount() {
		return dTotalAmount;
	}

	public void setdTotalAmount(String dTotalAmount) {
		this.dTotalAmount = dTotalAmount;
	}

	public String getsPayBTAmount() {
		return sPayBTAmount;
	}

	public void setsPayBTAmount(String sPayBTAmount) {
		this.sPayBTAmount = sPayBTAmount;
	}

	public String getxPayBTAmount() {
		return xPayBTAmount;
	}

	public void setxPayBTAmount(String xPayBTAmount) {
		this.xPayBTAmount = xPayBTAmount;
	}

	public String getyPayBTAmount() {
		return yPayBTAmount;
	}

	public void setyPayBTAmount(String yPayBTAmount) {
		this.yPayBTAmount = yPayBTAmount;
	}

	public String getqPayBTAmount() {
		return qPayBTAmount;
	}

	public void setqPayBTAmount(String qPayBTAmount) {
		this.qPayBTAmount = qPayBTAmount;
	}

	public String getpPayBTAmount() {
		return pPayBTAmount;
	}

	public void setpPayBTAmount(String pPayBTAmount) {
		this.pPayBTAmount = pPayBTAmount;
	}

	public String getPayBTAmount() {
		return payBTAmount;
	}

	public void setPayBTAmount(String payBTAmount) {
		this.payBTAmount = payBTAmount;
	}

	public String getsPayDAmount() {
		return sPayDAmount;
	}

	public void setsPayDAmount(String sPayDAmount) {
		this.sPayDAmount = sPayDAmount;
	}

	public String getxPayDAmount() {
		return xPayDAmount;
	}

	public void setxPayDAmount(String xPayDAmount) {
		this.xPayDAmount = xPayDAmount;
	}

	public String getyPayDAmount() {
		return yPayDAmount;
	}

	public void setyPayDAmount(String yPayDAmount) {
		this.yPayDAmount = yPayDAmount;
	}

	public String getqPayDAmount() {
		return qPayDAmount;
	}

	public void setqPayDAmount(String qPayDAmount) {
		this.qPayDAmount = qPayDAmount;
	}

	public String getpPayDAmount() {
		return pPayDAmount;
	}

	public void setpPayDAmount(String pPayDAmount) {
		this.pPayDAmount = pPayDAmount;
	}

	public String getPayDAmount() {
		return payDAmount;
	}

	public void setPayDAmount(String payDAmount) {
		this.payDAmount = payDAmount;
	}

	public String getsPayBTTotalAmount() {
		return sPayBTTotalAmount;
	}

	public void setsPayBTTotalAmount(String sPayBTTotalAmount) {
		this.sPayBTTotalAmount = sPayBTTotalAmount;
	}

	public String getxPayBTTotalAmount() {
		return xPayBTTotalAmount;
	}

	public void setxPayBTTotalAmount(String xPayBTTotalAmount) {
		this.xPayBTTotalAmount = xPayBTTotalAmount;
	}

	public String getyPayBTTotalAmount() {
		return yPayBTTotalAmount;
	}

	public void setyPayBTTotalAmount(String yPayBTTotalAmount) {
		this.yPayBTTotalAmount = yPayBTTotalAmount;
	}

	public String getqPayBTTotalAmount() {
		return qPayBTTotalAmount;
	}

	public void setqPayBTTotalAmount(String qPayBTTotalAmount) {
		this.qPayBTTotalAmount = qPayBTTotalAmount;
	}

	public String getpPayBTTotalAmount() {
		return pPayBTTotalAmount;
	}

	public void setpPayBTTotalAmount(String pPayBTTotalAmount) {
		this.pPayBTTotalAmount = pPayBTTotalAmount;
	}

	public String getPayBTTotalAmount() {
		return payBTTotalAmount;
	}

	public void setPayBTTotalAmount(String payBTTotalAmount) {
		this.payBTTotalAmount = payBTTotalAmount;
	}

	public String getsPayDTotalAmount() {
		return sPayDTotalAmount;
	}

	public void setsPayDTotalAmount(String sPayDTotalAmount) {
		this.sPayDTotalAmount = sPayDTotalAmount;
	}

	public String getxPayDTotalAmount() {
		return xPayDTotalAmount;
	}

	public void setxPayDTotalAmount(String xPayDTotalAmount) {
		this.xPayDTotalAmount = xPayDTotalAmount;
	}

	public String getyPayDTotalAmount() {
		return yPayDTotalAmount;
	}

	public void setyPayDTotalAmount(String yPayDTotalAmount) {
		this.yPayDTotalAmount = yPayDTotalAmount;
	}

	public String getqPayDTotalAmount() {
		return qPayDTotalAmount;
	}

	public void setqPayDTotalAmount(String qPayDTotalAmount) {
		this.qPayDTotalAmount = qPayDTotalAmount;
	}

	public String getpPayDTotalAmount() {
		return pPayDTotalAmount;
	}

	public void setpPayDTotalAmount(String pPayDTotalAmount) {
		this.pPayDTotalAmount = pPayDTotalAmount;
	}

	public String getPayDTotalAmount() {
		return payDTotalAmount;
	}

	public void setPayDTotalAmount(String payDTotalAmount) {
		this.payDTotalAmount = payDTotalAmount;
	}

	public String getsNowBTAmount() {
		return sNowBTAmount;
	}

	public void setsNowBTAmount(String sNowBTAmount) {
		this.sNowBTAmount = sNowBTAmount;
	}

	public String getxNowBTAmount() {
		return xNowBTAmount;
	}

	public void setxNowBTAmount(String xNowBTAmount) {
		this.xNowBTAmount = xNowBTAmount;
	}

	public String getyNowBTAmount() {
		return yNowBTAmount;
	}

	public void setyNowBTAmount(String yNowBTAmount) {
		this.yNowBTAmount = yNowBTAmount;
	}

	public String getqNowBTAmount() {
		return qNowBTAmount;
	}

	public void setqNowBTAmount(String qNowBTAmount) {
		this.qNowBTAmount = qNowBTAmount;
	}

	public String getpNowBTAmount() {
		return pNowBTAmount;
	}

	public void setpNowBTAmount(String pNowBTAmount) {
		this.pNowBTAmount = pNowBTAmount;
	}

	public String getNowBTAmount() {
		return nowBTAmount;
	}

	public void setNowBTAmount(String nowBTAmount) {
		this.nowBTAmount = nowBTAmount;
	}

	public String getsNowDAmount() {
		return sNowDAmount;
	}

	public void setsNowDAmount(String sNowDAmount) {
		this.sNowDAmount = sNowDAmount;
	}

	public String getxNowDAmount() {
		return xNowDAmount;
	}

	public void setxNowDAmount(String xNowDAmount) {
		this.xNowDAmount = xNowDAmount;
	}

	public String getyNowDAmount() {
		return yNowDAmount;
	}

	public void setyNowDAmount(String yNowDAmount) {
		this.yNowDAmount = yNowDAmount;
	}

	public String getqNowDAmount() {
		return qNowDAmount;
	}

	public void setqNowDAmount(String qNowDAmount) {
		this.qNowDAmount = qNowDAmount;
	}

	public String getpNowDAmount() {
		return pNowDAmount;
	}

	public void setpNowDAmount(String pNowDAmount) {
		this.pNowDAmount = pNowDAmount;
	}

	public String getNowDAmount() {
		return nowDAmount;
	}

	public void setNowDAmount(String nowDAmount) {
		this.nowDAmount = nowDAmount;
	}

	
	
}
