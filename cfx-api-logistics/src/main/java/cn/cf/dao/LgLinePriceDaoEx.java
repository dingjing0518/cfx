package cn.cf.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.cf.dto.LgLinePriceDto;

public interface LgLinePriceDaoEx extends LgLinePriceDao{
	
	List<LgLinePriceDto> selectPricesByLinePk(@Param("linePk") String linePk);
	
}
