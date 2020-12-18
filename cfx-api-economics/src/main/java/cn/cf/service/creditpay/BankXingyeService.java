package cn.cf.service.creditpay;

import cn.cf.dto.B2bLoanNumberDto;
import cn.cf.entry.BankBaseResp;

public interface BankXingyeService extends BankBaseService{
	
	BankBaseResp searchOrder(B2bLoanNumberDto loanNumbber);
}
