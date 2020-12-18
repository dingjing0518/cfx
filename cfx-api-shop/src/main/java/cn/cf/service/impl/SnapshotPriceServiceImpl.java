package cn.cf.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import cn.cf.dao.B2bGoodsDao;
import cn.cf.dto.B2bGoodsDto;
import cn.cf.entity.GoodsPrice;
import cn.cf.entity.GoodsPriceData;
import cn.cf.json.JsonUtils;
import cn.cf.service.SnapshotPriceService;
import cn.cf.util.DateUtil;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.BasicDBObject;

@Service
public class SnapshotPriceServiceImpl implements SnapshotPriceService {
    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    B2bGoodsDao b2bGoodsDao;

    @Override
    public void action() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("isDelete", 1);
        List<B2bGoodsDto> b2bGoodsDtos = b2bGoodsDao.searchGrid(map);
        for (B2bGoodsDto b2bGoodsDto : b2bGoodsDtos) {
            save(b2bGoodsDto);
        }

    }

    @Override
    public void remove() {
        String fromDay = DateUtil.getYearMonthDay(-100, 2);
        Query query = Query.query(Criteria.where("data.date").lt(fromDay));
        mongoTemplate.updateMulti(query,
                new Update().pull("data", Query.query(Criteria.where("date").lt(fromDay))), "goodsPrice");
    }

    @Override
    public List<GoodsPriceData> searchList(String pk, int days) {
        List<GoodsPriceData> result = new ArrayList<GoodsPriceData>();
        String fromDay = DateUtil.getYearMonthDay(-days, 2);
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.unwind("data"),
                Aggregation.match(Criteria.where("data.date").gte(fromDay).andOperator(Criteria.where("pk").is(pk))),
                Aggregation.sort(Sort.Direction.ASC, "data.date")

        );
        AggregationResults<BasicDBObject> aggregate = mongoTemplate.aggregate(aggregation, GoodsPrice.class, BasicDBObject.class);
        List<BasicDBObject> mappedResults = aggregate.getMappedResults();

        for (BasicDBObject goodsPriceData : mappedResults) {
            JSONObject jsonObject = JsonUtils.toJSONObject( goodsPriceData.get("data").toString());
            GoodsPriceData priceData = new GoodsPriceData();
            priceData.setDate(jsonObject.getString("date"));
            priceData.setPrice(jsonObject.getDouble("price"));
            result.add(priceData);
        }


        return result;

    }


    private void save(B2bGoodsDto goodsPrice) {
        Query query = new Query();
        GoodsPriceData price = new GoodsPriceData();
        price.setDate(DateUtil.getToday(new SimpleDateFormat("yyyy-MM-dd")));
        price.setPrice(goodsPrice.getTonPrice());
        price.setIsUpDown(goodsPrice.getIsUpdown());
        query.addCriteria(Criteria.where("pk").is(goodsPrice.getPk()));
        Update update = new Update();
        update.addToSet("data", price);
        mongoTemplate.upsert(query, update, GoodsPrice.class);
    }


}
