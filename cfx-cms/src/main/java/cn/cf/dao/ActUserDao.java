package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.ActUserDto;
import cn.cf.dto.MemberShip;

public interface ActUserDao {

	int searchUserCount(Map<String, Object> map);

	List<ActUserDto> searchUserList(Map<String, Object> map);

	int insertActUser(Map<String, Object> map);

	ActUserDto selectByPk(Map<String, Object> map);

	MemberShip getMemberShipByUserId(String account);
	Object clone() throws CloneNotSupportedException;
	
	void deleteUser (Map<String, Object> map);

	void update(Map<String, Object> map1);

}
