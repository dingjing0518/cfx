package cn.cf.dto;

import java.util.List;

import cn.cf.util.DateUtil;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class FinanceTransactionDto {
	

	private String pk;
	private String companyName;
	private String supplierName;
	private String supplierPk;
	private String transactionAccount;
	private String receivablesAccount;
	
	private String transactionAmount;
	private String transactionAccountName;
	private String receivablesAccountName;
	private String description;
	private String orderNumber;
	private String serialNumber;

	private String employeeName;
	private String employeeNumber;
	
	private String payTime;
	
	
	
	

	public String getPk() {
		return pk;
	}
	public void setPk(String pk) {
		this.pk = pk;
	}
	public String getPayTime() {
		return payTime;
	}
	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}
	public String getSupplierPk() {
		return supplierPk;
	}
	public void setSupplierPk(String supplierPk) {
		this.supplierPk = supplierPk;
	}

	private List<FinanceTransactionSubOrderDto>subOrderDetails;
	
	public List<FinanceTransactionSubOrderDto> getSubOrderDetails() {
		return subOrderDetails;
	}
	public void setSubOrderDetails(List<FinanceTransactionSubOrderDto> subOrderDetails) {
		this.subOrderDetails = subOrderDetails;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getTransactionAccount() {
		return transactionAccount;
	}
	public void setTransactionAccount(String transactionAccount) {
		this.transactionAccount = transactionAccount;
	}
	public String getReceivablesAccount() {
		return receivablesAccount;
	}
	public void setReceivablesAccount(String receivablesAccount) {
		this.receivablesAccount = receivablesAccount;
	}
	public String getTransactionAmount() {
		return transactionAmount;
	}
	public void setTransactionAmount(String transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
	public String getTransactionAccountName() {
		return transactionAccountName;
	}
	public void setTransactionAccountName(String transactionAccountName) {
		this.transactionAccountName = transactionAccountName;
	}
	public String getReceivablesAccountName() {
		return receivablesAccountName;
	}
	public void setReceivablesAccountName(String receivablesAccountName) {
		this.receivablesAccountName = receivablesAccountName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getEmployeeNumber() {
		return employeeNumber;
	}
	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}
	
public static FinanceTransactionDto getFinanceTransaction(B2bFinanceRecordsDto financeDto,List<FinanceTransactionSubOrderDto>subOrderDetails){
		FinanceTransactionDto transactionDto = new FinanceTransactionDto();
		
		transactionDto.setPk(financeDto.getPk());
		transactionDto.setCompanyName(financeDto.getCompanyName());
		transactionDto.setSupplierName(financeDto.getSupplierName());
		transactionDto.setSupplierPk(financeDto.getSupplierPk());
		transactionDto.setTransactionAccount(financeDto.getTransactionAccount());
		transactionDto.setReceivablesAccount(financeDto.getReceivablesAccount());
		transactionDto.setTransactionAmount(financeDto.getTransactionAmount()+"");
		transactionDto.setTransactionAccountName(financeDto.getTransactionAccountName());
		transactionDto.setReceivablesAccountName(financeDto.getReceivablesAccountName());
		transactionDto.setDescription(financeDto.getDescription());
		transactionDto.setOrderNumber(financeDto.getOrderNumber());
		transactionDto.setSerialNumber(financeDto.getSerialNumber());
		transactionDto.setEmployeeName(financeDto.getEmployeeName());
		transactionDto.setEmployeeNumber(financeDto.getEmployeeNumber());

		transactionDto.setPayTime(financeDto.getInsertTime()==null?"":DateUtil.formatDateAndTime(financeDto.getInsertTime()));
		transactionDto.setSubOrderDetails(subOrderDetails);
		
		return transactionDto;
	}
	
	
}