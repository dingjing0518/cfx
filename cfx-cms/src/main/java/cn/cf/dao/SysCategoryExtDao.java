package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.SysCategoryDto;
import cn.cf.dto.SysCategoryExtDto;
import cn.cf.model.SysCategory;
import org.apache.ibatis.annotations.Param;

public interface SysCategoryExtDao extends SysCategoryDao{

	int valiDateCounts(SysCategory sysCategory);

	List<SysCategoryExtDto> getCategorys();

	List<SysCategoryExtDto> getByParentId(Map<String, Object> map);

	List<SysCategoryExtDto> getNewsCates(Map<String, Object> map);

	List<SysCategoryExtDto> searchListByParentPk(Map<String, Object> map);

	List<SysCategoryDto> getAllCategory();


}
