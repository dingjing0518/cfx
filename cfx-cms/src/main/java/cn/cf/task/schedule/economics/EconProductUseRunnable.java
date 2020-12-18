
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
import cn.cf.entity.EcnoProductUseExport;
import cn.cf.entity.ReportFormsDataTypeParams;
import cn.cf.model.SysExcelStore;
import cn.cf.task.schedule.ScheduledFutureMap;
import cn.cf.util.ArithUtil;
import cn.cf.util.DateUtil;

public class EconProductUseRunnable implements Runnable {
	
	
	private String name;

	private String fileName;
	
	private String uuid;
	private SysExcelStoreExtDao storeDao;
	private SysExcelStoreExtDto storeDtoTemp;
	public EconProductUseRunnable() {

	}
	public EconProductUseRunnable(String name,String uuid) {

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

	
	private void upLoadFile() {

		this.storeDao = (SysExcelStoreExtDao) BeanUtils.getBean("sysExcelStoreExtDao");
		MongoTemplate mongoTemplate = (MongoTemplate) BeanUtils.getBean("mongoTemplate");
		
		if (storeDao != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("isDeal", Constants.TWO);
			map.put("methodName", "exportEconProductUseList_"+StringUtils.defaultIfEmpty(this.uuid, ""));
			// 查询存在要导出的任务
			List<SysExcelStoreExtDto> storelist = storeDao.getFirstTimeExcelStore(map);
			if (storelist != null && storelist.size() > Constants.ZERO) {
				for (SysExcelStoreExtDto storeDto : storelist) {
                    this.storeDtoTemp = storeDto;
					this.fileName = "金融中心-数据管理-银行金融产品使用情况 -" + storeDto.getAccountName() + "-"
							+DateUtil.formatYearMonthDayHMS(new Date());
					if (CommonUtil.isNotEmpty(storeDto.getParams())) {
						ReportFormsDataTypeParams params = JSON.parseObject(storeDto.getParams(),
								ReportFormsDataTypeParams.class);
						if (mongoTemplate != null) {

							Query query = new Query();
							query.addCriteria(Criteria.where("bankPk").is(params.getBankPk()));
							query.addCriteria(Criteria.where("year").is(params.getYear()));
							query.with(new Sort(new Order(Direction.ASC, "flag"),new Order(Direction.ASC, "month")));
							List<EcnoProductUseExport>  list =	mongoTemplate.find(query, EcnoProductUseExport.class);
						    
							EcnoProductUseExport  export = new EcnoProductUseExport();
							if (list!=null&&list.size()>0) {
								//补全缺少的月份
								setEcnoProductUseMonth(list,params.getYear());
								for (EcnoProductUseExport e : list) {
									e.setbTAmount(new BigDecimal(Double.parseDouble(e.getsBTAmount())+Double.parseDouble(e.getxBTAmount())+
											Double.parseDouble(e.getyBTAmount())+Double.parseDouble(e.getqBTAmount())+Double.parseDouble(e.getpBTAmount())).setScale(2,   BigDecimal.ROUND_HALF_UP).toString());
									e.setdAmount(new BigDecimal(Double.parseDouble(e.getsDAmount())+Double.parseDouble(e.getxDAmount())+
											Double.parseDouble(e.getyDAmount())+Double.parseDouble(e.getqDAmount())+Double.parseDouble(e.getpDAmount())).setScale(2,   BigDecimal.ROUND_HALF_UP).toString());
									e.setbTTotalAmount(new BigDecimal(Double.parseDouble(e.getsBTTotalAmount())+Double.parseDouble(e.getxBTTotalAmount())+
											Double.parseDouble(e.getyBTTotalAmount())+Double.parseDouble(e.getqBTTotalAmount())+Double.parseDouble(e.getpBTTotalAmount())).setScale(2,   BigDecimal.ROUND_HALF_UP).toString());
									e.setdTotalAmount(new BigDecimal(Double.parseDouble(e.getsDTotalAmount())+Double.parseDouble(e.getxDTotalAmount())+
											Double.parseDouble(e.getyDTotalAmount())+Double.parseDouble(e.getqDTotalAmount())+Double.parseDouble(e.getpDTotalAmount())).setScale(2,   BigDecimal.ROUND_HALF_UP).toString());
									e.setPayBTAmount(new BigDecimal(Double.parseDouble(e.getsPayBTAmount())+Double.parseDouble(e.getxPayBTAmount())+
											Double.parseDouble(e.getyPayBTAmount())+Double.parseDouble(e.getqPayBTAmount())+Double.parseDouble(e.getpPayBTAmount())).setScale(2,   BigDecimal.ROUND_HALF_UP).toString());
									e.setPayDAmount(new BigDecimal(Double.parseDouble(e.getsPayDAmount())+Double.parseDouble(e.getxPayDAmount())+
											Double.parseDouble(e.getyPayDAmount())+Double.parseDouble(e.getqPayDAmount())+Double.parseDouble(e.getpPayDAmount())).setScale(2,   BigDecimal.ROUND_HALF_UP).toString());
									e.setPayBTTotalAmount(new BigDecimal(Double.parseDouble(e.getsPayBTTotalAmount())+Double.parseDouble(e.getxPayBTTotalAmount())+
											Double.parseDouble(e.getyPayBTTotalAmount())+Double.parseDouble(e.getqPayBTTotalAmount())+Double.parseDouble(e.getpPayBTTotalAmount())).setScale(2,   BigDecimal.ROUND_HALF_UP).toString());
									e.setPayDTotalAmount(new BigDecimal(Double.parseDouble(e.getsPayDTotalAmount())+Double.parseDouble(e.getxPayDTotalAmount())+
											Double.parseDouble(e.getyPayDTotalAmount())+Double.parseDouble(e.getqPayDTotalAmount())+Double.parseDouble(e.getpPayDTotalAmount())).setScale(2,   BigDecimal.ROUND_HALF_UP).toString());
									e.setNowBTAmount(new BigDecimal(Double.parseDouble(e.getsNowBTAmount())+Double.parseDouble(e.getxNowBTAmount())+
											Double.parseDouble(e.getyNowBTAmount())+Double.parseDouble(e.getqNowBTAmount())+Double.parseDouble(e.getpNowBTAmount())).setScale(2,   BigDecimal.ROUND_HALF_UP).toString());
									e.setNowDAmount(new BigDecimal(Double.parseDouble(e.getsNowDAmount())
											+ Double.parseDouble(e.getxNowDAmount())+Double.parseDouble(e.getyNowDAmount())+
											Double.parseDouble(e.getqNowDAmount())+Double.parseDouble(e.getpNowDAmount())).setScale(2,   BigDecimal.ROUND_HALF_UP).toString());
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
										setEcnoProductUseExportTotal(export,e);//月的合计
									}
								}
									list.add(export);
							}else {
								setEcnoProductUseMonth(list,params.getYear());
							}
							//先按flag排序，再按月份排序
							Collections.sort(list);
																				
							String ossPath = "";
							if (list != null && list.size() > Constants.ZERO) {
								ExportUtil<EcnoProductUseExport> exportUtil = new ExportUtil<EcnoProductUseExport>();
								String path = exportUtil.exportDynamicUtil("ecnoProductUse", this.fileName, list);
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


	private void setEcnoProductUseExportTotal(EcnoProductUseExport export, EcnoProductUseExport e) {
		export.setTitle("合计");
		export.setFlag(5);
		export.setsBTAmount(ArithUtil.addString(export.getsBTAmount(),e.getsBTAmount()));
		export.setxBTAmount(ArithUtil.addString(export.getxBTAmount(),e.getxBTAmount()));
		export.setyBTAmount(ArithUtil.addString(export.getyBTAmount(),e.getyBTAmount()));
		export.setqBTAmount(ArithUtil.addString(export.getqBTAmount(),e.getqBTAmount()));
		export.setpBTAmount(ArithUtil.addString(export.getpBTAmount(),e.getpBTAmount()));
		export.setbTAmount(ArithUtil.addString(export.getbTAmount(),e.getbTAmount()));
		
		export.setsDAmount(ArithUtil.addString(export.getsDAmount(),e.getsDAmount()));
		export.setxDAmount(ArithUtil.addString(export.getxDAmount(),e.getxDAmount()));
		export.setyDAmount(ArithUtil.addString(export.getyDAmount(),e.getyDAmount()));
		export.setqDAmount(ArithUtil.addString(export.getqDAmount(),e.getqDAmount()));
		export.setpDAmount(ArithUtil.addString(export.getpDAmount(),e.getpDAmount()));
		export.setdAmount(ArithUtil.addString(export.getdAmount(),e.getdAmount()));
		
		export.setsBTTotalAmount(ArithUtil.addString(export.getsBTTotalAmount(),e.getsBTTotalAmount()));
		export.setxBTTotalAmount(ArithUtil.addString(export.getxBTTotalAmount(),e.getxBTTotalAmount()));
		export.setyBTTotalAmount(ArithUtil.addString(export.getyBTTotalAmount(),e.getyBTTotalAmount()));
		export.setqBTTotalAmount(ArithUtil.addString(export.getqBTTotalAmount(),e.getqBTTotalAmount()));
		export.setpBTTotalAmount(ArithUtil.addString(export.getpBTTotalAmount(),e.getpBTTotalAmount()));
		export.setbTTotalAmount(ArithUtil.addString(export.getbTTotalAmount(),e.getbTTotalAmount()));

		export.setsDTotalAmount(ArithUtil.addString(export.getsDTotalAmount(),e.getsDTotalAmount()));
		export.setxDTotalAmount(ArithUtil.addString(export.getxDTotalAmount(),e.getxDTotalAmount()));
		export.setyDTotalAmount(ArithUtil.addString(export.getyDTotalAmount(),e.getyDTotalAmount()));
		export.setqDTotalAmount(ArithUtil.addString(export.getqDTotalAmount(),e.getqDTotalAmount()));
		export.setpDTotalAmount(ArithUtil.addString(export.getpDTotalAmount(),e.getpDTotalAmount()));
		export.setdTotalAmount(ArithUtil.addString(export.getdTotalAmount(),e.getdTotalAmount()));
		
		export.setsPayBTAmount(ArithUtil.addString(export.getsPayBTAmount(),e.getsPayBTAmount()));
		export.setxPayBTAmount(ArithUtil.addString(export.getxPayBTAmount(),e.getxPayBTAmount()));
		export.setyPayBTAmount(ArithUtil.addString(export.getyPayBTAmount(),e.getyPayBTAmount()));
		export.setqPayBTAmount(ArithUtil.addString(export.getqPayBTAmount(),e.getqPayBTAmount()));
		export.setpPayBTAmount(ArithUtil.addString(export.getpPayBTAmount(),e.getpPayBTAmount()));
		export.setPayBTAmount(ArithUtil.addString(export.getPayBTAmount(),e.getPayBTAmount()));
		
		export.setsPayDAmount(ArithUtil.addString(export.getsPayDAmount(),e.getsPayDAmount()));
		export.setxPayDAmount(ArithUtil.addString(export.getxPayDAmount(),e.getxPayDAmount()));
		export.setyPayDAmount(ArithUtil.addString(export.getyPayDAmount(),e.getyPayDAmount()));
		export.setqPayDAmount(ArithUtil.addString(export.getqPayDAmount(),e.getqPayDAmount()));
		export.setpPayDAmount(ArithUtil.addString(export.getpPayDAmount(),e.getpPayDAmount()));
		export.setPayDAmount(ArithUtil.addString(export.getPayDAmount(),e.getPayDAmount()));

		export.setsPayBTTotalAmount(ArithUtil.addString(export.getsPayBTTotalAmount(),e.getsPayBTTotalAmount()));
		export.setxPayBTTotalAmount(ArithUtil.addString(export.getxPayBTTotalAmount(),e.getxPayBTTotalAmount()));
		export.setyPayBTTotalAmount(ArithUtil.addString(export.getyPayBTTotalAmount(),e.getyPayBTTotalAmount()));
		export.setqPayBTTotalAmount(ArithUtil.addString(export.getqPayBTTotalAmount(),e.getqPayBTTotalAmount()));
		export.setpPayBTTotalAmount(ArithUtil.addString(export.getpPayBTTotalAmount(),e.getpPayBTTotalAmount()));
		export.setPayBTTotalAmount(ArithUtil.addString(export.getPayBTTotalAmount(),e.getPayBTTotalAmount()));
		
		export.setsPayDTotalAmount(ArithUtil.addString(export.getsPayDTotalAmount(),e.getsPayDTotalAmount()));
		export.setxPayDTotalAmount(ArithUtil.addString(export.getxPayDTotalAmount(),e.getxPayDTotalAmount()));
		export.setyPayDTotalAmount(ArithUtil.addString(export.getyPayDTotalAmount(),e.getyPayDTotalAmount()));
		export.setqPayDTotalAmount(ArithUtil.addString(export.getqPayDTotalAmount(),e.getqPayDTotalAmount()));
		export.setpPayDTotalAmount(ArithUtil.addString(export.getpPayDTotalAmount(),e.getpPayDTotalAmount()));
		export.setPayDTotalAmount(ArithUtil.addString(export.getPayDTotalAmount(),e.getPayDTotalAmount()));
		
		export.setsNowBTAmount(ArithUtil.addString(export.getsNowBTAmount(),e.getsNowBTAmount()));
		export.setxNowBTAmount(ArithUtil.addString(export.getxNowBTAmount(),e.getxNowBTAmount()));
		export.setyNowBTAmount(ArithUtil.addString(export.getyNowBTAmount(),e.getyNowBTAmount()));
		export.setqNowBTAmount(ArithUtil.addString(export.getqNowBTAmount(),e.getqNowBTAmount()));
		export.setpNowBTAmount(ArithUtil.addString(export.getpNowBTAmount(),e.getpNowBTAmount()));
		export.setNowBTAmount(ArithUtil.addString(export.getNowBTAmount(),e.getNowBTAmount()));
		
		export.setsNowDAmount(ArithUtil.addString(export.getsNowDAmount(),e.getsNowDAmount()));
		export.setxNowDAmount(ArithUtil.addString(export.getxNowDAmount(),e.getxNowDAmount()));
		export.setyNowDAmount(ArithUtil.addString(export.getyNowDAmount(),e.getyNowDAmount()));
		export.setqNowDAmount(ArithUtil.addString(export.getqNowDAmount(),e.getqNowDAmount()));
		export.setpNowDAmount(ArithUtil.addString(export.getpNowDAmount(),e.getpNowDAmount()));
		export.setNowDAmount(ArithUtil.addString(export.getNowDAmount(),e.getNowDAmount()));
	}




	private void setEcnoProductUseMonth(List<EcnoProductUseExport> list, String year) {
		
		EcnoProductUseExport  newe = new EcnoProductUseExport();
		if (list!=null && list.size()>0) {//该年份有数：默认有昨日，上周，当周，本月4条排列循序的数据
			
			int lastMonth = list.get(list.size()-1).getMonth();
			if(list.get(0).getFlag()==4){//非当前年数据
				if (list.get(0).getMonth()>1) {
					for (int i = 1; i < list.get(0).getMonth(); i++) {
						newe.setFlag(4);
						newe.setMonth(i);
						newe.setTitle(i+"月");
						list.add(newe);
						newe = new EcnoProductUseExport();
					}
				}
				if (lastMonth<12) {
					for (int i = lastMonth+1; i <=12; i++) {
						newe.setFlag(4);
						newe.setMonth(i);
						newe.setTitle(i+"月");
						list.add(newe);
						newe = new EcnoProductUseExport();
					}
				}
			}else{
				if (list.get(3).getMonth()>1) {//从年中旬开始统计，补全之前月
					for (int i = 1; i < list.get(3).getMonth(); i++) {
						newe.setFlag(4);
						newe.setMonth(i);
						list.add(newe);
						newe = new EcnoProductUseExport();
					}
				}
				if (lastMonth<12) {//统计到年终，补全之月
					for (int i = lastMonth+1; i <= 12; i++) {
						newe.setFlag(4);
						newe.setMonth(i);
						list.add(newe);
						newe = new EcnoProductUseExport();
					}
				}
			}
		}else{//该年份无数据
			if (CommonUtil.yesterday(1).toString().equals(year)) {
				newe = new EcnoProductUseExport();
				newe.setFlag(1);
				newe.setMonth(CommonUtil.yesterday(2));
				newe.setTitle("昨日");
				list.add(newe);
				newe = new EcnoProductUseExport();
				newe.setFlag(2);
				newe.setMonth(CommonUtil.yesterday(2));
				newe.setTitle("当周");
				list.add(newe);
				newe = new EcnoProductUseExport();
				newe.setFlag(3);
				newe.setMonth(CommonUtil.yesterday(2));
				newe.setTitle("上周");
				list.add(newe);
			}
			for (int i = 1; i <= 12; i++) {
				newe = new EcnoProductUseExport();
				newe.setFlag(4);
				newe.setMonth(i);
				newe.setTitle(i+"月");
				list.add(newe);
			}
			newe = new EcnoProductUseExport();
			newe.setTitle("合计");
			newe.setFlag(5);
			list.add(newe);
		}
	}




	public static String econProductUseParam(ReportFormsDataTypeParams params) {
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
