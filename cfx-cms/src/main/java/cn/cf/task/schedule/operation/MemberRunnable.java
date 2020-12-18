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
import cn.cf.dao.B2bMemberExtDao;
import cn.cf.dao.B2bRoleExtDao;
import cn.cf.dao.SysExcelStoreExtDao;
import cn.cf.dto.B2bMemberExtDto;
import cn.cf.dto.B2bRoleExtDto;
import cn.cf.dto.SysExcelStoreExtDto;
import cn.cf.entity.CustomerDataTypeParams;
import cn.cf.task.schedule.ScheduledFutureMap;
import cn.cf.util.DateUtil;

public class MemberRunnable implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(MemberRunnable.class);
    private String name;

    private String fileName = "";

    private String modelType = "";

    private Date insertTime;

    private String uuid;

    private SysExcelStoreExtDto storeDtoTemp;

    private SysExcelStoreExtDao storeDao;

    public MemberRunnable() {
    }

    public MemberRunnable(String name,String uuid) {
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

        B2bMemberExtDao b2bMemberExtDao = (B2bMemberExtDao) BeanUtils.getBean("b2bMemberExtDao");

        this.storeDao = (SysExcelStoreExtDao) BeanUtils.getBean("sysExcelStoreExtDao");

        if (storeDao != null) {
            Map<String, Object> map = ExportDoJsonParams.setSysExcelStore(Constants.ONE,"exportMembersList_"+StringUtils.defaultIfEmpty(this.uuid, ""));
            //查询存在要导出的任务
            List<SysExcelStoreExtDto> list = storeDao.getFirstTimeExcelStore(map);
            if (list != null && list.size() > Constants.ZERO) {
                for (SysExcelStoreExtDto storeDto : list) {
                    this.storeDtoTemp = storeDto;
                    CustomerDataTypeParams params = null;
                    if (CommonUtil.isNotEmpty(storeDto.getParams())) {
                         params = JSON.parseObject(storeDto.getParams(), CustomerDataTypeParams.class);
                        this.modelType = params.getModelType();
                        this.insertTime = storeDto.getInsertTime();
                        if (CommonUtil.isNotEmpty(this.modelType) && "1".equals(this.modelType)) {
                            this.fileName = "营销中心-客户管理-会员管理-" + storeDto.getAccountName() + "-" + DateUtil.formatYearMonthDayHMS(storeDto.getInsertTime());
                        } else {
                            this.fileName = "运营中心-客户管理-会员管理-" + storeDto.getAccountName() + "-" + DateUtil.formatYearMonthDayHMS(storeDto.getInsertTime());
                        }
                    }
                    if (b2bMemberExtDao != null) {
                        //设置查询参数
                        Map<String, Object> companyMap = memberListParams(params);
                        //判断不同公司类型，获取不同权限
                        if("1".equals(this.modelType)){
                            setMkMemberParams(storeDto.getAccountPk(), map);
                        }else{
                            setOperMemberParams(storeDto.getAccountPk(), map);
                        }
                        int counts = b2bMemberExtDao.searchB2bMemberCounts(companyMap);
                        String ossPath = "";
                        if (counts > Constants.EXCEL_NUMBER_TENHOUSAND) {
                            //大于10000，分页查询数据
                            ossPath = setLimitPages(companyMap, counts, b2bMemberExtDao, storeDto);
                        } else {
                            //如果小于或等于10000条直接上传
                            ossPath = setNotLimitPages(b2bMemberExtDao, companyMap);
                        }
                        //更新订单导出状态
                        ExportDoJsonParams.updateExcelStoreStatus(storeDto,storeDao, ossPath);
                    }
                }
            }
        }
    }

    private String setNotLimitPages(B2bMemberExtDao b2bMemberExtDao, Map<String, Object> memberMap) {
        String ossPath = "";
        List<B2bMemberExtDto> memberList = b2bMemberExtDao.searchB2bMemberList(memberMap);
        if (memberList != null && memberList.size() > Constants.ZERO) {
            //设置会员用户权限名称
            setMemberRole(memberList);
            ExportUtil<B2bMemberExtDto> exportUtil = new ExportUtil<>();
            String path = exportUtil.exportDynamicUtil("member", this.fileName, memberList);
            File file = new File(path);
            //上传到OSS
            ossPath = OSSUtils.ossUploadXls(file, Constants.FIVE);
        }
        return ossPath;
    }

    // 如果多余5000条数据分多个excel导出，并打包上传OSS
    private String setLimitPages(Map<String, Object> memberMap, double counts, B2bMemberExtDao b2bMemberExtDao, SysExcelStoreExtDto storeDto) {
        double numbers = Math.floor(counts / Constants.EXCEL_NUMBER_TENHOUSAND);//获取分页查询次数
        memberMap.put("limit", Constants.EXCEL_NUMBER_TENHOUSAND);
        List<File> fileList = new ArrayList<>();
        for (int i = Constants.ZERO; i < numbers + Constants.ONE; i++) { //
            int start = Constants.ZERO;
            if (i != Constants.ZERO) {
                start = ExportDoJsonParams.indexPageNumbers(i);
            }
            memberMap.put("start", start);
            //判断不同公司类型，获取不同数据
            List<B2bMemberExtDto> memberList = b2bMemberExtDao.searchB2bMemberList(memberMap);
            memberMap.remove("start");
            if (memberList != null && memberList.size() > Constants.ZERO) {
                ExportUtil<B2bMemberExtDto> exportUtil = new ExportUtil<>();
                String excelName = "";
                if (CommonUtil.isNotEmpty(this.modelType) && "1".equals(this.modelType)) {
                    excelName = "营销中心-客户管理-会员管理-" + storeDto.getAccountName() + "-" + DateUtil.formatYearMonthDayHMS(new Date())+"-"+(i+1);
                } else {
                    excelName = "运营中心-客户管理-会员管理-" + storeDto.getAccountName() + "-" + DateUtil.formatYearMonthDayHMS(new Date())+"-"+(i+1);
                }
                //设置会员用户权限名称
                setMemberRole(memberList);
                String path = exportUtil.exportDynamicUtil("member", excelName, memberList);
                File file = new File(path);
                fileList.add(file);
            }
        }
        //上传Zip到OSS
        return OSSUtils.ossUploadXls(new File(ZipUtils.fileToZip(fileList, this.fileName)), Constants.FILE);
    }


    //设置会员用户权限名称
    private void setMemberRole(List<B2bMemberExtDto> memberList){

        for (int i = 0; i < memberList.size(); i++) {
            String roleNames = "";
            B2bRoleExtDao roleDao = (B2bRoleExtDao) BeanUtils.getBean("b2bRoleExtDao");

            if (roleDao != null) {
                List<B2bRoleExtDto> roleList = roleDao
                        .searchRoleBymemberPk(memberList.get(i).getPk());
                boolean isSuperMember = false;
                if (roleList != null && roleList.size() > 0) {
                    for (int j = 0; j < roleList.size(); j++) {
                        if (roleList.get(j) != null) {
                            roleNames += roleList.get(j).getName() + ",";
                            if (roleList.get(j).getCompanyType() != null
                                    && roleList.get(j).getCompanyType() == 0) {
                                isSuperMember = true;
                            }
                        }
                    }
                }
                if (isSuperMember) {
                    memberList.get(i).setCompanyType(0);
                }
                if (!"".equals(roleNames)) {
                    memberList.get(i).setRoleName(
                            roleNames.substring(0, roleNames.length() - 1));
                }
            }
        }
    }


    private Map<String, Object> memberListParams(CustomerDataTypeParams params) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("insertTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.insertTime));
        if (params != null) {
            map.put("auditStatus", params.getAuditStatus());
            map.put("companyName", params.getCompanyName());
            map.put("mobile", params.getMobile());
            map.put("isVisable", params.getIsVisable());
            map.put("insertStartTime", params.getInsertStartTime());
            map.put("insertEndTime", params.getInsertEndTime());
            map.put("updateStartTime", params.getUpdateStartTime());
            map.put("updateEndTime", params.getUpdateEndTime());
            map.put("auditStartTime", params.getAuditStartTime());
            map.put("auditEndTime", params.getAuditEndTime());
            map.put("registerSource", params.getRegisterSource());
            map.put("level", params.getLevel());
            map.put("levelName", params.getLevelName());
            map.put("feedStartTime", params.getFeedStartTime());
            map.put("feedEndTime", params.getFeedEndTime());
        }
        return map;
    }

    /**
     * 运营中心-权限显示配置
     * @param accountPk
     * @param map
     */
    private void setOperMemberParams(String accountPk, Map<String, Object> map) {
        if(CommonUtil.isNotEmpty(accountPk)){
            if(!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.OPER_CM_MEMBER_COL_COMPANYNAME)){
                map.put("companyNameCol",ColAuthConstants.OPER_CM_MEMBER_COL_COMPANYNAME);
            }
            if(!CommonUtil.isExistAuthName(accountPk,ColAuthConstants.OPER_CM_MEMBER_COL_TEL)){
                map.put("memberTelCol",ColAuthConstants.OPER_CM_MEMBER_COL_TEL);
            }
            if(!CommonUtil.isExistAuthName(accountPk,ColAuthConstants.OPER_CM_MEMBER_COL_MEMBERNAME)){
                map.put("memberNameCol",ColAuthConstants.OPER_CM_MEMBER_COL_MEMBERNAME);
            }
            if(!CommonUtil.isExistAuthName(accountPk,ColAuthConstants.OPER_CM_MEMBER_COL_MEMBERACC)){
                map.put("memberAccCol",ColAuthConstants.OPER_CM_MEMBER_COL_MEMBERACC);
            }
        }
    }

    /**
     *  营销中心-权限显示配置
     * @param accountPk
     * @param map
     */
    private void setMkMemberParams(String accountPk, Map<String, Object> map) {
        if(CommonUtil.isNotEmpty(accountPk)){
            if(!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_CM_MEMBER_COL_COMPANYNAME)){
                map.put("companyNameCol",ColAuthConstants.MARKET_CM_MEMBER_COL_COMPANYNAME);
            }
            if(!CommonUtil.isExistAuthName(accountPk,ColAuthConstants.MARKET_CM_MEMBER_COL_TEL)){
                map.put("memberTelCol",ColAuthConstants.MARKET_CM_MEMBER_COL_TEL);
            }
            if(!CommonUtil.isExistAuthName(accountPk,ColAuthConstants.MARKET_CM_MEMBER_COL_MEMBERNAME)){
                map.put("memberNameCol",ColAuthConstants.MARKET_CM_MEMBER_COL_MEMBERNAME);
            }
            if(!CommonUtil.isExistAuthName(accountPk,ColAuthConstants.MARKET_CM_MEMBER_COL_MEMBERACC)){
                map.put("memberAccCol",ColAuthConstants.MARKET_CM_MEMBER_COL_MEMBERACC);
            }
        }
    }
}
