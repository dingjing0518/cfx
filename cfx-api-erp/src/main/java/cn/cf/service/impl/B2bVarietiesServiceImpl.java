package cn.cf.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.cf.dao.B2bVarietiesDaoEx;
import cn.cf.dto.B2bVarietiesDto;
import cn.cf.model.B2bVarieties;
import cn.cf.service.B2bVarietiesService;
import cn.cf.util.KeyUtils;

@Service
public class B2bVarietiesServiceImpl implements B2bVarietiesService{

	@Autowired
	private B2bVarietiesDaoEx b2bVarietiesDaoEx;

	@Override
	public B2bVarietiesDto getByParentAndName(Map<String, Object> map){
		B2bVarietiesDto dto = null;
		List<B2bVarietiesDto> list = b2bVarietiesDaoEx.getByParentAndName(map);
		if(null != list && list.size()>0){
			dto = list.get(0);
		}
		return dto;
	}

	@Override
	@Transactional
	public String createNewVarieties(Map<String, Object> map){
		
		B2bVarieties varieties = new B2bVarieties();
		varieties.setName(map.get("name").toString());
		String pk = KeyUtils.getUUID();
		varieties.setPk(pk);
		varieties.setParentPk(map.get("parentPk").toString());
		varieties.setInsertTime(new Date());
		varieties.setIsDelete(1);
		varieties.setIsVisable(1);
		varieties.setSort(1);
		b2bVarietiesDaoEx.insert(varieties);
	    return pk;
	}

	@Override
	public B2bVarietiesDto getByPk(String pk){
		// TODO Auto-generated method stub
		return b2bVarietiesDaoEx.getByPk(pk);
	}
	
	

}
