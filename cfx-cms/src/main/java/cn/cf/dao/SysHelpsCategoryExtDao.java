package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.SysHelpsCategoryDto;

public interface SysHelpsCategoryExtDao extends SysHelpsCategoryDao {

	int deleteHelpsCategory(String pk);

	int getByMaxPk();

	List<SysHelpsCategoryDto> getAll();
	
	List<SysHelpsCategoryDto> isExistCategoryName(Map<String,Object> map);

}
