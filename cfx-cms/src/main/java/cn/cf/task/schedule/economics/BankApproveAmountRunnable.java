package cn.cf.task.schedule.economics;

import java.io.File;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

import cn.cf.common.ExportDoJsonParams;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.alibaba.fastjson.JSON;

import cn.cf.common.Constants;
import cn.cf.common.ExportUtil;
import cn.cf.common.utils.BeanUtils;
import cn.cf.common.utils.CommonUtil;
import cn.cf.common.utils.OSSUtils;
import cn.cf.dao.SysExcelStoreExtDao;
import cn.cf.dto.SysExcelStoreDto;
import cn.cf.dto.SysExcelStoreExtDto;
import cn.cf.entity.BankApproveAmountExport;
import cn.cf.entity.ReportFormsDataTypeParams;
import cn.cf.model.SysExcelStore;
import cn.cf.task.schedule.ScheduledFutureMap;
import cn.cf.util.ArithUtil;
import cn.cf.util.DateUtil;

public class BankApproveAmountRunnable implements Runnable {
	
	
	private String name;

	private String fileName;
	private String uuid;
	private SysExcelStoreExtDto storeDtoTemp;

	private SysExcelStoreExtDao storeDao;
	public BankApproveAmountRunnable() {

	}
	
	public BankApproveAmountRunnable(String name ,String uuid) {

		this.name = name;
		this.uuid =uuid;
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
		public void upLoadFile() throws Exception {

			this.storeDao = (SysExcelStoreExtDao) BeanUtils.getBean("sysExcelStoreExtDao");
			MongoTemplate mongoTemplate = (MongoTemplate) BeanUtils.getBean("mongoTemplate");
			
			if (storeDao != null) {
				Map<String, Object> map = new HashMap<>();
				map.put("isDeal", Constants.TWO);
				map.put("methodName", "exportBankApproveAmountList_"+StringUtils.defaultIfEmpty(this.uuid, ""));
				// 查询存在要导出的任务
				List<SysExcelStoreExtDto> storelist = storeDao.getFirstTimeExcelStore(map);
				if (storelist != null && storelist.size() > Constants.ZERO) {
					for (SysExcelStoreExtDto storeDto : storelist) {
						this.storeDtoTemp = storeDto;
						this.fileName = "金融中心-数据管理-银行审批额度 -" + storeDto.getAccountName() + "-"
								+ DateUtil.formatYearMonthDayHMS(new Date());
						if (CommonUtil.isNotEmpty(storeDto.getParams())) {
							ReportFormsDataTypeParams params = JSON.parseObject(storeDto.getParams(),
									ReportFormsDataTypeParams.class);
							if (mongoTemplate != null) {

								Query query = new Query();
								query.addCriteria(Criteria.where("bankPk").is(params.getBankPk()));
								query.addCriteria(Criteria.where("year").is(params.getYear()));
								query.with(new Sort(new Order(Direction.ASC, "flag"),new Order(Direction.ASC, "month")));
								List<BankApproveAmountExport>  list =	mongoTemplate.find(query, BankApproveAmountExport.class);
								BankApproveAmountExport  export = new BankApproveAmountExport();
								if (list!=null&&list.size()>0) {
									//补全缺少的月份
									setBankApproveAmountMonth(list,params.getYear());
										for (BankApproveAmountExport e : list) {
											e.setBTAmount(new BigDecimal(Double.parseDouble(e.getsBTAmount())+Double.parseDouble(e.getxBTAmount())+Double.parseDouble(e.getyBTAmount())+Double.parseDouble(e.getqBTAmount())+Double.parseDouble(e.getpBTAmount())).setScale(2,   BigDecimal.ROUND_HALF_UP).toString());
											e.setDAmount(new BigDecimal(Double.parseDouble(e.getsDAmount())+Double.parseDouble(e.getxDAmount())+Double.parseDouble(e.getyDAmount())+Double.parseDouble(e.getqDAmount())+Double.parseDouble(e.getpDAmount())+Double.parseDouble(e.getqDAmount())).setScale(2,   BigDecimal.ROUND_HALF_UP).toString());
											e.setBTTotalAmount(new BigDecimal(Double.parseDouble(e.getsBTTotalAmount())+Double.parseDouble(e.getxBTTotalAmount())+Double.parseDouble(e.getyBTTotalAmount())+Double.parseDouble(e.getpBTTotalAmount())+Double.parseDouble(e.getqBTTotalAmount())).setScale(2,   BigDecimal.ROUND_HALF_UP).toString());
											e.setDTotalAmount(new BigDecimal(Double.parseDouble(e.getsDTotalAmount())+Double.parseDouble(e.getxDTotalAmount())+Double.parseDouble(e.getyDTotalAmount())+Double.parseDouble(e.getpDTotalAmount())+Double.parseDouble(e.getqDTotalAmount())).setScale(2,   BigDecimal.ROUND_HALF_UP).toString());
											if (e.getFlag()==1) {
												e.setTitle("昨日");
											}
											if (e.getFlag()==2) {
												e.setTitle("当周");
											}
											if (e.getFlag()==3) {
												e.setTitle("上周");
											}
											if (e.getFlag()==4) {
												e.setTitle(e.getMonth()+"月");
												setBankApproveAmountExportTotal(export,e);//月的合计
											}
										}
										list.add(export);
								}else {
									setBankApproveAmountMonth(list,params.getYear());
								}
								//先按flag排序，再按月份排序
								Collections.sort(list);
																					
								String ossPath = "";
								if (list != null && list.size() > Constants.ZERO) {
									ExportUtil<BankApproveAmountExport> exportUtil = new ExportUtil<BankApproveAmountExport>();
									String path = exportUtil.exportDynamicUtil("bankApproveAmount", this.fileName, list);
									File file = new File(path);
									// 上传到OSS
									ossPath = OSSUtils.ossUploadXls(file, Constants.FIVE);
								}
								
								// 更新订单导出状态
								ExportDoJsonParams.updateExcelStoreStatus(storeDto,storeDao, ossPath);
							}
						}
					}
				}
			}

		}
		

		private void setBankApproveAmountExportTotal(BankApproveAmountExport export, BankApproveAmountExport e) {
			export.setTitle("合计");
			export.setFlag(5);
			export.setsBTAmount(ArithUtil.addString(export.getsBTAmount(),e.getsBTAmount()));
			export.setxBTAmount(ArithUtil.addString(export.getxBTAmount(),e.getxBTAmount()));
			export.setyBTAmount(ArithUtil.addString(export.getyBTAmount(),e.getyBTAmount()));
			export.setqBTAmount(ArithUtil.addString(export.getqBTAmount(),e.getqBTAmount()));
			export.setpBTAmount(ArithUtil.addString(export.getpBTAmount(),e.getpBTAmount()));
			export.setBTAmount(ArithUtil.addString(export.getBTAmount(),e.getBTAmount()));
			
			export.setsDAmount(ArithUtil.addString(export.getsDAmount(),e.getsDAmount()));
			export.setxDAmount(ArithUtil.addString(export.getxDAmount(),e.getxDAmount()));
			export.setyDAmount(ArithUtil.addString(export.getyDAmount(),e.getyDAmount()));
			export.setqDAmount(ArithUtil.addString(export.getqDAmount(),e.getqDAmount()));
			export.setpDAmount(ArithUtil.addString(export.getpDAmount(),e.getpDAmount()));
			export.setDAmount(ArithUtil.addString(export.getDAmount(),e.getDAmount()));
			
			export.setsBTTotalAmount(ArithUtil.addString(export.getsBTTotalAmount(),e.getsBTTotalAmount()));
			export.setxBTTotalAmount(ArithUtil.addString(export.getxBTTotalAmount(),e.getxBTTotalAmount()));
			export.setyBTTotalAmount(ArithUtil.addString(export.getyBTTotalAmount(),e.getyBTTotalAmount()));
			export.setqBTTotalAmount(ArithUtil.addString(export.getqBTTotalAmount(),e.getqBTTotalAmount()));
			export.setpBTTotalAmount(ArithUtil.addString(export.getpBTTotalAmount(),e.getpBTTotalAmount()));
			export.setBTTotalAmount(ArithUtil.addString(export.getBTTotalAmount(),e.getBTTotalAmount()));
			
			export.setsDTotalAmount(ArithUtil.addString(export.getsDTotalAmount(),e.getsDTotalAmount()));
			export.setxDTotalAmount(ArithUtil.addString(export.getxDTotalAmount(),e.getxDTotalAmount()));
			export.setyDTotalAmount(ArithUtil.addString(export.getyDTotalAmount(),e.getyDTotalAmount()));
			export.setqDTotalAmount(ArithUtil.addString(export.getqDTotalAmount(),e.getqDTotalAmount()));
			export.setpDTotalAmount(ArithUtil.addString(export.getpDTotalAmount(),e.getpDTotalAmount()));
			export.setDTotalAmount(ArithUtil.addString(export.getDTotalAmount(),e.getDTotalAmount()));
		}
		private void setBankApproveAmountMonth(List<BankApproveAmountExport> list, String year) {
			
			BankApproveAmountExport  newe = new BankApproveAmountExport();
			if (list!=null && list.size()>0) {
				int lastMonth = list.get(list.size()-1).getMonth();
				if(list.get(0).getFlag()==4){//非当前年数据
					if (list.get(0).getMonth()>1) {
						for (int i = 1; i < list.get(0).getMonth(); i++) {
							newe.setFlag(4);
							newe.setMonth(i);
							newe.setTitle(i+"月");
							list.add(newe);
							newe = new BankApproveAmountExport();
						}
					}
					if (lastMonth<12) {
						for (int i = lastMonth+1; i <=12; i++) {
							newe.setFlag(4);
							newe.setMonth(i);
							newe.setTitle(i+"月");
							list.add(newe);
							newe = new BankApproveAmountExport();
						}
					}
				}else{
					//该年份有数：默认有昨日，上周，当周，本月4条排列循序的数据
					if (list.size()==3) {//还未执行21的月
						for (int i = 1; i <= 12; i++) {
							newe.setFlag(4);
							newe.setMonth(i);
							newe.setTitle(i+"月");
							list.add(newe);
							newe = new BankApproveAmountExport();
						}
					}else{
						if (list.get(3).getMonth()>1) {//从年中旬开始统计，补全之前月
							for (int i = 1; i < list.get(3).getMonth(); i++) {
								newe.setFlag(4);
								newe.setMonth(i);
								list.add(newe);
								newe = new BankApproveAmountExport();
							}
						}
						if (lastMonth<12) {//统计到年终，补全之月
							for (int i = lastMonth+1; i <= 12; i++) {
								newe.setFlag(4);
								newe.setMonth(i);
								list.add(newe);
								newe = new BankApproveAmountExport();
							}
						}
					}
				}
			}else{//该年份无数据
				if (CommonUtil.yesterday(1).toString().equals(year)) {
					newe = new BankApproveAmountExport();
					newe.setFlag(1);
					newe.setMonth(CommonUtil.yesterday(2));
					newe.setTitle("昨日");
					list.add(newe);
					newe = new BankApproveAmountExport();
					newe.setFlag(2);
					newe.setMonth(CommonUtil.yesterday(2));
					newe.setTitle("当周");
					list.add(newe);
					newe = new BankApproveAmountExport();
					newe.setFlag(3);
					newe.setMonth(CommonUtil.yesterday(2));
					newe.setTitle("上周");
					list.add(newe);
				}
				for (int i = 1; i <= 12; i++) {
					newe = new BankApproveAmountExport();
					newe.setFlag(4);
					newe.setMonth(i);
					newe.setTitle(i+"月");
					list.add(newe);
				}
				newe = new BankApproveAmountExport();
				newe.setTitle("合计");
				newe.setFlag(5);
				list.add(newe);
			}
		}
		
		public static String bankApproveAmountParam(ReportFormsDataTypeParams params) {
			String paramStr = "";

			if (CommonUtil.isNotEmpty(params.getBankName())) {
				paramStr = paramStr + "银行名称：" + params.getBankName() + ";";
			}
			if (CommonUtil.isNotEmpty(params.getYear())) {
				paramStr = paramStr + "年份：" + params.getYear();
			} 
			return paramStr;
		}
		
	
}
