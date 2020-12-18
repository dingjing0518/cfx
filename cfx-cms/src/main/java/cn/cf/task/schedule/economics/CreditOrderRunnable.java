package cn.cf.task.schedule.economics;

import cn.cf.common.ColAuthConstants;
import cn.cf.common.Constants;
import cn.cf.common.ExportDoJsonParams;
import cn.cf.common.ExportUtil;
import cn.cf.common.utils.BeanUtils;
import cn.cf.common.utils.CommonUtil;
import cn.cf.common.utils.OSSUtils;
import cn.cf.common.utils.ZipUtils;
import cn.cf.dao.B2bEconomicsGoodsExDao;
import cn.cf.dao.B2bOrderExtDao;
import cn.cf.dao.SysExcelStoreExtDao;
import cn.cf.dto.B2bEconomicsGoodsDto;
import cn.cf.dto.B2bLoanNumberExtDto;
import cn.cf.dto.SysExcelStoreDto;
import cn.cf.dto.SysExcelStoreExtDto;
import cn.cf.entity.B2bRepaymentRecord;
import cn.cf.entity.OrderDataTypeParams;
import cn.cf.model.SysExcelStore;
import cn.cf.task.schedule.ScheduledFutureMap;
import cn.cf.util.ArithUtil;
import cn.cf.util.DateUtil;
import cn.cf.util.InterestUtil;
import com.alibaba.fastjson.JSON;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ScheduledFuture;

public class CreditOrderRunnable implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(CreditOrderRunnable.class);
    private String name;

    private String fileName;

    private String accountPk;

    private String orderName;
    private String orderType;

    private Date insertTime;

    private String uuid;
    private SysExcelStoreExtDto storeDtoTemp;

    private B2bOrderExtDao orderDao;

    private SysExcelStoreExtDao storeDao;

    private B2bEconomicsGoodsExDao b2bEconomicsGoodsExDao;

    private MongoTemplate mongoTemplate;

    public CreditOrderRunnable() {
    }


    public CreditOrderRunnable(String name,String orderName,String orderType,String uuid) {
        this.orderName = orderName;
        this.orderType = orderType;
        this.name = name;
        this.uuid = uuid;
    }

    @Override
    public void run() {
        //上传数据
        ScheduledFuture future = null;
        if (CommonUtil.isNotEmpty(this.name)) {
            future = ScheduledFutureMap.map.get(this.name);
        }
        try {
            upLoadFile();
        } catch (Exception e) {
            ExportDoJsonParams.updateErrorExcelStoreStatus(this.storeDao,this.storeDtoTemp,e.getMessage());
        } finally {
            ScheduledFutureMap.stopFuture(future,this.name);
        }
    }

    //上传到OSS
    public void upLoadFile() throws Exception {

        this.orderDao = (B2bOrderExtDao) BeanUtils.getBean("b2bOrderExtDao");
        this.storeDao = (SysExcelStoreExtDao) BeanUtils.getBean("sysExcelStoreExtDao");
        this.b2bEconomicsGoodsExDao = (B2bEconomicsGoodsExDao) BeanUtils.getBean("b2bEconomicsGoodsExDao");
        this.mongoTemplate = (MongoTemplate) BeanUtils.getBean("mongoTemplate");

        if (this.storeDao != null) {
            Map<String, Object> map = ExportDoJsonParams.setSysExcelStore(Constants.TWO,"exportCreditOrders_"+StringUtils.defaultIfEmpty(this.uuid, ""));
            //查询存在要导出的任务
            List<SysExcelStoreExtDto> list = this.storeDao.getFirstTimeExcelStore(map);
            if (list != null && list.size() > Constants.ZERO) {
                for (SysExcelStoreExtDto storeDto : list) {
                    this.storeDtoTemp = storeDto;
                    OrderDataTypeParams params = null;
                    if (CommonUtil.isNotEmpty(storeDto.getParams())) {
                         params = JSON.parseObject(storeDto.getParams(), OrderDataTypeParams.class);
                        this.accountPk = storeDto.getAccountPk();
                        this.insertTime = storeDto.getInsertTime();
                        this.orderType = params.getOrderType();
                        this.orderName = params.getOrderName();
                        this.fileName = "金融中心-授信管理-订单管理-借款单管理-"+storeDto.getAccountName()+"-"+ DateUtil.formatYearMonthDayHMS(storeDto.getInsertTime());
                    }

                if (this.orderDao != null) {
                    //设置查询参数
                    Map<String, Object> orderMap = orderParams(params);
                    String ossPath = "";
                    int counts = this.orderDao.searchCreditOrders(orderMap);
                    if (counts > Constants.EXCEL_NUMBER_TENHOUSAND) {
                        //大于5000，分页查询数据
                        ossPath = setLimitPages(orderMap, counts,storeDto,params);
                    } else {
                        //如果小于或等于5000条直接上传
                        ossPath = setNotLimitPages(orderMap,params);
                    }
                    //更新订单导出状态
                    ExportDoJsonParams.updateExcelStoreStatus(storeDto,this.storeDao, ossPath);
                }
              }
            }
        }
    }
    private String setNotLimitPages(Map<String, Object> orderMap,OrderDataTypeParams params) {
        String ossPath = "";
        List<B2bLoanNumberExtDto> list = this.orderDao.searchCreditOrderList(orderMap);
        if (list != null && list.size() > Constants.ZERO) {
            // 计算相关利息
            matchRate(list);
            ExportUtil<B2bLoanNumberExtDto> exportUtil = new ExportUtil<>();
            String templateName = setTemplateFile(params);
            String path = exportUtil.exportDynamicUtil(templateName,this.fileName, list);
            File file = new File(path);
            //上传到OSS
            ossPath = OSSUtils.ossUploadXls(file, Constants.FIVE);
        }
        return ossPath;
    }

    //修改上传列表状态
    private void updateExcelStoreStatus(SysExcelStoreDto dto, String ossPath) {
        if (CommonUtil.isNotEmpty(ossPath)) {
            SysExcelStoreDto storeDto = this.storeDao.getByPk(dto.getPk());
            SysExcelStore store = new SysExcelStore();
            org.springframework.beans.BeanUtils.copyProperties(storeDto, store);
            store.setIsDeal(Constants.ONE);
            store.setUrl(ossPath);
            this.storeDao.update(store);
        }
    }
    // 如果多余5000条数据分多个excel导出，并打包上传OSS
    private String setLimitPages(Map<String, Object> orderMap, double counts,SysExcelStoreExtDto storeDto,OrderDataTypeParams params) {
        double numbers = Math.floor(counts / Constants.EXCEL_NUMBER_TENHOUSAND);//获取分页查询次数
        orderMap.put("limit", Constants.EXCEL_NUMBER_TENHOUSAND);
        List<File> fileList = new ArrayList<>();
        for (int i = Constants.ZERO; i < numbers + Constants.ONE; i++) { //
            int start = Constants.ZERO;
            if (i != Constants.ZERO) {
                start = ExportDoJsonParams.indexPageNumbers(i);
            }
            orderMap.put("start", start);
            List<B2bLoanNumberExtDto> list = this.orderDao.searchCreditOrderList(orderMap);
            orderMap.remove("start");
            if (list != null && list.size() > Constants.ZERO) {
                // 计算相关利息
                matchRate(list);
                ExportUtil<B2bLoanNumberExtDto> exportUtil = new ExportUtil<>();
                String excelName = "金融中心-授信管理-订单管理-借款单管理-"+storeDto.getAccountName()+"-"+ DateUtil.formatYearMonthDayHMS(new Date())+"-"+(i+1);
                String templateName = setTemplateFile(params);
                String path = exportUtil.exportDynamicUtil(templateName,excelName, list);
                File file = new File(path);
                fileList.add(file);
            }
        }
        //上传Zip到OSS
        return OSSUtils.ossUploadXls(new File(ZipUtils.fileToZip(fileList, this.fileName)), Constants.FILE);
    }

    private void matchRate(List<B2bLoanNumberExtDto> list) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 计算相关利息
        for (B2bLoanNumberExtDto dto : list) {
            //计算已还利息、应收服务费、化纤贷手续费
            Double interest = 0d;// 实还利息
            Double sevenCharge = 0d;//化纤汇手续费
            Double serviceCharge = 0d;//应收服务费
            Double amount = 0d;//实还本金
            if (dto.getLoanStatus() != null
                    && (dto.getLoanStatus() == 5 || dto.getLoanStatus() == 6 || dto.getLoanStatus() == 3)
                    && dto.getLoanStartTime() != null && !dto.getLoanStartTime().equals("")) {
                Double loanAmount = dto.getLoanAmount() == null ? 0.0 : dto.getLoanAmount();
                Double principal = dto.getPrincipal() == null ? 0.0 : dto.getPrincipal();
                // 未还银行的本金
                Double repayingBank = ArithUtil.sub(loanAmount, principal);
                String loanStartTime = sdf.format(dto.getLoanStartTime());
                String nowTime = sdf.format(new Date());
                // String loanEndTime = sdf.format(dto.getLoanEndTime());
                Double totalRate = dto.getTotalRate() == null ? 0.0 : dto.getTotalRate();
                Double bankRate = dto.getBankRate() == null ? 0.0 : dto.getBankRate();
                Double sevenRate = dto.getSevenRate() == null ? 0.0 : dto.getSevenRate();
                Double platformRate = ArithUtil.sub(totalRate, bankRate);
                dto.setPlatformRate(platformRate);
                if (repayingBank < 0) {//还款金额大于放款金额的时候，应该是0
                    dto.setRepayingInterest(0d);
                    dto.setRepayingSerCharge(0d);
                    dto.setAmountPayable(0d);
                } else {
                    int type = 0;
                    if (dto.getEconomicsGoodsType() != null && (dto.getEconomicsGoodsType() == 2 || dto.getEconomicsGoodsType() == 4)) {// 化纤贷
                        type = 2;
                    } else if (dto.getEconomicsGoodsType() != null && (dto.getEconomicsGoodsType() == 1 || dto.getEconomicsGoodsType() == 3)) {
                        type = 1;
                    }
                    try {
                        // 未还银行利息
                        Double repayingInterest = InterestUtil.getInterest(loanStartTime, nowTime, repayingBank, bankRate);
                        // 未还本金平台服务费
                        Double repayingSerCharge = InterestUtil.getCoverCharges(loanStartTime, nowTime, repayingBank,
                                totalRate, bankRate, sevenRate, type);
                        // 客户应还金额 = 未还本金+ 未还服务费+ 未还银行利息；
                        Double amountPayable = ArithUtil.add(ArithUtil.add(repayingBank, repayingSerCharge),
                                repayingInterest);

                        dto.setRepayingInterest(repayingInterest);
                        dto.setRepayingSerCharge(repayingSerCharge);
                        dto.setAmountPayable(amountPayable);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                Criteria c = new Criteria();
                c.andOperator(Criteria.where("orderNumber").is(dto.getOrderNumber()));
                Query query = new Query(c);
                List<B2bRepaymentRecord> recordList = mongoTemplate.find(query, B2bRepaymentRecord.class);
                for (int i = 0; i < recordList.size(); i++) {
                    B2bRepaymentRecord o = recordList.get(i);
                    if (null != o.getInterest()) {
                        interest += o.getInterest();
                    }
                    if (null != o.getSevenCharge()) {
                        sevenCharge += o.getSevenCharge();
                    }
                    if (null != o.getServiceCharge()) {
                        serviceCharge += o.getServiceCharge();
                    }
                    if (null != o.getAmount()) {
                        amount += o.getAmount();
                    }
                }
           }
            dto.setInterest(interest);
            dto.setSevenCharge(sevenCharge);
            dto.setServiceCharge(serviceCharge);
            dto.setRepaymentAmount(amount);
            dto.setRemainingPrincipal(dto.getLoanAmount() == null ? 0 : ArithUtil.sub(dto.getLoanAmount(), amount));
        }
    }




    private Map<String, Object> orderParams(OrderDataTypeParams params) {
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("orderName", this.orderName);
        map.put("orderType", this.orderType);
        map.put("insertTime", this.insertTime);
        map.put("colPurName", CommonUtil.isExistAuthName(this.accountPk,
                ColAuthConstants.EM_CREDIT_ORDER_LOANBILL_COL_PURNAME));
        map.put("colSuppName", CommonUtil.isExistAuthName(this.accountPk,
                ColAuthConstants.EM_CREDIT_ORDER_LOANBILL_COL_SUPPNAME));
        if (params != null) {
            map.put("orderNumber", params.getOrderNumber());
            map.put("purchaserName", params.getPurchaserName());
            map.put("contractNumber", params.getContractNumber());
            map.put("loanNumber", params.getLoanNumber());
            map.put("loanStartTimeBegin", params.getLoanStartTimeBegin());
            map.put("loanStartTimeEnd", params.getLoanStartTimeEnd());
            map.put("loanEndTimeBegin", params.getLoanEndTimeBegin());
            map.put("loanEndTimeEnd", params.getLoanEndTimeEnd());
            map.put("loanStatus", params.getLoanStatus());
            map.put("bankPk", params.getBankPk());
            map.put("economicsGoodsType", params.getEconomicsGoodsType());
            map.put("economicsGoodsName", params.getEconomicsGoodsName());
            map.put("isOverdue", params.getIsOverdue());
        }
        return map;
    }

    private String setTemplateFile(OrderDataTypeParams params){
            String fileName = "";
        if (params.getEconomicsGoodsName() != null && !params.getEconomicsGoodsName().equals("")) {// 化纤贷和化纤白条选择的模板不一样
            B2bEconomicsGoodsDto goodsDto = this.b2bEconomicsGoodsExDao.getByName(params.getEconomicsGoodsName());
            if (goodsDto != null && goodsDto.getGoodsType() != null && goodsDto.getGoodsType() == 2) {
                fileName = "creditDOrder";
            } else {
                fileName = "creditOrder";
            }
        } else {
            fileName = "creditOrder";

        }
        return fileName;
    }
}
