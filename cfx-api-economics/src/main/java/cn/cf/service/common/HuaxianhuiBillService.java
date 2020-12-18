package cn.cf.service.common;

import java.util.Map;

import cn.cf.dto.B2bBillCusgoodsDtoEx;
import cn.cf.dto.B2bBillOrderDto;
import cn.cf.dto.B2bBillSigningDto;



/**
 * 
 * @description:化纤汇与票据业务桥接层
 * @author FJM
 * @date 2019-7-26 上午9:41:55
 */
public interface HuaxianhuiBillService {

	/**
	 * 票付通绑定/解绑
	 * @param companyPk 企业平台用户
	 * @param companyName 企业名称
	 * @param organizationCode 企业统一社会信用代码
	 * @param account 企业电票开户账号
	 * @param account 企业电票开户行名
	 * @param bankNo 企业电票开户行号
	 * @param bindType 绑定类型BD01 绑定  BD00 解绑
	 * @return
	 */
	String bindBill(String companyPk,String companyName,String organizationCode,String account,String bankNo,String bindType);
	
	/**
	 * 票付通客户绑定查询
	 * @param companyPk
	 * @param organizationCode
	 * @return
	 */
	String searchBillPft(B2bBillCusgoodsDtoEx dto);
	
	
	/**
	 * 票付通供应商签约查询
	 * @param companyPk
	 * @param organizationCode
	 * @return
	 */
	String searchBillSignPft(B2bBillSigningDto sign);
	
	/**
	 * 票据支付
	 * @param orderNumber
	 * @param contractNo
	 * @param dto
	 * @param paymentType
	 * @param paymentName
	 * @param billGoodsPk
	 * @param member
	 * @return
	 */
	String payOrder(String orderNumber,String contractNo,Integer paymentType,String paymentName,String billGoodsPk);
	
	/**
	 * 票据订单查询票据信息
	 * @param orderNumber
	 * @return
	 */
	String searchBillInfo(String orderNumber);
	/**
	 * 票付通支付记录查询
	 * @param orderNumber
	 * @return
	 */
	String searchPayInfoPft(String orderNumber);
	/**
	 * 取消票据支付
	 * @param order
	 * @return
	 */
	 Map<String,Object> billPayCancel(B2bBillOrderDto order); 
	 
	 /**
	  * 贴现额度查询
	  * @param orderNumber
	  * @return
	  */	 		
	 String searchAmountTx();
	 /**
	  * 供应商签约
	  * @param companyPk
	  * @return
	  */
	 String billSinging(String companyPk);
	 /**
	  * 
	  * @param companyPk
	  * @param billGoodsPk
	  * @return
	  */
	 String acceptTx(String companyPk,String billGoodsPk);
}
