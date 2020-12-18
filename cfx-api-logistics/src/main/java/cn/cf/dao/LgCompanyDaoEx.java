package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.LgCompanyDto;

public interface LgCompanyDaoEx  extends LgCompanyDao{
		
	//根据名字查询物流承运商
	public LgCompanyDto getLogisticsByName(String logisticsName);
/**
 * 查询承运商list
 * @param map
 * @return
 */
	public List<LgCompanyDto> searchLgCompanyList(Map<String, Object> map);
	
}
