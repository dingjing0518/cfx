package cn.cf.task.schedule.operation;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import cn.cf.common.ColAuthConstants;
import cn.cf.common.Constants;
import cn.cf.common.ExportDoJsonParams;
import cn.cf.common.ExportUtil;
import cn.cf.common.utils.BeanUtils;
import cn.cf.common.utils.CommonUtil;
import cn.cf.common.utils.OSSUtils;
import cn.cf.common.utils.ZipUtils;
import cn.cf.dao.B2bLotteryRecordExDao;
import cn.cf.dao.SysExcelStoreExtDao;
import cn.cf.dto.B2bLotteryRecordExDto;
import cn.cf.dto.SysExcelStoreExtDto;
import cn.cf.entity.CustomerDataTypeParams;
import cn.cf.task.schedule.ScheduledFutureMap;
import cn.cf.util.DateUtil;

public class LotteryAwardRunnable implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(LotteryAwardRunnable.class);
    private String name;

    private String fileName = "";

    private String accountPk;

    private String accountName;

    private String isHistory;

    private Date insertTime;

    private String uuid;
    private SysExcelStoreExtDto storeDtoTemp;

    private SysExcelStoreExtDao storeDao;
    public LotteryAwardRunnable() {
    }

    public LotteryAwardRunnable(String name,String uuid) {
        this.uuid = uuid;
        this.name = name;
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
            ScheduledFutureMap.stopFuture(future, this.name);
        }
    }

    //上传到OSS
    public void upLoadFile() throws Exception {

        B2bLotteryRecordExDao b2bLotteryRecordExDao = (B2bLotteryRecordExDao) BeanUtils.getBean("b2bLotteryRecordExDao");

        this.storeDao = (SysExcelStoreExtDao) BeanUtils.getBean("sysExcelStoreExtDao");

        if (storeDao != null) {
            Map<String, Object> map = ExportDoJsonParams.setSysExcelStore(Constants.ONE,"exportAwardRoster_"+StringUtils.defaultIfEmpty(this.uuid, ""));
            //查询存在要导出的任务
            List<SysExcelStoreExtDto> list = storeDao.getFirstTimeExcelStore(map);
            if (list != null && list.size() > Constants.ZERO) {
                for (SysExcelStoreExtDto storeDto : list) {
                    this.storeDtoTemp = storeDto;
                    CustomerDataTypeParams params = null;
                    if (CommonUtil.isNotEmpty(storeDto.getParams())) {
                        params = JSON.parseObject(storeDto.getParams(), CustomerDataTypeParams.class);
                        this.accountName = storeDto.getAccountName();
                        this.isHistory = params.getIsHistory();
                        this.insertTime = storeDto.getInsertTime();
                        if (CommonUtil.isNotEmpty(this.isHistory) && "1".equals(this.isHistory)){
                            this.fileName = "运营中心-会员活动-抽奖管理-抽奖记录-" + this.accountName + "-" + DateUtil.formatYearMonthDayHMS(storeDto.getInsertTime());
                        }else{
                            this.fileName = "运营中心-会员活动-抽奖管理-获奖名单-" + this.accountName + "-" + DateUtil.formatYearMonthDayHMS(storeDto.getInsertTime());
                        }
                        this.accountPk = storeDto.getAccountPk();
                    }
                    if (b2bLotteryRecordExDao != null) {
                        //设置查询参数
                        Map<String, Object> lotteryMap = lotteryListParams(params);
                        int counts = b2bLotteryRecordExDao.searchLotteryAwardRosterCount(lotteryMap);
                        String ossPath = "";
                        if (counts > Constants.EXCEL_NUMBER_TENHOUSAND) {
                            //大于10000，分页查询数据
                            ossPath = setLimitPages(lotteryMap, counts, b2bLotteryRecordExDao);
                        } else {
                            //如果小于或等于10000条直接上传
                            ossPath = setNotLimitPages(b2bLotteryRecordExDao, lotteryMap);
                        }
                        //更新订单导出状态
                        ExportDoJsonParams.updateExcelStoreStatus(storeDto,storeDao, ossPath);
                    }
                }
            }
        }
    }
    private String setNotLimitPages(B2bLotteryRecordExDao b2bLotteryRecordExDao, Map<String, Object> lotteryMap) {
        String ossPath = "";
        List<B2bLotteryRecordExDto> lotteryList = b2bLotteryRecordExDao.searchLotteryAwardRosterList(lotteryMap);
        if (lotteryList != null && lotteryList.size() > Constants.ZERO) {
            ExportUtil<B2bLotteryRecordExDto> exportUtil = new ExportUtil<>();
            String path = exportUtil.exportDynamicUtil("lotteryRecord", this.fileName, lotteryList);
            File file = new File(path);
            //上传到OSS
            ossPath = OSSUtils.ossUploadXls(file, Constants.FIVE);
        }
        return ossPath;
    }

    // 如果多余5000条数据分多个excel导出，并打包上传OSS
    private String setLimitPages(Map<String, Object> lotteryMap, double counts, B2bLotteryRecordExDao b2bLotteryRecordExDao) {
        double numbers = Math.floor(counts / Constants.EXCEL_NUMBER_TENHOUSAND);//获取分页查询次数
        lotteryMap.put("limit", Constants.EXCEL_NUMBER_TENHOUSAND);
        List<File> fileList = new ArrayList<>();
        for (int i = Constants.ZERO; i < numbers + Constants.ONE; i++) { //
            int start = Constants.ZERO;
            if (i != Constants.ZERO) {
                start = ExportDoJsonParams.indexPageNumbers(i);
            }
            lotteryMap.put("start", start);
            //判断不同公司类型，获取不同数据

            List<B2bLotteryRecordExDto> lotteryList = b2bLotteryRecordExDao.searchLotteryAwardRosterList(lotteryMap);
            lotteryMap.remove("start");
            if (lotteryList != null && lotteryList.size() > Constants.ZERO) {
                ExportUtil<B2bLotteryRecordExDto> exportUtil = new ExportUtil<>();
                String excelName = "";
                if (CommonUtil.isNotEmpty(this.isHistory) && "1".equals(this.isHistory)){
                    excelName = "运营中心-会员活动-抽奖管理-抽奖记录-" + this.accountName + "-" + DateUtil.formatYearMonthDayHMS(new Date())+"-"+(i+1);
                }else{
                    excelName = "运营中心-会员活动-抽奖管理-获奖名单-" + this.accountName + "-" + DateUtil.formatYearMonthDayHMS(new Date())+"-"+(i+1);
                }
                String path = exportUtil.exportDynamicUtil("lotteryRecord", excelName, lotteryList);
                File file = new File(path);
                fileList.add(file);
            }
        }
        //上传Zip到OSS
        return OSSUtils.ossUploadXls(new File(ZipUtils.fileToZip(fileList, this.fileName)), Constants.FILE);
    }

    private Map<String, Object> lotteryListParams(CustomerDataTypeParams params) {
        Map<String, Object> map = new HashMap<String, Object>();

        if (params != null) {
            // 获奖名单页签条件
            if (params.getIsHistory() == null) {
                map.put("status", params.getStatus());
                map.put("awardStatus", params.getIsAwardStatus());
                map.put("activityType", params.getActivityType());

            }
            map.put("insertTimeStart", params.getInsertTimeStart());
            map.put("insertTimeEnd", params.getInsertTimeEnd());
            map.put("insertTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.insertTime));
            map.put("mobile", params.getMobile());
            map.put("memberName", params.getMemberName());
            map.put("isHistory", params.getIsHistory());
            // 抽奖记录页签查询条件
            if (params.getIsHistory() != null) {
                map.put("status", params.getIsStatus());
                map.put("awardStatus", params.getAwardStatus());
                map.put("name", params.getAwardName());
                map.put("awardType", params.getAwardType());
            }
            if ("1".equals(params.getColAuthAwardType())) {
                setRosterColParams(this.accountPk, map);
            }
            if ("2".equals(params.getColAuthAwardType())) {
                setHistoryColParams(this.accountPk, map);
            }
        }
        return map;
    }

    private void setRosterColParams(String accountPk, Map<String,Object> map){
        if (CommonUtil.isNotEmpty(accountPk)){
            if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.OPER_ACTIVITY_AWARD_COL_MEMBERNAME)){
                map.put("memberNameCol",ColAuthConstants.OPER_ACTIVITY_AWARD_COL_MEMBERNAME);
            }
            if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.OPER_ACTIVITY_AWARD_COL_MOBILE)){
                map.put("mobileCol",ColAuthConstants.OPER_ACTIVITY_AWARD_COL_MOBILE);
            }
            if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.OPER_ACTIVITY_AWARD_COL_COMPANYNAME)){
                map.put("companyNameCol",ColAuthConstants.OPER_ACTIVITY_AWARD_COL_COMPANYNAME);
            }
        }
    }

    private void setHistoryColParams(String accountPk,Map<String,Object> map){
        if (CommonUtil.isNotEmpty(accountPk)){
            if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.OPER_ACTIVITY_HIS_COL_MEMBERNAME)){
                map.put("memberNameCol",ColAuthConstants.OPER_ACTIVITY_HIS_COL_MEMBERNAME);
            }
            if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.OPER_ACTIVITY_HIS_COL_MOBILE)){
                map.put("mobileCol",ColAuthConstants.OPER_ACTIVITY_HIS_COL_MOBILE);
            }
            if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.OPER_ACTIVITY_HIS_COMPANYNAME)){
                map.put("companyNameCol",ColAuthConstants.OPER_ACTIVITY_HIS_COMPANYNAME);
            }
        }
    }
}
