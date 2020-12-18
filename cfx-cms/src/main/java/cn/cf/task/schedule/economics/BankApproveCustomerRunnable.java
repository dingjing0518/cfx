package cn.cf.task.schedule.economics;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

import cn.cf.property.PropertyConfig;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import cn.cf.common.Constants;
import cn.cf.common.ExportDoJsonParams;
import cn.cf.common.utils.BeanUtils;
import cn.cf.common.utils.CommonUtil;
import cn.cf.common.utils.OSSUtils;
import cn.cf.dao.SysExcelStoreExtDao;
import cn.cf.dto.SysExcelStoreDto;
import cn.cf.dto.SysExcelStoreExtDto;
import cn.cf.entity.EconCustBankMonthSumRF;
import cn.cf.entity.EconomicsCustomerBankReportForms;
import cn.cf.model.SysExcelStore;
import cn.cf.task.schedule.ScheduledFutureMap;
import cn.cf.util.DateUtil;
import net.sf.json.JSONObject;
import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.Configuration;
import net.sf.jxls.transformer.XLSTransformer;

public class BankApproveCustomerRunnable implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(BankApproveCustomerRunnable.class);
    private String name;

    private String fileName;

    private String years;

    private String bankPk;

    private String uuid;
    private SysExcelStoreExtDto storeDtoTemp;

    private MongoTemplate mongoTemplate;

    private SysExcelStoreExtDao storeDao;

    public BankApproveCustomerRunnable() {
    }


    public BankApproveCustomerRunnable(String name, String years, String bankPk,String uuid) {
        this.years = years;
        this.name = name;
        this.bankPk = bankPk;
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
            Map<String, Object> map = ExportDoJsonParams.setSysExcelStore(Constants.FOUR,"exportBankApproveCustomer_"+StringUtils.defaultIfEmpty(this.uuid, ""));
            //查询存在要导出的任务
            List<SysExcelStoreExtDto> list = this.storeDao.getFirstTimeExcelStore(map);
            if (list != null && list.size() > Constants.ZERO) {
                for (SysExcelStoreExtDto storeDto : list) {
                    this.storeDtoTemp = storeDto;
                    JSONObject obj = JSONObject.fromObject(storeDto.getParams());
                    if (obj.has("bankPk")){
                        this.bankPk = obj.get("bankPk").toString();
                    }
                    if (obj.has("years")){
                        this.years = obj.get("years").toString();
                    }

                    String ossPath = "";
                        this.fileName = "金融中心-数据管理-银行审批客户数-"+storeDto.getAccountName()+"-"+ DateUtil.formatYearMonthDayHMS(storeDto.getInsertTime());
                        if (this.mongoTemplate != null) {
                            //导出报表 更新订单导出状态
                            ossPath = setNotLimitPages();
                            updateExcelStoreStatus(storeDto, setNotLimitPages());
                    }
                    ExportDoJsonParams.updateExcelStoreStatus(storeDto,this.storeDao, ossPath);
                }
            }
        }
    }
    private String setNotLimitPages() throws Exception{

        Map<String, Object> map = getCUstomApprove();
        String path = exportEconomicsCustomer(
                "econCustBankRF",
                (List<EconomicsCustomerBankReportForms>) map.get("tempList"),
                (EconCustBankMonthSumRF) map.get("monthSumRF"));
            File file = new File(path);
            //上传到OSS
        return OSSUtils.ossUploadXls(file, Constants.FIVE);
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

    private Map<String, Object> getCUstomApprove(){
        Query query = new Query();
        query.addCriteria(Criteria.where("year").is(this.years).and("bankPk").is(this.bankPk));
        query.with(new Sort(new Sort.Order(Sort.Direction.ASC, "month")));
        List<EconomicsCustomerBankReportForms> list = this.mongoTemplate.find(query, EconomicsCustomerBankReportForms.class);
        EconCustBankMonthSumRF monthSumRF = new EconCustBankMonthSumRF();
        String[] timeList = Constants.ECONOMICS_CUSTOMER_TIME_LIST;
        Map<String, Object> map = new HashMap<String, Object>();
        List<EconomicsCustomerBankReportForms> tempList = new ArrayList<EconomicsCustomerBankReportForms>();

        if (list != null && list.size() > 0) {
            for (int i = 0; i < timeList.length; i++) {
                EconomicsCustomerBankReportForms rf = list.get(i);
                rf.setMonthName(timeList[i]);
                tempList.add(rf);
            }
            // 算出月份数量合计
            setSelfBankMonthSum(monthSumRF, tempList);
        }else{
            for (int i = 0; i < timeList.length; i++) {
                EconomicsCustomerBankReportForms nrf = new EconomicsCustomerBankReportForms();
                nrf.setMonthName(timeList[i]);
                tempList.add(nrf);
            }
        }
        monthSumRF.setName("合计");
        map.put("tempList", tempList);
        map.put("monthSumRF", monthSumRF);
        return map;
    }

    public String exportEconomicsCustomer(String template, List<EconomicsCustomerBankReportForms> list, EconCustBankMonthSumRF monthSumRF){

        URL url = this.getClass().getClassLoader().getResource("template");
        String templateFileNameFilePath = url.getPath() + "/" + template + ".xls"; // 模板路径
        String tempPath = PropertyConfig.getProperty("FILE_PATH");
        File file = new File(tempPath);
        if (!file.exists() && !file.isDirectory()) {
            file.mkdir();
        }
        String fileNames = this.fileName + ".xls";
        String destFileNamePath = tempPath + "/" + fileNames;// 生成文件路径
        Map<String, Object> beans = new HashMap<String, Object>();
        beans.put("dto", list);
        beans.put("dates", new Date());
        beans.put("counts", list.size());
        beans.put("monthSumRF", monthSumRF);

        Configuration config = new Configuration();
        XLSTransformer transformer = new XLSTransformer(config);
        FileInputStream is = null;
        try {
            transformer.transformXLS(templateFileNameFilePath, beans, destFileNamePath);
            is = new FileInputStream(destFileNamePath);
            is.close();
        } catch (ParsePropertyException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return destFileNamePath;
    }

    private void setSelfBankMonthSum(EconCustBankMonthSumRF monthSumRF, List<EconomicsCustomerBankReportForms> list) {

        // 本月的月份统计数量(做每种类型每种来源月份的合计计算)
        // 化纤白条
        int blankMonthSH = 0;
        int blankMonthPT = 0;
        int blankMonthXFM = 0;
        int blankMonthYX = 0;
        int blankMonthOther = 0;
        // 化纤贷
        int loanMonthSH = 0;
        int loanMonthPT = 0;
        int loanMonthXFM = 0;
        int loanMonthYX = 0;
        int loanMonthOther = 0;

        // 化纤白条+化纤贷
        int blaLoMonthSH = 0;
        int blaLoMonthPT = 0;
        int blaLoMonthXFM = 0;
        int blaLoMonthYX = 0;
        int blaLoMonthOther = 0;


        //每月累计相加，得出合计数据（数据不准）
        int blankAddUpMonthSH = 0;
        int blankAddUpMonthPT = 0;
        int blankAddUpMonthXFM = 0;
        int blankAddUpMonthYX = 0;
        int blankAddUpMonthOther = 0;
        // 化纤贷
        int loanAddUpMonthSH = 0;
        int loanAddUpMonthPT = 0;
        int loanAddUpMonthXFM = 0;
        int loanAddUpMonthYX = 0;
        int loanAddUpMonthOther = 0;

        // 化纤白条+化纤贷
        int blaLoAddUpMonthSH = 0;
        int blaLoAddUpMonthPT = 0;
        int blaLoAddUpMonthXFM = 0;
        int blaLoAddUpMonthYX = 0;
        int blaLoAddUpMonthOther = 0;

        if (list != null && list.size() > 0) {
            for (EconomicsCustomerBankReportForms ecoCustRF : list) {
                if (ecoCustRF.getTimes() != null && ecoCustRF.getTimes() == 4) {
                    // 每种类型每个来源每月合计
                    // 白条产品类型每月合计
                    blankMonthSH += ecoCustRF.getBlankSH() == null ? 0 : ecoCustRF.getBlankSH();
                    blankMonthPT += ecoCustRF.getBlankPT() == null ? 0 : ecoCustRF.getBlankPT();
                    blankMonthXFM += ecoCustRF.getBlankXFM() == null ? 0 : ecoCustRF.getBlankXFM();
                    blankMonthYX += ecoCustRF.getBlankYX() == null ? 0 : ecoCustRF.getBlankYX();
                    blankMonthOther += ecoCustRF.getBlankOther() == null ? 0 : ecoCustRF.getBlankOther();

                    // 化纤贷产品类型每月合计
                    loanMonthSH += ecoCustRF.getLoanSH() == null ? 0 : ecoCustRF.getLoanSH();
                    loanMonthPT += ecoCustRF.getLoanPT() == null ? 0 : ecoCustRF.getLoanPT();
                    loanMonthXFM += ecoCustRF.getLoanXFM() == null ? 0 : ecoCustRF.getLoanXFM();
                    loanMonthYX += ecoCustRF.getLoanYX() == null ? 0 : ecoCustRF.getLoanYX();
                    loanMonthOther += ecoCustRF.getLoanOther() == null ? 0 : ecoCustRF.getLoanOther();

                    // 化纤白条+化纤贷产品类型每月合计
                    blaLoMonthSH += ecoCustRF.getBlaLoSH() == null ? 0 : ecoCustRF.getBlaLoSH();
                    blaLoMonthPT += ecoCustRF.getBlaLoPT() == null ? 0 : ecoCustRF.getBlaLoPT();
                    blaLoMonthXFM += ecoCustRF.getBlaLoXFM() == null ? 0 : ecoCustRF.getBlaLoXFM();
                    blaLoMonthYX += ecoCustRF.getBlaLoYX() == null ? 0 : ecoCustRF.getBlaLoYX();
                    blaLoMonthOther += ecoCustRF.getBlaLoOther() == null ? 0 : ecoCustRF.getBlaLoOther();



                    //每月累计相加，得出合计数据（数据不准）
                    // 白条产品类型每月合计
                    blankAddUpMonthSH += ecoCustRF.getBlankAddUpSH() == null ? 0 : ecoCustRF.getBlankAddUpSH();
                    blankAddUpMonthPT += ecoCustRF.getBlankPT() == null ? 0 : ecoCustRF.getBlankAddUpPT();
                    blankAddUpMonthXFM += ecoCustRF.getBlankXFM() == null ? 0 : ecoCustRF.getBlankAddUpXFM();
                    blankAddUpMonthYX += ecoCustRF.getBlankAddUpYX() == null ? 0 : ecoCustRF.getBlankAddUpYX();
                    blankAddUpMonthOther += ecoCustRF.getBlankAddUpOther() == null ? 0 : ecoCustRF.getBlankAddUpOther();

                    // 化纤贷产品类型每月合计
                    loanAddUpMonthSH += ecoCustRF.getLoanAddUpSH() == null ? 0 : ecoCustRF.getLoanAddUpSH();
                    loanAddUpMonthPT += ecoCustRF.getLoanAddUpPT() == null ? 0 : ecoCustRF.getLoanAddUpPT();
                    loanAddUpMonthXFM += ecoCustRF.getLoanAddUpXFM() == null ? 0 : ecoCustRF.getLoanAddUpXFM();
                    loanAddUpMonthYX += ecoCustRF.getLoanAddUpYX() == null ? 0 : ecoCustRF.getLoanAddUpYX();
                    loanAddUpMonthOther += ecoCustRF.getLoanAddUpOther() == null ? 0 : ecoCustRF.getLoanAddUpOther();

                    // 化纤白条+化纤贷产品类型每月合计
                    blaLoAddUpMonthSH += ecoCustRF.getBlaLoAddUpSH() == null ? 0 : ecoCustRF.getBlaLoAddUpSH();
                    blaLoAddUpMonthPT += ecoCustRF.getBlaLoAddUpPT() == null ? 0 : ecoCustRF.getBlaLoAddUpPT();
                    blaLoAddUpMonthXFM += ecoCustRF.getBlaLoAddUpXFM() == null ? 0 : ecoCustRF.getBlaLoAddUpXFM();
                    blaLoAddUpMonthYX += ecoCustRF.getBlaLoAddUpYX() == null ? 0 : ecoCustRF.getBlaLoAddUpYX();
                    blaLoAddUpMonthOther += ecoCustRF.getBlaLoAddUpOther() == null ? 0 : ecoCustRF.getBlaLoAddUpOther();
                }
            }

            monthSumRF.setBlankAddUpMonthSH(blankAddUpMonthSH);
            monthSumRF.setBlankAddUpMonthXFM(blankAddUpMonthXFM);
            monthSumRF.setBlankAddUpMonthYX(blankAddUpMonthYX);
            monthSumRF.setBlankAddUpMonthPT(blankAddUpMonthPT);
            monthSumRF.setBlankAddUpMonthOther(blankAddUpMonthOther);
            monthSumRF.setBlankAddUpMonthCount(blankAddUpMonthSH+blankAddUpMonthXFM+blankAddUpMonthYX+blankAddUpMonthPT+blankAddUpMonthOther);

            monthSumRF.setLoanAddUpMonthSH(loanAddUpMonthSH);
            monthSumRF.setLoanAddUpMonthXFM(loanAddUpMonthXFM);
            monthSumRF.setLoanAddUpMonthYX(loanAddUpMonthYX);
            monthSumRF.setLoanAddUpMonthPT(loanAddUpMonthPT);
            monthSumRF.setLoanAddUpMonthOther(loanAddUpMonthOther);
            monthSumRF.setLoanAddUpMonthCount(loanAddUpMonthSH+loanAddUpMonthXFM+loanAddUpMonthYX+loanAddUpMonthPT+loanAddUpMonthOther);

            monthSumRF.setBlaLoAddUpMonthSH(blaLoAddUpMonthSH);
            monthSumRF.setBlaLoAddUpMonthXFM(blaLoAddUpMonthXFM);
            monthSumRF.setBlaLoAddUpMonthYX(blaLoAddUpMonthYX);
            monthSumRF.setBlaLoAddUpMonthPT(blaLoAddUpMonthPT);
            monthSumRF.setBlaLoAddUMonthpOther(blaLoAddUpMonthOther);
            monthSumRF.setBlaLoAddUpMonthCount(blaLoAddUpMonthSH+blaLoAddUpMonthXFM+blaLoAddUpMonthYX+blaLoAddUpMonthPT+blaLoAddUpMonthOther);
        }
        monthSumRF.setBlankSHMonthCount(blankMonthSH);
        monthSumRF.setBlankPTMonthCount(blankMonthPT);
        monthSumRF.setBlankXFMMonthCount(blankMonthXFM);
        monthSumRF.setBlankYXMonthCount(blankMonthYX);
        monthSumRF.setBlankOtherMonthCount(blankMonthOther);
        monthSumRF.setBlankMonthAllCount(blankMonthSH+blankMonthXFM+blankMonthYX+blankMonthPT+blankMonthOther);

        monthSumRF.setLoanSHMonthCount(loanMonthSH);
        monthSumRF.setLoanPTMonthCount(loanMonthPT);
        monthSumRF.setLoanXFMMonthCount(loanMonthXFM);
        monthSumRF.setLoanYXMonthCount(loanMonthYX);
        monthSumRF.setLoanOtherMonthCount(loanMonthOther);
        monthSumRF.setLoanMonthAllCount(loanMonthSH+loanMonthXFM+loanMonthYX+loanMonthPT+loanMonthOther);

        monthSumRF.setBlaLoSHMonthCount(blaLoMonthSH);
        monthSumRF.setBlaLoPTMonthCount(blaLoMonthPT);
        monthSumRF.setBlaLoXFMMonthCount(blaLoMonthXFM);
        monthSumRF.setBlaLoYXMonthCount(blaLoMonthYX);
        monthSumRF.setBlaLoOtherMonthCount(blaLoMonthOther);
        monthSumRF.setBlaLoMonthAllCount(blaLoMonthSH+blaLoMonthXFM+blaLoMonthYX+blaLoMonthPT+blaLoMonthOther);
    }
}
