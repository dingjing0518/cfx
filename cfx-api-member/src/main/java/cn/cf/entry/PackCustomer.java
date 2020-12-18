package cn.cf.entry;

import cn.cf.model.B2bCompany;
import cn.cf.model.B2bCustomerManagement;
import cn.cf.model.B2bCustomerSalesman;
import cn.cf.model.B2bMember;

public class PackCustomer {
	
	private B2bCompany company;
	private B2bMember member;
	private B2bCustomerManagement customerManagement;
	private B2bCustomerSalesman salesman;
	
	public PackCustomer(CustomerImport customerImport,String storePk) {
		company = new B2bCompany();
		member = new B2bMember();
		customerManagement = new B2bCustomerManagement();
		salesman = new B2bCustomerSalesman();
		
		company.setName(customerImport.getCompanyName());
		company.setOrganizationCode(customerImport.getOrganizationCode());
		company.setContacts(customerImport.getContracts());
		company.setContactsTel(customerImport.getContractsTel());
		company.setProvinceName(customerImport.getProvinceName());
		company.setCityName(customerImport.getCityName());
		company.setAreaName(customerImport.getAreaName());
		company.setRegAddress(customerImport.getAddress());
		company.setBankName(customerImport.getBankName());
		company.setBankAccount(customerImport.getBankAccount());
		company.setBlUrl(customerImport.getBlUrl());
		company.setEcUrl(customerImport.getEcUrl());
		
		member.setMobile(customerImport.getMobile());
		
		customerManagement.setStorePk(storePk);
		
		salesman.setEmployeeName(customerImport.getEmployeeName());
		salesman.setEmployeeNumber(customerImport.getEmployeeNumber());
		salesman.setPurchaserName(customerImport.getCompanyName());
		salesman.setStorePk(storePk);
		
	}

	public B2bCompany getCompany() {
		return company;
	}

	public void setCompany(B2bCompany company) {
		this.company = company;
	}

	public B2bMember getMember() {
		return member;
	}

	public void setMember(B2bMember member) {
		this.member = member;
	}

	public B2bCustomerManagement getCustomerManagement() {
		return customerManagement;
	}

	public void setCustomerManagement(B2bCustomerManagement customerManagement) {
		this.customerManagement = customerManagement;
	}

	public B2bCustomerSalesman getSalesman() {
		return salesman;
	}

	public void setSalesman(B2bCustomerSalesman salesman) {
		this.salesman = salesman;
	}
	
	

}
