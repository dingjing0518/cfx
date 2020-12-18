package cn.cf.service.creditpay.impl;

import cn.cf.common.RestCode;
import cn.cf.common.creditpay.zhonghang.PostUtils;
import cn.cf.common.creditpay.zhonghang.XmlUtils;
import cn.cf.dto.*;
import cn.cf.entity.B2bCreditGoodsDtoMa;
import cn.cf.entity.B2bOrderDtoMa;
import cn.cf.entity.B2bRepaymentRecord;
import cn.cf.entry.BankBaseResp;
import cn.cf.entry.BankInfo;
import cn.cf.property.PropertyConfig;
import cn.cf.service.creditpay.BankZhonghangService;
import cn.cf.util.DateUtil;
import cn.cf.util.KeyUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BankZhonghangServiceImpl implements BankZhonghangService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public BankBaseResp searchBankCreditAmount(B2bCompanyDto company, B2bCreditGoodsDto dto) {
        BankBaseResp resp = null;
        String id = null;
        XmlUtils xml = new XmlUtils();
        try {
            //发送报文请求
            String xmlStrs = xml.zhCreditAmount(company.getName(), company.getOrganizationCode());
            id = this.saveBankInfo(null, null,
                    xmlStrs, null,
                    "active", "customer");
            String rest = PostUtils.post(PropertyConfig.getProperty("zh_bank_url"), xmlStrs);
            this.saveBankInfo(null, id, xmlStrs,
                    rest, "active", "customer");
            JSONObject jsonObject = xml.getJsonByXml(rest);
            logger.error("bank zhonghang return message: jsonObject is {}", jsonObject);
            if (null != jsonObject && !"".equals(jsonObject)) {
                if (jsonObject.getString("flag").equals("2")) {
                    logger.error("searchBankCreditAmount error for bank zhonghang: msg is {}", jsonObject.getString("msg"));
                    return resp;
                }
                resp = new BankBaseResp();
                List<B2bEconomicsBankCompanyDto> ebcList = new ArrayList<B2bEconomicsBankCompanyDto>();
                B2bEconomicsBankCompanyDto ebc = new B2bEconomicsBankCompanyDto();
                JSONArray jsonArray = jsonObject.getJSONArray("list_obj");
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject json = (JSONObject) jsonArray.get(i);
                    Integer type = json.getInteger("type");//额度品种代码(金融产品类型)
                    String startDate = json.getString("startDate");//额度生效日期
                    String endDate = json.getString("endDate");//额度到期日
                    Double amount = json.getDouble("amount");//产品授信额度
                    Double availableAmount = json.getDouble("availableAmount");//产品可用额度
                    ebc.setType(type);
                    ebc.setCreditStartTime(DateUtil.parseDateFormatYMD(startDate));
                    ebc.setCreditEndTime(DateUtil.parseDateFormatYMD(endDate));
                    ebc.setContractAmount(amount);
                    ebc.setAvailableAmount(availableAmount);
                }
                Double totalAmount = jsonObject.getDouble("totalAmount"); //授信总额度
                ebc.setCreditAmount(totalAmount);
                ebc.setPk(KeyUtils.getUUID());
                ebc.setCompanyPk(company.getPk());
                ebc.setBankPk(dto.getBankPk());
                ebcList.add(ebc);
                resp.setEbcList(ebcList);
            }
        } catch (Exception e) {
            logger.error("search amount error for bank zhonghang:", e);
        }
        return resp;
    }

    @Override
    public BankBaseResp payOrder(B2bOrderDtoMa order, B2bCompanyDto company, SysCompanyBankcardDto card, B2bCreditGoodsDtoMa cgDto) {
        BankBaseResp resp = null;
        String id = null;
        XmlUtils xml = new XmlUtils();
        try {
            String xmlStrs = xml.zhPayOrder(company.getName(), order.getOrderNumber(), order.getInsertTime(), order.getReceivablesTime(),
                    company.getOrganizationCode(), order.getOrderAmount(), order.getActualAmount(), order.getSupplier().getSupplierName(),
                    order.getSupplier().getSupplierName(), card.getBankAccount(), card.getBankNo(), card.getBankName());
            id = this.saveBankInfo(null, null,
                    xmlStrs, null,
                    "active", "pay");
            String rest = PostUtils.post(PropertyConfig.getProperty("zh_bank_url"), xmlStrs);
            this.saveBankInfo(null, id, xmlStrs,
                    rest, "active", "pay");
            JSONObject jsonObject = xml.getJsonByXml(rest);
            logger.error("bank zhonghang return message: jsonObject is {}", jsonObject);
            if (null != jsonObject && !"".equals(jsonObject)) {
                resp = new BankBaseResp();
                if (jsonObject.getString("flag").equals("1")) {
                    resp.setCode(RestCode.CODE_0000.getCode());
                    resp.setMsg(jsonObject.getString("msg"));
                } else {
                    logger.error("payOrder error for bank zhonghang: msg is {}", jsonObject.getString("msg"));
                    resp.setCode(RestCode.CODE_Z000.getCode());
                    resp.setMsg("银行信息数据异常");
                }
            }
        } catch (Exception e) {
            logger.error("payOrder error for bank zhonghang:", e);
        }
        return resp;
    }

    @Override
    public BankBaseResp searchloan(B2bLoanNumberDto loanNumber) {
        BankBaseResp resp = null;
        String id = null;
        XmlUtils xml = new XmlUtils();
        try {
            String xmlStrs = xml.zhSearchloan(loanNumber.getPurchaserName(), loanNumber.getOrganizationCode(),
                    loanNumber.getOrderNumber());
            id = this.saveBankInfo(null, null,
                    xmlStrs, null,
                    "active", "loan");
            String rest = PostUtils.post(PropertyConfig.getProperty("zh_bank_url"), xmlStrs);
            this.saveBankInfo(null, id, xmlStrs,
                    rest, "active", "loan");
            JSONObject jsonObject = xml.getJsonByXml(rest);
            logger.error("bank zhonghang return message: jsonObject is {}", jsonObject);
            if (null != jsonObject && !"".equals(jsonObject)) {
                if (jsonObject.getString("flag").equals("2")) {
                    logger.error("searchloan error for bank zhonghang: msg is {}", jsonObject.getString("msg"));
                    return resp;
                }
                resp = new BankBaseResp();
                BankCreditDto cdto = new BankCreditDto();
                String orderNumber = jsonObject.getString("orderNumber");//订单编号
                Double loanAmount = jsonObject.getDouble("loanAmount");//放款金额
                Integer loanStatus = jsonObject.getInteger("loanStatus");//放款状态
                String startDate = jsonObject.getString("startDate");//放款起始日期
                String endDate = jsonObject.getString("endDate");//放款到期日期
                String lousNumber = jsonObject.getString("lousNumber");//借据编号
                Double Rate = jsonObject.getDouble("Rate");//年化率
                cdto.setContractNumber(orderNumber);
                cdto.setLoanAmount(loanAmount);
                if (loanStatus == 1) {
                    cdto.setLoanStatus(2);
                } else {
                    cdto.setLoanStatus(loanStatus);
                }
                cdto.setLoanStartDate(DateUtil.parseDateFormatYMD(startDate));
                cdto.setLoanEndDate(DateUtil.parseDateFormatYMD(endDate));
                cdto.setIouNumber(lousNumber);
                cdto.setLoanRate(Rate);
                cdto.setPayStatus(1);
                resp.setBankCreditDto(cdto);
            }
        } catch (Exception e) {
            logger.error("searchloan error for bank zhonghang:", e);
        }
        return resp;
    }

    @Override
    public BankBaseResp searchRepayment(B2bLoanNumberDto loanNumber) {
        BankBaseResp resp = null;
        String id = null;
        XmlUtils xml = new XmlUtils();
        try {
            String xmlStrs = xml.zhRepayment(loanNumber.getPurchaserName(), loanNumber.getOrganizationCode(),
                    loanNumber.getLoanNumber());
            id = this.saveBankInfo(null, null,
                    xmlStrs, null,
                    "active", "repayment");
            String rest = PostUtils.post(PropertyConfig.getProperty("zh_bank_url"), xmlStrs);
            this.saveBankInfo(null, id, xmlStrs,
                    rest, "active", "repayment");
            JSONObject jsonObject = xml.getJsonByXml(rest);
            logger.error("bank zhonghang return message: jsonObject is {}", jsonObject);
            if (null != jsonObject && !"".equals(jsonObject)) {
                if (jsonObject.getString("flag").equals("2")) {
                    logger.error("searchRepayment error for bank zhonghang: msg is {}", jsonObject.getString("msg"));
                    return resp;
                }
                resp = new BankBaseResp();
                //total 总笔数
                if (null != jsonObject.get("total") && Integer.parseInt(jsonObject.get("total").toString()) > 0) {
                    List<B2bRepaymentRecord> list = new ArrayList<B2bRepaymentRecord>();
                    B2bRepaymentRecord brpr = new B2bRepaymentRecord();
                    JSONArray jsonArray = jsonObject.getJSONArray("list_obj");
                    Double repaymentAmount = 0d;
                    Double repaymentInterest = 0d;
                    Double repaymentPenalty = 0d;
                    Double repaymentCompound = 0d;
                    for (int i = 0; i < jsonArray.size(); i++) {
                        JSONObject json = (JSONObject) jsonArray.get(i);
                        Double principal = json.getDouble("principal"); //还款本金
                        Double interest = json.getDouble("interest"); //还款利息
                        Double penalty = json.getDouble("penalty"); //实还罚息
                        Double compound = json.getDouble("compound"); //实还复利
                        String tranDate = json.getString("tranDate"); //交易日期
                        brpr.setAmount(principal);
                        brpr.setInterest(interest);
                        brpr.setPenalty(penalty);
                        brpr.setCompound(compound);
                        brpr.setCreateTime(tranDate);
                        repaymentAmount += brpr.getAmount();
                        repaymentInterest += brpr.getInterest();
                        repaymentPenalty += brpr.getPenalty();
                        repaymentCompound += brpr.getCompound();
                        brpr.setAmount(repaymentAmount);
                        brpr.setInterest(repaymentInterest);
                        brpr.setPenalty(repaymentPenalty);
                        brpr.setCompound(repaymentCompound);
                    }
                    Integer status = jsonObject.getInteger("status"); //借据状态
                    brpr.setStatus(status);
                    list.add(brpr);
                    resp.setB2bRepaymentRecordList(list);
                }
            }
        } catch (Exception e) {
            logger.error("searchRepayment error for bank zhonghang:", e);
        }
        return resp;
    }

    @Override
    public BankBaseResp cancelOrder(B2bLoanNumberDto loanNumber) {
        BankBaseResp resp = null;
        String id = null;
        XmlUtils xml = new XmlUtils();
        try {
            String xmlStrs = xml.zhCancelOrder(loanNumber.getOrderNumber());
            id = this.saveBankInfo(null, null,
                    xmlStrs, null,
                    "active", "cancel");
            String rest = PostUtils.post(PropertyConfig.getProperty("zh_bank_url"), xmlStrs);
            this.saveBankInfo(null, id, xmlStrs,
                    rest, "active", "cancel");
            JSONObject jsonObject = xml.getJsonByXml(rest);
            logger.error("bank zhonghang return message: jsonObject is {}", jsonObject);
            if (null != jsonObject && !"".equals(jsonObject)) {
                resp = new BankBaseResp();
                if (jsonObject.getString("flag").equals("1")) {
                    resp.setCode(RestCode.CODE_0000.getCode());
                    resp.setMsg(jsonObject.getString("msg"));
                } else {
                    logger.error("cancelOrder error for bank zhonghang: msg is {}", jsonObject.getString("msg"));
                    resp.setCode(RestCode.CODE_Z000.getCode());
                }
            }
        } catch (Exception e) {
            logger.error("cancelOrder error for bank zhonghang:", e);
        }
        return resp;
    }

    private String saveBankInfo(String code, String id, String requestValue,
                                String responseValue, String requestMode, String hxhCode) {
        BankInfo bank = new BankInfo();
        if (id == null) {
            String infoId = KeyUtils.getUUID();
            bank.setId(infoId);
            bank.setCode(code);
            bank.setBankName("zhonghangBank");
            bank.setRequestValue(requestValue);
            bank.setInsertTime(DateUtil.formatDateAndTime(new Date()));
            bank.setRequestMode(requestMode);
            bank.setHxhCode(hxhCode);
            mongoTemplate.save(bank);
            return infoId;
        } else {
            Update update = new Update();
            update.set("responseValue", responseValue);
            mongoTemplate.upsert(new Query(Criteria.where("id").is(id)),
                    update, BankInfo.class);
            return id;
        }
    }
}
