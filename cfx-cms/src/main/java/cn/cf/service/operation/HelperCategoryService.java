package cn.cf.service.operation;

import java.util.List;

import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.dto.SysHelpsCategoryDto;

public interface HelperCategoryService {
	/**
	 * 搜索帮助分类列表
	 * @param qm
	 * @return
	 */
	PageModel<SysHelpsCategoryDto> searchSysHelpCategoryData(QueryModel<SysHelpsCategoryDto> qm);
	/**
	 * 更新帮助分类状态
	 * @param sysHelpsCategoryDto
	 * @return
	 */
	int updateSysHelpsCategoryStatus(SysHelpsCategoryDto sysHelpsCategoryDto);

	/**
	 * 删除帮助分类
	 * @param pk
	 * @return
	 */
	int delSysHelpsCategory(String pk);
	/**
	 * 修改帮助分类
	 * @param sysHelpsCategoryDto
	 * @return
	 */
	int updateSysHelpsCategory(SysHelpsCategoryDto sysHelpsCategoryDto);
	/**
	 * 查询所有帮助分类
	 * @return
	 */
	public List<SysHelpsCategoryDto> getAllSysHelpCategoryData();

}
