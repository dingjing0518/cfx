package cn.cf.service.operation;

import cn.cf.dto.SysRegionsDto;

import java.util.List;

public interface InformationCenterService {
	
	/**
	 * 把保存在数据库里的资讯信息导入mongo
	 * @return
	 */
	String importNewsToMongo();
	

	
	
	/**
	 * 根据parentPk查询地区
	 * @param parentPk
	 * @return
	 */
	List<SysRegionsDto> searchSysRegionsList(String parentPk);
}
