package cn.cf.task.schedule.marketing;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;

import cn.cf.common.ColAuthConstants;
import cn.cf.common.Constants;
import cn.cf.common.ExportDoJsonParams;
import cn.cf.common.ExportUtil;
import cn.cf.common.utils.BeanUtils;
import cn.cf.common.utils.CommonUtil;
import cn.cf.common.utils.OSSUtils;
import cn.cf.common.utils.ZipUtils;
import cn.cf.dao.B2bOrderExtDao;
import cn.cf.dao.B2bOrderGoodsExtDao;
import cn.cf.dao.SysExcelStoreExtDao;
import cn.cf.dto.SysExcelStoreDto;
import cn.cf.dto.SysExcelStoreExtDto;
import cn.cf.entity.OperationSupplierSaleRF;
import cn.cf.entity.OrderGoodsWeightAmount;
import cn.cf.entity.ReportFormsDataTypeParams;
import cn.cf.model.SysExcelStore;
import cn.cf.task.schedule.ScheduledFutureMap;
import cn.cf.util.ArithUtil;
import cn.cf.util.DateUtil;

public class SupplierSaleReportRunnable   implements Runnable  {
	private String name;

	private String fileName;
	
	private String accountPk;
	
	private String uuid;
	private SysExcelStoreExtDto storeDtoTemp;

	private SysExcelStoreExtDao storeDao;

	public SupplierSaleReportRunnable() {

	}

	
	public SupplierSaleReportRunnable(String name,String uuid) {

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
		B2bOrderExtDao b2bOrderExtDao = (B2bOrderExtDao) BeanUtils.getBean("b2bOrderExtDao");
		this.storeDao = (SysExcelStoreExtDao) BeanUtils.getBean("sysExcelStoreExtDao");
		B2bOrderGoodsExtDao   b2bOrderGoodsExtDao = (B2bOrderGoodsExtDao)BeanUtils.getBean("b2bOrderGoodsExtDao");
		if (storeDao != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("isDeal", Constants.TWO);
			map.put("methodName", "exportSupplierSaleReport_"+StringUtils.defaultIfEmpty(this.uuid, ""));
			// 查询存在要导出的任务
			List<SysExcelStoreExtDto> list = storeDao.getFirstTimeExcelStore(map);
			if (list != null && list.size() > Constants.ZERO) {
				for (SysExcelStoreExtDto storeDto : list) {
					this.storeDtoTemp = storeDto;
					this.fileName = "营销中心-数据管理-供应商销售报表-" + storeDto.getAccountName() + "-"
							+ DateUtil.formatYearMonthDayHMS(new Date());
					if (CommonUtil.isNotEmpty(storeDto.getParams())) {
						ReportFormsDataTypeParams params = JSON.parseObject(storeDto.getParams(),
								ReportFormsDataTypeParams.class);
						this.accountPk = storeDto.getAccountPk();
						if (b2bOrderExtDao != null) {
							// 设置查询参数
							Map<String, Object> orderMap = orderParams(params, storeDto);
							// 设置权限
							String ossPath = "";
							int counts = b2bOrderExtDao.countSupplierSaleRF(map);
							if (counts > Constants.EXCEL_NUMBER_TENHOUSAND) {
								// 大于10000，分页查询数据
								ossPath = setLimitPages(orderMap, counts, b2bOrderExtDao, storeDto,b2bOrderGoodsExtDao);
							} else {
								// 如果小于或等于10000条直接上传
								ossPath = setNotLimitPages(orderMap, b2bOrderExtDao, storeDto,b2bOrderGoodsExtDao);
							}
							// 更新订单导出状态
							ExportDoJsonParams.updateExcelStoreStatus(storeDto,storeDao, ossPath);
						}
					}
				}
			}
		}
	}


	private String setNotLimitPages(Map<String, Object> orderMap, B2bOrderExtDao b2bOrderExtDao,
			SysExcelStoreExtDto storeDto, B2bOrderGoodsExtDao b2bOrderGoodsExtDao) {
		String ossPath = "";
		List<OperationSupplierSaleRF> list = b2bOrderExtDao.searchSupplierSaleRF(orderMap);
		if (list != null && list.size() > Constants.ZERO) {
			getCol(b2bOrderGoodsExtDao, list);
			ExportUtil<OperationSupplierSaleRF> exportUtil = new ExportUtil<OperationSupplierSaleRF>();
			String path = exportUtil.exportDynamicUtil("supplierSaleReport", this.fileName, list);
			File file = new File(path);
			// 上传到OSS
			ossPath = OSSUtils.ossUploadXls(file, Constants.FIVE);
		}
		return ossPath;
	}


	private String setLimitPages(Map<String, Object> orderMap, int counts, B2bOrderExtDao b2bOrderExtDao,
			SysExcelStoreExtDto storeDto, B2bOrderGoodsExtDao b2bOrderGoodsExtDao) {
		double numbers = Math.floor(counts / Constants.EXCEL_NUMBER_TENHOUSAND);// 获取分页查询次数
		orderMap.put("limit", Constants.EXCEL_NUMBER_TENHOUSAND);
		List<File> fileList = new ArrayList<>();
		for (int i = Constants.ZERO; i < numbers + Constants.ONE; i++) { //
			int start = Constants.ZERO;
			if (i != Constants.ZERO) {
				start = ExportDoJsonParams.indexPageNumbers(i);
			}
			orderMap.put("start", start);
			List<OperationSupplierSaleRF> list = b2bOrderExtDao.searchSupplierSaleRF(orderMap);
			orderMap.remove("start");
			if (list != null && list.size() > Constants.ZERO) {
				// 拼接数据，根据总订单号统计所有子订单的重量和金额
				getCol(b2bOrderGoodsExtDao, list);
			
				ExportUtil<OperationSupplierSaleRF> exportUtil = new ExportUtil<OperationSupplierSaleRF>();
				String excelName = "营销中心-数据管理-供应商销售报表" + storeDto.getAccountName() + "-"
						+ DateUtil.formatYearMonthDayHMS(new Date())+"-"+(i+1);
				String path = exportUtil.exportDynamicUtil("supplierSaleReport", excelName, list);
				File file = new File(path);
				fileList.add(file);
			}
		}
		// 上传Zip到OSS
		return OSSUtils.ossUploadXls(new File(ZipUtils.fileToZip(fileList, this.fileName)), Constants.FILE);
	}


	private void getCol(B2bOrderGoodsExtDao b2bOrderGoodsExtDao, List<OperationSupplierSaleRF> list) {
		for (OperationSupplierSaleRF operationSupplierSaleRF : list) {

			operationSupplierSaleRF.setAmount(new BigDecimal(ArithUtil.roundBigDecimal(operationSupplierSaleRF.getAmount(),2)));

		    if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_REPORT_SUPPLIERSALE_COL_STORENAME)) {
		        operationSupplierSaleRF.setStoreName(CommonUtil.hideCompanyName(operationSupplierSaleRF.getStoreName()));
		    }
		    
		    if(!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.MARKET_REPORT_SUPPLIERSALE_COL_ACCOUNTNAME)){
		        operationSupplierSaleRF.setAccountName(CommonUtil.hideContacts(operationSupplierSaleRF.getAccountName()));
		    }
		    
			List<String> orderNumberList = new ArrayList<String>();
			String orderNumbers = operationSupplierSaleRF.getOrderNumbers();
			if (orderNumbers != null && !"".equals(orderNumbers) && orderNumbers.contains(",")) {
				String[] array = operationSupplierSaleRF.getOrderNumbers().split(",");
				for (int j = 0; j < array.length; j++) {
					orderNumberList.add(array[j]);
				}
			}
			if (orderNumbers != null && !"".equals(orderNumbers) && !orderNumbers.contains(",")) {
				orderNumberList.add(orderNumbers);
			}

			if (orderNumberList != null && orderNumberList.size() > 0) {
				List<OrderGoodsWeightAmount> weightAmountList = b2bOrderGoodsExtDao.getB2bOrderGoodsWA(orderNumberList);
				if (weightAmountList != null && weightAmountList.size() > 0) {
					Double weights = 0d;
					weights = getWeights(weightAmountList, weights);
					operationSupplierSaleRF.setWeight(CommonUtil.formatDoubleFive(weights));
				}
			}
		}
	}


	private Double getWeights(List<OrderGoodsWeightAmount> weightAmountList, Double weights) {
		for(OrderGoodsWeightAmount amount:weightAmountList){
			Double weight = amount.getWeight() ==null?0d:amount.getWeight();
			weights +=weight;
		}
		return weights;
	}


	private Map<String, Object> orderParams(ReportFormsDataTypeParams params, SysExcelStoreExtDto storeDto) {
		String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(storeDto.getInsertTime());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderName", "receivablesTime");
		map.put("orderType", "desc");
		if (CommonUtil.isNotEmpty(params.getStartTime())) {
			map.put("startTime", params.getStartTime());
		}
		if (CommonUtil.isNotEmpty(params.getEndTime())) {
			map.put("endTime", params.getEndTime());
		}
			map.put("insertTime", now);
		return map;
	}


	public static String supplierSaleReportParam(ReportFormsDataTypeParams params, SysExcelStore store) {
		String paramStr = "";
		paramStr = CommonUtil.getDateShow(paramStr,  params.getStartTime(), params.getEndTime(),  "日期：");
		return paramStr;
	}

}
