package cn.cf.service.manage.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.dao.B2bMemberDao;
import cn.cf.dto.B2bMemberDto;
import cn.cf.entity.MangoBackstageInfo;
import cn.cf.entity.MangoOperationInfo;
import cn.cf.entity.SendMails;
import cn.cf.entity.Sms;
import cn.cf.service.manage.SmsService;

@Service("CmsSmsService")
public class SmsServiceImpl implements SmsService {
	@Autowired
	private B2bMemberDao b2bMemberDao;

	@Autowired
	private MongoTemplate mongoTemplate;
	@Override
	public PageModel<Sms> searchsmsRecordList(QueryModel<Sms> qm) {
		
		PageModel<Sms> pm = new PageModel<Sms>();
		 Query query = new Query();
		 if(null!=qm.getEntity().getMobile()&&!"".equals(qm.getEntity().getMobile())){
			 query.addCriteria(Criteria.where("mobile").regex(qm.getEntity().getMobile())); 
		 }
		 if(null!=qm.getEntity().getCompanyName()&&!"".equals(qm.getEntity().getCompanyName())){
			 query.addCriteria(Criteria.where("companyName").regex(qm.getEntity().getCompanyName())); 

		 }
		 if(null!=qm.getEntity().getStatus()&&!"".equals(qm.getEntity().getStatus())){
			 query.addCriteria(Criteria.where("status").is(qm.getEntity().getStatus()));
		 }
		 
		if("desc".equals(qm.getFirstOrderType())){
			 query.with(new Sort(Direction.DESC, qm.getFirstOrderName()));
		}else{
			 query.with(new Sort(Direction.ASC, qm.getFirstOrderName()));
		}
		 query.skip(qm.getStart()).limit(qm.getLimit());
	     List<Sms> list = mongoTemplate.find(  query, Sms.class);
	     int counts = (int) mongoTemplate .count(query, Sms.class);
	 	 pm.setTotalCount(counts);
	     pm.setDataList(list);

		return pm;
	}
	@Override
	public PageModel<SendMails> searchsendMailsList(QueryModel<SendMails> qm) {

		
		PageModel<SendMails> pm = new PageModel<SendMails>();
		 Query query = new Query();
		 if(null!=qm.getEntity().getMobile()&&!"".equals(qm.getEntity().getMobile())){
			 query.addCriteria(Criteria.where("mobile").regex(qm.getEntity().getMobile())); 
		 }
		 if(null!=qm.getEntity().getCompanyName()&&!"".equals(qm.getEntity().getCompanyName())){
			 query.addCriteria(Criteria.where("companyName").regex(qm.getEntity().getCompanyName())); 

		 }
			if("desc".equals(qm.getFirstOrderType())){
				 query.with(new Sort(Direction.DESC, qm.getFirstOrderName()));
			}else{
				 query.with(new Sort(Direction.ASC, qm.getFirstOrderName()));
			}
		 query.skip(qm.getStart()).limit(qm.getLimit());
	     List<SendMails> list = mongoTemplate.find(  query, SendMails.class);
	     int counts = (int) mongoTemplate .count(query, SendMails.class);
	 	 pm.setTotalCount(counts);
	     pm.setDataList(list);

		return pm;
	}
	@Override
	public PageModel<MangoOperationInfo> searchOperationApiLogsList(QueryModel<MangoOperationInfo> qm) {
		
		
		PageModel<MangoOperationInfo> pm = new PageModel<MangoOperationInfo>();
		 Criteria logsCriter = new Criteria();
		 logsCriter =  Criteria.where("_id").nin("");
		 if(null!=qm.getEntity().getMobile()&&!"".equals(qm.getEntity().getMobile())){
			 B2bMemberDto dto = b2bMemberDao.getByMobile(qm.getEntity().getMobile());
			 if(dto != null){
			 logsCriter.and("userId").regex(dto.getPk());
			 } 
		 }
		 if(null!=qm.getEntity().getInsertStartTime()&&!"".equals(qm.getEntity().getInsertStartTime())
				 && null!=qm.getEntity().getInsertEndTime()&&!"".equals(qm.getEntity().getInsertEndTime())){
			 logsCriter.and("insertTime").gte(qm.getEntity().getInsertStartTime()+" 00:00:00").lte(qm.getEntity().getInsertEndTime()+" 23:59:59");
		 }
		 
		 if(null!=qm.getEntity().getUrl()&&!"".equals(qm.getEntity().getUrl())){
			 logsCriter.and("url").regex(qm.getEntity().getUrl());
		 }
		 if(null!=qm.getEntity().getIp()&&!"".equals(qm.getEntity().getIp())){
			 logsCriter.and("ip").regex(qm.getEntity().getIp());
		 }
		 Query query = new Query(logsCriter);
		 query.with(new Sort(Direction.DESC, "insertTime"));
		 
		query.skip(qm.getStart()).limit(qm.getLimit());

	     List<MangoOperationInfo> list = mongoTemplate.find(query, MangoOperationInfo.class);
	     
	     
	     int counts = (int) mongoTemplate.count(query, MangoOperationInfo.class);
	     List<MangoOperationInfo> tempList = new ArrayList<MangoOperationInfo>();
	     if(counts > 0){
	    	 for (MangoOperationInfo mangoOperationInfo : list) {
	    		B2bMemberDto dto = b2bMemberDao.getByPk(mangoOperationInfo.getUserId());
	    		if(dto != null){
	    			mangoOperationInfo.setMobile(dto.getMobile() == null?"":dto.getMobile());
	    		}
	    		tempList.add(mangoOperationInfo);
			}  
	     }
	 	 pm.setTotalCount(counts);
	     pm.setDataList(tempList);

		return pm;
	}
	@Override
	public PageModel<MangoBackstageInfo> searchOperationBackLogsList(QueryModel<MangoBackstageInfo> qm) {
		
		
		PageModel<MangoBackstageInfo> pm = new PageModel<MangoBackstageInfo>();
		 Criteria logsCriter = new Criteria();
		 logsCriter =  Criteria.where("_id").nin("");
		 if(null!=qm.getEntity().getAccountName()&&!"".equals(qm.getEntity().getAccountName())){

			 logsCriter.and("accountName").regex(qm.getEntity().getAccountName());
		 }
		 if(null!=qm.getEntity().getInsertStartTime()&&!"".equals(qm.getEntity().getInsertStartTime())
				 && null!=qm.getEntity().getInsertEndTime()&&!"".equals(qm.getEntity().getInsertEndTime())){
			 logsCriter.and("insertTime").gte(qm.getEntity().getInsertStartTime()+" 00:00:00").lte(qm.getEntity().getInsertEndTime()+" 23:59:59");
		 }
		 
		 if(null!=qm.getEntity().getUrl()&&!"".equals(qm.getEntity().getUrl())){
			 logsCriter.and("url").regex(qm.getEntity().getUrl());
		 }
		 Query query = new Query(logsCriter);
		 query.with(new Sort(Direction.DESC, "insertTime"));
		 
		query.skip(qm.getStart()).limit(qm.getLimit());

	     List<MangoBackstageInfo> list = mongoTemplate.find(query, MangoBackstageInfo.class);
	     
	     
	     int counts = (int) mongoTemplate.count(query, MangoBackstageInfo.class);
	 	 pm.setTotalCount(counts);
	     pm.setDataList(list);

		return pm;
	}

}
