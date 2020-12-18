package cn.cf.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import cn.cf.entry.MangoInterfaceInfo;
import cn.cf.entry.MangoOperationInfo;
import cn.cf.service.MangoService;
import cn.cf.util.DateUtil;
import cn.cf.util.KeyUtils;

@Service
public class MangoServiceImpl implements MangoService {
	
	@Autowired
	private MongoTemplate mongoTemplate; 
	
	/**
	 * 插入用户操作日志
	 */
	@Override
	public void insertMangoOperationInfo(MangoOperationInfo mangoOperationInfo) {
		mangoOperationInfo.setId(KeyUtils.getUUID());
		//如果有传登陆用户id则同步公司信息
		if(mangoOperationInfo.getUserId()!=null){
			
		}
		mongoTemplate.save(mangoOperationInfo);
	}
	
	/**
	 * 插入接口调用日志(返回id)
	 */
	@Override
	public String insertMangoInterfaceInfo(MangoInterfaceInfo mangoInterfaceInfo) {
			
		if(mangoInterfaceInfo.getId()!=null&&!"".equals(mangoInterfaceInfo.getId())){
			Update update = new Update();
			update.set("responseValue", mangoInterfaceInfo.getResponseValue());
			update.set("responseTime", DateUtil.formatDateAndTime(new Date()));
			mongoTemplate.upsert(new Query(Criteria.where("id").is(mangoInterfaceInfo.getId())), update, MangoInterfaceInfo.class);
			return mangoInterfaceInfo.getId();
		}else{
			mangoInterfaceInfo.setId(KeyUtils.getUUID());
			mongoTemplate.save(mangoInterfaceInfo);
			return mangoInterfaceInfo.getId();
		}
	}

}
