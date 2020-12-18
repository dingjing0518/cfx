package cn.cf.service.bill;

import java.util.List;

import cn.cf.dto.B2bBillCusgoodsDto;
import cn.cf.dto.B2bBillCustomerDto;
import cn.cf.dto.B2bBillInventoryDto;
import cn.cf.dto.B2bBillOrderDto;
import cn.cf.dto.B2bBillSigncardDto;
import cn.cf.dto.SysCompanyBankcardDto;
import cn.cf.entity.B2bOrderDtoMa;
import cn.cf.entry.BankBaseResp;
/**
 * 宁波银行票据支付管理
 * @description:
 * @author FJM
 * @date 2019-7-29 下午2:14:37
 */
public interface BillNingboService {
	/**
	 * 票付通绑定
	 * @param companyPk 企业平台用户
	 * @param companyName 企业名称
	 * @param organizationCode 企业统一社会信用代码
	 * @param account 企业电票开户账号
	 * @param bankNo 企业电票开户行号
	 * @return
	 */
	BankBaseResp bindBillPft(String companyPk,String companyName,String organizationCode,List<B2bBillSigncardDto> cards,String bindType);
	
	
	/**
	 * 票付通绑定查询
	 * @param organizationCode 企业统一社会信用代码
	 * @return
	 */
	BankBaseResp searchBillPft(String organizationCode,Integer status);
	
	/**
	 * 票付通支付
	 * @param order 订单信息
	 * @param goods 票据产品
	 * @param card 收款人卡号
	 * @param billType 票据类型 DT00 无限制  DT01 商承票  DT02 银承票
	 * @param payType //0非见票支付  1见票支付
	 * @return
	 */
	BankBaseResp payPft(B2bOrderDtoMa order,B2bBillCusgoodsDto goods,SysCompanyBankcardDto card,Integer billType,Integer payType,String mesgid);
	
	/**
	 * 票付通订单查询
	 * @param orderNumber
	 * @return
	 */
	BankBaseResp searchOrderPft(String orderNumber);
	
	/**
	 * 票付通支付流水查询
	 * @param orderNumber
	 * @return
	 */
	BankBaseResp searchPayinfoPft(String orderNumber);
	
	/**
	 * 票付通票据解锁
	 * @param orderNumber
	 * @param type 0签收 1取消
	 * @return
	 */
	BankBaseResp unlockOrderPft(String paymentNo,List<B2bBillInventoryDto> list,Integer type);
	
	
	/**
	 * 票付通支付流水更新
	 * @param odto
	 * @param status 0取消 1完成
	 * @return
	 */
	BankBaseResp updateSerialNumberPft(B2bBillOrderDto odto,Integer status);
	/**
	 * 票付通订单附加信息更新
	 * @param odto
	 * @param status 0取消 1完成
	 * @return
	 */
	BankBaseResp updateOrderInfoPft(B2bBillOrderDto odto,Integer status);
	
	/**
	 * 贴现支付
	 * @param order 订单信息
	 * @param goods 票据产品
	 * @param card 收款人卡号
	 * @param customer 票据客户
	 * @param flag true 新增 false 取消
	 * @return
	 */
	BankBaseResp payTx(B2bOrderDtoMa order,SysCompanyBankcardDto card,B2bBillCustomerDto customer,Integer status);

	/**
	 * 贴现订单查询
	 * @param orderNumber
	 * @return
	 */
	BankBaseResp searchOrderTx(String orderNumber);
	
	
	/**
	 * 贴现订单详细查询
	 * @param orderNumber
	 * @return
	 */
	BankBaseResp searchOrderDetailsTx(String orderNumber);
	
	/**
	 * 贴现额度查询
	 * @return
	 */
	BankBaseResp searchAmountTx();
	
	/**
	 *  贴现资金流向查询
	 * @param payerAmount
	 * @param payerBankNo
	 * @param payeeAmount
	 * @param payeeBankNo
	 * @return
	 */
	BankBaseResp searchOrderFlowTx(String payerAmount,String payerBankNo,String payeeAmount,String payeeBankNo);
	

}
