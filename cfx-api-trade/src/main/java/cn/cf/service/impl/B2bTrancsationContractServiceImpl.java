package cn.cf.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.PageModel;
import cn.cf.dao.B2bTrancsationContractDaoEx;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bStoreDto;
import cn.cf.dto.B2bTrancsationContractDto;
import cn.cf.service.B2bTrancsationContractService;

@Service
public class B2bTrancsationContractServiceImpl implements B2bTrancsationContractService {
	
	@Autowired
	private B2bTrancsationContractDaoEx b2bTrancsationContractDaoEx;

	@Override
	public PageModel<B2bTrancsationContractDto> searchPageTrancsationList(Map<String, Object> map, B2bStoreDto store, B2bCompanyDto company) {
		PageModel<B2bTrancsationContractDto> pm = new PageModel<B2bTrancsationContractDto>();
		if(null == map){
			map = new HashMap<String, Object>();
		}
		map.put("isDelete", 1);
		if("-1".equals(company.getParentPk())){
			map.put("storePk", null==store?"":store.getPk());
		}else{
			map.put("companyPk", company.getPk());
		}
		List<B2bTrancsationContractDto> list = b2bTrancsationContractDaoEx.searchTrancsationList(map);
		if(null == list || list.size()==0 || null ==list.get(0)){
			list = new ArrayList<B2bTrancsationContractDto>();
		}
		int counts = b2bTrancsationContractDaoEx.searchCount(map);
		pm.setDataList(list);
		pm.setTotalCount(counts);
		if(null != map.get("start")){
			pm.setStartIndex(Integer.parseInt(map.get("start").toString()));
			pm.setPageSize(Integer.parseInt(map.get("limit").toString()));
		}
		return pm;
	}

	@Override
	public List<B2bTrancsationContractDto> searchTrancsationList(Map<String, Object> map,B2bStoreDto store,B2bCompanyDto company) {
		if (null == map) {
			map = new HashMap<String, Object>();
		}
		map.put("isDelete", 1);
		if ("-1".equals(company.getParentPk())) {
			map.put("storePk", null == store?"":store.getPk());
		} else {
			map.put("companyPk", company.getPk());
		}
		return b2bTrancsationContractDaoEx.searchTrancsationList(map);
	}
	
	

}
