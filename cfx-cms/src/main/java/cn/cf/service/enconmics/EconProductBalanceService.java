package cn.cf.service.enconmics;

import java.util.List;

import cn.cf.dto.B2bEconomicsBankDto;

public interface EconProductBalanceService {
	
	
	/***
	 * 页面初始化日均值
	 * @param list 
	 * @param today 
	 * @param StringyesterDay 
	 * @return
	 */
	String getEveryDayProductBalance(List<B2bEconomicsBankDto> list, String start, String end);


}
