package cn.cf;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import cn.cf.entity.UVHandlerInfo;
import cn.cf.util.DateUtil;

@Service(value = "uvhandler")
public class UVHandler extends Handler {

	@Autowired
	private MongoTemplate mongoTemplate;

	// mongo里面uv表结构：ip,userId,count,date

	/**
	 * UV统计
	 */
	// 先判断session是否存在，如果存在，取出userId,然后从mongo里面根据userId和当前日期(yyyy-MM-dd)查找，如果查到，增加count
	// 如果没有查到，插入userId,当前日期(yyyy-MM-dd)和count为1；如果session不存在，取出IP，然后从mongo里面根据ip和当前日期查找
	// 查到，增加count;查不到，新增ip,日期，和count为1
	@Override
	public void handleRequest(HttpServletRequest request) {
		buildUV(request);
	}
	
	
	private synchronized void buildUV(HttpServletRequest request) {
//		String sessionId = request.getParameter("sessionId");
//		String userId = "";
//		String ip = CommonUtil.getIpAddress(request);
		String ip = request.getParameter("ipAddress");
		String today = DateUtil.formatYearMonthDay(new Date());
		String type = request.getParameter("type");
//		if(null!=sessionId){
//			
//			Sessions session = JedisUtils.get(sessionId, Sessions.class);
			Criteria uv = new Criteria();
			Query query = new Query();
//			if (null != session) {
//				userId = session.getMemberPk();
//				uv.andOperator(Criteria.where("userId").is(userId), Criteria.where("date").is(today));
//				query.addCriteria(uv);
//				UVHandlerInfo uvHandlerInfo = mongoTemplate.findOne(query, UVHandlerInfo.class);
//				if (uvHandlerInfo != null) {
//					updateUV(query, uvHandlerInfo);
//				} else {
//					addUserUV(userId, today);
//				}
//			} else {
				uv.andOperator(Criteria.where("ip").is(ip), Criteria.where("date").is(today),Criteria.where("type").is(type));
				query.addCriteria(uv);
				UVHandlerInfo uvHandlerInfo = mongoTemplate.findOne(query, UVHandlerInfo.class);
				if (uvHandlerInfo != null) {
					updateUV(query, uvHandlerInfo);
				} else {
					addIpUV(today,ip, type);
				}
//			}
			
//		}else{
//			
//			if(null!=ip){
//				Query query = new Query();
//				Criteria uv = new Criteria();
//				uv.andOperator(Criteria.where("ip").is(ip), Criteria.where("date").is(today));
//				query.addCriteria(uv);
//				UVHandlerInfo uvHandlerInfo = mongoTemplate.findOne(query, UVHandlerInfo.class);
//				if (uvHandlerInfo != null) {
//					updateUV(query, uvHandlerInfo);
//				} else {
//					addIpUV(today, ip);
//				}
//			}else{
//				logger.info("ip get is null.......");
//			}
//			
//		}
		
		
	
	}

	private void addIpUV(String today, String ip,String type) {
		UVHandlerInfo model = new UVHandlerInfo();
		model.setCount(1);
		model.setDate(today);
		model.setIp(ip);
		model.setType(type);
		mongoTemplate.insert(model);
	}

//	private void addUserUV(String userId, String today) {
//		UVHandlerInfo model = new UVHandlerInfo();
//		model.setCount(1);
//		model.setDate(today);
//		model.setUserId(userId);
//		mongoTemplate.insert(model);
//	}

	private void updateUV(Query query, UVHandlerInfo uvHandlerInfo) {
		Update update = Update.update("count", uvHandlerInfo.getCount() + 1);
		mongoTemplate.updateFirst(query, update, UVHandlerInfo.class);
	}

}
