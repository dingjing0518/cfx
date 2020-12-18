package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.ActGroupDto;
import cn.cf.model.ActGroup;

public interface ActGroupDao {

	int searchActGroupCount(Map<String, Object> map);

	List<ActGroupDto> searchActGroupList(Map<String, Object> map);

	int deleteActGroup(ActGroup actGroup);

	int addActGroup(ActGroup actGroup);

	int updateActGroup(ActGroup actGroup);

	List<ActGroup> findActGroup(ActGroup actGroup);

	List<ActGroupDto> findByUserId(String userId);

	List<ActGroupDto> getAllGroupList();

	Integer insertActUserGroup(Map<String, Object> map);

	int deleteActListGroup(Map<String, Object> map);

	int updateActUserGroup(Map<String, Object> map);

	ActGroupDto selectByPk(Map<String, Object> map);

}
