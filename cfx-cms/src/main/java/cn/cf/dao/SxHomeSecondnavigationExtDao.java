package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.SxHomeSecondnavigationDto;
import cn.cf.dto.SxHomeSecondnavigationExtDto;

public interface SxHomeSecondnavigationExtDao extends SxHomeSecondnavigationDao {

	int searchExtGridCount(Map<String, Object> map);

	List<SxHomeSecondnavigationExtDto> searchExtGrid(Map<String, Object> map);

	Integer isExtSecondNavigation(Map<String, Object> map);

	int updateExt(SxHomeSecondnavigationExtDto dto);

	List<SxHomeSecondnavigationDto> getAllSecondNavigationList();

}
