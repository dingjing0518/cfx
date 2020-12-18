package cn.cf.dao;


import cn.cf.dto.LogisticsLinePriceDto;
import cn.cf.dto.LogisticsRouteDto;
import cn.cf.entity.LogisticsRouteExport;
import cn.cf.model.LogisticsRouteGridModel;

import java.util.List;
import java.util.Map;

public interface LogisticsRouteDao  {

    int countLogisticsRoute (Map<String, Object> map);

    List<LogisticsRouteGridModel> getLogisticsRoute(Map<String, Object> map);

    int delete(String pk);
    //查询单条线路
    LogisticsRouteDto selectOne(String pk);
    //查询线路的平台价格
    List<LogisticsLinePriceDto> selectPriceList(String pk);
    //模板名查询
    LogisticsRouteDto getByName(String name);
    //更新线路
    int update(LogisticsRouteDto dto);

    int insert(LogisticsRouteDto dto);
    //查询多条线路
    List<LogisticsRouteDto> searchList(Map<String, Object> map);
    //根据pk查找
    List<LogisticsRouteDto> searchListByPk(Map<String, Object> map);

    //线路的最低起运价
    List<LogisticsRouteExport> searchLogisticsRoute(Map<String, Object> map);

    List<LogisticsRouteExport> searchLogisticsPrice(Map<String, Object> map);

	int countAllLogisticsRoute(Map<String, Object> map);

	List<LogisticsRouteExport> getAllLogisticsRoute(Map<String, Object> orderMap);


}