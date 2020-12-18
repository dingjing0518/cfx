package cn.cf.task.schedule.marketing;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.alibaba.fastjson.JSON;

import cn.cf.common.ColAuthConstants;
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
import cn.cf.entity.PurchaserSaleReportForms;
import cn.cf.entity.ReportFormsDataTypeParams;
import cn.cf.model.SysExcelStore;
import cn.cf.task.schedule.ScheduledFutureMap;
import cn.cf.util.DateUtil;

public class PurchaserSaleReportRunnable  implements Runnable{

	private String name;

	private String fileName;
	
	private String accountPk;
	
	private String uuid;
	private SysExcelStoreExtDto storeDtoTemp;

	private SysExcelStoreExtDao storeDao;
	
	public PurchaserSaleReportRunnable() {

	}
	
	
	public PurchaserSaleReportRunnable(String name,String uuid) {

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

		MongoTemplate mongoTemplate = (MongoTemplate) BeanUtils.getBean("mongoTemplate");
		this.storeDao = (SysExcelStoreExtDao) BeanUtils.getBean("sysExcelStoreExtDao");
		if (storeDao != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("isDeal", Constants.TWO);
			map.put("methodName", "exportPurchaserSaleList_"+StringUtils.defaultIfEmpty(this.uuid, ""));
			// 查询存在要导出的任务
			List<SysExcelStoreExtDto> list = storeDao.getFirstTimeExcelStore(map);
			if (list != null && list.size() > Constants.ZERO) {
				for (SysExcelStoreExtDto storeDto : list) {
					this.storeDtoTemp = storeDto;
					this.fileName = "营销中心-数据管理-采购商交易统计" + storeDto.getAccountName() + "-"
							+  DateUtil.formatYearMonthDayHMS(new Date());
					if (CommonUtil.isNotEmpty(storeDto.getParams())) {
						ReportFormsDataTypeParams params = JSON.parseObject(storeDto.getParams(),
								ReportFormsDataTypeParams.class);
						this.accountPk = storeDto.getAccountPk();
						if (mongoTemplate != null) {
							// 设置查询参数
							Query query = reportParams(params, storeDto);
							// 设置权限
							String ossPath = "";
							int counts = (int) mongoTemplate.count(query, PurchaserSaleReportForms.class);
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
		List<PurchaserSaleReportForms> list = mongoTemplate.find(query, PurchaserSaleReportForms.class);
		if (list != null && list.size() > Constants.ZERO) {
			 getCol(list);
			ExportUtil<PurchaserSaleReportForms> exportUtil = new ExportUtil<PurchaserSaleReportForms>();
			String path = exportUtil.exportDynamicUtil("purchaserSaleRF", this.fileName, list);
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
			List<PurchaserSaleReportForms> list = mongoTemplate.find(query, PurchaserSaleReportForms.class);
	        
			if (list != null && list.size() > Constants.ZERO) {
				    getCol(list);
				ExportUtil<PurchaserSaleReportForms> exportUtil = new ExportUtil<PurchaserSaleReportForms>();
				String excelName = "营销中心-数据管理-采购商交易统计-" + storeDto.getAccountName() + "-"
						+  DateUtil.formatYearMonthDayHMS(new Date())+"-"+(i+1);
				String path = exportUtil.exportDynamicUtil("purchaserSaleRF", excelName, list);
				File file = new File(path);
				fileList.add(file);
			}
		}
		// 上传Zip到OSS
		return OSSUtils.ossUploadXls(new File(ZipUtils.fileToZip(fileList, this.fileName)), Constants.FILE);
	}

	private void getCol(List<PurchaserSaleReportForms> list) {for (PurchaserSaleReportForms p : list) {
	    if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_REPORT_PURCHASERSALE_COL_PURNAME)) {
            p.setPurchaserName(CommonUtil.hideCompanyName(p.getPurchaserName()));
        }
	    if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_REPORT_PURCHASERSALE_COL_AUCCOUNTNAME)) {
            p.setAccountName(CommonUtil.hideContacts(p.getAccountName()));
        }
    }}

	private Query reportParams(ReportFormsDataTypeParams params, SysExcelStoreExtDto storeDto) {
		Query query = new Query();
		query.addCriteria(Criteria.where("year").is(params.getYear()));
		return query;
	}

	public static String purchaserSaleReportParam(ReportFormsDataTypeParams params, SysExcelStore store) {
		String paramStr = "";
		if (CommonUtil.isNotEmpty(params.getYear())) {
			paramStr = paramStr + "年份：" + params.getYear();
		} 
		return paramStr;
	}
	
	
	
	
	
}
