package cn.cf.service.logistics.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.cf.PageModel;
import cn.cf.common.ColAuthConstants;
import cn.cf.common.QueryModel;
import cn.cf.common.utils.CommonUtil;
import cn.cf.dao.LgCompanyExtDao;
import cn.cf.dao.LgMemberExtDao;
import cn.cf.dto.LgCompanyDto;
import cn.cf.dto.LgCompanyExtDto;
import cn.cf.dto.LogisticsRouteDto;
import cn.cf.model.LgCompanyEx;
import cn.cf.service.logistics.LogisticsService;
import cn.cf.util.KeyUtils;
import cn.cf.util.MD5Utils;

@Service
public class LogisticsServiceImpl implements LogisticsService {

    @Autowired
    private LgCompanyExtDao lgCompanyExtDao;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private LgMemberExtDao LgMemberExtDao;

    @Override
    public PageModel<LgCompanyExtDto> searchCompanyList(QueryModel<LgCompanyExtDto> qm,String accountPk) {

        PageModel<LgCompanyExtDto> pm = new PageModel<LgCompanyExtDto>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("start", qm.getStart());
        map.put("limit", qm.getLimit());
        map.put("orderName", qm.getFirstOrderName());
        map.put("orderType", qm.getFirstOrderType());
        map.put("name", qm.getEntity().getName());
        map.put("isVisable", qm.getEntity().getIsVisable());
        map.put("isDelete", qm.getEntity().getIsDelete());
        map.put("mobile", qm.getEntity().getMobile());
        map.put("updateStartTime", qm.getEntity().getUpdateStartTime());
        map.put("updateEndTime", qm.getEntity().getUpdateEndTime());
        map.put("colCompanyName", CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_SUPPLIER_COL_COM_NAME));
        map.put("colContacts", CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_SUPPLIER_COL_CONTACTS));
        map.put("colContactsTel", CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_SUPPLIER_COL_CONTACTSTEL));
        map.put("colBlurl", CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_SUPPLIER_COL_BLURL));
        int totalCount = lgCompanyExtDao.searchGridCount(map);
        List<LgCompanyExtDto> list = lgCompanyExtDao.searchGrid(map);
        pm.setTotalCount(totalCount);
        pm.setDataList(list);
        return pm;
    }

    @Override
    @Transactional
    public Integer LgCompany(LgCompanyEx company) {
        int result = 0;
        company.setUpdateTime(new Date());
        if (company.getPk() != null && !"".equals(company.getPk())) {
            result = lgCompanyExtDao.updateEx(company) + lgCompanyExtDao.updateContacts(company);
        } else {
            company.setPk(KeyUtils.getUUID());
            company.setMemberPk(KeyUtils.getUUID());
            company.setPassword(MD5Utils.MD5Encode(company.getMobile(), "utf-8"));
            company.setIsDelete(1);
            company.setIsVisable(1);
            company.setInsertTime(new Date());
            company.setParantPk("-1");
            result = lgCompanyExtDao.insert(company) + lgCompanyExtDao.insertContacts(company);
        }
        return result;
    }

    @Override
    public int getLgCompanyByVaildCompany(Map<String, Object> map) {
        return lgCompanyExtDao.searchVaildName(map);
    }

    @Override
    public int getLgCompanyByVaildMobile(Map<String, Object> map) {
        return lgCompanyExtDao.searchVaildMobile(map);
    }

    @Override
    @Transactional
    public int editLogisticsCompanyState(Integer isVisable, String pk) {
        LgCompanyEx company = new LgCompanyEx();
        company.setPk(pk);
        company.setIsVisable(isVisable);
        int temp = lgCompanyExtDao.updateEx(company);
        if (temp > 0) {

            // 物流供应商被禁用（启用 ），更新物流供应商的前端登录账号禁用（启用）
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("companyPk", pk);
            map.put("isVisable", isVisable);
            LgMemberExtDao.updateByCompanyPk(map);

            // mongo更新数据
            Query query = Query.query(Criteria.where("companyPk").is(pk));
            Update update = Update.update("companyIsVisable", isVisable);
            mongoTemplate.updateMulti(query, update, LogisticsRouteDto.class);

        }
        return temp;
    }

    // 根据Pk查询物流承运商
    @Override
    public LgCompanyDto getLgCompanyByPk(String pk) {
        return lgCompanyExtDao.getLgCompanyByPk(pk);
    }

    @Override
    public List<LgCompanyDto> getLgCompanyList() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("isDelete", 1);
        map.put("isVisable", 1);
        return lgCompanyExtDao.searchList(map);
    }

    @Override
    public List<LgCompanyDto> getAllLgCompanyList() {
        Map<String, Object> map = new HashMap<String, Object>();
        return lgCompanyExtDao.searchList(map);
    }

    @Override
    public LgCompanyDto searchPkByName(String companyName) {
        return lgCompanyExtDao.searchPkByName(companyName);
    }

    // @Override
    // public RestCode updateLogisticsCarrier(B2bLogisticsCarrier b2bLogisticsCarrier) {
    // try {
    // Map<String, Object> map =new HashMap<String, Object>();
    // map.put("carrierPk", b2bLogisticsCarrier.getPk());
    // map.put("carrierName", b2bLogisticsCarrier.getCarrierName());
    // map.put("isDelete", b2bLogisticsCarrier.getIsDelete());
    // map.put("isVisable", b2bLogisticsCarrier.getIsVisable());
    // map.put("updateTime", new Date());
    // carrierExtDao.update(map);
    // } catch (Exception e) {
    // e.printStackTrace();
    // return RestCode.CODE_S999;
    // }
    // return RestCode.CODE_0000;
    //
    // }

}
