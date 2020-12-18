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
import cn.cf.dto.SysExcelStoreDto;
import cn.cf.dto.SysExcelStoreExtDto;
import cn.cf.entity.OrderDataTypeParams;
import cn.cf.model.LgOrderGridModel;
import cn.cf.model.SysExcelStore;
import cn.cf.task.schedule.ScheduledFutureMap;
import cn.cf.util.DateUtil;

public class LogisticsOrderRunnable  implements Runnable {

	private String name;

	private String fileName;

	private String accountPk;
	
	private int    flag;

	private SysExcelStoreExtDto storeDtoTemp;
	private String uuid;
	private SysExcelStoreExtDao storeDao;
	
	public LogisticsOrderRunnable(int flag) {//1：正常订单 2：异常订单
		this.flag = flag;
	}
	
	public LogisticsOrderRunnable(String name,String uuid,int flag) {//1：正常订单 2：异常订单
		this.name = name;
		
		this.uuid = uuid;
		
		this.flag = flag;
		
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

		LgDeliveryOrderExtDao lgOrderExtDao = (LgDeliveryOrderExtDao) BeanUtils.getBean("lgDeliveryOrderExtDao");
		this.storeDao = (SysExcelStoreExtDao) BeanUtils.getBean("sysExcelStoreExtDao");
		if (storeDao != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("isDeal", Constants.TWO);
			if (this.flag ==1) {
				map.put("methodName", "exportLgOrderForm_"+StringUtils.defaultIfEmpty(this.uuid, ""));
			}else{
				map.put("methodName", "exportLgAbOrderForm_"+StringUtils.defaultIfEmpty(this.uuid, ""));
			}
			
			// 查询存在要导出的任务
			List<SysExcelStoreExtDto> list = storeDao.getFirstTimeExcelStore(map);
			if (list != null && list.size() > Constants.ZERO) {
				for (SysExcelStoreExtDto storeDto : list) {
					this.storeDtoTemp = storeDto;
					if (CommonUtil.isNotEmpty(storeDto.getParams())) {
						OrderDataTypeParams params = JSON.parseObject(storeDto.getParams(),
								OrderDataTypeParams.class);
						if (this.flag  ==1) {
							this.fileName = "物流中心-订单管理-正常订单-" + storeDto.getAccountName() + "-"
								+ DateUtil.formatYearMonthDayHMS(new Date());
						}else if (this.flag  ==2) {
							this.fileName = "物流中心-订单管理-异常订单-" + storeDto.getAccountName() + "-"
									+ DateUtil.formatYearMonthDayHMS(new Date());
						}
						this.accountPk = storeDto.getAccountPk();
						if (lgOrderExtDao != null) {
							String modelName = "";
							// 设置查询参数
							Map<String, Object> orderMap =  orderParams(params, storeDto);
							if (this.flag  ==1) {
								setColOrderParams(orderMap);
								modelName = "logisticsOrderForm";
							}else if (this.flag  ==2) {
								setColAbOrderParams(orderMap);
								 modelName = "logisticsAbOrderForm";
							}
							// 设置权限
							String ossPath = "";
							int counts = lgOrderExtDao.countlgOrder(orderMap);
							if (counts > Constants.EXCEL_NUMBER_TENHOUSAND) {
								// 大于10000，分页查询数据
								ossPath = setLimitPages(orderMap, counts, lgOrderExtDao, storeDto,modelName);
							} else {
								// 如果小于或等于10000条直接上传
								ossPath = setNotLimitPages(orderMap, lgOrderExtDao, storeDto,modelName);
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
			SysExcelStoreExtDto storeDto,String modelName) {
		String ossPath = "";
        List<LgOrderGridModel> list = lgOrderExtDao.getlgOrder(orderMap);
        if (list.size() > 0) {
            for (LgOrderGridModel lgOrderGridModel : list) {
                if (lgOrderGridModel.getSupplierName() != null && !lgOrderGridModel.getSupplierName().equals("")) {
                    lgOrderGridModel.setFromCompanyName(lgOrderGridModel.getSupplierName());
                }
            }
        }
		if (list != null && list.size() > Constants.ZERO) {
			ExportUtil<LgOrderGridModel> exportUtil = new ExportUtil<LgOrderGridModel>();
			String path = exportUtil.exportDynamicUtil(modelName, this.fileName, list);
			File file = new File(path);
			// 上传到OSS
			ossPath = OSSUtils.ossUploadXls(file, Constants.FIVE);
		}
		return ossPath;
	}

	private String setLimitPages(Map<String, Object> orderMap, int counts, LgDeliveryOrderExtDao lgOrderExtDao,
			SysExcelStoreExtDto storeDto,String modelName) {
		double numbers = Math.floor(counts / Constants.EXCEL_NUMBER_TENHOUSAND);// 获取分页查询次数
		orderMap.put("limit", Constants.EXCEL_NUMBER_TENHOUSAND);
		List<File> fileList = new ArrayList<>();
		for (int i = Constants.ZERO; i < numbers + Constants.ONE; i++) { //
			int start = Constants.ZERO;
			if (i != Constants.ZERO) {
				start = ExportDoJsonParams.indexPageNumbers(i);
			}
			orderMap.put("start", start);
			
	        List<LgOrderGridModel> list = lgOrderExtDao.getlgOrder(orderMap);
	        if (list.size() > 0) {
	            for (LgOrderGridModel lgOrderGridModel : list) {
	                if (lgOrderGridModel.getSupplierName() != null && !lgOrderGridModel.getSupplierName().equals("")) {
	                    lgOrderGridModel.setFromCompanyName(lgOrderGridModel.getSupplierName());
	                }
	            }
	        }
	        
			orderMap.remove("start");
			if (list != null && list.size() > Constants.ZERO) {
				ExportUtil<LgOrderGridModel> exportUtil = new ExportUtil<LgOrderGridModel>();
				String excelName = "";
				if (this.flag  ==1) {
					excelName = "物流中心-订单管理-正常订单-" + storeDto.getAccountName() + "-"
							+DateUtil.formatYearMonthDayHMS(new Date())+"-"+(i+1);
				}else {
					excelName = "物流中心-订单管理-异常订单-" + storeDto.getAccountName() + "-"
							+DateUtil.formatYearMonthDayHMS(new Date())+"-"+(i+1);
				}
				String path = exportUtil.exportDynamicUtil(modelName, excelName, list);
				File file = new File(path);
				fileList.add(file);
			}
		}
		// 上传Zip到OSS
		return OSSUtils.ossUploadXls(new File(ZipUtils.fileToZip(fileList, this.fileName)), Constants.FILE);
	}

	private void setColAbOrderParams(Map<String, Object> orderMap) {
		orderMap.put("colLogisticsComName", CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_ORDER_ORDERERROR_COL_LOGISTICSCOMNAME));
		orderMap.put("colFromComName",CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_ORDER_ORDERERROR_COL_FROMCOMNAME));
		orderMap.put("colFromAddress", CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_ORDER_ORDERERROR_COL_FROMADDRESS));
		orderMap.put("colFromContacts", CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_ORDER_ORDERERROR_COL_FROMCONTACTS));
        orderMap.put("colFromContactsTel", CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_ORDER_ORDERERROR_COL_FROMCONTACTSTEL));
        orderMap.put("colToComName", CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_ORDER_ORDERERROR_COL_TOCOMNAME));
        orderMap.put("colToAddress", CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_ORDER_ORDERERROR_COL_TOADDRESS));
        orderMap.put("colToContacts", CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_ORDER_ORDERERROR_COL_TOCONTACTS));
        orderMap.put("colToContactsTel", CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_ORDER_ORDERERROR_COL_TOCONTACTSTEL));
    }

	private void setColOrderParams(Map<String, Object> orderMap) {//正常
		orderMap.put("colLogisticsComName", CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_ORDER_NORMAL_COL_LOGISTICSCOMNAME));
		orderMap.put("colFromComName",CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_ORDER_NORMAL_COL_FROMCOMNAME));
		orderMap.put("colFromAddress", CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_ORDER_NORMAL_COL_FROMADDRESS));
		orderMap.put("colFromContacts", CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_ORDER_NORMAL_COL_FROMCONTACTS));
		orderMap.put("colFromContactsTel", CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_ORDER_NORMAL_COL_FROMCONTACTSTEL));
		orderMap.put("colToComName", CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_ORDER_NORMAL_COL_TOCOMNAME));
		orderMap.put("colToAddress", CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_ORDER_NORMAL_COL_TOADDRESS));
		orderMap.put("colToContacts", CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_ORDER_NORMAL_COL_TOCONTACTS));
		orderMap.put("colToContactsTel", CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_ORDER_NORMAL_COL_TOCONTACTSTEL));
    }

	private Map<String, Object> orderParams(OrderDataTypeParams params, SysExcelStoreExtDto dto) {
		String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dto.getInsertTime());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderName", "insertTime");
		map.put("orderType", "desc");
		if (CommonUtil.isNotEmpty(params.getOrderNumber())) {
			map.put("orderNum", params.getOrderNumber());
		}
		
		if (CommonUtil.isNotEmpty(params.getOrderTimeStart())) {
			map.put("orderTimeStart", params.getOrderTimeStart());
		} 
		if (CommonUtil.isNotEmpty(params.getOrderTimeEnd())) {
			map.put("orderTimeEnd", params.getOrderTimeEnd());
		} 
		
		if (CommonUtil.isNotEmpty(params.getGoodsInfo())) {
			map.put("goodsInfo", params.getGoodsInfo());
		} 
		
		if (CommonUtil.isNotEmpty(params.getFromCompanyName())) {
			map.put("fromCompanyName", params.getFromCompanyName());
		}
		
		if (CommonUtil.isNotEmpty(params.getFromContactsTel())) {
			map.put("fromContactsTel", params.getFromContactsTel());
		}
		
		if (CommonUtil.isNotEmpty(params.getToCompanyName())) {
			map.put("toCompanyName", params.getToCompanyName());
		}
		
		if (CommonUtil.isNotEmpty(params.getToContactsTel())) {
			map.put("toContactsTel", params.getToContactsTel());
		}
		
		if (CommonUtil.isNotEmpty(params.getOrderStatus())) {
			map.put("orderStatus", params.getOrderStatus());
		}
		
		if (CommonUtil.isNotEmpty(params.getArrivedTimeStart())) {
			map.put("arrivedTimeStart", params.getArrivedTimeStart());
		} 
		
		if (CommonUtil.isNotEmpty(params.getArrivedTimeEnd())) {
			map.put("arrivedTimeEnd", params.getArrivedTimeEnd());
		} 
		// 如果没有选择下单时间条件，则判断查询当前导出列表的添加时间作为结束时间
			map.put("insertTime", now);

		if (CommonUtil.isNotEmpty(params.getIsAbnormal())) {
			map.put("isAbnormal", params.getIsAbnormal());
		} 

		if (CommonUtil.isNotEmpty(params.getIsConfirmed())) {
			map.put("isConfirmed", params.getIsConfirmed());
		} 
		
		if (CommonUtil.isNotEmpty(params.getIsMatched())) {
			map.put("isMatched", params.getIsMatched());
		} 
		
		return map;
	}

	public static String logisticsOrderParam(OrderDataTypeParams params, SysExcelStore store) {
		String paramStr = "";
		if (CommonUtil.isNotEmpty(params.getOrderNumber())) {
			paramStr = paramStr +"订单编号：" + params.getOrderNumber() + ";";
		}
		
		paramStr = CommonUtil.getDateShow(paramStr,  params.getOrderTimeStart(), params.getOrderTimeEnd(),  "订单时间：");
		paramStr = CommonUtil.getDateShow(paramStr,  params.getArrivedTimeStart(), params.getArrivedTimeEnd(),  "配送时间：");
	
		if (CommonUtil.isNotEmpty(params.getGoodsInfo())) {
			paramStr = paramStr +"商品搜索：" + params.getGoodsInfo() + ";";
		}
		
		if (CommonUtil.isNotEmpty(params.getFromCompanyName())) {
			paramStr =paramStr + "提货公司：" + params.getFromCompanyName() + ";";
		}
		
		if (CommonUtil.isNotEmpty(params.getFromContactsTel())) {
			paramStr = paramStr +"提货人号码：" + params.getFromContactsTel() + ";";
		}
		
		if (CommonUtil.isNotEmpty(params.getToCompanyName())) {
			paramStr =paramStr + "送货公司：" + params.getToCompanyName() + ";";
		}
		
		if (CommonUtil.isNotEmpty(params.getToContactsTel())) {
			paramStr = paramStr +"收货人号码：" + params.getToContactsTel() + ";";
		}
		
		// 是否匹配
		if (params.getIsMatched().equals("0")) {//未匹配
			paramStr =paramStr + "订单状态：未匹配" + ";";
			
		}else if (params.getIsMatched().equals("1")) {//已匹配
			int orderTemp =params.getOrderStatus() == null ? 0 : Integer.parseInt(params.getOrderStatus());
			paramStr =paramStr + "订单状态：" +getStatusName(orderTemp) +";";
		}
		return paramStr;
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

	public static String logisticsAbOrderParam(OrderDataTypeParams params, SysExcelStore store) {
		String paramStr = "";
		if (CommonUtil.isNotEmpty(params.getOrderNumber())) {
			paramStr = "订单编号：" + params.getOrderNumber() + ";";
		}
		
		paramStr = CommonUtil.getDateShow(paramStr,  params.getOrderTimeStart(), params.getOrderTimeEnd(),  "订单时间：");
		paramStr = CommonUtil.getDateShow(paramStr,  params.getArrivedTimeStart(), params.getArrivedTimeEnd(),  "配送时间：");

		if (CommonUtil.isNotEmpty(params.getGoodsInfo())) {
			paramStr =paramStr +  "商品搜索：" + params.getGoodsInfo() + ";";
		}
		
		if (CommonUtil.isNotEmpty(params.getFromCompanyName())) {
			paramStr = paramStr + "提货公司：" + params.getFromCompanyName() + ";";
		}
		
		if (CommonUtil.isNotEmpty(params.getFromContactsTel())) {
			paramStr = paramStr + "提货人号码：" + params.getFromContactsTel() + ";";
		}
		
		if (CommonUtil.isNotEmpty(params.getToCompanyName())) {
			paramStr = paramStr + "送货公司：" + params.getToCompanyName() + ";";
		}
		
		if (CommonUtil.isNotEmpty(params.getToContactsTel())) {
			paramStr = paramStr + "收货人号码：" + params.getToContactsTel() + ";";
		}
		
		// 是否匹配
		if (params.getIsConfirmed().equals("1")) {
			paramStr =paramStr +  "是否确认：已确认" + ";";
			
		}else if (params.getIsConfirmed().equals("2")) {
			paramStr = paramStr + "是否确认：未确认" + ";";
		}
		return paramStr;
	}

}
