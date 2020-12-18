package cn.cf.entity;

import javax.persistence.Id;
import java.util.Date;


public class B2bRepaymentRecord {
	@Id
	private String id;
	private String orderNumber;
	private String iouNumber;
	private String createTime;
	private Double amount;//实还本金
	private Double interest;//实还利息
	private Double penalty;//实还罚息
	private Double compound;//实还复利
	private Integer status;//1 成功  -1失败
	private String companyPk;
	private String companyName;
	private String organizationCode;
	private String bankPk;
	private String bankName;
	private Double serviceCharge;//应收服务费
	private Double sevenCharge;//七日服务费
	private Double interestReceivable;//应收利息
	private Integer agentPayStatus;//服务费支付状态1支付中2成功3失败
	private String repaymentNumber;//还款流水号

	private Date loanStartTime;
	private Date loanEndTime;
	private Integer goodsType;//产品类型
	private String goodsName;//产品名称
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

	public String getIouNumber() {
		return iouNumber;
	}

	public void setIouNumber(String iouNumber) {
		this.iouNumber = iouNumber;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getInterest() {
		return interest;
	}

	public void setInterest(Double interest) {
		this.interest = interest;
	}

	public Double getPenalty() {
		return penalty;
	}

	public void setPenalty(Double penalty) {
		this.penalty = penalty;
	}

	public Double getCompound() {
		return compound;
	}

	public void setCompound(Double compound) {
		this.compound = compound;
	}

	public String getBankPk() {
		return bankPk;
	}

	public void setBankPk(String bankPk) {
		this.bankPk = bankPk;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public Double getServiceCharge() {
		return serviceCharge;
	}

	public void setServiceCharge(Double serviceCharge) {
		this.serviceCharge = serviceCharge;
	}

	public Double getInterestReceivable() {
		return interestReceivable;
	}

	public void setInterestReceivable(Double interestReceivable) {
		this.interestReceivable = interestReceivable;
	}

	public String getOrganizationCode() {
		return organizationCode;
	}

	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

	public Integer getAgentPayStatus() {
		return agentPayStatus;
	}

	public void setAgentPayStatus(Integer agentPayStatus) {
		this.agentPayStatus = agentPayStatus;
	}

	public Double getSevenCharge() {
		return sevenCharge;
	}

	public void setSevenCharge(Double sevenCharge) {
		this.sevenCharge = sevenCharge;
	}

	public String getRepaymentNumber() {
		return repaymentNumber;
	}

	public void setRepaymentNumber(String repaymentNumber) {
		this.repaymentNumber = repaymentNumber;
	}

	public Date getLoanStartTime() {
		return loanStartTime;
	}

	public void setLoanStartTime(Date loanStartTime) {
		this.loanStartTime = loanStartTime;
	}

	public Date getLoanEndTime() {
		return loanEndTime;
	}

	public void setLoanEndTime(Date loanEndTime) {
		this.loanEndTime = loanEndTime;
	}

	public Integer getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(Integer goodsType) {
		this.goodsType = goodsType;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
}
