package cn.cf.entry;

import java.util.List;

public class RespCustomer {
	
	private String code;
	
	private String msg;
	
	private List<CustomerImport> customerList;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<CustomerImport> getCustomerList() {
		return customerList;
	}

	public void setCustomerList(List<CustomerImport> customerList) {
		this.customerList = customerList;
	}
	
	
}
