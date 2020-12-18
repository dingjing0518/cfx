package cn.cf.service.enconmics;

import java.util.List;

import cn.cf.dto.B2bEconomicsBankDto;

public interface EconomicsBankService {
	
	/**
	 * 查询金融银行
	 * @return
	 */
	List<B2bEconomicsBankDto> searchBankList();
	/**
	 * 
	 *  查询全部金融银行
	 * @return
	 */
	List<B2bEconomicsBankDto> searchAllList();

}
