package cn.cf.service.creditpay;

import javax.servlet.http.HttpServletResponse;

import cn.cf.dto.B2bLoanNumberDto;
import cn.cf.entity.B2bRepaymentRecord;
import cn.cf.entry.BankBaseResp;

public interface BankGongshangService extends BankBaseService {
	/**
	 * 代扣申请提交
	 * @param record
	 * @return
	 */
	BankBaseResp skblOrderPay(B2bRepaymentRecord record);
	/**
	 * 代扣申请查询
	 * @param record
	 * @return
	 */
	BankBaseResp skblOrderQry(B2bRepaymentRecord record);
	
	/**
	 * 融资页面跳转
	 * @param type
	 * @param orderNumber
	 * @return
	 */
	void jumpLoanPage(Integer type,B2bLoanNumberDto b2bLoanNumberDto,HttpServletResponse resp);
}
