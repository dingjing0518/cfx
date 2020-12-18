package cn.cf.common.creditpay.shanghai;

import java.util.Date;

import cn.cf.property.PropertyConfig;
import cn.cf.util.DateUtil;
import cn.cf.util.KeyUtils;

public class XmlUtils {

	/**
	 * 登录
	 * @param userId
	 * @param password
	 * @return
	 */
	public String  login(String userId,String password){
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version='1.0' encoding = 'GBK'?>");
		sb.append("<BOSEBankData>");
		sb.append("<opReq>");
		sb.append("<serialNo>"+KeyUtils.getFlow(6)+"</serialNo>");//交易序列号(客户端唯一标识)
		sb.append("<reqTime>"+DateUtil.formatYearMonthDayHms(new Date())+"</reqTime>");//客户端请求时间
		sb.append("<ReqParam>");
		sb.append("<userID>"+userId+"</userID>");//登录客户号
		sb.append("<userPWD>"+password+"</userPWD>");//登录密码
		sb.append("</ReqParam>");
		sb.append("</opReq>");
		sb.append("</BOSEBankData>");
		return sb.toString();
	}
	/**
	 * 查询客户额度
	 * @param customerNumber
	 * @return
	 */
	public String  customerAmount(String customerNumber){
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version='1.0' encoding = 'GBK'?>");
		sb.append("<BOSEBankData>");
		sb.append("<opReq>");
		sb.append("<serialNo>"+KeyUtils.getFlow(6)+"</serialNo>");//交易序列号(客户端唯一标识)
		sb.append("<reqTime>"+DateUtil.formatYearMonthDayHms(new Date())+"</reqTime>");//客户端请求时间
		sb.append("<ReqParam>");
		sb.append("<T24Id>"+customerNumber+"</T24Id>");//T24客户号
		sb.append("<AgreementNo></AgreementNo>");//授信协议编号(可选参数)
		sb.append("<BusiModeId>1</BusiModeId>");//客户号类型 1.T24客户号（默认）；2.客户平台id
		sb.append("</ReqParam>");
		sb.append("</opReq>");
		sb.append("</BOSEBankData>");
		return sb.toString();
	}
	/**
	 * 放款条件维护
	 * @param agreementNo
	 * @param t24Id
	 * @param operaType
	 * @param orderNumber
	 * @param amount
	 * @param endDate
	 * @param purchaserName
	 * @param supplierName
	 * @return
	 */
	public String  applyLoanCondition(String agreementNo,String t24Id,String operaType, String poId, Double amount,String endDate,String purchaserName,String supplierName){
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version='1.0' encoding = 'GBK'?>");
		sb.append("<BOSEBankData>");
		sb.append("<opReq>");
		sb.append("<serialNo>"+KeyUtils.getFlow(6)+"</serialNo>");//交易序列号(客户端唯一标识)
		sb.append("<reqTime>"+DateUtil.formatYearMonthDayHms(new Date())+"</reqTime>");//客户端请求时间
		sb.append("<ReqParam>");
		sb.append("<AgreementNo>"+agreementNo+"</AgreementNo>");//授信协议编号(可选参数)
		sb.append("<T24Id>"+t24Id+"</T24Id>");//T24客户号
		sb.append("<OperaType>"+operaType+"</OperaType>");//操作类型 1:新增  2:更新  3:删除
		sb.append("<opReqSet>");
		sb.append("<opRequest>");
		sb.append("<PoId>"+poId+"</PoId>");//订单/采购合同编号
		sb.append("<PoRef>"+poId+"</PoRef>");//订单/采购合同流水号
		sb.append("<PoType>8</PoType>");//类型
		sb.append("<PoMode>0</PoMode>");//采购合同模式
		sb.append("<Ccy>CNY</Ccy>");//币种
		sb.append("<PoSumAmt>"+amount.toString()+"</PoSumAmt>");//采购合同总金额
		sb.append("<TotalNum></TotalNum>");//采购合同总数量
		sb.append("<PoStart>"+DateUtil.formatYearMonthDay(new Date())+"</PoStart>");//采购合同生效日
		sb.append("<PoEnd>"+endDate+"</PoEnd>");//采购合同到期日
		sb.append("<PoLoanRt></PoLoanRt>");
		sb.append("<BuyId>"+PropertyConfig.getProperty("sh_shop_no")+"</BuyId>");//买方客户号
		sb.append("<BuyName></BuyName>");//买方客户号
		sb.append("<SupplyNm>"+supplierName+"</SupplyNm>");//供应商名称
		sb.append("<CustNm>"+purchaserName+"</CustNm>");//采购商名称
		sb.append("</opRequest>");
		sb.append("</opReqSet>");
		sb.append("</ReqParam>");
		sb.append("</opReq>");
		sb.append("</BOSEBankData>");
		return sb.toString();
	}
	/**
	 * 放款申请
	 * @param agreementNo
	 * @param t24Id
	 * @param operaType
	 * @param orderNumber
	 * @param amount
	 * @param endDate
	 * @param purchaserName
	 * @param supplierName
	 * @return
	 */
	public String  applyLoan(String loanAccount, String agreementNo,String contractNumber,String t24Id,String entPayNo,String  term,String orderNumber,Double amount){
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version='1.0' encoding = 'GBK'?>");
		sb.append("<BOSEBankData>");
		sb.append("<opReq>");
		sb.append("<serialNo>"+loanAccount+"</serialNo>");//交易序列号(客户端唯一标识)
		sb.append("<reqTime>"+DateUtil.formatYearMonthDayHms(new Date())+"</reqTime>");//客户端请求时间
		sb.append("<ReqParam>");
		sb.append("<AgreementNo>"+agreementNo+"</AgreementNo>");//授信协议流水号
		sb.append("<LoanCntrNo>"+contractNumber+"</LoanCntrNo>");//贷款合同号
		sb.append("<MemberId>"+t24Id+"</MemberId>");//T24客户号
		sb.append("<MarginNo></MarginNo>");
		sb.append("<EntPayNo>"+entPayNo+"</EntPayNo>");//受托支付账户
		sb.append("<Currency>CNY</Currency>");//币种
		sb.append("<LoanAmt>"+amount.toString()+"</LoanAmt>");//金融
		sb.append("<PayNo></PayNo>");
		sb.append("<Term>"+term+"</Term>");
		sb.append("<opReqSet>");
		sb.append("<opRequest>");
		sb.append("<LoanCondId1>"+orderNumber+"</LoanCondId1>");//放款条件中的：PoId
		sb.append("<ConditionId1>8</ConditionId1>");
		sb.append("<LoanCondAmt1>"+amount.toString()+"</LoanCondAmt1>");
		sb.append("<LoanCondId2></LoanCondId2>");
		sb.append("<ConditionId2></ConditionId2>");
		sb.append("<LoanCondAmt2></LoanCondAmt2>");
		sb.append("</opRequest>");
		sb.append("</opReqSet>");
		sb.append("</ReqParam>");
		sb.append("</opReq>");
		sb.append("</BOSEBankData>");
		return sb.toString();
	}
	
	/**
	 * 放款查询
	 * @param orderNumber
	 * @return
	 */
	public String  searchLoan(String orderNumber){
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version='1.0' encoding = 'GBK'?>");
		sb.append("<BOSEBankData>");
		sb.append("<opReq>");
		sb.append("<serialNo>"+KeyUtils.getFlow(6)+"</serialNo>");//交易序列号(客户端唯一标识)
		sb.append("<reqTime>"+DateUtil.formatYearMonthDayHms(new Date())+"</reqTime>");//客户端请求时间
		sb.append("<ReqParam>");
		sb.append("<ChannelLoanNo>"+orderNumber+"</ChannelLoanNo>");//原来的交易序列号
		sb.append("</ReqParam>");
		sb.append("</opReq>");
		sb.append("</BOSEBankData>");
		return sb.toString();
	}

	/**
	 * 贷款信息查询
	 * @param orderNumber
	 * @return
	 */
	public String  searchloanApply(String loanNumber,String platformId,String customerNumber,String startDate,String endDate){
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version='1.0' encoding = 'GBK'?>");
		sb.append("<BOSEBankData>");
		sb.append("<opReq>");
		sb.append("<serialNo>"+KeyUtils.getFlow(6)+"</serialNo>");//交易序列号(客户端唯一标识)
		sb.append("<reqTime>"+DateUtil.formatYearMonthDayHms(new Date())+"</reqTime>");//客户端请求时间
		sb.append("<ReqParam>");
		sb.append("<PlatformId>"+platformId+"</PlatformId>");//平台客户号
		sb.append("<MemberId>"+customerNumber+"</MemberId>");//借款人客户号
		sb.append("<LoanNo>"+loanNumber+"</LoanNo>");//借据号
		sb.append("<Sdate>"+startDate+"</Sdate>");//开始日期
		sb.append("<Edate>"+endDate+"</Edate>");//结束日期
		sb.append("</ReqParam>");
		sb.append("</opReq>");
		sb.append("</BOSEBankData>");
		return sb.toString();
	}

	
	/**
	 * 还款查询
	 * @param orderNumber
	 * @return
	 */
	public String  searchRepayment(String loanNumber,String customerNumber,String startDate,String endDate){
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version='1.0' encoding = 'GBK'?>");
		sb.append("<BOSEBankData>");
		sb.append("<opReq>");
		sb.append("<serialNo>"+KeyUtils.getFlow(6)+"</serialNo>");//交易序列号(客户端唯一标识)
		sb.append("<reqTime>"+DateUtil.formatYearMonthDayHms(new Date())+"</reqTime>");//客户端请求时间
		sb.append("<ReqParam>");
		sb.append("<LoanNo>"+loanNumber+"</LoanNo>");//借据号
		sb.append("<MemberId>"+customerNumber+"</MemberId>");//借款人客户号
		sb.append("<Sdate>"+startDate+"</Sdate>");//开始日期
		sb.append("<Edate>"+endDate+"</Edate>");//结束日期
		sb.append("</ReqParam>");
		sb.append("</opReq>");
		sb.append("</BOSEBankData>");
		return sb.toString();
	}
	
	
	/**
	 * 费用代扣
	 * @param orderNumber
	 * @return
	 */
	public String  agentPay(String repeymentId,String purchaserName,String amount,String agreementNo){
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version='1.0' encoding = 'GBK'?>");
		sb.append("<BOSEBankData>");
		sb.append("<opReq>");
		sb.append("<serialNo>"+KeyUtils.getFlow(6)+"</serialNo>");//交易序列号(客户端唯一标识)
		sb.append("<reqTime>"+DateUtil.formatYearMonthDayHms(new Date())+"</reqTime>");//客户端请求时间
		sb.append("<ReqParam>");
		sb.append("<CustName>"+purchaserName+"</CustName>");//客户名称
		sb.append("<AgreementNo>"+agreementNo+"</AgreementNo>");//授信协议号
		sb.append("<CCY>CNY</CCY>");//币种
		sb.append("<Amt>"+amount.toString()+"</Amt>");//金额
		sb.append("<FeeType>L</FeeType>");//S：按赎货I：按应收账款L：按借据A：按协议
		sb.append("<ReqRef>"+repeymentId+"</ReqRef>");//赎货申请编号,标示一笔业务唯一性，防止重复发送。
		sb.append("</ReqParam>");
		sb.append("</opReq>");
		sb.append("</BOSEBankData>");
		return sb.toString();
	}

	/**
	 * 授信协议及合同查询
	 * @param agreementNo
	 * @param t24Id
	 * @param busiModeId
	 * @return
	 */
	public   String searchLimitAmt(String t24Id){
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version='1.0' encoding = 'GBK'?>");
		sb.append("<BOSEBankData>");
		sb.append("<opReq>");
		sb.append("<serialNo>"+KeyUtils.getFlow(6)+"</serialNo>");//交易序列号(客户端唯一标识)
		sb.append("<reqTime>"+DateUtil.formatYearMonthDayHms(new Date())+"</reqTime>");//客户端请求时间
		sb.append("<ReqParam>");
		sb.append("<T24Id>"+t24Id+"</T24Id>");//t24客户号
		sb.append("<AgreementNo>"+"</AgreementNo>");//授信协议编号
		sb.append("<BusiModeId>"+"</BusiModeId>");//业务模式代码
		sb.append("</ReqParam>");
		sb.append("</opReq>");
		sb.append("</BOSEBankData>");
		return sb.toString();


	}
	
}
