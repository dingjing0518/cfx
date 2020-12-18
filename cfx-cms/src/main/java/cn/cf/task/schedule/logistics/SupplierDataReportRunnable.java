package cn.cf.task.schedule.logistics;

import java.io.File;
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
import cn.cf.dao.LgDeliveryOrderExtDao;
import cn.cf.dao.SysExcelStoreDao;
import cn.cf.dao.SysExcelStoreExtDao;
import cn.cf.dto.DataReportDto;
import cn.cf.dto.SysExcelStoreDto;
import cn.cf.dto.SysExcelStoreExtDto;
import cn.cf.entity.ReportFormsDataTypeParams;
import cn.cf.model.SysExcelStore;
import cn.cf.task.schedule.ScheduledFutureMap;
import cn.cf.util.DateUtil;

public class SupplierDataReportRunnable implements Runnable {

	private String name;

	private String fileName;

	private SysExcelStoreExtDto storeDtoTemp;
	private String accountPk;
	private SysExcelStoreExtDao storeDao;
	private String uuid;
	
	public SupplierDataReportRunnable() {

	}

	public SupplierDataReportRunnable(String name,String uuid) {

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

	// 上传到OSS
	public void upLoadFile() {

		LgDeliveryOrderExtDao lgOrderExtDao = (LgDeliveryOrderExtDao) BeanUtils.getBean("lgDeliveryOrderExtDao");
		this.storeDao = (SysExcelStoreExtDao) BeanUtils.getBean("sysExcelStoreExtDao");
		if (storeDao != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("isDeal", Constants.TWO);
			map.put("methodName", "exportSupplierDataReport_"+StringUtils.defaultIfEmpty(this.uuid, ""));
			// 查询存在要导出的任务
			List<SysExcelStoreExtDto> list = storeDao.getFirstTimeExcelStore(map);
			if (list != null && list.size() > Constants.ZERO) {
				for (SysExcelStoreExtDto storeDto : list) {
					this.storeDtoTemp = storeDto;
					this.fileName = "物流中心-数据管理-物流供应商报表-" + storeDto.getAccountName() + "-"
							+ DateUtil.formatYearMonthDayHMS(new Date());
					if (CommonUtil.isNotEmpty(storeDto.getParams())) {
						ReportFormsDataTypeParams params = JSON.parseObject(storeDto.getParams(),
								ReportFormsDataTypeParams.class);
						this.accountPk = storeDto.getAccountPk();
						if (lgOrderExtDao != null) {
							// 设置查询参数
							Map<String, Object> orderMap = orderParams(params, storeDto);
							// 设置权限
							setColOrderParams(orderMap);
							String ossPath = "";
							int counts = lgOrderExtDao.searchGrossProfitCount(orderMap);
							if (counts > Constants.EXCEL_NUMBER_TENHOUSAND) {
								// 大于10000，分页查询数据
								ossPath = setLimitPages(orderMap, counts, lgOrderExtDao, storeDto);
							} else {
								// 如果小于或等于10000条直接上传
								ossPath = setNotLimitPages(orderMap, lgOrderExtDao, storeDto);
							}
							// 更新订单导出状态
							ExportDoJsonParams.updateExcelStoreStatus(storeDto,storeDao, ossPath);
						}
					}
				}
			}
		}

	}

	private String setNotLimitPages(Map<String, Object> orderMap, LgDeliveryOrderExtDao lgOrderExtDao,
			SysExcelStoreExtDto storeDto) {
		String ossPath = "";
		List<DataReportDto> list = lgOrderExtDao.searchCustomGrossProfit(orderMap);
		if (list != null && list.size() > Constants.ZERO) {
			ExportUtil<DataReportDto> exportUtil = new ExportUtil<DataReportDto>();
			String path = exportUtil.exportDynamicUtil("supplierDataReportForm", this.fileName, list);
			File file = new File(path);
			// 上传到OSS
			ossPath = OSSUtils.ossUploadXls(file, Constants.FIVE);
		}
		return ossPath;
	}

	// 如果多余10000条数据分多个excel导出，并打包上传OSS
	private String setLimitPages(Map<String, Object> orderMap, double counts, LgDeliveryOrderExtDao lgOrderExtDao,
			SysExcelStoreExtDto storeDto){
		double numbers = Math.floor(counts / Constants.EXCEL_NUMBER_TENHOUSAND);// 获取分页查询次数
		orderMap.put("limit", Constants.EXCEL_NUMBER_TENHOUSAND);
		List<File> fileList = new ArrayList<>();
		for (int i = Constants.ZERO; i < numbers + Constants.ONE; i++) { //
			int start = Constants.ZERO;
			if (i != Constants.ZERO) {
				start = ExportDoJsonParams.indexPageNumbers(i);
			}
			orderMap.put("start", start);
			List<DataReportDto> list = lgOrderExtDao.searchCustomGrossProfit(orderMap);
			orderMap.remove("start");
			if (list != null && list.size() > Constants.ZERO) {
				ExportUtil<DataReportDto> exportUtil = new ExportUtil<DataReportDto>();
				String excelName = "物流中心-数据管理-物流供应商报表-" + storeDto.getAccountName() + "-"
						+DateUtil.formatYearMonthDayHMS(new Date())+"-"+(i+1);
				String path = exportUtil.exportDynamicUtil("supplierDataReportForm", excelName, list);
				File file = new File(path);
				fileList.add(file);
			}
		}
		// 上传Zip到OSS
		return OSSUtils.ossUploadXls(new File(ZipUtils.fileToZip(fileList, this.fileName)), Constants.FILE);
	}

	private void setColOrderParams(Map<String, Object> map) {
		map.put("colPurName", true);
		map.put("colPurContactsTel", true);

		map.put("colToAddress", CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_REPORT_SUPP_COL_TOADDRESS));
		map.put("colToCompanyName",
				CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_REPORT_SUPP_COL_TOCOMNAME));
		map.put("colLogisticsName",
				CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_REPORT_SUPP_COL_LOGISTICSNAME));
		map.put("colFromCompanyName",
				CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_REPORT_SUPP_COL_FROMCOMNAME));
	}

	private Map<String, Object> orderParams(ReportFormsDataTypeParams params, SysExcelStoreDto dto) {
		String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dto.getInsertTime());
		String allStatus = "2,3,4,5,6,10";// 全部订单状态
		String str = "-1";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderName", "insertTime");
		map.put("orderType", "desc");
		if (CommonUtil.isNotEmpty(params.getLogisticsCompanyName())) {
			map.put("logisticsCompanyName", params.getLogisticsCompanyName());
		}
		if (CommonUtil.isNotEmpty(params.getOrderPk())) {
			map.put("orderPk", params.getOrderPk());
		}
		if (CommonUtil.isNotEmpty(params.getInsertStartTime())) {
			map.put("insertStartTime", params.getInsertStartTime());
		} 
		if (CommonUtil.isNotEmpty(params.getInsertEndTime())) {
			map.put("insertEndTime", params.getInsertEndTime());
		}

		// 如果没有选择下单时间条件，则判断查询当前导出列表的添加时间作为结束时间
			map.put("insertTime", now);

		map.put("isMatched", 1);
		// 订单状态
		Integer orderTemp = null;
		if (params.getOrderStatus() != null && !params.getOrderStatus().equals("")) {
			orderTemp = Integer.parseInt(params.getOrderStatus());
		}

		if (orderTemp == null) {
			map.put("orderStatus", allStatus.split(","));
		} else {
			if (orderTemp == Constants.ORDER_STATUS_002 || orderTemp == Constants.ORDER_STATUS_003
					|| orderTemp == Constants.ORDER_STATUS_004 || orderTemp == Constants.ORDER_STATUS_005
					|| orderTemp == Constants.ORDER_STATUS_006 || orderTemp == Constants.ORDER_STATUS_010) {
				map.put("orderStatus", String.valueOf(orderTemp).split(","));

			} else {
				map.put("orderStatus", String.valueOf(str).split(","));
			}
		}
		// 结算状态
		if (CommonUtil.isNotEmpty(params.getIsSettleFreight())) {
			map.put("isSettleFreight", params.getIsSettleFreight());
		}

		// 异常状态
		if (CommonUtil.isNotEmpty(params.getIsAbnormal())) {
			map.put("isAbnormal", params.getIsAbnormal());
		}
		return map;
	}

	private static String getIsSettleFreightName(String isSettleFreight) {
		String result = "";
		if (isSettleFreight.equals("1")) {
			result = "未结算";
		} else if (isSettleFreight.equals("2")) {
			result = "已结算";
		}
		return result;
	}

	private static String getStatusName(Integer orderTemp) {
		String result = "";
		if (orderTemp == Constants.ORDER_STATUS_002) {
			result = "已取消";
		} else if (orderTemp == Constants.ORDER_STATUS_003) {
			result = "已签收";
		} else if (orderTemp == Constants.ORDER_STATUS_004) {
			result = "配送中";
		} else if (orderTemp == Constants.ORDER_STATUS_005) {
			result = "提货中";
		} else if (orderTemp == Constants.ORDER_STATUS_006) {
			result = "财务已确认，待指派车辆";
		} else if (orderTemp == Constants.ORDER_STATUS_008) {
			result = "待确认";
		} else if (orderTemp == Constants.ORDER_STATUS_009) {
			result = "待付款";
		} else if (orderTemp == Constants.ORDER_STATUS_010) {
			result = "已关闭";
		}
		return result;
	}

	public static String supplierDataReportParam(ReportFormsDataTypeParams params, SysExcelStore dto) {
		String paramStr = "";
		if (CommonUtil.isNotEmpty(params.getLogisticsCompanyName())) {
			paramStr = paramStr +  "物流承运商：" + params.getLogisticsCompanyName() + ";";
		}
		
		
		if (CommonUtil.isNotEmpty(params.getOrderPk())) {
			paramStr = paramStr + "订单编号：" + params.getOrderPk() + ";";
		}
		
		paramStr = CommonUtil.getDateShow(paramStr,  params.getInsertStartTime(), params.getInsertEndTime(),  "订单时间：");


		// 订单状态
		Integer orderTemp = null;
		if (params.getOrderStatus() != null && !params.getOrderStatus().equals("")) {
			orderTemp = Integer.parseInt(params.getOrderStatus());
		}

		if (orderTemp == null) {
		} else {
			if (orderTemp == Constants.ORDER_STATUS_002 || orderTemp == Constants.ORDER_STATUS_003
					|| orderTemp == Constants.ORDER_STATUS_004 || orderTemp == Constants.ORDER_STATUS_005
					|| orderTemp == Constants.ORDER_STATUS_006 || orderTemp == Constants.ORDER_STATUS_010) {
				paramStr = paramStr + "订单状态：" + getStatusName(orderTemp) + ";";

			} 
		}
		// 结算状态
		if (CommonUtil.isNotEmpty(params.getIsSettleFreight())) {
			paramStr = paramStr + "结算状态：" + getIsSettleFreightName(params.getIsSettleFreight()) + ";";
		}
		
		if (CommonUtil.isNotEmpty(params.getIsAbnormal())) {
			if (params.getIsAbnormal().equals("1")) {
				paramStr = paramStr + "是否异常：否" + ";";
			}else {
				paramStr = paramStr + "是否异常：是" + ";";
			}
		
		}
		return paramStr;
	}
}
