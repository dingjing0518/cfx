package cn.cf.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import cn.cf.PageModel;
import cn.cf.dao.B2bPriceMovementDaoEx;
import cn.cf.dto.B2bPriceMovementDtoEx;
import cn.cf.entity.B2bPriceMovementEntity;
import cn.cf.service.B2bPriceMovementService;

@Service
public class B2bPriceMovementServiceImpl implements B2bPriceMovementService {
	
	@Autowired
	private B2bPriceMovementDaoEx b2bPriceMovementDao;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public PageModel<B2bPriceMovementDtoEx> searchB2bPriceMovementList(
			Map<String, Object> map) {
		PageModel<B2bPriceMovementDtoEx> pm = new PageModel<B2bPriceMovementDtoEx>();
		if(null == map){
			map = new HashMap<String,Object>();
		}
		map.put("isDelete", 1);
		List<B2bPriceMovementDtoEx> list = b2bPriceMovementDao.searchB2bPriceMovementList(map);
		int count = b2bPriceMovementDao.searchB2bPriceMovementCount(map);
		Criteria c = null;
		Query query = null;
		List<B2bPriceMovementEntity> tempList =new ArrayList<>();
		for (B2bPriceMovementDtoEx b2bPriceMovementDtoEx : list) {
			c = new Criteria();
			c.and("movementPk").is(b2bPriceMovementDtoEx.getPk()).and("isShow").is(1);
			query = new Query(c);
			query.with(new Sort(Sort.Direction.DESC, "date"));
			query.skip(0).limit(2);
			tempList = mongoTemplate.find(query, B2bPriceMovementEntity.class);
			if (tempList.size()==0) {
				b2bPriceMovementDtoEx.setPrice(null);
				b2bPriceMovementDtoEx.setYesterdayPrice(null);
			}
			if (tempList.size()==1) {
				b2bPriceMovementDtoEx.setPrice(tempList.get(0).getPrice());
				b2bPriceMovementDtoEx.setYesterdayPrice(null);
			}
			if (tempList.size()==2) {
				b2bPriceMovementDtoEx.setPrice(tempList.get(0).getPrice());
				b2bPriceMovementDtoEx.setYesterdayPrice(tempList.get(1).getPrice());
			}
		}
		pm.setDataList(list);
		pm.setTotalCount(count);
		pm.setStartIndex(Integer.parseInt(map.get("start").toString()));
		pm.setPageSize(Integer.parseInt(map.get("limit").toString()));
		return pm;
	}

 

}
