package cn.cf.dao;

import java.util.List;

import cn.cf.dto.LogisticsErpStepInfoDto;

public interface LogisticsErpStepInfoDaoEx extends LogisticsErpStepInfoDao{

	void deleteByLogisticsPk(String pk);

	List<LogisticsErpStepInfoDto> searchLogisticsErpStepInfoList(String logisticsPk);

}
