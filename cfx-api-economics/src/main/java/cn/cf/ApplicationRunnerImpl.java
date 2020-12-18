package cn.cf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

import cn.cf.common.BanksType;
import cn.cf.common.OnlineType;
import cn.cf.common.RiskControlType;
import cn.cf.constant.BillType;
import cn.cf.dao.SysPropertyDao;
import cn.cf.dto.SysPropertyDto;
import cn.cf.json.JsonUtils;
import cn.cf.property.PropertyConfig;

/**
 * springboot启动完成之后执行
 * @description:
 * @author FJM
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
    	try {
    		Map<String, Object> pmap = new HashMap<String, Object>();
    		List<SysPropertyDto> properties = propertyDao.searchList(pmap);
    		if(null != properties && properties.size()>0){
    			for(SysPropertyDto p : properties){
    				if(p.getType()==1){
    					PropertyConfig.init(p.getContent());
    				}
    				//金融银行类
    				if(p.getType()==2){
    					switch (p.getProductName()) {
    					case BanksType.bank_suzhou:
    						PropertyConfig.init(getAppendStr("sz", p.getContent()));
    						break;
    					case BanksType.bank_guangda:
    						PropertyConfig.init(getAppendStr("gd", p.getContent()));
    						break;
    					case BanksType.bank_xingye:
    						PropertyConfig.init(getAppendStr("xy", p.getContent()));
    						break;
    					case BanksType.bank_gongshang:
    						PropertyConfig.init(getAppendStr("gs", p.getContent()));
    						break;
    					case BanksType.bank_sunong:
    						PropertyConfig.init(getAppendStr("sn", p.getContent()));
    						break;
    					case BanksType.bank_zheshang:
    						PropertyConfig.init(getAppendStr("zs", p.getContent()));
    						break;
    					case BanksType.bank_shanghai:
    						PropertyConfig.init(getAppendStr("sh", p.getContent()));
    						break;
    					case BanksType.bank_jianshe:
    						PropertyConfig.init(getAppendStr("js", p.getContent()));
    						break;
    					case BanksType.bank_zhonghang:
							PropertyConfig.init(getAppendStr("zh", p.getContent()));
							break;
    					default:
    						break;
    					}
    				}
    				//线上支付类
    				if(p.getType()==3){
    					switch (p.getProductShotName()) {
    					case OnlineType.EQF:
    						PropertyConfig.init(getAppendStr(OnlineType.EQF, p.getContent()));
    						break;
    					default:
    						break;
    					}
    				}
    				//风控类
    				if(p.getType()==4){
    					if(RiskControlType.WZSY.getValue().equals(p.getProductShotName())){
    						PropertyConfig.init(getAppendStr(RiskControlType.WZSY.getValue(), p.getContent()));
    					}
    					if(RiskControlType.RAYX.getValue().equals(p.getProductShotName())){
    						PropertyConfig.init(getAppendStr(RiskControlType.RAYX.getValue(), p.getContent()));
    					}
    				}
    				//票据类
    				if(p.getType()==5){
    					switch (p.getProductShotName()) {
    					case BillType.PFT:
    						PropertyConfig.init(getAppendStr("nb", p.getContent()));
    						break;
    					case BillType.TX:
    						PropertyConfig.init(getAppendStr("nb", p.getContent()));
    						break;
    					default:
    						break;
    					}
    				}
    			}
    			logger.info("------------>properties属性加载完毕!");
    		}
		} catch (Exception e) {
			logger.error("error:"+e);
		}
    }
    
    private String getAppendStr(String key,String content){
    	if(null != content && !"".equals(content)){
    		JSONObject json = JsonUtils.toJSONObject(content);
    		JSONObject nj = new JSONObject();
    		for(Map.Entry<String, Object> entry : json.entrySet()){
    			nj.put(key+"_"+entry.getKey(), entry.getValue());
    		}
    		return nj.toJSONString();
    	}
    	return null;
    }
}
