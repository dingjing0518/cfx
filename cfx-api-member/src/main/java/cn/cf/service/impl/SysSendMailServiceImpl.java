package cn.cf.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import cn.cf.PageModel;
import cn.cf.common.RestCode;
import cn.cf.common.SendMails;
import cn.cf.service.SysSendMailService;
@Service
public class SysSendMailServiceImpl implements SysSendMailService {
@Autowired 
private   MongoTemplate mongoTemplate;
	@Override
	public PageModel<SendMails> searchsendMailList(Map<String, Object> map) {
		PageModel<SendMails> pm = new PageModel<SendMails>();
		try {
			int start =   Integer.valueOf(map.get("start").toString());
			int isRead= (int) map.get("isRead");
			Query query= new Query(Criteria.where("memberPk").is(map.get("memberPk")));
			if(isRead==1||isRead==2){
				query.addCriteria(Criteria.where("isRead").is(isRead) );
			}
			query.with(new Sort(Direction.ASC, "insertTime"));
			query.skip(start).limit(  Integer.valueOf(map.get("limit").toString()));
		 	int counts = (int) mongoTemplate.count(query, SendMails.class);
		 	pm.setTotalCount(counts);
			List<SendMails> list = mongoTemplate.find(query,
					SendMails.class);
			pm.setDataList(list);
			Query  all=  new Query(Criteria.where("memberPk").is(map.get("memberPk")));
			Query   Unread =new Query(Criteria.where("memberPk").is(map.get("memberPk")).orOperator(Criteria.where("isRead").is(1) ));
			
			int allRead =(int) mongoTemplate.count(all, SendMails.class);
			int Unreads =(int) mongoTemplate.count(Unread, SendMails.class);
		 
		    ArrayList<Object> AL=new ArrayList<Object>();
		    AL.add("all:"+allRead);
		    AL.add("unreads:"+Unreads);
		    AL.add("read:"+(allRead-Unreads));
			 
		   
			 pm.setOtherData(AL);
		} catch (Exception e) {
			e.printStackTrace();
			
		}

		return pm;
	}
 
	@Override
	public RestCode updateIsRead(String id) {
		try {
			Update update = new Update();
			update.set("isRead",2);
			Query query= new Query(Criteria.where("id").is(id));
			mongoTemplate.upsert(query, update,  SendMails.class);
		} catch (Exception e) {
			e.printStackTrace();
			return RestCode.CODE_A002;
		}
		
		return RestCode.CODE_0000;
	}
 
}
//sdto.setContent("dafdjasfkdjflkdj");
//sdto.setMobile("18014003209");
//
//B2bMemberDto mdto=new B2bMemberDto();
//mdto.setCompanyPk("760c5cacfbea4ba99b8a47de913aad9b");
//mdto.setPk("e92378b0afa24398bad97956bd531514");
//SmsUtils.sendMail(sdto, mdto,   mongoTemplate);