package cn.cf.service.logistics.impl;


import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Comparator;
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
import cn.cf.common.RestCode;
import cn.cf.common.utils.CommonUtil;
import cn.cf.dao.B2bGradeDao;
import cn.cf.dao.B2bProductExtDao;
import cn.cf.dao.LgCompanyExtDao;
import cn.cf.dao.LogisticsLinePriceDao;
import cn.cf.dao.LogisticsRouteDao;
import cn.cf.dao.SysRegionsExtDao;
import cn.cf.dto.B2bGradeDto;
import cn.cf.dto.B2bProductDto;
import cn.cf.dto.LgCompanyDto;
import cn.cf.dto.LogisticsLinePriceDto;
import cn.cf.dto.LogisticsRouteDto;
import cn.cf.entity.LogisticsRouteExport;
import cn.cf.model.LgRegionsPk;
import cn.cf.model.LogisticsRouteGridModel;
import cn.cf.model.LogisticsRouteModel;
import cn.cf.model.ManageAccount;
import cn.cf.service.logistics.LogisticsRouteService;
import cn.cf.util.KeyUtils;
import jxl.Cell;
import jxl.Sheet;

@Service
public class LogisticsRouteServiceImpl implements LogisticsRouteService{
    @Autowired
    private LogisticsRouteDao logisticsRouteDao;

    @Autowired
    private LogisticsLinePriceDao logisticsLinePriceDao;

    @Autowired
    private LgCompanyExtDao lgCompanyDao;

    @Autowired
    private B2bProductExtDao b2bProductDaoEx;

    @Autowired
    private B2bGradeDao b2bGradeDao;

    @Autowired
    private SysRegionsExtDao sysRegionsDao;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public PageModel<LogisticsRouteGridModel> getLogisticsRoute(QueryModel<LogisticsRouteModel> qm,String accountPk) {
        PageModel<LogisticsRouteGridModel> pm = new PageModel<LogisticsRouteGridModel>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("start", qm.getStart());
        map.put("limit", qm.getLimit());
        map.put("orderName", qm.getFirstOrderName());
        map.put("orderType", qm.getFirstOrderType());
        map.put("companyPk", qm.getEntity().getCompanyPk());
        map.put("status", qm.getEntity().getStatus());
        map.put("fromUpdateTime", qm.getEntity().getFromUpdateTime());
        map.put("toUpdateTime", qm.getEntity().getToUpdateTime());
        map.put("fromAddress", qm.getEntity().getFromAddress());
        map.put("toAddress", qm.getEntity().getToAddress());
        map.put("isDelete", 1);
        map.put("colRouteName",  CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_ROUTE_COL_ROUTENAME));
        map.put("colLogisticsComName",  CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_ROUTE_COL_LOGISTICSCOMNAME));
        map.put("colToAddress",  CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_ROUTE_COL_TOADDRESS));
        map.put("colFromAddress",  CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_ROUTE_COL_FROMADDRESS));
        int totalCount = logisticsRouteDao.countLogisticsRoute(map);
        List<LogisticsRouteGridModel> list = logisticsRouteDao.getLogisticsRoute(map);
        pm.setTotalCount(totalCount);
        pm.setDataList(list);
        return pm;
    }

    @Override
    public int delLogisticsRoute(String pk) {
        // mongo更新数据
        Query query = Query.query(Criteria.where("pk").is(pk));
        Update update = Update.update("isDelete", 2);
        mongoTemplate.updateMulti(query, update, LogisticsRouteDto.class);
        return logisticsRouteDao.delete(pk);
    }

    @Override
    public LogisticsRouteDto selectOne(String pk) {
        LogisticsRouteDto logisticsRouteDto = logisticsRouteDao.selectOne(pk);
        List<LogisticsLinePriceDto> list = logisticsRouteDao.selectPriceList(pk);
        if (list != null && list.size() > 0) {
            logisticsRouteDto.setList(list);
        }
        logisticsRouteDto.setList(list);
        return logisticsRouteDto;
    }

    @Override
    @Transactional
    public int saveLogisticsRoute(LogisticsRouteDto dto, String showstartweights, String showendweights,
                                  String showlogisticsprices, String showinlogisticsprices, String pricePks) {
        // -2:模板名重复
        int retVal = 0;
        String[] showstartweight = showstartweights.split(",");
        String[] showendweight = showendweights.split(",");
        String[] showlogisticsprice = showlogisticsprices.split(",");
        String[] showinlogisticsprice = showinlogisticsprices.split(",");
        String[] pricePk = pricePks.split(",");
        retVal = 0;
        LogisticsRouteDto logisticsRouteDto = logisticsRouteDao.getByName(dto.getName());
        dto.setInsertTime(new Date());
        dto.setUpdateTime(new Date());
        dto.setIsDelete(1);
        if (logisticsRouteDto != null) {
            if (dto.getPk() != null && !"".equals(dto.getPk())) {
                retVal = logisticsRouteDao.update(dto);
                if (retVal > 0) {// 更新线路价格
                    saveLogisticsLinePrice(retVal, dto, showstartweight, showendweight, showlogisticsprice,
                            showinlogisticsprice, pricePk);
                }
            } else {
                return -2;
            }
        } else {
            if (null == dto.getPk() || "".equals(dto.getPk())) {
                // 新增
                dto.setPk(KeyUtils.getUUID());
                retVal = logisticsRouteDao.insert(dto);
                if (retVal > 0) {// 新增线路价格
                    saveLogisticsLinePrice(retVal, dto, showstartweight, showendweight, showlogisticsprice,
                            showinlogisticsprice, pricePk);
                }
            } else {
                retVal = logisticsRouteDao.update(dto);
                if (retVal > 0) {// 更新线路价格
                    saveLogisticsLinePrice(retVal, dto, showstartweight, showendweight, showlogisticsprice,
                            showinlogisticsprice, pricePk);
                }
            }
        }
        saveLogisticsLineToMonogo(dto.getPk());

        return retVal;
    }

    private void saveLogisticsLinePrice(Integer retVal, LogisticsRouteDto dto, String[] showstartweight,
                                        String[] showendweight, String[] showlogisticsprice, String[] showinlogisticsprice, String[] pricePk) {
        if (pricePk.length > 0) {
            retVal = logisticsLinePriceDao.delete(dto.getPk());
        }
        for (int i = 0; i < showstartweight.length; i++) {
            LogisticsLinePriceDto logisticsLinePriceDto = new LogisticsLinePriceDto();
            if (showstartweight.length > i) {
                logisticsLinePriceDto.setFromWeight(Double.valueOf(showstartweight[i]));
            } else {
                logisticsLinePriceDto.setFromWeight(0.0);
            }
            if (showendweight.length > i) {
                logisticsLinePriceDto.setToWeight(Double.valueOf(showendweight[i]));
            } else {
                logisticsLinePriceDto.setToWeight(0.0);
            }
            if (showlogisticsprice.length > i) {
                logisticsLinePriceDto.setSalePrice(Double.valueOf(showlogisticsprice[i]));
            } else {
                logisticsLinePriceDto.setSalePrice(0.0);
            }
            if (showinlogisticsprice.length > i) {
                logisticsLinePriceDto.setBasisPrice(Double.valueOf(showinlogisticsprice[i]));
            } else {
                logisticsLinePriceDto.setBasisPrice(0.0);
            }
            logisticsLinePriceDto.setPk(KeyUtils.getUUID());
            logisticsLinePriceDto.setLinePk(dto.getPk());
            logisticsLinePriceDto.setInsertTime(new Date());
            logisticsLinePriceDto.setUpdateTime(new Date());
            logisticsLinePriceDto.setIsDelete(1);
            retVal = logisticsLinePriceDao.insert(logisticsLinePriceDto);
        }
    }

    // mongo插入数据
    private RestCode saveLogisticsLineToMonogo(String logisticsLinePk) {
        Query qu = new Query(Criteria.where("pk").is(logisticsLinePk));
        List<LogisticsRouteDto> list = mongoTemplate.find(qu, LogisticsRouteDto.class);
        if (list != null) {// 如果线路已存在
            mongoTemplate.remove(qu, LogisticsRouteDto.class);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pk", logisticsLinePk);
        List<LogisticsRouteDto> logisticsRouteList = logisticsRouteDao.searchListByPk(map);// 线路同步
        for (LogisticsRouteDto l : logisticsRouteList) {
            insertMongoRoute(l);
            Query quste = new Query(Criteria.where("linePk").is(l.getPk()));// 价格同步
            List<LogisticsLinePriceDto> linePriceList = mongoTemplate.find(quste, LogisticsLinePriceDto.class);
            if (linePriceList.size() > 0) {
                mongoTemplate.remove(quste, LogisticsLinePriceDto.class);
            }
            Map<String, Object> mapPrice = new HashMap<String, Object>();
            mapPrice.put("linePk", l.getPk());
            mapPrice.put("isDelete", 1);

            List<LogisticsLinePriceDto> steps = logisticsLinePriceDao.searchList(mapPrice);
            for (LogisticsLinePriceDto lste : steps) {
                insertMongoPrice(lste);
            }
        }
        return RestCode.CODE_0000;
    }

    private void insertMongoRoute(LogisticsRouteDto l) {
        if (l.getCompanyPk() == null) {
            l.setCompanyPk("");
            l.setCompanyName("");
        }
        if (l.getProductPk() == null) {
            l.setProductName("");
            l.setProductPk("");
        }
        if (l.getGradePk() == null) {
            l.setGradeName("");
            l.setGradePk("");
        }
        if (l.getFromProvicePk() == null) {
            l.setFromProviceName("");
            l.setFromProvicePk("");
        }
        if (l.getFromCityName() == null) {
            l.setFromCityName("");
            l.setFromCityPk("");
        }
        if (l.getFromAreaPk() == null) {
            l.setFromAreaPk("");
            l.setFromAreaName("");
        }
        if (l.getFromTownPk() == null) {
            l.setFromTownName("");
            l.setFromTownPk("");
        }
        if (l.getToProvicePk() == null) {
            l.setToProviceName("");
            l.setToProvicePk("");
        }
        if (l.getToCityName() == null) {
            l.setToCityName("");
            l.setToCityPk("");
        }
        if (l.getToAreaPk() == null) {
            l.setToAreaPk("");
            l.setToAreaName("");
        }
        if (l.getToTownPk() == null) {
            l.setToTownName("");
            l.setToTownPk("");
        }

        if (l.getLeastWeight() == null) {
            l.setLeastWeight(0.0);
        }
        if (l.getFreight() == null) {
            l.setFreight(0.0);
        }
        if (l.getBasicPrice() == null) {
            l.setBasicPrice(0.0);
        }
        mongoTemplate.insert(l);

    }

    private void insertMongoPrice(LogisticsLinePriceDto lste) {
        if (lste.getFromWeight() == null) {
            lste.setFromWeight(0.0);
        }
        if (lste.getToWeight() == null) {
            lste.setToWeight(0.0);
        }
        if (lste.getBasisPrice() == null) {
            lste.setBasisPrice(0.0);
        }
        if (lste.getSalePrice() == null) {
            lste.setSalePrice(0.0);
        }
        mongoTemplate.insert(lste);

    }

    @Override
    public PageModel<LogisticsRouteExport> searchLogisticsRouteList(QueryModel<LogisticsRouteModel> qm,ManageAccount account) {
        PageModel<LogisticsRouteExport> pm = new PageModel<LogisticsRouteExport>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("companyPk", qm.getEntity().getCompanyPk());
        map.put("status", qm.getEntity().getStatus());
        map.put("fromUpdateTime", qm.getEntity().getFromUpdateTime());
        map.put("toUpdateTime", qm.getEntity().getToUpdateTime());
        map.put("fromAddress", qm.getEntity().getFromAddress());
        map.put("toAddress", qm.getEntity().getToAddress());
        map.put("isDelete", 1);
        
        if(account != null){
            if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.LG_ROUTE_COL_LOGISTICSCOMNAME)){
                map.put("colLogisticsName",ColAuthConstants.LG_ROUTE_COL_LOGISTICSCOMNAME);
            }
            if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.LG_ROUTE_COL_ROUTENAME)){
                map.put("colName",ColAuthConstants.LG_ROUTE_COL_ROUTENAME);
            }
            if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.LG_ROUTE_COL_TOADDRESS)){
                map.put("colToAddress",ColAuthConstants.LG_ROUTE_COL_TOADDRESS);
            }
            if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.LG_ROUTE_COL_FROMADDRESS)){
                map.put("colFromAddress",ColAuthConstants.LG_ROUTE_COL_FROMADDRESS);
            }
        }
        List<LogisticsRouteExport> list = logisticsRouteDao.searchLogisticsRoute(map);
        List<LogisticsRouteExport> listLinePrice = logisticsRouteDao.searchLogisticsPrice(map);
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                listLinePrice.add(list.get(i));
            }
        }
        // 对list进行排序updateTime
        sortList(listLinePrice, qm.getFirstOrderType());
        pm.setTotalCount(listLinePrice.size());
        pm.setDataList(listLinePrice);
        return pm;
    }

    private void sortList(List<LogisticsRouteExport> listLinePrice, final String orderType) {
        Collections.sort(listLinePrice, new Comparator<LogisticsRouteExport>() {
            @Override
            public int compare(LogisticsRouteExport arg0, LogisticsRouteExport arg1) {
                String a = arg0.getUpdateTime();
                String b = arg1.getUpdateTime();
                if (orderType.equals("desc")) {
                    return b.compareTo(a);
                } else {
                    return a.compareTo(b);
                }
            }
        });
    }

    @Transactional
    @Override
    public int saveImportRoute(Sheet sheet) throws UnsupportedEncodingException {
        Date date = new Date();
        int retVal = 0;
        try {
            // 内部价
            for (int i = 2; i < sheet.getRows(); i++) {// sheet.getRows():获得表格文件行数
                Cell cell = sheet.getCell(12, i);
                // 内部价
                if (Double.parseDouble(cell.getContents().toString()) == 0.0) {
                    LogisticsRouteDto routeDto = new LogisticsRouteDto();

                    // 线路名不能重复
                    String routeName = sheet.getCell(1, i).getContents().toString();
                    if (routeName == null || routeName.equals("")) {
                        break;
                    }
                    LogisticsRouteDto logisticsRouteDto = logisticsRouteDao.getByName(routeName);
                    if (logisticsRouteDto != null) {
                        break;
                    }
                    routeDto.setName(routeName);

                    // 物流供应商存在
                    String company = sheet.getCell(0, i).getContents().toString();
                    if (company != null && !company.equals("")) {
                        LgCompanyDto companyDto = lgCompanyDao.searchPkByName(company);
                        if (companyDto != null) {
                            routeDto.setCompanyPk(companyDto.getPk());
                            routeDto.setCompanyName(companyDto.getName());
                            routeDto.setCompanyIsDelete(companyDto.getIsDelete());
                            routeDto.setCompanyIsVisable(companyDto.getIsVisable());
                            routeDto.setCompanyAuditStatus(companyDto.getAuditStatus());
                        } else {
                            break;
                        }
                    } else {
                        break;
                    }

                    // 品名
                    String product = sheet.getCell(10, i).getContents().toString();
                    if (product != null && !product.equals("")) {
                        B2bProductDto proDto = b2bProductDaoEx.getByNameEx(product);
                        
                        if (proDto != null) {
                            routeDto.setProductName(product);
                            routeDto.setProductPk(proDto.getPk());
                        } else {
                            break;
                        }
                    }

                    // 等级
                    String grade = sheet.getCell(11, i).getContents().toString();
                    if (grade != null && !grade.equals("")) {
                        B2bGradeDto gradeDto = b2bGradeDao.getByName(grade);
                        if (gradeDto != null) {
                            routeDto.setGradeName(grade);
                            routeDto.setGradePk(gradeDto.getPk());
                        } else {
                            break;
                        }
                    }

                    // 地址
                    String fromProviceName = sheet.getCell(2, i).getContents().toString();
                    String fromCityName = sheet.getCell(3, i).getContents().toString();
                    String fromAreaName = sheet.getCell(4, i).getContents().toString();
                    String fromTownName = sheet.getCell(5, i).getContents().toString();
                    LgRegionsPk address = findRegionsPk(fromProviceName, fromCityName, fromAreaName, fromTownName);
                    routeDto.setFromProviceName(fromProviceName);
                    routeDto.setFromProvicePk(address.getProvincePk());
                    routeDto.setFromCityName(fromCityName);
                    routeDto.setFromCityPk(address.getCityPk());
                    routeDto.setFromAreaName(fromAreaName);
                    routeDto.setFromAreaPk(address.getAreaPk());
                    routeDto.setFromTownName(fromTownName);
                    routeDto.setFromTownPk(address.getTownPk());

                    String toProviceName = sheet.getCell(6, i).getContents().toString();
                    String toCityName = sheet.getCell(7, i).getContents().toString();
                    String toAreaName = sheet.getCell(8, i).getContents().toString();
                    String toTownName = sheet.getCell(9, i).getContents().toString();
                    LgRegionsPk toAddress = findRegionsPk(toProviceName, toCityName, toAreaName, toTownName);
                    routeDto.setToProviceName(toProviceName);
                    routeDto.setToProvicePk(toAddress.getProvincePk());
                    routeDto.setToCityName(toCityName);
                    routeDto.setToCityPk(toAddress.getCityPk());
                    routeDto.setToAreaName(toAreaName);
                    routeDto.setToAreaPk(toAddress.getAreaPk());
                    routeDto.setToTownName(toTownName);
                    routeDto.setToTownPk(toAddress.getTownPk());

                    routeDto.setLeastWeight(Double.parseDouble(sheet.getCell(13, i).getContents().toString()));
                    routeDto.setFreight(Double.parseDouble(sheet.getCell(14, i).getContents().toString()));
                    routeDto.setBasicPrice(Double.parseDouble(sheet.getCell(15, i).getContents().toString()));
                    routeDto.setIsDelete(1);
                    routeDto.setStatus(1);
                    routeDto.setInsertTime(date);
                    routeDto.setUpdateTime(date);
                    routeDto.setPk(KeyUtils.getUUID());
                    logisticsRouteDao.insert(routeDto);
                    insertMongoRoute(routeDto);

                }
            }
            // 外部价
            for (int j =2; j < sheet.getRows(); j++) {
                if (Double.parseDouble(sheet.getCell(12, j).getContents().toString()) > 0) {
                    LogisticsLinePriceDto priceDto = new LogisticsLinePriceDto();
                    String routeName = sheet.getCell(1, j).getContents().toString();
                    if (routeName == null || routeName.equals("")) {
                        break;
                    } else {
                        LogisticsRouteDto logisticsRouteDto = logisticsRouteDao.getByName(routeName);
                        if (logisticsRouteDto != null) {
                            priceDto.setLinePk(logisticsRouteDto.getPk());
                        } else {
                            break;
                        }
                        priceDto.setFromWeight(Double.parseDouble(sheet.getCell(12, j).getContents().toString()));
                        priceDto.setToWeight(Double.parseDouble(sheet.getCell(13, j).getContents().toString()));
                        priceDto.setSalePrice(Double.parseDouble(sheet.getCell(14, j).getContents().toString()));
                        priceDto.setBasisPrice(Double.parseDouble(sheet.getCell(15, j).getContents().toString()));
                        priceDto.setIsDelete(1);
                        priceDto.setInsertTime(date);
                        priceDto.setUpdateTime(date);
                        priceDto.setPk(KeyUtils.getUUID());
                        logisticsLinePriceDao.insert(priceDto);
                        insertMongoPrice(priceDto);
                    }
                }

            }
            return  retVal;
        } catch (Exception e) {
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            retVal = -1;
            return  retVal;
        }



    }

    private LgRegionsPk findRegionsPk(String fromProvinceName, String fromCityName, String fromAreaName,
                                      String fromTownName) throws UnsupportedEncodingException {
        String pk = "";
        LgRegionsPk lgRegionsName = new LgRegionsPk();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("isDelete", 1);
        map.put("isVisable", 1);
        // 去除最后一个字符
        if (fromProvinceName != null && !fromProvinceName.equals("")) {
            map.put("name", cancelLastVal(fromProvinceName));
            map.put("parentPk", -1);
            pk = sysRegionsDao.selectRegionsNameByName(map);
            lgRegionsName.setProvincePk(pk);
            if (fromCityName != null && !fromCityName.equals("") && pk != null && !pk.equals("")) {
                map.put("name", cancelLastVal(fromCityName));
                map.put("parentPk", pk);
                pk = sysRegionsDao.selectRegionsNameByName(map);
                lgRegionsName.setCityPk(pk);
                if (pk != null && !pk.equals("") && fromAreaName != null && !fromAreaName.equals("")) {
                    map.put("name", cancelLastVal(fromAreaName));
                    map.put("parentPk", pk);
                    pk = sysRegionsDao.selectRegionsNameByName(map);
                    lgRegionsName.setAreaPk(pk);
                    if (pk != null && !pk.equals("") && fromTownName != null && !fromTownName.equals("")) {
                        map.put("name", cancelLastVal(fromTownName));
                        map.put("parentPk", pk);
                        pk = sysRegionsDao.selectRegionsNameByName(map);
                        lgRegionsName.setTownPk(pk);
                    } else {
                        return lgRegionsName;
                    }
                } else {
                    return lgRegionsName;
                }
            }
        }

        return lgRegionsName;
    }

    private String cancelLastVal(String name) throws UnsupportedEncodingException {
        if (name == null || name.equals("")) {
            return name;
        }
        String str = "省市区镇";
        String valtemp = name.substring(name.length() - 1, name.length());
        if (new String(str.getBytes(), "utf-8").indexOf(new String(valtemp.getBytes(), "utf-8")) != -1) {
            return name.substring(0, name.length() - 1);
        } else {
            return name;
        }
    }
}
