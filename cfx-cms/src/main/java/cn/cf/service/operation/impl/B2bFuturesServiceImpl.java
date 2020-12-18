package cn.cf.service.operation.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.cf.common.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.dao.B2bFuturesTypeDaoEx;
import cn.cf.dto.B2bFuturesTypeDto;
import cn.cf.dto.B2bFuturesTypeDtoEx;
import cn.cf.entity.B2bFutures;
import cn.cf.entity.B2bFuturesEx;
import cn.cf.model.B2bFuturesType;
import cn.cf.service.operation.B2bFuturesService;
import cn.cf.util.KeyUtils;

@Service
public class B2bFuturesServiceImpl implements B2bFuturesService {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private B2bFuturesTypeDaoEx futuresTypeDaoEx;

	@Override
	public PageModel<B2bFutures> searchFuturesList(QueryModel<B2bFuturesEx> qm) {
		PageModel<B2bFutures> pm = new PageModel<B2bFutures>();
		Query query = new Query();
		query.addCriteria(Criteria.where("isVisable").is(1));
		query.addCriteria(Criteria.where("block").is(qm.getEntity().getBlock()));
		if (qm.getEntity().getFuturesPk() != null && !qm.getEntity().getFuturesPk().equals("")) {
			query.addCriteria(Criteria.where("futuresPk").is(qm.getEntity().getFuturesPk()));
		}
		if (qm.getEntity().getClosingStartTime() != null && !qm.getEntity().getClosingStartTime().equals("")) {
			if (qm.getEntity().getClosingEndTime() != null && !qm.getEntity().getClosingEndTime().equals("")) {
				query.addCriteria(Criteria.where("date").gte(qm.getEntity().getClosingStartTime()).lte(qm.getEntity().getClosingEndTime()));
			}else{
				query.addCriteria(Criteria.where("date").gte(qm.getEntity().getClosingStartTime()));
			}
		}else if (qm.getEntity().getClosingEndTime() != null && !qm.getEntity().getClosingEndTime().equals("")) {
			query.addCriteria(Criteria.where("date").lte(qm.getEntity().getClosingEndTime()));
		}
		
		if (qm.getEntity().getUpdateStartTime() != null && !qm.getEntity().getUpdateStartTime().equals("")) {
			if (qm.getEntity().getUpdateEndTime() != null && !qm.getEntity().getUpdateEndTime().equals("")) {
				query.addCriteria(Criteria.where("updateTime").gte(qm.getEntity().getUpdateStartTime()).lte(getTomorrow(qm.getEntity().getUpdateEndTime())));
			}else{
				query.addCriteria(Criteria.where("updateTime").gte(qm.getEntity().getUpdateStartTime()));
			}
		}else if (qm.getEntity().getUpdateEndTime() != null && !qm.getEntity().getUpdateEndTime().equals("")) {
			query.addCriteria(Criteria.where("updateTime").lte(getTomorrow(qm.getEntity().getUpdateEndTime())));
		}
	
		int counts = (int) mongoTemplate.count(query, B2bFutures.class);
		if ("desc".equals(qm.getFirstOrderType())) {
			query.with(new Sort(Direction.DESC, qm.getFirstOrderName()));
		} else {
			query.with(new Sort(Direction.ASC, qm.getFirstOrderName()));
		}
		query.skip(qm.getStart()).limit(qm.getLimit());
		pm.setTotalCount(counts);
		pm.setDataList(mongoTemplate.find(query, B2bFutures.class));
		return pm;
	}

	private String getTomorrow(String time) {
		 SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		Date date1;
		 String tom = "";
		try {
			date1 = sdf.parse(time);
			Calendar c = Calendar.getInstance();
		    c.setTime(date1);
		    c.add(Calendar.DAY_OF_MONTH, 1);
		     tom =    sdf.format(c.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return tom;
	}

	@Override
	public String updateFutures(B2bFutures future) {
		String msg = Constants.RESULT_SUCCESS_MSG;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateString = formatter.format(new Date());
			boolean  flag = isExsitFutures(future);
			if (flag) {
				if (future.getId() != null && !future.getId().equals("")) {
					Criteria c = new Criteria();
					c.andOperator(Criteria.where("id").is(future.getId()));
					Query query = new Query(c);
					Update update = Update.update("price", future.getPrice()).set("updateTime", dateString).set("date", future.getDate());
					mongoTemplate.updateFirst(query, update, B2bFutures.class);
				} else {
					future.setId(KeyUtils.getUUID());
					future.setIsVisable(1);
					future.setBlock(future.getBlock());
					future.setUpdateTime(dateString);
					future.setInsertTime(dateString);
					mongoTemplate.insert(future);
				}
			}else{
				msg = "{\"success\":false,\"msg\":\"当前日期已经维护价格!\"}";
			}
			
		} catch (Exception e) {
			msg = Constants.RESULT_FAIL_MSG;
			e.printStackTrace();
		}
		return msg;
	}

	    private boolean isExsitFutures(B2bFutures future) {
		    Query query = new Query();
			query.addCriteria(Criteria.where("futuresPk").is(future.getFuturesPk()));
			query.addCriteria(Criteria.where("date").is(future.getDate()));
			query.addCriteria(Criteria.where("block").is(future.getBlock()));
			if (future.getId() != null && !future.getId().equals("")) {
				query.addCriteria(Criteria.where("id").ne(future.getId()));
			}
			B2bFutures futures =  mongoTemplate.findOne(query, B2bFutures.class);
			if (futures!=null) {
				return false;
			}else{
				return true;
			}
	    }

	@Override
	public String delFutures(String pk) {
		String msg = Constants.RESULT_SUCCESS_MSG;
		try {
			Query query = new Query(new Criteria().andOperator(Criteria.where("id").is(pk)));
			mongoTemplate.remove(query, B2bFutures.class);
		} catch (Exception e) {
			msg = Constants.RESULT_FAIL_MSG;
			e.printStackTrace();
		}
		return msg;
	}

	@Override
	public List<B2bFuturesTypeDto> searchFuturesTypeList(String flag) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isDelete", 1);
		map.put("isVisable", 1);
		map.put("block", flag);
		return futuresTypeDaoEx.searchGrid(map);
	}

	@Override
	public String updateFuturesType(B2bFuturesType futuresType) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(new Date());
		String msg = Constants.RESULT_SUCCESS_MSG;
		boolean flag = true;
		try {
			//非禁用操作判断名称是否重复
			if (futuresType.getIsVisable()==null||futuresType.getIsVisable().equals("")) {
				flag =checkFuturesTypeName(futuresType);
				if (!flag) {
					msg = "{\"success\":false,\"msg\":\"该期货品种已存在!\"}";
				}
			}else {
				//更新monoge数据
				Criteria c = new Criteria();
				c.andOperator(Criteria.where("futuresPk").is(futuresType.getPk()));
				Query query = new Query(c);
				Update update = Update.update("isVisable", futuresType.getIsVisable()).set("updateTime", dateString);
				mongoTemplate.updateMulti(query, update, B2bFutures.class);
			}
			if (flag) {
				if (futuresType.getPk() != null && !futuresType.getPk().equals("")) {
					futuresType.setUpdateTime(new Date());
					futuresTypeDaoEx.updateEx(futuresType);
				} else {
					futuresType.setPk(KeyUtils.getUUID());
					futuresTypeDaoEx.insertEx(futuresType);
				}
			}
		} catch (Exception e) {
			msg = Constants.RESULT_FAIL_MSG;
			e.printStackTrace();
		}
		return msg;
	}

	private boolean checkFuturesTypeName(B2bFuturesType futuresType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", futuresType.getName());
		map.put("block", futuresType.getBlock());
		map.put("pk", futuresType.getPk());
		B2bFuturesTypeDto dto = futuresTypeDaoEx.isExist(map);
		if (dto != null &&!dto.equals("")) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public PageModel<B2bFuturesTypeDtoEx> searchFuturesTypeListGrid(QueryModel<B2bFuturesTypeDtoEx> qm) {
		PageModel<B2bFuturesTypeDtoEx> pm = new PageModel<B2bFuturesTypeDtoEx>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", qm.getStart());
		map.put("limit", qm.getLimit());
		map.put("orderName", qm.getFirstOrderName());
		map.put("orderType", qm.getFirstOrderType());
		map.put("name", qm.getEntity().getName());
		map.put("updateStartTime", qm.getEntity().getUpdateStartTime());
		map.put("updateEndTime", qm.getEntity().getUpdateEndTime());
		map.put("block", qm.getEntity().getBlock());
		map.put("isDelete", 1);
		int totalCount = futuresTypeDaoEx.searchGridExtCount(map);
		List<B2bFuturesTypeDtoEx> list = futuresTypeDaoEx.searchGridExt(map);
		pm.setTotalCount(totalCount);
		pm.setDataList(list);
		return pm;
	}


}
