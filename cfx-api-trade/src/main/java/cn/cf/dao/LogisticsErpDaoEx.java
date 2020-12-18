package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.LogisticsErpDto;
import cn.cf.dto.LogisticsErpDtoEx;

public interface LogisticsErpDaoEx  extends LogisticsErpDao{
/**
 * 模板是否存在
 * @param dto
 * @return
 */
	int searchExistence(LogisticsErpDto dto);

List<LogisticsErpDto> searchLogisticsErpList(Map<String, Object> map);

int searchLogisticsErpCount(Map<String, Object> map);

Map<String, Object> searchLogisticsErpCounts(Map<String, Object> map);

LogisticsErpDtoEx getLogisticsByPk(String pk);

int searchExistenceByName(LogisticsErpDtoEx dto);

}
