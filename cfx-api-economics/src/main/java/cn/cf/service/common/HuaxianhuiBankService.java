package cn.cf.service.common;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import cn.cf.common.RestCode;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bLoanNumberDto;
import cn.cf.dto.B2bMemberDto;
import cn.cf.dto.B2bOnlinepayRecordDto;
import cn.cf.entity.B2bRepaymentRecord;

/**
 * 
 * @description:银行与化纤汇中间业务桥接层
 * @author FJM
 * @date 2018-5-17 下午8:10:42
 */
public interface HuaxianhuiBankService {
	
	
	/**
	 * 更新银行授信额度
	 * @param b2bCompanyDtoEx
	 */
	void updateCreditAmount(B2bCompanyDto b2bCompanyDtoEx);
	
	/**
	 * 订单支付
	 * @param orderNumber
	 * @param password
	 * @param dto
	 * @param paymentPk
	 * @param paymentName
	 * @param creditGoodsPk
	 * @return
	 */
	Map<String,Object> updateOrder(String orderNumber,String contractNo,String password,B2bCompanyDto dto,Integer paymentType,String paymentName,String creditGoodsPk,B2bMemberDto member);
	
	/**
	 * 更新放款信息
	 * @param odto
	 */
	void updateLoanDetails(B2bLoanNumberDto odto);
	
	/**
	 * 更新还款信息
	 * @param odto
	 */
	void updateRepaymentDetails(B2bLoanNumberDto odto);
	
	/**
	 * 更新交易记录
	 * @param orderNumber
	 */
	void updateFinanceRecords(String orderNumber);
	
	/**
	 * 代扣申请提交
	 * @param odto
	 * @param record
	 */
	void updateAgentPay(B2bLoanNumberDto odto,List<B2bRepaymentRecord> recordList);
	
	/**
	 * 代扣申请查询
	 * @param odto
	 * @param record
	 */
	void updateAgentQry(B2bLoanNumberDto odto,String agentPayNumber);
	/**
	 * 支付成功后跳转新页面
	 * @param token
	 * @param orderNumber
	 * @param resp
	 */
	void jumpLoanPage(Integer source,String orderNumber,HttpServletResponse resp);
	
	/**
	 * 线上支付
	 * @param orderNumber
	 * @param contractNo
	 * @param goodsPk
	 * @return
	 */
	String onlinePay(String orderNumber,String contractNo,String goodsPk,String paymentName,Integer paymentType);
	/**
	 * 线上支付查询
	 * @param record
	 */
	void onlinePaySearch(B2bOnlinepayRecordDto record);
	/**
	 * 线上支付关闭
	 * @param record
	 */
	 Map<String,Object>  onlinePayCancel(B2bOnlinepayRecordDto record);
	 /**
	  * 修改委托放款信息
	  * @param o
	  */
	String updateEntrustLoanDetails(B2bLoanNumberDto o,Integer status);
	/**
	 * 修改委托还款信息
	 * @param o
	 * @param status
	 * @return
	 */
	String updateEntrusRepayment(B2bLoanNumberDto o,Double principal,Double interest);
	
	/**
	 * 整合银行客户推送信息
	 * @param rest
	 * @param type 银行分类(3:建设银行)
	 * @return
	 */
	String integrationCreditInfo(String rest,String bankName);
	/**
	 * 整合银行订单结果推送信息
	 * @param encodeData
	 * @param bankJianshe
	 * @return
	 */
	RestCode orderPayResult(String rest, String bankName);
	/**
	 * 金融产品客户申请
	 * @param companyPk
	 * @param bankPk
	 * @return
	 */
	String creditCustomerApply(String companyPk);
}
