package cn.cf.service.operation.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.dao.SysCategoryDao;
import cn.cf.dao.SysCategoryExtDao;
import cn.cf.dto.SysCategoryDto;
import cn.cf.dto.SysCategoryExtDto;
import cn.cf.model.SysCategory;
import cn.cf.service.operation.SysCategoryService;
import cn.cf.util.KeyUtils;

@Service
public class SysCategoryServiceImpl implements SysCategoryService {

	@Autowired
	private SysCategoryDao sysCategoryDao;
	
	@Autowired
	private SysCategoryExtDao sysCategoryExtDao;
	@Override
	public PageModel<SysCategoryDto> searchSysCategorydata(QueryModel<SysCategoryDto> qm) {
		
		
		PageModel<SysCategoryDto> pm = new PageModel<SysCategoryDto>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", qm.getStart());
		map.put("limit", qm.getLimit());
		map.put("orderName", qm.getFirstOrderName());
		map.put("orderType", qm.getFirstOrderType());
		map.put("isDelete", 1);
		int totalCount = sysCategoryDao.searchGridCount(map);
		List<SysCategoryDto> list = sysCategoryDao.searchGrid(map);
		pm.setTotalCount(totalCount);
		pm.setDataList(list);
		return pm;
	}
	@Override
	public int updatesysCategory(SysCategory sysCategory) {
		
		String pk = null;
		int result = 0;
		//验证是否重复添加
		if(sysCategory.getName()!=null&&!"".equals(sysCategory.getName())){
			int count =  sysCategoryExtDao.valiDateCounts(sysCategory);
			if(count>0){
				return -1;
			}
		}
		if (sysCategory.getPk() != null && !"".equals(sysCategory.getPk())) {
			pk = sysCategory.getPk();
			result = sysCategoryDao.update(sysCategory);
		} else {
			pk = KeyUtils.getUUID();
			sysCategory.setPk(pk);
			sysCategory.setInsertTime(new Date());
			sysCategory.setIsDelete(1);
			sysCategory.setIsVisable(1);
			result = sysCategoryDao.insert(sysCategory);
		}
		return result;
	}
	@Override
	public List<SysCategoryExtDto> getCategoryByPid(String parentPk,String block) {
		
		
		Map<String, Object> map =new HashMap<String, Object>();
		String pks="";
		parentPk = parentPk.substring(1, parentPk.length() - 1);
		String[] parentPks = parentPk.split(",");
		for (int i = 0; i < parentPks.length; i++) {
			if(i==0){
				pks+=" and (parentId="+parentPks[i];
			}else{
				pks+=" or parentId="+parentPks[i];
			}
		if(i==parentPks.length-1){
			pks+=")";
		}}
		map.put("parentPks", pks);
		return sysCategoryExtDao.searchListByParentPk(map);
	}
	@Override
	public List<SysCategoryDto> getAllCategory() {
		
		return sysCategoryExtDao.getAllCategory();
	}
	
}
