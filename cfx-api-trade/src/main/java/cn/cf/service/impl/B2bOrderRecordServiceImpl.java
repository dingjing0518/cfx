package cn.cf.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import cn.cf.dto.B2bMemberDto;
import cn.cf.entity.OrderRecord;
import cn.cf.model.B2bOrder;
import cn.cf.service.B2bOrderRecordService;
import cn.cf.util.DateUtil;
import cn.cf.util.KeyUtils;

@Service
public class B2bOrderRecordServiceImpl implements B2bOrderRecordService{
	
	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public List<OrderRecord> searchOrderRecordList(String orderNumber) {
		Criteria criteria = new Criteria().andOperator(
				Criteria.where("orderNumber").is(orderNumber),Criteria.where("status").gte(-1)
				);
		Query query = new Query(criteria);
		query.with(new Sort(new Order(Direction.DESC,"insertTime")));
		List<OrderRecord> list =  mongoTemplate.find(query, OrderRecord.class);
		List<OrderRecord> newlist = new ArrayList<OrderRecord>();
		if(null != list && list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				if(i>0 && list.get(i).getStatus() == list.get(i-1).getStatus()){
					continue;
				}
				newlist.add(list.get(i));
			}
		}
		return newlist;
	}

	@Override
	public void insertOrderRecord(B2bMemberDto m, B2bOrder order, String type,String content) {
		OrderRecord or = new OrderRecord();
		or.setId(KeyUtils.getUUID());
		or.setOrderNumber(order.getOrderNumber());
		or.setContent(type+content);
		or.setInsertTime(DateUtil.getDateFormat(new Date()));
		if(null != order.getOrderStatus()){
			or.setStatus(order.getOrderStatus());
		}
		if(null != m){
			or.setMobile(m.getMobile());
		}
		mongoTemplate.save(or);
		
	}

}
