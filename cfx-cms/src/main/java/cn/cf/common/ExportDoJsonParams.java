package cn.cf.common;

import cn.cf.common.utils.BeanUtils;
import cn.cf.common.utils.CommonUtil;
import cn.cf.dao.*;
import cn.cf.dto.*;
import cn.cf.entity.CustomerDataTypeParams;
import cn.cf.entity.GoodsDataTypeParams;
import cn.cf.entity.OrderDataTypeParams;
import cn.cf.entity.SysExcelStoreExceptionEntity;
import cn.cf.model.SysExcelStore;
import cn.cf.util.KeyUtils;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExportDoJsonParams {


    /**
     * 处理商品管理导出参数，字段映射汉字
     *
     * @param params
     * @return
     */
    public static String doGoodsRunnableParams(GoodsDataTypeParams params, Date time) {

        String paramsName = "";

        String insertTime = new SimpleDateFormat("yyy-MM-dd").format(time);

        if (CommonUtil.isNotEmpty(params.getStoreName())) {
            paramsName += "店铺:" + params.getStoreName() + ";";
        }
        if (CommonUtil.isNotEmpty(params.getCompanyName())) {
            paramsName += "公司名称:" + params.getCompanyName() + ";";
        }
        if (CommonUtil.isNotEmpty(params.getBrandPk())) {
            B2bGoodsBrandExtDao b2bGoodsBrandExtDao = (B2bGoodsBrandExtDao) BeanUtils.getBean("b2bGoodsBrandExtDao");

                if (b2bGoodsBrandExtDao != null){
                    B2bGoodsBrandDto dto = b2bGoodsBrandExtDao.getByPk(params.getBrandPk());
                    if (dto != null){
                        paramsName += "厂家品牌:" + dto.getBrandName() + ";";
                    }
                }
        }

        if (CommonUtil.isNotEmpty(params.getMaterialName())) {
            paramsName += "物料名称:" + params.getMaterialName() + ";";
        }

        paramsName = setGoodsProperty(params, paramsName);

        if (CommonUtil.isNotEmpty(params.getSeedvarietyPk())) {
            B2bVarietiesExtDao varietiesExtDao = (B2bVarietiesExtDao) BeanUtils.getBean("b2bVarietiesExtDao");
            B2bVarietiesDto seVariDto = varietiesExtDao.getByPk(params.getSeedvarietyPk());
            if (seVariDto != null) {
                paramsName += "子品种:" + seVariDto.getName() + ";";
            }
        }

        try {
            if (CommonUtil.isNotEmpty(CommonUtil.checkUpdateExportTime(params.getInsertStartTime(), params.getInsertEndTime()))) {
                paramsName += "创建时间:" + CommonUtil.checkUpdateExportTime(params.getInsertStartTime(), params.getInsertEndTime()) + ";";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (CommonUtil.isNotEmpty(CommonUtil.checkUpdateExportTime(params.getUpStartTime(), params.getUpEndTime()))) {
                paramsName += "上架时间:" + CommonUtil.checkUpdateExportTime(params.getUpStartTime(), params.getUpEndTime()) + ";";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (CommonUtil.isNotEmpty(CommonUtil.checkUpdateExportTime(params.getUpdateStartTime(), params.getUpdateEndTime()))) {
                paramsName += "更新时间:" + CommonUtil.checkUpdateExportTime(params.getUpdateStartTime(), params.getUpdateEndTime()) + ";";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (CommonUtil.isNotEmpty(params.getBatchNumber())) {
            paramsName += "批号:" + params.getBatchNumber() + ";";
        }

        if (CommonUtil.isNotEmpty(params.getSeriesPk())) {
            B2bSpecExtDao b2bSpecExtDao = (B2bSpecExtDao) BeanUtils.getBean("b2bSpecExtDao");
            B2bSpecDto b2bSpecDto = b2bSpecExtDao.getByPk(params.getSeriesPk());
            if (b2bSpecDto != null) {
                paramsName += "规格系列:" + b2bSpecDto.getName() + ";";
            }
        }
        if (CommonUtil.isNotEmpty(params.getIsNewProduct())) {

            String isNewProductName = "";
            if ("0".equals(params.getIsNewProduct())) {
                isNewProductName = "是";
            } else {
                isNewProductName = "否";
            }
            paramsName += "是否显示:" + isNewProductName + ";";
        }

        if (CommonUtil.isNotEmpty(params.getIsUpdown())) {

            String isUpdownName = "";
            if ("1".equals(params.getIsUpdown())) {
                isUpdownName = "待上架";
            } else if ("2".equals(params.getIsUpdown())) {
                isUpdownName = "上架";
            } else if ("3".equals(params.getIsUpdown())) {
                isUpdownName = "下架";
            }
            paramsName += "上下架:" + isUpdownName + ";";
        }

        if (CommonUtil.isNotEmpty(params.getIsBlend())) {

            String isBlendName = "";
            if ("1".equals(params.getIsBlend())) {
                isBlendName = "是";
            } else if ("2".equals(params.getIsBlend())) {
                isBlendName = "否";
            }
            paramsName += "是否混纺:" + isBlendName + ";";
        }


        if (CommonUtil.isNotEmpty(params.getTechnologyPk())) {
            SxTechnologyDao sxTechnologyDao = (SxTechnologyDao) BeanUtils.getBean("sxTechnologyDao");
            if (sxTechnologyDao != null){
                SxTechnologyDto dto = sxTechnologyDao.getByPk(params.getTechnologyPk());
                if (dto != null) {
                    paramsName += "工艺:" + dto.getName() + ";";
                }
            }
        }



        if (CommonUtil.isNotEmpty(params.getRawMaterialParentPk())) {
            SxMaterialDao sxMaterialDao = (SxMaterialDao) BeanUtils.getBean("sxMaterialDao");
            if (sxMaterialDao != null){
                SxMaterialDto dto = sxMaterialDao.getByPk(params.getRawMaterialParentPk());
                if (dto != null) {
                    paramsName += "原料一级:" + dto.getName() + ";";
                }
            }
        }

        if (CommonUtil.isNotEmpty(params.getRawMaterialPk())) {
            SxMaterialDao sxMaterialDao = (SxMaterialDao) BeanUtils.getBean("sxMaterialDao");
            if (sxMaterialDao != null){
                SxMaterialDto dto = sxMaterialDao.getByPk(params.getRawMaterialPk());
                if (dto != null) {
                    paramsName += "原料二级:" + dto.getName() + ";";
                }
            }
        }



        //设置所属板块
//        paramsName = setBlock(params.getBlock(),paramsName);

        if (CommonUtil.isNotEmpty(params.getType())) {

            String typeName = "";
            if ("normal".equals(params.getType())) {
                typeName = "正常";
            } else if ("sale".equals(params.getType())) {
                typeName = "特卖";
            } else if ("presale".equals(params.getType())) {
                typeName = "预售";
            } else if ("auction".equals(params.getType())) {
                typeName = "竞拍";
            } else if ("binding".equals(params.getType())) {
                typeName = "拼团";
            }
            paramsName += "商品类型:" + typeName + ";";
        }

        return paramsName;
    }
    /**
     * 处理订单管理导出参数，字段映射汉字
     *
     * @param params
     * @return
     */
    public static String doOrderRunnableParams(OrderDataTypeParams params, Date time) {
        String orderParamsName = "";
        if (CommonUtil.isNotEmpty(params.getOrderNumber())) {
            orderParamsName += "订单编号:" + params.getOrderNumber() + ";";
        }
        if (CommonUtil.isNotEmpty(params.getPurchaserName())) {
            orderParamsName += "采购商名称:" + params.getPurchaserName() + ";";
        }
        try {
            if (CommonUtil.isNotEmpty(CommonUtil.checkUpdateExportTime(params.getInsertStartTime(), params.getInsertEndTime()))) {
                orderParamsName += "下单时间:" + CommonUtil.checkUpdateExportTime(params.getInsertStartTime(), params.getInsertEndTime()) + ";";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (CommonUtil.isNotEmpty(params.getStoreName())) {
            orderParamsName += "店铺:" + params.getStoreName() + ";";
        }
        if (CommonUtil.isNotEmpty(params.getSource())) {

            String sourceName = "";
            if ("1".equals(params.getSource())){
                sourceName = "pc端";
            } else if ("2".equals(params.getSource())){
                sourceName = "移动端";
            } else if ("3".equals(params.getSource())){
                sourceName = "app";
            }
            orderParamsName += "订单来源:" + sourceName + ";";
        }

        try {
            if (CommonUtil.isNotEmpty(CommonUtil.checkUpdateExportTime(params.getPaymentStartTime(), params.getPaymentEndTime()))) {
                orderParamsName += "支付时间:" + CommonUtil.checkUpdateExportTime(params.getPaymentStartTime(), params.getPaymentEndTime()) + ";";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (CommonUtil.isNotEmpty(CommonUtil.checkUpdateExportTime(params.getReceivablesStartTime(), params.getReceivablesEndTime()))) {
                orderParamsName += "收款时间:" + CommonUtil.checkUpdateExportTime(params.getReceivablesStartTime(), params.getReceivablesEndTime()) + ";";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (CommonUtil.isNotEmpty(params.getPaymentType())) {

            String payName = "";
            if ("1".equals(params.getPaymentType())){
                payName = "线下支付";
            } else if ("2".equals(params.getPaymentType())){
                payName = "余额支付";
            } else if ("3".equals(params.getPaymentType())){
                payName = "金融产品";
            }else if ("4".equals(params.getPaymentType())){
                payName = "线上支付";
            }else if ("5".equals(params.getPaymentType())){
                payName = "票据支付";
            }
            orderParamsName += "支付方式:" + payName + ";";
        }
        //设置所属板块
//        orderParamsName = setBlock(params.getBlock(),orderParamsName);

        if (CommonUtil.isNotEmpty(params.getOrderStatus())) {
            String payName = "";
            if ("0".equals(params.getOrderStatus())){
                payName = "待审核";
            } else if ("1".equals(params.getOrderStatus())){
                payName = "待付款";
            } else if ("2".equals(params.getOrderStatus())){
                payName = "待确认";
            }else if ("3".equals(params.getOrderStatus())){
                payName = "待发货";
            }else if ("4".equals(params.getOrderStatus())){
                payName = "已发货";
            }else if ("5".equals(params.getOrderStatus())){
                payName = "部分发货";
            }else if ("6".equals(params.getOrderStatus())){
                payName = "已完成";
            }else if ("-1".equals(params.getOrderStatus())){
                payName = "订单取消";
            }
            orderParamsName += "订单状态:" + payName + ";";
        }

        return orderParamsName;
    }

//    private static String setBlock(String block,String paramsName){
//        if (CommonUtil.isNotEmpty(block)) {
//            String blockName = "";
//            if ("cf".equals(block)) {
//                blockName = "化纤";
//            } else {
//                blockName = "纱线";
//            }
//            paramsName += "所属板块:" + blockName + ";";
//        }
//        return paramsName;
//    }


    public static void updateExcelStoreStatus(SysExcelStoreDto dto, SysExcelStoreExtDao storeDao, String ossPath) {
        if (storeDao != null) {
            SysExcelStoreDto storeDto = storeDao.getByPk(dto.getPk());
            SysExcelStore store = new SysExcelStore();
            org.springframework.beans.BeanUtils.copyProperties(storeDto, store);
            store.setIsDeal(Constants.ONE);
            if (CommonUtil.isNotEmpty(ossPath)) {
                store.setUrl(ossPath);
            } else {
                store.setUrl("");
            }
            storeDao.update(store);
        }
    }

    /**
     * Exception 对象转换成String
     * @param e
     * @return
     */
    public static String getErrorInfoFromException(Exception e) {
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            return "\r\n" + sw.toString() + "\r\n";
        } catch (Exception e2) {
            return "bad getErrorInfoFromException";
        }
    }
    public static void updateErrorExcelStoreStatus(SysExcelStoreExtDao storeDao,SysExcelStoreExtDto dto,String errorMsg) {
        if (storeDao != null && dto != null) {
            SysExcelStore store = new SysExcelStore();
            org.springframework.beans.BeanUtils.copyProperties(dto, store);
            store.setIsDeal(Constants.THREE);
            storeDao.update(store);
        }
        MongoTemplate mongoTemplate = (MongoTemplate) BeanUtils.getBean("mongoTemplate");
        SysExcelStoreExceptionEntity entry = new SysExcelStoreExceptionEntity();
        entry.setId(KeyUtils.getUUID());
        entry.setInsertTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        if (dto != null) {
            entry.setMethodName(dto.getMethodName());
        }
        entry.setErrorMsg(errorMsg);
        mongoTemplate.insert(entry);
    }


    public static String doPriceTreandRunnableParams(GoodsDataTypeParams params, Date time) {
        String paramsName = "";
        paramsName = setGoodsProperty(params, paramsName);
        try {
            if (CommonUtil.isNotEmpty(CommonUtil.checkUpdateExportTime(params.getPlatformUpdateTimeStart(), params.getPlatformUpdateTimeEnd()))) {
                paramsName += "平台更新时间:" + CommonUtil.checkUpdateExportTime(params.getPlatformUpdateTimeStart(), params.getPlatformUpdateTimeEnd()) + ";";
            }
            if (CommonUtil.isNotEmpty(CommonUtil.checkUpdateExportTime(params.getUpdateTimeStart(), params.getUpdateTimeEnd()))) {
                paramsName += "修改时间:" + CommonUtil.checkUpdateExportTime(params.getUpdateTimeStart(), params.getUpdateTimeEnd()) + ";";
            }
            if (CommonUtil.isNotEmpty(CommonUtil.checkUpdateExportTime(params.getDateStart(), params.getDateEnd()))) {
                paramsName += "日期:" + CommonUtil.checkUpdateExportTime(params.getDateStart(), params.getDateEnd()) + ";";
            }
            if (CommonUtil.isNotEmpty(CommonUtil.checkUpdateExportTime(params.getDateHistoryStart(), params.getDateHistoryEnd()))) {
                paramsName += "日期:" + CommonUtil.checkUpdateExportTime(params.getDateHistoryStart(), params.getDateHistoryEnd()) + ";";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (CommonUtil.isNotEmpty(params.getIsShowHome()) && !"-1".equals(params.getIsShowHome())) {
            String isShowName = "";
            if ("1".equals(params.getIsShowHome())){
                isShowName = "显示";
            }
            if ("2".equals(params.getIsShowHome())){
                isShowName = "不显示";
            }

            paramsName += "是否显示:" + isShowName + ";";
        }

        if (CommonUtil.isNotEmpty(params.getIsHome())) {
            String isName = "";
            if ("1".equals(params.getIsHome())){
                isName = "显示";
            }
            if ("2".equals(params.getIsHome())){
                isName = "不显示";
            }

            paramsName += "是否显示:" + isName + ";";
        }

        //纱线价格趋势查询字段
        if (CommonUtil.isNotEmpty(params.getTechnologyName())) {
            SxTechnologyDao sxTechnologyDao = (SxTechnologyDao) BeanUtils.getBean("sxTechnologyDao");
            if (sxTechnologyDao != null){
                SxTechnologyDto dto = sxTechnologyDao.getByPk(params.getTechnologyName());
                if (dto != null) {
                    paramsName += "工艺:" + dto.getName() + ";";
                }
            }
        }

        if (CommonUtil.isNotEmpty(params.getFirstMaterialName())) {
            SxMaterialDao sxMaterialDao = (SxMaterialDao) BeanUtils.getBean("sxMaterialDao");
            if (sxMaterialDao != null){
                SxMaterialDto dto = sxMaterialDao.getByPk(params.getFirstMaterialName());
                if (dto != null) {
                    paramsName += "原料一级:" + dto.getName() + ";";
                }
            }
        }

        if (CommonUtil.isNotEmpty(params.getSecondMaterialName())) {
            SxMaterialDao sxMaterialDao = (SxMaterialDao) BeanUtils.getBean("sxMaterialDao");
            if (sxMaterialDao != null){
                SxMaterialDto dto = sxMaterialDao.getByPk(params.getSecondMaterialName());
                if (dto != null) {
                    paramsName += "原料二级:" + dto.getName() + ";";
                }
            }
        }

        //设置所属板块
//        paramsName = setBlock(params.getBlock(),paramsName);

        return paramsName;
    }

    private static String setGoodsProperty(GoodsDataTypeParams params, String paramsName) {
        if (CommonUtil.isNotEmpty(params.getProductPk())) {
            B2bProductExtDao productExtDao = (B2bProductExtDao) BeanUtils.getBean("b2bProductExtDao");
            B2bProductDto productDto = productExtDao.getByPk(params.getProductPk());
            if (productDto != null) {
                paramsName += "品名:" + productDto.getName() + ";";
            }
        }
        if (CommonUtil.isNotEmpty(params.getProductName())) {
                paramsName += "品名:" + params.getProductName() + ";";
        }
        if (CommonUtil.isNotEmpty(params.getVarietiesPk())) {
            B2bVarietiesExtDao varietiesExtDao = (B2bVarietiesExtDao) BeanUtils.getBean("b2bVarietiesExtDao");
            B2bVarietiesDto variDto = varietiesExtDao.getByPk(params.getVarietiesPk());
            if (variDto != null) {
                paramsName += "品种:" + variDto.getName() + ";";
            }
        }

        if (CommonUtil.isNotEmpty(params.getVarietiesName())) {
                paramsName += "品种:" + params.getVarietiesName() + ";";
        }
        if (CommonUtil.isNotEmpty(params.getSpecPk())) {
            B2bSpecExtDao b2bSpecExtDao = (B2bSpecExtDao) BeanUtils.getBean("b2bSpecExtDao");
            B2bSpecDto b2bSpecDto = b2bSpecExtDao.getByPk(params.getSpecPk());
            if (b2bSpecDto != null) {
                paramsName += "规格大类:" + b2bSpecDto.getName() + ";";
            }
        }
        if (CommonUtil.isNotEmpty(params.getSpecName())) {
                paramsName += "规格大类:" + params.getSpecName() + ";";
        }
        if (CommonUtil.isNotEmpty(params.getBrandName())) {
            paramsName += "厂家品牌:" + params.getBrandName() + ";";
        }
        return paramsName;
    }


    /**
     * 处理借款单管理导出参数，字段映射汉字
     *
     * @param params
     * @return
     */
    public static String doCreditOrderRunnableParams(OrderDataTypeParams params, Date time) {
        String paramsName = "";
        if (CommonUtil.isNotEmpty(params.getOrderNumber())) {
            paramsName += "借款单号:" + params.getOrderNumber() + ";";
        }
        if (CommonUtil.isNotEmpty(params.getContractNumber())) {
            paramsName += "合同编号:" + params.getContractNumber() + ";";
        }
        if (CommonUtil.isNotEmpty(params.getLoanNumber())) {
            paramsName += "借据编号:" + params.getLoanNumber() + ";";
        }
        if (CommonUtil.isNotEmpty(params.getPurchaserName())) {
            paramsName += "采购商:" + params.getPurchaserName() + ";";
        }
        try {
            if (CommonUtil.isNotEmpty(CommonUtil.checkUpdateExportTime(params.getLoanStartTimeBegin(), params.getLoanStartTimeEnd()))) {
                paramsName += "放款开始时间:" + CommonUtil.checkUpdateExportTime(params.getLoanStartTimeBegin(), params.getLoanStartTimeEnd()) + ";";
            }
            if (CommonUtil.isNotEmpty(CommonUtil.checkUpdateExportTime(params.getLoanEndTimeBegin(), params.getLoanEndTimeEnd()))) {
                paramsName += "放款到期时间:" + CommonUtil.checkUpdateExportTime(params.getLoanEndTimeBegin(), params.getLoanEndTimeEnd()) + ";";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (CommonUtil.isNotEmpty(params.getEconomicsGoodsName())) {
            paramsName += "金融产品:" + params.getEconomicsGoodsName() + ";";
        }

        if (CommonUtil.isNotEmpty(params.getBankPk())) {
            B2bEconomicsBankExtDao bankDao = (B2bEconomicsBankExtDao) BeanUtils.getBean("b2bEconomicsBankExtDao");
            if (bankDao != null){
                B2bEconomicsBankDto dto = bankDao.getByPk(params.getBankPk());
                if (dto != null){
                    paramsName += "借贷银行:" + dto.getBankName() + ";";
                }
            }
        }
        if (CommonUtil.isNotEmpty(params.getLoanStatus())) {
            String payName = "";
            if ("1".equals(params.getLoanStatus())){
                payName = "待申请";
            } else if ("2".equals(params.getLoanStatus())){
                payName = "申请中";
            }else if ("3".equals(params.getLoanStatus())){
                payName = "申请成功";
            }else if ("4".equals(params.getLoanStatus())){
                payName = "申请失败";
            }else if ("5".equals(params.getLoanStatus())){
                payName = "已还款";
            }else if ("6".equals(params.getLoanStatus())){
                payName = "部分还款";
            }
            paramsName += "订单状态:" + payName + ";";
        }

        if (CommonUtil.isNotEmpty(params.getIsOverdue())) {
            String payName = "";
            if ("1".equals(params.getIsOverdue())){
                payName = "正常";
            } else if ("2".equals(params.getIsOverdue())){
                payName = "逾期";
            }
            paramsName += "是否逾期:" + payName + ";";
        }
        return paramsName;
    }

    /**
     * 处理客户信息导出参数，字段映射汉字
     *
     * @param params
     * @return
     */
    public static String doCustomerRunnableParams(CustomerDataTypeParams params, Date time) {
        String insertTime = new SimpleDateFormat("yyy-MM-dd").format(time);
        String paramsName = "";
        if (CommonUtil.isNotEmpty(params.getName())) {
            paramsName += "公司名称:" + params.getName() + ";";
        }
        if (CommonUtil.isNotEmpty(params.getCompanyName())) {
            paramsName += "公司名称:" + params.getCompanyName() + ";";
        }
        if (CommonUtil.isNotEmpty(params.getLevel())) {
            paramsName += "等级:" + params.getLevel() + ";";
        }
        if (CommonUtil.isNotEmpty(params.getLevelName())) {
            paramsName += "等级名称:" + params.getLevelName() + ";";
        }
        if (CommonUtil.isNotEmpty(params.getMobile())) {
            paramsName += "注册电话:" + params.getMobile() + ";";
        }
        if (CommonUtil.isNotEmpty(params.getContactsTel())) {
            paramsName += "电话:" + params.getContactsTel() + ";";
        }
        if (CommonUtil.isNotEmpty(params.getMemberName())) {
            paramsName += "姓名:" + params.getMemberName() + ";";
        }

        if (CommonUtil.isNotEmpty(params.getIsVisable())) {
            String isVisableName = "";
            if ("1".equals(params.getIsVisable())){
                isVisableName = "是";
            }
            if ("2".equals(params.getIsVisable())){
                isVisableName = "否";
            }
            paramsName += "是否启用:" + isVisableName + ";";
        }

        try {
            if (CommonUtil.isNotEmpty(CommonUtil.checkUpdateExportTime(params.getInsertStartTime(), params.getInsertEndTime()))) {
                paramsName += "注册时间:" + CommonUtil.checkUpdateExportTime(params.getInsertStartTime(), params.getInsertEndTime()) + ";";
            }
            if (CommonUtil.isNotEmpty(CommonUtil.checkUpdateExportTime(params.getUpdateStartTime(), params.getUpdateEndTime()))) {
                paramsName += "最后修改时间:" + CommonUtil.checkUpdateExportTime(params.getUpdateStartTime(), params.getUpdateEndTime()) + ";";
            }
            if (CommonUtil.isNotEmpty(CommonUtil.checkUpdateExportTime(params.getAuditStartTime(), params.getAuditEndTime()))) {
                paramsName += "审核时间:" + CommonUtil.checkUpdateExportTime(params.getAuditStartTime(), params.getAuditEndTime()) + ";";
            }
            if (CommonUtil.isNotEmpty(CommonUtil.checkUpdateExportTime(params.getFeedStartTime(), params.getFeedEndTime()))) {
                paramsName += "最后赠送时间:" + CommonUtil.checkUpdateExportTime(params.getFeedStartTime(), params.getFeedEndTime()) + ";";
            }

            } catch (Exception e) {
                e.printStackTrace();
        }
        if (CommonUtil.isNotEmpty(params.getOrganizationCode())) {
            paramsName += "统一社会信用代码:" + params.getOrganizationCode() + ";";
        }
        SysRegionsDao regionsDao = (SysRegionsDao) BeanUtils.getBean("sysRegionsDao");
        if (CommonUtil.isNotEmpty(params.getProvinceName())
                ||CommonUtil.isNotEmpty(params.getCityName())
                ||CommonUtil.isNotEmpty(params.getAreaName())){
            paramsName += "所在地区:";
            if (CommonUtil.isNotEmpty(params.getProvinceName())) {
                SysRegionsDto dto = regionsDao.getByPk(params.getProvinceName());//查询pk
                if (dto != null){
                    paramsName += dto.getName()+";";
                }
            }
            if (CommonUtil.isNotEmpty(params.getCityName())) {
                SysRegionsDto dto = regionsDao.getByPk(params.getCityName());//查询pk
                if (dto != null){
                    paramsName += dto.getName()+";";
                }
            }
            if (CommonUtil.isNotEmpty(params.getAreaName())) {
                SysRegionsDto dto = regionsDao.getByPk(params.getAreaName());//查询pk
                if (dto != null){
                    paramsName += dto.getName()+";";
                }
            }
        }
        if (CommonUtil.isNotEmpty(params.getAuditStatus())&& !"0".equals(params.getAuditStatus())) {
            String payName = "";
           if ("1".equals(params.getAuditStatus())){
                payName = "待审核";
            } else if ("2".equals(params.getAuditStatus())){
                payName = "审核通过";
            }else if ("3".equals(params.getAuditStatus())){
                payName = "审核不通过";
            }
            paramsName += "审核状态:" + payName + ";";
        }


        if (CommonUtil.isNotEmpty(params.getAuditSpStatus()) && !"0".equals(params.getAuditSpStatus())) {
            String payName = "";
            if ("1".equals(params.getAuditSpStatus())){
                payName = "待审核";
            } else if ("2".equals(params.getAuditSpStatus())){
                payName = "审核通过";
            }else if ("3".equals(params.getAuditSpStatus())){
                payName = "审核不通过";
            }
            paramsName += "审核状态:" + payName + ";";
        }

        if (CommonUtil.isNotEmpty(params.getRegisterSource())) {
            String sourceName = "";
            if ("1".equals(params.getRegisterSource())){
                sourceName = "pc端";
            } else if ("2".equals(params.getRegisterSource())){
                sourceName = "wap端";
            } else if ("3".equals(params.getRegisterSource())){
                sourceName = "app端";
            }
            paramsName += "注册平台:" + sourceName + ";";
        }

        return paramsName;
    }

    public static String doActivityRunnableParams(CustomerDataTypeParams params, Date time) {
        String paramsName ="";
        try {
            if (CommonUtil.isNotEmpty(CommonUtil.checkUpdateExportTime(params.getFeedStartTime(), params.getFeedEndTime()))) {
                paramsName += "最后赠送时间:" + CommonUtil.checkUpdateExportTime(params.getFeedStartTime(), params.getFeedEndTime()) + ";";
            }
            if (CommonUtil.isNotEmpty(CommonUtil.checkUpdateExportTime(params.getInsertTimeStart(), params.getInsertTimeEnd()))) {
                paramsName += "获奖时间:" + CommonUtil.checkUpdateExportTime(params.getInsertTimeStart(), params.getInsertTimeEnd()) + ";";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (CommonUtil.isNotEmpty(params.getAwardType())) {
            String awardName = "";
            if ("3".equals(params.getAwardType())){
                awardName = "再接再厉、明日再来";
            } else if ("1".equals(params.getAwardType())){
                awardName = "优惠券";
            } else if ("2".equals(params.getAwardType())){
                awardName = "实物";
            }
            paramsName += "奖品类型:" + awardName + ";";
        }

        if (CommonUtil.isNotEmpty(params.getIsAwardStatus()) && !"0".equals(params.getIsAwardStatus())) {
            String awardName = "";
            if ("1".equals(params.getIsAwardStatus())){
                awardName = "未发放";
            } else if ("2".equals(params.getIsAwardStatus())){
                awardName = "已发放";
            }
            paramsName += "发放状态:" + awardName + ";";
        }
        if (CommonUtil.isNotEmpty(params.getIsStatus()) && !"0".equals(params.getIsStatus())) {
            String awardName = "";
            if ("1".equals(params.getIsStatus())){
                awardName = "未中奖";
            } else if ("2".equals(params.getIsStatus())){
                awardName = "已中奖";
            }
            paramsName += "中奖状态:" + awardName + ";";
        }

        if (CommonUtil.isNotEmpty(params.getAwardStatus())) {
            String awardName = "";
            if ("3".equals(params.getAwardStatus())){
                awardName = "无";
            } else if ("1".equals(params.getAwardStatus())){
                awardName = "未发放";
            } else if ("2".equals(params.getAwardStatus())){
                awardName = "已发放";
            }
            paramsName += "奖品发放状态:" + awardName + ";";
        }
        if (CommonUtil.isNotEmpty(params.getActivityType())) {
            String activityName = "";
            if ("1".equals(params.getActivityType())){
                activityName = "抽奖";
            } else if ("2".equals(params.getActivityType())){
                activityName = "白条新客户见面礼";
            }else if ("3".equals(params.getActivityType())){
                activityName = "白条老客户尊享礼";
            }
            paramsName += "活动类型:" + activityName + ";";
        }
        if (CommonUtil.isNotEmpty(params.getMemberName())) {
            paramsName += "姓名:" + params.getMemberName() + ";";
        }
        if (CommonUtil.isNotEmpty(params.getAwardName())) {
            paramsName += "所获奖项:" + params.getAwardName() + ";";
        }
        if (CommonUtil.isNotEmpty(params.getMobile())) {
            paramsName += "注册手机号:" + params.getMobile() + ";";
        }
        return paramsName;
    }


    public static int indexPageNumbers(int index){
        return index*Constants.EXCEL_NUMBER_TENHOUSAND;
    }

    public static Map<String,Object> setSysExcelStore(Integer type,String methodName){
        Map<String, Object> map = new HashMap<>();
        map.put("isDeal", Constants.TWO);
        if (type != null) {
            map.put("type", type);
        }
        if (methodName.contains("_null")){
            String[] strings = methodName.split("_");
            map.put("methodName", strings[0]);
        }else{
            map.put("methodName", methodName);
        }
        return map;
    }
}
