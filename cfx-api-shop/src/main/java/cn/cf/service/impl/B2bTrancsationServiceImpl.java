package cn.cf.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.PageModel;
import cn.cf.dao.B2bTrancsationDaoEx;
import cn.cf.dto.B2bCompanyDtoEx;
import cn.cf.dto.B2bTrancsationDto;
import cn.cf.service.B2bTrancsationService;

@Service
public class B2bTrancsationServiceImpl implements B2bTrancsationService {
	
	@Autowired
	private B2bTrancsationDaoEx b2bTrancsationDao;
	
	@Override
	public PageModel<B2bTrancsationDto> searchPageTrancsationList(Map<String, Object> map,B2bCompanyDtoEx company) {
		PageModel<B2bTrancsationDto> pm = new PageModel<B2bTrancsationDto>();
		if(null == map){
			map = new HashMap<String, Object>();
		}
		map.put("isDelete", 1);
		if("-1".equals(company.getParentPk())){
			map.put("storePk", company.getStorePk());
		}else{
			map.put("companyPk", company.getPk());
		}
		List<B2bTrancsationDto> list = b2bTrancsationDao.searchTrancsationList(map);
		if(null == list || list.size()==0 || null ==list.get(0)){
			list = new ArrayList<B2bTrancsationDto>();
		}
		int counts = b2bTrancsationDao.searchCount(map);
		pm.setDataList(list);
		pm.setTotalCount(counts);
		if(null != map.get("start")){
			pm.setStartIndex(Integer.parseInt(map.get("start").toString()));
			pm.setPageSize(Integer.parseInt(map.get("limit").toString()));
		}
		return pm;
	}

	@Override
	public List<B2bTrancsationDto> searchTrancsationList(
			Map<String, Object> map, B2bCompanyDtoEx company) {
		if(null == map){
			map = new HashMap<String, Object>();
		}
		map.put("isDelete", 1);
		if("-1".equals(company.getParentPk())){
			map.put("storePk", company.getStorePk());
		}else{
			map.put("companyPk", company.getPk());
		}
		return b2bTrancsationDao.searchTrancsationList(map);
	}

}
