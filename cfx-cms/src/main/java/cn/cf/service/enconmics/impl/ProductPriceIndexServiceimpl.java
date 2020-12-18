package cn.cf.service.enconmics.impl;

import cn.cf.PageModel;
import cn.cf.common.Constants;
import cn.cf.common.QueryModel;
import cn.cf.common.utils.CommonUtil;
import cn.cf.dao.B2bProductExtDao;
import cn.cf.dao.B2bProductPriceIndexExtDao;
import cn.cf.dao.SysExcelStoreExtDao;
import cn.cf.dto.B2bProductDto;
import cn.cf.dto.B2bProductPriceIndexDto;
import cn.cf.dto.B2bProductPriceIndexExtDto;
import cn.cf.entity.PricProductIndexParams;
import cn.cf.entity.ProductPriceIndexEntry;
import cn.cf.json.JsonUtils;
import cn.cf.model.B2bProductPriceIndex;
import cn.cf.model.ManageAccount;
import cn.cf.model.SysExcelStore;
import cn.cf.service.enconmics.ProductPriceIndexService;
import cn.cf.util.KeyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ProductPriceIndexServiceimpl implements ProductPriceIndexService {

    @Autowired
    private B2bProductPriceIndexExtDao b2bProductPriceIndexDao;

    @Autowired
    private B2bProductExtDao b2bProductExtDao;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private SysExcelStoreExtDao sysExcelStoreExtDao;

    @Override
    public PageModel<B2bProductPriceIndexExtDto> searchProductPriceIndexLis(QueryModel<B2bProductPriceIndexExtDto> qm) {
        PageModel<B2bProductPriceIndexExtDto> pm = new PageModel<>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("start", qm.getStart());
        map.put("limit", qm.getLimit());
        map.put("orderName", qm.getFirstOrderName());
        map.put("orderType", qm.getFirstOrderType());
        map.put("productName", qm.getEntity().getProductName());
        map.put("updateStartTime", qm.getEntity().getUpdateStartTime());
        map.put("updateEndTime", qm.getEntity().getUpdateEndTime());
        map.put("isVisable", qm.getEntity().getIsVisable());
        int totalCount = b2bProductPriceIndexDao.searchGridExtCount(map);
        List<B2bProductPriceIndexExtDto> list = b2bProductPriceIndexDao.searchGridExt(map);
        pm.setTotalCount(totalCount);
        pm.setDataList(list);
        return pm;
    }

    @Override
    public List<B2bProductDto> getProductList() {

        return b2bProductExtDao.getAllIsvisableProductList();
    }

    @Override
    public B2bProductPriceIndexDto getProductIndexByPk(String pk) {
        return b2bProductPriceIndexDao.getByPk(pk);
    }

    @Override
    public int updateProductIndex(B2bProductPriceIndex priceIndex) {

        int result = 0;
        Date date = new Date();
        priceIndex.setUpdateTime(date);
        if (CommonUtil.isNotEmpty(priceIndex.getPk())){
            result = b2bProductPriceIndexDao.updateObj(priceIndex);
        }else{

            Map<String,Object> map = new HashMap<>();
            map.put("productPk",priceIndex.getProductPk());
            List<B2bProductPriceIndexDto> list = b2bProductPriceIndexDao.searchList(map);
            if(list != null && list.size() > 0){
                result = -1;
            } else{
                priceIndex.setPk(KeyUtils.getUUID());
                priceIndex.setInsertTime(date);
                priceIndex.setUpdateTime(date);
                priceIndex.setIsDelete(Constants.ONE);
                priceIndex.setIsVisable(Constants.ONE);
                result = b2bProductPriceIndexDao.insert(priceIndex);
            }
        }

        return result;
    }

    @Override
    public PageModel<ProductPriceIndexEntry> searchTransactionProductPriceIndexLis(QueryModel<ProductPriceIndexEntry> qm) {

        PageModel<ProductPriceIndexEntry> pm = new PageModel<>();

        Criteria criteria = new Criteria();
        criteria.andOperator(Criteria.where("_id").nin(""));

        if(null!=qm.getEntity().getGoodsInfo() && !"".equals(qm.getEntity().getGoodsInfo())){
            criteria.and("goodsInfo").regex(qm.getEntity().getGoodsInfo());
        }
        if(null!=qm.getEntity().getPurchaserName()&& !"".equals(qm.getEntity().getPurchaserName())){
            criteria.and("purchaserName").is(qm.getEntity().getPurchaserName());
        }
        if(null!=qm.getEntity().getLoanNumber() && !"".equals(qm.getEntity().getLoanNumber())){
            criteria.and("loanNumber").is(qm.getEntity().getLoanNumber());
        }
        if(null!=qm.getEntity().getIsConfirm() && !"".equals(qm.getEntity().getIsConfirm())){
            criteria.and("isConfirm").is(qm.getEntity().getIsConfirm());
        }
        String start = qm.getEntity().getRepaymentTimeStart();
        String end = qm.getEntity().getRepaymentTimeEnd();
        if(CommonUtil.isNotEmpty(start) && CommonUtil.isNotEmpty(end)){
            criteria.and("repaymentTime").gte(start).lte(end);
        }
        if(!CommonUtil.isNotEmpty(start) && CommonUtil.isNotEmpty(end)){
            criteria.and("repaymentTime").lte(end);
        }
        if(CommonUtil.isNotEmpty(start) && !CommonUtil.isNotEmpty(end)){
            criteria.and("repaymentTime").gte(start);
        }
        criteria.and("priceIndex").exists(true);
        Query query = new Query(criteria);
        if ("desc".equals(qm.getFirstOrderType())) {
            query.with(new Sort(Sort.Direction.DESC, qm.getFirstOrderName()));
        } else {
            query.with(new Sort(Sort.Direction.ASC, qm.getFirstOrderName()));
        }
     /*   query.skip(qm.getStart()).limit(qm.getLimit());*/
        List<ProductPriceIndexEntry> list = mongoTemplate.find(query, ProductPriceIndexEntry.class);
        int counts = (int) mongoTemplate.count(query, ProductPriceIndexEntry.class);
        List<ProductPriceIndexEntry> tempList = new ArrayList<>();
        if (counts > 0){
            for (ProductPriceIndexEntry entry:list) {
                B2bProductPriceIndexDto priceIndex = b2bProductPriceIndexDao.getExtByProductPk(entry.getProductPk());
                if (priceIndex != null){
                    entry.setNowPriceIndex(priceIndex.getPriceIndex());
                    //计算涨跌幅
                    if(priceIndex.getPriceIndex() != null && entry.getPriceIndex() != null){
                        //涨跌幅=(当前价格指数-成交价格指数)/成交价格指数
                        DecimalFormat dF = new DecimalFormat("0.00");
                        Integer dividePrice = priceIndex.getPriceIndex() - entry.getPriceIndex();
                        float  i =  (float)dividePrice/entry.getPriceIndex();
                        double percent =Double.parseDouble(dF.format((i <0.1 && i >-0.1)?0d:i));
                        double floorPer =0d;
                    		if (percent<0) {
                    			floorPer  = Math.ceil(percent*10);
                    			 entry.setRiseAndFall(floorPer*10);
                                 //需缴纳保证金 需补交保证金XXX元=该商品的未发吨数*商品单价*涨跌幅-已缴纳保证金（用户在确认窗口填写的本次缴纳保证金金额累加
                                 if (floorPer/10 <= -0.1){
                                     if (entry.getWeightShipped() != null){
                                         double weightShipped = entry.getWeightShipped().doubleValue();
                                         double presentPrice = entry.getPresentPrice() == null?0d:entry.getPresentPrice().doubleValue();
                                         double depositMount = entry.getDepositMount() == null?0d:entry.getDepositMount().doubleValue();
                                         //应缴金额
                                         double dueofpayMount = Double.parseDouble(dF.format(weightShipped*presentPrice*Math.abs(floorPer/10) - depositMount));
                                         if (dueofpayMount > 0d) {
                                             entry.setDueofpayMount(dueofpayMount);
                                         }
                                     }
                                 }
							}
                        if (floorPer<0d) {
                        	tempList.add(entry);
						}
                    }
                }
                
            }
        }
        List<ProductPriceIndexEntry> reslist =  new ArrayList<>();
       if ( tempList.size()>0) {
    	   int  pageNum  =  qm.getStart()/qm.getLimit()+1;
    	   reslist  =   startPage (tempList, pageNum,qm.getLimit());
       }
        pm.setTotalCount(tempList.size());
        pm.setDataList(reslist);
        return pm;
    }
  

        /**
         * 开始分页
         * @param tempList
         * @param pageNum 页码
         * @param pageSize 每页多少条数据
         * @return
         */
        public static List<ProductPriceIndexEntry> startPage(List<ProductPriceIndexEntry> tempList, Integer pageNum,
                Integer pageSize) {
            if (tempList == null) {
                return null;
            }
            if (tempList.size() == 0) {
                return null;
            }

            Integer count = tempList.size(); // 记录总数
            Integer pageCount = 0; // 页数
            if (count % pageSize == 0) {
                pageCount = count / pageSize;
            } else {
                pageCount = count / pageSize + 1;
            }

            int fromIndex = 0; // 开始索引
            int toIndex = 0; // 结束索引

            if (pageNum != pageCount) {
                fromIndex = (pageNum - 1) * pageSize;
                toIndex = fromIndex + pageSize;
            } else {
                fromIndex = (pageNum - 1) * pageSize;
                toIndex = count;
            }

            List<ProductPriceIndexEntry> pageList = tempList.subList(fromIndex, toIndex);

            return pageList;
        }
    
    @Override
    public void saveProductPriceIndexExcelToOss(PricProductIndexParams params, ManageAccount account) {
        Date time = new Date();
        try {
            Map<String,String> insertMap = CommonUtil.checkExportTime(params.getRepaymentTimeStart(), params.getRepaymentTimeEnd(), new SimpleDateFormat("yyyy-MM-dd").format(time));
            params.setRepaymentTimeStart(insertMap.get("startTime"));
            params.setRepaymentTimeEnd(insertMap.get("endTime"));
            String json = JsonUtils.convertToString(params);
            SysExcelStore store = new SysExcelStore();
            store.setPk(KeyUtils.getUUID());
            store.setMethodName("exportPriceIndex_"+ KeyUtils.getUUID());
            store.setParams(json);
            store.setIsDeal(Constants.TWO);
            store.setInsertTime(time);
            store.setName("金融中心-数据管理-成交价格指数-导出");
            store.setType(Constants.TWO);
            store.setAccountPk(account.getPk());

            String pararamsName = "";
            if (CommonUtil.isNotEmpty(params.getGoodsInfo())) {
                pararamsName += "品名:" + params.getGoodsInfo() + ";";
            }
            if (CommonUtil.isNotEmpty(params.getPurchaserName())) {
                pararamsName += "采购商名称:" + params.getPurchaserName() + ";";
            }
            try {
                if (CommonUtil.isNotEmpty(CommonUtil.checkUpdateExportTime(params.getRepaymentTimeStart(), params.getRepaymentTimeEnd()))) {
                    pararamsName += "到期时间:" + CommonUtil.checkUpdateExportTime(params.getRepaymentTimeStart(), params.getRepaymentTimeEnd()) + ";";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (params.getIsConfirm() != null) {
                String isConfirm = "";
                if (params.getIsConfirm() == 1){
                    isConfirm = "未确认";
                } else if (params.getIsConfirm() == 2){
                    isConfirm = "已确认";
                }
                pararamsName += "状态:" + isConfirm + ";";
            }
            if (CommonUtil.isNotEmpty(params.getLoanNumber())) {
                pararamsName += "借款单号:" + params.getLoanNumber() + ";";
            }
            store.setParamsName(pararamsName);
            sysExcelStoreExtDao.insert(store);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
