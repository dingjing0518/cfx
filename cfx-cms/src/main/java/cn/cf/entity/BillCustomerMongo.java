package cn.cf.entity;

import java.util.Date;
import java.util.List;

import org.apache.poi.ss.formula.ptg.Ptg;

import cn.cf.dto.B2bBillCusgoodsApplyDto;


public class BillCustomerMongo {
	private String pk;
	private String companyPk;
	private String companyName;
	private String contacts;
	private String contactsTel;
	private Date insertTime;
	private Integer status;
	private String assidirPk;
	private String assidirName;
	private Date 	approveTime;
	private String processInstanceId;
	private List<B2bBillCusgoodsApplyDto> goodsList;
	private  String productName;
	private String address;
	public String getPk() {
		return pk;
	}
	public void setPk(String pk) {
		this.pk = pk;
	}
	public String getCompanyPk() {
		return companyPk;
	}
	public void setCompanyPk(String companyPk) {
		this.companyPk = companyPk;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getContacts() {
		return contacts;
	}
	public void setContacts(String contacts) {
		this.contacts = contacts;
	}
	public String getContactsTel() {
		return contactsTel;
	}
	public void setContactsTel(String contactsTel) {
		this.contactsTel = contactsTel;
	}
	public Date getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getAssidirPk() {
		return assidirPk;
	}
	public void setAssidirPk(String assidirPk) {
		this.assidirPk = assidirPk;
	}
	public String getAssidirName() {
		return assidirName;
	}
	public void setAssidirName(String assidirName) {
		this.assidirName = assidirName;
	}
	public Date getApproveTime() {
		return approveTime;
	}
	public void setApproveTime(Date approveTime) {
		this.approveTime = approveTime;
	}
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	public List<B2bBillCusgoodsApplyDto> getGoodsList() {
		return goodsList;
	}
	public void setGoodsList(List<B2bBillCusgoodsApplyDto> goodsList) {
		this.goodsList = goodsList;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	
}
