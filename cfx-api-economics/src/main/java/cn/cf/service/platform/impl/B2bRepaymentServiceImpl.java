package cn.cf.service.platform.impl;

import cn.cf.PageModel;
import cn.cf.dao.B2bLoanNumberDaoEx;
import cn.cf.dto.B2bLoanNumberDto;
import cn.cf.entity.B2bRepaymentRecord;
import cn.cf.service.platform.B2bRepaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import java.util.List;
import java.util.Map;

/**
 * @author FJM
 * @description:
 * @date 2020/6/3
 */
@Service
public class B2bRepaymentServiceImpl implements B2bRepaymentService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private B2bLoanNumberDaoEx b2bLoanNumberDaoEx;

    @Override
    public PageModel<B2bRepaymentRecord> searchListByPage(Map<String, Object> map) {
        PageModel<B2bRepaymentRecord> pm = new PageModel<>();
        Query query = queryVoucher(map);
        query.with(new Sort(Direction.DESC, "createTime"));
        Integer start = Integer.parseInt(map.get("start").toString());
        Integer limit = Integer.parseInt(map.get("limit").toString());
        query.skip(start).limit(limit);
        List<B2bRepaymentRecord> list = mongoTemplate.find(query,
                B2bRepaymentRecord.class);
        if(null != list && list.size()>0){
            for(B2bRepaymentRecord r : list){
                r.setCreateTime(r.getCreateTime().replaceAll("-|/",""));
            }
        }
        int counts = (int) mongoTemplate.count(query, B2bRepaymentRecord.class);
        pm.setTotalCount(counts);
        pm.setDataList(list);
        pm.setStartIndex(start);
        pm.setPageSize(limit);
        return pm;
    }

    @Override
    public B2bRepaymentRecord getById(String id) {
        B2bRepaymentRecord dto = mongoTemplate.findById(id, B2bRepaymentRecord.class);
        if(null != dto && null != dto.getOrderNumber()){
            B2bLoanNumberDto order = b2bLoanNumberDaoEx.getByOrderNumber(dto.getOrderNumber());
            if(null != order){
                dto.setLoanStartTime(order.getLoanStartTime());
                dto.setLoanEndTime(order.getLoanEndTime());
                dto.setCreateTime(dto.getCreateTime().replaceAll("-|/",""));
            }
        }
        return dto;
    }

    private Query queryVoucher(Map<String, Object> map) {
        Criteria c = new Criteria();
        if (null != map.get("startTime")
                && null == map.get("endTime")) {
            System.out.println("startTime:"+map.get("startTime").toString());
            c.and("createTime").gte(
                    map.get("startTime").toString());
        }
        if (null != map.get("endTime")
                && null == map.get("startTime")) {
            System.out.println("endTime:"+map.get("endTime").toString());
            c.and("createTime").lte(
                    map.get("endTime").toString());
        }
        if (null != map.get("endTime")
                && null != map.get("startTime")) {
            System.out.println("startTime:"+map.get("startTime").toString()+"endTime:"+map.get("endTime").toString());
            c.andOperator(
                    Criteria.where("createTime").gte(
                            map.get("startTime").toString()),
                    Criteria.where("createTime").lte(
                            map.get("endTime").toString()));
        }

        if (null != map.get("companyPk")) {
            c.and("companyPk").is(map.get("companyPk").toString());
        }
        if (null != map.get("orderNumber")) {
            System.out.println("orderNumber:"+map.get("orderNumber").toString());
            c.and("orderNumber").is(map.get("orderNumber").toString());
        }
        return new Query(c);
    }


}
