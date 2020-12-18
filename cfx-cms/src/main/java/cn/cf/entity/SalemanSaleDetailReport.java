package cn.cf.entity;

import java.math.BigDecimal;

import org.bson.types.Decimal128;

public class SalemanSaleDetailReport {
	private String date ; 
	private String accountPk;
	private String accountName ;
	private Integer supcompanyNum =0 ;
	private Integer purcompanyNum =0;
	//采购商
	private Double pPOYWeight=0.0;
	private Double pDTYWeight=0.0;
	private Double pFDYWeight=0.0;
	private Double pCUTWeight=0.0;
	private Double pOtherWeight=0.0;
	//供应商
	private Double sPOYWeight=0.0;
	private Double sDTYWeight=0.0;
	private Double sFDYWeight=0.0;
	private Double sCUTWeight=0.0;
	private Double sOtherWeight=0.0;
	
	
	private BigDecimal pPOYWeightb;
	private BigDecimal pDTYWeightb;
	private BigDecimal pFDYWeightb;
	private BigDecimal pCUTWeightb;
	private BigDecimal pOtherWeightb;
	//供应商
	private BigDecimal sPOYWeightb;
	private BigDecimal sDTYWeightb;
	private BigDecimal sFDYWeightb;
	private BigDecimal sCUTWeightb;
	private BigDecimal sOtherWeightb;
	
	
	
	
	public BigDecimal getpPOYWeightb() {
		return pPOYWeightb;
	}
	public void setpPOYWeightb(BigDecimal pPOYWeightb) {
		this.pPOYWeightb = pPOYWeightb;
	}
	public BigDecimal getpDTYWeightb() {
		return pDTYWeightb;
	}
	public void setpDTYWeightb(BigDecimal pDTYWeightb) {
		this.pDTYWeightb = pDTYWeightb;
	}
	public BigDecimal getpFDYWeightb() {
		return pFDYWeightb;
	}
	public void setpFDYWeightb(BigDecimal pFDYWeightb) {
		this.pFDYWeightb = pFDYWeightb;
	}
	public BigDecimal getpCUTWeightb() {
		return pCUTWeightb;
	}
	public void setpCUTWeightb(BigDecimal pCUTWeightb) {
		this.pCUTWeightb = pCUTWeightb;
	}
	public BigDecimal getpOtherWeightb() {
		return pOtherWeightb;
	}
	public void setpOtherWeightb(BigDecimal pOtherWeightb) {
		this.pOtherWeightb = pOtherWeightb;
	}
	public BigDecimal getsPOYWeightb() {
		return sPOYWeightb;
	}
	public void setsPOYWeightb(BigDecimal sPOYWeightb) {
		this.sPOYWeightb = sPOYWeightb;
	}
	public BigDecimal getsDTYWeightb() {
		return sDTYWeightb;
	}
	public void setsDTYWeightb(BigDecimal sDTYWeightb) {
		this.sDTYWeightb = sDTYWeightb;
	}
	public BigDecimal getsFDYWeightb() {
		return sFDYWeightb;
	}
	public void setsFDYWeightb(BigDecimal sFDYWeightb) {
		this.sFDYWeightb = sFDYWeightb;
	}
	public BigDecimal getsCUTWeightb() {
		return sCUTWeightb;
	}
	public void setsCUTWeightb(BigDecimal sCUTWeightb) {
		this.sCUTWeightb = sCUTWeightb;
	}
	public BigDecimal getsOtherWeightb() {
		return sOtherWeightb;
	}
	public void setsOtherWeightb(BigDecimal sOtherWeightb) {
		this.sOtherWeightb = sOtherWeightb;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
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
	public Integer getSupcompanyNum() {
		return supcompanyNum;
	}
	public void setSupcompanyNum(Integer supcompanyNum) {
		this.supcompanyNum = supcompanyNum;
	}
	public Integer getPurcompanyNum() {
		return purcompanyNum;
	}
	public void setPurcompanyNum(Integer purcompanyNum) {
		this.purcompanyNum = purcompanyNum;
	}
	public Double getpPOYWeight() {
		return pPOYWeight;
	}
	public void setpPOYWeight(Double pPOYWeight) {
		this.pPOYWeight = pPOYWeight;
	}
	public Double getpDTYWeight() {
		return pDTYWeight;
	}
	public void setpDTYWeight(Double pDTYWeight) {
		this.pDTYWeight = pDTYWeight;
	}
	public Double getpFDYWeight() {
		return pFDYWeight;
	}
	public void setpFDYWeight(Double pFDYWeight) {
		this.pFDYWeight = pFDYWeight;
	}
	public Double getpCUTWeight() {
		return pCUTWeight;
	}
	public void setpCUTWeight(Double pCUTWeight) {
		this.pCUTWeight = pCUTWeight;
	}
	public Double getpOtherWeight() {
		return pOtherWeight;
	}
	public void setpOtherWeight(Double pOtherWeight) {
		this.pOtherWeight = pOtherWeight;
	}
	public Double getsPOYWeight() {
		return sPOYWeight;
	}
	public void setsPOYWeight(Double sPOYWeight) {
		this.sPOYWeight = sPOYWeight;
	}
	public Double getsDTYWeight() {
		return sDTYWeight;
	}
	public void setsDTYWeight(Double sDTYWeight) {
		this.sDTYWeight = sDTYWeight;
	}
	public Double getsFDYWeight() {
		return sFDYWeight;
	}
	public void setsFDYWeight(Double sFDYWeight) {
		this.sFDYWeight = sFDYWeight;
	}
	public Double getsCUTWeight() {
		return sCUTWeight;
	}
	public void setsCUTWeight(Double sCUTWeight) {
		this.sCUTWeight = sCUTWeight;
	}
	public Double getsOtherWeight() {
		return sOtherWeight;
	}
	public void setsOtherWeight(Double sOtherWeight) {
		this.sOtherWeight = sOtherWeight;
	}
	
}
