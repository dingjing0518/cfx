package cn.cf.task.schedule.operation;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.alibaba.fastjson.JSON;

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
import cn.cf.entity.FlowDataReport;
import cn.cf.entity.ReportFormsDataTypeParams;
import cn.cf.model.SysExcelStore;
import cn.cf.task.schedule.ScheduledFutureMap;
import cn.cf.util.DateUtil;

public class FlowDataRunnable implements Runnable {
	
	private String name;

	private String fileName;
	
	private String accountPk;
	
	private String uuid;

	private SysExcelStoreExtDao storeDao;
	private SysExcelStoreExtDto storeDtoTemp;
	public FlowDataRunnable() {

	}

	public FlowDataRunnable(String name,String uuid) {

		this.name = name;
		
		this.uuid =uuid;
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

		MongoTemplate mongoTemplate = (MongoTemplate) BeanUtils.getBean("mongoTemplate");
		this.storeDao = (SysExcelStoreExtDao) BeanUtils.getBean("sysExcelStoreExtDao");
		if (storeDao != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("isDeal", Constants.TWO);
			map.put("methodName", "exportFlowData_"+StringUtils.defaultIfEmpty(this.uuid, ""));
			// 查询存在要导出的任务
			List<SysExcelStoreExtDto> list = storeDao.getFirstTimeExcelStore(map);
			if (list != null && list.size() > Constants.ZERO) {
				for (SysExcelStoreExtDto storeDto : list) {
					this.storeDtoTemp = storeDto;
					this.fileName = "运营中心-数据报表-流量数据-" + storeDto.getAccountName() + "-"
							+  DateUtil.formatYearMonthDayHMS(new Date());
					if (CommonUtil.isNotEmpty(storeDto.getParams())) {
						ReportFormsDataTypeParams params = JSON.parseObject(storeDto.getParams(),
								ReportFormsDataTypeParams.class);
						this.accountPk = storeDto.getAccountPk();
						if (mongoTemplate != null) {
							String ossPath = "";
							
								Query query = reportParams(params, storeDto);
								// 设置查询参数
								int counts = (int) mongoTemplate.count(query, FlowDataReport.class);
								if (counts > Constants.EXCEL_NUMBER_TENHOUSAND) {
									// 大于10000，分页查询数据
									ossPath = setLimitPages(query, counts, mongoTemplate, storeDto);
								} else {
									// 如果小于或等于10000条直接上传
									ossPath = setNotLimitPages(query, mongoTemplate, storeDto);
								}
							// 更新订单导出状态
							ExportDoJsonParams.updateExcelStoreStatus(storeDto,storeDao, ossPath);
						}
						
					
				}
			}
		}
		}
	}
	private String setNotLimitPages(Query query, MongoTemplate mongoTemplate, SysExcelStoreExtDto storeDto) {
		String ossPath = "";
		List<FlowDataReport>	list =	mongoTemplate.find(query, FlowDataReport.class);
		if (list != null && list.size() > Constants.ZERO) {
			ExportUtil<FlowDataReport> exportUtil = new ExportUtil<FlowDataReport>();
			String path = exportUtil.exportDynamicUtil("flowData", this.fileName, list);
			File file = new File(path);
			// 上传到OSS
			ossPath = OSSUtils.ossUploadXls(file, Constants.FIVE);
		}
		return ossPath;
	}



	private String setLimitPages(Query query, int counts, MongoTemplate mongoTemplate, SysExcelStoreExtDto storeDto) {
		double numbers = Math.floor(counts / Constants.EXCEL_NUMBER_TENHOUSAND);// 获取分页查询次数
		
		List<File> fileList = new ArrayList<>();
		for (int i = Constants.ZERO; i < numbers + Constants.ONE; i++) { //
			int start = Constants.ZERO;
			if (i != Constants.ZERO) {
				start = ExportDoJsonParams.indexPageNumbers(i);
			}
			query.skip(start).limit(Constants.EXCEL_NUMBER_TENHOUSAND);
			List<FlowDataReport>	list =	mongoTemplate.find(query, FlowDataReport.class);
			if (list != null && list.size() > Constants.ZERO) {
				ExportUtil<FlowDataReport> exportUtil = new ExportUtil<FlowDataReport>();
				String excelName = "运营中心-数据报表-流量数据" + storeDto.getAccountName() + "-"
						+ DateUtil.formatYearMonthDayHMS(new Date())+"-"+(i+1);
				String path = exportUtil.exportDynamicUtil("bussStoreDataReport", excelName, list);
				File file = new File(path);
				fileList.add(file);
			}
		}
		// 上传Zip到OSS
		return OSSUtils.ossUploadXls(new File(ZipUtils.fileToZip(fileList, this.fileName)), Constants.FILE);
	}



	private Query reportParams(ReportFormsDataTypeParams params, SysExcelStoreExtDto storeDto) {
		Query query = new Query();
		if (params.getStartTime() != null && !params.getStartTime().equals("")) {
			if (params.getEndTime() != null && !params.getEndTime().equals("")) {
				query.addCriteria(Criteria.where("date").gte(params.getStartTime()).lte(params.getEndTime()));
			} else {
				query.addCriteria(Criteria.where("date").gte(params.getStartTime()));
			}
		} else if (params.getEndTime() != null && !params.getEndTime().equals("")) {
			query.addCriteria(Criteria.where("date").lte(params.getEndTime()));
		}
		
		if (null != params.getIds() && !"".equals(params.getIds())) {
			List<String> idsList = getIds(params);
			query.addCriteria(Criteria.where("_id").in(idsList));
		}
		query.with(new Sort(Direction.DESC, "date"));
		return query;
	}

	private static List<String> getIds(ReportFormsDataTypeParams params) {
		List<String> idsList = new ArrayList<>();
		if (params.getIds().contains(",")) {
			String[] idarr = params.getIds().split(",");
			for (int i = 0; i < idarr.length; i++) {
				idsList.add(idarr[i]);
			}
		} else {
			idsList.add(params.getIds());
		}
		return idsList;
	}

	public static String flowDataParam(ReportFormsDataTypeParams params, SysExcelStore store) {
		String paramStr = "";
		
		paramStr = CommonUtil.getDateShow(paramStr,  params.getStartTime(), params.getEndTime(),  "日期：");
		if (null != params.getIds() && !"".equals( params.getIds())) {
			List<String> idsList = getIds(params);
			paramStr = paramStr + "勾选" + idsList.size()+"条数据;";
		}
		return paramStr;
	}



}
