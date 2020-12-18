/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bEconomicsDatumExtDto;
import cn.cf.model.B2bEconomicsDatum;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bEconomicsDatumExtDao extends B2bEconomicsDatumDao{
	List<B2bEconomicsDatumExtDto> searchGridExt(Map<String, Object> map);
	int searchGridExtCount(Map<String, Object> map);
	
	int updateEconomicsDatum(B2bEconomicsDatumExtDto dto);
	int updateEconomicsDatumEx(B2bEconomicsDatumExtDto customerExtDto);
	B2bEconomicsDatum selectEconomicsDatum(B2bEconomicsDatumExtDto customerExtDto);
}
