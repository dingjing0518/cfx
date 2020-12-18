package cn.cf.dao;

import java.util.List;

import cn.cf.dto.LogisticsErpMemberDto;

public interface LogisticsErpMemberDaoEx  {

	void deleteByLogisticsPk(String pk);

	void insert(LogisticsErpMemberDto ls);

	List<LogisticsErpMemberDto> searchLogisticsErpMemberList(String pk);

}
