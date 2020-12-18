package cn.cf.entity;

import java.math.BigDecimal;

public class SupplierSaleDataReport {
	private String date;
	private String pk;
	private String name;
	private String accountPk;
	private String accountName;
	private int POYnum=0;
	private Double POYprice=0.0;
	private Double POYweight=0.0;
	private BigDecimal POYpriceb;
	private BigDecimal POYweightb;
	private String POYRatio;
	private int FDYnum=0;
	private Double FDYprice=0.0;
	private Double FDYweight=0.0;
	private BigDecimal FDYpriceb;
	private BigDecimal FDYweightb;
	private String FDYRatio;
	private int DTYnum=0;
	private Double DTYprice=0.0;
	private Double DTYweight=0.0;
	private BigDecimal DTYpriceb;
	private BigDecimal DTYweightb;
	private String DTYRatio;
	private int CUTnum=0;
	private Double CUTprice=0.0;
	private Double CUTweight=0.0;
	private BigDecimal CUTpriceb;
	private BigDecimal CUTweightb;
	private String CUTRatio;
	private int othernum=0;
	private Double otherprice=0.0;
	private Double otherweight=0.0;
	private BigDecimal otherpriceb;
	private BigDecimal otherweightb;
	private String otherRatio;
	private String[] accountArry;
	
	
	
	
	public BigDecimal getPOYpriceb() {
		return POYpriceb;
	}
	public void setPOYpriceb(BigDecimal pOYpriceb) {
		POYpriceb = pOYpriceb;
	}
	public BigDecimal getPOYweightb() {
		return POYweightb;
	}
	public void setPOYweightb(BigDecimal pOYweightb) {
		POYweightb = pOYweightb;
	}
	public BigDecimal getFDYpriceb() {
		return FDYpriceb;
	}
	public void setFDYpriceb(BigDecimal fDYpriceb) {
		FDYpriceb = fDYpriceb;
	}
	public BigDecimal getFDYweightb() {
		return FDYweightb;
	}
	public void setFDYweightb(BigDecimal fDYweightb) {
		FDYweightb = fDYweightb;
	}
	public BigDecimal getDTYpriceb() {
		return DTYpriceb;
	}
	public void setDTYpriceb(BigDecimal dTYpriceb) {
		DTYpriceb = dTYpriceb;
	}
	public BigDecimal getDTYweightb() {
		return DTYweightb;
	}
	public void setDTYweightb(BigDecimal dTYweightb) {
		DTYweightb = dTYweightb;
	}
	public BigDecimal getCUTpriceb() {
		return CUTpriceb;
	}
	public void setCUTpriceb(BigDecimal cUTpriceb) {
		CUTpriceb = cUTpriceb;
	}
	public BigDecimal getCUTweightb() {
		return CUTweightb;
	}
	public void setCUTweightb(BigDecimal cUTweightb) {
		CUTweightb = cUTweightb;
	}
	public BigDecimal getOtherpriceb() {
		return otherpriceb;
	}
	public void setOtherpriceb(BigDecimal otherpriceb) {
		this.otherpriceb = otherpriceb;
	}
	public BigDecimal getOtherweightb() {
		return otherweightb;
	}
	public void setOtherweightb(BigDecimal otherweightb) {
		this.otherweightb = otherweightb;
	}
	public String[] getAccountArry() {
		return accountArry;
	}
	public void setAccountArry(String[] accountArry) {
		this.accountArry = accountArry;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getPk() {
		return pk;
	}
	public void setPk(String pk) {
		this.pk = pk;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAccountPk() {
		return accountPk;
	}
	public void setAccountPk(String accountPk) {
		this.accountPk = accountPk;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public int getPOYnum() {
		return POYnum;
	}
	public void setPOYnum(int pOYnum) {
		POYnum = pOYnum;
	}
	
	public String getPOYRatio() {
		return POYRatio;
	}
	public void setPOYRatio(String pOYRatio) {
		POYRatio = pOYRatio;
	}
	public int getFDYnum() {
		return FDYnum;
	}
	public void setFDYnum(int fDYnum) {
		FDYnum = fDYnum;
	}
	
	public String getFDYRatio() {
		return FDYRatio;
	}
	public void setFDYRatio(String fDYRatio) {
		FDYRatio = fDYRatio;
	}
	public int getDTYnum() {
		return DTYnum;
	}
	public void setDTYnum(int dTYnum) {
		DTYnum = dTYnum;
	}
	
	public String getDTYRatio() {
		return DTYRatio;
	}
	public void setDTYRatio(String dTYRatio) {
		DTYRatio = dTYRatio;
	}
	public int getCUTnum() {
		return CUTnum;
	}
	public void setCUTnum(int cUTnum) {
		CUTnum = cUTnum;
	}

	public String getCUTRatio() {
		return CUTRatio;
	}
	public void setCUTRatio(String cUTRatio) {
		CUTRatio = cUTRatio;
	}
	public int getOthernum() {
		return othernum;
	}
	public void setOthernum(int othernum) {
		this.othernum = othernum;
	}
	
	public Double getCUTprice() {
		return CUTprice;
	}
	public void setCUTprice(Double cUTprice) {
		CUTprice = cUTprice;
	}
	public Double getCUTweight() {
		return CUTweight;
	}
	public void setCUTweight(Double cUTweight) {
		CUTweight = cUTweight;
	}
	public Double getOtherprice() {
		return otherprice;
	}
	public void setOtherprice(Double otherprice) {
		this.otherprice = otherprice;
	}
	public Double getOtherweight() {
		return otherweight;
	}
	public void setOtherweight(Double otherweight) {
		this.otherweight = otherweight;
	}
	public String getOtherRatio() {
		return otherRatio;
	}
	public void setOtherRatio(String otherRatio) {
		this.otherRatio = otherRatio;
	}
	public Double getPOYprice() {
		return POYprice;
	}
	public void setPOYprice(Double pOYprice) {
		POYprice = pOYprice;
	}
	public Double getPOYweight() {
		return POYweight;
	}
	public void setPOYweight(Double pOYweight) {
		POYweight = pOYweight;
	}
	public Double getFDYprice() {
		return FDYprice;
	}
	public void setFDYprice(Double fDYprice) {
		FDYprice = fDYprice;
	}
	public Double getFDYweight() {
		return FDYweight;
	}
	public void setFDYweight(Double fDYweight) {
		FDYweight = fDYweight;
	}
	public Double getDTYprice() {
		return DTYprice;
	}
	public void setDTYprice(Double dTYprice) {
		DTYprice = dTYprice;
	}
	public Double getDTYweight() {
		return DTYweight;
	}
	public void setDTYweight(Double dTYweight) {
		DTYweight = dTYweight;
	}
	
	
	
}
