package cn.cf.service;

import java.util.List;
import java.util.Map;

import cn.cf.PageModel;
import cn.cf.common.RestCode;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bMemberDto;
import cn.cf.dto.B2bReserveOrderDto;
import cn.cf.dto.B2bStoreDto;
import cn.cf.entity.BatchOrder;
import cn.cf.entity.Pgoods;
import cn.cf.entity.Porder;
import cn.cf.entity.Sessions;
import cn.cf.model.B2bReserveOrderEx;

public interface B2bReserveService {


	RestCode submitReserveOrder(Porder o, Map<String, List<Pgoods>> map,
			BatchOrder bo);
	 PageModel<B2bReserveOrderEx>  searchReserveOrderList(String type,
				B2bStoreDto store,B2bCompanyDto company,Map<String,Object> map,Sessions session,B2bMemberDto memberDto);
		Map<String,Object> searchReserveOrderCount(String searchType, B2bStoreDto store,
			B2bCompanyDto company, Map<String, Object> map, Sessions session,
			B2bMemberDto memberDto);
		B2bReserveOrderDto getReserveDetails(String orderNumber);
		int updateReserveStatus(Map<String, Object> map);
		/***
		 * 查询预约时间是当天的预约单
		 * @return
		 */
		List<B2bReserveOrderDto> searchReserveOrderListForToday();


}
