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

import cn.cf.common.ColAuthConstants;
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
import cn.cf.entity.SupplierEconomicsOrderReportForms;
import cn.cf.model.SysExcelStore;
import cn.cf.task.schedule.ScheduledFutureMap;
import cn.cf.util.DateUtil;

public class EconProdBussRunnable implements Runnable {
	
	
	
	private String name;

	private String fileName;
	
	private String uuid;

	private SysExcelStoreExtDto storeDtoTemp;

	private SysExcelStoreExtDao storeDao;
	
	public EconProdBussRunnable() {

	}
	
	public EconProdBussRunnable(String name,String uuid) {

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

		this.storeDao = (SysExcelStoreExtDao) BeanUtils.getBean("sysExcelStoreExtDao");
		MongoTemplate mongoTemplate = (MongoTemplate) BeanUtils.getBean("mongoTemplate");
		
		if (storeDao != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("isDeal", Constants.TWO);
			map.put("methodName", "exportEconProdBussRF_"+StringUtils.defaultIfEmpty(this.uuid, ""));
			// 查询存在要导出的任务
			List<SysExcelStoreExtDto> storelist = storeDao.getFirstTimeExcelStore(map);
			if (storelist != null && storelist.size() > Constants.ZERO) {
				for (SysExcelStoreExtDto storeDto : storelist) {
					this.storeDtoTemp = storeDto;
					this.fileName = "金融中心-数据管理-供应商金融产品交易 -" + storeDto.getAccountName() + "-"
							+DateUtil.formatYearMonthDayHMS(new Date());
					if (CommonUtil.isNotEmpty(storeDto.getParams())) {
						ReportFormsDataTypeParams params = JSON.parseObject(storeDto.getParams(),
								ReportFormsDataTypeParams.class);
						if (mongoTemplate != null) {

							Query query = new Query();
							query.addCriteria(Criteria.where("year").is(params.getYear()).and("bankPk").is(params.getBankPk()));
							List<SupplierEconomicsOrderReportForms> list = mongoTemplate.find(query,
									SupplierEconomicsOrderReportForms.class);
							//隐藏店铺名称
							if (list.size()>0) {
					            for (SupplierEconomicsOrderReportForms s : list) {
					                if (!CommonUtil.isExistAuthName(storeDto.getAccountPk(), ColAuthConstants.EM_REPORT_PROBUSS_COL_STORENAME)) {
					                    s.setStoreName(CommonUtil.hideCompanyName(s.getStoreName()));
					                }
					            }
					        }
							
																				
							String ossPath = "";
							if (list != null && list.size() > Constants.ZERO) {
								ExportUtil<SupplierEconomicsOrderReportForms> exportUtil = new ExportUtil<SupplierEconomicsOrderReportForms>();
								String path = exportUtil.exportDynamicUtil("econProdutBussinessRF", this.fileName, list);
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

	public static String econProdBussParam(ReportFormsDataTypeParams params) {
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
