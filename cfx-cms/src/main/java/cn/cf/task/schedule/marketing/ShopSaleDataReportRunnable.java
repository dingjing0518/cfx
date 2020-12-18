package cn.cf.task.schedule.marketing;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

import cn.cf.common.ExportDoJsonParams;
import cn.cf.property.PropertyConfig;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.alibaba.fastjson.JSON;

import cn.cf.common.Constants;
import cn.cf.common.utils.BeanUtils;
import cn.cf.common.utils.CommonUtil;
import cn.cf.common.utils.OSSUtils;
import cn.cf.dao.SysExcelStoreExtDao;
import cn.cf.dto.SysExcelStoreExtDto;
import cn.cf.entity.ReportFormsDataTypeParams;
import cn.cf.entity.SupplierSaleDataReportForms;
import cn.cf.entity.SupplierSaleTotalData;
import cn.cf.model.SysExcelStore;
import cn.cf.task.schedule.ScheduledFutureMap;
import cn.cf.util.DateUtil;
import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.Configuration;
import net.sf.jxls.transformer.XLSTransformer;

public class ShopSaleDataReportRunnable implements Runnable {

	private String name;

	private String uuid;
	private SysExcelStoreExtDto storeDtoTemp;
	private SysExcelStoreExtDao storeDao;
	public ShopSaleDataReportRunnable() {

	}
	
	public ShopSaleDataReportRunnable(String name,String uuid) {

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
			map.put("methodName", "exportShopSaleDataReport_"+StringUtils.defaultIfEmpty(this.uuid, ""));
			// 查询存在要导出的任务
			List<SysExcelStoreExtDto> storelist = storeDao.getFirstTimeExcelStore(map);
			if (storelist != null && storelist.size() > Constants.ZERO) {
				for (SysExcelStoreExtDto storeDto : storelist) {
					this.storeDtoTemp = storeDto;
					if (CommonUtil.isNotEmpty(storeDto.getParams())) {
						ReportFormsDataTypeParams params = JSON.parseObject(storeDto.getParams(),
								ReportFormsDataTypeParams.class);
						if (mongoTemplate != null) {

							String ossPath = searchSupplierSaleData(params, mongoTemplate, storeDto);
							File file = new File(ossPath);
							// 上传到OSS
							ossPath = OSSUtils.ossUploadXls(file, Constants.FIVE);
							// 更新订单导出状态
							ExportDoJsonParams.updateExcelStoreStatus(storeDto,storeDao, ossPath);
						}
					}
				}
			}
		}

	}
	private String searchSupplierSaleData(ReportFormsDataTypeParams params, MongoTemplate mongoTemplate,
			SysExcelStoreExtDto storeDto) {
		Map<String, Object> map = new HashMap<>();
		Query query = new Query();
		query.addCriteria(Criteria.where("year").is(params.getYear()));
		query.with(new Sort(new Order(Direction.ASC, "month")));
		List<SupplierSaleDataReportForms> list = mongoTemplate.find(query, SupplierSaleDataReportForms.class);

		if (list != null && list.size() > 0) {
			// 拼接合计
			SupplierSaleTotalData totalData = new SupplierSaleTotalData();
			setTotalData(list, totalData);
			map.put("list", list);
			map.put("totalData", totalData);
		} else {
			// 如果没有查到选择的年份数据，则默认为零
			String[] nameArr = Constants.ECONOMICS_CUSTOMER_TIME_LIST;
			List<SupplierSaleDataReportForms> listEmpty = new ArrayList<>();
			for (int i = 3; i < nameArr.length; i++) {
				SupplierSaleDataReportForms dataReportForms = new SupplierSaleDataReportForms();
				String name = nameArr[i];
				dataReportForms.setMonthName(name);
				listEmpty.add(dataReportForms);
			}
			SupplierSaleTotalData emptyData = new SupplierSaleTotalData();
			map.put("list", listEmpty);
			map.put("totalData", emptyData);
		}

		return exportExcelReport("shopSupplierDataRF", params.getYear(),
				(List<SupplierSaleDataReportForms>) map.get("list"), (SupplierSaleTotalData) map.get("totalData"),
				storeDto);

	}

	private String exportExcelReport(String name, String years, List<SupplierSaleDataReportForms> list,
			SupplierSaleTotalData totalData, SysExcelStoreExtDto storeDto) {

		URL url = this.getClass().getClassLoader().getResource("template");
		String templateFileNameFilePath = url.getPath() + "/" + name + ".xls"; // 模板路径
		String tempPath = PropertyConfig.getProperty("FILE_PATH");

		File file = new File(tempPath);
		if (!file.exists() && !file.isDirectory()) {
			file.mkdir();
		}

		String fileName = "金融中心-数据管理-平台销售数据 -" + storeDto.getAccountName() + "-"
				+ DateUtil.formatYearMonthDayHMS(new Date());
		String destFileNamePath = tempPath + "/" + fileName + ".xls";// 生成文件路径
		Map<String, Object> beans = new HashMap<String, Object>();
		beans.put("dto", list);
		beans.put("dates", new Date());
		beans.put("totalData", totalData);
		beans.put("year", years);

		Configuration config = new Configuration();
		XLSTransformer transformer = new XLSTransformer(config);
		FileInputStream is = null;
		try {
			transformer.transformXLS(templateFileNameFilePath, beans, destFileNamePath);
			is = new FileInputStream(destFileNamePath);
			// 3.通过response获取ServletOutputStream对象(out)
		} catch (ParsePropertyException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return destFileNamePath;
	}

	private void setTotalData(List<SupplierSaleDataReportForms> list, SupplierSaleTotalData totalData) {

		int countsAddup = 0;
		BigDecimal amountAddup = new BigDecimal(0.0);
		double weightAddup = 0d;

		int countsSH = 0;
		BigDecimal amountSH = new BigDecimal(0.0);
		double weightSH = 0d;

		int countsXFM = 0;
		BigDecimal amountXFM = new BigDecimal(0.0);
		double weightXFM = 0d;

		int countsOther = 0;
		BigDecimal amountOther = new BigDecimal(0.0);
		double weightOther = 0d;
		int monthIndex = 3;
		for (SupplierSaleDataReportForms data : list) {

			data.setMonthName(Constants.ECONOMICS_CUSTOMER_TIME_LIST[monthIndex]);
			monthIndex++;

			countsAddup += data.getCountsAddUp();
			amountAddup = amountAddup.add(data.getAmountAddUp());
			weightAddup += data.getWeightAddUp();

			countsSH += data.getCountsSH();
			amountSH = amountSH.add(data.getAmountSH());
			weightSH += data.getWeightSH();

			countsXFM += data.getCountsXFM();
			amountXFM = amountXFM.add(data.getAmountXFM());
			weightXFM += data.getWeightXFM();

			countsOther += data.getCountsOther();
			amountOther = amountOther.add(data.getAmountOther());
			weightOther += data.getWeightOther();

			data.setAmountSH(data.getAmountSH().setScale(2, BigDecimal.ROUND_HALF_UP));
			data.setAmountXFM(data.getAmountXFM().setScale(2, BigDecimal.ROUND_HALF_UP));
			data.setAmountAddUp(data.getAmountAddUp().setScale(2, BigDecimal.ROUND_HALF_UP));
			data.setAmountOther(data.getAmountOther().setScale(2, BigDecimal.ROUND_HALF_UP));
			// list.add(data);
		}

		totalData.setAmountAddup(amountAddup.setScale(2, BigDecimal.ROUND_HALF_UP));
		totalData.setCountsAddup(countsAddup);
		totalData.setWeightAddup(CommonUtil.formatDoubleFour(weightAddup));

		totalData.setAmountSH(amountSH.setScale(2, BigDecimal.ROUND_HALF_UP));
		totalData.setCountsSH(countsSH);
		totalData.setWeightSH(CommonUtil.formatDoubleFour(weightSH));

		totalData.setAmountXFM(amountXFM.setScale(2, BigDecimal.ROUND_HALF_UP));
		totalData.setCountsXFM(countsXFM);
		totalData.setWeightXFM(CommonUtil.formatDoubleFour(weightXFM));

		totalData.setAmountOther(amountOther.setScale(2, BigDecimal.ROUND_HALF_UP));
		totalData.setCountsOther(countsOther);
		totalData.setWeightOther(CommonUtil.formatDoubleFour(weightOther));
	}

	public static String shopSaleDataReportParam(ReportFormsDataTypeParams params, SysExcelStore store) {
		String paramStr = "";
		if (CommonUtil.isNotEmpty(params.getYear())) {
			paramStr = paramStr + "年份：" + params.getYear();
		}
		return paramStr;
	}

}
