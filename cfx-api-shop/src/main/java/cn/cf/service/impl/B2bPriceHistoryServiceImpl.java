package cn.cf.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import cn.cf.entity.B2bPriceMovementEntity;
import cn.cf.service.B2bPriceHistoryService;
import cn.cf.util.DateUtil;

@Service
public class B2bPriceHistoryServiceImpl implements B2bPriceHistoryService {
	
	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public List<B2bPriceMovementEntity> searchB2bPriceHistoryList(
			String pk,Integer days) {
		  String date = DateUtil.getYearMonthDay(-days, 2);
		  Criteria c = new Criteria();
		  c.and("date").gte(date).and("movementPk").is(pk).and("isShow").is(1);
		  Query query = new Query(c);
		  query.with(new Sort(new Sort.Order(Sort.Direction.ASC, "date")));
		return mongoTemplate.find(query, B2bPriceMovementEntity.class);
	}
	
	
}
