package cn.cf.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import cn.cf.PageModel;
import cn.cf.dao.SysNewsDaoEx;
import cn.cf.dto.SysNewsDto;
import cn.cf.dto.SysNewsDtoEx;
import cn.cf.entity.SysNewsStorageEntity;
import cn.cf.model.SysNews;
import cn.cf.service.SysNewsService;
import cn.cf.util.DateUtil;

@Service
public class SysNewsServiceImpl implements SysNewsService {
	

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private SysNewsDaoEx sysNewsDaoEx;

	@Override
	public PageModel<SysNewsStorageEntity> searchNews (Map<String, Object> map) {
		PageModel<SysNewsStorageEntity> pm = new PageModel<SysNewsStorageEntity>();
		Integer coordinate = Integer.valueOf(map.get("coordinate").toString());
		Long start =Long.valueOf(map.get("start").toString());
		Integer limit = Integer.parseInt(map.get("limit").toString());
		Criteria criteria=buildNewsCriteriaOne(map);
		List<SysNewsStorageEntity> nameList = mongosSearch(criteria, start,limit,coordinate);
		int count=(int) mongoTemplate.count(new Query().addCriteria(criteria),SysNewsStorageEntity.class);
		pm.setDataList(nameList);
		pm.setTotalCount(count);
		pm.setStartIndex(Integer.parseInt(map.get("start").toString()));
		pm.setPageSize(Integer.parseInt(map.get("limit").toString()));
		return pm;
	}
	
//	private Integer searchnewsCount(Map<String, Object> map) {
//		StringBuilder query = addNewsCriteria(map);
//		String jsonSql = "{'distinct':'sysNewsStorageEntity', 'key':'newsPk', 'query':"+query+" }";
//		CommandResult commandResult = mongoTemplate.executeCommand(jsonSql);
//		BasicDBList list = (BasicDBList) commandResult.get("values");
//		return list.size();
//	}
	
	
	
	private List<SysNewsStorageEntity> mongosSearch(Criteria criteria, Long start, Integer limit, Integer coordinate) {
		Sort sort1 = null;
		Sort sort2 = null;
		// 最新资讯 （左上） coordinate 1
		if (coordinate == 1 || coordinate == 2 || coordinate == 7) {
			sort1 = new Sort(Direction.DESC, "insertTime");
		} else if (coordinate == 3 || coordinate == 5) {// 左下列表
			sort1 = new Sort(Direction.ASC, "top");
			sort2 = new Sort(Direction.DESC, "insertTime");
		} else if (coordinate == 4 || coordinate == 6) {// 排行榜//推荐
			sort1 = new Sort(Direction.DESC, "pvCount");
			sort2 = new Sort(Direction.DESC, "insertTime");
		}
		TypedAggregation<SysNewsStorageEntity> aggregation = Aggregation.newAggregation(SysNewsStorageEntity.class,Aggregation.match(criteria),
				Aggregation.group("newsPk").first("newsPk").as("newsPk").first("newSource").as("newSource")
						.first("categoryPk").as("categoryPk").first("newsPk").as("pk").first("title").as("title")
						.first("insertTime").as("insertTime").first("newAbstrsct").as("newAbstrsct").first("top")
						.as("top").first("url").as("url").first("status").as("status").first("pvCount").as("pvCount"),
				Aggregation.sort(sort1).and(sort2), Aggregation.skip(start), Aggregation.limit(limit));
		
		List<SysNewsStorageEntity> consultReportSearch = mongoTemplate.aggregate(aggregation, SysNewsStorageEntity.class).getMappedResults();
		return consultReportSearch;
	}


//	private StringBuilder addNewsCriteria(Map<String, Object> map) {
//		StringBuilder searchQuery = new StringBuilder();
//		searchQuery.append("{_id:{$nin:['']},$and:[{ 'categoryPk' : { $nin : [ '' ]},'isVisable' : 1,'isDelete' : 1,'status' : 2} ],");
//		if (null != map.get("categoryPk") && !"".equals(map.get("categoryPk"))) {
//			String categoryPk = (String) map.get("categoryPk");
//			searchQuery.append("'categoryPk':").append("\"" + categoryPk + "\",");
//		}
//		if (null != map.get("title") && !"".equals(map.get("title").toString())) {
//			String title = map.get("title").toString();
//			//Pattern pattern = Pattern.compile(".*?" + escapeExprSpecialWord(title) + ".*");
//			searchQuery.append("title:").append("{$regex:'"+escapeExprSpecialWord(title)+"'},");
//		}
//		if (null != map.get("pk") && !"".equals(map.get("pk").toString())) {
//			String pk = map.get("pk").toString();
//			searchQuery.append("newsPk:").append("{$ne:'" + pk + "'},");
//		}
//		if (null != map.get("parentId") && !"".equals(map.get("parentId").toString())) {
//			String parentId = map.get("parentId").toString();
//			searchQuery.append("parentId:").append(parentId+",");
//		}
//		if (null != map.get("coordinate") && !"".equals(map.get("coordinate").toString())) {// 行情中心模块
//			Integer coordinate = (Integer) map.get("coordinate");
//			if (coordinate == 2) {
//				searchQuery.append("recommendPosition:").append("{ $in: [2, 3] },");
//			}
//			if (coordinate == 7) {// 首页资讯模块
//				searchQuery.append("recommendPosition:").append("{ $in: [1, 3] },");
//			}
//			searchQuery.append("}");
//		}
//		return searchQuery;
//	}

	/*
	 * CMS系统修改任何category，都要即时更新mongodb 查找未删除，未禁用的符合条件的news集合
	 */
	@Override
	public PageModel<SysNewsStorageEntity> searchNews_old(Map<String, Object> map) throws Exception {
		Integer coordinate = Integer.valueOf(map.get("coordinate").toString());
		Integer start = Integer.parseInt(map.get("start").toString());
		Integer limit = Integer.parseInt(map.get("limit").toString());
		List<Criteria> cList = buildNewsCriteria(map);
		PageModel<SysNewsStorageEntity> pm = new PageModel<SysNewsStorageEntity>();
		Query query = new Query(new Criteria().andOperator(cList.toArray(new Criteria[cList.size()])));
		Sort sort = null;
		List<Order> orderList = new ArrayList<Order>();
		// 最新资讯 （左上） coordinate 1
		if (coordinate == 1) {
			Order orderCurrentPrice = new Order(Direction.DESC, "insertTime");
			orderList.add(orderCurrentPrice);
			sort = new Sort(orderList);
			query.with(sort);
		} else if (coordinate == 2) {// 右上
			Order orderCurrentPrice = new Order(Direction.DESC, "insertTime");
			orderList.add(orderCurrentPrice);
			sort = new Sort(orderList);
			query.with(sort);
		} else if (coordinate == 3) {// 左下列表
			Order orderCurrentPrice = new Order(Direction.ASC, "top");
			orderList.add(orderCurrentPrice);
			Order orderCurrentPrice2 = new Order(Direction.DESC, "insertTime");
			orderList.add(orderCurrentPrice2);
			sort = new Sort(orderList);
			query.with(sort);
		} else if (coordinate == 4 || coordinate == 6) {// 排行榜//推荐
			Order orderCurrentPrice = new Order(Direction.DESC, "pvCount");
			orderList.add(orderCurrentPrice);
			Order orderCurrentPrice2 = new Order(Direction.DESC, "insertTime");
			orderList.add(orderCurrentPrice2);
			sort = new Sort(orderList);
			query.with(sort);
		} else if (coordinate == 5) {
			Order orderCurrentPrice = new Order(Direction.ASC, "top");
			orderList.add(orderCurrentPrice);
			Order orderCurrentPrice2 = new Order(Direction.DESC, "insertTime");
			orderList.add(orderCurrentPrice2);
			sort = new Sort(orderList);
			query.with(sort);
		} else if (coordinate == 7) {// 首页资讯模块
			Order orderCurrentPrice = new Order(Direction.DESC, "insertTime");
			orderList.add(orderCurrentPrice);
			sort = new Sort(orderList);
			query.with(sort);
		} else {
			Order orderCurrentPrice = new Order(Direction.ASC, "top");
			orderList.add(orderCurrentPrice);
			Order orderCurrentPrice2 = new Order(Direction.DESC, "insertTime");
			orderList.add(orderCurrentPrice2);
			sort = new Sort(Direction.ASC, "top");
			query.with(sort);
		}
		List<SysNewsStorageEntity> list = mongoTemplate.find(query, SysNewsStorageEntity.class);
		Integer counts = (int) mongoTemplate.count(query, SysNewsStorageEntity.class);
		// String newsPk = "";
		// if (map.containsKey("pk")) {
		// newsPk = map.get("pk").toString();
		// }
		System.out.println("--------" + query);
		List<SysNewsStorageEntity> subList = new ArrayList<SysNewsStorageEntity>();
		if (list.size() > 0) {
			for (int i = 0; i < list.size() - 1; i++) {
				list.get(i).setPk(list.get(i).getNewsPk());
				list.get(i).setContent("");
				for (int j = list.size() - 1; j > i; j--) {
					list.get(j).setContent("");
					list.get(j).setPk(list.get(j).getNewsPk());
					if (list.get(j).getNewsPk().equals(list.get(i).getNewsPk())) {
						list.remove(j);
						counts = counts - 1;
					}
				}
			}
			if (start == 0) {
				subList = list.subList(start, counts - limit > 0 ? limit : counts);
			} else {
				subList = list.subList(start, counts - (start + limit) > 0 ? start + limit : counts);
			}
		}
		pm.setDataList(subList);
		pm.setTotalCount(counts);
		pm.setStartIndex(Integer.parseInt(map.get("start").toString()));
		pm.setPageSize(Integer.parseInt(map.get("limit").toString()));
		return pm;
	}

	
	
	private Criteria buildNewsCriteriaOne(Map<String, Object> map) {
		Criteria c = new Criteria();
		c = Criteria.where("_id").nin("");
		c.andOperator(Criteria.where("categoryPk").nin(""));
		if (null != map.get("categoryPk") && !"".equals(map.get("categoryPk"))) {
			String categoryPk = (String) map.get("categoryPk");
			c.and("categoryPk").is(categoryPk);
		}
		if (null != map.get("parentId") && !"".equals(map.get("parentId"))) {
			Integer parentId = Integer.parseInt(map.get("parentId").toString()) ;
			c.and("parentId").is(parentId);
		}
		c.and("isDelete").is(1);
		c.and("isVisable").is(1);
		c.and("status").is(2);
		if (null != map.get("title") && !"".equals(map.get("title").toString())) {
			String title = map.get("title").toString();
			Pattern pattern = Pattern.compile("^.*"+escapeExprSpecialWord(title)+".*$",Pattern.CASE_INSENSITIVE);
			c.and("title").regex(pattern);
		}
		if (null != map.get("pk") && !"".equals(map.get("pk").toString())) {
			String pk = map.get("pk").toString();
			c.and("newsPk").ne(pk);
		}
		if (null != map.get("coordinate") && !"".equals(map.get("coordinate").toString())) {// 行情中心模块
			Integer coordinate = (Integer) map.get("coordinate");
			if (coordinate == 2) {
				c.and("recommendPosition").ne(1).gt(0);
			}
			if (coordinate == 7) {// 首页资讯模块
				c.and("recommendPosition").ne(2).gt(0);
			}
		}
		return c;
	}

	private List<Criteria> buildNewsCriteria(Map<String, Object> map) {
		List<Criteria> cList = new ArrayList<Criteria>(0);
		Criteria c = new Criteria();
		c = Criteria.where("_id").nin("");
		c.andOperator(Criteria.where("categoryPk").nin(""));
		if (null != map.get("categoryPk") && !"".equals(map.get("categoryPk"))) {
			String categoryPk = (String) map.get("categoryPk");
			c.and("categoryPk").is(categoryPk);
		}
		c.and("isDelete").is(1);
		c.and("isVisable").is(1);
		c.and("status").is(2);
		if (null != map.get("title") && !"".equals(map.get("title").toString())) {
			String title = map.get("title").toString();
			Pattern pattern = Pattern.compile(".*?" + escapeExprSpecialWord(title) + ".*");
			c.and("title").regex(pattern);
		}
		if (null != map.get("pk") && !"".equals(map.get("pk").toString())) {
			String pk = map.get("pk").toString();
			c.and("newsPk").ne(pk);
		}
		if (null != map.get("coordinate") && !"".equals(map.get("coordinate").toString())) {// 行情中心模块
			Integer coordinate = (Integer) map.get("coordinate");
			if (coordinate == 2) {
				c.and("recommendPosition").ne(1).gt(0);
			}
			if (coordinate == 7) {// 首页资讯模块
				c.and("recommendPosition").ne(2).gt(0);
			}
		}
		cList.add(c);
		return cList;
	}

	
	 /**
     * 转义正则特殊字符 （$()*+.[]?\^{},|）
     *
     * @param keyword
     * @return
     */
   public static String escapeExprSpecialWord(String keyword) {
       String[] fbsArr = { "\\", "$", "(", ")", "*", "+", ".", "[", "]", "?", "^", "{", "}", "|" };
       for (String key : fbsArr) {
           if (keyword.contains(key)) {
               keyword = keyword.replace(key, "\\" + key);
           }
       }
       return keyword;
   }

	
	@Override
	public SysNewsDtoEx getSysNewsByPk(String pk, String categoryPk) throws Exception {
		SysNewsDto newsDto = sysNewsDaoEx.getByPk(pk);
		if (null != newsDto) {
			newsDto.setPvCount(newsDto.getPvCount() == null ? 0 : newsDto.getPvCount() + 1);
			SysNews model = new SysNews();
			model.UpdateDTO(newsDto);
			sysNewsDaoEx.update(model);
			Query query = new Query();
			query.addCriteria(Criteria.where("newsPk").is(pk));
			Update update = Update.update("pvCount", newsDto.getPvCount());
			mongoTemplate.updateMulti(query, update, SysNewsStorageEntity.class);
		}
		SysNewsDtoEx newsDtoEx = new SysNewsDtoEx();
		if (null == newsDto) {
			return null;
		}
		// 查询上一条和下一条
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pk", newsDto.getPk());
		map.put("insertTime", DateUtil.formatDateAndTime(newsDto.getInsertTime()));
		map.put("categoryPk", categoryPk);
		map.put("isDelete", 1);
		map.put("isVisable", 1);
		map.put("status", 2);
		map.put("type", 1);
		Map<String, Object> prev = sysNewsDaoEx.getNewsByNext(map);
		map.put("type", 2);
		Map<String, Object> next = sysNewsDaoEx.getNewsByNext(map);
		newsDtoEx.setPrevNews(prev);
		newsDtoEx.setNextNews(next);
		BeanUtils.copyProperties(newsDtoEx, newsDto);
		return newsDtoEx;
	}
	
	

}
