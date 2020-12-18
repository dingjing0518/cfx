package cn.cf.service.common;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.cf.dto.B2bBillCusgoodsDto;
import cn.cf.dto.B2bBillCusgoodsDtoEx;
import cn.cf.dto.B2bBillGoodsDto;
import cn.cf.dto.B2bBillInventoryDto;
import cn.cf.dto.B2bBillOrderDto;
import cn.cf.dto.B2bBillSigncardDto;
import cn.cf.dto.B2bBillSigningDto;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bCreditGoodsDto;
import cn.cf.dto.B2bLoanNumberDto;
import cn.cf.dto.B2bOnlinepayGoodsDto;
import cn.cf.dto.BankCreditDto;
import cn.cf.dto.SysCompanyBankcardDto;
import cn.cf.entity.B2bCreditGoodsDtoMa;
import cn.cf.entity.B2bOrderDtoMa;
import cn.cf.entity.B2bRepaymentRecord;
import cn.cf.entity.WzDownLoad;
import cn.cf.entry.BankBaseResp;

/**
 * 
 * @description:根据银行返回的信息进行化纤汇业务回调
 * @author FJM
 * @date 2018-5-22 下午9:36:10
 */
public interface HuaxianhuiService {
	
	/**
	 * 订单支付业务回调
	 * @param odto:订单
	 * @param paymentPk:支付方式
	 * @param paymentName:支付方式名称
	 * @param pdto:采购商信息
	 * @param creditGoods:金融产品
	 * @param cdto:授信银行
	 * @param bdto:供应商账户
	 * @param fdtos:一对多交易记录
	 */
	@Transactional
	void updateBackPyOrder(B2bOrderDtoMa odto,String contractNo,Integer paymentType,String paymentName,B2bCompanyDto pdto,B2bCreditGoodsDtoMa creditGoods,SysCompanyBankcardDto bdto,BankBaseResp resp);
	
	/**
	 * 查询订单放款记录
	 * @param odto:订单
	 * @param creditDto:银行返回的放款信息
	 */
	void updateBackLoanOrder(B2bLoanNumberDto odto,BankCreditDto creditDto);

	void updatezhLoanOrder(B2bLoanNumberDto odto,BankCreditDto creditDto);

	/**
	 * 查询订单还款记录(删除更新)
	 * @param odto 订单
	 * @param list 还款列表
	 */
	void updateBackRepayment(B2bOrderDtoMa om,B2bLoanNumberDto odto,List<B2bRepaymentRecord> list,Integer loanStatus,Date repaymentDate);
	
	/**
	 * 查询订单还款记录(累加递增)
	 * @param om 订单
	 * @param odto 借款单
	 * @param list 还款列表
	 * @param loanStatus 还款状态
	 * @param repaymentDate 最后还款时间
	 * @param flag 是否需要修改可用额度 true 是 false 否
	 * @return
	 */
	@Transactional
	List<B2bRepaymentRecord> updateBackRepaymentAnother(B2bOrderDtoMa om,B2bLoanNumberDto odto,List<B2bRepaymentRecord> list,Integer loanStatus,Date repaymentDate,Boolean flag);
	
	/**
	 * 代扣申请回调
	 * @param odto
	 * @param record
	 * @param resp
	 */
	void updateAngetPay(B2bLoanNumberDto odto,B2bRepaymentRecord record,BankBaseResp resp);
	/**
	 * 代扣回调查询
	 * @param odto
	 */
	void updateAngetQry(B2bRepaymentRecord record,BankBaseResp resp);
	
	/**
	 * 上传微众文件
	 * @param downLoad
	 */
	void upLoadWz(WzDownLoad downLoad);
	/**
	 * 更新微众文件
	 * @param downLoad
	 */
	String updateWz(String companyPk,String fileName);
	
	/**
	 * 更新风控信息
	 * @param company
	 * @param data
	 */
	void updateReport(String companyPk,String companyName,String shotName,String data,String fileUrl);
	
	/**
	 * 线上支付业务成功回调
	 */
	@Transactional
	void successBackPyOnline(B2bOrderDtoMa odto,String bankAccount, String bankName);

	/**
	 * 线上支付业务失败回调
	 * @param odto:订单号
	 */
	@Transactional
	void errorBackPyOnline(String orderNumber);
	/**
	 * 线上支付业务申请
	 * @param order
	 * @param serialNumber
	 * @param card
	 * @param onlinePayGoods
	 */
	@Transactional
	void applyBackOnline(B2bOrderDtoMa order,String contractNo,String paymentName,Integer paymentType,String serialNumber,SysCompanyBankcardDto card,B2bOnlinepayGoodsDto onlinePayGoods,String returnUrl);

	/**
	 * 确认放款
	 * @param o
	 * @param ebc
	 * @param gdto
	 */
	@Transactional
	void sureLoan(B2bLoanNumberDto o,B2bCreditGoodsDto gdto);
	
	/**
	 * 票付通支付成功回调
	 * @param order
	 * @param contractNo
	 * @param paymentName
	 * @param paymentType
	 * @param serialNumber
	 * @param card
	 * @param gdto
	 * @param paytype 0非见票 1见票
	 */
	@Transactional
	void updateBackBillOrder(B2bOrderDtoMa order,String contractNo,String paymentName,Integer paymentType,String serialNumber,String returnUrl,SysCompanyBankcardDto card,B2bBillCusgoodsDto gdto,String mesgid,Integer paytype);
	
	/**
	 * 票付通票据信息修改
	 * @param order
	 * @param list
	 * @param flag true取消回调  false 非取消回调
	 */
	@Transactional
	void updateBackBillOrderInventory(B2bBillOrderDto order,List<B2bBillInventoryDto> list);
	
	/**
	 * 采购商客户绑定票付通账户回调
	 * @param companyPk
	 * @param bindType
	 */
	@Transactional
	void customerBindPftAccount(String companyPk, List<B2bBillSigncardDto> list);
	/**
	 * 采购商客户查询票付通账户回调
	 * @param cus
	 * @param list
	 */
	@Transactional
	void customerSearchPftAccount(B2bBillCusgoodsDtoEx cus,List<B2bBillSigncardDto> list);
	/**
	 * 供应商签约票付通账户回调
	 * @param cus
	 * @param list
	 */
	@Transactional
	void supplierBindPftAccount(B2bBillSigningDto sign,List<B2bBillSigncardDto> list);
	/**
	 * 供应商查询票付通账户回调
	 * @param cus
	 * @param list
	 */
	@Transactional
	void supplierSearchPftAccount(B2bBillSigningDto sign,List<B2bBillSigncardDto> list);
	/**
	 * 票付通账户解绑
	 * @param cus
	 * @param list
	 */
	@Transactional
	void unBindPftAccount(String companyPk,List<B2bBillSigncardDto> list);
	/**
	 * 更新贴现额度
	 * @param companyPk
	 * @param bindType
	 */
	@Transactional
	void updateAmount(B2bBillGoodsDto dto);
	/**
	 * 更新贴现支付结果
	 * @param om
	 * @param gdto
	 * @param serialNumber
	 */
	@Transactional
	void updateBackTx(B2bOrderDtoMa om,B2bBillCusgoodsDto gdto,String serialNumber);
}
