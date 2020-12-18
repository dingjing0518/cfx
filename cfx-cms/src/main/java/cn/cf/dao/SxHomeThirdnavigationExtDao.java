package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.SxHomeThirdnavigationExtDto;

public interface SxHomeThirdnavigationExtDao  extends  SxHomeThirdnavigationDao{

	int searchExtGridCount(Map<String, Object> map);

	List<SxHomeThirdnavigationExtDto> searchExtGrid(Map<String, Object> map);

	Integer updateExt(SxHomeThirdnavigationExtDto dto);

	Integer isExtThirdNavigation(Map<String, Object> map);

}
