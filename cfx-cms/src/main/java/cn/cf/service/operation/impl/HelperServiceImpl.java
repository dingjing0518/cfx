package cn.cf.service.operation.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.dao.SysHelpsCategoryExtDao;
import cn.cf.dao.SysHelpsExtDao;
import cn.cf.dto.SysHelpsCategoryDto;
import cn.cf.dto.SysHelpsDto;
import cn.cf.dto.SysHelpsExtDto;
import cn.cf.model.SysHelps;
import cn.cf.service.operation.HelperService;

@Service
public class HelperServiceImpl implements HelperService {

	@Autowired
	private SysHelpsExtDao sysHelpsExtDao;
	
	@Autowired
	private SysHelpsCategoryExtDao sysHelpsCategoryExtDao;
	 
	@Override
	public PageModel<SysHelpsExtDto> searchsysHelpdata(QueryModel<SysHelpsExtDto> qm) {
		
		
		PageModel<SysHelpsExtDto> pm = new PageModel<SysHelpsExtDto>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", qm.getStart());
		map.put("limit", qm.getLimit());
		map.put("orderName", qm.getFirstOrderName());
		map.put("orderType", qm.getFirstOrderType());
		map.put("status", qm.getEntity().getStatus());
		map.put("isVisable", qm.getEntity().getIsVisable());
		map.put("title", qm.getEntity().getTitle());
		map.put("insertStartTime", qm.getEntity().getInsertStartTime());
		map.put("insertEndTime", qm.getEntity().getInsertEndTime());
		map.put("helpCategoryPk", qm.getEntity().getHelpCategoryPk());
		map.put("showPlace", qm.getEntity().getShowPlace());
		map.put("isDelete", 1);
		int totalCount = sysHelpsExtDao.searchGridExtCount(map);
		List<SysHelpsExtDto> list = sysHelpsExtDao.searchGridExt(map);
		pm.setTotalCount(totalCount);
		pm.setDataList(list);
		return pm;
	}


	@Override
	public int updateSysHelps(SysHelpsDto sysHelpsDto) {
		
		
		int result = 0;
		if (sysHelpsDto.getPk() != null && !"".equals(sysHelpsDto.getPk())) {
			SysHelpsDto dto = sysHelpsExtDao.getByPk(sysHelpsDto.getPk());
			
			if(dto != null){
				SysHelps helps = new SysHelps();
				helps.setPk(dto.getPk());
				helps.setTitle(dto.getTitle());
				helps.setContent(dto.getContent());
				helps.setHelpCategoryPk(dto.getHelpCategoryPk());
				helps.setSort(dto.getSort());
				helps.setStatus(dto.getStatus());
				helps.setInsertTime(dto.getInsertTime());
				helps.setIsDelete(dto.getIsDelete());
				helps.setIsVisable(sysHelpsDto.getIsVisable());
				helps.setShowPlace(dto.getShowPlace());
				helps.setShowType(dto.getShowType());
				result = sysHelpsExtDao.update(helps);	
			}
		} 
		return result;
	}


	@Override
	public int delSysHelps(String pk) {
		
		
		if(pk != null && !"".equals(pk)){
			return sysHelpsExtDao.deleteHelps(pk);
		}else{
			return 0;
		}
	}


	@Override
	public SysHelpsDto getSysHelpsByPk(String pk) {
		return sysHelpsExtDao.getByPk(pk);
	}


	@Override
	public int insertSysHelps(SysHelpsDto sysHelpsDto) {
		
		int result = 0;
		//判断是否帮助列表位置所属帮助分类
		SysHelpsCategoryDto  helpsCategoryDto = sysHelpsCategoryExtDao.getByPk(sysHelpsDto.getHelpCategoryPk());
		if (helpsCategoryDto!=null) {
			String catePlace =	helpsCategoryDto.getShowPlace();
			if (sysHelpsDto.getShowPlace()!=null&&!sysHelpsDto.getShowPlace().equals("")) {
				String[] array = sysHelpsDto.getShowPlace().split(",");
				if (array.length>0) {
					for (int i = 0; i < array.length; i++) {
						if(!catePlace.contains(array[i])){
							result = 2;
							break;
						}
					}
				}
			}else{
				result = 2;
			}
		}else{
			result = 2;
		}
		if (result==0) {
			//添加
			if(sysHelpsDto != null && (sysHelpsDto.getPk() == null || "".equals(sysHelpsDto.getPk()))){
					int nextPk = sysHelpsExtDao.getByMaxPk();
					SysHelps helps = new SysHelps();
					helps.setPk(nextPk+1+"");
					helps.setInsertTime(new Date());
					helps.setIsDelete(1);
					helps.setIsVisable(1);

					helps.setSort(sysHelpsDto.getSort());
					helps.setShowType(sysHelpsDto.getShowType());
					helps.setHelpCategoryPk(sysHelpsDto.getHelpCategoryPk());
					helps.setStatus(sysHelpsDto.getStatus());
					helps.setContent(sysHelpsDto.getContent());
					helps.setTitle(sysHelpsDto.getTitle());
					helps.setShowPlace(sysHelpsDto.getShowPlace());
					result = sysHelpsExtDao.insert(helps);
				}
			//编辑
			if(sysHelpsDto != null && sysHelpsDto.getPk() != null && !"".equals(sysHelpsDto.getPk())){
					SysHelpsDto dto = sysHelpsExtDao.getByPk(sysHelpsDto.getPk());
					if(dto != null){
						SysHelps helps = new SysHelps();
						helps.setPk(dto.getPk());
						helps.setInsertTime(dto.getInsertTime());
						helps.setIsDelete(dto.getIsDelete());
						helps.setIsVisable(dto.getIsVisable());
						helps.setSort(sysHelpsDto.getSort());
						
						helps.setHelpCategoryPk(sysHelpsDto.getHelpCategoryPk());
						helps.setTitle(sysHelpsDto.getTitle());
						helps.setContent(sysHelpsDto.getContent());
						helps.setStatus(sysHelpsDto.getStatus());
						helps.setShowType(sysHelpsDto.getShowType());
						helps.setShowPlace(sysHelpsDto.getShowPlace());
						result = sysHelpsExtDao.update(helps);	
					}
			}
		}
			return result;
	}

}
