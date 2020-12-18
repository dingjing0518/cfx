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
import cn.cf.dao.SysExcelStoreExtDao;
import cn.cf.dto.SupplierInvoiceDto;
import cn.cf.dto.SysExcelStoreDto;
import cn.cf.dto.SysExcelStoreExtDto;
import cn.cf.entity.CustomerDataTypeParams;
import cn.cf.model.SysExcelStore;
import cn.cf.task.schedule.ScheduledFutureMap;
import cn.cf.util.DateUtil;

public class SupplierInvoiceFormRunnable implements Runnable {
	private String name;

	private String fileName;

	private String accountPk;
	private String 	uuid;
	private SysExcelStoreExtDto storeDtoTemp;

	private SysExcelStoreExtDao storeDao;
	public SupplierInvoiceFormRunnable() {

	}
	
	public SupplierInvoiceFormRunnable(String name ,String uuid) {

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
			map.put("methodName", "exportSupplierInvoiceForm_"+StringUtils.defaultIfEmpty(this.uuid, ""));
			// 查询存在要导出的任务
			List<SysExcelStoreExtDto> list = storeDao.getFirstTimeExcelStore(map);
			if (list != null && list.size() > Constants.ZERO) {
				for (SysExcelStoreExtDto storeDto : list) {
					this.storeDtoTemp = storeDto;
					this.fileName = "物流中心-财务管理-发票管理（供应商）-" + storeDto.getAccountName() + "-"
							+DateUtil.formatYearMonthDayHMS(new Date());
					if (CommonUtil.isNotEmpty(storeDto.getParams())) {
						CustomerDataTypeParams params = JSON.parseObject(storeDto.getParams(),
								CustomerDataTypeParams.class);
						this.accountPk = storeDto.getAccountPk();
						if (lgOrderExtDao != null) {
							// 设置查询参数
							Map<String, Object> orderMap = orderParams(params, storeDto);
							// 设置权限
							setColOrderParams(orderMap);
							String ossPath = "";
							int counts = lgOrderExtDao.searchInvoiceCount(orderMap);
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
		List<SupplierInvoiceDto> list = lgOrderExtDao.searchInvoice(orderMap);
		if (list != null && list.size() > Constants.ZERO) {
			ExportUtil<SupplierInvoiceDto> exportUtil = new ExportUtil<SupplierInvoiceDto>();
			String path = exportUtil.exportDynamicUtil("supplierInvoiceForm", this.fileName, list);
			File file = new File(path);
			// 上传到OSS
			ossPath = OSSUtils.ossUploadXls(file, Constants.FIVE);
		}
		return ossPath;
	}

	private String setLimitPages(Map<String, Object> orderMap, int counts, LgDeliveryOrderExtDao lgOrderExtDao,
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
			List<SupplierInvoiceDto> list = lgOrderExtDao.searchInvoice(orderMap);
			orderMap.remove("start");
			if (list != null && list.size() > Constants.ZERO) {
				ExportUtil<SupplierInvoiceDto> exportUtil = new ExportUtil<SupplierInvoiceDto>();
				String excelName = "物流中心-财务管理-发票管理（供应商）-" + storeDto.getAccountName() + "-"
						+ DateUtil.formatYearMonthDayHMS(new Date())+"-"+(i+1);
				String path = exportUtil.exportDynamicUtil("supplierInvoiceForm", excelName, list);
				File file = new File(path);
				fileList.add(file);
			}
		}
		// 上传Zip到OSS
		return OSSUtils.ossUploadXls(new File(ZipUtils.fileToZip(fileList, this.fileName)), Constants.FILE);
	}

	private void setColOrderParams(Map<String, Object> orderMap) {
		orderMap.put("colComName",
				CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_FC_SUPPINVOICE_COL_COMNAME));
		orderMap.put("colContacts",
				CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_FC_SUPPINVOICE_COL_CONTACTS));
		orderMap.put("colContactsTel",
				CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_FC_SUPPINVOICE_COL_CONTACTSTEL));
	}

	private Map<String, Object> orderParams(CustomerDataTypeParams params, SysExcelStoreExtDto dto) {
		String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dto.getInsertTime());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderName", "insertTime");
		map.put("orderType", "desc");
		if (CommonUtil.isNotEmpty(params.getLogisticsCompanyName())) {
			map.put("logisticsCompanyName", params.getLogisticsCompanyName());
		}
		if (CommonUtil.isNotEmpty(params.getOrderPk())) {
			map.put("orderPk", params.getOrderPk());
		}

		// 如果没有选择下单时间条件，则判断查询当前导出列表的添加时间作为结束时间
		map.put("insertTime", now);

		if (CommonUtil.isNotEmpty(params.getSupplierInvoiceStatus())) {
			map.put("supplierInvoiceStatus", params.getSupplierInvoiceStatus());
		}
		return map;
	}

	private static String getInvoiceStatus(String memberInvoiceStatus) {
		String result = "";
		if (memberInvoiceStatus.equals("1")) {
			result = "未开票";
		} else if (memberInvoiceStatus.equals("2")) {
			result = "已开票";
		} else if (memberInvoiceStatus.equals("3")) {
			result = "已寄送";
		}
		return result;
	}

	public static String supplierInvoiceParam(CustomerDataTypeParams params, SysExcelStore dto) {
		String paramStr = "";
		String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dto.getInsertTime());
		if (CommonUtil.isNotEmpty(params.getLogisticsCompanyName())) {
			paramStr = paramStr + "公司抬头：" + params.getLogisticsCompanyName() + ";";
		}
		if (CommonUtil.isNotEmpty(params.getOrderPk())) {
			paramStr = paramStr + "订单编号：" + params.getOrderPk();
		}

		if (CommonUtil.isNotEmpty(params.getSupplierInvoiceStatus())) {
			paramStr =  paramStr +"开票状态：" + getInvoiceStatus(params.getSupplierInvoiceStatus()) + ";";
		}
		return paramStr;
	}

}
