package cn.cf.service.creditpay;

import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bCreditGoodsDto;
import cn.cf.dto.SysCompanyBankcardDto;
import cn.cf.entity.B2bCreditDtoMa;
import cn.cf.entity.B2bCreditGoodsDtoMa;
import cn.cf.entity.B2bOrderDtoMa;
import cn.cf.entry.BankBaseResp;

public interface BankJiansheService extends BankBaseService{
	
	/**
	 * 客户申请
	 * @param creditDto
	 * @return
	 */
	BankBaseResp customerApply(B2bCreditDtoMa creditDto,B2bCompanyDto company);
	
	/**
	 * 客户额度查询
	 * @param creditDto
	 * @return
	 */
	BankBaseResp searchBankCreditAmountUnique(B2bCreditDtoMa creditDto,B2bCreditGoodsDto dto);
	
	/**
	 * 订单支付
	 * @param creditDto
	 * @return
	 */
	BankBaseResp payOrderUnique(B2bCreditDtoMa creditDto,B2bOrderDtoMa order,B2bCompanyDto company,
			SysCompanyBankcardDto card, B2bCreditGoodsDtoMa cgDto);
	/**
	 * 处理客户推送消息
	 * @param rest
	 * @return
	 */
	BankBaseResp integrationCustomer(String rest);
	/**
	 * 处理订单支付结果信息
	 * @param rest
	 * @return
	 */
	BankBaseResp orderPayresult(String rest);
	
	
}
