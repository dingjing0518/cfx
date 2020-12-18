package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.ActMemberShipDto;

public interface ActMemberShipDao {

	int searchActMemShipCount(Map<String, Object> map);

	List<ActMemberShipDto> searchActMemShipList(Map<String, Object> map);

	int deleteActListGroup(Map<String, Object> map);

}
