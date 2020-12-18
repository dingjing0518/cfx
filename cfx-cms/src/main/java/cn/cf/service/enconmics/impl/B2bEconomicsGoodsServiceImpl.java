package cn.cf.service.enconmics.impl;

import cn.cf.PageModel;
import cn.cf.common.Constants;
import cn.cf.common.QueryModel;
import cn.cf.common.utils.CommonUtil;
import cn.cf.common.utils.PingYinUtil;
import cn.cf.dao.*;
import cn.cf.dto.*;
import cn.cf.entity.B2bEconomicsImprovingdataEntity;
import cn.cf.entity.EconCustomerMongo;
import cn.cf.json.JsonUtils;
import cn.cf.model.B2bBillGoods;
import cn.cf.model.B2bCreditreportGoods;
import cn.cf.model.B2bOnlinepayGoods;
import cn.cf.service.enconmics.B2bEconomicsGoodsService;
import cn.cf.util.DateUtil;
import cn.cf.util.KeyUtils;
import com.alibaba.fastjson.JSONObject;
import jxl.read.biff.BiffException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class B2bEconomicsGoodsServiceImpl implements B2bEconomicsGoodsService {

    @Autowired
    private B2bEconomicsGoodsExDao economicsGoodsExDao;

    @Autowired
    private B2bOnlinepayGoodsExtDao b2bOnlinepayGoodsExtDao;

    @Autowired
    private B2bCreditreportGoodsExtDao b2bCreditreportGoodsExtDao;
    @Autowired
    private B2bBillGoodsExtDao b2bBillGoodsExtDao;

    @Autowired
    private B2bEconomicsCustomerGoodsExDao b2bEconomicsCustomerGoodsExDao;

    @Autowired
    private B2bEconomicsCustomerExtDao b2bEconomicsCustomerExtDao;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public PageModel<B2bEconomicsGoodsExDto> searchEcGoodsList(QueryModel<B2bEconomicsGoodsExDto> qm) {
        PageModel<B2bEconomicsGoodsExDto> pm = new PageModel<B2bEconomicsGoodsExDto>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("start", qm.getStart());
        map.put("limit", qm.getLimit());
        map.put("orderName", qm.getFirstOrderName());
        map.put("orderType", qm.getFirstOrderType());
        map.put("name", qm.getEntity().getName());
        map.put("upStartTime", qm.getEntity().getUpdateStartTime());
        map.put("upEndTime", qm.getEntity().getUpdateEndTime());
        map.put("expireStartTime", qm.getEntity().getExpireStartTime());
        map.put("expireEndTime", qm.getEntity().getExpireEndTime());
        map.put("status", qm.getEntity().getStatus());
        map.put("isDelete", Constants.ONE);
        int totalCount = economicsGoodsExDao.countEcGoodsGrid(map);
        List<B2bEconomicsGoodsExDto> list = economicsGoodsExDao.searchEcGoodsGrid(map);
        if (list.size()>0) {
        	for (B2bEconomicsGoodsExDto dto : list) {
				if (dto.getStoreInfo()!=null && !dto.getStoreInfo().equals("")) {
					List<B2bStoreDto> storeDtos = JSONObject.parseArray(dto.getStoreInfo(), B2bStoreDto.class);
					String storeNames="";
					for (B2bStoreDto storeDto : storeDtos) {
						storeNames = storeNames+storeDto.getName()+",";
					}
					dto.setStoreNames(storeNames.substring(0, storeNames.length()-1));
				}
			}
		}
        pm.setTotalCount(totalCount);
        pm.setDataList(list);
        return pm;
    }

    @Override
    public PageModel<B2bBillGoodsExtDto> searchBillGoodsList(QueryModel<B2bBillGoodsExtDto> qm) {
        PageModel<B2bBillGoodsExtDto> pm = new PageModel<>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("start", qm.getStart());
        map.put("limit", qm.getLimit());
        map.put("orderName", qm.getFirstOrderName());
        map.put("orderType", qm.getFirstOrderType());
        map.put("name", qm.getEntity().getName());
        map.put("shotName", qm.getEntity().getShotName());
        map.put("bankName", qm.getEntity().getBankName());
        map.put("type", qm.getEntity().getType());
        map.put("isDelete", Constants.ONE);
        int totalCount = b2bBillGoodsExtDao.searchGridExtCount(map);
        List<B2bBillGoodsExtDto> list = b2bBillGoodsExtDao.searchGridExt(map);
        pm.setTotalCount(totalCount);
        pm.setDataList(list);
        return pm;
    }

    @Override
    public String update(B2bEconomicsGoodsExDto economicsGoods) {
        int result = 0;
        String msg = Constants.RESULT_SUCCESS_MSG;
        if (economicsGoodsExDao.isRepeat(economicsGoods) == 0) {
            if (economicsGoods.getPk() == null || "".equals(economicsGoods.getPk())) {
                economicsGoods.setPk(KeyUtils.getUUID());
                economicsGoods.setStatus(Constants.ONE);
                result = economicsGoodsExDao.insertEx(economicsGoods);
            } else {
                result = economicsGoodsExDao.updateEx(economicsGoods);
            }
            if (result > 0) {
                msg = Constants.RESULT_SUCCESS_MSG;
            } else {
                msg = Constants.RESULT_FAIL_MSG;
            }
        } else {
            msg = Constants.ISEXIST_NAME_ECGOODS;
        }
        return msg;
    }

    @Override
    public B2bEconomicsGoodsDto getByPk(String pk) {
        return economicsGoodsExDao.getByPk(pk);
    }

    @Override
    public B2bEconomicsGoodsDto getByName(String name) {
        return economicsGoodsExDao.getByName(name);
    }

    @Override
    public List<B2bEconomicsGoodsDto> searchList() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("isDelete", Constants.ONE);
        map.put("status", Constants.ONE);
        return economicsGoodsExDao.searchList(map);
    }

    @Override
    public PageModel<B2bOnlinepayGoodsExtDto> searchOnlinePayGoodsList(QueryModel<B2bOnlinepayGoodsExtDto> qm) {
        PageModel<B2bOnlinepayGoodsExtDto> pm = new PageModel<>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("start", qm.getStart());
        map.put("limit", qm.getLimit());
        map.put("orderName", qm.getFirstOrderName());
        map.put("orderType", qm.getFirstOrderType());
        map.put("name", qm.getEntity().getName());
        map.put("shotName", qm.getEntity().getShotName());
        map.put("isDelete", 1);
        int totalCount = b2bOnlinepayGoodsExtDao.searchGridExtCount(map);
        List<B2bOnlinepayGoodsExtDto> list = b2bOnlinepayGoodsExtDao.searchGridExt(map);
        pm.setTotalCount(totalCount);
        pm.setDataList(list);
        return pm;
    }

    @Override
    public String updateOnlinePayGoods(B2bOnlinepayGoodsExtDto dto) {
        String msg = "";
        int result = 0;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", dto.getName());
        if (CommonUtil.isNotEmpty(dto.getBankPk())) {
            map.put("bankPk", dto.getBankPk());
        }
        List<B2bOnlinepayGoodsExtDto> isExist = b2bOnlinepayGoodsExtDao.searchGridExt(map);
        if (isExist != null && isExist.size() > 0) {
            B2bOnlinepayGoodsExtDto extDto = isExist.get(0);
            if (!extDto.getPk().equals(dto.getPk())) {
                msg = Constants.ONLINEPAYGOODS_MSG;
            } else {
                //判断当编辑的时候不修改就点击保存的情况
                msg = Constants.RESULT_SUCCESS_MSG;
            }

        } else {
            if (dto.getPk() == null || "".equals(dto.getPk())) {
                B2bOnlinepayGoods onlinepay = new B2bOnlinepayGoods();
                onlinepay.setPk(KeyUtils.getUUID());
                onlinepay.setIsVisable(Constants.ONE);
                onlinepay.setIsDelete(Constants.ONE);
                onlinepay.setShotName(PingYinUtil.getFirstSpell(dto.getName()));
                onlinepay.setName(dto.getName());
                onlinepay.setBankPk(dto.getBankPk());
                onlinepay.setBankName(dto.getBankName());
                result = b2bOnlinepayGoodsExtDao.insert(onlinepay);
            } else {
                result = b2bOnlinepayGoodsExtDao.updateObj(dto);
            }
            if (result > 0) {
                msg = Constants.RESULT_SUCCESS_MSG;
            }
        }
        return msg;
    }

    @Override
    public String updateStatusOnlinePayGoods(B2bOnlinepayGoodsExtDto dto) {
        String msg = "";
        int result = 0;
        if (dto.getPk() != null && !"".equals(dto.getPk())) {
            result = b2bOnlinepayGoodsExtDao.updateObj(dto);
        }
        if (result > 0) {
            msg = Constants.RESULT_SUCCESS_MSG;
        } else {
            msg = Constants.RESULT_FAIL_MSG;
        }
        return msg;
    }

    @Override
    public String insertBillGoods(B2bBillGoods goods) {
        String msg = "";
        int result = 0;
        Map<String, Object> map = new HashMap<>();
        map.put("name", goods.getName());
        List<B2bBillGoodsDto> list = b2bBillGoodsExtDao.searchList(map);
        if (list != null && list.size() > 0) {
            msg = Constants.BILLGOODS_MSG;
        } else {
            goods.setPk(KeyUtils.getUUID());
            goods.setIsDelete(Constants.ONE);
            goods.setIsVisable(Constants.ONE);
            goods.setShotName(PingYinUtil.getFirstSpell(goods.getName()));
            result = b2bBillGoodsExtDao.insert(goods);
            if (result > 0) {
                msg = Constants.RESULT_SUCCESS_MSG;
            } else {
                msg = Constants.RESULT_FAIL_MSG;
            }
        }
        return msg;
    }

    @Override
    public String updateBillGoods(B2bBillGoodsExtDto dto) {

        int result = 0;
        String msg = "";
        if (dto.getIsUpdateStatus() != null && dto.getIsUpdateStatus() == Constants.ONE) {
            //修改状态
            result = b2bBillGoodsExtDao.updateObj(dto);
        } else {
            //更新数据
            Map<String, Object> map = new HashMap<>();
            map.put("name", dto.getName());
            map.put("bankPk", dto.getBankPk());
            map.put("type", dto.getType());
            map.put("gateway", dto.getGateway());
            map.put("pk", dto.getPk());
            List<B2bBillGoodsDto> list = b2bBillGoodsExtDao.isExitGoods(map);
            if (list != null && list.size() > Constants.ZERO) {
                B2bBillGoodsDto goodsDto = list.get(Constants.ZERO);
                if (goodsDto.getPk().equals(dto.getPk())) {
                    return Constants.RESULT_SUCCESS_MSG;
                } else {
                    return Constants.BILLGOODS_MSG;
                }

            } else {
               // dto.setShotName(PingYinUtil.getFirstSpell(dto.getName()));
                result = b2bBillGoodsExtDao.updateObj(dto);
            }
        }

        if (result > 0) {
            msg = Constants.RESULT_SUCCESS_MSG;
        } else {
            msg = Constants.RESULT_FAIL_MSG;
        }
        return msg;
    }

    @Override
    public PageModel<B2bCreditreportGoodsExtDto> searchCreditReportGoodsList(QueryModel<B2bCreditreportGoodsExtDto> qm) {
        PageModel<B2bCreditreportGoodsExtDto> pm = new PageModel<>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("start", qm.getStart());
        map.put("limit", qm.getLimit());
        map.put("orderName", qm.getFirstOrderName());
        map.put("orderType", qm.getFirstOrderType());
        map.put("name", qm.getEntity().getName());
        map.put("shotName", qm.getEntity().getShotName());
        map.put("isDelete", 1);
        int totalCount = b2bCreditreportGoodsExtDao.searchGridExtCount(map);
        List<B2bCreditreportGoodsExtDto> list = b2bCreditreportGoodsExtDao.searchGridExt(map);
        pm.setTotalCount(totalCount);
        pm.setDataList(list);
        return pm;
    }

    @Override
    public String updateCreditReportGoods(B2bCreditreportGoodsExtDto dto) {
        String msg = "";
        int result = 0;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", dto.getName());
        List<B2bCreditreportGoodsExtDto> isExist = b2bCreditreportGoodsExtDao.searchGridExt(map);
        if (isExist != null && isExist.size() > 0) {
            B2bCreditreportGoodsExtDto extDto = isExist.get(0);
            if (!extDto.getPk().equals(dto.getPk())) {
                msg = Constants.CREDITREPORTGOODS_MSG;
            } else {
                //判断当编辑的时候不修改就点击保存的情况
                msg = Constants.RESULT_SUCCESS_MSG;
            }
        } else {
            if (dto.getPk() == null || "".equals(dto.getPk())) {
                B2bCreditreportGoods onlinepay = new B2bCreditreportGoods();
                onlinepay.setPk(KeyUtils.getUUID());
                onlinepay.setIsVisable(Constants.ONE);
                onlinepay.setIsDelete(Constants.ONE);
                onlinepay.setShotName(PingYinUtil.getFirstSpell(dto.getName()));
                onlinepay.setName(dto.getName());
                result = b2bCreditreportGoodsExtDao.insert(onlinepay);
            } else {
                result = b2bCreditreportGoodsExtDao.updateObj(dto);
            }
            if (result > 0) {
                msg = Constants.RESULT_SUCCESS_MSG;
            }
        }
        return msg;
    }

    @Override
    public String updateStatusCreditReportGoods(B2bCreditreportGoodsExtDto dto) {
        String msg = "";
        int result = 0;
        if (dto.getPk() != null && !"".equals(dto.getPk())) {
            result = b2bCreditreportGoodsExtDao.updateObj(dto);
        }
        if (result > 0) {
            msg = Constants.RESULT_SUCCESS_MSG;
        } else {
            msg = Constants.RESULT_FAIL_MSG;
        }
        return msg;
    }

    @Override
    public List<B2bCreditreportGoodsDto> searchCreditReportGoods() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("isVisable", 1);
        map.put("isDelete", 1);
        return b2bCreditreportGoodsExtDao.searchList(map);
    }

    @Override
    public B2bEconomicsCustomerDto getEconomicsCustomer(String pk) {

        return b2bEconomicsCustomerExtDao.getByPk(pk);
    }

    @Override
    public Double countPurchaserSalesAmount(String purchaserPk) {
        double amount = 0d;
        amount = economicsGoodsExDao.countPurchaserSalesAmount(purchaserPk);
        return CommonUtil.formatDoubleTwo(amount);
    }

    @Override
    public Double rawPurchaseIncrePer(String purchaserPk) {
        List<Double> list = economicsGoodsExDao.rawPurchaseIncrePerList(purchaserPk);
        Double percent = 0d;
        if (list != null && list.size() > 0) {
            //前年
            Double lasterYear = list.get(0);
            //去年
            Double lastYear = list.get(1);
            if (lastYear > 0) {
                percent = (lasterYear / lastYear) * 100;
            }
        }
        return percent;
    }

    @Override
    public Integer addUpOnlineSalesNumbers(String purchaserPk) {
        int counts = 0;
        Map<String, Object> map = new HashMap<>();
        map.put("purchaserPk", purchaserPk);
        for (int i = 1; i < 13; i++) {
            map.put("month", i);
            Integer count = economicsGoodsExDao.addUpOnlineSalesNumbers(map);
            if (count != null && count > 0) {
                counts++;
            }
            map.remove("month");
        }
        return counts;
    }

    @Override
    public Integer addUpEconSalesNumbers(String purchaserPk) {
        int counts = 0;
        Map<String, Object> map = new HashMap<>();
        map.put("purchaserPk", purchaserPk);
        for (int i = 1; i < 13; i++) {
            map.put("month", i);
            Integer count = economicsGoodsExDao.addUpEconSalesNumbers(map);
            if (count != null && count > 0) {
                counts++;
            }
            map.remove("month");
        }
        return counts;
    }

    @Override
    public Double countEconSalesAmount(String purchaserPk) {
        double amount = 0d;
        amount = (economicsGoodsExDao.countEconSalesAmount(purchaserPk)) / 10000;
        return amount;
    }

    @Override
    public int countEconGoodsIsOver(String purchaserPk) {
        return economicsGoodsExDao.countEconGoodsIsOver(purchaserPk);
    }

  @Override
public String updateperfectCustDatum(B2bEconomicsImprovingdataEntity dto) {

    int result = 0;
    B2bEconomicsCustomerDto customerDto = b2bEconomicsCustomerExtDao.getByPk(dto.getEconCustmerPk());
    if (customerDto != null){
        String ipproveDataInfo = "";
        B2bEconomicsCustomerExtDto customerExtDto = new B2bEconomicsCustomerExtDto();
        customerExtDto.setPk(dto.getEconCustmerPk());
        String date = DateUtil.formatDateAndTime(new Date());
        if (CommonUtil.isNotEmpty(customerDto.getImprovingdataInfo())){
            B2bEconomicsImprovingdataEntity improvingdataExtDto = JSONObject.parseObject(customerDto.getImprovingdataInfo(),B2bEconomicsImprovingdataEntity.class);
            dto.setInsertTime(improvingdataExtDto.getInsertTime());
            dto.setUpdateTime(date);
            setEntry(dto,improvingdataExtDto);
            ipproveDataInfo = JsonUtils.convertToString(dto);
        }else{
            dto.setUpdateTime(date);
            dto.setInsertTime(date);
            ipproveDataInfo = JsonUtils.convertToString(dto);
        }
        customerExtDto.setImprovingdataInfo(ipproveDataInfo);
        result = b2bEconomicsCustomerExtDao.updateEconomicsCustomer(customerExtDto);
    }

    String msg = "";
    if (result > 0) {
        msg = Constants.RESULT_SUCCESS_MSG;
    } else {
        msg = Constants.RESULT_FAIL_MSG;
    }
    return msg;
}

    private void setEntry(B2bEconomicsImprovingdataEntity model,B2bEconomicsImprovingdataEntity entry){

        model.setEstablishingTimeScore(entry.getEstablishingTimeScore());
        model.setTaxSalesScore(entry.getTaxSalesScore());
        model.setFinanAmountTaxSalesScore(entry.getFinanAmountTaxSalesScore());
        model.setEcomCooperateTimeScore(entry.getEcomCooperateTimeScore());
        model.setDeviceStatusScore(entry.getDeviceStatusScore());
        model.setRawPurchaseAmountScore(entry.getRawPurchaseAmountScore());
        model.setTransAmountOnlineScore(entry.getTransAmountOnlineScore());
        model.setTransOnlineRawPurchPerScore(entry.getTransOnlineRawPurchPerScore());
        model.setUseMonthsNumScore(entry.getUseMonthsNumScore());
        model.setEconUseMonthsNumScore(entry.getEconUseMonthsNumScore());
        model.setEconUseMonthsAmountScore(entry.getEconUseMonthsAmountScore());
        model.setReleGuaraTaxSalesPerScore(entry.getReleGuaraTaxSalesPerScore());
        model.setRawPurchaseIncrePerScore(entry.getRawPurchaseIncrePerScore());
        model.setReleFinanAmountTaxSalesScore(entry.getReleFinanAmountTaxSalesScore());
        model.setAnnualizTaxSalesPerScore(entry.getAnnualizTaxSalesPerScore());
        model.setAnnualizTaxAmountScore(entry.getAnnualizTaxAmountScore());
        model.setRawBrandScore(entry.getRawBrandScore());
        model.setRawAnnualizAmountScore(entry.getRawAnnualizAmountScore());
        model.setWorkshopSquareScore(entry.getWorkshopSquareScore());
        model.setFlowOfProductionScore(entry.getFlowOfProductionScore());
        model.setManagementModelScore(entry.getManagementModelScore());
        model.setGuaraTaxSalesPerScore(entry.getGuaraTaxSalesPerScore());
        model.setPurcherVarietyScore(entry.getPurcherVarietyScore());
        model.setShareChangeNumScore(entry.getShareChangeNumScore());
        model.setEnforcedNumScore(entry.getEnforcedNumScore());
        model.setZxEnforcedNumScore(entry.getZxEnforcedNumScore());
        model.setFinanInstitutionScore(entry.getFinanInstitutionScore());
        model.setSupplierCooperateTimeScore(entry.getSupplierCooperateTimeScore());
        model.setBusinessShopNumScore(entry.getBusinessShopNumScore());
        model.setEconOverdueNumScore(entry.getEconOverdueNumScore());
        model.setManageStableScore(entry.getManageStableScore());
        model.setConsumStableScore(entry.getConsumStableScore());
        model.setAverageIndustryConsumPerScore(entry.getAverageIndustryConsumPerScore());
        model.setUpstreamCustStableScore(entry.getUpstreamCustStableScore());
        model.setDownstreamCustStableScore(entry.getDownstreamCustStableScore());
        model.setUpstreamVipCustStableScore(entry.getUpstreamVipCustStableScore());
        model.setDownstreamCustSalesStableScore(entry.getDownstreamCustSalesStableScore());
        model.setTaxLevelScore(entry.getTaxLevelScore());
        model.setAllScore(entry.getAllScore());
    }

  @Override
  public String importPerfectCustDatumScoreFile(MultipartFile file, String companyPk, String processInstanceId) throws IOException, BiffException {
      String msg = "";
      if (!(file.getSize() > 0)) {
          msg = Constants.IMPORT_FILE_ISEMPTY;
      } else {
          InputStream is = file.getInputStream();
          Map<String,Object> sheetMap = getTotalRows(is);
          //获取模板行数
          int rows = (int)sheetMap.get("rows");
          Sheet sheet = (Sheet)sheetMap.get("sheet");
          if (rows > 0 ){
              for (int i = 1; i < rows; i++) {
                  Map<String, Object> map = readExcel(sheet, i);
                  boolean isNumber = (boolean) map.get("isNumber");
                  boolean isAllScore = (boolean) map.get("isAllScore");
                  String excelCompanyPk = (String) map.get("companyPk");
                  if (CommonUtil.isNotEmpty(excelCompanyPk)) {
                      if (isAllScore) {
                          if (isNumber) {
                              //更新分值
                              Map<String, Object> returnMap = updateScore(excelCompanyPk, processInstanceId, map, i);
                              msg = (String) returnMap.get("msg");
                              boolean isSuccess = (boolean) returnMap.get("isSuccess");
                              if (!isSuccess) {
                                  break;
                              }
                          } else {
                              msg = "{\"success\":false,\"msg\":\"第" + (i + 1) + "行存在分值填写错误！\"}";
                              break;
                          }
                      } else {
                          msg = "{\"success\":false,\"msg\":\"第" + (i + 1) + "行总得分计算有误！\"}";
                          break;
                      }
                  } else{
                      msg = "{\"success\":false,\"msg\":\"第" + (i + 1) + "行导入模板内容有误！\"}";
                      break;
                  }
              }
          }
      }
      return msg;
  }

    /**
     * 更新分值
     * @param companyPk
     * @param processInstanceId
     * @param map
     * @return
     */
    private Map<String,Object> updateScore(String companyPk, String processInstanceId, Map<String, Object> map,int i) {
        int result = 0;
        String msg = Constants.RESULT_FAIL_MSG;
        B2bEconomicsCustomerDto dto = b2bEconomicsCustomerExtDao.getByCompanyPk(companyPk);
        List<Double> list = (List<Double>) map.get("list");
        boolean isSuccess = false;
        if (dto != null){
            if (list != null && list.size() != 39) {
                msg = "{\"success\":false,\"msg\":\"第"+(i+1)+"行导入数据有问题！\"}";
            } else {
                if (list != null && list.size() == 39) {
                    B2bEconomicsImprovingdataEntity entity = JSONObject.parseObject(dto.getImprovingdataInfo(),B2bEconomicsImprovingdataEntity.class);

                    //设置分值
                    setDatumSore(list, entity);
                    String ipproveDataInfo = JsonUtils.convertToString(entity);
                    B2bEconomicsCustomerExtDto customerExtDto = new B2bEconomicsCustomerExtDto();
                    customerExtDto.setPk(dto.getPk());
                    customerExtDto.setImprovingdataInfo(ipproveDataInfo);
                    result = b2bEconomicsCustomerExtDao.updateEconomicsCustomer(customerExtDto);
                    Criteria criteria = new Criteria();
                    if (!CommonUtil.isNotEmpty(processInstanceId)) {
                        Map<String, Object> searchMap = new HashMap<>();
                        searchMap.put("companyPk", companyPk);
                        List<B2bEconomicsCustomerDto> companyList = b2bEconomicsCustomerExtDao.searchList(searchMap);
                        if (companyList != null && companyList.size() > 0) {
                            B2bEconomicsCustomerDto extDto = companyList.get(0);
                            if (CommonUtil.isNotEmpty(extDto.getProcessInstanceId())) {
                                processInstanceId = extDto.getProcessInstanceId();
                            }
                        }
                    }
                    criteria.andOperator(Criteria.where("processInstanceId").is(processInstanceId));
                    Query query = new Query(criteria);
                    EconCustomerMongo econCustomerMongo = mongoTemplate.findOne(query, EconCustomerMongo.class);
                    if (econCustomerMongo != null) {
                        B2bEconomicsImprovingdataEntity econDto = econCustomerMongo.getImprovingdataDto();
                        if (econDto != null) {
                            setDatumSore(list, econDto);
                            econCustomerMongo.setImprovingdataDto(econDto);
                            mongoTemplate.save(econCustomerMongo);
                        }
                    }
                }
                if (result > 0) {
                    msg = Constants.RESULT_SUCCESS_MSG;
                    isSuccess = true;
                } else {
                    msg = "{\"success\":false,\"msg\":\"第"+(i+1)+"行操作失败!\"}";
                }
            }
        } else{
            msg = "{\"success\":false,\"msg\":\"操作失败!第"+(i+1)+"行先完善资料，再导入分值!\"}";
        }
        Map<String,Object> returnMap = new HashMap<>();
        returnMap.put("isSuccess",isSuccess);
        returnMap.put("msg",msg);
        return returnMap;
    }


    public static boolean isIntegerOrDouble(String str) {

        Pattern patternInteger = Pattern.compile("[0-9]*");
        Pattern patternDouble = Pattern.compile("[0-9]+.[0-9]");
        Matcher isNumInteger = patternInteger.matcher(str);
        Matcher isNumDouble = patternDouble.matcher(str);
        if (isNumInteger.matches() || isNumDouble.matches()) {
            return true;
        }
        return false;
    }

private void setDatumSore(List<Double> list, B2bEconomicsImprovingdataEntity model) {
    model.setEstablishingTimeScore(list.get(0));
    model.setTaxSalesScore(list.get(1));
    model.setFinanAmountTaxSalesScore(list.get(2));
    model.setEcomCooperateTimeScore(list.get(3));
    model.setDeviceStatusScore(list.get(4));
    model.setRawPurchaseAmountScore(list.get(5));
    model.setTransAmountOnlineScore(list.get(6));
    model.setTransOnlineRawPurchPerScore(list.get(7));
    model.setUseMonthsNumScore(list.get(8));
    model.setEconUseMonthsNumScore(list.get(9));
    model.setEconUseMonthsAmountScore(list.get(10));
    model.setReleGuaraTaxSalesPerScore(list.get(11));
    model.setRawPurchaseIncrePerScore(list.get(12));
    model.setReleFinanAmountTaxSalesScore(list.get(13));
    model.setAnnualizTaxSalesPerScore(list.get(14));
    model.setAnnualizTaxAmountScore(list.get(15));
    model.setRawBrandScore(list.get(16));
    model.setRawAnnualizAmountScore(list.get(17));
    model.setWorkshopSquareScore(list.get(18));
    model.setFlowOfProductionScore(list.get(19));
    model.setManagementModelScore(list.get(20));
    model.setGuaraTaxSalesPerScore(list.get(21));
    model.setPurcherVarietyScore(list.get(22));
    model.setShareChangeNumScore(list.get(23));
    model.setEnforcedNumScore(list.get(24));
    model.setZxEnforcedNumScore(list.get(25));
    model.setFinanInstitutionScore(list.get(26));
    model.setSupplierCooperateTimeScore(list.get(27));
    model.setBusinessShopNumScore(list.get(28));
    model.setEconOverdueNumScore(list.get(29));
    model.setManageStableScore(list.get(30));
    model.setConsumStableScore(list.get(31));
    model.setAverageIndustryConsumPerScore(list.get(32));
    model.setUpstreamCustStableScore(list.get(33));
    model.setDownstreamCustStableScore(list.get(34));
    model.setUpstreamVipCustStableScore(list.get(35));
    model.setDownstreamCustSalesStableScore(list.get(36));
    model.setTaxLevelScore(list.get(37));
    //model.setFinanAmountScore(list.get(38));
    model.setAllScore(list.get(38));
}


    public Map<String,Object> getTotalRows(InputStream is){
        org.apache.poi.ss.usermodel.Workbook workbook = null;
        try {
            workbook = WorkbookFactory.create(is);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
        Map<String,Object> map = new HashMap<>();
        //读取第一个工作页sheet
        Sheet sheet = workbook.getSheetAt(0);
        map.put("sheet",sheet);
        map.put("rows",getExcelRealRow(sheet));
        /** 得到Excel的行数 */
        return map;
    }

    // 获取Excel表的真实行数
    int getExcelRealRow(Sheet sheet) {
        boolean flag = false;
        for (int i = 1; i <= sheet.getLastRowNum(); ) {
            Row r = sheet.getRow(i);
            if (r == null) {
                // 如果是空行（即没有任何数据、格式），直接把它以下的数据往上移动
                sheet.shiftRows(i + 1, sheet.getLastRowNum(), -1);
                continue;
            }
            flag = false;
            for (Cell c : r) {
                if (c.getCellType() != Cell.CELL_TYPE_BLANK) {
                    flag = true;
                    break;
                }
            }
            if (flag) {
                i++;
                continue;
            } else {
                // 如果是空白行（即可能没有数据，但是有一定格式）
                if (i == sheet.getLastRowNum())// 如果到了最后一行，直接将那一行remove掉
                    sheet.removeRow(r);
                else//如果还没到最后一行，则数据往上移一行
                    sheet.shiftRows(i + 1, sheet.getLastRowNum(), -1);
            }
        }
        return sheet.getLastRowNum()+1;
    }



    //获取Excel中的每一列数据
    public static Map<String, Object> readExcel(Sheet sheet,int i) {
        List<Double> lists = new LinkedList<>();
        //读取excel文件
        Map<String, Object> map = new HashMap<>();
            double allScore = 0d;
            /** 得到Excel的列数 */
            Row row = sheet.getRow(i);
            if (row != null) {
                int totalCells = sheet.getRow(i).getLastCellNum();
                //getPhysicalNumberOfCells();
                boolean isNumber = true;
                boolean isAllScore = false;
                for (int c = 1; c < totalCells; c++) {
                    Cell cell = row.getCell(c);
                    double score = 0d;
                    String value = "0";
                    if (null != cell) {
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        value = cell.getStringCellValue();
                    }
                        if (c == Constants.ONE) {
                            map.put("companyPk", value);
                        } else {
                            if (CommonUtil.isNotEmpty(value)) {
                                    if (!isIntegerOrDouble(value)) {
                                        isNumber = false;
                                        break;
                                    }
                                    score = Double.valueOf(value);
                                    //最后一列总分不计入合计中
                                    if (c != (totalCells -1)) {
                                        allScore += score;
                                    }
                                    //判断模板所加分值是否正确
                                    if (c == (totalCells -1) && ( allScore == Double.valueOf(value))){
                                        isAllScore = true;
                                    }
                            }
                        }

                    if (c != Constants.ONE) {
                        lists.add(score);
                    }
                }
                map.put("list", lists);
                map.put("isNumber", isNumber);
                map.put("isAllScore", isAllScore);
            }
        return map;
    }

    @Override
    public int countStoreSalesNum(String purchaserPk) {
        return economicsGoodsExDao.countStoreSalesNumbers(purchaserPk);
    }

    @Override
    public Double getPlatformCustDatum(String purchaserPk) {
        return economicsGoodsExDao.countPlatformAmount(purchaserPk);
    }

    @Override
    public Double getCreditCustDatum(String purchaserPk) {
        return economicsGoodsExDao.countCreditAmount(purchaserPk);
    }

    @Override
    public List<B2bEconomicsCustomerGoodsDto> getEconomicsCustomerGoods(String pk) {

        Map<String, Object> map = new HashMap<>();
        map.put("economicsCustomerPk", pk);
        List<B2bEconomicsCustomerGoodsDto> list = b2bEconomicsCustomerGoodsExDao.searchList(map);
        return list;
    }
}
