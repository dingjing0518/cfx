package cn.cf.service.creditpay;

import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bCreditGoodsDto;
import cn.cf.dto.B2bLoanNumberDto;
import cn.cf.dto.SysCompanyBankcardDto;
import cn.cf.entity.B2bCreditGoodsDtoMa;
import cn.cf.entity.B2bOrderDtoMa;
import cn.cf.entry.BankBaseResp;



public interface BankBaseService {
	/**
	 * 查询银行授信额度
	 * company 采购商信息
	 * @return
	 */
	BankBaseResp searchBankCreditAmount(B2bCompanyDto company,B2bCreditGoodsDto dto);
	
	/**
	 * 查询银行放款信息 
	 * loanNumber 金融产品订单
	 * @return
	 */
	BankBaseResp searchloan(B2bLoanNumberDto loanNumber);
	
	/**
	 * 查询银行还款信息
	 * loanNumber 金融产品订单
	 * @return
	 */
	BankBaseResp searchRepayment(B2bLoanNumberDto loanNumber);
	
	/**
	 * 订单支付
	 * order  订单信息
	 * company 采购商公司信息
	 * card    供应商卡号信息
	 * @return
	 */
	BankBaseResp payOrder(B2bOrderDtoMa order,B2bCompanyDto company,SysCompanyBankcardDto card,B2bCreditGoodsDtoMa cgDto);
	
	/**
	 * 订单取消
	 * order 订单信息
	 * company 公司信息
	 * @return
	 */
	BankBaseResp cancelOrder(B2bLoanNumberDto loanNumber);
}
