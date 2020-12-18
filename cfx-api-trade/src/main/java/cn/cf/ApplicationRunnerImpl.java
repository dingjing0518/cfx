package cn.cf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.cf.common.onlinepay.BanksType;
import cn.cf.json.JsonUtils;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import cn.cf.dao.SysPropertyDao;
import cn.cf.dto.SysPropertyDto;
import cn.cf.property.PropertyConfig;

/**
 * springboot启动完成之后执行
 *
 * @author FJM
 * @description:
 * @date 2019-9-11 下午1:03:12
 */
@Component
public class ApplicationRunnerImpl implements ApplicationRunner {

    @Autowired
    private SysPropertyDao propertyDao;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 加载完成后初始化属性文件
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        //只查系统的
        try {
            Map<String, Object> pmap = new HashMap<String, Object>();
            List<SysPropertyDto> properties = propertyDao.searchList(pmap);
            if (null != properties && properties.size() > 0) {
                for (SysPropertyDto p : properties) {
                    if (p.getType() == 1) {
                        PropertyConfig.init(p.getContent());
                    }
                    //金融银行类
                    if (p.getType() == 2) {
                        switch (p.getProductName()) {
                            case BanksType.bank_zhonghang:
                                PropertyConfig.init(getAppendStr("zh", p.getContent()));
                                break;
                            default:
                                break;
                        }
                    }
                }
                logger.info("------------>properties属性加载完毕!");
            }
        } catch (Exception e) {
            logger.error("error:" + e);
        }
    }

    private String getAppendStr(String key, String content) {
        if (null != content && !"".equals(content)) {
            JSONObject json = JsonUtils.toJSONObject(content);
            JSONObject nj = new JSONObject();
            for (Map.Entry<String, Object> entry : json.entrySet()) {
                nj.put(key + "_" + entry.getKey(), entry.getValue());
            }
            return nj.toJSONString();
        }
        return null;
    }
}
