package cn.cf.dto;

import cn.cf.dto.BaseDTO;

/**
 * 订单支付（额度支付）
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public class OrderPaymentLimitDto extends BaseDTO implements java.io.Serializable,Cloneable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7588825963597390624L;
	private String  customerNo;//转出平台用户号
	private String orderNo;//订单编号
	private String orderAmount;//订单总金额
	private String orderUse;//订单用途
	private String payeePlatformUser;//收款人平台用户号
	private String payeePlatName;//收款人名称
	private String  payeeName;//收款人户名
	private String payeeVirtualAccount;//收款人实体账户
	private String orderStatus;//订单状态
	private String borrowerPlatformUser;//借款人平台用户号
	private String  borrowerName;//借收款人户名
	private String borrowerVirtualAccount;//借收款人虚拟账户
	private String payeeBankName;//行名
	private String payeeBankNo;//行号
	
	private String rspDate;//响应日期
	private String rspTime;//响应时间
	private String rspCode;//响应编码
	private String rspSerno;//响应编号
	private String rspMsg;//响应消息
	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}
	public String getOrderUse() {
		return orderUse;
	}
	public void setOrderUse(String orderUse) {
		this.orderUse = orderUse;
	}
	public String getPayeePlatformUser() {
		return payeePlatformUser;
	}
	public void setPayeePlatformUser(String payeePlatformUser) {
		this.payeePlatformUser = payeePlatformUser;
	}
	public String getPayeeName() {
		return payeeName;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}
	public String getPayeeVirtualAccount() {
		return payeeVirtualAccount;
	}
	public void setPayeeVirtualAccount(String payeeVirtualAccount) {
		this.payeeVirtualAccount = payeeVirtualAccount;
	}
	public String getBorrowerPlatformUser() {
		return borrowerPlatformUser;
	}
	public void setBorrowerPlatformUser(String borrowerPlatformUser) {
		this.borrowerPlatformUser = borrowerPlatformUser;
	}
	public String getBorrowerName() {
		return borrowerName;
	}
	public void setBorrowerName(String borrowerName) {
		this.borrowerName = borrowerName;
	}
	public String getBorrowerVirtualAccount() {
		return borrowerVirtualAccount;
	}
	public void setBorrowerVirtualAccount(String borrowerVirtualAccount) {
		this.borrowerVirtualAccount = borrowerVirtualAccount;
	}
	public String getRspDate() {
		return rspDate;
	}
	public void setRspDate(String rspDate) {
		this.rspDate = rspDate;
	}
	public String getRspTime() {
		return rspTime;
	}
	public void setRspTime(String rspTime) {
		this.rspTime = rspTime;
	}
	public String getRspCode() {
		return rspCode;
	}
	public void setRspCode(String rspCode) {
		this.rspCode = rspCode;
	}
	public String getRspSerno() {
		return rspSerno;
	}
	public void setRspSerno(String rspSerno) {
		this.rspSerno = rspSerno;
	}
	public String getRspMsg() {
		return rspMsg;
	}
	public void setRspMsg(String rspMsg) {
		this.rspMsg = rspMsg;
	}
	public String getPayeeBankName() {
		return payeeBankName;
	}
	public void setPayeeBankName(String payeeBankName) {
		this.payeeBankName = payeeBankName;
	}
	public String getPayeeBankNo() {
		return payeeBankNo;
	}
	public void setPayeeBankNo(String payeeBankNo) {
		this.payeeBankNo = payeeBankNo;
	}
	public String getPayeePlatName() {
		return payeePlatName;
	}
	public void setPayeePlatName(String payeePlatName) {
		this.payeePlatName = payeePlatName;
	}
	 
	public OrderPaymentLimitDto() {
		// TODO Auto-generated constructor stub
	}
	
	public OrderPaymentLimitDto(String customerNo, String orderNo,
			String orderAmount, String orderUse, String payeePlatformUser,
			String payeePlatName, String payeeName, String payeeVirtualAccount,
			String orderStatus, String borrowerPlatformUser,
			String borrowerName, String borrowerVirtualAccount,
			String payeeBankName, String payeeBankNo, String rspDate,
			String rspTime, String rspCode, String rspSerno, String rspMsg) {
		super();
		this.customerNo = customerNo;
		this.orderNo = orderNo;
		this.orderAmount = orderAmount;
		this.orderUse = orderUse;
		this.payeePlatformUser = payeePlatformUser;
		this.payeePlatName = payeePlatName;
		this.payeeName = payeeName;
		this.payeeVirtualAccount = payeeVirtualAccount;
		this.orderStatus = orderStatus;
		this.borrowerPlatformUser = borrowerPlatformUser;
		this.borrowerName = borrowerName;
		this.borrowerVirtualAccount = borrowerVirtualAccount;
		this.payeeBankName = payeeBankName;
		this.payeeBankNo = payeeBankNo;
		this.rspDate = rspDate;
		this.rspTime = rspTime;
		this.rspCode = rspCode;
		this.rspSerno = rspSerno;
		this.rspMsg = rspMsg;
	}
	
	@Override
	 public Object clone()    
    {  
        try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;  
    } 
	
}