package cn.cf.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import cn.cf.PageModel;
import cn.cf.dto.B2bStoreDto;
import cn.cf.entity.OrderSyncToMongo;
import cn.cf.service.B2bOrderSyncToMongoService;
import cn.cf.util.DateUtil;

@Service
public class B2bOrderSyncToMongoServiceImpl implements B2bOrderSyncToMongoService {


        @Autowired
        private MongoTemplate mongoTemplate;


        @Override
        public PageModel<OrderSyncToMongo> searchOrderSyncToMongoList(
                Map<String, Object> map, String storePk) {
            PageModel<OrderSyncToMongo> pm = new PageModel<OrderSyncToMongo>();
            if(null != map.get("searchType") && "2".equals(map.get("searchType").toString())){
                if (null != storePk && !"".equals(storePk)) {
                    map.put("storePk", storePk);
                }
            }

            Query query = queryVoucher(map);
            query.with(new Sort(Sort.Direction.DESC, "insertTime"));
            query.skip(Integer.parseInt(map.get("start").toString())).limit(Integer.parseInt(map.get("limit").toString()));


            List<OrderSyncToMongo> list = mongoTemplate.find(query, OrderSyncToMongo.class);
            int counts = (int) mongoTemplate.count(query, OrderSyncToMongo.class);
            pm.setTotalCount(counts);
            pm.setDataList(list);

                pm.setStartIndex(Integer.parseInt(map.get("start").toString()));
                pm.setPageSize(Integer.parseInt(map.get("limit").toString()));

            return pm;
        }


        private Query queryVoucher(Map<String, Object> map) {
            Criteria c = new Criteria();
//		 c =  Criteria.where("_id").nin("");
            if(null!=map.get("orderNumber")){
                c.and("orderNumber").regex(map.get("orderNumber").toString());
            }
            if(!"".equals(map.get("accountedTimeBegin"))&&null!=map.get("accountedTimeBegin") && "".equals(map.get("accountedTimeEnd"))){
                c.and("insertTime").gte(map.get("accountedTimeBegin").toString());
            }
            if(!"".equals(map.get("accountedTimeEnd"))&&null!=map.get("accountedTimeEnd") && "".equals(map.get("accountedTimeBegin"))){
                c.and("insertTime").lte(map.get("accountedTimeEnd").toString());
            }
            if(!"".equals(map.get("accountedTimeEnd"))&&null!=map.get("accountedTimeEnd") && null!=map.get("accountedTimeBegin")){
                c.andOperator(Criteria.where("insertTime").gte(map.get("accountedTimeBegin").toString()),
                        Criteria.where("insertTime").lte(map.get("accountedTimeEnd").toString()));
            }
            if(null!=map.get("storePk")) {
                List<String> list = new ArrayList<String>();
                String[] strs = map.get("storePk").toString().split(",");
                for (int i = 0; i < strs.length; i++) {
                    list.add(strs[i]);
                }
                c.and("storePk").in(list);
            }
            if(null != map.get("isPush")){
            	c.and("isPush").is(Integer.parseInt(map.get("isPush").toString()));
            }
            return new Query(c);
        }
        
        
    	@Override
    	public void syncOrderNumberToMongo(String orderNumber, B2bStoreDto b2bStoreDto, String detail) throws Exception {

    		OrderSyncToMongo syncToMongo = new OrderSyncToMongo();
    		if (b2bStoreDto!=null && b2bStoreDto.equals("")) {
    			syncToMongo.setStorePk(b2bStoreDto.getPk());
    			syncToMongo.setCompanyPk(b2bStoreDto.getCompanyPk());
    		}
    		syncToMongo.setId(orderNumber);
    		syncToMongo.setIsPush(2);
    		syncToMongo.setInsertTime(DateUtil.formatYearMonthDay(new Date()));
    		syncToMongo.setOrderNumber(orderNumber);
    		syncToMongo.setDetail(detail);
    		Query query = new Query();
    		query.addCriteria(Criteria.where("orderNumber").is(orderNumber).and("isPush").is(2));
    		List<OrderSyncToMongo> list = mongoTemplate.find(query, OrderSyncToMongo.class);
    		if(list != null && list.size() > 0){}else{
    			mongoTemplate.save(syncToMongo);
    		}
    	}


}


