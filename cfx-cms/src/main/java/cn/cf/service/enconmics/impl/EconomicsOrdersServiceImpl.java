package cn.cf.service.enconmics.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.cf.common.*;
import cn.cf.common.Constants;
import cn.cf.dao.*;
import cn.cf.entity.*;
import cn.cf.json.JsonUtils;
import cn.cf.model.SysExcelStore;
import cn.cf.property.PropertyConfig;
import cn.cf.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import cn.cf.PageModel;
import cn.cf.common.utils.CommonUtil;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bContractDto;
import cn.cf.dto.B2bContractExtDto;
import cn.cf.dto.B2bContractGoodsDto;
import cn.cf.dto.B2bContractGoodsExtDto;
import cn.cf.dto.B2bGoodsDto;
import cn.cf.dto.B2bLoanNumberDto;
import cn.cf.dto.B2bLoanNumberExtDto;
import cn.cf.dto.B2bManageRegionDto;
import cn.cf.dto.B2bOrderExtDto;
import cn.cf.dto.B2bOrderGoodsExtDto;
import cn.cf.dto.B2bPaymentDto;
import cn.cf.dto.MarketingPersonnelDto;
import cn.cf.model.B2bLoanNumber;
import cn.cf.model.ManageAccount;
import cn.cf.service.enconmics.EconomicsOrdersService;
import net.sf.json.JSONObject;

@Service
public class EconomicsOrdersServiceImpl implements EconomicsOrdersService {

    @Autowired
    private B2bPaymentExDao b2bPaymentExDao;

    @Autowired
    private B2bOrderExtDao orderExtDao;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private B2bOrderGoodsExtDao orderGoodsExtDao;

    @Autowired
    private B2bLoanNumberExtDao loanNumberExtDao;

    @Autowired
    private B2bContractExtDao b2bContractExtDao;

    @Autowired
    private B2bContractGoodsExtDao b2bContractGoodsExtDao;

    @Autowired
    private B2bCompanyExtDao b2bCompanyExtDao;

    @Autowired
    private B2bGoodsExtDao b2bGoodsExtDao;

    @Autowired
    private SysExcelStoreExtDao sysExcelStoreExtDao;
    
    @Autowired
    private MarketingOrderMemberExtDao orderMemberDao;
    
    @Autowired
    private MarketingPersonnelExtDao marketingPersonnelDao;
    
    @Autowired
    private B2bManageRegionDao b2bManageRegionDao;
    /**
     * 付款方式
     */
    @Override
    public List<B2bPaymentDto> getPaymentList() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("isVisable", 1);
        return b2bPaymentExDao.searchList(map);
    }

    /**
     * 授信订单列表
     */
    @Override
    public PageModel<B2bLoanNumberExtDto> creditOrderList(QueryModel<B2bLoanNumberExtDto> qm, Integer limitflag) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        PageModel<B2bLoanNumberExtDto> pm = new PageModel<B2bLoanNumberExtDto>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("orderNumber", qm.getEntity().getOrderNumber());
        map.put("contractNumber", qm.getEntity().getContractNumber());
        map.put("loanNumber", qm.getEntity().getLoanNumber());
        map.put("loanStartTimeBegin", qm.getEntity().getLoanStartTimeBegin());
        map.put("loanStartTimeEnd", qm.getEntity().getLoanStartTimeEnd());
        map.put("loanEndTimeBegin", qm.getEntity().getLoanEndTimeBegin());
        map.put("loanEndTimeEnd", qm.getEntity().getLoanEndTimeEnd());
        map.put("loanStatus", qm.getEntity().getLoanStatus());
        map.put("purchaserName", qm.getEntity().getPurchaserName());
        map.put("economicsGoodsType", qm.getEntity().getEconomicsGoodsType());
        map.put("economicsGoodsName", qm.getEntity().getEconomicsGoodsName());
     
        map.put("isOverdue", qm.getEntity().getIsOverdue());
        map.put("bankPk", qm.getEntity().getBankPk());
        if(!StringUtils.isBlank(qm.getFirstOrderName()) && qm.getFirstOrderName().equals("isOverdueName")){
            map.put("orderName", "isOverdue");
        }else{
         map.put("orderName", qm.getFirstOrderName());
        }
        map.put("orderType", qm.getFirstOrderType());

        map.put("colPurName", CommonUtil.isExistAuthName(qm.getEntity().getAccountPk(),
                ColAuthConstants.EM_CREDIT_ORDER_LOANBILL_COL_PURNAME));
        map.put("colSuppName", CommonUtil.isExistAuthName(qm.getEntity().getAccountPk(),
                ColAuthConstants.EM_CREDIT_ORDER_LOANBILL_COL_SUPPNAME));

        // 是否显示分页字段
        if (limitflag == 1) {
            map.put("start", qm.getStart());
            map.put("limit", qm.getLimit());
        }
        List<B2bLoanNumberExtDto> list = orderExtDao.searchCreditOrderList(map);
        // 计算相关利息
        for (B2bLoanNumberExtDto dto : list) {
            if (dto.getLoanStatus() != null
                    && (dto.getLoanStatus() == 5 || dto.getLoanStatus() == 6 || dto.getLoanStatus() == 3)
                    && dto.getLoanStartTime() != null && !dto.getLoanStartTime().equals("")) {
                Double loanAmount = dto.getLoanAmount() == null ? 0.0 : dto.getLoanAmount();
                Double principal = dto.getPrincipal() == null ? 0.0 : dto.getPrincipal();
                // 未还银行的本金
                Double repayingBank = ArithUtil.sub(loanAmount, principal);
                Double totalRate = dto.getTotalRate() == null ? 0.0 : dto.getTotalRate();
                Double bankRate = dto.getBankRate() == null ? 0.0 : dto.getBankRate();
                Double platformRate = ArithUtil.sub(totalRate, bankRate);
                dto.setPlatformRate(platformRate);
                if (repayingBank<0) {//还款金额大于放款金额的时候，应该是0
                	 dto.setRepayingInterest(0d);
	                 dto.setRepayingSerCharge(0d);
	                 dto.setAmountPayable(0d);
				}else{
					String loanStartTime = sdf.format(dto.getLoanStartTime());
	                String nowTime = sdf.format(new Date());
	                // String loanEndTime = sdf.format(dto.getLoanEndTime());
	                Double sevenRate = dto.getSevenRate() == null ? 0.0 : dto.getSevenRate();
	     
	                int type = 0;
	                if (dto.getEconomicsGoodsType() != null && (dto.getEconomicsGoodsType() == 2||dto.getEconomicsGoodsType() == 4)) {// 化纤贷
	                    type = 2;
	                } else if (dto.getEconomicsGoodsType() != null && (dto.getEconomicsGoodsType() == 1||dto.getEconomicsGoodsType() == 3)) {
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
                
            }
        }
        int counts = orderExtDao.searchCreditOrders(map);
        pm.setDataList(list);

        pm.setTotalCount(counts);
        return pm;
    }

    @Override
    public int saveCreditOrderToOss(OrderDataTypeParams params, ManageAccount account) {
        Date time = new Date();
            String timeStr = new SimpleDateFormat("yyyy-MM-dd").format(time);
        try {
            Map<String, String> map = CommonUtil.checkExportTime(params.getLoanStartTimeBegin(), params.getLoanStartTimeEnd(), timeStr);
            params.setLoanStartTimeBegin(map.get("startTime"));
            params.setLoanStartTimeEnd(map.get("endTime"));
            Map<String, String> updateMap = CommonUtil.checkExportTime(params.getLoanEndTimeBegin(), params.getLoanEndTimeEnd(), timeStr);
            params.setLoanEndTimeBegin(updateMap.get("startTime"));
            params.setLoanEndTimeEnd(updateMap.get("endTime"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String json = JsonUtils.convertToString(params);
        SysExcelStore store = new SysExcelStore();
        store.setPk(KeyUtils.getUUID());
        store.setMethodName("exportCreditOrders_"+params.getUuid());
        store.setParams(json);
        store.setParamsName(ExportDoJsonParams.doCreditOrderRunnableParams(params,time));
        store.setIsDeal(Constants.TWO);
        store.setInsertTime(new Date());
        store.setName("金融中心-授信管理-订单管理-借款单管理");
        store.setType(Constants.TWO);
        store.setAccountPk(account.getPk());
        return sysExcelStoreExtDao.insert(store);
    }

    @Override
    public PageModel<B2bContractGoodsExtDto> contractGoodsList(QueryModel<B2bContractGoodsExtDto> qm) {
        PageModel<B2bContractGoodsExtDto> pm = new PageModel<B2bContractGoodsExtDto>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("contractNo", qm.getEntity().getContractNo());
        map.put("orderName", qm.getFirstOrderName());
        map.put("orderType", qm.getFirstOrderType());
        map.put("start", qm.getStart());
        map.put("limit", qm.getLimit());
        int counts = b2bContractGoodsExtDao.searchGridCountExt(map);
        List<B2bContractGoodsExtDto> list = b2bContractGoodsExtDao.searchGridExt(map);
        if (counts > 0) {
            for (B2bContractGoodsExtDto dto : list) {
                if (qm.getEntity().getBlock().equals(Constants.BLOCK_CF)) {
                    CfGoods cfGoods = JSON.parseObject(dto.getGoodsInfo(), CfGoods.class);
                    dto.setCfGoods(cfGoods);
                    dto.setBlock(Constants.BLOCK_CF);
                } else {
                    SxGoods sxGoods = JSON.parseObject(dto.getGoodsInfo(), SxGoods.class);
                    dto.setSxGoods(sxGoods);
                    dto.setBlock(Constants.BLOCK_SX);
                }
            }
        }
        pm.setDataList(list);
        pm.setTotalCount(counts);
        return pm;
    }

    @Override
    public PageModel<B2bContractExtDto> contractList(QueryModel<B2bContractExtDto> qm, ManageAccount account) {
        PageModel<B2bContractExtDto> pm = new PageModel<B2bContractExtDto>();
       Map<String, Object> map  = getStringObjectMap(qm);
        int counts = b2bContractExtDao.searchGridCountExt(map);
        String block = qm.getEntity().getBlock();
        if (Constants.BLOCK_CF.equals(block)) {
            setCFColContractParams(map, account);
        }
        if (Constants.BLOCK_SX.equals(block)) {
            setSXColContractParams(map, account);
        }
        List<B2bContractExtDto> list = b2bContractExtDao.searchGridExt(map);
        // 查询付款凭证
        if (Constants.BLOCK_CF.equals(block)
                && CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.OPER_CONTRACTMG_COL_VOUCHER)) {
            setPayVoucher(list);
        }
        if (Constants.BLOCK_SX.equals(block) && CommonUtil.isExistAuthName(account.getPk(),
                ColAuthConstants.YARN_ORDER_CONTRACTMG_COL_VOUCHER)) {
            setPayVoucher(list);
        }
        if (list != null && list.size() > 0) {
            for (B2bContractExtDto extDto : list) {
                if (Constants.BLOCK_CF.equals(block)){
                    OrderExportUtil.setContractParams(account, extDto);
                }
                if (Constants.BLOCK_SX.equals(block)) {
                    OrderExportUtil.setContractSxParams(account, extDto);
                }
            }
        }
        pm.setDataList(list);
        pm.setTotalCount(counts);
        return pm;
    }

    @Override
    public List<OrderRecord> contractTrackList(String contractNo) {
        Query query = Query.query(Criteria.where("orderNumber").is(contractNo));
        query.with(new Sort(new Sort.Order(Direction.DESC, "insertTime")));
        return mongoTemplate.find(query,OrderRecord.class);
    }

    /**
     * 设置付款凭证URL
     *
     * @param list
     */
    private void setPayVoucher(List<B2bContractExtDto> list) {
        if (list != null && list.size() > 0) {
            for (B2bContractExtDto extDto : list) {
                Query query = Query.query(Criteria.where("orderNumber").is(extDto.getContractNo()));
                List<B2bPayVoucher> rfList = mongoTemplate.find(query, B2bPayVoucher.class);
                if (rfList != null && rfList.size() > 0) {
                    List<String> vList = new ArrayList<>();
                    for (B2bPayVoucher payv : rfList) {
                        vList.add(payv.getUrl());
                    }
                    extDto.setPayVoucherUrl(vList);
                }
            }
        }
    }

    /**
     * 化纤合同显示列权限控制
     *
     * @param map
     * @param account
     */
    private void setCFColContractParams(Map<String, Object> map, ManageAccount account) {
        if (account != null) {
            if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.OPER_CONTRACTMG_COL_STORENAME)) {
                map.put("storeNameCol", ColAuthConstants.OPER_CONTRACTMG_COL_STORENAME);
            }
        }
    }

    /**
     * 纱线合同显示列权限控制
     *
     * @param map
     * @param account
     */
    private void setSXColContractParams(Map<String, Object> map, ManageAccount account) {
        if (account != null) {
            if (!CommonUtil.isExistAuthName(account.getPk(),
                    ColAuthConstants.YARN_ORDER_CONTRACTMG_COL_STORENAME)) {
                map.put("storeNameCol", ColAuthConstants.YARN_ORDER_CONTRACTMG_COL_STORENAME);
            }
        }
    }

    @Override
    public int updateContract(B2bContractExtDto dto) {

        B2bContractGoodsExtDto goodsDto = new B2bContractGoodsExtDto();
        goodsDto.setContractNo(dto.getContractNo());
        goodsDto.setContractStatus(dto.getContractStatus());
        int count = 0;
        count += b2bContractGoodsExtDao.updateContractGoods(goodsDto);
        count += b2bContractExtDao.updateContract(dto);
        if (count >= 2){
            cn.cf.entity.OrderRecord orderRecord = new cn.cf.entity.OrderRecord();
            orderRecord.setId(KeyUtils.getUUID());
            orderRecord.setInsertTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            String content ="合同取消,合同单号："+dto.getContractNo()+",关闭原因："+dto.getCloseReason();
            orderRecord.setContent(content);
            orderRecord.setOrderNumber(dto.getContractNo());
            orderRecord.setStatus(Constants.MINUS_ONE);
            mongoTemplate.insert(orderRecord);
        }
        return count;
    }

    /**
     * 还款记录
     */
    @Override
    public PageModel<B2bRepaymentRecord> getB2bRepaymentRecordList(QueryModel<B2bRepaymentRecord> qm) {
        Criteria c = new Criteria();
        PageModel<B2bRepaymentRecord> pm = new PageModel<B2bRepaymentRecord>();
        c.andOperator(Criteria.where("orderNumber").is(qm.getEntity().getOrderNumber()));
        Query query = new Query(c);
        query.with(new Sort(Direction.DESC, "createTime"));
        query.skip(qm.getStart()).limit(qm.getLimit());
        List<B2bRepaymentRecord> list = mongoTemplate.find(query, B2bRepaymentRecord.class);
        Long counts = mongoTemplate.count(query, B2bRepaymentRecord.class);
        pm.setDataList(list);
        pm.setTotalCount(counts.intValue());
        return pm;
    }

    /**
     * 化纤贷订单列表
     *
     * @param qm
     * @param limitFlage
     * @return
     */
    @Override
    public PageModel<B2bOrderExtDto> fiberLoanOrderList(QueryModel<B2bOrderExtDto> qm, int limitFlage) {
        PageModel<B2bOrderExtDto> pm = new PageModel<B2bOrderExtDto>();
        /*
         * Map<String, Object> map = new HashMap<String, Object>();
         * List<B2bPaymentDto> paymentList =
         * b2bPaymentExDao.getPaymentByInType(4, 5); if (paymentList != null &&
         * paymentList.size() > 0) { map.put("paymentPks", paymentList); }
         * map.put("mobile", qm.getEntity().getMobile()); map.put("orderNumber",
         * qm.getEntity().getOrderNumber()); map.put("iouNumber",
         * qm.getEntity().getIouNumber()); map.put("contractNumber",
         * qm.getEntity().getContractNumber()); map.put("loanStartTimeBegin",
         * qm.getEntity().getLoanStartTimeBegin()); map.put("loanStartTimeEnd",
         * qm.getEntity().getLoanStartTimeEnd() // map.put("repaymentStatus",
         * qm.getEntity().getRepaymentStatus()); map.put("orderName",
         * qm.getFirstOrderName()); map.put("orderType",
         * qm.getFirstOrderType()); if (limitFlage == 1) { map.put("start",
         * qm.getStart()); map.put("limit", qm.getLimit()); }
         * List<B2bOrderExtDto> list = orderExtDao.searchCreditOrderList(map);
         * int counts = orderExtDao.searchCreditOrders(map);
         * pm.setDataList(list); pm.setTotalCount(counts);
         */
        return pm;
    }

    /**
     * 授信管理-订单管理 订单列表
     *
     * @param qm
     * @return
     */
    @Override
    public PageModel<B2bOrderExtDto> searchOrderList(QueryModel<B2bOrderExtDto> qm, ManageAccount account) {
        PageModel<B2bOrderExtDto> pm = new PageModel<B2bOrderExtDto>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("start", qm.getStart());
        map.put("limit", qm.getLimit());
        map.put("orderName", qm.getFirstOrderName());
        map.put("orderType", qm.getFirstOrderType());
        map.put("orderNumber", qm.getEntity().getOrderNumber());
        // map.put("purchaserName", qm.getEntity().getPurchaserName());
        // map.put("supplierName", qm.getEntity().getSupplierName());
        map.put("purchaseType", qm.getEntity().getPurchaseType());
        map.put("storeName", qm.getEntity().getStoreName());
        map.put("insertStartTime", qm.getEntity().getInsertStartTime());
        map.put("insertEndTime", qm.getEntity().getInsertEndTime());
        map.put("paymentName", qm.getEntity().getPaymentName());
        map.put("orderStatus", qm.getEntity().getOrderStatus());
        map.put("memberPk", qm.getEntity().getMemberPk());
        map.put("source", qm.getEntity().getSource());
        map.put("paymentStartTime", qm.getEntity().getPaymentStartTime());
        map.put("paymentEndTime", qm.getEntity().getPaymentEndTime());
        map.put("paymentType", qm.getEntity().getPaymentType());
        if (account != null) {
            if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.EM_CREDIT_ORDER_ORDERMG_COL_PURNAME)) {
                map.put("purchaserNameCol", ColAuthConstants.EM_CREDIT_ORDER_ORDERMG_COL_PURNAME);
            }
            if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.EM_CREDIT_ORDER_ORDERMG_COL_STORENAME)) {
                map.put("storeNameCol", ColAuthConstants.EM_CREDIT_ORDER_ORDERMG_COL_STORENAME);
            }
            if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.EM_CREDIT_ORDER_ORDERMG_COL_ADDRESS)) {
                map.put("addressCol", ColAuthConstants.EM_CREDIT_ORDER_ORDERMG_COL_ADDRESS);
            }
            if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.EM_CREDIT_ORDER_ORDERMG_COL_CONTACTS)) {
                map.put("contactNameCol", ColAuthConstants.EM_CREDIT_ORDER_ORDERMG_COL_CONTACTS);
            }
            if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.EM_CREDIT_ORDER_ORDERMG_COL_SUPNAME)) {
                map.put("supplierNameCol", ColAuthConstants.EM_CREDIT_ORDER_ORDERMG_COL_SUPNAME);
            }
        }
        int totalCount = orderExtDao.searchGridExtCount(map);
        List<B2bOrderExtDto> list = orderExtDao.searchGridExt(map);
        // 判断如果选择了付款凭证权限，则不显示
        if (CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.EM_CREDIT_ORDER_ORDERMG_COL_VOUCHERLIST)) {
            for (B2bOrderExtDto d : list) {
                Criteria criatira = new Criteria();
                criatira.andOperator(Criteria.where("orderNumber").is(d.getOrderNumber()),
                        Criteria.where("type").is(1));
                List<B2bPayVoucher> imgUrls = mongoTemplate.find(new Query(criatira), B2bPayVoucher.class);
                if (null != imgUrls && imgUrls.size() != 0) {
                    d.setVoucherList(imgUrls);
                }
            }
        }
        pm.setTotalCount(totalCount);
        pm.setDataList(list);
        return pm;
    }

    /**
     * 授信管理-订单管理 导出订单
     *
     * @param qm
     * @return
     */
    @Override
    public List<B2bOrderExtDto> exportOrderListNew(QueryModel<B2bOrderExtDto> qm, ManageAccount account) {
        List<B2bOrderExtDto> list = new ArrayList<B2bOrderExtDto>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("orderName", qm.getFirstOrderName());
        map.put("orderType", qm.getFirstOrderType());
        map.put("orderNumber", qm.getEntity().getOrderNumber());
        // map.put("purchaserName", qm.getEntity().getPurchaserName());
        // map.put("supplierName", qm.getEntity().getSupplierName());
        map.put("purchaseType", qm.getEntity().getPurchaseType());
        map.put("storeName", qm.getEntity().getStoreName());
        map.put("insertStartTime", qm.getEntity().getInsertStartTime());
        map.put("insertEndTime", qm.getEntity().getInsertEndTime());
        map.put("orderStatus", qm.getEntity().getOrderStatus());
        map.put("memberPk", qm.getEntity().getMemberPk());
        map.put("source", qm.getEntity().getSource());
        map.put("paymentStartTime", qm.getEntity().getPaymentStartTime());
        map.put("paymentEndTime", qm.getEntity().getPaymentEndTime());
        map.put("paymentType", qm.getEntity().getPaymentType());
        if (account != null) {
            if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.EM_CREDIT_ORDER_ORDERMG_COL_PURNAME)) {
                map.put("purchaserNameCol", ColAuthConstants.EM_CREDIT_ORDER_ORDERMG_COL_PURNAME);
            }
            if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.EM_CREDIT_ORDER_ORDERMG_COL_SUPNAME)) {
                map.put("supplierNameCol", ColAuthConstants.EM_CREDIT_ORDER_ORDERMG_COL_SUPNAME);
            }
        }
        list = orderExtDao.searchOrderGoodsList(map);
        List<B2bOrderExtDto> listTemp = new ArrayList<B2bOrderExtDto>();
        if (list != null && list.size() > 0) {
            for (B2bOrderExtDto b2bOrderDto : list) {
                if (b2bOrderDto.getDisType() != null && !"".equals(b2bOrderDto.getDisType())) {
                    b2bOrderDto.setBoxes(b2bOrderDto.getAfterBoxes());
                    b2bOrderDto.setWeight(b2bOrderDto.getAfterWeight());
                }
                listTemp.add(b2bOrderDto);
            }
        }
        return listTemp;
    }

    /**
     * 授信管理-订单管理-订单商品
     *
     * @param qm
     * @return
     */
    @Override
    public PageModel<B2bOrderGoodsExtDto> getOrderGoods(QueryModel<B2bOrderGoodsExtDto> qm, ManageAccount account) {
        Map<String, Object> map = new HashMap<String, Object>();
        PageModel<B2bOrderGoodsExtDto> pm = new PageModel<B2bOrderGoodsExtDto>();
        map.put("orderNumber", qm.getEntity().getOrderNumber());
        int counts = orderGoodsExtDao.getB2bOrderGoodsCount(map);
        if (account != null) {
            if (!CommonUtil.isExistAuthName(account.getPk(),
                    ColAuthConstants.EM_CREDIT_ORDER_ORDERMG_ORDERGOODS_COL_STORENAME)) {
                map.put("storeName", ColAuthConstants.EM_CREDIT_ORDER_ORDERMG_ORDERGOODS_COL_STORENAME);
            }
        }

        List<B2bOrderGoodsExtDto> list = orderGoodsExtDao.getB2bOrderGoods(map);

        pm.setTotalCount(counts);
        pm.setDataList(list);
        return pm;
    }

    @Override
    public B2bOrderExtDto getEconProductByOrderNumber(String orderNumber) {
        return orderExtDao.getEconProductByOrderNumber(orderNumber);
    }

    @Override
    public List<EconomicsProductOrder> searchEconomicsOrders(Map<String, Object> map) {
        return orderExtDao.searchEconomicsOrders(map);
    }

    @Override
    public int updateLoanNumber(B2bLoanNumber loanNumber) {
        return loanNumberExtDao.updateExt(loanNumber);
    }

    /**
     * 下载合同
     *
     * @param contractNo
     * @param req
     * @param resp
     */
    @Override
    public void downContractOrder(String contractNo, HttpServletRequest req, HttpServletResponse resp,String block) {
        String tempPath = PropertyConfig.getProperty("FILE_PATH");
        // 告诉浏览器用什么软件可以打开此文件
        resp.setHeader("content-Type", "application/pdf");
        // 下载文件的默认名称
        resp.setHeader("Content-Disposition", "attachment;filename=" + contractNo + ".pdf");
        B2bContractDto dto = b2bContractExtDao.getByContractNo(contractNo);
        Map<String, Object> map = new HashMap<>();
        map.put("contractNo", contractNo);
        List<B2bContractGoodsExtDto> dtoGoods = b2bContractGoodsExtDao.searchGridExt(map);
        try {
            Document document = new Document();
            Boolean flag = true;
            // 已付款状态的存临时文件
//            if (dto.getContractStatus() > 1) {
//                // 如果已有则下载
//                flag = downloadLocal(req, resp, contractNo);
//            }
            if (flag) {
                PdfWriter.getInstance(document, new FileOutputStream(tempPath + "/" + contractNo + ".pdf"));
                PdfWriter.getInstance(document, resp.getOutputStream());
                B2bCompanyDto supplier = b2bCompanyExtDao.getByPk(dto.getSupplierPk());
                // 使用itext-asian.jar中的字体
                BaseFont baseFont = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
                Font font = new Font(baseFont);
                Font fontBlack = new Font(baseFont, 14, Font.BOLD);
                document.open();
                PdfPTable table = new PdfPTable(13);
                table.setSpacingBefore(10);// 设置表头间距
                table.setTotalWidth(500);// 设置表格的总宽度
                table.setTotalWidth(new float[]{19, 74, 34, 20, 30, 49, 45, 49, 40, 42, 42, 42, 38});// 设置表格的各列宽度
                // 使用以上两个函数，必须使用以下函数，将宽度锁定。
                table.setLockedWidth(true);
                String companyName = "";
                if (null != dtoGoods && dtoGoods.size() > 0) {
                    String goodsPk = "";
                    // String goodsPk = dtoGoods.get(0).getGoodsPk();
                    B2bGoodsDto goodsDto = b2bGoodsExtDao.getByPk(goodsPk);
                    if (goodsDto != null) {
                        companyName = goodsDto.getCompanyName();
                    }
                }
                table.addCell(
                        getCell(companyName + "\n\n销  售  合  同", new Font(baseFont, 20, Font.BOLD), 13, true, null));
                table.addCell(getCell("合同编号：", font, 2, false, null));
                table.addCell(getCell(dto.getContractNo(), font, 4, false, null));
                table.addCell(getCell("签订地点：", font, 2, false, null));
                table.addCell(getCell(
                        (supplier.getProvinceName() == null ? "" : supplier.getProvinceName())
                                + (supplier.getCityName() == null ? "" : supplier.getCityName())
                                + (supplier.getAreaName() == null ? "" : supplier.getAreaName()),
                        font, 5, false, null));
                table.addCell(getCell("签订日期：", font, 2, false, null));
                table.addCell(getCell(DateUtil.formatYearMonthDay(dto.getStartTime()) + "", font, 5, false, null));
                table.addCell(getCell("", font, 7, false, null));
                table.addCell(getCell("根据《中华人民共和国合同法》的有关规定，经甲乙双方协商一致，签订本合同并遵照执行：", font, 13, false, null));
                // 1 产品名称、规格、等级、数量及价格
                table.addCell(getBlackCell("1  产品名称、规格、等级、数量及价格", fontBlack, 13, false, null));
                table.addCell(getCell("名称及规格", font, 3, true, null));
                table.addCell(getCell("等级", font, 2, true, null));
                if(Constants.BLOCK_CF.equals(block)){
                    table.addCell(getCell("数量（吨）", font, 2, true, null));
                    table.addCell(getCell("单价（元/吨）", font, 2, true, null));
                }else{
                    table.addCell(getCell("数量（千克）", font, 2, true, null));
                    table.addCell(getCell("单价（元/千克）", font, 2, true, null));
                }

                table.addCell(getCell("金额", font, 2, true, null));
                table.addCell(getCell("备注", font, 2, true, null));
                if (null != dtoGoods && dtoGoods.size() > 0) {
                    for (int i = 0; i < dtoGoods.size(); i++) {
                        B2bContractGoodsDto goods = dtoGoods.get(i);
                        String specifications = "";
                        String productName = "";
                        String gradeName = "";
                        String goodsInfo = goods.getGoodsInfo();
                        if (goodsInfo != null && !"".equals(goodsInfo)){
                            if (Constants.BLOCK_CF.equals(block)) {
                                CfGoods cfGoods = JSON.parseObject(goodsInfo, CfGoods.class);
                                productName = cfGoods.getProductName() == null ? "" : cfGoods.getProductName();
                                specifications = cfGoods.getSpecifications() == null ? "" : cfGoods.getSpecifications();// 规格
                                gradeName = cfGoods.getGradeName() == null ? "" : cfGoods.getGradeName();// 等级
                            }
                            if (Constants.BLOCK_SX.equals(block)) {
                                SxGoods cfGoods = JSON.parseObject(goodsInfo, SxGoods.class);
                                productName = cfGoods.getTechnologyName() == null ? "" : cfGoods.getTechnologyName();
                                specifications = cfGoods.getSpecName() == null ? "" : cfGoods.getSpecName();// 规格
                            }
                        }
                        table.addCell(getCell(productName + "  " + specifications, font, 3, true, null));
                        table.addCell(getCell(gradeName, font, 2, true, null));
                        table.addCell(getCell(new BigDecimal(goods.getWeight().toString()).toPlainString(), font, 2,
                                true, null));
                        String contractPrice = goods.getContractPrice() == null ? ""
                                : CommonUtil.formatDoubleTwo(goods.getContractPrice()).toString();

                        table.addCell(getCell(contractPrice, font, 2, true, null));
                        Double contractTotalPrice = 0d;
                        if (goods.getWeight() != null && goods.getContractPrice() != null) {
                            contractTotalPrice = goods.getWeight() * goods.getContractPrice();
                        }

                        table.addCell(getCell(CommonUtil.formatDoubleTwo(contractTotalPrice).toString(), font, 2, true,
                                null));
                        table.addCell(getCell("", font, 2, true, null));
                    }
                }
                table.addCell(getCell("合同金额人名币（大写）：" + Tool.change(dto.getTotalAmount()), font, 9, false, null));
                table.addCell(getCell("（小写）：", font, 2, true, null));
                table.addCell(
                        getCell(new BigDecimal(dto.getTotalAmount().toString()).toPlainString(), font, 2, true, null));
                // 2 产品质量标准
                table.addCell(getBlackMultiCell("2  产品质量标准\n\n",
                        "2.1  产品标准按照甲方企业定等标准执行。\n\n2.2  若产品不符合约定的质量标准，乙方应在收货后5个工作日内以书面方式提出，否则产品视作符合约定标准。", fontBlack,
                        font, 13, false, null));
                // 3 交货时间、交货地点及交换货方式
                String logisticsModelType = "";
                if (null != dto.getLogisticsModelType()) {
                    if (dto.getLogisticsModelType() == 1) {
                        logisticsModelType = "×客户自提   ×商家承运   √平台承运";
                    }
                    if (dto.getLogisticsModelType() == 2) {
                        logisticsModelType = "×客户自提   √商家承运   ×平台承运";
                    }
                    if (dto.getLogisticsModelType() == 3) {
                        logisticsModelType = "√客户自提   ×商家承运   ×平台承运";
                    }
                } else {
                    logisticsModelType = "×客户自提   ×商家承运   ×平台承运";
                }

                // 运费总价
                Double totalFreight = 0.0;
                for (B2bContractGoodsExtDto b2bContractGoodsDtoEx : dtoGoods) {
                    Double totalFreights = b2bContractGoodsDtoEx.getFreight() * b2bContractGoodsDtoEx.getWeight();
                    totalFreight = totalFreight + totalFreights;
                }

                String deliveryInfoBlack = "3  交货时间、交货地点及交换货方式\n\n";
                String deliveryInfo = "3.1  交货时间：" + DateUtil.formatYearMonthDay(dto.getStartTime()) + "  交货地点：甲方工厂\n\n"
                        + "3.2  交货方式：" + logisticsModelType + "\n\n" + "运输运费合计"
                        + CommonUtil.formatDoubleTwo(totalFreight).toString() + "元，运保费未含在合同条款中";
                table.addCell(getBlackMultiCell(deliveryInfoBlack, deliveryInfo, fontBlack, font, 13, false, null));

                // 4 产品包装
                String payType = "";
                if (dto.getPaymentType() != null) {
                    if (dto.getPaymentType() == 1) {
                        payType = "×在线支付    √线下支付";
                    } else {
                        payType = "√在线支付    ×线下支付";
                    }
                } else {
                    payType = "×在线支付    ×线下支付";
                }
                String productPackage4Black = "4  产品包装\n\n";
                String productPackage4 = "    采用行业惯用包装，能够满足搬运需求。\n\n";
                String productPackage5Black = "5  付款期限及付款方式\n\n";
                String productPackage5 = "5.1  本合同签订之日乙方向甲方支付合同价款，本合同自动生效。\n\n" + "5.2  付款方式：" + payType + "\n\n";
                String productPackage6Black = "6  提货期限\n\n";
                String productPackage6 = "6.1  合同载明货物自签订之日起，" + dto.getDays() + "日内提完。\n\n"
                        + "6.2  乙方提货时需遵守甲方厂纪厂规并保证安全，因提货造成的甲方、乙方自身及第三方任何损害均由乙方负责处理，与甲方无关。\n\n";
                String productPackage7Black = "7  其他\n\n";
                String productPackage7 = "7.1  合同履行过程中，甲、乙双方发生纠纷应友好协商解决。协商不成则提交合同履行地人民法院解决。\n\n"
                        + "7.2  本合同文本不得涂改，若需修改，甲、乙双方应协商达成一致修改意见，并签订书面文本。\n\n" + "7.3  本合同一式两份，甲、乙双方各执一份，具同等法律效力。\n\n"
                        + "7.4  乙方未经甲方许可，不得自行使用和许可第三方使用甲方提供的包装、外观、产品标识。不得泄露任何合作的内容，包括价格、规格、数量等信息。\n\n"
                        + "7.5  因乙方原因导致合同终止的，乙方应赔偿甲方销售差价损失，差价损失按合同签订日至合同截止日（含合同延期）期间的最低价格计算。";

                PdfPCell cell = new PdfPCell();

                Paragraph p4Black = new Paragraph(productPackage4Black, fontBlack);
                p4Black.setAlignment(Paragraph.ALIGN_CENTER);
                Paragraph p4 = new Paragraph(productPackage4, font);
                p4.setAlignment(Paragraph.ALIGN_BASELINE);

                Paragraph p5Black = new Paragraph(productPackage5Black, fontBlack);
                p4Black.setAlignment(Paragraph.ALIGN_CENTER);
                Paragraph p5 = new Paragraph(productPackage5, font);
                p4.setAlignment(Paragraph.ALIGN_BASELINE);

                Paragraph p6Black = new Paragraph(productPackage6Black, fontBlack);
                p4Black.setAlignment(Paragraph.ALIGN_CENTER);
                Paragraph p6 = new Paragraph(productPackage6, font);
                p4.setAlignment(Paragraph.ALIGN_BASELINE);

                Paragraph p7Black = new Paragraph(productPackage7Black, fontBlack);
                p4Black.setAlignment(Paragraph.ALIGN_CENTER);
                Paragraph p7 = new Paragraph(productPackage7, font);
                p4.setAlignment(Paragraph.ALIGN_BASELINE);

                p4Black.add(p4);
                p4Black.add(p5Black);
                p4Black.add(p5);
                p4Black.add(p6Black);
                p4Black.add(p6);
                p4Black.add(p7Black);
                p4Black.add(p7);
                cell.setPhrase(p4Black);
                cell.setColspan(13);

                table.addCell(cell);

                // 甲方
                B2bCompanyDto cDto = b2bCompanyExtDao.getByPk(dto.getSupplierPk());
                String jaInfo = "";
                if (null != cDto && null != cDto.getPk()) {
                    String provinceName = cDto.getProvinceName() == null ? "" : cDto.getProvinceName();
                    String cityName = cDto.getCityName() == null ? "" : cDto.getCityName();
                    String areaName = cDto.getAreaName() == null ? "" : cDto.getAreaName();
                    String regAddress = cDto.getRegAddress() == null ? "" : cDto.getRegAddress();
                    String bankName = "";
                    String bankAccount = "";
                    if(dto != null){
                        String supplierInfo = dto.getSupplierInfo();
                        if (supplierInfo != null && !"".equals(supplierInfo)){
                            SupplierInfo obj = JSON.parseObject(supplierInfo, SupplierInfo.class);
                            bankName = obj.getBankName() == null ? "" : obj.getBankName();
                            bankAccount = obj.getBankAccount() == null ? "" : obj.getBankAccount();
                        }
                    }
                    String supplierName = cDto.getName() == null ? "": cDto.getName();

                    String contactsTel = cDto.getContactsTel() == null ? "" : cDto.getContactsTel();
                    jaInfo += "甲方（卖方）\n\n";
                    jaInfo += "公司名称：" + supplierName + "\n\n";
                    jaInfo += "开户银行：" + bankName + "\n\n";
                    jaInfo += "银行账号：" + bankAccount + "\n\n";
                    jaInfo += "电话：" + contactsTel + "\n\n";
                    jaInfo += "地址：" + provinceName + cityName + areaName + regAddress + "\n\n";
                    jaInfo += "甲方盖章：\n\n";
                    jaInfo += "甲方法定代表人或委托代理人签字：____________";
                } else {
                    jaInfo += "甲方（卖方）\n\n";
                    jaInfo += "公司名称：\n\n";
                    jaInfo += "开户银行：\n\n";
                    jaInfo += "银行账号：\n\n";
                    jaInfo += "电话：\n\n";
                    jaInfo += "地址：\n\n";
                    jaInfo += "甲方盖章：\n\n";
                    jaInfo += "甲方法定代表人或委托代理人签字：____________";
                }

                // 乙方
                Map<String, Object> yiMap = new HashMap<String, Object>();
                String purchaserInfo = dto.getPurchaserInfo();
                String invoiceName = "";
                if (purchaserInfo != null && !"".equals(purchaserInfo)) {
                    JSONObject obj = JSONObject.fromObject(purchaserInfo.replace("\r\n", ""));
                    if (obj.has("invoiceName")) {
                        invoiceName = obj.get("invoiceName").toString();
                    }
                }
                yiMap.put("companyName", invoiceName);
                yiMap.put("isDelete", Constants.ONE);
                // List<B2bInvoiceDto> yiDtoList
                // =b2bInvoiceDao.searchList(yiMap);
                List<B2bCompanyDto> yiDtoList = b2bCompanyExtDao.searchGrid(yiMap);
                String yiInfo = "";
                if (null != yiDtoList && yiDtoList.size() > 0) {
                    B2bCompanyDto yiDto = yiDtoList.get(0);
                    String yprovinceName = yiDto.getProvinceName() == null ? "" : yiDto.getProvinceName();
                    String ycityName = yiDto.getCityName() == null ? "" : yiDto.getCityName();
                    String yareaName = yiDto.getAreaName() == null ? "" : yiDto.getAreaName();
                    String yregAddress = yiDto.getRegAddress() == null ? "" : yiDto.getRegAddress();
                    String yName = yiDto.getName() == null ? "" : yiDto.getName();
                    String yBankName = yiDto.getBankName() == null ? "" : yiDto.getBankName();
                    String yBankAccount = yiDto.getBankAccount() == null ? "" : yiDto.getBankAccount();
                    String yContactsTel = yiDto.getContactsTel() == null ? "" : yiDto.getContactsTel();
                    yiInfo += "乙方（买方）\n\n";
                    yiInfo += "公司名称：" + yName + "\n\n";
                    yiInfo += "开户银行：" + yBankName + "\n\n";
                    yiInfo += "银行账号：" + yBankAccount + "\n\n";
                    yiInfo += "电话：" + yContactsTel + "\n\n";
                    yiInfo += "地址：" + yprovinceName + ycityName + yareaName + yregAddress + "\n\n";
                    yiInfo += "乙方盖章：\n\n";
                    yiInfo += "乙方法定代表人或委托代理人签字：____________";
                } else {
                    yiInfo += "乙方（买方）\n\n";
                    yiInfo += "公司名称：\n\n";
                    yiInfo += "开户银行：\n\n";
                    yiInfo += "银行账号：\n\n";
                    yiInfo += "电话：\n\n";
                    yiInfo += "地址：\n\n";
                    yiInfo += "乙方盖章：\n\n";
                    yiInfo += "乙方法定代表人或委托代理人签字：____________";
                }

                table.addCell(getCell(jaInfo, font, 6, false, null));
                table.addCell(getCell(yiInfo, font, 7, false, null));
                document.add(table);
                document.close();
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (dto.getContractStatus() <= 1) {
                File file = new File(tempPath + "/" + contractNo + ".pdf");
                if (file.exists() && file.isFile()) {
                    file.delete();
                }
            }
        }
    }

    /**
     * @param value       值
     * @param font        字体
     * @param colspan     合并宽
     * @param alginCenter 是否居中
     * @param rowspan     合并高
     * @return
     */
    private PdfPCell getCell(String value, Font font, Integer colspan, boolean alginCenter, Integer rowspan) {
        PdfPCell cell = new PdfPCell();
        Paragraph p = new Paragraph(value, font);
        cell.setPhrase(p);
        cell.setColspan(colspan);
        font.setSize(6);
        if (rowspan != null) {
            cell.setRowspan(rowspan);
        }
        if (alginCenter) {
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        }
        return cell;
    }

    private PdfPCell getBlackCell(String value, Font font, Integer colspan, boolean alginCenter, Integer rowspan) {
        PdfPCell cell = new PdfPCell();
        Paragraph p = new Paragraph(value, font);
        p.setAlignment(Paragraph.ALIGN_CENTER);
        cell.setPhrase(p);
        cell.setColspan(colspan);
        font.setSize(6);
        if (rowspan != null) {
            cell.setRowspan(rowspan);
        }
        return cell;
    }

    private PdfPCell getBlackMultiCell(String valueBlack, String value, Font fontBlack, Font font, Integer colspan,
                                       boolean alginCenter, Integer rowspan) {
        PdfPCell cell = new PdfPCell();
        Paragraph p = new Paragraph(valueBlack, fontBlack);
        p.setAlignment(Paragraph.ALIGN_CENTER);
        Paragraph p1 = new Paragraph(value, font);
        p1.setAlignment(Paragraph.ALIGN_BASELINE);
        p.add(p1);
        cell.setPhrase(p);
        cell.setColspan(colspan);
        font.setSize(6);
        if (rowspan != null) {
            cell.setRowspan(rowspan);
        }
        return cell;
    }

    private boolean downloadLocal(HttpServletRequest req, HttpServletResponse response, String fileName)
            throws FileNotFoundException {
        boolean flag = true;
        try {
            String tempPath = PropertyConfig.getProperty("FILE_PATH");
            // 读到流中
            File file = new File(tempPath + "/" + fileName + ".pdf");
            if (!file.exists()) {
                return flag;
            }

            InputStream inStream = new FileInputStream(tempPath + "/" + fileName + ".pdf");// 文件的存放路径
            byte[] b = new byte[1024];
            int len;
            // 循环取出流中的数据
            while ((len = inStream.read(b)) > 0) {
                response.getOutputStream().write(b, 0, len);
                flag = false;
            }
            inStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

	@Override
	public  Map<String, Object>  getRepayLoan(String orderNumber,double amount) {
		Double interest = 0d;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		B2bLoanNumberDto  dto = loanNumberExtDao.getByOrderNumber(orderNumber);
		if (dto!=null) {
				String  loanStartTime = sdf.format(dto.getLoanStartTime());
		         String nowTime = sdf.format(new Date());
		         Double totalRate = dto.getTotalRate() == null ? 0.0 : dto.getTotalRate();
		         Double bankRate = dto.getBankRate() == null ? 0.0 : dto.getBankRate();
		         Double sevenRate = dto.getSevenRate() == null ? 0.0 : dto.getSevenRate();
		         int type = 0;
		         if (dto.getEconomicsGoodsType() != null && (dto.getEconomicsGoodsType() == 2||dto.getEconomicsGoodsType() == 4)) {// 化纤贷
		             type = 2;
		         } else if (dto.getEconomicsGoodsType() != null && (dto.getEconomicsGoodsType() == 1||dto.getEconomicsGoodsType() == 3)) {
		             type = 1;
		         }
		         // 未还银行利息
	             Double repayingInterest = InterestUtil.getInterest(loanStartTime, nowTime, amount, bankRate);
	             // 未还本金平台服务费
	          //   Double repayingSerCharge = InterestUtil.getCoverCharges(loanStartTime, nowTime, amount,totalRate, bankRate, sevenRate, type);
	          //   interest = ArithUtil.add(repayingInterest, repayingSerCharge);
	             interest = repayingInterest;
		}
           Map<String, Object>   map = new HashMap<String, Object>();
           map.put("interest", interest);
		return  map;
	}

	@Override
	public PageModel<B2bContractExtDto> searchMContractList(QueryModel<B2bContractExtDto> qm, ManageAccount account) {
        PageModel<B2bContractExtDto> pm = new PageModel<B2bContractExtDto>();
        List<B2bContractExtDto> list = new ArrayList<B2bContractExtDto>();
      Map<String, Object> map =  getStringObjectMap(qm);
      String block = qm.getEntity().getBlock();
      if (Constants.BLOCK_CF.equals(block)) {
          setCFOperColContractParams(map, account);
      }
      // 查询登陆账户信息
      MarketingPersonnelDto dto =this.getAccountName(account.getPk());
      int totalCount = 0;
      if(null != dto){
    	  if (dto.getType() == 1) {//业务员账号登录
    		  map.put("businessPk", account.getPk());
    		  totalCount = orderMemberDao.searchContactCount(map);
              list = orderMemberDao.searchContactList(map);
    	  }else if (dto.getType() == 2){ //平台交易员登录,看所有的订单
    		  totalCount = b2bContractExtDao.searchGridCountExt(map);
    		  list = b2bContractExtDao.searchGridExt(map);
    	  }else if (dto.getType() == 3){//区域经理
    		   B2bManageRegionDto regionDto = b2bManageRegionDao.getByPk(dto.getRegionPk());
               if (regionDto != null && regionDto.getArea() != null && !regionDto.getArea().equals("")) {
            	   List<RegionJson> regionJsons = JSON.parseArray(regionDto.getArea(), RegionJson.class);
            	   map.put("regionList", regionJsons);
                   map.put("loginPk", account.getPk());
                   totalCount = orderMemberDao.searchContactCount(map);
                   list = orderMemberDao.searchContactList(map);
               }else{//区域总监看见所有
            	   totalCount = b2bContractExtDao.searchGridCountExt(map);
         		  list = b2bContractExtDao.searchGridExt(map);
               }
    	  }
      } 
    
        // 查询付款凭证
        if (Constants.BLOCK_CF.equals(block)&& CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.MARKET_CONTRACT_MANAGE_COL_VOUCHER)) {
            setPayVoucher(list);
        }
        if (list != null && list.size() > 0) {
            for (B2bContractExtDto extDto : list) {
                if (Constants.BLOCK_CF.equals(block)){
                    OrderExportUtil.setOperContractParams(account, extDto);
                }
               
            }
        }
        pm.setDataList(list);
        pm.setTotalCount(totalCount);
        return pm;
    }
	
	private void setCFOperColContractParams(Map<String, Object> map, ManageAccount account) {
        if (account != null) {
            if (!CommonUtil.isExistAuthName(account.getPk(), ColAuthConstants.MARKET_CONTRACT_MANAGE_COL_STORENAME)) {
                map.put("storeNameCol", ColAuthConstants.MARKET_CONTRACT_MANAGE_COL_STORENAME);
            }
        }
    }

	private MarketingPersonnelDto getAccountName(String accountPk) {

	        Map<String, Object> mapAccount = new HashMap<>();
	        mapAccount.put("accountPk", accountPk);
	        mapAccount.put("isDelete", 1);
	        mapAccount.put("isVisable", 1);
	        return  marketingPersonnelDao.getByMap(mapAccount);
	    }
	private  Map<String, Object> getStringObjectMap(QueryModel<B2bContractExtDto> qm) {
		Map<String, Object>  map =   new HashMap<String, Object>();
		map.put("contractNo", qm.getEntity().getContractNo());
        map.put("insertTimeStart", qm.getEntity().getInsertTimeStart());
        map.put("insertTimeEnd", qm.getEntity().getInsertTimeEnd());
        map.put("payTimeStart", qm.getEntity().getPayTimeStart());
        map.put("payTimeEnd", qm.getEntity().getPayTimeEnd());
        map.put("storeName", qm.getEntity().getStoreName());
        map.put("contractStatus", qm.getEntity().getContractStatus());
        map.put("paymentType", qm.getEntity().getPaymentType());
        map.put("block", qm.getEntity().getBlock());
        map.put("economicsGoodsType", qm.getEntity().getEconomicsGoodsType());
        map.put("economicsGoodsName", qm.getEntity().getEconomicsGoodsName());
        map.put("purchaserName", qm.getEntity().getPurchaserName());
        
        map.put("orderName", qm.getFirstOrderName());
        map.put("orderType", qm.getFirstOrderType());
        map.put("start", qm.getStart());
        map.put("limit", qm.getLimit());
        return map;
	}
}