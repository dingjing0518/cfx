package cn.cf.service.operation;



import java.util.List;

import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.dto.SysCategoryDto;
import cn.cf.dto.SysCategoryExtDto;
import cn.cf.model.SysCategory;

public interface SysCategoryService {
	/**
	 * 搜索资讯分类列表
	 * @param qm
	 * @return
	 */
	PageModel<SysCategoryDto> searchSysCategorydata(QueryModel<SysCategoryDto> qm);
	/**
	 * 更新资讯分类
	 * @param sysCategory
	 * @return
	 */
	int updatesysCategory(SysCategory sysCategory);
	/**
	 * 根据父Pk获取资讯分类集合
	 * @param parentId
	 * @return
	 */
	List<SysCategoryExtDto> getCategoryByPid(String parentId,String block);
	/**
	 * 获取所有资讯分类
	 * @return
	 */
	List<SysCategoryDto> getAllCategory();

	

}
