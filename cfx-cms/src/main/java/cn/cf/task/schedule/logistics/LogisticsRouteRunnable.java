package cn.cf.task.schedule.logistics;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

import cn.cf.common.ExportDoJsonParams;
import cn.cf.task.schedule.yarn.YarnGoodsRunnable;
import com.alibaba.fastjson.JSON;

import cn.cf.common.ColAuthConstants;
import cn.cf.common.Constants;
import cn.cf.common.ExportUtil;
import cn.cf.common.utils.BeanUtils;
import cn.cf.common.utils.CommonUtil;
import cn.cf.common.utils.OSSUtils;
import cn.cf.common.utils.ZipUtils;
import cn.cf.dao.LogisticsRouteDao;
import cn.cf.dao.SysExcelStoreExtDao;
import cn.cf.dto.SysExcelStoreDto;
import cn.cf.dto.SysExcelStoreExtDto;
import cn.cf.entity.CustomerDataTypeParams;
import cn.cf.entity.DataReportExtDto;
import cn.cf.entity.LogisticsRouteExport;
import cn.cf.model.SysExcelStore;
import cn.cf.task.schedule.ScheduledFutureMap;
import cn.cf.util.DateUtil;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogisticsRouteRunnable implements Runnable {

	private static Logger logger = LoggerFactory.getLogger(LogisticsRouteRunnable.class);
	private String name;

	private String fileName;
	private SysExcelStoreExtDto storeDtoTemp;
	private String accountPk;
	private String uuid;
	private SysExcelStoreExtDao storeDao;
	public LogisticsRouteRunnable() {

	}

	public LogisticsRouteRunnable(String name,String uuid) {
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

		LogisticsRouteDao logisticsRouteDao = (LogisticsRouteDao) BeanUtils.getBean("logisticsRouteDao");
		this.storeDao = (SysExcelStoreExtDao) BeanUtils.getBean("sysExcelStoreExtDao");
		if (storeDao != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("isDeal", Constants.TWO);
			map.put("methodName", "exportLogisticsRoute_"+StringUtils.defaultIfEmpty(this.uuid, ""));
			// 查询存在要导出的任务
			List<SysExcelStoreExtDto> list = storeDao.getFirstTimeExcelStore(map);
			if (list != null && list.size() > Constants.ZERO) {
				for (SysExcelStoreExtDto storeDto : list) {
					this.storeDtoTemp = storeDto;
					this.fileName = "物流中心-线路管理-" + storeDto.getAccountName() + "-"
							+DateUtil.formatYearMonthDayHMS(new Date());
					if (CommonUtil.isNotEmpty(storeDto.getParams())) {
						CustomerDataTypeParams params = JSON.parseObject(storeDto.getParams(),
								CustomerDataTypeParams.class);
						this.accountPk = storeDto.getAccountPk();
						if (logisticsRouteDao != null) {
							// 设置查询参数
							Map<String, Object> orderMap = orderParams(params, storeDto);
							// 设置权限
							setColOrderParams(orderMap);
							String ossPath = "";
					        int counts = logisticsRouteDao.countAllLogisticsRoute(orderMap);
							if (counts > Constants.EXCEL_NUMBER_TENHOUSAND) {
								// 大于10000，分页查询数据
								ossPath = setLimitPages(orderMap, counts, logisticsRouteDao, storeDto);
							} else {
								// 如果小于或等于10000条直接上传
								ossPath = setNotLimitPages(orderMap, logisticsRouteDao, storeDto);
							}
							// 更新订单导出状态
							ExportDoJsonParams.updateExcelStoreStatus(storeDto,storeDao, ossPath);
						}
					}
				}
			}
		}

	
	}
	private String setNotLimitPages(Map<String, Object> orderMap, LogisticsRouteDao logisticsRouteDao,
			SysExcelStoreExtDto storeDto) {
		String ossPath = "";
        List<LogisticsRouteExport> list = logisticsRouteDao.getAllLogisticsRoute(orderMap);
		if (list != null && list.size() > Constants.ZERO) {
			ExportUtil<LogisticsRouteExport> exportUtil = new ExportUtil<LogisticsRouteExport>();
			String path = exportUtil.exportDynamicUtil("logisticsRouteForm", this.fileName, list);
			File file = new File(path);
			// 上传到OSS
			ossPath = OSSUtils.ossUploadXls(file, Constants.FIVE);
		}
		return ossPath;
	}

	private String setLimitPages(Map<String, Object> orderMap, int counts, LogisticsRouteDao logisticsRouteDao,
			SysExcelStoreExtDto storeDto) {
		double numbers = Math.floor(counts / Constants.EXCEL_NUMBER_TENHOUSAND);// 获取分页查询次数
		orderMap.put("limit", Constants.EXCEL_NUMBER_TENHOUSAND);
		List<File> fileList = new ArrayList<>();
		for (int i = Constants.ZERO; i < numbers + Constants.ONE; i++) { //
			int start = Constants.ZERO;
			if (i != Constants.ZERO) {
				start = ExportDoJsonParams.indexPageNumbers(i);
			}
			orderMap.put("start", start);
	        List<LogisticsRouteExport> list = logisticsRouteDao.getAllLogisticsRoute(orderMap);
	        
			orderMap.remove("start");
			if (list != null && list.size() > Constants.ZERO) {
				ExportUtil<LogisticsRouteExport> exportUtil = new ExportUtil<LogisticsRouteExport>();
				String excelName = "物流中心-线路管理-" + storeDto.getAccountName() + "-"
						+ DateUtil.formatYearMonthDayHMS(new Date())+"-"+(i+1);
				String path = exportUtil.exportDynamicUtil("logisticsRouteForm", excelName, list);
				File file = new File(path);
				fileList.add(file);
			}
		}
		// 上传Zip到OSS
		return OSSUtils.ossUploadXls(new File(ZipUtils.fileToZip(fileList, this.fileName)), Constants.FILE);
	}

	private void setColOrderParams(Map<String, Object> orderMap) {
        if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_ROUTE_COL_LOGISTICSCOMNAME)){
        	orderMap.put("colLogisticsName",ColAuthConstants.LG_ROUTE_COL_LOGISTICSCOMNAME);
        }
        if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_ROUTE_COL_ROUTENAME)){
        	orderMap.put("colName",ColAuthConstants.LG_ROUTE_COL_ROUTENAME);
        }
        if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_ROUTE_COL_TOADDRESS)){
        	orderMap.put("colToAddress",ColAuthConstants.LG_ROUTE_COL_TOADDRESS);
        }
        if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_ROUTE_COL_FROMADDRESS)){
        	orderMap.put("colFromAddress",ColAuthConstants.LG_ROUTE_COL_FROMADDRESS);
        }
    }

	private Map<String, Object> orderParams(CustomerDataTypeParams params, SysExcelStoreExtDto storeDto) {
		String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(storeDto.getInsertTime());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderName", "insertTime");
		map.put("orderType", "desc");
		if (CommonUtil.isNotEmpty(params.getCompanyPk())) {
			map.put("companyPk", params.getCompanyPk());
		}
		if (CommonUtil.isNotEmpty(params.getStatus())) {
			map.put("status", params.getStatus());
		}
		if (CommonUtil.isNotEmpty(params.getFromUpdateTime())) {
			map.put("fromUpdateTime", params.getFromUpdateTime());
		} 
		if (CommonUtil.isNotEmpty(params.getToUpdateTime())) {
			map.put("toUpdateTime", params.getToUpdateTime());
		} 
		if (CommonUtil.isNotEmpty(params.getFromAddress())) {
			map.put("fromAddress", params.getFromAddress());
		} 
		
		if (CommonUtil.isNotEmpty(params.getToAddress())) {
			map.put("toAddress", params.getToAddress());
		} 
		map.put("isDelete", 1);
		// 如果没有选择下单时间条件，则判断查询当前导出列表的添加时间作为结束时间
			map.put("insertTime", now);
		
		return map;
	}

	public static String logisticsRouteParam(CustomerDataTypeParams params, SysExcelStore dto) {
		String paramStr = "";
		String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dto.getInsertTime());

		if (CommonUtil.isNotEmpty(params.getLogisticsCompanyName())) {
			paramStr = paramStr + "物流供应商：" + params.getLogisticsCompanyName() + ";";
		}
		
		if (CommonUtil.isNotEmpty(params.getFromAddress())) {
			paramStr = paramStr + "始发地：" + params.getFromAddress() + ";";
		}
		
		if (CommonUtil.isNotEmpty(params.getToAddress())) {
			paramStr =paramStr+ "目的地：" + params.getToAddress() + ";";
		}
		
		paramStr = CommonUtil.getDateShow(paramStr,  params.getFromUpdateTime(), params.getToUpdateTime(),  "最新更新时间：");


		if (CommonUtil.isNotEmpty(params.getStatus())) {
			paramStr = paramStr+"是否启用：" + getStatus(params.getStatus()) + ";";
		}
	

		return paramStr;
	}

	private static String getStatus(String status) {
		String result = "";
		if (status.equals("0")) {
			result = "未启用";
		} else if (status.equals("1")) {
			result = "已启用";
		} 
		
		return result;
	}
	
	
	

}
