package cn.cf.task.schedule.marketing;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Sort;
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
import cn.cf.entity.SupplierSaleDataReport;
import cn.cf.model.SysExcelStore;
import cn.cf.task.schedule.ScheduledFutureMap;
import cn.cf.util.ArithUtil;
import cn.cf.util.DateUtil;

public class SupplierSaleDataReportRunnable  implements Runnable  {
	private String name;

	private String fileName;
	
	private String accountPk;
	
	private String uuid;
	private SysExcelStoreExtDto storeDtoTemp;

	private SysExcelStoreExtDao storeDao;
	public SupplierSaleDataReportRunnable() {

	}

	
	public SupplierSaleDataReportRunnable(String name,String uuid) {

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
			map.put("methodName", "exportSupplierSaleDataReport_"+StringUtils.defaultIfEmpty(this.uuid, ""));
			// 查询存在要导出的任务
			List<SysExcelStoreExtDto> list = storeDao.getFirstTimeExcelStore(map);
			if (list != null && list.size() > Constants.ZERO) {
				for (SysExcelStoreExtDto storeDto : list) {
					this.storeDtoTemp = storeDto;
					this.fileName = "营销中心-数据管理-供应商销售数据" + storeDto.getAccountName() + "-"
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
							TypedAggregation<SupplierSaleDataReport> aggregationCount = Aggregation.newAggregation(
									SupplierSaleDataReport.class, Aggregation.match(criteria),
									Aggregation.group("pk").first("pk").as("pk"));
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
		TypedAggregation<SupplierSaleDataReport>	aggregation = Aggregation.newAggregation(SupplierSaleDataReport.class, Aggregation.match(criteria),
				Aggregation.sort(Sort.Direction.ASC, "pk"),
				Aggregation.group("pk").first("pk").as("pk").first("name").as("name").addToSet("accountName")
						.as("accountArry").sum("POYnum").as("POYnum").sum("POYprice").as("POYprice")
						.sum("POYweight").as("POYweight").sum("FDYnum").as("FDYnum").sum("FDYprice").as("FDYprice")
						.sum("FDYweight").as("FDYweight").sum("DTYnum").as("DTYnum").sum("DTYprice").as("DTYprice")
						.sum("DTYweight").as("DTYweight").sum("CUTnum").as("CUTnum").sum("CUTprice").as("CUTprice")
						.sum("CUTweight").as("CUTweight").sum("othernum").as("othernum").sum("otherprice")
						.as("otherprice").sum("otherweight").as("otherweight"));
		AggregationResults<SupplierSaleDataReport> ar = mongoTemplate.aggregate(aggregation,
				SupplierSaleDataReport.class);
		List<SupplierSaleDataReport> list = ar.getMappedResults();
		if (list != null && list.size() > Constants.ZERO) {
			getCol(list,criteria,mongoTemplate);
			ExportUtil<SupplierSaleDataReport> exportUtil = new ExportUtil<SupplierSaleDataReport>();
			String path = exportUtil.exportDynamicUtil("supplierSaleData", this.fileName, list);
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
			TypedAggregation<SupplierSaleDataReport> aggregation  = Aggregation.newAggregation(SupplierSaleDataReport.class, Aggregation.match(criteria),
					Aggregation.sort(Sort.Direction.ASC, "pk"),
					Aggregation.group("pk").first("pk").as("pk").first("name").as("name").addToSet("accountName")
							.as("accountArry").sum("POYnum").as("POYnum").sum("POYprice").as("POYprice")
							.sum("POYweight").as("POYweight").sum("FDYnum").as("FDYnum").sum("FDYprice").as("FDYprice")
							.sum("FDYweight").as("FDYweight").sum("DTYnum").as("DTYnum").sum("DTYprice").as("DTYprice")
							.sum("DTYweight").as("DTYweight").sum("CUTnum").as("CUTnum").sum("CUTprice").as("CUTprice")
							.sum("CUTweight").as("CUTweight").sum("othernum").as("othernum").sum("otherprice")
							.as("otherprice").sum("otherweight").as("otherweight"),
					Aggregation.skip(start), Aggregation.limit(Constants.EXCEL_NUMBER_TENHOUSAND));
			AggregationResults<SupplierSaleDataReport> ar = mongoTemplate.aggregate(aggregation,
					SupplierSaleDataReport.class);
			List<SupplierSaleDataReport> list = ar.getMappedResults();    
			if (list != null && list.size() > Constants.ZERO) {
				getCol(list,criteria,mongoTemplate);

				ExportUtil<SupplierSaleDataReport> exportUtil = new ExportUtil<SupplierSaleDataReport>();
				String excelName = "营销中心-数据管理-供应商销售数据-" + storeDto.getAccountName() + "-"
						+ DateUtil.formatYearMonthDayHMS(new Date())+"-"+(i+1);
				String path = exportUtil.exportDynamicUtil("supplierSaleData", excelName, list);
				File file = new File(path);
				fileList.add(file);
			}
		}
		// 上传Zip到OSS
		return OSSUtils.ossUploadXls(new File(ZipUtils.fileToZip(fileList, this.fileName)), Constants.FILE);
	}

	private void getCol(List<SupplierSaleDataReport> list, Criteria criteria, MongoTemplate mongoTemplate) {
		TypedAggregation<SupplierSaleDataReport> aggregationTotal = Aggregation.newAggregation(
				SupplierSaleDataReport.class, Aggregation.match(criteria),
				Aggregation.group("pk").first("pk").as("pk").sum("$POYweight").as("POYweight").sum("$FDYweight")
						.as("FDYweight").sum("$DTYweight").as("DTYweight").sum("$CUTweight").as("CUTweight")
						.sum("$otherweight").as("otherweight"));
		AggregationResults<SupplierSaleDataReport> arTotel = mongoTemplate.aggregate(aggregationTotal,
				SupplierSaleDataReport.class);
		if (arTotel.getMappedResults() != null && arTotel.getMappedResults().size() > 0) {
			double POYweight = 0d;
			double FDYweight = 0d;
			double DTYweight = 0d;
			double CUTweight = 0d;
			double Otherweight = 0d;
			for (int i = 0; i < arTotel.getMappedResults().size(); i++) {
				Otherweight = Otherweight + new BigDecimal(arTotel.getMappedResults().get(i).getOtherweight().toString()).setScale(4, BigDecimal.ROUND_DOWN).doubleValue() ;
				POYweight = POYweight + new BigDecimal(arTotel.getMappedResults().get(i).getPOYweight().toString()).setScale(4, BigDecimal.ROUND_DOWN).doubleValue();
				FDYweight = FDYweight +new BigDecimal(arTotel.getMappedResults().get(i).getFDYweight().toString()).setScale(4, BigDecimal.ROUND_DOWN).doubleValue() ;
				DTYweight = DTYweight +new BigDecimal(arTotel.getMappedResults().get(i).getDTYweight().toString()).setScale(4, BigDecimal.ROUND_DOWN).doubleValue();
				CUTweight = CUTweight +new BigDecimal(arTotel.getMappedResults().get(i).getCUTweight().toString()).setScale(4, BigDecimal.ROUND_DOWN).doubleValue() ;
			}
			for (SupplierSaleDataReport report : list) {
			    if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_REPORT_SUPPLIERSALEDATA_COL_STORENAME)) {
                    report.setName(CommonUtil.hideCompanyName(report.getName()));
                }
				report.setPOYpriceb(new BigDecimal(report.getPOYprice().toString()).setScale(2, RoundingMode.HALF_UP));
				report.setPOYweightb(new BigDecimal(report.getPOYweight().toString()).setScale(4, BigDecimal.ROUND_DOWN));
				report.setFDYpriceb(new BigDecimal(report.getFDYprice().toString()).setScale(2, RoundingMode.HALF_UP));
				report.setFDYweightb(new BigDecimal(report.getFDYweight().toString()).setScale(4, BigDecimal.ROUND_DOWN));
				report.setDTYpriceb(new BigDecimal(report.getDTYprice().toString()).setScale(2, RoundingMode.HALF_UP));
				report.setDTYweightb(new BigDecimal(report.getDTYweight().toString()).setScale(4, BigDecimal.ROUND_DOWN));
				report.setCUTpriceb(new BigDecimal(report.getCUTprice().toString()).setScale(2, RoundingMode.HALF_UP));
				report.setCUTweightb(new BigDecimal(report.getCUTweight().toString()).setScale(4, BigDecimal.ROUND_DOWN));
				report.setOtherpriceb(new BigDecimal(report.getOtherprice().toString()).setScale(2, RoundingMode.HALF_UP));
				report.setOtherweightb(new BigDecimal(report.getOtherweight().toString()).setScale(4, BigDecimal.ROUND_DOWN));
				report.setPOYRatio(countRatio(new BigDecimal(report.getPOYweight().toString()).setScale(4, BigDecimal.ROUND_DOWN).doubleValue(), POYweight));
				report.setFDYRatio(countRatio(new BigDecimal(report.getFDYweight().toString()).setScale(4, BigDecimal.ROUND_DOWN).doubleValue(), FDYweight));
				report.setDTYRatio(countRatio(new BigDecimal(report.getDTYweight().toString()).setScale(4, BigDecimal.ROUND_DOWN).doubleValue(), DTYweight));
				report.setCUTRatio(countRatio(new BigDecimal(report.getCUTweight().toString()).setScale(4, BigDecimal.ROUND_DOWN).doubleValue(), CUTweight));
				report.setOtherRatio(countRatio(report.getOtherweight(), Otherweight));
				
				if (report.getAccountArry().length > 0) {
				  if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_REPORT_SUPPLIERSALEDATA_COL_ACCOUNTNAME)) {
				      String str = "";
				      for (int i = 0; i < report.getAccountArry().length; i++) {
				          str = str + CommonUtil.hideContacts(report.getAccountArry()[i])+",";
				      }
                        report.setAccountName(str.substring(0, str.length()-1));
                    }else{
                        String str = StringUtils.join(report.getAccountArry(), ","); // 数组转字符串(逗号分隔)(推荐)
                          report.setAccountName(str);
                    }
				}
			}
		}
	
		
	}

	private String countRatio(Double double1, Double double2) {
		if (double2 == 0) {
			return "0%";
		} else {
			Double result = ArithUtil.mul(ArithUtil.div(double1, double2, 4), 100);
			return result.toString() + "%";
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
	public static String supplierSaleDataReportParam(ReportFormsDataTypeParams params, SysExcelStore store) {
		String paramStr = "";
		paramStr = CommonUtil.getDateShow(paramStr,  params.getStartTime(), params.getEndTime(),  "日期：");
		return paramStr;
	}

}
