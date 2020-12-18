package cn.cf.task.schedule.yarn;

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
import cn.cf.dao.B2bGoodsExtDao;
import cn.cf.dao.ManageAuthorityExtDao;
import cn.cf.dao.SysExcelStoreExtDao;
import cn.cf.dto.B2bGoodsExtDto;
import cn.cf.dto.ManageAuthorityDto;
import cn.cf.dto.SysExcelStoreExtDto;
import cn.cf.entity.GoodsDataTypeParams;
import cn.cf.entity.SxGoodsExt;
import cn.cf.model.ManageAccount;
import cn.cf.task.schedule.ScheduledFutureMap;
import cn.cf.util.DateUtil;

public class YarnGoodsRunnable implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(YarnGoodsRunnable.class);
    private String name;

    private String fileName;

    private String block;

    private String accountPk;

    private String rolePk;

    private Date insertTime;

    private String uuid;

    private SysExcelStoreExtDto storeDtoTemp;

    private SysExcelStoreExtDao storeDao;

    private B2bGoodsExtDao b2bGoodsExtDao;

    private ManageAuthorityExtDao manageAuthorityExtDao;

    public YarnGoodsRunnable() {
    }

    public YarnGoodsRunnable(String name,String uuid) {
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
            ScheduledFutureMap.stopFuture(future,this.name);
        }
    }

    //上传到OSS
    public void upLoadFile() throws Exception {

        this.storeDao = (SysExcelStoreExtDao) BeanUtils.getBean("sysExcelStoreExtDao");
        this.b2bGoodsExtDao = (B2bGoodsExtDao) BeanUtils.getBean("b2bGoodsExtDao");
        this.manageAuthorityExtDao = (ManageAuthorityExtDao) BeanUtils.getBean("manageAuthorityExtDao");

        if (this.storeDao != null) {
            Map<String, Object> map = ExportDoJsonParams.setSysExcelStore(Constants.THREE,"exportSxGoodsList_"+StringUtils.defaultIfEmpty(this.uuid, ""));
            //查询存在要导出的任务
            List<SysExcelStoreExtDto> list = this.storeDao.getFirstTimeExcelStore(map);
            if (list != null && list.size() > Constants.ZERO) {
                for (SysExcelStoreExtDto storeDto : list) {
                    this.storeDtoTemp = storeDto;
                    GoodsDataTypeParams params = null;
                    if (CommonUtil.isNotEmpty(storeDto.getParams())) {
                        params = JSON.parseObject(storeDto.getParams(), GoodsDataTypeParams.class);
                        this.block = params.getBlock();
                        this.accountPk = storeDto.getAccountPk();
                        this.rolePk = storeDto.getRolePk();
                        this.insertTime = storeDto.getInsertTime();
                        this.fileName = "化纤中心-商品管理-" + storeDto.getAccountName() + "-" + DateUtil.formatYearMonthDayHMS(storeDto.getInsertTime());
                        }
                    //设置查询参数
                    Map<String, Object> orderMap = goodsListParams(params);
                    //设置权限
                    setGoodsAuthParams(orderMap);
                    String ossPath = "";
                    int counts  = b2bGoodsExtDao.searchSxGridExtCount(orderMap);
                    if (counts > Constants.EXCEL_NUMBER_TENHOUSAND) {
                        //大于5000，分页查询数据
                        ossPath = setLimitPages(orderMap, counts,storeDto);
                    } else {
                        //如果小于或等于5000条直接上传
                        ossPath = setNotLimitPages(orderMap);
                    }
                    //更新订单导出状态
                    ExportDoJsonParams.updateExcelStoreStatus(storeDto,this.storeDao, ossPath);
                }
            }
        }
    }

    private String setNotLimitPages(Map<String, Object> orderMap) {
        String ossPath = "";
        ManageAccount account = new ManageAccount();
        account.setRolePk(this.rolePk);
        List<B2bGoodsExtDto> goodsList = b2bGoodsExtDao.searchSxGridExt(orderMap);
        if (goodsList != null && goodsList.size() > Constants.ZERO) {
            //设置权限
            setCompanyAuth(account, goodsList);
            ExportUtil<B2bGoodsExtDto> exportUtil = new ExportUtil<>();
            String path = exportUtil.exportDynamicUtil("goodsManage",this.fileName, goodsList);
            File file = new File(path);
            //上传到OSS
            ossPath = OSSUtils.ossUploadXls(file, Constants.FIVE);
        }
        return ossPath;
    }

    private void setCompanyAuth(ManageAccount account, List<B2bGoodsExtDto> goodsList) {
        List<ManageAuthorityDto> setDtoList = manageAuthorityExtDao.getColManageAuthorityByRolePk(account.getRolePk());
        Map<String,String> checkMap = CommonUtil.checkColAuth(account,setDtoList);
        for (B2bGoodsExtDto dto : goodsList) {
            SxGoodsExt sxGoods = JSON.parseObject(dto.getGoodsInfo(), SxGoodsExt.class);
            if (CommonUtil.isEmpty(checkMap.get(ColAuthConstants.YARN_GOODS_MG_COL_PLANTNAME))) {
                sxGoods.setPlantName(CommonUtil.hideCompanyName(sxGoods.getPlantName()));
            }
            if (!CommonUtil.isNotEmpty(sxGoods.getCertificateName())){
                sxGoods.setCertificateName("无");
            }
            sxGoods.setIsBlendName(getIsblend(sxGoods.getIsBlend()));
            dto.setSxGoodsExt(sxGoods);
        }
        checkMap.clear();
    }
    private String getIsblend(Integer isBlend) {
        String str = "" ;
        if (isBlend!=null) {
            if (isBlend==1) {
                str = "是";
            }else if (isBlend==2) {
                str = "否";
            }
        }
        return str;
    }

    // 如果多余5000条数据分多个excel导出，并打包上传OSS
    private String setLimitPages(Map<String, Object> orderMap, double counts,SysExcelStoreExtDto storeDto) throws Exception {
        double numbers = Math.floor(counts / Constants.EXCEL_NUMBER_TENHOUSAND);//获取分页查询次数
        orderMap.put("limit", Constants.EXCEL_NUMBER_TENHOUSAND);
        List<File> fileList = new ArrayList<>();
        ManageAccount account = new ManageAccount();
        account.setRolePk(this.rolePk);
        for (int i = Constants.ZERO; i < numbers + Constants.ONE; i++) { //
            int start = Constants.ZERO;
            if (i != Constants.ZERO) {
                start = ExportDoJsonParams.indexPageNumbers(i);
            }
            orderMap.put("start", start);
            List<B2bGoodsExtDto> goodsList = b2bGoodsExtDao.searchSxGridExt(orderMap);
            orderMap.remove("start");
            if (goodsList != null && goodsList.size() > Constants.ZERO) {
                //设置权限
                setCompanyAuth(account, goodsList);
                String excelName = "纱线中心-商品管理-" + storeDto.getAccountName() + "-" + DateUtil.formatYearMonthDayHMS(new Date()) + "-" + (i + 1);
                ExportUtil<B2bGoodsExtDto> exportUtil = new ExportUtil<>();
                String path = exportUtil.exportDynamicUtil("goodsManage",excelName, goodsList);
                File file = new File(path);
                fileList.add(file);
            }
        }
        //上传Zip到OSS
        return OSSUtils.ossUploadXls(new File(ZipUtils.fileToZip(fileList, this.fileName)), Constants.FILE);
    }


    private void setGoodsAuthParams(Map<String, Object> map) {

        if (!CommonUtil.isExistAuthName(this.accountPk, ColAuthConstants.YARN_GOODS_MG_COL_STORENAME)) {
            map.put("storeNameCol", ColAuthConstants.YARN_GOODS_MG_COL_STORENAME);
        }
        if (!CommonUtil.isExistAuthName(this.accountPk,  ColAuthConstants.YARN_GOODS_MG_COL_COMPANYNAME)) {
            map.put("companyNameCol",  ColAuthConstants.YARN_GOODS_MG_COL_COMPANYNAME);
        }
        if (!CommonUtil.isExistAuthName(this.accountPk,   ColAuthConstants.YARN_GOODS_MG_COL_BRANDNAME)) {
            map.put("brandNameCol",   ColAuthConstants.YARN_GOODS_MG_COL_BRANDNAME);
        }
    }

    private Map<String, Object> goodsListParams(GoodsDataTypeParams params) {
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("isDelete", Constants.ONE);
        map.put("insertTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.insertTime));
        map.put("block", Constants.BLOCK_SX);
        if (params != null) {
            map.put("isUpdown", params.getIsUpdown());
            map.put("storeName", params.getStoreName());
            map.put("brandPk", params.getBrandPk());
            map.put("technologyPk", params.getTechnologyPk());
            map.put("rawMaterialPk", params.getRawMaterialPk());
            map.put("rawMaterialParentPk", params.getRawMaterialParentPk());
            map.put("insertStratTime", params.getInsertStartTime());
            map.put("insertEndTime", params.getInsertEndTime());
            map.put("updateStartTime", params.getUpdateStartTime());
            map.put("updateEndTime", params.getUpdateEndTime());
            map.put("type", params.getType());
            map.put("companyName", params.getCompanyName());
            map.put("upStartTime", params.getUpStartTime());
            map.put("upEndTime", params.getUpEndTime());
            map.put("batchNumber", params.getBatchNumber());
            map.put("materialName", params.getMaterialName());
            map.put("type", params.getType());
            map.put("isNewProduct", params.getIsNewProduct());
            map.put("isBlend", params.getIsBlend());
            map.put("specPk", params.getSpecPk());
        }
        return map;
    }
}
