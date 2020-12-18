package cn.cf.entity;

import java.util.Comparator;

public class EconCustomerApproveExport implements Comparable<EconCustomerApproveExport>{
	private String title;
	/**============  营销相关=========== */
	private  Integer yBTApproving = 0;//白条
	private  Integer yBTPass = 0;
	private  Integer yBTUnpass = 0 ;
	private  Integer yDApproving = 0 ;//化纤贷
	private  Integer yDPass = 0;
	private  Integer yDUnpass= 0;
	private  Integer yBTDApproving= 0;//化纤贷+白条
	private  Integer yBTDPass= 0;
	private  Integer yBTDUnpass= 0;
	
	private  Integer yApproving=0;//合计
	private  Integer yPass=0;
	private  Integer yUnpass=0;
	
	/**============  平台相关=========== */
	private  Integer pBTApproving= 0;//白条
	private  Integer pBTPass= 0;
	private  Integer pBTUnpass= 0;
	private  Integer pDApproving= 0;//化纤贷
	private  Integer pDPass= 0;
	private  Integer pDUnpass= 0;
	private  Integer pBTDApproving= 0;//化纤贷+白条
	private  Integer pBTDPass= 0;
	private  Integer pBTDUnpass= 0;
	
	private  Integer pApproving=0;//合计
	private  Integer pPass=0;
	private  Integer pUnpass=0;
	
	/**============  盛虹相关=========== */
	private  Integer sBTApproving= 0;//白条
	private  Integer sBTPass= 0;
	private  Integer sBTUnpass= 0;
	private  Integer sDApproving= 0;//化纤贷
	private  Integer sDPass= 0;
	private  Integer sDUnpass= 0;
	private  Integer sBTDApproving= 0;//化纤贷+白条
	private  Integer sBTDPass= 0;
	private  Integer sBTDUnpass= 0;
	
	private  Integer sApproving=0;//合计
	private  Integer sPass=0;
	private  Integer sUnpass=0;
	/**============  新凤鸣相关=========== */
	private  Integer xBTApproving= 0;//白条
	private  Integer xBTPass= 0;
	private  Integer xBTUnpass= 0;
	private  Integer xDApproving= 0;//化纤贷
	private  Integer xDPass= 0;
	private  Integer xDUnpass= 0;
	private  Integer xBTDApproving= 0;//化纤贷+白条
	private  Integer xBTDPass= 0;
	private  Integer xBTDUnpass= 0;
	
	private  Integer xApproving=0;//合计
	private  Integer xPass=0;
	private  Integer xUnpass=0;
	
	/**============  其他相关=========== */
	private  Integer qBTApproving= 0;//白条
	private  Integer qBTPass= 0;
	private  Integer qBTUnpass= 0;
	private  Integer qDApproving= 0;//化纤贷
	private  Integer qDPass= 0;
	private  Integer qDUnpass= 0;
	private  Integer qBTDApproving= 0;//化纤贷+白条
	private  Integer qBTDPass= 0;
	private  Integer qBTDUnpass= 0;
	
	private  Integer qApproving=0;//计
	private  Integer qPass=0;
	private  Integer qUnpass=0;
	/**=================column=====================*/
	private Integer flag;//1:昨日 ;2:当周;3:上周;4:年
	private String year ;
	private Integer month;
	private String bankPk;
	
	public int compareTo(EconCustomerApproveExport o) {
		int i = this.flag - o.getFlag();//先按照标识排序
		if(i == 0){
			return this.month - o.getMonth();//如果标识相等了再用月份进行排序
		}
		return i;
	}

    
		 
	public Integer getsApproving() {
		return sApproving;
	}
	public void setsApproving(Integer sApproving) {
		this.sApproving = sApproving;
	}
	public Integer getsPass() {
		return sPass;
	}
	public void setsPass(Integer sPass) {
		this.sPass = sPass;
	}
	public Integer getsUnpass() {
		return sUnpass;
	}
	public void setsUnpass(Integer sUnpass) {
		this.sUnpass = sUnpass;
	}
	public Integer getyApproving() {
		return yApproving;
	}
	public void setyApproving(Integer yApproving) {
		this.yApproving = yApproving;
	}
	public Integer getyPass() {
		return yPass;
	}
	public void setyPass(Integer yPass) {
		this.yPass = yPass;
	}
	public Integer getyUnpass() {
		return yUnpass;
	}
	public void setyUnpass(Integer yUnpass) {
		this.yUnpass = yUnpass;
	}
	public Integer getpApproving() {
		return pApproving;
	}
	public void setpApproving(Integer pApproving) {
		this.pApproving = pApproving;
	}
	public Integer getpPass() {
		return pPass;
	}
	public void setpPass(Integer pPass) {
		this.pPass = pPass;
	}
	public Integer getpUnpass() {
		return pUnpass;
	}
	public void setpUnpass(Integer pUnpass) {
		this.pUnpass = pUnpass;
	}
	public Integer getxApproving() {
		return xApproving;
	}
	public void setxApproving(Integer xApproving) {
		this.xApproving = xApproving;
	}
	public Integer getxPass() {
		return xPass;
	}
	public void setxPass(Integer xPass) {
		this.xPass = xPass;
	}
	public Integer getxUnpass() {
		return xUnpass;
	}
	public void setxUnpass(Integer xUnpass) {
		this.xUnpass = xUnpass;
	}
	public Integer getqApproving() {
		return qApproving;
	}
	public void setqApproving(Integer qApproving) {
		this.qApproving = qApproving;
	}
	public Integer getqPass() {
		return qPass;
	}
	public void setqPass(Integer qPass) {
		this.qPass = qPass;
	}
	public Integer getqUnpass() {
		return qUnpass;
	}
	public void setqUnpass(Integer qUnpass) {
		this.qUnpass = qUnpass;
	}
	public String getBankPk() {
		return bankPk;
	}
	public void setBankPk(String bankPk) {
		this.bankPk = bankPk;
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
	public Integer getyBTApproving() {
		return yBTApproving;
	}
	public void setyBTApproving(Integer yBTApproving) {
		this.yBTApproving = yBTApproving;
	}
	public Integer getyBTPass() {
		return yBTPass;
	}
	public void setyBTPass(Integer yBTPass) {
		this.yBTPass = yBTPass;
	}
	public Integer getyBTUnpass() {
		return yBTUnpass;
	}
	public void setyBTUnpass(Integer yBTUnpass) {
		this.yBTUnpass = yBTUnpass;
	}
	public Integer getyDApproving() {
		return yDApproving;
	}
	public void setyDApproving(Integer yDApproving) {
		this.yDApproving = yDApproving;
	}
	public Integer getyDPass() {
		return yDPass;
	}
	public void setyDPass(Integer yDPass) {
		this.yDPass = yDPass;
	}
	public Integer getyDUnpass() {
		return yDUnpass;
	}
	public void setyDUnpass(Integer yDUnpass) {
		this.yDUnpass = yDUnpass;
	}
	public Integer getyBTDApproving() {
		return yBTDApproving;
	}
	public void setyBTDApproving(Integer yBTDApproving) {
		this.yBTDApproving = yBTDApproving;
	}
	public Integer getyBTDPass() {
		return yBTDPass;
	}
	public void setyBTDPass(Integer yBTDPass) {
		this.yBTDPass = yBTDPass;
	}
	public Integer getyBTDUnpass() {
		return yBTDUnpass;
	}
	public void setyBTDUnpass(Integer yBTDUnpass) {
		this.yBTDUnpass = yBTDUnpass;
	}
	public Integer getpBTApproving() {
		return pBTApproving;
	}
	public void setpBTApproving(Integer pBTApproving) {
		this.pBTApproving = pBTApproving;
	}
	public Integer getpBTPass() {
		return pBTPass;
	}
	public void setpBTPass(Integer pBTPass) {
		this.pBTPass = pBTPass;
	}
	public Integer getpBTUnpass() {
		return pBTUnpass;
	}
	public void setpBTUnpass(Integer pBTUnpass) {
		this.pBTUnpass = pBTUnpass;
	}
	public Integer getpDApproving() {
		return pDApproving;
	}
	public void setpDApproving(Integer pDApproving) {
		this.pDApproving = pDApproving;
	}
	public Integer getpDPass() {
		return pDPass;
	}
	public void setpDPass(Integer pDPass) {
		this.pDPass = pDPass;
	}
	public Integer getpDUnpass() {
		return pDUnpass;
	}
	public void setpDUnpass(Integer pDUnpass) {
		this.pDUnpass = pDUnpass;
	}
	public Integer getpBTDApproving() {
		return pBTDApproving;
	}
	public void setpBTDApproving(Integer pBTDApproving) {
		this.pBTDApproving = pBTDApproving;
	}
	public Integer getpBTDPass() {
		return pBTDPass;
	}
	public void setpBTDPass(Integer pBTDPass) {
		this.pBTDPass = pBTDPass;
	}
	public Integer getpBTDUnpass() {
		return pBTDUnpass;
	}
	public void setpBTDUnpass(Integer pBTDUnpass) {
		this.pBTDUnpass = pBTDUnpass;
	}
	public Integer getsBTApproving() {
		return sBTApproving;
	}
	public void setsBTApproving(Integer sBTApproving) {
		this.sBTApproving = sBTApproving;
	}
	public Integer getsBTPass() {
		return sBTPass;
	}
	public void setsBTPass(Integer sBTPass) {
		this.sBTPass = sBTPass;
	}
	public Integer getsBTUnpass() {
		return sBTUnpass;
	}
	public void setsBTUnpass(Integer sBTUnpass) {
		this.sBTUnpass = sBTUnpass;
	}
	public Integer getsDApproving() {
		return sDApproving;
	}
	public void setsDApproving(Integer sDApproving) {
		this.sDApproving = sDApproving;
	}
	public Integer getsDPass() {
		return sDPass;
	}
	public void setsDPass(Integer sDPass) {
		this.sDPass = sDPass;
	}
	public Integer getsDUnpass() {
		return sDUnpass;
	}
	public void setsDUnpass(Integer sDUnpass) {
		this.sDUnpass = sDUnpass;
	}
	public Integer getsBTDApproving() {
		return sBTDApproving;
	}
	public void setsBTDApproving(Integer sBTDApproving) {
		this.sBTDApproving = sBTDApproving;
	}
	public Integer getsBTDPass() {
		return sBTDPass;
	}
	public void setsBTDPass(Integer sBTDPass) {
		this.sBTDPass = sBTDPass;
	}
	public Integer getsBTDUnpass() {
		return sBTDUnpass;
	}
	public void setsBTDUnpass(Integer sBTDUnpass) {
		this.sBTDUnpass = sBTDUnpass;
	}
	public Integer getxBTApproving() {
		return xBTApproving;
	}
	public void setxBTApproving(Integer xBTApproving) {
		this.xBTApproving = xBTApproving;
	}
	public Integer getxBTPass() {
		return xBTPass;
	}
	public void setxBTPass(Integer xBTPass) {
		this.xBTPass = xBTPass;
	}
	public Integer getxBTUnpass() {
		return xBTUnpass;
	}
	public void setxBTUnpass(Integer xBTUnpass) {
		this.xBTUnpass = xBTUnpass;
	}
	public Integer getxDApproving() {
		return xDApproving;
	}
	public void setxDApproving(Integer xDApproving) {
		this.xDApproving = xDApproving;
	}
	public Integer getxDPass() {
		return xDPass;
	}
	public void setxDPass(Integer xDPass) {
		this.xDPass = xDPass;
	}
	public Integer getxDUnpass() {
		return xDUnpass;
	}
	public void setxDUnpass(Integer xDUnpass) {
		this.xDUnpass = xDUnpass;
	}
	public Integer getxBTDApproving() {
		return xBTDApproving;
	}
	public void setxBTDApproving(Integer xBTDApproving) {
		this.xBTDApproving = xBTDApproving;
	}
	public Integer getxBTDPass() {
		return xBTDPass;
	}
	public void setxBTDPass(Integer xBTDPass) {
		this.xBTDPass = xBTDPass;
	}
	public Integer getxBTDUnpass() {
		return xBTDUnpass;
	}
	public void setxBTDUnpass(Integer xBTDUnpass) {
		this.xBTDUnpass = xBTDUnpass;
	}
	public Integer getqBTApproving() {
		return qBTApproving;
	}
	public void setqBTApproving(Integer qBTApproving) {
		this.qBTApproving = qBTApproving;
	}
	public Integer getqBTPass() {
		return qBTPass;
	}
	public void setqBTPass(Integer qBTPass) {
		this.qBTPass = qBTPass;
	}
	public Integer getqBTUnpass() {
		return qBTUnpass;
	}
	public void setqBTUnpass(Integer qBTUnpass) {
		this.qBTUnpass = qBTUnpass;
	}
	public Integer getqDApproving() {
		return qDApproving;
	}
	public void setqDApproving(Integer qDApproving) {
		this.qDApproving = qDApproving;
	}
	public Integer getqDPass() {
		return qDPass;
	}
	public void setqDPass(Integer qDPass) {
		this.qDPass = qDPass;
	}
	public Integer getqDUnpass() {
		return qDUnpass;
	}
	public void setqDUnpass(Integer qDUnpass) {
		this.qDUnpass = qDUnpass;
	}
	public Integer getqBTDApproving() {
		return qBTDApproving;
	}
	public void setqBTDApproving(Integer qBTDApproving) {
		this.qBTDApproving = qBTDApproving;
	}
	public Integer getqBTDPass() {
		return qBTDPass;
	}
	public void setqBTDPass(Integer qBTDPass) {
		this.qBTDPass = qBTDPass;
	}
	public Integer getqBTDUnpass() {
		return qBTDUnpass;
	}
	public void setqBTDUnpass(Integer qBTDUnpass) {
		this.qBTDUnpass = qBTDUnpass;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	

}
