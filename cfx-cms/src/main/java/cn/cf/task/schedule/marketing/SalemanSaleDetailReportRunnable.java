package cn.cf.task.schedule.marketing;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;

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
import cn.cf.entity.ReportFormsDataTypeParams;
import cn.cf.entity.SalemanSaleDetailReport;
import cn.cf.model.SysExcelStore;
import cn.cf.task.schedule.ScheduledFutureMap;
import cn.cf.util.DateUtil;

public class SalemanSaleDetailReportRunnable  implements Runnable {
	private String name;

	private String fileName;
	
	private String accountPk;
	
	private String uuid;
	private SysExcelStoreExtDto storeDtoTemp;
    private SysExcelStoreExtDao storeDao;
    public SalemanSaleDetailReportRunnable() {

	}

	public SalemanSaleDetailReportRunnable(String name, String uuid) {

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
			map.put("methodName", "exportsalemanSaleDetailReport_"+StringUtils.defaultIfEmpty(this.uuid, ""));
			// 查询存在要导出的任务
			List<SysExcelStoreExtDto> list = storeDao.getFirstTimeExcelStore(map);
			if (list != null && list.size() > Constants.ZERO) {
				for (SysExcelStoreExtDto storeDto : list) {
					this.storeDtoTemp = storeDto;
					this.fileName = "营销中心-数据管理-业务员交易明细" + storeDto.getAccountName() + "-"
							+ DateUtil.formatYearMonthDayHMS(new Date());
					if (CommonUtil.isNotEmpty(storeDto.getParams())) {
						ReportFormsDataTypeParams params = JSON.parseObject(storeDto.getParams(),
								ReportFormsDataTypeParams.class);
						this.accountPk = storeDto.getAccountPk();
						if (mongoTemplate != null) {
							// 设置查询参数
							Criteria criteria = reportParams(params, storeDto);
							// 设置权限
							String ossPath = "";
							
							TypedAggregation<SalemanSaleDetailReport> aggregationCount = Aggregation.newAggregation(
									SalemanSaleDetailReport.class, Aggregation.match(criteria), Aggregation.group("accountPk")
											.first("accountPk").as("accountPk").first("accountName").as("accountName"));
							AggregationResults<SalemanSaleDetailReport> arCount = mongoTemplate.aggregate(aggregationCount,
									SalemanSaleDetailReport.class);
							int counts = arCount.getMappedResults().size();
							if (counts > Constants.EXCEL_NUMBER_TENHOUSAND) {
								// 大于10000，分页查询数据
								ossPath = setLimitPages(criteria, counts, mongoTemplate, storeDto);
							} else {
								// 如果小于或等于10000条直接上传
								ossPath = setNotLimitPages(criteria, mongoTemplate, storeDto);
							}
							// 更新订单导出状态

							ExportDoJsonParams.updateExcelStoreStatus(storeDto,storeDao, ossPath);
						}
					}
				}
			}
		}

	}
	private String setNotLimitPages(Criteria criteria, MongoTemplate mongoTemplate, SysExcelStoreExtDto storeDto) {
		String ossPath = "";
		TypedAggregation<SalemanSaleDetailReport> aggregation = Aggregation.newAggregation(SalemanSaleDetailReport.class, Aggregation.match(criteria),
				Aggregation.group("accountPk").first("accountPk").as("accountPk").first("accountName")
						.as("accountName").sum("supcompanyNum").as("supcompanyNum").sum("purcompanyNum")
						.as("purcompanyNum").sum("pPOYWeight").as("pPOYWeight").sum("pDTYWeight").as("pDTYWeight")
						.sum("pFDYWeight").as("pFDYWeight").sum("pCUTWeight").as("pCUTWeight").sum("pOtherWeight")
						.as("pOtherWeight").sum("sPOYWeight").as("sPOYWeight").sum("sDTYWeight").as("sDTYWeight")
						.sum("sFDYWeight").as("sFDYWeight").sum("sCUTWeight").as("sCUTWeight").sum("sOtherWeight")
						.as("sOtherWeight"));
		AggregationResults<SalemanSaleDetailReport> ar = mongoTemplate.aggregate(aggregation,SalemanSaleDetailReport.class);
		List<SalemanSaleDetailReport> list = ar.getMappedResults();
		if (list != null && list.size() > Constants.ZERO) {
			 getCol(list);
			ExportUtil<SalemanSaleDetailReport> exportUtil = new ExportUtil<SalemanSaleDetailReport>();
			String path = exportUtil.exportDynamicUtil("salemanSaleDetail", this.fileName, list);
			File file = new File(path);
			// 上传到OSS
			ossPath = OSSUtils.ossUploadXls(file, Constants.FIVE);
		}
		return ossPath;
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
			TypedAggregation<SalemanSaleDetailReport> aggregation  = Aggregation.newAggregation(SalemanSaleDetailReport.class, Aggregation.match(criteria),
					Aggregation.group("accountPk").first("accountPk").as("accountPk").first("accountName")
					.as("accountName").sum("supcompanyNum").as("supcompanyNum").sum("purcompanyNum")
					.as("purcompanyNum").sum("pPOYWeight").as("pPOYWeight").sum("pDTYWeight").as("pDTYWeight")
					.sum("pFDYWeight").as("pFDYWeight").sum("pCUTWeight").as("pCUTWeight").sum("pOtherWeight")
					.as("pOtherWeight").sum("sPOYWeight").as("sPOYWeight").sum("sDTYWeight").as("sDTYWeight")
					.sum("sFDYWeight").as("sFDYWeight").sum("sCUTWeight").as("sCUTWeight").sum("sOtherWeight")
					.as("sOtherWeight"),
			Aggregation.skip(start), Aggregation.limit(Constants.EXCEL_NUMBER_TENHOUSAND));
			AggregationResults<SalemanSaleDetailReport> ar = mongoTemplate.aggregate(aggregation,
					SalemanSaleDetailReport.class);
			List<SalemanSaleDetailReport> list = ar.getMappedResults();	        
			if (list != null && list.size() > Constants.ZERO) {
				    getCol(list);
				ExportUtil<SalemanSaleDetailReport> exportUtil = new ExportUtil<SalemanSaleDetailReport>();
				String excelName = "营销中心-数据管理-业务员交易明细-" + storeDto.getAccountName() + "-"
						+ DateUtil.getDateFormat(new Date(), "yyyyMMddHHmmss");
				String path = exportUtil.exportDynamicUtil("salemanSaleDetail", excelName, list);
				File file = new File(path);
				fileList.add(file);
			}
		}
		// 上传Zip到OSS
		return OSSUtils.ossUploadXls(new File(ZipUtils.fileToZip(fileList, this.fileName)), Constants.FILE);
	}

	private void getCol(List<SalemanSaleDetailReport> list) {
		for (SalemanSaleDetailReport s : list) {
		    if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_REPORT_SALEMANSALEDETAIL_COL_ACCOUNTNAME)) {
                s.setAccountName(CommonUtil.hideContacts(s.getAccountName()));
            }
			s.setpPOYWeightb(new BigDecimal(s.getpPOYWeight().toString()).setScale(4, BigDecimal.ROUND_DOWN));
			s.setpDTYWeightb(new BigDecimal(s.getpDTYWeight().toString()).setScale(4, BigDecimal.ROUND_DOWN));
			s.setpFDYWeightb(new BigDecimal(s.getpFDYWeight().toString()).setScale(4, BigDecimal.ROUND_DOWN));
			s.setpCUTWeightb(new BigDecimal(s.getpCUTWeight().toString()).setScale(4, BigDecimal.ROUND_DOWN));
			s.setpOtherWeightb(new BigDecimal(s.getpOtherWeight().toString()).setScale(4, BigDecimal.ROUND_DOWN));
			s.setsPOYWeightb(new BigDecimal(s.getsPOYWeight().toString()).setScale(4, BigDecimal.ROUND_DOWN));
			s.setsDTYWeightb(new BigDecimal(s.getsDTYWeight().toString()).setScale(4, BigDecimal.ROUND_DOWN));
			s.setsFDYWeightb(new BigDecimal(s.getsFDYWeight().toString()).setScale(4, BigDecimal.ROUND_DOWN));
			s.setsCUTWeightb(new BigDecimal(s.getsCUTWeight().toString()).setScale(4, BigDecimal.ROUND_DOWN));
			s.setsOtherWeightb(new BigDecimal(s.getsOtherWeight().toString()).setScale(4, BigDecimal.ROUND_DOWN));
		}
	}

	private Criteria reportParams(ReportFormsDataTypeParams params, SysExcelStoreExtDto storeDto) {
		Criteria criteria = new Criteria();
		if (params.getStartTime() != null && !params.getStartTime().equals("")) {
			if (params.getEndTime() != null && !params.getEndTime().equals("")) {
				criteria = Criteria.where("date").gte(params.getStartTime()).lte(params.getEndTime());
			} else {
				criteria = Criteria.where("date").gte(params.getStartTime());
			}
		} else if (params.getEndTime() != null && !params.getEndTime().equals("")) {
			criteria = Criteria.where("date").lte(params.getEndTime());
		}
		return criteria;
	}

	public static String salemanSaleDetailReportParam(ReportFormsDataTypeParams params, SysExcelStore store) {
		String paramStr = "";
		paramStr = CommonUtil.getDateShow(paramStr,  params.getStartTime(), params.getEndTime(),  "日期：");
		return paramStr;
	}
}
