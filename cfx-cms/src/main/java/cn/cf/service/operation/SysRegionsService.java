package cn.cf.service.operation;

import cn.cf.dto.SysRegionsDto;
import cn.cf.model.SysRegions;

import java.util.List;

public interface SysRegionsService {
	/**
	 * 获取所有省份
	 * @return
	 */
	List<SysRegionsDto> getSysregisByProvenceList();
	/**
	 * 获取某一省份下的说有城市
	 * @param parentPk
	 * @return
	 */
	List<SysRegionsDto> getSysregisByCityList(String parentPk);
	
	List<SysRegionsDto> getAllSubRegionsDesc(String parentPk);
	
	int getMaxRegionsPk();
	/**
	 * 获取所有地区
	 * @return
	 */
	List<SysRegionsDto> getSysRegonsAll();
	/**
	 * 根据地区名称获取地区对象
	 * @param name
	 * @return
	 */
	SysRegionsDto getbyName(String name);
	/**
	 * 根据地区Pk获取地区对象
	 * @param pk
	 * @return
	 */
	SysRegionsDto getbyPk(String pk);
	/**
	 * 根据父PK获取所有子地区信息
	 * @param parentPK
	 * @return
	 */
	List<SysRegionsDto> getByParentPk(String parentPK);
	/**
	 * 新增地区
	 * @param model
	 * @return
	 */
	int add(SysRegions model);
	/**
	 * 修改地区
	 * @param model
	 * @return
	 */
	int update(SysRegions model);
	/**
	 * 删除地区
	 * @param pk
	 * @return
	 */
	int delete(String pk);
	
}
