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

import cn.cf.dao.SysPropertyDao;
import cn.cf.dto.SysPropertyDto;
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
    	//只查系统的
    	Map<String, Object> pmap = new HashMap<String, Object>();
    	pmap.put("type", 1);
    	List<SysPropertyDto> properties = propertyDao.searchList(pmap);
    	if(null != properties && properties.size()>0){
    		for(SysPropertyDto p : properties){
    			PropertyConfig.init(p.getContent());
    		}
    		logger.info("------------>properties属性加载完毕!");
    	}
    }
}
