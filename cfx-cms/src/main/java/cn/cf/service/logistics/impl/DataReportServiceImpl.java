package cn.cf.service.logistics.impl;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.PageModel;
import cn.cf.common.ColAuthConstants;
import cn.cf.common.Constants;
import cn.cf.common.QueryModel;
import cn.cf.common.utils.CommonUtil;
import cn.cf.dao.LgDeliveryOrderExtDao;
import cn.cf.dao.SysExcelStoreExtDao;
import cn.cf.dto.DataReportDto;
import cn.cf.entity.DataReportExtDto;
import cn.cf.model.SearchReport;
import cn.cf.service.logistics.DataReportService;

/**
 * 毛利
 * 
 * @author xht
 *
 */
@Service
public class DataReportServiceImpl implements DataReportService {

	@Autowired
	private LgDeliveryOrderExtDao lgOrderExtDao;
	
	@Autowired
	private SysExcelStoreExtDao sysExcelStoreExtDao;

	/**
	 * 毛利
	 */
	@Override
	public PageModel<DataReportExtDto> searchGrossProfitList(QueryModel<SearchReport> qm, int i,String accountPk) {
		String allStr = "3,4,5,6";// 报表 的所有状态:6财务已确认；5提货中；4配送中；3已签收
		String str = "4,5,6";// 进行中状态：
		String finishStr = "3";// 完成
		String tempStr = "-1";
		PageModel<DataReportExtDto> pm = new PageModel<DataReportExtDto>();
		Map<String, Object> map = new HashMap<String, Object>();
		if (i == 1) {
			map.put("start", qm.getStart());
			map.put("limit", qm.getLimit());
		}
		map.put("orderName", qm.getFirstOrderName());
		map.put("orderType", qm.getFirstOrderType());
		map.put("logisticsCompanyName", qm.getEntity().getLogisticsCompanyName());
		map.put("logisticsContacts", qm.getEntity().getLogisticsContacts());
		map.put("orderPk", qm.getEntity().getOrderPk());
		map.put("insertStartTime", qm.getEntity().getInsertStartTime());
		map.put("isSettleFreight", qm.getEntity().getIsSettleFreight());
		map.put("insertEndTime", qm.getEntity().getInsertEndTime());
		// temp : 0:初始化 ； 1 全部 ；2 进行中；3 已完成；4 异常
		int temp = qm.getEntity().getReportStatus() == null ? 0 : qm.getEntity().getReportStatus();
		int orderTemp = qm.getEntity().getOrderStatus() == null ? 0 : qm.getEntity().getOrderStatus();
		
		if (temp == 0 || temp == 1 || temp == 4) {// 初始化；全部
			if (temp == 4) {// 异常：isAbnormal=2
				map.put("isAbnormal", Constants.ORDER_ISABNORMAL_002);
			}
			if (orderTemp == 0) {
				map.put("orderStatus", allStr.split(","));
			} else {
				if (orderTemp == Constants.ORDER_STATUS_003|| orderTemp == Constants.ORDER_STATUS_004 
						|| orderTemp == Constants.ORDER_STATUS_005|| orderTemp == Constants.ORDER_STATUS_006) {
					map.put("orderStatus", String.valueOf(orderTemp).split(","));
				} else {
					map.put("orderStatus", tempStr.split(","));
				}
			}
		}
		
		if (temp == 2) {// 进行中
			map.put("isAbnormal", Constants.ORDER_ISABNORMAL_001);
			if (orderTemp == 0) {
				map.put("orderStatus", str.split(","));
			} else {
				if (orderTemp == Constants.ORDER_STATUS_004 || orderTemp == Constants.ORDER_STATUS_005
						|| orderTemp == Constants.ORDER_STATUS_006) {
					map.put("orderStatus", String.valueOf(orderTemp).split(","));
				} else {
					map.put("orderStatus", tempStr.split(","));
				}
			}
		}
		
		if (temp == 3) {// 已完成
			map.put("isAbnormal", Constants.ORDER_ISABNORMAL_001);
			if (orderTemp == 0) {
				map.put("orderStatus", finishStr.split(","));
			} else {
				if (orderTemp == Constants.ORDER_STATUS_003) {
					map.put("orderStatus", String.valueOf(orderTemp).split(","));
				} else {
					map.put("orderStatus", tempStr.split(","));
				}
			}
		}
		
		map.put("colPurName",  CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_REPORT_GP_COL_PURNAME));
		map.put("colLogisticsName",  CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_REPORT_GP_COL_LOGISTICSNAME));
		int totalCount = lgOrderExtDao.searchGrossProfitCount(map);
		List<DataReportExtDto> list = lgOrderExtDao.searchGrossProfit(map);
		pm.setTotalCount(totalCount);
		pm.setDataList(list);
		return pm;
	}
	
	
	
	/**
	 * 客户报表
	 */
	@Override
	public PageModel<DataReportDto> searchCustomerDataReportList(QueryModel<SearchReport> qm, int i,String accountPk) {
		String str = "4,5,6,7,8,9";// 进行中状态
		String finishStr = "2,3,10";// 完成
		String tempStr = "-1";
		PageModel<DataReportDto> pm = new PageModel<DataReportDto>();
		Map<String, Object> map = new HashMap<String, Object>();
		if (i == 1) {
			map.put("start", qm.getStart());
			map.put("limit", qm.getLimit());
		}
		map.put("orderName", qm.getFirstOrderName());
		map.put("orderType", qm.getFirstOrderType());
		map.put("purchaserName", qm.getEntity().getPurchaserName());
		map.put("purchasersContacts", qm.getEntity().getPurchasersContacts());
		map.put("orderPk", qm.getEntity().getOrderPk());
		map.put("insertStartTime", qm.getEntity().getInsertStartTime());
		map.put("insertEndTime", qm.getEntity().getInsertEndTime());
		map.put("isSettleFreight", qm.getEntity().getIsSettleFreight());
		// temp : 0:初始化 ； 1 全部 ；2 进行中；3 已完成；4 异常
		int temp = qm.getEntity().getReportStatus() == null ? 0 : qm.getEntity().getReportStatus();
		int orderTemp = qm.getEntity().getOrderStatus() == null ? 0 : qm.getEntity().getOrderStatus();
		

		if (temp == 0 || temp == 1 || temp == 4) {// 初始化；全部；异常
			if (temp == 4) {// 异常：isAbnormal=2
				map.put("isAbnormal", Constants.ORDER_ISABNORMAL_002);
			}
			if (orderTemp == 0) {
				map.put("orderStatus", null);
			} else {
				map.put("orderStatus", String.valueOf(orderTemp).split(","));
			}
		}
		if (temp == 2) {// 进行中
			map.put("isAbnormal", Constants.ORDER_ISABNORMAL_001);
			if (orderTemp == 0) {
				map.put("orderStatus", str.split(","));
			} else {
				if (orderTemp != Constants.ORDER_STATUS_002 && orderTemp != Constants.ORDER_STATUS_003&&orderTemp != Constants.ORDER_STATUS_010) {
					map.put("orderStatus", String.valueOf(orderTemp).split(","));
				} else {
					map.put("orderStatus", tempStr.split(","));
				}
			}
		}
		if (temp == 3) {// 已完成
			map.put("isAbnormal", Constants.ORDER_ISABNORMAL_001);
			if (orderTemp == 0) {
				map.put("orderStatus", finishStr.split(","));
			} else {
				if (orderTemp == Constants.ORDER_STATUS_002 || orderTemp == Constants.ORDER_STATUS_003 ||orderTemp == Constants.ORDER_STATUS_010) {
					map.put("orderStatus", String.valueOf(orderTemp).split(","));
				} else {
					map.put("orderStatus", tempStr.split(","));
				}
			}
		}
		
		  map.put("colLogisticsName", true);
		  map.put("colFromCompanyName", true);
	      map.put("colPurName",  CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_REPORT_CUT_COL_PURNAME));
	      map.put("colToCompanyName",  CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_REPORT_CUT_COL_TOCOMNAME));
	      map.put("colPurContactsTel",  CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_REPORT_CUT_COL_PURCONTACTSTEL));
	      map.put("colToAddress",  CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_REPORT_CUT_COL_TOADDRESS));


		int totalCount = lgOrderExtDao.searchGrossProfitCount(map);

		List<DataReportDto> list = lgOrderExtDao.searchCustomGrossProfit(map);
		if (list.size() > 0) {
			for (DataReportDto dto : list) {
				if (null!=dto.getIsAbnormal()&&dto.getIsAbnormal().equals("异常")&&null!=dto.getIsConfirmed()&&dto.getIsConfirmed()==1) {
					dto.setOrderStatusName("异常-已确认");
				}
				if (null!=dto.getIsAbnormal()&&dto.getIsAbnormal().equals("异常")&&null!=dto.getIsConfirmed()&&dto.getIsConfirmed()==2) {
					dto.setOrderStatusName("异常-未确认");
				}
				//签收时间
				try {
					dto.setTransConsumption(countTransConsumption(dto.getTransConsumption()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		pm.setTotalCount(totalCount);
		pm.setDataList(list);
		return pm;
	}
	
	
	/**
	 * 
	 * @param insertTime 订单时间
	 * @param signTime 签收时间
	 * @return
	 */
	private String countTransConsumption(String   dataLong) throws ParseException {
			String result = null;
			long day = 0;  
	        long hour = 0;  
	        long min = 0;  
	        long sec = 0;  
	  			long diff ;  
	  			if (dataLong!=null && !dataLong.equals("")) {
	  				diff = Long.parseLong(dataLong)*1000;
	  				if (diff>0) {
	  				   day = diff / (24 * 60 * 60 * 1000);  
		  				hour = (diff / (60 * 60 * 1000) - day * 24);  
		  				min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);  
		  				sec = (diff/1000-day*24*60*60-hour*60*60-min*60);  
		  		       result = (day*24+hour)+"小时"+min+"分"+sec+"秒";  
					}else{
						result = "--";
					}
				}else {
					result = "--";
				}
	        return result; 
	}

	@Override
	public PageModel<DataReportDto> searchSupplierDataReportList(QueryModel<SearchReport> qm, int i,String accountPk) {
		String allStatus = "2,3,4,5,6,10";// 全部订单状态
		String str = "-1";
		PageModel<DataReportDto> pm = new PageModel<DataReportDto>();
		Map<String, Object> map = new HashMap<String, Object>();
		if (i == 1) {
			map.put("start", qm.getStart());
			map.put("limit", qm.getLimit());
		}
		map.put("orderName", qm.getFirstOrderName());
		map.put("orderType", qm.getFirstOrderType());
		map.put("logisticsCompanyName", qm.getEntity().getLogisticsCompanyName());
		map.put("logisticsContacts", qm.getEntity().getLogisticsContacts());
		map.put("orderPk", qm.getEntity().getOrderPk());
		map.put("insertStartTime", qm.getEntity().getInsertStartTime());
		map.put("insertEndTime", qm.getEntity().getInsertEndTime());
		// 结算状态
		map.put("isSettleFreight", qm.getEntity().getIsSettleFreight());
		map.put("isMatched", 1);
		// 订单状态
		Integer orderTemp = qm.getEntity().getOrderStatus();
		if (orderTemp == null) {
			map.put("orderStatus", allStatus.split(","));
		} else {
			if (orderTemp == Constants.ORDER_STATUS_002 || orderTemp == Constants.ORDER_STATUS_003
					|| orderTemp == Constants.ORDER_STATUS_004 || orderTemp == Constants.ORDER_STATUS_005
					|| orderTemp == Constants.ORDER_STATUS_006|| orderTemp == Constants.ORDER_STATUS_010) {
				map.put("orderStatus", String.valueOf(orderTemp).split(","));
			} else {
				map.put("orderStatus", String.valueOf(str).split(","));
			}
		}
		
		// 异常状态
		map.put("isAbnormal", qm.getEntity().getIsAbnormal());
		
		 map.put("colPurName",  true);
         map.put("colPurContactsTel", true);
       
         map.put("colToAddress",  CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_REPORT_SUPP_COL_TOADDRESS));
         map.put("colToCompanyName",  CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_REPORT_SUPP_COL_TOCOMNAME));
         map.put("colLogisticsName",  CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_REPORT_SUPP_COL_LOGISTICSNAME));
         map.put("colFromCompanyName",  CommonUtil.isExistAuthName(accountPk, ColAuthConstants.LG_REPORT_SUPP_COL_FROMCOMNAME));

         
		int totalCount = lgOrderExtDao.searchGrossProfitCount(map);
		List<DataReportDto> list = lgOrderExtDao.searchCustomGrossProfit(map);
		for (DataReportDto  dto :list ) {//物流供应商结算金额
			if (dto.getSupplierName() != null&& !dto.getSupplierName().equals("")) {
				dto.setFromCompanyName(dto.getSupplierName());
			}
			if (null!=dto.getIsAbnormal()&&dto.getIsAbnormal().equals("异常")&&null!=dto.getIsConfirmed()&&dto.getIsConfirmed()==1) {
				dto.setOrderStatusName("异常-已确认");
			}
			if (null!=dto.getIsAbnormal()&&dto.getIsAbnormal().equals("异常")&&null!=dto.getIsConfirmed()&&dto.getIsConfirmed()==2) {
				dto.setOrderStatusName("异常-未确认");
			}
		}
		pm.setTotalCount(totalCount);
		pm.setDataList(list);
		return pm;
	}

	public Double  checkDouble (Double num) {
		if(num==null || num.equals("")){
			return 0.0; 
		}else {
			return num ;
		}		
	}



	
	
}
