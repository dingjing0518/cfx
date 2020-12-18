package cn.cf.task.schedule.economics;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import cn.cf.common.Constants;
import cn.cf.common.ExportDoJsonParams;
import cn.cf.common.ExportUtil;
import cn.cf.common.utils.BeanUtils;
import cn.cf.common.utils.CommonUtil;
import cn.cf.common.utils.OSSUtils;
import cn.cf.dao.SysExcelStoreExtDao;
import cn.cf.dto.SysExcelStoreExtDto;
import cn.cf.entity.EconCustomerApproveExport;
import cn.cf.task.schedule.ScheduledFutureMap;
import cn.cf.util.DateUtil;
import net.sf.json.JSONObject;

public class ApproveManageRunnable implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(ApproveManageRunnable.class);
    private String name;

    private String fileName;

    private String years;

    private String bankPk;

    private String uuid;

    private SysExcelStoreExtDto storeDtoTemp;

    private MongoTemplate mongoTemplate;

    private SysExcelStoreExtDao storeDao;

    public ApproveManageRunnable() {
    }


    public ApproveManageRunnable(String name, String years, String bankPk,String uuid) {
        this.years = years;
        this.bankPk = bankPk;
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

        this.storeDao = (SysExcelStoreExtDao) BeanUtils.getBean("sysExcelStoreExtDao");
        this.mongoTemplate = (MongoTemplate) BeanUtils.getBean("mongoTemplate");
        if (this.storeDao != null) {
            Map<String, Object> map = ExportDoJsonParams.setSysExcelStore(Constants.FOUR,"exportApprovalManageList_"+StringUtils.defaultIfEmpty(this.uuid, ""));
            //查询存在要导出的任务
            List<SysExcelStoreExtDto> list = this.storeDao.getFirstTimeExcelStore(map);
            if (list != null && list.size() > Constants.ZERO) {
                for (SysExcelStoreExtDto storeDto : list) {
                    this.storeDtoTemp = storeDto;
                    if (CommonUtil.isNotEmpty(storeDto.getParams())) {
                        JSONObject obj = JSONObject.fromObject(storeDto.getParams());
                        if (obj.has("bankPk")){
                            this.bankPk = obj.get("bankPk").toString();
                        }
                        if (obj.has("years")){
                            this.years = obj.get("years").toString();
                        }
                    }
                    String ossPath = "";
                    this.fileName = "金融中心-数据管理-审批表-"+storeDto.getAccountName()+"-"+ DateUtil.formatYearMonthDayHMS(storeDto.getInsertTime());
                    if (this.mongoTemplate != null) {
                        //导出报表 更新订单导出状态
                        ossPath = setNotLimitPages();
                    }
                    //更新订单导出状态
                    ExportDoJsonParams.updateExcelStoreStatus(storeDto,this.storeDao, ossPath);
                }
            }
        }
    }
    private String setNotLimitPages() {

        List<EconCustomerApproveExport> list = getCustomApprove();
        ExportUtil<EconCustomerApproveExport> export = new ExportUtil<>();
            File file = new File(export.exportDynamicUtil("approvalManage",this.fileName,list));
            //上传到OSS
        return OSSUtils.ossUploadXls(file, Constants.FIVE);
    }

    private List<EconCustomerApproveExport> getCustomApprove(){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("bankPk", this.bankPk);
        map.put("year", this.years);
        return searchApprovalManageList(map);
    }

    public List<EconCustomerApproveExport> searchApprovalManageList(Map<String, Object> map) {
        Query query = new Query();
        for (String key : map.keySet()) {
            query.addCriteria(Criteria.where(key).is(map.get(key)));
        }
        query.with(new Sort(new Sort.Order(Sort.Direction.ASC, "flag"), new Sort.Order(Sort.Direction.ASC, "month")));
        List<EconCustomerApproveExport> list = mongoTemplate.find(query, EconCustomerApproveExport.class);

        EconCustomerApproveExport export = new EconCustomerApproveExport();
        if (list != null && list.size() > 0) {
            // 补全缺少的月份
            setMonth(list);
            for (EconCustomerApproveExport e : list) {
                e.setyApproving(e.getyBTApproving() + e.getyDApproving() + e.getyBTDApproving());
                e.setyPass(e.getyBTPass() + e.getyDPass() + e.getyBTDPass());
                e.setyUnpass(e.getyBTUnpass() + e.getyDUnpass() + e.getyBTDUnpass());

                e.setpApproving(e.getpBTApproving() + e.getpDApproving() + e.getpBTDApproving());
                e.setpPass(e.getpBTPass() + e.getpDPass() + e.getpBTDPass());
                e.setpUnpass(e.getpBTUnpass() + e.getpDUnpass() + e.getpBTDUnpass());

                e.setsApproving(e.getsBTApproving() + e.getsDApproving() + e.getsBTDApproving());
                e.setsPass(e.getsBTPass() + e.getsDPass() + e.getsBTDPass());
                e.setsUnpass(e.getsBTUnpass() + e.getsDUnpass() + e.getsBTDUnpass());

                e.setxApproving(e.getxBTApproving() + e.getxDApproving() + e.getxBTDApproving());
                e.setxPass(e.getxBTPass() + e.getxDPass() + e.getxBTDPass());
                e.setxUnpass(e.getxBTUnpass() + e.getxDUnpass() + e.getxBTDUnpass());
                e.setqApproving(e.getqBTApproving() + e.getqDApproving() + e.getqBTDApproving());
                e.setqPass(e.getqBTPass() + e.getqDPass() + e.getqBTDPass());
                e.setqUnpass(e.getqBTUnpass() + e.getqDUnpass() + e.getqBTDUnpass());
                if (e.getFlag() == 1) {
                    e.setTitle("昨日");
                }
                if (e.getFlag() == 2) {
                    e.setTitle("当周");
                }
                if (e.getFlag() == 3) {
                    e.setTitle("上周");
                }
                if (e.getFlag() == 4) {
                    e.setTitle(e.getMonth() + "月");
                    setExportTotal(export, e);// 月的合计
                }
            }
            list.add(export);
        } else {
            setMonth(list);
        }
        // 先按flag排序，再按月份排序
        Collections.sort(list);
        return list;
    }

    private void setMonth(List<EconCustomerApproveExport> list) {
        EconCustomerApproveExport newe = new EconCustomerApproveExport();
        if (list != null && list.size() > 0) {
            int lastMonth = list.get(list.size() - 1).getMonth();
            if (list.get(0).getFlag() == 4) {// 非当前年数据
                if (list.get(0).getMonth() > 1) {
                    for (int i = 1; i < list.get(0).getMonth(); i++) {
                        newe.setFlag(4);
                        newe.setMonth(i);
                        newe.setTitle(i + "月");
                        list.add(newe);
                        newe = new EconCustomerApproveExport();
                    }
                }
                if (lastMonth < 12) {
                    for (int i = lastMonth + 1; i <= 12; i++) {
                        newe.setFlag(4);
                        newe.setMonth(i);
                        newe.setTitle(i + "月");
                        list.add(newe);
                        newe = new EconCustomerApproveExport();
                    }
                }
            } else {
                // 当前年份有数：默认有昨日，上周，当周，本月4条排列循序的数据
                if (list.get(3).getMonth() > 1) {// 从年中旬开始统计，补全之前月
                    for (int i = 1; i < list.get(3).getMonth(); i++) {
                        newe.setFlag(4);
                        newe.setMonth(i);
                        list.add(newe);
                        newe = new EconCustomerApproveExport();
                    }
                }
                if (lastMonth < 12) {// 统计到年终，补全之月
                    for (int i = lastMonth + 1; i <= 12; i++) {
                        newe.setFlag(4);
                        newe.setMonth(i);
                        list.add(newe);
                        newe = new EconCustomerApproveExport();
                    }
                }
            }
        } else {// 该年份无数据
            for (int i = 1; i <= 12; i++) {
                newe.setFlag(4);
                newe.setMonth(i);
                newe.setTitle(i + "月");
                list.add(newe);
                newe = new EconCustomerApproveExport();
            }
            newe.setTitle("合计");
            newe.setFlag(5);
            list.add(newe);
        }
    }

    // 合计
    private void setExportTotal(EconCustomerApproveExport export, EconCustomerApproveExport e) {
        export.setTitle("合计");
        export.setFlag(5);
        export.setyBTApproving(export.getyBTApproving() + e.getyBTApproving());
        export.setyBTPass(export.getyBTPass() + e.getyBTPass());
        export.setyBTUnpass(export.getyBTUnpass() + e.getyBTUnpass());
        export.setyDApproving(export.getyDApproving() + e.getyDApproving());
        export.setyDPass(export.getyDPass() + e.getyDPass());
        export.setyDUnpass(export.getyDUnpass() + e.getyDUnpass());
        export.setyBTDApproving(export.getyBTDApproving() + e.getyBTDApproving());
        export.setyBTDPass(export.getyBTDPass() + e.getyBTDPass());
        export.setyBTDUnpass(export.getyBTDUnpass() + e.getyBTDUnpass());
        export.setyApproving(export.getyApproving() + e.getyApproving());
        export.setyPass(export.getyPass() + e.getyPass());
        export.setyUnpass(export.getyUnpass() + e.getyUnpass());

        export.setpBTApproving(export.getpBTApproving() + e.getpBTApproving());
        export.setpBTPass(export.getpBTPass() + e.getpBTPass());
        export.setpBTUnpass(export.getpBTUnpass() + e.getpBTUnpass());
        export.setpDApproving(export.getpDApproving() + e.getpDApproving());
        export.setpDPass(export.getpDPass() + e.getpDPass());
        export.setpDUnpass(export.getpDUnpass() + e.getpDUnpass());
        export.setpBTDApproving(export.getpBTDApproving() + e.getpBTDApproving());
        export.setpBTDPass(export.getpBTDPass() + e.getpBTDPass());
        export.setpBTDUnpass(export.getpBTDUnpass() + e.getpBTDUnpass());
        export.setpApproving(export.getpApproving() + e.getpApproving());
        export.setpPass(export.getpPass() + e.getpPass());
        export.setpUnpass(export.getpUnpass() + e.getpUnpass());

        export.setsBTApproving(export.getsBTApproving() + e.getsBTApproving());
        export.setsBTPass(export.getsBTPass() + e.getsBTPass());
        export.setsBTUnpass(export.getsBTUnpass() + e.getsBTUnpass());
        export.setsDApproving(export.getsDApproving() + e.getsDApproving());
        export.setsDPass(export.getsDPass() + e.getsDPass());
        export.setsDUnpass(export.getsDUnpass() + e.getsDUnpass());
        export.setsBTDApproving(export.getsBTDApproving() + e.getsBTDApproving());
        export.setsBTDPass(export.getsBTDPass() + e.getsBTDPass());
        export.setsBTDUnpass(export.getsBTDUnpass() + e.getsBTDUnpass());
        export.setsApproving(export.getsApproving() + e.getsApproving());
        export.setsPass(export.getsPass() + e.getsPass());
        export.setsUnpass(export.getsUnpass() + e.getsUnpass());

        export.setxBTApproving(export.getxBTApproving() + e.getxBTApproving());
        export.setxBTPass(export.getxBTPass() + e.getxBTPass());
        export.setxBTUnpass(export.getxBTUnpass() + e.getxBTUnpass());
        export.setxDApproving(export.getxDApproving() + e.getxDApproving());
        export.setxDPass(export.getxDPass() + e.getxDPass());
        export.setxDUnpass(export.getxDUnpass() + e.getxDUnpass());
        export.setxBTDApproving(export.getxBTDApproving() + e.getxBTDApproving());
        export.setxBTDPass(export.getxBTDPass() + e.getxBTDPass());
        export.setxBTDUnpass(export.getxBTDUnpass() + e.getxBTDUnpass());
        export.setxApproving(export.getxApproving() + e.getxApproving());
        export.setxPass(export.getxPass() + e.getxPass());
        export.setxUnpass(export.getxUnpass() + e.getxUnpass());

        export.setqBTApproving(export.getqBTApproving() + e.getqBTApproving());
        export.setqBTPass(export.getqBTPass() + e.getqBTPass());
        export.setqBTUnpass(export.getqBTUnpass() + e.getqBTUnpass());
        export.setqDApproving(export.getqDApproving() + e.getqDApproving());
        export.setqDPass(export.getqDPass() + e.getqDPass());
        export.setqDUnpass(export.getqDUnpass() + e.getqDUnpass());
        export.setqBTDApproving(export.getqBTDApproving() + e.getqBTDApproving());
        export.setqBTDPass(export.getqBTDPass() + e.getqBTDPass());
        export.setqBTDUnpass(export.getqBTDUnpass() + e.getqBTDUnpass());
        export.setqApproving(export.getqApproving() + e.getqApproving());
        export.setqPass(export.getqPass() + e.getqPass());
        export.setqUnpass(export.getqUnpass() + e.getqUnpass());
    }
}
