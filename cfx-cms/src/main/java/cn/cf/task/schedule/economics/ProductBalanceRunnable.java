package cn.cf.task.schedule.economics;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

import cn.cf.common.ExportDoJsonParams;
import cn.cf.property.PropertyConfig;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import cn.cf.dao.B2bEconomicsBankExtDao;
import cn.cf.dao.SysExcelStoreExtDao;
import cn.cf.dto.B2bEconomicsBankDto;
import cn.cf.dto.SysExcelStoreExtDto;
import cn.cf.entity.BankBalanceEntity;
import cn.cf.entity.EconProductbalanceReport;
import cn.cf.entity.ReportFormsDataTypeParams;
import cn.cf.json.JsonUtils;
import cn.cf.task.schedule.ScheduledFutureMap;
import cn.cf.util.ArithUtil;
import cn.cf.util.DateUtil;

public class ProductBalanceRunnable implements Runnable {
	Logger log = LoggerFactory.getLogger(ProductBalanceRunnable.class);
	private String name;
	private String uuid;
	private SysExcelStoreExtDto storeDtoTemp;

	private SysExcelStoreExtDao storeDao;
	public ProductBalanceRunnable() {

	}
	
	public ProductBalanceRunnable(String name,String uuid) {

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
			ExportDoJsonParams.updateErrorExcelStoreStatus(this.storeDao,this.storeDtoTemp,ExportDoJsonParams.getErrorInfoFromException(e));
			log.error("ProductBalanceRunnable+++++++++++++++++++",e);
        } finally {
            ScheduledFutureMap.stopFuture(future,this.name);
        }
    }

	private void upLoadFile() throws Exception {

		this.storeDao = (SysExcelStoreExtDao) BeanUtils.getBean("sysExcelStoreExtDao");
		B2bEconomicsBankExtDao  b2bEconomicsBankExtDao = (B2bEconomicsBankExtDao) BeanUtils.getBean("b2bEconomicsBankExtDao");
		MongoTemplate mongoTemplate = (MongoTemplate) BeanUtils.getBean("mongoTemplate");
		if (storeDao != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("isDeal", Constants.TWO);
			map.put("methodName", "exportProductBalance_"+StringUtils.defaultIfEmpty(this.uuid, ""));
			// 查询存在要导出的任务
			List<SysExcelStoreExtDto> storelist = storeDao.getFirstTimeExcelStore(map);
			if (storelist != null && storelist.size() > Constants.ZERO) {
				for (SysExcelStoreExtDto storeDto : storelist) {
					this.storeDtoTemp = storeDto;
					if (CommonUtil.isNotEmpty(storeDto.getParams())) {
						ReportFormsDataTypeParams params = JSON.parseObject(storeDto.getParams(),
								ReportFormsDataTypeParams.class);
						if (mongoTemplate != null) {
							
							List<B2bEconomicsBankDto> list = b2bEconomicsBankExtDao.searchListOrderName();
					        // 1.生成Excel
					        String tempPath = PropertyConfig.getProperty("FILE_PATH");
					        String fileName = "金融中心-数据管理-金融产品使用余额统计表 -" + storeDto.getAccountName() + "-"
									+DateUtil.formatYearMonthDayHMS(new Date());					        
					        String destFileNamePath = tempPath + "/" + fileName+".xls";// 生成文件路径
					        // 1.生成Excel
					        XSSFWorkbook balanceExcel = createBalanceListExcel(mongoTemplate,list,params.getStartTime(),params.getEndTime());
					        FileInputStream is = null;
					        try{
					            // 输出成文件
					            File file = new File(fileName);
					            if(!file.exists() ) {
					            	// 生成的wb对象传输
					                FileOutputStream outputStream = new FileOutputStream(new File(destFileNamePath));
					                balanceExcel.write(outputStream);
					                outputStream.close();
					                //下载表格
					                is = new FileInputStream(destFileNamePath);
					                is.close();
					            }
					        }catch(Exception e){
								log.error("ProductBalanceRunnable_FileInputStream+++++++++++++++++++"+e.getMessage());
					            e.printStackTrace();
					        }
					        
							String ossPath = "";
							if (list != null && list.size() > Constants.ZERO) {
								File file = new File(destFileNamePath);
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
	
	
	
	private XSSFWorkbook createBalanceListExcel(MongoTemplate mongoTemplate, List<B2bEconomicsBankDto> list, String startTime, String endTime) {
		// 1.创建HSSFWorkbook，一个HSSFWorkbook对应一个Excel文件
		XSSFWorkbook wb = new XSSFWorkbook();
		// 2.在workbook中添加一个sheet,对应Excel文件中的sheet
		XSSFSheet sheet = wb.createSheet("sheet1");
		XSSFCellStyle style = wb.createCellStyle();
		// 填充色
		style.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style.setBorderTop(BorderStyle.THIN);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setAlignment(HorizontalAlignment.CENTER_SELECTION);

		XSSFFont font = wb.createFont();
		font.setBold(true);// 粗体显示
		style.setFont(font);
		// 3.设置表头，即每个列的列名

		// 3.1创建第一行
		XSSFRow row = sheet.createRow(0);
		// 此处创建一个序号列
		row.createCell(0).setCellStyle(style);
		row.getCell(0).setCellValue("日期");

		// 将列名写入
		int t = 1;
		for (int i = 0; i < list.size(); i++) {
			// 给列写入数据,创建单元格，写入数据
			row.createCell(t).setCellStyle(style);
			row.getCell(t).setCellValue(list.get(i).getBankName());
			row.createCell(t + 1).setCellValue("");
			row.getCell(t + 1).setCellStyle(style);
			row.createCell(t + 2).setCellValue("");
			row.getCell(t + 2).setCellStyle(style);
			row.createCell(t + 3).setCellValue("");
			row.getCell(t + 3).setCellStyle(style);
			sheet.addMergedRegion(new CellRangeAddress(0, 0, t, t + 3));
			t = t + 4;
		}
		// 3.2 创建第二行
		row = sheet.createRow(1);
		for (int i = 1; i < list.size() * 4; i = i + 4) {
			// 给列写入数据,创建单元格，写入数据
			row.createCell(i).setCellValue("化纤白条");
			row.getCell(i).setCellStyle(style);
			row.createCell(i + 1).setCellValue("");
			row.getCell(i + 1).setCellStyle(style);
			sheet.addMergedRegion(new CellRangeAddress(1,1, i, i + 1));
			
			row.createCell(i + 2).setCellValue("化纤贷");
			row.getCell(i + 2).setCellStyle(style);
			row.createCell(i + 3).setCellValue("");
			row.getCell(i + 3).setCellStyle(style);
			sheet.addMergedRegion(new CellRangeAddress(1, 1, i+2, i + 3));
		}
		
		// 3.3对银行名称，和日期合并居中（1：开始行 2：结束行 3：开始列 4：结束列）
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 0));
		List<EconProductbalanceReport> monongoList = getMonogoList(mongoTemplate,startTime, endTime);
		if (monongoList.size() > 0) {
			// 3.4 写入正式数据
			for (int i = 0; i < monongoList.size(); i++) {
				// 创建行
				row = sheet.createRow(i + 2);
				// 序号
				row.createCell(0).setCellValue(monongoList.get(i).getDate());
				Map<String, Object> map = new HashMap<String, Object>();
				for (BankBalanceEntity entity : monongoList.get(i).getBankList()) {
					map.put(entity.getBankPk() + "_bt", entity.getBtAmount());
					map.put(entity.getBankPk() + "_d", entity.getdAmount());
					map.put(entity.getBankPk() + "_btList", entity.getBtList());
					map.put(entity.getBankPk() + "_dList", entity.getdList());
				}
				int k = 1;
				for (int j = 0; j < list.size(); j++) {
					// 给列写入数据,创建单元格，写入数据
					row.createCell(k).setCellValue(map.get(list.get(j).getPk() + "_bt")==null?"0.00":map.get(list.get(j).getPk() + "_bt").toString());
					row.createCell(k + 1).setCellValue(map.get(list.get(j).getPk() + "_btList")==null?"":JsonUtils.convertToString(map.get(list.get(j).getPk() + "_btList")));
					row.createCell(k + 2).setCellValue(map.get(list.get(j).getPk() + "_d")==null?"0.00":map.get(list.get(j).getPk() + "_d").toString());
					row.createCell(k + 3).setCellValue(map.get(list.get(j).getPk() + "_dList")==null?"":JsonUtils.convertToString(map.get(list.get(j).getPk() + "_dList")));
					k = k + 4;
				}
			}
			// 3.5日均值
			row = sheet.createRow(monongoList.size() + 2);
			row.createCell(0).setCellValue("日均");
			Map<String, BigDecimal> mapSum = countSumAmount(monongoList);
			int day = countDays(startTime, endTime);
			int k = 1;
			for (int j = 0; j < list.size(); j++) {
				// 给列写入数据,创建单元格，写入数据

				if (mapSum != null && mapSum.get(list.get(j).getPk() + "_bt")!= null){
					row.createCell(k).setCellValue(ArithUtil.divBigDecimal(mapSum.get(list.get(j).getPk() + "_bt"), day)
							.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
				} else {
					row.createCell(k).setCellValue(0.00);
				}

				if (mapSum != null && mapSum.get(list.get(j).getPk() + "_d")!= null){
					row.createCell(k + 2).setCellValue(ArithUtil.divBigDecimal(mapSum.get(list.get(j).getPk() + "_d"), day)
							.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
				}  else {
					row.createCell(k + 2).setCellValue(0.00);
				}
				k = k + 4;
			}
		}

		return wb;
	}
	
	
	/**
	 * 求删选日期相差的天数
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	private int countDays(String start, String end) {
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		int day = 0;
		try {
			day = CommonUtil.getInterval(df.parse(start), df.parse(end));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (end.equals(CommonUtil.today())) {
			return day;
		}else{
			return day+1;
		}
		
	}

	/**
	 * 求所有天数的数值和
	 * 
	 * @param list
	 * @return
	 */
	private Map<String, BigDecimal> countSumAmount(List<EconProductbalanceReport> list) {
		Map<String, BigDecimal> map = new HashMap<String, BigDecimal>();
		for (EconProductbalanceReport report : list) {
			if (report.getBankList() != null && report.getBankList().size() > 0) {
				for (BankBalanceEntity entity : report.getBankList()) {

					if (map.containsKey(entity.getBankPk() + "_bt")) {
						map.put(entity.getBankPk() + "_bt",
								ArithUtil.addBigDecimal(entity.getBtAmount(), map.get(entity.getBankPk() + "_bt")));
					} else {
						map.put(entity.getBankPk() + "_bt", entity.getBtAmount());
					}

					if (map.containsKey(entity.getBankPk() + "_d")) {
						map.put(entity.getBankPk() + "_d",
								ArithUtil.addBigDecimal(entity.getdAmount(), map.get(entity.getBankPk() + "_d")));
					} else {
						map.put(entity.getBankPk() + "_d", entity.getdAmount());
					}
				}

			}
		}
		return map;
	}

	private List<EconProductbalanceReport> getMonogoList(MongoTemplate mongoTemplate, String startTime, String endTime) {
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("date").lte(endTime), Criteria.where("date").gte(startTime));
		Query query = new Query(criteria);
		List<EconProductbalanceReport> list = mongoTemplate.find(query.with(new Sort(new Order(Direction.ASC, "date"))),
				EconProductbalanceReport.class);
		return list;
	}

	public static String productBalanceParam(ReportFormsDataTypeParams params) {
		String paramStr = "";
		if (CommonUtil.isNotEmpty(params.getStartTime())) {
			paramStr = paramStr + "时间范围：" + params.getStartTime() ;
		}
		if (CommonUtil.isNotEmpty(params.getEndTime())) {
			paramStr = paramStr + "-" + params.getEndTime();
		} 
		return paramStr;
	}
}
