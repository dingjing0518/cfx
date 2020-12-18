package cn.cf.service.operation.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.dao.SysLivebroadCategoryExDao;
import cn.cf.dto.B2bLiveBroadCastCategoryExDto;
import cn.cf.dto.SysLivebroadCategoryDto;
import cn.cf.model.SysLivebroadCategory;
import cn.cf.service.operation.B2bBroadCastCategoryService;
import cn.cf.util.KeyUtils;

@Service
public class B2bBroadCastCategoryServiceImpl implements B2bBroadCastCategoryService {

	@Autowired
	private SysLivebroadCategoryExDao sysLivebroadCategoryExDao;
	@Override
	public PageModel<B2bLiveBroadCastCategoryExDto> searchLiveBroadCastCategorydata(
			QueryModel<B2bLiveBroadCastCategoryExDto> qm) {
		
		PageModel<B2bLiveBroadCastCategoryExDto> pm = new PageModel<B2bLiveBroadCastCategoryExDto>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", qm.getStart());
		map.put("limit", qm.getLimit());
		map.put("orderName", qm.getFirstOrderName());
		map.put("orderType", qm.getFirstOrderType());
		map.put("isDelete", 1);
		int totalCount = sysLivebroadCategoryExDao.searchGridExCount(map);
		List<B2bLiveBroadCastCategoryExDto> list = sysLivebroadCategoryExDao.searchGridEx(map);
		pm.setTotalCount(totalCount);
		pm.setDataList(list);
		return pm;
	}

	@Override
	public int updateLiveBroadCastCategory(B2bLiveBroadCastCategoryExDto b2bLiveBraodCastCategoryExDto) throws Exception {
	
		String pk = null;
		int result = 0;
		//验证是否重复添加
		if(b2bLiveBraodCastCategoryExDto.getName()!=null&&!"".equals(b2bLiveBraodCastCategoryExDto.getName())){
			int count =  sysLivebroadCategoryExDao.valiDateCounts(b2bLiveBraodCastCategoryExDto);
			if(count>0){
				return -1;
			}
		}
		if (b2bLiveBraodCastCategoryExDto.getPk() != null && !"".equals(b2bLiveBraodCastCategoryExDto.getPk())) {
			pk = b2bLiveBraodCastCategoryExDto.getPk();
			SysLivebroadCategoryDto sysLivebroadCategoryDto=sysLivebroadCategoryExDao.getByPk(pk);
			if(null!=b2bLiveBraodCastCategoryExDto.getIsDelete()){
				sysLivebroadCategoryDto.setIsDelete(b2bLiveBraodCastCategoryExDto.getIsDelete());
			}
			
			if(null!=b2bLiveBraodCastCategoryExDto.getIsVisable()){
				sysLivebroadCategoryDto.setIsVisable(b2bLiveBraodCastCategoryExDto.getIsVisable());
			}
			if(null!=b2bLiveBraodCastCategoryExDto.getSort()){
				sysLivebroadCategoryDto.setSort(b2bLiveBraodCastCategoryExDto.getSort());
			}
		
			if(b2bLiveBraodCastCategoryExDto.getName()!=null){
				sysLivebroadCategoryDto.setName(b2bLiveBraodCastCategoryExDto.getName());
			}
			sysLivebroadCategoryDto.setUpdateTime(new Date());
			
			result = sysLivebroadCategoryExDao.updateEx(sysLivebroadCategoryDto);
		} else {
			pk = KeyUtils.getUUID();

			
			SysLivebroadCategory sysLivebroadCategory=new SysLivebroadCategory();
			sysLivebroadCategory.setPk(pk);
			sysLivebroadCategory.setInsertTime(new Date());
			sysLivebroadCategory.setUpdateTime(new Date());
			sysLivebroadCategory.setIsDelete(1);
			sysLivebroadCategory.setIsVisable(1);
			sysLivebroadCategory.setSort(b2bLiveBraodCastCategoryExDto.getSort());
			sysLivebroadCategory.setName(b2bLiveBraodCastCategoryExDto.getName());
			result = sysLivebroadCategoryExDao.insert(sysLivebroadCategory);
		}
		return result;
	}

	@Override
	public List<B2bLiveBroadCastCategoryExDto> getAllBroadCastCategory() {
		
		return sysLivebroadCategoryExDao.getAllBroadCastCategory();
	}

}
