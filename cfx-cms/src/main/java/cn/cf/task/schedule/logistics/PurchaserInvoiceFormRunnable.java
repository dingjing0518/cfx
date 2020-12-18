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
import cn.cf.dto.PurchaserInvoiceDto;
import cn.cf.dto.SysExcelStoreDto;
import cn.cf.dto.SysExcelStoreExtDto;
import cn.cf.entity.CustomerDataTypeParams;
import cn.cf.model.SysExcelStore;
import cn.cf.task.schedule.ScheduledFutureMap;
import cn.cf.util.DateUtil;

public class PurchaserInvoiceFormRunnable implements Runnable {
	private String name;

	private String fileName;

	private String accountPk;
	private String uuid;
	private SysExcelStoreExtDto storeDtoTemp;
	private SysExcelStoreExtDao storeDao;
	public PurchaserInvoiceFormRunnable() {
	}

	public PurchaserInvoiceFormRunnable(String name,String uuid) {

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
	public void upLoadFile(){

		LgDeliveryOrderExtDao lgOrderExtDao = (LgDeliveryOrderExtDao) BeanUtils.getBean("lgDeliveryOrderExtDao");
		this.storeDao = (SysExcelStoreExtDao) BeanUtils.getBean("sysExcelStoreExtDao");
		if (storeDao != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("isDeal", Constants.TWO);
			map.put("methodName", "exportPurchaserInvoiceForm_"+StringUtils.defaultIfEmpty(this.uuid, ""));
			// 查询存在要导出的任务
			List<SysExcelStoreExtDto> list = storeDao.getFirstTimeExcelStore(map);
			if (list != null && list.size() > Constants.ZERO) {
				for (SysExcelStoreExtDto storeDto : list) {
					this.storeDtoTemp = storeDto;
					this.fileName = "物流中心-财务管理-发票管理（采购商）-" + storeDto.getAccountName() + "-"
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
							int counts = lgOrderExtDao.searchPurchaserInvoiceCount(orderMap);
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
		List<PurchaserInvoiceDto> list = lgOrderExtDao.searchPurchaserInvoice(orderMap);
		if (list != null && list.size() > Constants.ZERO) {
			ExportUtil<PurchaserInvoiceDto> exportUtil = new ExportUtil<PurchaserInvoiceDto>();
			String path = exportUtil.exportDynamicUtil("purchaserInvoiceForm", this.fileName, list);
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
			List<PurchaserInvoiceDto> list = lgOrderExtDao.searchPurchaserInvoice(orderMap);
			orderMap.remove("start");
			if (list != null && list.size() > Constants.ZERO) {
				ExportUtil<PurchaserInvoiceDto> exportUtil = new ExportUtil<PurchaserInvoiceDto>();
				String excelName = "物流中心-财务管理-发票管理（采购商）-" + storeDto.getAccountName() + "-"
						+ DateUtil.formatYearMonthDayHMS(new Date())+"-"+(i+1);
				String path = exportUtil.exportDynamicUtil("purchaserInvoiceForm", excelName, list);
				File file = new File(path);
				fileList.add(file);
			}
		}
		// 上传Zip到OSS
		return OSSUtils.ossUploadXls(new File(ZipUtils.fileToZip(fileList, this.fileName)), Constants.FILE);
	}

	private void setColOrderParams(Map<String, Object> orderMap) {
		orderMap.put("colInvoiceName",
				CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_FC_PURINVOICE_COL_INVOICENAME));
		orderMap.put("colInvoiceTaxidNumber",
				CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_FC_PURINVOICE_COL_INVOICETAXIDNUMBER));
		orderMap.put("colInvoiceAddress",
				CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_FC_PURINVOICE_COL_INVOICEREGADDRESS));
		orderMap.put("colInvoiceRegPhone",
				CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_FC_PURINVOICE_COL_INVOICEREGPHONE));
		orderMap.put("colInvoiceBankName",
				CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_FC_PURINVOICE_COL_INVOICEBANKNAME));
		orderMap.put("colInvoiceAccountName",
				CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_FC_PURINVOICE_COL_INVOICEBANKACCOUNT));
		orderMap.put("colInvoiceContacts",
				CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_FC_PURINVOICE_COL_CONTACTS));
		orderMap.put("colInvoiceContactsTel",
				CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_FC_PURINVOICE_COL_CONTACTSTEL));
		orderMap.put("colAddress",
				CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_FC_PURINVOICE_COL_ADDRESS));
	}

	private Map<String, Object> orderParams(CustomerDataTypeParams params, SysExcelStoreExtDto dto) {
		String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dto.getInsertTime());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderName", "insertTime");
		map.put("orderType", "desc");
		if (CommonUtil.isNotEmpty(params.getInvoiceName())) {
			map.put("invoiceName", params.getInvoiceName());
		}
		if (CommonUtil.isNotEmpty(params.getApplicateStartTime())) {
			map.put("applicateStartTime", params.getApplicateStartTime());
		}
		if (CommonUtil.isNotEmpty(params.getApplicateEndTime())) {
			map.put("applicateEndTime", params.getApplicateEndTime());
		}
		if (CommonUtil.isNotEmpty(params.getInvoiceStartTime())) {
			map.put("invoiceStartTime", params.getInvoiceStartTime());
		}
		if (CommonUtil.isNotEmpty(params.getInvoiceEndTime())) {
			map.put("invoiceEndTime", params.getInvoiceEndTime());
		}
		// 如果没有选择下单时间条件，则判断查询当前导出列表的添加时间作为结束时间
			map.put("insertTime", now);

		if (CommonUtil.isNotEmpty(params.getMemberInvoiceStatus())) {
			map.put("memberInvoiceStatus", params.getMemberInvoiceStatus());
		}

		if (CommonUtil.isNotEmpty(params.getInvoiceRegPhone())) {
			map.put("invoiceRegPhone", params.getInvoiceRegPhone());
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

	public static String purchaserInvoiceParam(CustomerDataTypeParams params, SysExcelStore dto) {
		String paramStr = "";

		if (CommonUtil.isNotEmpty(params.getInvoiceName())) {
			paramStr = paramStr + "公司抬头：" + params.getInvoiceName() + ";";
		}
		
		paramStr = CommonUtil.getDateShow(paramStr,  params.getApplicateStartTime(), params.getApplicateEndTime(),  "申请时间：");

		paramStr = CommonUtil.getDateShow(paramStr,  params.getInvoiceStartTime(), params.getInvoiceEndTime(),  "开票时间：");

		if (CommonUtil.isNotEmpty(params.getMemberInvoiceStatus())) {
			paramStr = paramStr + "开票状态：" + getInvoiceStatus(params.getMemberInvoiceStatus()) + ";";
		}
		if (CommonUtil.isNotEmpty(params.getInvoiceRegPhone())) {
			paramStr =  paramStr +"联系电话：" + params.getInvoiceRegPhone() + ";";
		}

		return paramStr;
	}

}
