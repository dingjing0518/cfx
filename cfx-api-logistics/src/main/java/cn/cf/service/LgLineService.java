package cn.cf.service;

import java.util.List;
import cn.cf.dto.LogisticsRouteDto;
import cn.cf.entity.SearchLgLine;
import cn.cf.entity.SearchLgPrice;

public interface LgLineService {

	/**
	 * 匹配物流承运商
	 * @param searchLgLine
	 * @param weight
	 * @return
	 */
	List<LogisticsRouteDto> getLogisticsSetpinfos(SearchLgLine searchLgLine, Double weight);

	/**
	 * 查询商品的运费单价
	 * @param searchLgPrice
	 * @return
	 */
	List<LogisticsRouteDto> getOrderFreight(SearchLgPrice searchLgPrice);
	
	
}
