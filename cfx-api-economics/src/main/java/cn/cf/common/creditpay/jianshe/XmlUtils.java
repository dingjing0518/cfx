package cn.cf.common.creditpay.jianshe;

import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bLoanNumberDto;
import cn.cf.entity.B2bCreditDtoMa;

public class XmlUtils {
	
	/**
	 * 发送token
	 * @param company
	 * @return
	 */
	public static String  getToken(){
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version='1.0' encoding = 'UTF-8'?>");
		sb.append("<Request>");
		sb.append("<Ext_Acs_MgTp_Cd>1</Ext_Acs_MgTp_Cd>");//操作类型
		sb.append("<Ext_Acs_Tokn>TOKEN</Ext_Acs_Tokn>");//token令牌
		sb.append("</Request>");
		return sb.toString();
	}
	
	/**
	 * 发送客户授信申请
	 * @param company
	 * @return
	 */
	public static String  customerApply(B2bCompanyDto company,B2bCreditDtoMa credit){
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version='1.0' encoding = 'UTF-8'?>");
		sb.append("<request>");
		sb.append("<Stk_Cst_Ind>1</Stk_Cst_Ind>");
		sb.append("<Ahn_Ind>1</Ahn_Ind>");
		sb.append("<EntNm>"+company.getName()+"</EntNm>");
		sb.append("<Unn_Soc_Cr_Cd>"+company.getOrganizationCode()+"</Unn_Soc_Cr_Cd>");
		sb.append("<HpCnt>"+credit.getInfo().getHpcnt()+"</HpCnt>");
		sb.append("<Prch_Amt>"+credit.getInfo().getPrchAmount()+"</Prch_Amt>");
		sb.append("<Acm_Pch_Cnt>"+credit.getInfo().getPrchCount()+"</Acm_Pch_Cnt>");
		sb.append("<Cst_Nm>"+credit.getInfo().getRcName()+"</Cst_Nm>");
		sb.append("<Crdt_TpCd>"+credit.getInfo().getRcDocType()+"</Crdt_TpCd>");
		sb.append("<Crdt_No>"+credit.getInfo().getRcDocNumber()+"</Crdt_No>");
		sb.append("<MblPh_No>"+credit.getInfo().getRcDocMobile()+"</MblPh_No>");
		sb.append("<TaxPymt_Amt>"+credit.getInfo().getTaxAmount()+"</TaxPymt_Amt>");
		sb.append("<Co_StDt>"+credit.getInfo().getCoopStartDate()+"</Co_StDt>");
		sb.append("<Aply_CrGLn>"+credit.getInfo().getCreditAmount()+"</Aply_CrGLn>");
		sb.append("<Lgst_Ddln_Val>"+credit.getInfo().getLoanTrem()+"</Lgst_Ddln_Val>");
		sb.append("</request>");
		return sb.toString();
	}
	
	/**
	 * 查询客户额度信息、借款信息
	 * @param company
	 * @return
	 */
	public static String  searchCustomerAmount(String accountNumber){
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version='1.0' encoding = 'UTF-8'?>");
		sb.append("<request>");
		sb.append("<Loan_AccNo>"+accountNumber+"</Loan_AccNo>");
		sb.append("</request>");
		return sb.toString();
	}
	
	/**
	 * 查询借据还款线信息
	 * @param company
	 * @return
	 */
	public static String  searchCustomerRepayment(B2bLoanNumberDto loanNumber){
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version='1.0' encoding = 'UTF-8'?>");
		sb.append("<request>");
		sb.append("<Loan_AccNo>"+loanNumber.getLoanNumber()+"</Loan_AccNo>");
		sb.append("<QRY_START_DT> </QRY_START_DT>");
		sb.append("<QRY_END_DT> </QRY_END_DT>");
		sb.append("<TX_TYPE>1</TX_TYPE>");
		sb.append("</request>");
		return sb.toString();
	}

}
