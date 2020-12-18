package cn.cf.service.impl;

import cn.cf.dao.B2bFuturesTypeDao;
import cn.cf.dto.B2bFuturesTypeDto;
import cn.cf.entity.B2bFutures;
import cn.cf.entity.Meg;
import cn.cf.entity.Oil;
import cn.cf.entity.Pta;
import cn.cf.service.MarketService;
import cn.cf.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class MarketServiceImpl implements MarketService {
	@Autowired
	MongoTemplate mongoTemplate;
	@Autowired
	private B2bFuturesTypeDao b2bFuturesTypeDao;

	@Override
	public List<Meg> searchMegList(int days) {
		String fromDay = DateUtil.getYearMonthDay(-days, 2);
		Query query = Query.query(Criteria.where("date").gte(fromDay)
				.and("name").is("MEG1808")); // 默认1808
		query.with(new Sort(new Sort.Order(Sort.Direction.ASC, "date")));
		return mongoTemplate.find(query, Meg.class);
	}

	@Override
	public List<Meg> searchMegList(int days, String name) {
		String fromDay = DateUtil.getYearMonthDay(-days, 2);
		Query query = Query.query(Criteria.where("date").gte(fromDay)
				.and("name").is(name));
		query.with(new Sort(new Sort.Order(Sort.Direction.ASC, "date")));
		return mongoTemplate.find(query, Meg.class);
	}

	@Override
	public List<Pta> searchPtaList(int days, String name) {
		String fromDay = DateUtil.getYearMonthDay(-days, 2);
		Query query = Query.query(Criteria.where("date").gte(fromDay)
				.and("name").is(name));
		query.with(new Sort(new Sort.Order(Sort.Direction.ASC, "date")));
		return mongoTemplate.find(query, Pta.class);
	}

	@Override
	public List<Oil> searchOilList(int days) {
		String fromDay = DateUtil.getYearMonthDay(-days, 2);
		Query query = Query.query(Criteria.where("date").gte(fromDay));
		query.with(new Sort(new Sort.Order(Sort.Direction.ASC, "date")));
		return mongoTemplate.find(query, Oil.class);
	}

	@Override
	public List<B2bFuturesTypeDto> searchFuturesType(Map<String, Object> map) {
		map.put("isHome", 1);
		map.put("isVisable", 1);
		map.put("isDelete", 1);
		map.put("orderName", "sort");
		map.put("orderType", "DESC");
		List<B2bFuturesTypeDto> list=b2bFuturesTypeDao.searchList(map);
		return list;
	}

	@Override
	public List<B2bFutures> searchFutures(Integer days, String futuresPk,String block) {
		String fromDay = DateUtil.getYearMonthDay(-days, 2);
		String nowDay=DateUtil.formatYearMonthDay(new Date());
		Query query = Query.query(Criteria.where("date").gte(fromDay).lt(nowDay)
				.and("futuresPk").is(futuresPk)
				.and("block").is(block));
		query.with(new Sort(new Sort.Order(Sort.Direction.ASC, "date")));
		return mongoTemplate.find(query, B2bFutures.class);
	}
 
}
