package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bMarketLiveBroadExtDto;
import cn.cf.dto.SysMarketLivebroadDto;

public interface SysMarketLivebroadExtDao extends SysMarketLivebroadDao {

	int searchGridExCount(Map<String, Object> map);

	List<B2bMarketLiveBroadExtDto> searchGridEx(Map<String, Object> map);

	int updateEx(SysMarketLivebroadDto sysMarketLivebroadDto);

}
