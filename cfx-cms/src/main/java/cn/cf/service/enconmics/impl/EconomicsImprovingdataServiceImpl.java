package cn.cf.service.enconmics.impl;

import cn.cf.service.enconmics.EconomicsImprovingdataService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import cn.cf.entity.B2bEconomicsImprovingdataEntity;
import cn.cf.entity.EconCustomerMongo;

@Service
public class EconomicsImprovingdataServiceImpl implements EconomicsImprovingdataService {

	
	@Autowired
	private  MongoTemplate mongoTemplate;
	
	@Override
	public B2bEconomicsImprovingdataEntity getMongoByEconomCustPk(String processInstanceId) {
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("processInstanceId").is(processInstanceId));
		Query query = new Query(criteria);
		EconCustomerMongo econCustomerMongo = mongoTemplate.findOne(query, EconCustomerMongo.class);
		return econCustomerMongo.getImprovingdataDto();
	}


}
