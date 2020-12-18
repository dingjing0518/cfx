package cn.cf.service.operation.impl;

import cn.cf.DataMaps;
import cn.cf.PageModel;
import cn.cf.common.Constants;
import cn.cf.common.QueryModel;
import cn.cf.common.utils.CommonUtil;
import cn.cf.dao.SysCategoryExtDao;
import cn.cf.dao.SysNewsCategoryExtDao;
import cn.cf.dao.SysNewsExtDao;
import cn.cf.dto.*;
import cn.cf.entity.SysNewsStorageEntity;
import cn.cf.model.SysNews;
import cn.cf.model.SysNewsCategory;
import cn.cf.property.PropertyConfig;
import cn.cf.service.operation.NewsService;
import cn.cf.util.KeyUtils;
import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosAlert;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.mongodb.BasicDBList;
import com.mongodb.CommandResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class NewsServiceImpl implements NewsService {

	@Autowired
	private SysNewsExtDao sysNewsExtDao;
	@Autowired
	private SysCategoryExtDao sysCategoryExtDao;

	@Autowired
	private SysNewsCategoryExtDao sysNewsCategoryExtDao;

	@Autowired
	private MongoTemplate mongoTemplate;

	private static Logger logger = LoggerFactory.getLogger(NewsServiceImpl.class);

	@Override
	public SysNewsExtDto getSysNewsByPk(String pk) {
		Map<String,Object> map = new HashMap<>();
		map.put("pk",pk);
		return sysNewsExtDao.getNewsByPk(map);
	}

	@Override
	public List<SysCategoryExtDto> getCategorys(String newPK) {
		Map<String,Object> maps = new HashMap<>();
		maps.put("pk",newPK);
		SysNewsDto entiy = getSysNewsByPk(newPK);
		List<SysCategoryExtDto> list = sysCategoryExtDao.getCategorys();
		if (list != null) {
			for (SysCategoryExtDto s : list) {
				Map<Integer, String> map = DataMaps.sysCategory;
				if (map.get(s.getParentId()) != null) {
					s.setpName(map.get(s.getParentId()));
					s.setpId(s.getParentId());
				}

                Map<String,Object> parentMap = new HashMap<>();
                parentMap.put("parentPk",s.getParentId());
				List<SysCategoryExtDto> cs = sysCategoryExtDao.getByParentId(parentMap);
				if (entiy != null) {
					for (SysCategoryExtDto sc : cs) {
                        Map<String,Object> categoryMap = new HashMap<>();
                        categoryMap.put("newsPk",entiy.getPk());
						List<SysCategoryExtDto> nc = sysCategoryExtDao
								.getNewsCates(categoryMap);
						for (SysCategoryExtDto ncs : nc) {
							if (ncs.getCategoryPk().equals(sc.getPk())) {
								sc.setFlag(1);
								s.setFlag(1);
							}
						}
					}
				}
				s.setCategorys(cs);
			}
		}

		return list;
	}

	@Override
	public int updateSysNews(SysNews sysNews, String categoryPk) {

		String pk = null;
		int result = 0;
		Date date = new Date();
		if (sysNews.getPk() != null && !"".equals(sysNews.getPk())) {
			pk = sysNews.getPk();
			if(sysNews.getStatus() != null && sysNews.getStatus() == 2){
				sysNews.setEstimatedTime(date);
			}
			result = sysNewsExtDao.update(sysNews);
		} else {
			pk = KeyUtils.getUUID();
			sysNews.setPk(pk);
			sysNews.setInsertTime(date);
			sysNews.setIsDelete(Constants.ONE);
			sysNews.setIsVisable(Constants.ONE);
			sysNews.setRecommend(Constants.ONE);
			sysNews.setIsPush(Constants.ONE);
			if(sysNews.getStatus() != null && sysNews.getStatus() == 2){
				sysNews.setEstimatedTime(date);
			}
			result = sysNewsExtDao.insert(sysNews);
		}
		if (result == 1) {
			if (categoryPk != null) {
				sysNewsCategoryExtDao.deleteByNewsPk(pk);
				categoryPk = categoryPk.substring(1, categoryPk.length() - 1);
				String[] categoryPks = categoryPk.split(",");
				if (!"".equals(categoryPks)) {
					for (int j = 0; j < categoryPks.length; j++) {
						SysNewsCategory snc = new SysNewsCategory();
						snc.setPk(KeyUtils.getUUID());
						snc.setNewsPk(pk);
						String catepk = categoryPks[j];
						catepk = catepk.substring(1, catepk.length() - 1);
						snc.setCategoryPk(catepk);
						sysNewsCategoryExtDao.insert(snc);

					}
				}
			}
			List<SysNewsDto> sysNewsList = new ArrayList<SysNewsDto>();
			SysNewsDto dto = sysNewsExtDao.getByPk(pk);
			sysNewsList.add(dto);
			updateSysNewsStorage(sysNewsList);
		}
		return result;

	}

	private int updateSysNewsStorage(List<SysNewsDto> sysNewsList) {
		int result = 0;
		if (sysNewsList != null && sysNewsList.size() > 0) {
			for (SysNewsDto sysNewsDto : sysNewsList) {
				try {
					Query query = Query.query(Criteria.where("newsPk").is(
							sysNewsDto.getPk()));
					mongoTemplate.findAllAndRemove(query,
							SysNewsStorageEntity.class);
					List<SysNewsCategoryDto> newsCategoryList = (List<SysNewsCategoryDto>) sysNewsCategoryExtDao
							.getByNewsPk(sysNewsDto.getPk());
					if (newsCategoryList != null && newsCategoryList.size() > 0) {
						for (SysNewsCategoryDto sysNewsCategoryDto : newsCategoryList) {
							SysNewsStorageEntity newsStorage = new SysNewsStorageEntity();
							SysCategoryDto categoryDto = sysCategoryExtDao
									.getByPk(sysNewsCategoryDto.getCategoryPk());
							newsStorage.setId(KeyUtils.getUUID());
							newsStorage.setNewsPk(sysNewsDto.getPk());
							if (categoryDto != null) {
								newsStorage.setCategoryPk(categoryDto.getPk());
								newsStorage.setCategoryName(categoryDto
										.getName());
								newsStorage.setParentId(categoryDto
										.getParentId());
							}
							newsStorage.setTitle(sysNewsDto.getTitle());
							if (sysNewsDto.getInsertTime() != null) {
								newsStorage.setInsertTime(new SimpleDateFormat(
										"yyyy-MM-dd HH:mm:ss")
										.format(sysNewsDto.getInsertTime()));
							}
							newsStorage.setIsDelete(sysNewsDto.getIsDelete());
							newsStorage.setIsVisable(sysNewsDto.getIsVisable());
							newsStorage.setContent(sysNewsDto.getContent());
							if (sysNewsDto.getRecommend() != null) {
								newsStorage.setRecommend(sysNewsDto
										.getRecommend());
							} else {
								newsStorage.setRecommend(0);
							}
							if (sysNewsDto.getTop() != null) {
								newsStorage.setTop(sysNewsDto.getTop());
							} else {
								newsStorage.setTop(2);
							}
							newsStorage.setKeyword(sysNewsDto.getKeyword());
							newsStorage.setNewAbstrsct(sysNewsDto
									.getNewAbstrsct());
							newsStorage.setUrl(sysNewsDto.getUrl());
							if (sysNewsDto.getEstimatedTime() != null) {
								newsStorage
										.setEstimatedTime(new SimpleDateFormat(
												"yyyy-MM-dd HH:mm:ss")
												.format(sysNewsDto
														.getEstimatedTime()));
							} else {
								newsStorage.setEstimatedTime("");
							}
							newsStorage.setStatus(sysNewsDto.getStatus());
							newsStorage.setNewSource(sysNewsDto.getNewSource());
							newsStorage.setRecommendPosition(sysNewsDto.getRecommendPosition());
							newsStorage
									.setPvCount(sysNewsDto.getPvCount() == null ? 0L
											: sysNewsDto.getPvCount());
							mongoTemplate.save(newsStorage);
							result = 1;
						}
					} else {
						SysNewsStorageEntity newsStorage = new SysNewsStorageEntity();
						newsStorage.setId(KeyUtils.getUUID());
						newsStorage.setNewsPk(sysNewsDto.getPk());
						newsStorage.setTitle(sysNewsDto.getTitle());
						if (sysNewsDto.getInsertTime() != null) {
							newsStorage.setInsertTime(new SimpleDateFormat(
									"yyyy-MM-dd HH:mm:ss").format(sysNewsDto
									.getInsertTime()));
						} else {
							newsStorage.setInsertTime("");
						}
						newsStorage.setIsDelete(sysNewsDto.getIsDelete());
						newsStorage.setIsVisable(sysNewsDto.getIsVisable());
						newsStorage.setContent(sysNewsDto.getContent());
						if (sysNewsDto.getRecommend() != null) {
							newsStorage.setRecommend(sysNewsDto.getRecommend());
						} else {
							newsStorage.setRecommend(0);
						}
						if (sysNewsDto.getTop() != null) {
							newsStorage.setTop(sysNewsDto.getTop());
						} else {
							newsStorage.setTop(2);
						}
						newsStorage.setKeyword(sysNewsDto.getKeyword());
						newsStorage.setNewAbstrsct(sysNewsDto.getNewAbstrsct());
						newsStorage.setUrl(sysNewsDto.getUrl());
						if (sysNewsDto.getEstimatedTime() != null) {
							newsStorage.setEstimatedTime(new SimpleDateFormat(
									"yyyy-MM-dd HH:mm:ss").format(sysNewsDto
									.getEstimatedTime()));
						} else {
							newsStorage.setEstimatedTime("");
						}
						newsStorage.setStatus(sysNewsDto.getStatus());
						newsStorage.setNewSource(sysNewsDto.getNewSource());
						newsStorage.setRecommendPosition(sysNewsDto.getRecommendPosition());
						mongoTemplate.save(newsStorage);
						result = 1;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	@Override
	public int updateSysNewsStatus(SysNews sysNews) {

		int result = 0;
		if (sysNews.getPk() != null && !"".equals(sysNews.getPk())) {
			if(sysNews.getStatus() != null && sysNews.getStatus() == 2){
				sysNews.setEstimatedTime(new Date());
			}
			result = sysNewsExtDao.update(sysNews);
			if (result > 0) {
				String pk = sysNews.getPk();
				SysNewsDto dto = sysNewsExtDao.getByPk(pk);
				if (dto != null) {
					if (sysNews.getTop() != null
							&& !"".equals(sysNews.getTop())) {
						dto.setTop(sysNews.getTop());
					}
					if (sysNews.getIsVisable() != null
							&& !"".equals(sysNews.getIsVisable())) {
						dto.setIsVisable(sysNews.getIsVisable());
					}
					if (sysNews.getRecommend() != null
							&& !"".equals(sysNews.getRecommend())) {
						dto.setRecommend(sysNews.getRecommend());
					}
					List<SysNewsDto> sysNewsList = new ArrayList<SysNewsDto>();
					sysNewsList.add(dto);
					updateSysNewsStorage(sysNewsList);
					result = 1;
				}
			}
		}
		return result;
	}

	@Override
	public int updateNewsPushStatus(SysNews sysNews) {
		int result = 0;
		if (sysNews.getPk() != null && !"".equals(sysNews.getPk())) {

			sysNews.setEstimatedTime(new Date());
			Query query = Query.query(Criteria.where("newsPk").is(sysNews.getPk()));
			List<SysNewsStorageEntity> list = mongoTemplate.find(query, SysNewsStorageEntity.class);
			if (list != null && list.size() > 0){
				String MASTER_SECRET = PropertyConfig.getProperty("MASTER_SECRET");
				String APP_KEY = PropertyConfig.getProperty("APP_KEY");
				//获取不同分类资讯，取出一条（因为不同分类的资讯是同一条新闻是相同的，mongo中保存的是按分类的多条数据）
				SysNewsStorageEntity entity = list.get(0);
				JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY, null, ClientConfig.getInstance());
				Map<String, String> parm = new HashMap<>();
				parm.put("title", entity.getTitle());
				parm.put("type",String.valueOf(Constants.ONE));//1文章
				parm.put("value",sysNews.getPk());
				parm.put("categoryPk",entity.getCategoryPk());
				String text = "";
				if (entity.getContent() != null && !"".equals(entity.getContent())){
					text = CommonUtil.getTextByHtml(entity.getContent());
					if (text.length() > 100){
						parm.put("message", text.substring(0,100));
					}else{
						parm.put("message", text);
					}
				}
				//推送新闻到APP，包含Android和IOS
				PushPayload payload = jPushAll(parm);
				try {
					PushResult res = jpushClient.sendPush(payload);
					//推送成功后修改状态
					if (res.statusCode == Constants.ZERO){
						Update update = Update.update("isPush", Constants.TWO);
						mongoTemplate.updateMulti(query,update,SysNewsStorageEntity.class);
						result = sysNewsExtDao.update(sysNews);
					}
				} catch (APIConnectionException e) {
					// Connection error, should retry later
					logger.error("updateNewsPushStatus_Connection error, should retry later：" + e);

				} catch (APIRequestException e) {
					// Should review the error, and fix the request
					logger.error("updateNewsPushStatus_Should review the error, and fix the request：" , e);
					logger.error("updateNewsPushStatus_HTTP Status: " , e.getStatus());
					logger.error("updateNewsPushStatus_Error Code: " , e.getErrorCode());
					logger.error("updateNewsPushStatus_Error Message: " , e.getErrorMessage());
				}
			}
		}
		return result;
	}

	/**
	 * 推送创建对象
	 * @param parm
	 * @return
	 */
	public static PushPayload jPushAll(Map<String, String> parm) {
		//创建option
		PushPayload payload = PushPayload.newBuilder()
				.setPlatform(Platform.all())  //所有平台的用户
				.setAudience(Audience.all())
				.setNotification(Notification.newBuilder()
						.addPlatformNotification(IosNotification.newBuilder() //发送ios
								.setAlert(IosAlert.newBuilder().setTitleAndBody(parm.get("title"),null,parm.get("message")).build()) //消息体
								//.setBadge(+1)
								.setContentAvailable(true)
								.setSound("happy") //ios提示音
								.addExtras(parm) //附加参数
								.build())
						.addPlatformNotification(AndroidNotification.newBuilder() //发送android
								.addExtras(parm) //附加参数
								.setAlert(parm.get("message")) //消息体
								.setTitle(parm.get("title"))
								.build())
						.build())
				.setOptions(Options.newBuilder().setApnsProduction(false).build())//指定开发环境 true为生产模式 false 为测试模式 (android不区分模式,ios区分模式)
				.setMessage(Message.newBuilder().setMsgContent(parm.get("message")).addExtras(parm).build())//自定义信息
				.build();
		return payload;
	}

	@Override
	public PageModel<SysNewsStorageEntity> searchSysNewsStorageList(QueryModel<SysNewsStorageEntity> qm) {
		
		
		PageModel<SysNewsStorageEntity> pm = new PageModel<SysNewsStorageEntity>();
		Criteria criteria = new Criteria();
		 criteria =  Criteria.where("_id").nin("");
		 
		Long start = (long) qm.getStart();
		String sort = qm.getFirstOrderName();
		String type = qm.getFirstOrderType();
		
		if(null!=qm.getEntity().getCategoryPk() && !"".equals(qm.getEntity().getCategoryPk())){
			 criteria.and("categoryPk").is(qm.getEntity().getCategoryPk());
		 }
		if(null!=qm.getEntity().getParentId() && !"".equals(qm.getEntity().getParentId())){
			 criteria.and("parentId").is(qm.getEntity().getParentId());
		 }
		if(null!=qm.getEntity().getIsVisable() && !"".equals(qm.getEntity().getIsVisable())){
			 criteria.and("isVisable").is(qm.getEntity().getIsVisable());
		 }
		if(null!=qm.getEntity().getRecommend() && !"".equals(qm.getEntity().getRecommend())){
			 criteria.and("recommend").is(qm.getEntity().getRecommend());
		 }
		if(null!=qm.getEntity().getTop() && !"".equals(qm.getEntity().getTop())){
			 criteria.and("top").is(qm.getEntity().getTop());
		 }
		if(null!=qm.getEntity().getKeyword() && !"".equals(qm.getEntity().getKeyword())){
			 criteria.and("keyword").regex(qm.getEntity().getKeyword());
		 }
		if(null!=qm.getEntity().getStatus() && !"".equals(qm.getEntity().getStatus())){
			 criteria.and("status").is(qm.getEntity().getStatus());
		 }
		if(null!=qm.getEntity().getTitle() && !"".equals(qm.getEntity().getTitle())){
			 criteria.and("title").regex(qm.getEntity().getTitle());
		 }
		if(null != qm.getEntity().getIsPush() && !"".equals(qm.getEntity().getIsPush())){
			criteria.and("isPush").is(qm.getEntity().getIsPush());
		}
		 criteria.and("isDelete").is(1);
		List<SysNewsStorageEntity> nameList = mongosSearch(criteria,sort,type,start,qm.getLimit());

		//统计数量
		StringBuilder searchQuery = new StringBuilder();
		searchQuery.append("{");
		if(null != qm.getEntity().getCategoryPk() && !"".equals(qm.getEntity().getCategoryPk())){
			searchQuery.append("'categoryPk':").append("\""+qm.getEntity().getCategoryPk()+"\"");
		}
		if(null != qm.getEntity().getParentId() && !"".equals(qm.getEntity().getParentId())){
			if(searchQuery.length() > 1){
				searchQuery.append(",");
			}
			searchQuery.append("'parentId':").append(""+qm.getEntity().getParentId()+"");
		}
		if(null != qm.getEntity().getIsVisable() && !"".equals(qm.getEntity().getIsVisable())){
			if(searchQuery.length() > 1){
				searchQuery.append(",");
			}
			searchQuery.append("'isVisable':").append(""+qm.getEntity().getIsVisable()+"");
		}
		if(null!=qm.getEntity().getRecommend() && !"".equals(qm.getEntity().getRecommend())){
			if(searchQuery.length() > 1){
				searchQuery.append(",");
			}
			searchQuery.append("'recommend':").append(""+qm.getEntity().getRecommend()+"");
		 }
		if(null!=qm.getEntity().getIsPush() && !"".equals(qm.getEntity().getIsPush())){
			if(searchQuery.length() > 1){
				searchQuery.append(",");
			}
			searchQuery.append("'isPush':").append(""+qm.getEntity().getIsPush()+"");
		}
		if(null!=qm.getEntity().getTop() && !"".equals(qm.getEntity().getTop())){
			if(searchQuery.length() > 1){
				searchQuery.append(",");
			}
			searchQuery.append("'top':").append(""+qm.getEntity().getTop()+"");
		 }
		if(null!=qm.getEntity().getStatus() && !"".equals(qm.getEntity().getStatus())){
			if(searchQuery.length() > 1){
				searchQuery.append(",");
			}
			searchQuery.append("'status':").append(""+qm.getEntity().getStatus()+"");
		 }
		if(null!=qm.getEntity().getStatus() && !"".equals(qm.getEntity().getStatus())){
			if(searchQuery.length() > 1){
				searchQuery.append(",");
			}
			searchQuery.append("'status':").append(""+qm.getEntity().getStatus()+"");
		 }

		if(null!=qm.getEntity().getKeyword() && !"".equals(qm.getEntity().getKeyword())){
			if(searchQuery.length() > 1){
				searchQuery.append(",");
			}
			searchQuery.append("keyword:").append("{$regex:'"+qm.getEntity().getKeyword()+"'}");
		 }
		if(null!=qm.getEntity().getTitle() && !"".equals(qm.getEntity().getTitle())){
			if(searchQuery.length() > 1){
				searchQuery.append(",");
			}
			searchQuery.append("title:").append("{$regex:'"+qm.getEntity().getTitle()+"'}");
		 }
		if(searchQuery.length() > 1){
			searchQuery.append(",");
		}
		searchQuery.append("'isDelete':").append(""+1+"");
		searchQuery.append("}");
		//,'query':{'categoryName':"+"\"行业视点\""+"}
        String jsonSql = "{'distinct':'sysNewsStorageEntity', 'key':'newsPk','query':"+searchQuery+"}";
        CommandResult  commandResult = mongoTemplate.executeCommand(jsonSql);
        BasicDBList list = (BasicDBList)commandResult.get("values"); 
        int counts = list.size();

	 	pm.setTotalCount(counts);
	    pm.setDataList(nameList);
		return pm;
	}
	
//	@Override
//	public List<ExportSysNewsStorageEntity> searchExportSysNewsStorageList(SysNewsStorageEntity qm) {
//		Criteria criteria = new Criteria();
//		 criteria =  Criteria.where("_id").nin("");
//		 
//		if(null!=qm.getCategoryPk() && !"".equals(qm.getCategoryPk())){
//			 criteria.and("categoryPk").is(qm.getCategoryPk());
//		 }
//		if(null!=qm.getParentId() && !"".equals(qm.getParentId())){
//			 criteria.and("parentId").is(qm.getParentId());
//		 }
//		if(null!=qm.getIsVisable() && !"".equals(qm.getIsVisable())){
//			 criteria.and("isVisable").is(qm.getIsVisable());
//		 }
//		if(null!=qm.getRecommend() && !"".equals(qm.getRecommend())){
//			 criteria.and("recommend").is(qm.getRecommend());
//		 }
//		if(null!=qm.getTop() && !"".equals(qm.getTop())){
//			 criteria.and("top").is(qm.getTop());
//		 }
//		if(null!=qm.getKeyword() && !"".equals(qm.getKeyword())){
//			 criteria.and("keyword").regex(qm.getKeyword());
//		 }
//		if(null!=qm.getStatus() && !"".equals(qm.getStatus())){
//			 criteria.and("status").is(qm.getStatus());
//		 }
//		if(null!=qm.getTitle() && !"".equals(qm.getTitle())){
//			 criteria.and("title").regex(qm.getTitle());
//		 }
//		if(null!=qm.getIsPush() && !"".equals(qm.getIsPush())){
//			criteria.and("isPush").is(qm.getIsPush());
//		}
//		 criteria.and("isDelete").is(1);
//		 TypedAggregation<SysNewsStorageEntity> aggregation = Aggregation.newAggregation(
//	        		SysNewsStorageEntity.class,  
//	                Aggregation.match( criteria),
//	                Aggregation.group("newsPk")  
//	                .first("newsPk").as("newsPk")  
//	                .first("title").as("title") 
//	                .addToSet("categoryPk").as("categoryPk")
//	                .addToSet("categoryName").as("categoryNameArr")
//	                .addToSet("parentId").as("parentIdArr")
//	                .first("insertTime").as("insertTime")  
//	                .first("isDelete").as("isDelete")  
//	                .first("isVisable").as("isVisable")
//	                .first("recommend").as("recommend")
//	                .first("top").as("top")
//	                .first("keyword").as("keyword")  
//	                .first("newAbstrsct").as("newAbstrsct")
//	                .first("url").as("url")  
//	                .first("status").as("status")
//	                .first("estimatedTime").as("estimatedTime")
//					.first("newSource").as("newSource")
//					.first("pvCount").as("pvCount")
//	                .first("recommendPosition").as("recommendPosition")
//	        );  
//	        List<SysNewsStorageEntity> consultReportSearch = mongoTemplate.aggregate(aggregation, SysNewsStorageEntity.class).getMappedResults();
//	        List<ExportSysNewsStorageEntity> nameList = new ArrayList<ExportSysNewsStorageEntity>();
//	        if(consultReportSearch != null &&consultReportSearch.size() > 0){
//	        	for (SysNewsStorageEntity sysNewsStorageEntity : consultReportSearch) {
//	        		ExportSysNewsStorageEntity entity = new ExportSysNewsStorageEntity();
//	        		
//	        		if(sysNewsStorageEntity.getCategoryNameArr() != null){
//	        			String categoryNames = "";
//	        			for (int i = 0; i < sysNewsStorageEntity.getCategoryNameArr().length; i++) {
//	        				categoryNames += sysNewsStorageEntity.getCategoryNameArr()[i]+";";
//						}
//	        			if(categoryNames != ""){
//	        			entity.setCategoryNames(categoryNames.substring(0, categoryNames.length()-1));
//	        			}
//	        		}
//	        		if(sysNewsStorageEntity.getParentIdArr() != null){
//	        			String name = "";
//						for (int i = 0; i < sysNewsStorageEntity.getParentIdArr().length; i++) {
//						String sysName = DataMaps.sysCategory.get(Integer.valueOf(sysNewsStorageEntity.getParentIdArr()[i]));
//						name+= sysName+";";
//						}
//						if(name != ""){
//						entity.setBelongSysNames(name.substring(0, name.length()-1));
//						}
//	        		}
//	        		
//	                entity.setInsertTime(sysNewsStorageEntity.getInsertTime());
//	               
//	                if(sysNewsStorageEntity.getStatus() != null && sysNewsStorageEntity.getStatus() == 2){
//	                	 entity.setStatusName("已发布");
//	                }else{
//	                	 entity.setStatusName("未发布");	
//	                }
//	                if(sysNewsStorageEntity.getRecommendPosition() != null && sysNewsStorageEntity.getRecommendPosition() == -1){
//	                	entity.setRecommendPosition("暂无");
//	                } else if(sysNewsStorageEntity.getRecommendPosition() != null && sysNewsStorageEntity.getRecommendPosition() == 1){
//	                	entity.setRecommendPosition("首页资讯模块");
//	                }else if(sysNewsStorageEntity.getRecommendPosition() != null && sysNewsStorageEntity.getRecommendPosition() == 2){
//	                	entity.setRecommendPosition("行情中心模块");
//	                }else if(sysNewsStorageEntity.getRecommendPosition() != null && sysNewsStorageEntity.getRecommendPosition() == 3){
//	                	entity.setRecommendPosition("首页资讯模块,行情中心模块");
//	                }else{
//	                	entity.setRecommendPosition("暂无");
//	                }
//	                
//	                entity.setKeyword(sysNewsStorageEntity.getKeyword());
//	                if(sysNewsStorageEntity.getTop() != null && sysNewsStorageEntity.getTop() == 1){
//	                	entity.setTopName("是");
//	                }else{
//	                	entity.setTopName("否");
//	                }
//	                if(sysNewsStorageEntity.getIsVisable() != null && sysNewsStorageEntity.getIsVisable() == 1){
//	                	entity.setIsVisableName("启用");
//	                }else{
//	                	entity.setIsVisableName("禁用");
//	                }
//	                entity.setPvCount(sysNewsStorageEntity.getPvCount());
//	                entity.setTitle(sysNewsStorageEntity.getTitle());
//	                nameList.add(entity);
//				}
//	        }
//		return nameList;
//	}

	private List<SysNewsStorageEntity> mongosSearch(Criteria criteria,String sort,String sortType,Long start,Integer limit){
		Sort sorts = new Sort(Direction.ASC, sort);
		if(sortType != null && "desc".equals(sortType) ){
         sorts = new Sort(Direction.DESC, sort);  
		} else{
			sorts = new Sort(Direction.ASC, sort);  
		}
		
        TypedAggregation<SysNewsStorageEntity> aggregation = Aggregation.newAggregation(
        		SysNewsStorageEntity.class,  
                Aggregation.match( criteria),
                Aggregation.group("newsPk")  
                .first("newsPk").as("newsPk")  
                .first("title").as("title") 
                .addToSet("categoryPk").as("categoryPk")
                .addToSet("categoryName").as("categoryNameArr")
                .addToSet("parentId").as("parentIdArr")
                .first("insertTime").as("insertTime")  
                .first("isDelete").as("isDelete")  
                .first("isVisable").as("isVisable")
               // .first("content").as("content")  
                .first("recommend").as("recommend")
                .first("top").as("top")
                .first("keyword").as("keyword")  
                .first("newAbstrsct").as("newAbstrsct")
                .first("url").as("url")  
                .first("status").as("status")
                .first("estimatedTime").as("estimatedTime")
                .first("newSource").as("newSource")
				.first("pvCount").as("pvCount")
				.first("isPush").as("isPush")
                .first("recommendPosition").as("recommendPosition"),
                Aggregation.sort(sorts),
              
               Aggregation.skip(start),  
               Aggregation.limit(limit)    
        );  
        List<SysNewsStorageEntity> consultReportSearch = mongoTemplate.aggregate(aggregation, SysNewsStorageEntity.class).getMappedResults(); 
        List<SysNewsStorageEntity> nameList = new ArrayList<SysNewsStorageEntity>();
        if(consultReportSearch != null &&consultReportSearch.size() > 0){
        	for (SysNewsStorageEntity sysNewsStorageEntity : consultReportSearch) {
        		SysNewsStorageEntity entity = new SysNewsStorageEntity();
        		entity.setNewsPk(sysNewsStorageEntity.getNewsPk());
        		
        		if(sysNewsStorageEntity.getCategoryNameArr() != null){
        			String categoryNames = "";
        			for (int i = 0; i < sysNewsStorageEntity.getCategoryNameArr().length; i++) {
        				categoryNames += sysNewsStorageEntity.getCategoryNameArr()[i]+";";
					}
        			if(categoryNames != ""){
        			entity.setCategoryNames(categoryNames.substring(0, categoryNames.length()-1));
        			}
        		}
        		
        		if(sysNewsStorageEntity.getParentIdArr() != null){
        			String name = "";
					for (int i = 0; i < sysNewsStorageEntity.getParentIdArr().length; i++) {
					String sysName = DataMaps.sysCategory.get(Integer.valueOf(sysNewsStorageEntity.getParentIdArr()[i]));
					name+= sysName+";";
					}
					if(name != ""){
					entity.setBelongSysNames(name.substring(0, name.length()-1));
					}
        		}
        		entity.setTitle(sysNewsStorageEntity.getTitle());
                entity.setInsertTime(sysNewsStorageEntity.getInsertTime());
                entity.setIsDelete(sysNewsStorageEntity.getIsDelete());
                entity.setIsVisable(sysNewsStorageEntity.getIsVisable());
                entity.setRecommend(sysNewsStorageEntity.getRecommend());
                entity.setKeyword(sysNewsStorageEntity.getKeyword());
                entity.setNewAbstrsct(sysNewsStorageEntity.getNewAbstrsct());
                entity.setUrl(sysNewsStorageEntity.getUrl());
                entity.setTop(sysNewsStorageEntity.getTop());
                entity.setEstimatedTime(sysNewsStorageEntity.getEstimatedTime());
                entity.setStatus(sysNewsStorageEntity.getStatus());
                entity.setNewSource(sysNewsStorageEntity.getNewSource());
				entity.setIsPush(sysNewsStorageEntity.getIsPush());
                entity.setPvCount(sysNewsStorageEntity.getPvCount());
                entity.setRecommendPosition(sysNewsStorageEntity.getRecommendPosition());
               // entity.setContent(sysNewsStorageEntity.getContent());
                nameList.add(entity);
			}
        }
        return nameList;
	}

}
