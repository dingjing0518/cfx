package cn.cf.service.operation.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.PageModel;
import cn.cf.common.Constants;
import cn.cf.common.QueryModel;
import cn.cf.dao.SysHelpsCategoryDao;
import cn.cf.dao.SysHelpsCategoryExtDao;
import cn.cf.dto.SysHelpsCategoryDto;
import cn.cf.model.SysHelpsCategory;
import cn.cf.service.operation.HelperCategoryService;

@Service
public class HelperCategoryServiceImpl implements HelperCategoryService {
	@Autowired
	private SysHelpsCategoryDao sysHelpsCategoryDao;
	@Autowired
	private SysHelpsCategoryExtDao sysHelpsCategoryExtDao;

	@Override
	public PageModel<SysHelpsCategoryDto> searchSysHelpCategoryData(QueryModel<SysHelpsCategoryDto> qm) {
		
		
		PageModel<SysHelpsCategoryDto> pm = new PageModel<SysHelpsCategoryDto>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", qm.getStart());
		map.put("limit", qm.getLimit());
		map.put("orderName", qm.getFirstOrderName());
		map.put("orderType", qm.getFirstOrderType());
		map.put("isVisable", qm.getEntity().getIsVisable());
		map.put("name", qm.getEntity().getName());
		map.put("showPlace", qm.getEntity().getShowPlace());

		map.put("isDelete", 1);
		int totalCount = sysHelpsCategoryDao.searchGridCount(map);
		List<SysHelpsCategoryDto> list = sysHelpsCategoryDao.searchGrid(map);
		pm.setTotalCount(totalCount);
		pm.setDataList(list);
		return pm;
	}

	@Override
	public int updateSysHelpsCategoryStatus(SysHelpsCategoryDto sysHelpsCategoryDto) {
		
		int result = 0;
		if (sysHelpsCategoryDto.getPk() != null && !"".equals(sysHelpsCategoryDto.getPk())) {
			SysHelpsCategoryDto cdto = sysHelpsCategoryDao.getByPk(sysHelpsCategoryDto.getPk());
			if(cdto != null){
				SysHelpsCategory helpsCategory = new SysHelpsCategory();
				helpsCategory.setPk(cdto.getPk());
				helpsCategory.setName(cdto.getName());
				helpsCategory.setInsertTime(cdto.getInsertTime());
				helpsCategory.setIsDelete(cdto.getIsDelete());
				helpsCategory.setIsVisable(sysHelpsCategoryDto.getIsVisable());
				helpsCategory.setShowType(cdto.getShowType());
				helpsCategory.setSort(cdto.getSort());
				helpsCategory.setShowPlace(cdto.getShowPlace());
				result = sysHelpsCategoryDao.update(helpsCategory);	
			}
		} 
		return result;
	}

	@Override
	public int delSysHelpsCategory(String pk) {
		
		if(pk != null && !"".equals(pk)){
			return sysHelpsCategoryExtDao.deleteHelpsCategory(pk);
		}else{
			return 0;
		}
	}

	@Override
	public int updateSysHelpsCategory(SysHelpsCategoryDto sysHelpsCategoryDto) {
		
		int result = 0;
		
		//添加
		if(sysHelpsCategoryDto != null && (sysHelpsCategoryDto.getPk() == null || "".equals(sysHelpsCategoryDto.getPk()))){
			Map<String,Object> map = new HashMap<>();
			map.put("name", sysHelpsCategoryDto.getName());
			map.put("isDelete", Constants.ONE);
			List<SysHelpsCategoryDto> dtoList = sysHelpsCategoryExtDao.searchList(map);
			if(dtoList != null && dtoList.size() > 0){
				return 2;
			}
			int nextPk = sysHelpsCategoryExtDao.getByMaxPk();
			SysHelpsCategory category = new SysHelpsCategory();
			category.setPk(nextPk+1+"");
			category.setInsertTime(new Date());
			category.setIsDelete(1);
			category.setIsVisable(1);
			category.setShowType(sysHelpsCategoryDto.getShowType());
			category.setName(sysHelpsCategoryDto.getName());
			category.setSort(sysHelpsCategoryDto.getSort());
			category.setShowPlace(sysHelpsCategoryDto.getShowPlace());
			result = sysHelpsCategoryDao.insert(category);
		}
		//编辑
		if(sysHelpsCategoryDto != null && sysHelpsCategoryDto.getPk() != null && !"".equals(sysHelpsCategoryDto.getPk())){
			SysHelpsCategoryDto dto = sysHelpsCategoryDao.getByPk(sysHelpsCategoryDto.getPk());
			Map<String,Object> map = new HashMap<>();
			map.put("name", sysHelpsCategoryDto.getName());
			map.put("pk", sysHelpsCategoryDto.getPk());
			List<SysHelpsCategoryDto> list = sysHelpsCategoryExtDao.isExistCategoryName(map);
			if(list != null && list.size() > 0){
				return 2;
			}
			SysHelpsCategory helpsCategory = new SysHelpsCategory();
			helpsCategory.setPk(dto.getPk());
			helpsCategory.setName(sysHelpsCategoryDto.getName());
			helpsCategory.setInsertTime(dto.getInsertTime());
			helpsCategory.setIsDelete(dto.getIsDelete());
			helpsCategory.setIsVisable(dto.getIsVisable());
			helpsCategory.setShowType(sysHelpsCategoryDto.getShowType());
			helpsCategory.setSort(sysHelpsCategoryDto.getSort());
			helpsCategory.setShowPlace(sysHelpsCategoryDto.getShowPlace());
			result = sysHelpsCategoryDao.update(helpsCategory);	
		}
		return result;
	}

	@Override
	public List<SysHelpsCategoryDto> getAllSysHelpCategoryData() {
		
		return sysHelpsCategoryExtDao.getAll();
	}

}
