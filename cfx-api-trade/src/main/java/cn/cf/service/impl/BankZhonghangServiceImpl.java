package cn.cf.service.impl;

import cn.cf.common.RestCode;
import cn.cf.common.zhonghang.XmlUtils ;
import cn.cf.common.zhonghang.PostUtils;
import cn.cf.entity.BankBaseResp;
import cn.cf.entity.BankInfo;
import cn.cf.property.PropertyConfig;
import cn.cf.service.BankZhonghangService;
import cn.cf.util.DateUtil;
import cn.cf.util.KeyUtils;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class BankZhonghangServiceImpl implements BankZhonghangService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public BankBaseResp orderStatusUpd(String orderNumber, Integer orderStatus) {
        BankBaseResp resp = null;
        String id = null;
        XmlUtils xml = new XmlUtils();
        try {
            String xmlStrs = xml.zhStatusUpdate(orderNumber, orderStatus);
            id = this.saveBankInfo(null, null,
                    xmlStrs, null,
                    "active", "orderStatusUpd");
            String rest = PostUtils.post(PropertyConfig.getProperty("zh_bank_url"), xmlStrs);
            this.saveBankInfo(null, id, xmlStrs,
                    rest, "active", "orderStatusUpd");
            JSONObject jsonObject = xml.getJsonByXml(rest);
            logger.error("bank zhonghang return message：jsonObject is {}", jsonObject);
            if (null != jsonObject && !"".equals(jsonObject)) {
                resp = new BankBaseResp();
                if (jsonObject.getString("flag").equals("1")) {
                    resp.setCode(RestCode.CODE_0000.getCode());
                    resp.setMsg(jsonObject.getString("msg"));
                } else {
                    logger.error("orderStatusUpd error for bank zhonghang: msg is {}", jsonObject.getString("msg"));
                    resp.setCode(RestCode.CODE_Z000.getCode());
                }
            }
        } catch (Exception e) {
            logger.error("orderStatusUpd error for bank zhonghang:", e);
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
