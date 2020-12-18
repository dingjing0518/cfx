package cn.cf.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.cf.PageModel;
import cn.cf.common.RestCode;
import cn.cf.common.SendMails;
import cn.cf.entity.Sessions;
import cn.cf.service.SysSendMailService;
import cn.cf.utils.BaseController;
/**
 * @author:FJM
 * @describe:站内信接口汇总
 * @time:2017-5-23 下午3:20:49
 */
@RestController
@RequestMapping("/member")
public class MessageController extends BaseController {

	@Autowired
	private SysSendMailService mailService;
	
	@Autowired
	private MongoTemplate mongoTemplate;

	@RequestMapping(value = "/sendMailList", method = RequestMethod.POST)
	public String sendMailList(HttpServletRequest request) {
		Sessions session = this.getSessions(request);
		Map<String, Object> map = this.paramsToMap(request);
		map.put("memberPk", session.getMemberPk());
		PageModel<SendMails> pm = mailService.searchsendMailList(map);
		return RestCode.CODE_0000.toJson(pm);

	}

	@RequestMapping(value = "/updateIsRead", method = RequestMethod.POST)
	public String updateIsRead(HttpServletRequest request, String id) {
		RestCode res = mailService.updateIsRead(id);
		return res.toJson();

	}
	
	@RequestMapping(value = "/pushMobileMessage", method = RequestMethod.POST)
	public String pushMobileMessage(HttpServletRequest request,String nextId,String preId) { 
		Sessions session = this.getSessions(request);
		if(session == null){
			return RestCode.CODE_S003.toJson();
		}
		
//		SendMails mail = new SendMails();
//		try {
//			mail.setCompanyPk(session.getCompanyPk());
//			mail.setContent("测试数据：您好，贵司于14点54分针对求购需求AB纱 AAA null 20D/7F进行报价，价格为20000.0元/吨。");
//			mail.setIsRead(1);// 1未读 2已读
//			mail.setMemberPk(session.getMemberPk());
//			mail.setMobile(session.getMobile());
//			mail.setId(KeyUtils.getUUID());
//			mail.setInsertTime(DateUtil.formatDateAndTime(new Date()));
//			mongoTemplate.insert(mail);
//		} catch (Exception e) {
//		}

		int isRead = 1;
		//开始调用，没有调上一条，下一条
		String messages = null;
		Query query = new Query(Criteria.where("companyPk").is(session.getCompanyPk())
				.and("mobile").is(session.getMobile())
				.and("memberPk").is(session.getMemberPk())).with(new Sort(Direction.ASC,"insertTime"));
		
		if(nextId == null && preId == null){ 
			List<SendMails> messageList = mongoTemplate.find(query, SendMails.class);
			//第一条读取过的消息设置为已读
				String firstMailId = "";
				SendMails firstMails = new SendMails();
				for (int i = 0; i < messageList.size(); i++) {
					SendMails mails = messageList.get(i);
					if(mails != null && mails.getIsRead() != null && mails.getIsRead() == 1){

						firstMails.setId(mails.getId());
						firstMails.setCompanyPk(mails.getCompanyPk());
						firstMails.setMemberPk(mails.getMemberPk());
						firstMails.setIsRead(mails.getIsRead());
						firstMails.setInsertTime(mails.getInsertTime());
						firstMails.setContent(mails.getContent());
						firstMails.setMobile(mails.getMobile());
						//上一页消息为未读
						if(i > 1){
							firstMails.setPreId(messageList.get(i-1).getId());
						}else{
							firstMails.setPreId("");	
						}
						break;
					}
				}
				//下一条未读
				Query queryRead = new Query(Criteria.where("companyPk").is(session.getCompanyPk())
						.and("mobile").is(session.getMobile())
						.and("memberPk").is(session.getMemberPk())
						.and("isRead").is(isRead)).with(new Sort(Direction.ASC,"insertTime"));
				List<SendMails> readList = mongoTemplate.find(queryRead, SendMails.class);
					for (int j = 0; j < readList.size(); j++) {
						SendMails nextMails = readList.get(j);
						if(nextMails != null && (nextMails.getId()).equals(firstMailId)){
							//说明不是最后一条未读
							if(j < readList.size()-1){
								firstMails.setNextId(readList.get(j+1).getId());
							}else{
								firstMails.setNextId("");
							}
						}
					}
				//把未读状态改为已读
					messages = RestCode.CODE_0000.toJson(firstMails);
					Query updateQuery = Query.query(Criteria.where("id").is(firstMails.getId()));
					Update update = Update.update("isRead", 2);
					mongoTemplate.updateMulti(updateQuery, update, SendMails.class);
			}

		String id = "";
		if(preId != null && !"".equals(preId)){
			id = preId; 
		}else if(nextId != null && !"".equals(nextId)){
			id = nextId;
		}
		
		if(id != null && !"".equals(id)){
			Query queryById = new Query(Criteria.where("id").is(id));
			SendMails message = mongoTemplate.findOne(queryById, SendMails.class);
			if(message != null){
				//上一页能查到已读的消息
				Query queryPre = new Query(Criteria.where("companyPk").is(session.getCompanyPk())
						.and("mobile").is(session.getMobile())
						.and("memberPk").is(session.getMemberPk())
						.and("isRead").is(message.getIsRead())).with(new Sort(Direction.ASC,"insertTime"));
				List<SendMails> messagePreList = mongoTemplate.find(queryPre, SendMails.class);
					for (int i = 0; i < messagePreList.size(); i++) {
						if(i!= 0 && message.getId() != null && (message.getId()).equals(messagePreList.get(i).getId())){
							if(messagePreList.get(i-1) != null){
								message.setPreId(messagePreList.get(i-1).getId());
							}else{
								message.setPreId("");
							}
						}
					}
				//下一页只能查到未读的消息
				Query queryNext = new Query(Criteria.where("companyPk").is(session.getCompanyPk())
							.and("mobile").is(session.getMobile())
							.and("memberPk").is(session.getMemberPk())
							.and("isRead").is(message.getIsRead())).with(new Sort(Direction.ASC,"insertTime"));
				List<SendMails> messageNextList = mongoTemplate.find(queryNext, SendMails.class);
				for (int j = 0; j < messageNextList.size(); j++) {
					if(message.getId() != null && (message.getId()).equals(messageNextList.get(j).getId())){
						if(j < messageNextList.size()-1){
							message.setNextId(messageNextList.get(j+1).getId());
						}else{
							message.setNextId("");
						}
					}
				}	
				messages = RestCode.CODE_0000.toJson(message);
				Query updateQuery = Query.query(Criteria.where("id").is(id));
				Update update = Update.update("isRead", 2);
				mongoTemplate.updateMulti(updateQuery, update, SendMails.class);
			}
		}
		if(messages == null || messages == "" ){
			return RestCode.CODE_0000.toJson();
		}
		return messages;
	}
	

}
