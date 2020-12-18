package cn.cf.task.schedule.economics;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import cn.cf.dao.B2bBillOrderExtDao;
import cn.cf.dao.SysExcelStoreExtDao;
import cn.cf.dto.B2bBillOrderExtDto;
import cn.cf.dto.SysExcelStoreExtDto;
import cn.cf.entity.LogisticsRouteExport;
import cn.cf.entity.OrderDataTypeParams;
import cn.cf.model.SysExcelStore;
import cn.cf.util.DateUtil;

public class BillOrderRunnable  implements Runnable{
	private String name;

	private String fileName;
	private String uuid;
	private String accountPk;
	private SysExcelStoreExtDto storeDtoTemp;
	public BillOrderRunnable() {

	}
	
	public BillOrderRunnable(String name ,String uuid) {

		this.name = name;
		this.uuid =uuid;
	}

	@Override
	public void run() {

		B2bBillOrderExtDao b2bBillOrderExtDao = (B2bBillOrderExtDao) BeanUtils.getBean("b2bBillOrderExtDao");
		SysExcelStoreExtDao storeDao = (SysExcelStoreExtDao) BeanUtils.getBean("sysExcelStoreExtDao");
		if (storeDao != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("isDeal", Constants.TWO);
			map.put("methodName", "billOrder_"+StringUtils.defaultIfEmpty(this.uuid, ""));
			// 查询存在要导出的任务
			List<SysExcelStoreExtDto> list = storeDao.getFirstTimeExcelStore(map);
			if (list != null && list.size() > Constants.ZERO) {
				for (SysExcelStoreExtDto storeDto : list) {
                    this.storeDtoTemp = storeDto;
					this.fileName = "金融中心-票据支付-" + storeDto.getAccountName() + "-"
							+DateUtil.formatYearMonthDayHMS(new Date());
					if (CommonUtil.isNotEmpty(storeDto.getParams())) {
						OrderDataTypeParams params = JSON.parseObject(storeDto.getParams(),
								OrderDataTypeParams.class);
						this.accountPk = storeDto.getAccountPk();
						if (b2bBillOrderExtDao != null) {
							// 设置查询参数
							Map<String, Object> orderMap = orderParams(params, storeDto);
							// 设置权限
							setColOrderParams(orderMap);
							String ossPath = "";
					        int counts = b2bBillOrderExtDao.searchGridCountExt(orderMap);
							if (counts > Constants.EXCEL_NUMBER_TENHOUSAND) {
								// 大于10000，分页查询数据
								ossPath = setLimitPages(orderMap, counts, b2bBillOrderExtDao, storeDto);
							} else {
								// 如果小于或等于10000条直接上传
								ossPath = setNotLimitPages(orderMap, b2bBillOrderExtDao, storeDto);
							}
							// 更新订单导出状态
							ExportDoJsonParams.updateExcelStoreStatus(storeDto,storeDao, ossPath);
						}
					}
				}
			}
		}

	
	}

	private void setColOrderParams(Map<String, Object> orderMap) {
		if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.EM_ECONOMICS_BILL_PAYRECORD_COL_SUPPLIER)) {
			orderMap.put("supplierCol", ColAuthConstants.EM_ECONOMICS_BILL_PAYRECORD_COL_SUPPLIER);
		}
        
        if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.EM_ECONOMICS_BILL_PAYRECORD_COL_PURCHESER)) {
        	orderMap.put("purcheserCol", ColAuthConstants.EM_ECONOMICS_BILL_PAYRECORD_COL_PURCHESER);
		}
        if (!CommonUtil.isExistAuthName(accountPk, ColAuthConstants.EM_ECONOMICS_BILL_PAYRECORD_COL_STORENAME)) {
        	orderMap.put("storeNameCol", ColAuthConstants.EM_ECONOMICS_BILL_PAYRECORD_COL_STORENAME);
		}
   }

	private String setNotLimitPages(Map<String, Object> orderMap, B2bBillOrderExtDao b2bBillOrderExtDao,
			SysExcelStoreExtDto storeDto) {
		String ossPath = "";
        List<B2bBillOrderExtDto> list = b2bBillOrderExtDao.searchGridExt(orderMap);
		if (list != null && list.size() > Constants.ZERO) {
			ExportUtil<B2bBillOrderExtDto> exportUtil = new ExportUtil<B2bBillOrderExtDto>();
			String path = exportUtil.exportDynamicUtil("billOrder", this.fileName, list);
			File file = new File(path);
			// 上传到OSS
			ossPath = OSSUtils.ossUploadXls(file, Constants.FIVE);
		}
		return ossPath;
	}

	private String setLimitPages(Map<String, Object> orderMap, int counts, B2bBillOrderExtDao b2bBillOrderExtDao,
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
	        List<B2bBillOrderExtDto> list = b2bBillOrderExtDao.searchGridExt(orderMap);
	        
			orderMap.remove("start");
			if (list != null && list.size() > Constants.ZERO) {
				ExportUtil<B2bBillOrderExtDto> exportUtil = new ExportUtil<B2bBillOrderExtDto>();
				String excelName = "金融中心-票据支付-" + storeDto.getAccountName() + "-"
						+ DateUtil.formatYearMonthDayHMS(new Date())+"-"+(i+1);
				String path = exportUtil.exportDynamicUtil("billOrder", excelName, list);
				File file = new File(path);
				fileList.add(file);
			}
		}
		// 上传Zip到OSS
		return OSSUtils.ossUploadXls(new File(ZipUtils.fileToZip(fileList, this.fileName)), Constants.FILE);
	}

	private Map<String, Object> orderParams(OrderDataTypeParams params, SysExcelStoreExtDto storeDto) {
		String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(storeDto.getInsertTime());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderName", "insertTime");
		map.put("orderType", "desc");
		if (CommonUtil.isNotEmpty(params.getOrderNumber())) {
			map.put("orderNumber", params.getOrderNumber());
		}
		if (CommonUtil.isNotEmpty(params.getSerialNumber())) {
			map.put("serialNumber", params.getSerialNumber());
		}
		if (CommonUtil.isNotEmpty(params.getPurchaserName())) {
			map.put("purchaserName", params.getPurchaserName());
		} 
		if (CommonUtil.isNotEmpty(params.getInsertTimeBegin())) {
			map.put("insertTimeBegin", params.getInsertTimeBegin());
		} 
		if (CommonUtil.isNotEmpty(params.getInsertTimeEnd())) {
			map.put("insertTimeEnd", params.getInsertTimeEnd());
		} 
		if (CommonUtil.isNotEmpty(params.getGoodsName())) {
			map.put("goodsName", params.getGoodsName());
		} 
		if (CommonUtil.isNotEmpty(params.getStatus())) {
			map.put("status", params.getStatus());
		} 
		// 如果没有选择下单时间条件，则判断查询当前导出列表的添加时间作为结束时间
			map.put("insertTime", now);
		
		return map;
	}

	public static String billOrderParam(OrderDataTypeParams params, SysExcelStore store) {
		String paramStr = "";
		if (CommonUtil.isNotEmpty(params.getOrderNumber())) {
			paramStr = "订单编号：" + params.getOrderNumber() + ";";
		}
		if (CommonUtil.isNotEmpty(params.getSerialNumber())) {
			paramStr =paramStr +  "支付流水号：" + params.getSerialNumber() + ";";
		}
		if (CommonUtil.isNotEmpty(params.getPurchaserName())) {
			paramStr = paramStr + "采购商信息：" + params.getPurchaserName() + ";";
		}
		paramStr = CommonUtil.getDateShow(paramStr,  params.getInsertTimeBegin(), params.getInsertTimeEnd(),  "票据交易时间：");

		if (CommonUtil.isNotEmpty(params.getGoodsName())) {
			paramStr = paramStr + "票据产品：" + params.getGoodsName() + ";";
		}
	
		// 是否匹配
		if (params.getStatus().equals("1")) {
			paramStr =paramStr +  "票据状态：处理中" + ";";
		}else if (params.getStatus().equals("2")) {
			paramStr = paramStr + "票据状态：成功" + ";";
		}else if (params.getStatus().equals("3")) {
			paramStr = paramStr + "票据状态：失败" + ";";
		}else if (params.getStatus().equals("4")) {
			paramStr = paramStr + "票据状态：锁定" + ";";
		}else if (params.getStatus().equals("5")) {
			paramStr = paramStr + "票据状态：完成处理中" + ";";
		}else if (params.getStatus().equals("-1")) {
			paramStr = paramStr + "票据状态：取消处理中" + ";";
		}
		return paramStr;
	}
}
