package cn.cf.task.schedule.chemifiber;

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
import cn.cf.dao.B2bCompanyExtDao;
import cn.cf.dao.B2bGoodsExtDao;
import cn.cf.dao.ManageAuthorityExtDao;
import cn.cf.dao.SysExcelStoreExtDao;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bGoodsExtDto;
import cn.cf.dto.ManageAuthorityDto;
import cn.cf.dto.SysExcelStoreExtDto;
import cn.cf.entity.CfGoods;
import cn.cf.entity.GoodsDataTypeParams;
import cn.cf.model.ManageAccount;
import cn.cf.task.schedule.ScheduledFutureMap;
import cn.cf.util.DateUtil;

public class GoodsRunnable implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(GoodsRunnable.class);
    private String name;

    private String fileName;

    private String block;

    private String accountPk;

    private String rolePk;

    private Date insertTime;

    private String isOperBlock;
    private String uuid;

    private SysExcelStoreExtDto storeDtoTemp;

    private SysExcelStoreExtDao storeDao;

    private B2bCompanyExtDao b2bCompanyExtDao;

    private B2bGoodsExtDao b2bGoodsExtDao;

    private ManageAuthorityExtDao manageAuthorityExtDao;

    public GoodsRunnable() {
    }

    public GoodsRunnable(String name,String uuid) {
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

        this.storeDao = (SysExcelStoreExtDao) BeanUtils.getBean("sysExcelStoreExtDao");
        this.b2bGoodsExtDao = (B2bGoodsExtDao) BeanUtils.getBean("b2bGoodsExtDao");
        this.b2bCompanyExtDao = (B2bCompanyExtDao) BeanUtils.getBean("b2bCompanyExtDao");
        this.manageAuthorityExtDao = (ManageAuthorityExtDao) BeanUtils.getBean("manageAuthorityExtDao");
        if (this.storeDao != null) {
            Map<String, Object> map = ExportDoJsonParams.setSysExcelStore(Constants.THREE,"exportGoodsList_"+StringUtils.defaultIfEmpty(this.uuid, ""));
            //查询存在要导出的任务
            List<SysExcelStoreExtDto> list = this.storeDao.getFirstTimeExcelStore(map);
            if (list != null && list.size() > Constants.ZERO) {//只取一条数据
                for (SysExcelStoreExtDto storeDto : list) {
                    this.storeDtoTemp = storeDto;
                    String ossPath = "";
                    GoodsDataTypeParams params = null;
                    if (CommonUtil.isNotEmpty(storeDto.getParams())) {
                        params = JSON.parseObject(storeDto.getParams(), GoodsDataTypeParams.class);
                        this.block = params.getBlock();
                        this.accountPk = storeDto.getAccountPk();
                        this.rolePk = storeDto.getRolePk();
                        this.isOperBlock = params.getIsOperBlock();
                        this.insertTime = storeDto.getInsertTime();
                        this.uuid = storeDto.getMethodName();
                        if ("1".equals(this.isOperBlock)) {
                            this.fileName = "化纤中心-商品管理-" + storeDto.getAccountName() + "-" + DateUtil.formatYearMonthDayHMS(storeDto.getInsertTime());
                        } else {
                            this.fileName = "营销中心-商品管理-" + storeDto.getAccountName() + "-" + DateUtil.formatYearMonthDayHMS(storeDto.getInsertTime());
                        }
                    }

                    //设置查询参数
                    Map<String, Object> orderMap = getStringObjectMap(params);

                    int counts = b2bGoodsExtDao.searchGridExtCount(orderMap);
                    //设置权限
                    if ("1".equals(this.isOperBlock)) {
                        setGoodsAuthParams(orderMap);
                    } else {
                        setGoodsMkAuthParams(orderMap);
                    }
                    if (counts > Constants.EXCEL_NUMBER_TENHOUSAND) {
                        //大于5000，分页查询数据
                        ossPath = setLimitPages(orderMap, counts, storeDto);
                    } else {
                        //如果小于或等于5000条直接上传
                        ossPath = setNotLimitPages(orderMap, storeDto);
                    }
                    //更新订单导出状态
                    ExportDoJsonParams.updateExcelStoreStatus(storeDto,this.storeDao, ossPath);
                }
            }
        }
    }

    private String setNotLimitPages(Map<String, Object> orderMap, SysExcelStoreExtDto storeExtDto){
        String ossPath = "";
        ManageAccount account = new ManageAccount();
        account.setRolePk(this.rolePk);
        List<B2bGoodsExtDto> goodsList = b2bGoodsExtDao.searchGridExt(orderMap);
        if (goodsList != null && goodsList.size() > Constants.ZERO) {
            //设置权限
            setCompanyAuth(account, goodsList);
            ExportUtil<B2bGoodsExtDto> exportUtil = new ExportUtil<>();
            String path = exportUtil.exportDynamicUtil("goodsIsUpDown", this.fileName, goodsList);
            File file = new File(path);
            //上传到OSS
            ossPath = OSSUtils.ossUploadXls(file, Constants.FIVE);
        }
        return ossPath;
    }

    private void setCompanyAuth(ManageAccount account, List<B2bGoodsExtDto> goodsList){
        List<ManageAuthorityDto> setDtoList = manageAuthorityExtDao
                .getColManageAuthorityByRolePk(account.getRolePk());
        Map<String, String> checkMap = CommonUtil.checkColAuth(account, setDtoList);
        for (B2bGoodsExtDto dto : goodsList) {

            CfGoods cfGoods = JSON.parseObject(dto.getGoodsInfo(), CfGoods.class);
            if ("1".equals(this.isOperBlock)) {
                if (CommonUtil.isEmpty(checkMap.get(ColAuthConstants.OPER_GOODSMG_UPDOWN_COL_PLANTNAME))) {
                	
                    cfGoods.setPlantName(CommonUtil.hideCompanyName(cfGoods.getPlantName()));
                }
            } else {
                if (CommonUtil.isEmpty(checkMap.get(ColAuthConstants.MARKET_GOODSSTATISTIC_COL_PLANTNAME))) {
                    cfGoods.setPlantName(CommonUtil.hideCompanyName(cfGoods.getPlantName()));
                }
            }

            dto.setCfgoods(cfGoods);
        }
    }

    // 如果多余5000条数据分多个excel导出，并打包上传OSS
    private String setLimitPages(Map<String, Object> orderMap, double counts, SysExcelStoreExtDto storeDto) {
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
            List<B2bGoodsExtDto> goodsList = b2bGoodsExtDao.searchGridExt(orderMap);
            orderMap.remove("start");
            if (goodsList != null && goodsList.size() > Constants.ZERO) {
                //设置权限
                setCompanyAuth(account, goodsList);
                String excelName = "";
                if ("1".equals(this.isOperBlock)) {
                    excelName = "化纤中心-商品管理-" + storeDto.getAccountName() + "-" + DateUtil.formatYearMonthDayHMS(new Date()) + "-" + (i + 1);
                } else {
                    excelName = "营销中心-商品管理-" + storeDto.getAccountName() + "-" + DateUtil.formatYearMonthDayHMS(new Date()) + "-" + (i + 1);
                }
                ExportUtil<B2bGoodsExtDto> exportUtil = new ExportUtil<>();
                String path = exportUtil.exportDynamicUtil("goodsIsUpDown", excelName, goodsList);
                File file = new File(path);
                fileList.add(file);
            }
        }
        //上传Zip到OSS
        return OSSUtils.ossUploadXls(new File(ZipUtils.fileToZip(fileList, this.fileName)), Constants.FILE);
    }

    private Map<String, Object> getStringObjectMap(GoodsDataTypeParams params) {
        Map<String, Object> map = goodsListParams(params);
        // 查询总公司时通手机查询子公司的商品
        if (params != null && params.getCompanyPk() != null) {
            B2bCompanyDto coms = b2bCompanyExtDao.getByPk(params.getCompanyPk());
            String[] comPks = new String[1];
            if (coms != null && "-1".equals(coms.getParentPk())) {
                List<B2bCompanyDto> l = b2bCompanyExtDao.searchCompanyByParentAndChild(coms.getPk());
                comPks = new String[l.size()];
                for (int i = 0; i < l.size(); i++) {
                    comPks[i] = l.get(i).getPk();
                }
            } else {
                comPks[0] = params.getCompanyPk();
            }
            map.put("companyPk", comPks);
        }
        return map;
    }

    private void setGoodsAuthParams(Map<String, Object> map) {

        if (!CommonUtil.isExistAuthName(this.accountPk, ColAuthConstants.OPER_GOODSMG_UPDOWN_COL_STORENAME)) {
            map.put("storeNameCol", ColAuthConstants.OPER_GOODSMG_UPDOWN_COL_STORENAME);
        }
        if (!CommonUtil.isExistAuthName(this.accountPk, ColAuthConstants.OPER_GOODSMG_UPDOWN_COL_COMPANYNAME)) {
            map.put("companyNameCol", ColAuthConstants.OPER_GOODSMG_UPDOWN_COL_COMPANYNAME);
        }
        if (!CommonUtil.isExistAuthName(this.accountPk, ColAuthConstants.OPER_GOODSMG_UPDOWN_COL_BRANDNAME)) {
            map.put("brandNameCol", ColAuthConstants.OPER_GOODSMG_UPDOWN_COL_BRANDNAME);
        }
    }

    private void setGoodsMkAuthParams(Map<String, Object> map) {

        if (!CommonUtil.isExistAuthName(this.accountPk, ColAuthConstants.MARKET_GOODSSTATISTIC_COL_STORENAME)) {
            map.put("storeNameCol", ColAuthConstants.MARKET_GOODSSTATISTIC_COL_STORENAME);
        }
        if (!CommonUtil.isExistAuthName(this.accountPk, ColAuthConstants.MARKET_GOODSSTATISTIC_COL_COMPANYNAME)) {
            map.put("companyNameCol", ColAuthConstants.MARKET_GOODSSTATISTIC_COL_COMPANYNAME);
        }
        if (!CommonUtil.isExistAuthName(this.accountPk, ColAuthConstants.MARKET_GOODSSTATISTIC_COL_BRANDNAME)) {
            map.put("brandNameCol", ColAuthConstants.MARKET_GOODSSTATISTIC_COL_BRANDNAME);
        }
    }


    private Map<String, Object> goodsListParams(GoodsDataTypeParams params) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("isUpdown", params.getIsUpdown());
        map.put("storeName", params.getStoreName());
        map.put("brandName", params.getBrandName());
        map.put("brandPk", params.getBrandPk());
        map.put("insertStratTime", params.getInsertStartTime());
        map.put("brandName", params.getBrandName());// 厂家品牌
        map.put("productPk", params.getProductPk());// 品名
        map.put("varietiesPk", params.getVarietiesPk());// 品种
        map.put("seedvarietyPk", params.getSeedvarietyPk());// 子品种
        map.put("technologyPk", params.getTechnologyPk());
        map.put("rawMaterialPk", params.getRawMaterialPk());//二级原料
        map.put("rawMaterialParentPk", params.getRawMaterialParentPk());//一级原料
        map.put("insertStratTime", params.getInsertStartTime());// 创建时间
        map.put("insertEndTime", params.getInsertEndTime());
        map.put("updateStartTime", params.getUpdateStartTime());
        map.put("updateEndTime", params.getUpdateEndTime());
        map.put("specPk", params.getSpecPk());// 规格大类
     
        map.put("seriesPk", params.getSeriesPk());// 规格系列
        map.put("isDelete", Constants.ONE);
        map.put("type", params.getType());// 商品类型
        map.put("isNewProduct", params.getIsNewProduct());// 是否显示
        map.put("companyName", params.getCompanyName());
        map.put("isBlend", params.getIsBlend());
        map.put("materialName", params.getMaterialName());
        map.put("upStartTime", params.getUpStartTime());// 上架时间
        map.put("upEndTime", params.getUpEndTime());
        map.put("batchNumber", params.getBatchNumber());
        map.put("insertTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.insertTime));
        map.put("type", params.getType());
        map.put("block", params.getBlock());
        return map;
    }
}
