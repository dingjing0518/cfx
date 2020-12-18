package cn.cf.dao;

import cn.cf.dto.LogisticsLinePriceDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface LogisticsLinePriceDao {

    Integer insert(LogisticsLinePriceDto logisticsLinePriceDto);

    Integer update(LogisticsLinePriceDto logisticsLinePriceDto);
    //查询线路价格
    List<LogisticsLinePriceDto> searchList(Map<String, Object> mapPrice);

    Integer delete(String linePk);

    List<LogisticsLinePriceDto> logisticsPrice(@Param("weight") String weight, @Param("linePk") String linePk);

}
