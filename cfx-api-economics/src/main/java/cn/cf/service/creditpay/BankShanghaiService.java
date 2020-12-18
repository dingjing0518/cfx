package cn.cf.service.creditpay;

import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bLoanNumberDto;
import cn.cf.dto.SysCompanyBankcardDto;
import cn.cf.entity.B2bCreditGoodsDtoMa;
import cn.cf.entity.B2bOrderDtoMa;
import cn.cf.entity.B2bRepaymentRecord;
import cn.cf.entry.BankBaseResp;

public interface BankShanghaiService extends BankBaseService{
	
	/**
	 * 放款计划
	 * @param order
	 * @param company
	 * @param card
	 * @param cgDto
	 * @return
	 */
	BankBaseResp conditionLoan(B2bOrderDtoMa order, B2bCompanyDto company,
			SysCompanyBankcardDto card, B2bCreditGoodsDtoMa cgDto);
	
	
	/**
	 * 费用代扣
	 * @param order
	 * @param company
	 * @param card
	 * @param cgDto
	 * @return
	 */
	BankBaseResp agentPay(B2bLoanNumberDto loanNumber,B2bRepaymentRecord record);

}
