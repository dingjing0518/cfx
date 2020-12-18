package cn.cf.service.creditpay;

import cn.cf.dto.B2bCompanyDto;
import cn.cf.entry.BankBaseResp;

public interface BankSuzhouService extends BankBaseService{
	
	
	/**
	 * 查询提款(http方式)
	 * @param order
	 * @param company
	 * @param goodsType
	 * @return
	 */
	BankBaseResp searchWithdrawalsByHttp(String orderNumber,
			String customerNumber, Integer goodsType);
	
	/**
	 * 微众资料申请
	 * @param company
	 * @return
	 */
	BankBaseResp wzApplication(B2bCompanyDto company);
	
	/**
	 * 微众资料查询
	 * @param company
	 * @return
	 */
	BankBaseResp wzSearch(B2bCompanyDto company);
	
	/**
	 * 微众文件下载
	 * @param fileName
	 * @return
	 */
	BankBaseResp wzDownload(String fileName);

}
