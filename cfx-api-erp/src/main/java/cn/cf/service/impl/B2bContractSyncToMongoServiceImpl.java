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
import cn.cf.entity.ContractSyncToMongo;
import cn.cf.service.B2bContractSyncToMongoService;
import cn.cf.util.DateUtil;

@Service
public class B2bContractSyncToMongoServiceImpl implements B2bContractSyncToMongoService {

    @Autowired
    private MongoTemplate mongoTemplate;


    @Override
    public PageModel<ContractSyncToMongo> searchContractSyncToMongoList(
            Map<String, Object> map,String storePk) {
        PageModel<ContractSyncToMongo> pm = new PageModel<ContractSyncToMongo>();
        if(null != map.get("searchType") && "1".equals(map.get("searchType").toString())){
                if (null != storePk && !"".equals(storePk)) {
                    map.put("storePk",storePk);
                }
            }

        Query query = queryVoucher(map);
        query.with(new Sort(Sort.Direction.DESC, "insertTime"));
        Integer start = Integer.parseInt(map.get("start").toString());
		 Integer limit = Integer.parseInt(map.get("limit").toString());
        query.skip(start).limit(limit);

        List<ContractSyncToMongo> list = mongoTemplate.find(query, ContractSyncToMongo.class);
        int counts = (int) mongoTemplate.count(query, ContractSyncToMongo.class);
        pm.setTotalCount(counts);
        pm.setDataList(list);
        if (null != map.get("start")) {
            pm.setStartIndex(start);
            pm.setPageSize(limit);
        }

        return pm;
    }


    private Query queryVoucher(Map<String, Object> map) {
        Criteria c = new Criteria();
//		 c =  Criteria.where("_id").nin("");
        if(null!=map.get("contractNumber")){
            c.and("contractNumber").regex(map.get("contractNumber").toString());
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
	public void syncContractToMongo(String contractNo, B2bStoreDto b2bstore, String detail) throws Exception {
		ContractSyncToMongo syncToMongo = new ContractSyncToMongo();
		syncToMongo.setId(contractNo);
		syncToMongo.setCompanyPk(b2bstore.getCompanyPk());
		syncToMongo.setIsPush(2);//
		syncToMongo.setStorePk(b2bstore.getPk());
		syncToMongo.setInsertTime(DateUtil.formatYearMonthDay(new Date()));
		syncToMongo.setContractNumber(contractNo);
		syncToMongo.setDetail(detail);
		
		Query query = new Query();
		query.addCriteria(Criteria.where("contractNo").is(contractNo).and("isPush").is(2));
		List<ContractSyncToMongo> list = mongoTemplate.find(query, ContractSyncToMongo.class);
		if(list != null && list.size() > 0){}else{
			mongoTemplate.save(syncToMongo);
		}

	}
}
