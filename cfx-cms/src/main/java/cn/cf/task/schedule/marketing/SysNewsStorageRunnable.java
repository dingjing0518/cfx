package cn.cf.task.schedule.marketing;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;

import com.alibaba.fastjson.JSON;

import cn.cf.DataMaps;
import cn.cf.common.Constants;
import cn.cf.common.ExportDoJsonParams;
import cn.cf.common.ExportUtil;
import cn.cf.common.utils.BeanUtils;
import cn.cf.common.utils.CommonUtil;
import cn.cf.common.utils.OSSUtils;
import cn.cf.common.utils.ZipUtils;
import cn.cf.dao.SysExcelStoreExtDao;
import cn.cf.dto.SysExcelStoreDto;
import cn.cf.dto.SysExcelStoreExtDto;
import cn.cf.entity.CustomerDataTypeParams;
import cn.cf.entity.ExportSysNewsStorageEntity;
import cn.cf.entity.SysNewsStorageEntity;
import cn.cf.model.SysExcelStore;
import cn.cf.task.schedule.ScheduledFutureMap;
import cn.cf.util.DateUtil;

public class SysNewsStorageRunnable  implements Runnable {
	private String name;

	private String fileName;
	
	private String accountPk;
	
	private String uuid;

	private SysExcelStoreExtDto storeDtoTemp;

	private SysExcelStoreExtDao storeDao;

	public SysNewsStorageRunnable () {

	}

	
	public SysNewsStorageRunnable (String name,String uuid) {
		
		this.name = name;
		
		this.uuid = uuid;
	}

	
	@Override
	public void run() {
        //上传数据
        ScheduledFuture future = null;
        if (CommonUtil.isNotEmpty(this.name)) {
        	future = ScheduledFutureMap.map.get(this.name);
		}
        try {
            upLoadFile();
        } catch (Exception e) {
			ExportDoJsonParams.updateErrorExcelStoreStatus(this.storeDao,this.storeDtoTemp,e.getMessage());
        } finally {
            ScheduledFutureMap.stopFuture(future,this.name);
        }
    }


	private void upLoadFile() {
		this.storeDao = (SysExcelStoreExtDao) BeanUtils.getBean("sysExcelStoreExtDao");
		MongoTemplate   mongoTemplate = (MongoTemplate)BeanUtils.getBean("mongoTemplate");
		if (storeDao != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("isDeal", Constants.TWO);
			map.put("methodName", "exportSysNewsStorageEntity_"+StringUtils.defaultIfEmpty(this.uuid, ""));
			// 查询存在要导出的任务
			List<SysExcelStoreExtDto> list = storeDao.getFirstTimeExcelStore(map);
			if (list != null && list.size() > Constants.ZERO) {
				for (SysExcelStoreExtDto storeDto : list) {
                    this.storeDtoTemp = storeDto;
					this.fileName = "运营中心-资讯中心-文章列表-" + storeDto.getAccountName() + "-"
							+ DateUtil.formatYearMonthDayHMS(new Date());
					if (CommonUtil.isNotEmpty(storeDto.getParams())) {
						CustomerDataTypeParams params = JSON.parseObject(storeDto.getParams(),
								CustomerDataTypeParams.class);
						this.accountPk = storeDto.getAccountPk();
						if (mongoTemplate != null) {
							// 设置查询参数
							Criteria criteria  = getCriteria(params, storeDto);
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
					                .first("recommendPosition").as("recommendPosition")
					        );  
					        List<SysNewsStorageEntity> consultReportSearch = mongoTemplate.aggregate(aggregation, SysNewsStorageEntity.class).getMappedResults(); 
							// 设置权限
							String ossPath = "";
							int counts = consultReportSearch.size();
							if (counts > Constants.EXCEL_NUMBER_TENHOUSAND) {
								// 大于10000，分页查询数据
								ossPath = setLimitPages(criteria, counts, mongoTemplate, storeDto);
							} else {
								// 如果小于或等于10000条直接上传
								ossPath = setNotLimitPages(consultReportSearch, storeDto);
							}
							// 更新订单导出状态

							ExportDoJsonParams.updateExcelStoreStatus(storeDto,storeDao, ossPath);
						}
					}
				}
			}
		}
	}

	private String setLimitPages(Criteria criteria, int counts, MongoTemplate mongoTemplate,
			SysExcelStoreExtDto storeDto) {
		double numbers = Math.floor(counts / Constants.EXCEL_NUMBER_TENHOUSAND);// 获取分页查询次数
		List<File> fileList = new ArrayList<>();
		for (int i = Constants.ZERO; i < numbers + Constants.ONE; i++) { //
			int start = Constants.ZERO;
			if (i != Constants.ZERO) {
				start = ExportDoJsonParams.indexPageNumbers(i);
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
		                .first("recommend").as("recommend")
		                .first("top").as("top")
		                .first("keyword").as("keyword")  
		                .first("newAbstrsct").as("newAbstrsct")
		                .first("url").as("url")  
		                .first("status").as("status")
		                .first("estimatedTime").as("estimatedTime")
						.first("newSource").as("newSource")
						.first("pvCount").as("pvCount")
		                .first("recommendPosition").as("recommendPosition"),
		                Aggregation.skip(start),  
		                Aggregation.limit(Constants.EXCEL_NUMBER_TENHOUSAND) 
		        );  
		        List<SysNewsStorageEntity> consultReportSearch = mongoTemplate.aggregate(aggregation, SysNewsStorageEntity.class).getMappedResults();
				List<ExportSysNewsStorageEntity> list = getCol(consultReportSearch);
		        if (list != null && list.size() > Constants.ZERO) {
				// 拼接数据，根据总订单号统计所有子订单的重量和金额
				ExportUtil<ExportSysNewsStorageEntity> exportUtil = new ExportUtil<ExportSysNewsStorageEntity>();
				String excelName = "运营中心-资讯中心-文章列表-" + storeDto.getAccountName() + "-"
						+   DateUtil.formatYearMonthDayHMS(new Date())+"-"+(i+1);
				String path = exportUtil.exportDynamicUtil("sysNews", excelName, list);
				File file = new File(path);
				fileList.add(file);
			}
		}
		// 上传Zip到OSS
		return OSSUtils.ossUploadXls(new File(ZipUtils.fileToZip(fileList, this.fileName)), Constants.FILE);
	}


	private String setNotLimitPages(List<SysNewsStorageEntity> consultReportSearch, SysExcelStoreExtDto storeDto) {
		String ossPath = "";
		List<ExportSysNewsStorageEntity> nameList = getCol(consultReportSearch);
    	if (nameList != null && nameList.size() > Constants.ZERO) {
			ExportUtil<ExportSysNewsStorageEntity> exportUtil = new ExportUtil<ExportSysNewsStorageEntity>();
			String path = exportUtil.exportDynamicUtil("sysNews", this.fileName, nameList);
			File file = new File(path);
			// 上传到OSS
			ossPath = OSSUtils.ossUploadXls(file, Constants.FIVE);
		}
		return ossPath;
	}


	private List<ExportSysNewsStorageEntity> getCol(List<SysNewsStorageEntity> consultReportSearch) {
		List<ExportSysNewsStorageEntity> nameList = new ArrayList<ExportSysNewsStorageEntity>();
		if (consultReportSearch!=null && consultReportSearch.size()>0) {
    	for (SysNewsStorageEntity sysNewsStorageEntity : consultReportSearch) {
    		ExportSysNewsStorageEntity entity = new ExportSysNewsStorageEntity();
    		
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
    		
            entity.setInsertTime(sysNewsStorageEntity.getInsertTime());
           
            if(sysNewsStorageEntity.getStatus() != null && sysNewsStorageEntity.getStatus() == 2){
            	 entity.setStatusName("已发布");
            }else{
            	 entity.setStatusName("未发布");	
            }
            if(sysNewsStorageEntity.getRecommendPosition() != null && sysNewsStorageEntity.getRecommendPosition() == -1){
            	entity.setRecommendPosition("暂无");
            } else if(sysNewsStorageEntity.getRecommendPosition() != null && sysNewsStorageEntity.getRecommendPosition() == 1){
            	entity.setRecommendPosition("首页资讯模块");
            }else if(sysNewsStorageEntity.getRecommendPosition() != null && sysNewsStorageEntity.getRecommendPosition() == 2){
            	entity.setRecommendPosition("行情中心模块");
            }else if(sysNewsStorageEntity.getRecommendPosition() != null && sysNewsStorageEntity.getRecommendPosition() == 3){
            	entity.setRecommendPosition("首页资讯模块,行情中心模块");
            }else{
            	entity.setRecommendPosition("暂无");
            }
            
            entity.setKeyword(sysNewsStorageEntity.getKeyword());
            if(sysNewsStorageEntity.getTop() != null && sysNewsStorageEntity.getTop() == 1){
            	entity.setTopName("是");
            }else{
            	entity.setTopName("否");
            }
            if(sysNewsStorageEntity.getIsVisable() != null && sysNewsStorageEntity.getIsVisable() == 1){
            	entity.setIsVisableName("启用");
            }else{
            	entity.setIsVisableName("禁用");
            }
            entity.setPvCount(sysNewsStorageEntity.getPvCount());
            entity.setTitle(sysNewsStorageEntity.getTitle());
            nameList.add(entity);
		}
	}
		return nameList;
	}


	private Criteria getCriteria(CustomerDataTypeParams params, SysExcelStoreExtDto storeDto) {
		String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(storeDto.getInsertTime());

		Criteria criteria = new Criteria();
		 criteria =  Criteria.where("_id").nin("");
		 
		if(null!=params.getCategoryPk() && !"".equals(params.getCategoryPk())){
			 criteria.and("categoryPk").is(params.getCategoryPk());
		 }
		if(null!=params.getParentId() && !"".equals(params.getParentId())){
			 criteria.and("parentId").is(Integer.parseInt(params.getParentId()));
		 }
		if(null!=params.getIsVisable() && !"".equals(params.getIsVisable())){
			 criteria.and("isVisable").is(Integer.parseInt(params.getIsVisable()));
		 }
		if(null!=params.getRecommend() && !"".equals(params.getRecommend())){
			 criteria.and("recommend").is(Integer.parseInt(params.getRecommend()));
		 }
		if(null!=params.getTop() && !"".equals(params.getTop())){
			 criteria.and("top").is(Integer.parseInt(params.getTop()));
		 }
		if(null!=params.getKeyword() && !"".equals(params.getKeyword())){
			 criteria.and("keyword").regex(params.getKeyword());
		 }
		if(null!=params.getStatus() && !"".equals(params.getStatus())){
			 criteria.and("status").is(Integer.parseInt(params.getStatus()));
		 }
		if(null!=params.getTitle() && !"".equals(params.getTitle())){
			 criteria.and("title").regex(params.getTitle());
		 }
		if(null!=params.getIsPush() && !"".equals(params.getIsPush())){
			criteria.and("isPush").is(Integer.parseInt(params.getIsPush()));
		}
		 criteria.and("isDelete").is(1);
		 criteria.and("insertTime").lte(now);
		return criteria;
	}


	public static String sysNewsStorageParam(CustomerDataTypeParams params, SysExcelStore store) {
		String paramStr = "";
		if (CommonUtil.isNotEmpty(params.getIsVisable())) {
			paramStr = paramStr+"是否启用：" + getIVisable(params.getIsVisable())+";";
		}
		if (CommonUtil.isNotEmpty(params.getTop())) {
			paramStr = paramStr + "是否置顶：" + getTop(params.getTop())+";";
		}
		if (CommonUtil.isNotEmpty(params.getParentId())) {
			paramStr = paramStr + "所属系统：" + getParentId(params.getParentId())+";";
		}
		if (CommonUtil.isNotEmpty(params.getCategoryPk())) {
			paramStr = paramStr + "分类名称：" + params.getCategoryName()+";";
		}
		if (CommonUtil.isNotEmpty(params.getKeyword())) {
			paramStr = paramStr + "关键词：" + params.getKeyword()+";";
		}
		if (CommonUtil.isNotEmpty(params.getTitle())) {
			paramStr = paramStr + "标题：" + params.getTitle()+";";
		}
		if (CommonUtil.isNotEmpty(params.getIsPush())) {
			paramStr = paramStr + "是否推送：" + getIsPush(params.getIsPush())+";";
		}
		if (CommonUtil.isNotEmpty(params.getStatus())) {
			paramStr = paramStr + "是否发布：" + getStatus(params.getStatus())+";";
		}
		return paramStr;
	}

	private static String getStatus(String status) {
		String result = "";
		if (status.equals("1")) {
			result = "未发布";
		} else if (status.equals("2")) {
			result = "已发布";
		}
		return result;
	}


	private static String getIsPush(String isPush) {
		String result = "";
		if (isPush.equals("1")) {
			result = "未推送";
		} else if (isPush.equals("2")) {
			result = "已推送";
		}
		return result;
	}


	private static String getParentId(String parentId) {
		String result = "";
		if (parentId.equals("1")) {
			result = "电商系统";
		} else if (parentId.equals("2")) {
			result = "物流系统";
		} else if (parentId.equals("3")) {
			result = "金融系统";
		} 
		return result;
	}


	private static String getTop(String top) {
		String result = "";
		if (top.equals("1")) {
			result = "是";
		} else if (top.equals("2")) {
			result = "否";
		} 
		return result;
	}


	private static String getIVisable(String isVisable) {
		String result = "";
		if (isVisable.equals("1")) {
			result = "启用";
		} else if (isVisable.equals("2")) {
			result = "禁用";
		} 
		return result;
	}
	
}
