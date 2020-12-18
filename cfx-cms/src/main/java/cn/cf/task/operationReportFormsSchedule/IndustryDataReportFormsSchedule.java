package cn.cf.task.operationReportFormsSchedule;

import cn.cf.common.Constants;
import cn.cf.common.utils.CommonUtil;
import cn.cf.dao.B2bCompanyExtDao;
import cn.cf.dao.B2bOrderExtDao;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bOrderDto;
import cn.cf.entity.*;
import cn.cf.util.KeyUtils;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
@EnableScheduling
public class IndustryDataReportFormsSchedule {

    private final static Logger logger = LoggerFactory.getLogger(IndustryDataReportFormsSchedule.class);
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private B2bOrderExtDao b2bOrderExtDao;

    @Autowired
    private B2bCompanyExtDao b2bCompanyExtDao;


    /**
     * 行情数据每月的21号执行，获取上月21号到本月20号数据
     */
    @Scheduled(cron = "0 30 0 21 * ?")
    //@Scheduled(cron = "0 0/5 * * * ?")
    public void transactionDataStore() {
        logger.error("++++++++++++++++++++++++++++++++IndustryDataReportFormsSchedule");
        Date date = new Date();
        // 上月21号
        String lastMonth = CommonUtil.specifyMonth(-1, 21, date);
        // 本月20号
        String selfMonth = CommonUtil.specifyMonth(0, 20, date);
        Calendar calendar = Calendar.getInstance();// 日历对象
        calendar.setTime(date);// 设置当前日期
        int month = calendar.get(Calendar.MONTH) + 1;// 当前月
        String year = new SimpleDateFormat("yyyy").format(date);
        //统计当前月份数据
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startTime", lastMonth);
        map.put("endTime", selfMonth);
        saveDataToMongo(month, year, map);//1不是历史数据，2历史数据

        //统计过去两年的历史数据
        for (int i = 1; i < 25; i++) {

            String hLastMonth = CommonUtil.specifyMonth(-(1 + i), 21, date);

            String hSelfMonth = CommonUtil.specifyMonth(-i, 20, date);
            //截取以前的年份
            String hyear = hSelfMonth.substring(0, 4);
            //以前月份
            int hmonth = CommonUtil.specifyMonths((24) - i, date);
            map.put("startTime", hLastMonth);
            map.put("endTime", hSelfMonth);
            saveDataToMongo(hmonth, hyear, map);
        }
    }

    private void saveDataToMongo(int month, String year, Map<String, Object> map) {
        List<IndustryProductSpecTopRF> productSpecList = b2bOrderExtDao.searchIndustryProductSpecCounts(map);
        Query query = new Query();
        query.addCriteria(Criteria.where("month").is(month).and("year").is(year));
        List<IndustryProductSpecTopRF> isSpeclist = mongoTemplate.find(query, IndustryProductSpecTopRF.class);
        //按规格统计行情Top10
        matchProductSpecAmount(month, year, productSpecList, isSpeclist);

        //按店铺统计行情Top10
        matchStoreAmount(month, year, map, query);

        //按采购商统计行情Top10
        matchPurchaserAmount(month, year, map, query);
    }

    private void matchPurchaserAmount(int month, String year, Map<String, Object> map, Query query) {
        List<IndustryPurchaserTopRF> purchaserList = b2bOrderExtDao.searchIndustryPurchaserCounts(map);
        List<IndustryPurchaserTopRF> isPurchaserlist = mongoTemplate.find(query, IndustryPurchaserTopRF.class);
        if (isPurchaserlist != null && isPurchaserlist.size() > 0) {
        } else {

            if (purchaserList != null && purchaserList.size() > 0) {
                int topIndex = 0;
                for (IndustryPurchaserTopRF industryPurchaserTopRF : purchaserList) {
                    B2bCompanyDto company = b2bCompanyExtDao.getByPk(industryPurchaserTopRF.getPurchaserPk());
                    IndustryPurchaserTopRF rf = new IndustryPurchaserTopRF();
                    rf.setId(KeyUtils.getUUID());
                    rf.setMonth(month);
                    rf.setYear(year);
                    rf.setAllAmount(industryPurchaserTopRF.getAllAmount());
                    rf.setWeight(new BigDecimal(industryPurchaserTopRF.getAfterWeight()));
                    rf.setCounts(industryPurchaserTopRF.getCounts());
                   String  purchaserName = "";
                    if (company != null){
                        purchaserName = company.getName();
                    }
                    rf.setPurchaserName(purchaserName);
                    rf.setNumbers(++topIndex);
                    mongoTemplate.save(rf);
                }
            }
        }
    }

    private void matchProductSpecAmount(int month, String year, List<IndustryProductSpecTopRF> productSpecList, List<IndustryProductSpecTopRF> isSpeclist) {
        if (isSpeclist != null && isSpeclist.size() > 0) {

        } else {
            if (productSpecList != null && productSpecList.size() > 0) {
                int topIndex = 0;
                for (IndustryProductSpecTopRF industryProductSpecTopRF : productSpecList) {

                    IndustryProductSpecTopRF rf = new IndustryProductSpecTopRF();
                    rf.setId(KeyUtils.getUUID());
                    rf.setMonth(month);
                    String productName = "";
                    if (CommonUtil.isNotEmpty(industryProductSpecTopRF.getProductName())){
                        if (!industryProductSpecTopRF.getProductName().contains("{")&&
                                !industryProductSpecTopRF.getProductName().contains("}")){
                            productName = industryProductSpecTopRF.getProductName();
                        }
                    }
                    String specName = "";
                    if (CommonUtil.isNotEmpty(industryProductSpecTopRF.getSpecName())){
                        if (!industryProductSpecTopRF.getSpecName().contains("{") &&
                                !industryProductSpecTopRF.getSpecName().contains("}")){
                            specName = industryProductSpecTopRF.getSpecName();
                        }
                    }
                    rf.setProductName(productName);
                    rf.setSpecName(specName);
                    rf.setAllAmount(industryProductSpecTopRF.getAllAmount());
                    rf.setWeight(new BigDecimal(industryProductSpecTopRF.getAfterWeight()));
                    rf.setYear(year);
                    rf.setNumbers(++topIndex);
                    mongoTemplate.save(rf);
                }
            }
        }
    }

    private void matchStoreAmount(int month, String year, Map<String, Object> map, Query query) {
        List<IndustryStoreTopRF> storeList = b2bOrderExtDao.searchIndustryStoreCounts(map);
        List<IndustryStoreTopRF> isStorelist = mongoTemplate.find(query, IndustryStoreTopRF.class);
        if (isStorelist != null && isStorelist.size() > 0) {
        } else {
            if (storeList != null && storeList.size() > 0) {
                int topIndex = 0;
                for (IndustryStoreTopRF industryStoreTopRF : storeList) {
                    IndustryStoreTopRF store = new IndustryStoreTopRF();
                    store.setId(KeyUtils.getUUID());
                    store.setMonth(month);
                    store.setYear(year);
                    store.setNumbers(++topIndex);
                    store.setStoreName(industryStoreTopRF.getStoreName());
                    store.setAllAmount(industryStoreTopRF.getAllAmount());
                    store.setCounts(industryStoreTopRF.getCounts());
                    store.setWeight(new BigDecimal(industryStoreTopRF.getAfterWeight()));
                    mongoTemplate.save(store);
                }
            }
        }
    }
}
