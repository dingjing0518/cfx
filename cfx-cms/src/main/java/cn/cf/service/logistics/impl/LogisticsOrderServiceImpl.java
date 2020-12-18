package cn.cf.service.logistics.impl;

import cn.cf.PageModel;
import cn.cf.common.ColAuthConstants;
import cn.cf.common.QueryModel;
import cn.cf.common.utils.CommonUtil;
import cn.cf.dao.LgDeliveryExceptionPicExtDao;
import cn.cf.dao.LgDeliveryOrderExtDao;
import cn.cf.dao.LgDeliveryOrderGoodsExtDao;
import cn.cf.dao.LgTrackDetailExDao;
import cn.cf.dto.LgOrderDetailDto;
import cn.cf.dto.LgTrackDto;
import cn.cf.dto.LogisticsLinePriceDto;
import cn.cf.dto.LogisticsRouteDto;
import cn.cf.entity.DeliveryForm;
import cn.cf.entity.LogisticsOrderAddress;
import cn.cf.entity.SearchLgLine;
import cn.cf.model.LgOrderGridModel;
import cn.cf.model.LgOrderSearchModel;
import cn.cf.service.logistics.LogisticsOrderService;
import cn.cf.util.ArithUtil;
import cn.cf.util.KeyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LogisticsOrderServiceImpl implements LogisticsOrderService {

    @Autowired
    private LgDeliveryOrderExtDao lgOrderExtDao;

    @Autowired
    private LgDeliveryOrderGoodsExtDao lgOrderGoodsExtDao;

    @Autowired
    private LgTrackDetailExDao lgTrackDetailExDao;

    @Autowired
    private LgDeliveryExceptionPicExtDao lgDeliveryExceptionPicDao;

    @Autowired
    private MongoTemplate mongoTemplate;


    /**
     * 正常/异常订单列表
     */
    @Override
    public PageModel<LgOrderGridModel> getlgOrder(QueryModel<LgOrderSearchModel> qm, int i,String accountPk) {
        PageModel<LgOrderGridModel> pm = new PageModel<LgOrderGridModel>();
        Map<String, Object> map = new HashMap<String, Object>();
        // 是否需要分页
        if (i == 1) {
            map.put("start", qm.getStart());
            map.put("limit", qm.getLimit());
        }
        map.put("orderName", qm.getFirstOrderName());
        map.put("orderType", qm.getFirstOrderType());
        map.put("orderNum", qm.getEntity().getOrderNumber());
        map.put("orderTimeStart", qm.getEntity().getOrderTimeStart());
        map.put("orderTimeEnd", qm.getEntity().getOrderTimeEnd());
        map.put("goodsInfo", qm.getEntity().getGoodsInfo());
        map.put("fromCompanyName", qm.getEntity().getFromCompanyName());
        map.put("fromContactsTel", qm.getEntity().getFromContactsTel());
        map.put("toCompanyName", qm.getEntity().getToCompanyName());
        map.put("toContactsTel", qm.getEntity().getToContactsTel());
        map.put("orderStatus", qm.getEntity().getOrderStatus());
        map.put("arrivedTimeStart", qm.getEntity().getArrivedTimeStart());
        map.put("arrivedTimeEnd", qm.getEntity().getArrivedTimeEnd());
        map.put("isAbnormal", qm.getEntity().getIsAbnormal());// 是否异常
        map.put("isConfirmed", qm.getEntity().getIsConfirmed());// 异常确认
        map.put("isMatched", qm.getEntity().getIsMatched());// 是否匹配
        if ( qm.getEntity().getIsAbnormal()==1) {//正常
            map.put("colLogisticsComName", CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_ORDER_NORMAL_COL_LOGISTICSCOMNAME));
            map.put("colFromComName",CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_ORDER_NORMAL_COL_FROMCOMNAME));
            map.put("colFromAddress", CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_ORDER_NORMAL_COL_FROMADDRESS));
            map.put("colFromContacts", CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_ORDER_NORMAL_COL_FROMCONTACTS));
            map.put("colFromContactsTel", CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_ORDER_NORMAL_COL_FROMCONTACTSTEL));
            map.put("colToComName", CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_ORDER_NORMAL_COL_TOCOMNAME));
            map.put("colToAddress", CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_ORDER_NORMAL_COL_TOADDRESS));
            map.put("colToContacts", CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_ORDER_NORMAL_COL_TOCONTACTS));
            map.put("colToContactsTel", CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_ORDER_NORMAL_COL_TOCONTACTSTEL));
        }
        if ( qm.getEntity().getIsAbnormal()==2) {
            map.put("colLogisticsComName", CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_ORDER_ORDERERROR_COL_LOGISTICSCOMNAME));
            map.put("colFromComName",CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_ORDER_ORDERERROR_COL_FROMCOMNAME));
            map.put("colFromAddress", CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_ORDER_ORDERERROR_COL_FROMADDRESS));
            map.put("colFromContacts", CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_ORDER_ORDERERROR_COL_FROMCONTACTS));
            map.put("colFromContactsTel", CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_ORDER_ORDERERROR_COL_FROMCONTACTSTEL));
            map.put("colToComName", CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_ORDER_ORDERERROR_COL_TOCOMNAME));
            map.put("colToAddress", CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_ORDER_ORDERERROR_COL_TOADDRESS));
            map.put("colToContacts", CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_ORDER_ORDERERROR_COL_TOCONTACTS));
            map.put("colToContactsTel", CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_ORDER_ORDERERROR_COL_TOCONTACTSTEL));
        }
      
        int totalCount = lgOrderExtDao.countlgOrder(map);
        List<LgOrderGridModel> list = lgOrderExtDao.getlgOrder(map);
        if (i == 2) {
            if (list.size() > 0) {
                for (LgOrderGridModel lgOrderGridModel : list) {
                    if (lgOrderGridModel.getSupplierName() != null && !lgOrderGridModel.getSupplierName().equals("")) {
                        lgOrderGridModel.setFromCompanyName(lgOrderGridModel.getSupplierName());
                    }
                }
            }
        }
        pm.setTotalCount(totalCount);
        pm.setDataList(list);
        return pm;
    }

    /**
     * 运货单详情
     */
    @Override
    public HashMap<String, Object> selectDetailByDeliveryPk(String pk,String accountPk) {
        HashMap<String, Object> data = new HashMap<String, Object>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pk",pk);
        map.put("colInvoinceName", CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_ORDER_NORMAL_COL_INVOINCENAME));
        map.put("colInvoinceTaxidNum", CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_ORDER_NORMAL_COL_INVOINCETAXIDNUM));
        map.put("colInvoinceBankAccount", CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_ORDER_NORMAL_COL_INVOINCEBANKACCOUNT));
        map.put("colInvoinceBankName", CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_ORDER_NORMAL_COL_INVOINCEBANKNAME));

        map.put("colFromComName",CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_ORDER_NORMAL_COL_FROMCOMNAME));
        map.put("colFromAddress", CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_ORDER_NORMAL_COL_FROMADDRESS));
        map.put("colFromContacts", CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_ORDER_NORMAL_COL_FROMCONTACTS));
        map.put("colFromContactsTel", CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_ORDER_NORMAL_COL_FROMCONTACTSTEL));
        map.put("colToComName", CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_ORDER_NORMAL_COL_TOCOMNAME));
        map.put("colToAddress", CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_ORDER_NORMAL_COL_TOADDRESS));
        map.put("colToContacts", CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_ORDER_NORMAL_COL_TOCONTACTS));
        map.put("colToContactsTel", CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_ORDER_NORMAL_COL_TOCONTACTSTEL));
       
        LgOrderDetailDto vo = lgOrderExtDao.selectHideDetailByPk(map);
        data.put("mainData", vo);
        data.put("trackDetail", lgTrackDetailExDao.selectTrackDetailBydeliveryPk(pk));
        return data;
    }

    /**
     * 异常反馈详情
     */
    @Override
    public HashMap<String, Object> lgOrderFeedBackDetail(String pk, String accountPk) {
        HashMap<String, Object> data = new HashMap<String, Object>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pk",pk);
        map.put("colInvoinceName", true);
        map.put("colInvoinceTaxidNum", true);
        map.put("colInvoinceBankAccount", true);
        map.put("colInvoinceBankName", true);

        map.put("colLogisticsComName", CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_ORDER_ORDERERROR_COL_LOGISTICSCOMNAME));
        map.put("colFromComName",CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_ORDER_ORDERERROR_COL_FROMCOMNAME));
        map.put("colFromAddress", CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_ORDER_ORDERERROR_COL_FROMADDRESS));
        map.put("colFromContacts", CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_ORDER_ORDERERROR_COL_FROMCONTACTS));
        map.put("colFromContactsTel", CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_ORDER_ORDERERROR_COL_FROMCONTACTSTEL));
        map.put("colToComName", CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_ORDER_ORDERERROR_COL_TOCOMNAME));
        map.put("colToAddress", CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_ORDER_ORDERERROR_COL_TOADDRESS));
        map.put("colToContacts", CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_ORDER_ORDERERROR_COL_TOCONTACTS));
        map.put("colToContactsTel", CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_ORDER_ORDERERROR_COL_TOCONTACTSTEL));
         LgOrderDetailDto vo = lgOrderExtDao.selectHideDetailByPk(map);
        data.put("mainData", vo);
        data.put("feedBackPic", lgDeliveryExceptionPicDao.selectPic(pk));
        data.put("trackDetail", lgTrackDetailExDao.selectTrackDetailBydeliveryPk(pk));
        return data;
    }

    @Override
    @Transactional
    public int savePushLgCompanyName(Map<String, Object> map) {
        int temp = lgOrderExtDao.savePushLgCompanyName(map) + lgOrderGoodsExtDao.updateOrderGoodsByDeliveryPk(map);
        /*
         * if (temp > 2) { LgOrderDetailDto vo = lgOrderExtDao.selectDetailByPk(String .valueOf(map.get("pk"))); if (vo != null && !vo.equals("")) {
         * if (vo.getOrderStatus() != 9 && vo.getOrderStatus() != 10) { // 给重新制定物流供应商的发送短信 SysSmsTemplateExtDto sysSmsTemplateDto =
         * sysService.getSmsByName("reavice_delivery"); Map<String, String> mapTemp = new HashMap<>(); mapTemp.put("orderNum", vo.getOrderPk());
         * List<LgMemberDto> memberList = lgMemberExtDao.getMemberByCompanyPk(vo.getLogisticsCompanyPk()); if (memberList != null && memberList.size()
         * > 0) { SmsLgCompanyInfo smsLgCompanyInfo = new SmsLgCompanyInfo(); smsLgCompanyInfo.setCompanyName(vo .getLogisticsCompanyName());
         * smsLgCompanyInfo.setCompanyPk(memberList.get(0) .getCompanyPk()); smsLgCompanyInfo.setMemberPk(memberList.get(0).getPk());
         * smsLgCompanyInfo.setMobile(memberList.get(0) .getMobile()); try{ SmsUtils.send(sysService.getContent(mapTemp, sysSmsTemplateDto),
         * smsLgCompanyInfo, mongoTemplate); }catch(Exception e){ e.printStackTrace(); } } } } }
         */
        return temp;
    }

    @Override
    @Transactional
    public int sureMoney(Map<String, Object> map) {
        int temp = lgOrderExtDao.sureMoney(map);
        if (temp > 0) {
            LgTrackDto lgTrackDto = new LgTrackDto();
            lgTrackDto.setPk(KeyUtils.getUUID());
            lgTrackDto.setDeliveryPk(String.valueOf(map.get("pk")));
            lgTrackDto.setOrderStatus(6);
            lgTrackDto.setStepDetail("财务已确认，待指派车辆");
            temp = lgTrackDetailExDao.insert(lgTrackDto);
            /*
             * // 给每个订单的Member发送短信，告诉物流供应商已经派送订单 LgOrderDetailDto vo = lgOrderExtDao.selectDetailByPk(String .valueOf(map.get("pk")));
             * SysSmsTemplateExtDto sysSmsTemplateDto = sysService.getSmsByName("reavice_delivery"); Map<String, String> mapTemp = new HashMap<>();
             * mapTemp.put("orderNum", vo.getOrderPk()); List<LgMemberDto> memberList = lgMemberExtDao.getMemberByCompanyPk(vo
             * .getLogisticsCompanyPk()); if (memberList != null && memberList.size() > 0) { SmsLgCompanyInfo smsLgCompanyInfo = new
             * SmsLgCompanyInfo(); smsLgCompanyInfo.setCompanyName(vo.getLogisticsCompanyName());
             * smsLgCompanyInfo.setCompanyPk(memberList.get(0).getCompanyPk()); smsLgCompanyInfo.setMemberPk(memberList.get(0).getPk());
             * smsLgCompanyInfo.setMobile(memberList.get(0).getMobile()); try{ SmsUtils.send( sysService.getContent(mapTemp, sysSmsTemplateDto),
             * smsLgCompanyInfo, mongoTemplate); }catch(Exception e){ } }
             */

        }
        return temp;
    }

    @Override
    @Transactional
    public int submitFeedBack(String pk, String imgs, String abnormalRemark) {
        int temp = lgOrderExtDao.submitFeedBack(pk, abnormalRemark);
        if (temp > 0) {
            if (imgs != null && !imgs.equals("")) {
                String[] img = imgs.split(",");
                if (img.length > 0) {
                    for (int i = 0; i < img.length; i++) {
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("pk", KeyUtils.getUUID());
                        map.put("deliveryPk", pk);
                        map.put("exceptionPicUrl", img[i]);
                        if (lgDeliveryExceptionPicDao.insert(map) < 0) {
                            temp = -1;
                        }
                    }
                }
            }
        }
        return temp;
    }

    @Override
    public LogisticsOrderAddress selectAddress(String pk) {
        return lgOrderExtDao.selectAddress(pk);
    }

    @Override
    public int saveOrder(LogisticsOrderAddress logisticsOrderAddress) {
        return lgOrderExtDao.saveOrder(logisticsOrderAddress);
    }

    @Override
    public int sureFeedback(String pk) {
        return lgOrderExtDao.sureFeedback(pk);
    }

    @Override
    public DeliveryForm exportDeliveryForm(String pk) {
        return lgOrderExtDao.exportDeliveryForm(pk);
    }

    @Override
    @Transactional
    public Integer selectByStatus() {
        int vert = 1;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("isMatched", 0);
        map.put("isAbnormal", 1);
        List<SearchLgLine> orNumList = lgOrderExtDao.selectByStatus(map);
        if (orNumList.size() > 0) {
            for (SearchLgLine list : orNumList) {
                map = new HashMap<String, Object>();
                map.put("fromProvicePk", list.getFromProvincePk());
                map.put("fromCityPk", list.getFromCityPk());
                map.put("fromAreaPk", list.getFromAreaPk());
                map.put("fromTownPk", list.getFromTownPk());
                map.put("toProvicePk", list.getToProvincePk());
                map.put("toCityPk", list.getToCityPk());
                map.put("toAreaPk", list.getToAreaPk());
                map.put("toTownPk", list.getToTownPk());
                map.put("productPk", list.getProductPk());
                map.put("gradePk", list.getGradePk());
                map.put("status", 1);
                map.put("isDelete", 1);
                map.put("companyIsDelete", 1);// 物流公司启用
                map.put("companyIsVisable", 1);
                map.put("companyAuditStatus", 1);
                // 自动匹配线路
                vert = updateLgCompany(map, list);
            }
        }
        return vert;
    }

    private int updateLgCompany(Map<String, Object> map, SearchLgLine searchLgLine) {
        int vert = 1;
        // 查找匹配线路
        List<LogisticsRouteDto> list = searchMatchLine(map, searchLgLine);
        // 跟新配送物流
        vert = updatePushLgCompany(list, searchLgLine);
        return vert;
    }

    /**
     * 查询线路
     */
    private List<LogisticsRouteDto> searchMatchLine(Map<String, Object> map, SearchLgLine searchLgLine) {
        List<LogisticsRouteDto> list = new ArrayList<LogisticsRouteDto>();
        list = queryMongo(map);
        if (null == list || list.size() == 0) {
            map.put("toTownPk", "");
            list = queryMongo(map);
            if (null == list || list.size() == 0) {
                map.put("toAreaPk", "");
                list = queryMongo(map);
                if (null == list || list.size() == 0) {
                    map.put("toAreaPk", searchLgLine.getToAreaPk() == null ? "" : searchLgLine.getToAreaPk());
                    map.put("toTownPk", searchLgLine.getToTownPk() == null ? "" : searchLgLine.getToTownPk());
                    map.put("gradePk", "");
                    list = queryMongo(map);
                    if (null == list || list.size() == 0) {
                        map.put("toTownPk", "");
                        list = queryMongo(map);
                        if (null == list || list.size() == 0) {
                            map.put("toAreaPk", "");
                            list = queryMongo(map);
                            if (null == list || list.size() == 0) {
                                map.put("gradePk", searchLgLine.getGradePk() == null ? "" : searchLgLine.getGradePk());
                                map.put("toAreaPk", searchLgLine.getToAreaPk() == null ? "" : searchLgLine.getToAreaPk());
                                map.put("toTownPk", searchLgLine.getToTownPk() == null ? "" : searchLgLine.getToTownPk());
                                map.put("productPk", "");
                                list = queryMongo(map);
                                if (null == list || list.size() == 0) {
                                    map.put("toTownPk", "");
                                    list = queryMongo(map);
                                    if (null == list || list.size() == 0) {
                                        map.put("toAreaPk", "");
                                        list = queryMongo(map);
                                        if (null == list || list.size() == 0) {
                                            map.put("toAreaPk", searchLgLine.getToAreaPk() == null ? "" : searchLgLine.getToAreaPk());
                                            map.put("toTownPk", searchLgLine.getToTownPk() == null ? "" : searchLgLine.getToTownPk());
                                            map.put("gradePk", "");
                                            list = queryMongo(map);
                                            if (null == list || list.size() == 0) {
                                                map.put("toTownPk", "");
                                                list = queryMongo(map);
                                                if (null == list || list.size() == 0) {
                                                    map.put("toAreaPk", "");
                                                    list = queryMongo(map);
                                                    if (null == list || list.size() == 0) {
                                                        map.put("toAreaPk", searchLgLine.getToAreaPk() == null ? "" : searchLgLine.getToAreaPk());
                                                        map.put("toTownPk", searchLgLine.getToTownPk() == null ? "" : searchLgLine.getToTownPk());
                                                        map.put("productPk", searchLgLine.getProductPk() == null ? "" : searchLgLine.getProductPk());
                                                        map.put("gradePk", searchLgLine.getGradePk() == null ? "" : searchLgLine.getGradePk());
                                                        map.put("fromTownPk", "");
                                                        list = queryMongo(map);
                                                        if (null == list || list.size() == 0) {
                                                            map.put("fromAreaPk", "");
                                                            list = queryMongo(map);
                                                            if (null == list || list.size() == 0) {
                                                                map.put(
                                                                        "fromAreaPk",
                                                                        searchLgLine.getFromAreaPk() == null ? "" : searchLgLine.getFromAreaPk());
                                                                map.put(
                                                                        "fromTownPk",
                                                                        searchLgLine.getFromTownPk() == null ? "" : searchLgLine.getFromTownPk());
                                                                map.put("gradePk", "");
                                                                list = queryMongo(map);
                                                                if (null == list || list.size() == 0) {
                                                                    map.put("fromTownPk", "");
                                                                    list = queryMongo(map);
                                                                    if (null == list || list.size() == 0) {
                                                                        map.put("fromAreaPk", "");
                                                                        list = queryMongo(map);
                                                                        if (null == list || list.size() == 0) {
                                                                            map.put(
                                                                                    "fromAreaPk",
                                                                                    searchLgLine.getFromAreaPk() == null ? "" : searchLgLine
                                                                                            .getFromAreaPk());
                                                                            map.put(
                                                                                    "fromTownPk",
                                                                                    searchLgLine.getFromTownPk() == null ? "" : searchLgLine
                                                                                            .getFromTownPk());
                                                                            map.put(
                                                                                    "gradePk",
                                                                                    searchLgLine.getGradePk() == null ? ""
                                                                                                                      : searchLgLine.getGradePk());
                                                                            map.put("productPk", "");
                                                                            list = queryMongo(map);
                                                                            if (null == list || list.size() == 0) {
                                                                                map.put("fromTownPk", "");
                                                                                list = queryMongo(map);
                                                                                if (null == list || list.size() == 0) {
                                                                                    map.put("fromAreaPk", "");
                                                                                    list = queryMongo(map);
                                                                                    if (null == list || list.size() == 0) {
                                                                                        map.put(
                                                                                                "fromAreaPk",
                                                                                                searchLgLine
                                                                                                        .getFromAreaPk() == null ? "" : searchLgLine
                                                                                                                .getFromAreaPk());
                                                                                        map.put(
                                                                                                "fromTownPk",
                                                                                                searchLgLine
                                                                                                        .getFromTownPk() == null ? "" : searchLgLine
                                                                                                                .getFromTownPk());
                                                                                        map.put("gradePk", "");
                                                                                        list = queryMongo(map);
                                                                                        if (null == list || list.size() == 0) {
                                                                                            map.put("fromTownPk", "");
                                                                                            list = queryMongo(map);
                                                                                            if (null == list || list.size() == 0) {
                                                                                                map.put("fromAreaPk", "");
                                                                                                list = queryMongo(map);
                                                                                            }

                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        if (null != list && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getLeastWeight() > 0 && searchLgLine.getWeight() <= list.get(i).getLeastWeight()) {
                    List<LogisticsLinePriceDto> dtoList = new ArrayList<LogisticsLinePriceDto>();
                    LogisticsLinePriceDto dto = new LogisticsLinePriceDto();
                    dto.setFromWeight(0.0);
                    dto.setToWeight(searchLgLine.getWeight());
                    dto.setBasisPrice(list.get(i).getBasicPrice()); // 结算价
                    dto.setSalePrice(list.get(i).getFreight()); // 对外价
                    dtoList.add(dto);
                    list.get(i).setList(dtoList);
                } else {
                    Query query = new Query();
                    query.addCriteria(Criteria.where("linePk").is(list.get(i).getPk()));
                    query.addCriteria(Criteria.where("fromWeight").lte(searchLgLine.getWeight()));
                    query.addCriteria(Criteria.where("toWeight").gt(searchLgLine.getWeight()));
                    Criteria stepInfo = new Criteria();
                    stepInfo.andOperator(
                            Criteria.where("linePk").is(list.get(i).getPk()),
                            Criteria.where("fromWeight").lte(searchLgLine.getWeight()),
                            Criteria.where("toWeight").gt(searchLgLine.getWeight()));
                    Query query1 = new Query(stepInfo);
                    List<LogisticsLinePriceDto> logisticsPrice = mongoTemplate.find(query1, LogisticsLinePriceDto.class);
                    if (null != logisticsPrice && logisticsPrice.size() > 0) {
                        Double totalPrice = ArithUtil.mul(logisticsPrice.get(0).getSalePrice(), searchLgLine.getWeight());// 符合阶梯规则的运费总价
                        // 当订单的最低起运重量为空，订单运费金额<最低起运价，以最低其运价为标准
                        if (list.get(i).getLeastWeight() == 0 && totalPrice < list.get(i).getFreight()) {
                            List<LogisticsLinePriceDto> dtoList = new ArrayList<LogisticsLinePriceDto>();
                            LogisticsLinePriceDto dto = new LogisticsLinePriceDto();
                            dto.setFromWeight(0.0);
                            dto.setToWeight(searchLgLine.getWeight());
                            dto.setBasisPrice(list.get(i).getBasicPrice()); // 结算价
                            dto.setSalePrice(list.get(i).getFreight()); // 对外价
                            dtoList.add(dto);
                            list.get(i).setList(dtoList);
                        } else {
                            list.get(i).setList(logisticsPrice);
                        }
                    } else {
                        list.remove(i);
                    }
                }
            }
        }
        return list;
    }

    /**
     * 查询mongo数据库
     *
     * @param map
     * @return
     */
    private List<LogisticsRouteDto> queryMongo(Map<String, Object> map) {
        Query query = new Query();
        for (String key : map.keySet()) {
            query.addCriteria(Criteria.where(key).is(map.get(key)));
        }
        List<LogisticsRouteDto> logisticsLine = mongoTemplate.find(query, LogisticsRouteDto.class);
        return logisticsLine;
    }

    private Integer updatePushLgCompany(List<LogisticsRouteDto> list, SearchLgLine searchLgLine) {
        // vert:1 匹配成功；2：线路不存在 ；<1 :匹配失败
        int vert = 1;
        if (null != list && list.size() > 0) {
            Double basisLinePrice = list.get(0).getList().get(0).getBasisPrice();
            // Double weight = 0.0;
            Map<String, Object> lgmap = new HashMap<String, Object>();
            lgmap.put("logisticsCompanyPk", list.get(0).getCompanyPk());
            lgmap.put("logisticsCompanyName", list.get(0).getCompanyName());
            lgmap.put("basisLinePrice", basisLinePrice);
            lgmap.put("linePricePk", list.get(0).getList().get(0).getLinePk());
            lgmap.put("settleWeight", searchLgLine.getWeight());
            lgmap.put("presentTotalFreight", searchLgLine.getWeight() * list.get(0).getList().get(0).getSalePrice());
            lgmap.put("originalTotalFreight", searchLgLine.getWeight() * list.get(0).getList().get(0).getSalePrice());
            lgmap.put("isMatched", 1);
            lgmap.put("pk", searchLgLine.getPk());
            vert = savePushLgCompanyName(lgmap);// 更新物流公司，order_goods表单价

        } else {
            vert = 2;
        }
        return vert;
    }

    @Override
    public List<LogisticsRouteDto> matchLgCompanyRoute(String pk, String logisticsCompanyPk) {
        // 获取线路的查询条件
        SearchLgLine vo = lgOrderExtDao.matchAddress(pk);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("fromProvicePk", vo.getFromProvincePk() == null ? "" : vo.getFromProvincePk());
        map.put("fromCityPk", vo.getFromCityPk() == null ? "" : vo.getFromCityPk());
        map.put("fromAreaPk", vo.getFromAreaPk() == null ? "" : vo.getFromAreaPk());
        map.put("fromTownPk", vo.getFromTownPk() == null ? "" : vo.getFromTownPk());
        map.put("toProvicePk", vo.getToProvincePk() == null ? "" : vo.getToProvincePk());
        map.put("toCityPk", vo.getToCityPk() == null ? "" : vo.getToCityPk());
        map.put("toAreaPk", vo.getToAreaPk() == null ? "" : vo.getToAreaPk());
        map.put("toTownPk", vo.getToTownPk() == null ? "" : vo.getToTownPk());
        map.put("productPk", vo.getProductPk() == null ? "" : vo.getProductPk());
        map.put("gradePk", vo.getGradePk() == null ? "" : vo.getGradePk());
        map.put("companyPk", logisticsCompanyPk);
        map.put("status", 1);
        map.put("isDelete", 1);
        map.put("companyIsDelete", 1);// 物流公司启用
        map.put("companyIsVisable", 1);
        map.put("companyAuditStatus", 1);
        List<LogisticsRouteDto> list = searchMatchLine(map, vo);
        if (list.size() > 0) {
            for (LogisticsRouteDto logisticsRouteDto : list) {
                logisticsRouteDto.setWeight(vo.getWeight());
            }
        }
        return list;
    }

}
