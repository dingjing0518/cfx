package cn.cf.common.conf;

import cn.cf.common.utils.JedisMaterialUtils;
import cn.cf.constant.Constants;
import cn.cf.dao.*;
import cn.cf.dto.*;
import cn.cf.property.PropertyConfig;
import cn.cf.util.CommonUtil;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class LoadConfigs implements ApplicationContextAware {

	private final static Logger logger = LoggerFactory.getLogger(LoadConfigs.class);

	@Autowired
	private SysPropertyDao sysPropertyDao;

	/**
	 * 项目启动时添加配置参数信息到redis缓存
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

		try {
			logger.info("---------------LoadConfigs_Start----------------");
			initSysProperty();
			logger.info("---------------LoadConfigs_End-------------------");
		} catch (Exception e) {
			logger.error("----------------------LoadConfigs_Error--------------");
			e.printStackTrace();
		}
	}


	private void initSysProperty() {
		Map<String,Object> map = new HashMap<>();
		map.put("type",Constants.ONE);
		List<SysPropertyDto> list = sysPropertyDao.searchList(map);
		if (list != null && list.size() > 0){
			for (SysPropertyDto dto:list){
				PropertyConfig.init(dto.getContent());
			}
		}
	}
	public static void main(String[] args) {
		//JedisUtils.del(Constants.categoryKey);
		//initSysCategory();
		//initSysBanner();
	}
}
