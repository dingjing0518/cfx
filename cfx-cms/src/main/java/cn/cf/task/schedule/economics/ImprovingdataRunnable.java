package cn.cf.task.schedule.economics;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

import cn.cf.property.PropertyConfig;
import org.activiti.engine.impl.util.json.JSONArray;
import org.activiti.engine.impl.util.json.JSONObject;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import cn.cf.common.ColAuthConstants;
import cn.cf.common.Constants;
import cn.cf.common.ExportDoJsonParams;
import cn.cf.common.ExportUtil;
import cn.cf.common.utils.BeanUtils;
import cn.cf.common.utils.CommonUtil;
import cn.cf.common.utils.OSSUtils;
import cn.cf.common.utils.ZipUtils;
import cn.cf.dao.B2bEconomicsCustomerExtDao;
import cn.cf.dao.SysExcelStoreExtDao;
import cn.cf.dto.B2bEconomicsCustomerExtDto;
import cn.cf.dto.SysExcelStoreExtDto;
import cn.cf.entity.B2bEconomicsImprovingdataEntity;
import cn.cf.entity.CustomerDataTypeParams;
import cn.cf.json.JsonUtils;
import cn.cf.model.SysExcelStore;
import cn.cf.task.schedule.ScheduledFutureMap;
import cn.cf.util.DateUtil;

public class ImprovingdataRunnable implements Runnable {

	private String name;

	private String fileName;

	private String uuid;

	private String accountPk;
	private SysExcelStoreExtDao storeDao;
	private SysExcelStoreExtDto storeDtoTemp;

	public ImprovingdataRunnable() {

	}

	public ImprovingdataRunnable(String name, String uuid) {
		this.name = name;
		this.uuid = uuid;

	}

	@Override
	public void run() {
		// 上传数据
		ScheduledFuture future = null;
		if (CommonUtil.isNotEmpty(this.name)) {
			future = ScheduledFutureMap.map.get(this.name);
		}
		try {
			upLoadFile();
		} catch (Exception e) {
			ExportDoJsonParams.updateErrorExcelStoreStatus(this.storeDao, this.storeDtoTemp, e.getMessage());
		} finally {
			ScheduledFutureMap.stopFuture(future, this.name);
		}
	}

	// 上传到OSS
	public void upLoadFile() throws Exception {
		B2bEconomicsCustomerExtDao customerExtDao = (B2bEconomicsCustomerExtDao) BeanUtils
				.getBean("b2bEconomicsCustomerExtDao");
		this.storeDao = (SysExcelStoreExtDao) BeanUtils.getBean("sysExcelStoreExtDao");
		if (storeDao != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("isDeal", Constants.TWO);
			map.put("methodName", "exportImprovingdata_" + StringUtils.defaultIfEmpty(this.uuid, ""));

			// 查询存在要导出的任务
			List<SysExcelStoreExtDto> list = storeDao.getFirstTimeExcelStore(map);
			if (list != null && list.size() > Constants.ZERO) {
				for (SysExcelStoreExtDto storeDto : list) {
					this.storeDtoTemp = storeDto;
					if (CommonUtil.isNotEmpty(storeDto.getParams())) {
						CustomerDataTypeParams params = JSON.parseObject(storeDto.getParams(),
								CustomerDataTypeParams.class);
						this.fileName = "金融中心-金融管理-申请客户管理-" + storeDto.getAccountName() + "-"
								+ DateUtil.formatYearMonthDayHMS(new Date());
						this.accountPk = storeDto.getAccountPk();
						if (customerExtDao != null) {
							// 设置查询参数
							Map<String, Object> orderMap = customerParams(params, storeDto);
							// 设置权限
							setColCustomerParams(orderMap);
							List<B2bEconomicsCustomerExtDto> dtos = customerExtDao.getExtImpInfoByEconomCustPk(orderMap);
							String ossPath = "";
							String excelName = "金融中心-金融管理-申请客户管理-" + storeDto.getAccountName() + "-"
									+ DateUtil.formatYearMonthDayHMS(new Date());
							ExportUtil<B2bEconomicsImprovingdataEntity> exportUtil = new ExportUtil<B2bEconomicsImprovingdataEntity>();
							String path = "";
							 int counts = dtos.size();
							if (params.getFileType().equals("CSV")) {
								if (counts > Constants.EXCEL_NUMBER_TENHOUSAND) {
									double numbers = Math.floor(counts / Constants.EXCEL_NUMBER_TENHOUSAND);// 获取分页查询次数
									orderMap.put("limit", Constants.EXCEL_NUMBER_TENHOUSAND);
									List<File> fileList = new ArrayList<>();
									for (int i = Constants.ZERO; i < numbers + Constants.ONE; i++) { //
										int start = Constants.ZERO;
										if (i != Constants.ZERO) {
											start = ExportDoJsonParams.indexPageNumbers(i);
										}
										orderMap.put("start", start);
										List<B2bEconomicsCustomerExtDto> lists = customerExtDao.getExtImpInfoByEconomCustPk(orderMap);
										orderMap.remove("start");
										if (lists != null && lists.size() > Constants.ZERO) {
											List<B2bEconomicsImprovingdataEntity> improvingdataExtDtos = new ArrayList<B2bEconomicsImprovingdataEntity>();
											// 主要设备名称和月份
											getDtoJson(orderMap.get("insertTime").toString(),map, improvingdataExtDtos, lists);
											String excelNames = "金融中心-金融管理-申请客户管理-" + storeDto.getAccountName() + "-"
													+ DateUtil.formatYearMonthDayHMS(new Date())+"-"+(i+1);
											path = exportUtil.exportCSVDynamicUtil("econImprovingdata", excelNames, improvingdataExtDtos);
											File file = new File(path);
											fileList.add(file);
										}
									}
									ossPath = OSSUtils.ossUploadXls(new File(ZipUtils.fileToZip(fileList, this.fileName)), Constants.FILE);
								}else{
									List<B2bEconomicsImprovingdataEntity> improvingdataExtDtos = new ArrayList<B2bEconomicsImprovingdataEntity>();
									if (dtos != null&&dtos.size()>0) {
										// 主要设备名称和月份
										getDtoJson(orderMap.get("insertTime").toString(),map, improvingdataExtDtos, dtos);
									} 
									path = exportUtil.exportCSVDynamicUtil("econImprovingdata", excelName,improvingdataExtDtos);
									File file = new File(path);
									// 上传到OSS
									ossPath = OSSUtils.ossUploadXls(file, Constants.EIGHT);
								}
							} else if (params.getFileType().equals("XLS")) {
								if (counts > Constants.EXCEL_NUMBER_TENHOUSAND) {
									double numbers = Math.floor(counts / Constants.EXCEL_NUMBER_TENHOUSAND);// 获取分页查询次数
									orderMap.put("limit", Constants.EXCEL_NUMBER_TENHOUSAND);
									List<File> fileList = new ArrayList<>();
									for (int i = Constants.ZERO; i < numbers + Constants.ONE; i++) {
										int start = Constants.ZERO;
										if (i != Constants.ZERO) {
											start = ExportDoJsonParams.indexPageNumbers(i);
										}
										orderMap.put("start", start);
										List<B2bEconomicsCustomerExtDto> lists =  customerExtDao.getExtImpInfoByEconomCustPk(orderMap);
										orderMap.remove("start");
										if (lists != null && lists.size() > Constants.ZERO) {
											List<B2bEconomicsImprovingdataEntity> improvingdataExtDtos = new ArrayList<B2bEconomicsImprovingdataEntity>();
											// 主要设备名称和月份
											getDtoJson(orderMap.get("insertTime").toString(),map, improvingdataExtDtos, lists);
											String excelNames = "金融中心-金融管理-申请客户管理-" + storeDto.getAccountName() + "-"
													+ DateUtil.formatYearMonthDayHMS(new Date())+"-"+(i+1);
											path = exportUtil.exportDynamicUtil("econImprovingdata", excelNames, improvingdataExtDtos);
											File file = new File(path);
											fileList.add(file);
										}
									}
									ossPath = OSSUtils.ossUploadXls(new File(ZipUtils.fileToZip(fileList, this.fileName)), Constants.FILE);
								}else{
									List<B2bEconomicsImprovingdataEntity> improvingdataExtDtos = new ArrayList<B2bEconomicsImprovingdataEntity>();
									if (dtos != null&&dtos.size()>0) {
										// 主要设备名称和月份
										getDtoJson(orderMap.get("insertTime").toString(),map, improvingdataExtDtos, dtos);
									} 
									path = exportUtil.exportDynamicUtil("econImprovingdata", excelName,
											improvingdataExtDtos);
									File file = new File(path);
									// 上传到OSS
									ossPath = OSSUtils.ossUploadXls(file, Constants.FIVE);
								}
								
							} else if (params.getFileType().equals("TXT")) {
								List<B2bEconomicsImprovingdataEntity> improvingdataExtDtos = new ArrayList<B2bEconomicsImprovingdataEntity>();
								if (dtos != null&&dtos.size()>0) {
									// 主要设备名称和月份
									getDtoJson(orderMap.get("insertTime").toString(),map, improvingdataExtDtos, dtos);
								} 
								path = exportImprovingTXT(improvingdataExtDtos, excelName);
								File file = new File(path);
								// 上传到OSS
								ossPath = OSSUtils.ossUploadXls(file, Constants.TWO);
							}
							// 更新订单导出状态
							ExportDoJsonParams.updateExcelStoreStatus(storeDto, storeDao, ossPath);
						}
					}
				}
			}

		}
	}

	private String exportImprovingTXT(List<B2bEconomicsImprovingdataEntity> improvingdataExtDtos, String excelName) {
		String line = System.getProperty("line.separator");
		String tempPath = PropertyConfig.getProperty("FILE_PATH");
		File fileurl = new File(tempPath);
		if (!fileurl.exists() && !fileurl.isDirectory()) {
			fileurl.mkdir();
		}
		String fileName = excelName + ".txt";
		String destFileNamePath = tempPath + "/" + fileName;// 生成文件路径
		try {
			File writename = new File(destFileNamePath); // 相对路径，如果没有则要建立一个新的output。txt文件
			writename.createNewFile(); // 创建新文件
			OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(writename), "UTF-8");
			BufferedWriter out = new BufferedWriter(fw);
			out.write("企业名称" + " " + improvingdataExtDtos.get(0).getCompanyName()+"\r\n");
			if(improvingdataExtDtos.get(0).getCompanyPk()!=null){
				out.write("企业pk" + " " + improvingdataExtDtos.get(0).getCompanyPk()+"\r\n");	
			}else{
				out.write("企业pk" +"\r\n");
			}
			out.write("企业成立年限" + " " + improvingdataExtDtos.get(0).getEstablishingTime()+"\r\n");
			out.write("上一年度纳税销售金额" + " " + improvingdataExtDtos.get(0).getLastTaxSales()+"\r\n");
			out.write("企业融资纳税销售比" + " " + improvingdataExtDtos.get(0).getFinanAmountTaxSales()+"\r\n");
			out.write("\r\n");

			out.write("企业与金融机构合作时间" + " " + improvingdataExtDtos.get(0).getEcomCooperateTime()+"\r\n");
			if (improvingdataExtDtos.get(0).getDeviceStatus()!=null) {
				out.write("参与评分的设备名称" + " " + improvingdataExtDtos.get(0).getDeviceStatus()+"\r\n");
			}else{
				out.write("参与评分的设备名称" +"\r\n");
			}
			
			if (improvingdataExtDtos.get(0).getRawPurchaseAmount()!=null) {
				out.write("原料年采购额" + " " + improvingdataExtDtos.get(0).getRawPurchaseAmount()+"\r\n");
			}else{
				out.write("原料年采购额"+"\r\n");
			}
			out.write("企业在平台上一年度累计交易金额" + " " + improvingdataExtDtos.get(0).getTransAmountOnline()+"\r\n");
			out.write("原料线上采购额较总采购额占比" + " " + improvingdataExtDtos.get(0).getTransOnlineRawPurchPer()+"\r\n");
			out.write("\r\n");

			out.write("企业在平台上一年度交易频次" + " " + improvingdataExtDtos.get(0).getUseMonthsNum()+"\r\n");
			out.write("企业在平台上一年度金融产品使用频次" + " " + improvingdataExtDtos.get(0).getEconUseMonthsNum()+"\r\n");
			out.write("近12个月累计金融产品提用金额" + " " + improvingdataExtDtos.get(0).getEconUseMonthsAmount()+"\r\n");
			out.write("关联企业对外担保与纳税销售比" + " " + improvingdataExtDtos.get(0).getReleGuaraTaxSalesPer()+"\r\n");
			out.write("企业原料采购额环比稳定性" + " " + improvingdataExtDtos.get(0).getRawPurchaseIncrePer()+"\r\n");
			out.write("\r\n");

			out.write("关联企业名下负债与纳税销售比" + " " + improvingdataExtDtos.get(0).getReleFinanAmountTaxSales()+"\r\n");
			out.write("企业银行流水占销售比" + " " + improvingdataExtDtos.get(0).getAnnualizTaxSalesPer()+"\r\n");
			out.write("企业近期纳税销售年化值" + " " + improvingdataExtDtos.get(0).getAnnualizTaxAmount()+"\r\n");
			if(improvingdataExtDtos.get(0).getRawBrand()!=null){
				out.write("原料供应商名称" + " " + improvingdataExtDtos.get(0).getRawBrand()+"\r\n");
			}else{
				out.write("原料供应商名称"+"\r\n" );
			}
			out.write("原料近期采购年化值" + " " + improvingdataExtDtos.get(0).getRawAnnualizAmount()+"\r\n");
			out.write("\r\n");

			out.write("生产经营厂房权属性质及面积" + " " + improvingdataExtDtos.get(0).getWorkshopAllSquare()+"\r\n");
			if (improvingdataExtDtos.get(0).getFlowOfProductionName()!=null) {
				out.write("企业生产流程" + " " + improvingdataExtDtos.get(0).getFlowOfProductionName()+"\r\n");
			}else {
				out.write("企业生产流程"+"\r\n");
			}
			if(improvingdataExtDtos.get(0).getManagementModelName()!=null){
				out.write("企业商业经营模式" + " " + improvingdataExtDtos.get(0).getManagementModelName()+"\r\n");
			}else{
				out.write("企业商业经营模式"+"\r\n");
			}
			out.write("企业担保余额与纳税销售比值" + " " + improvingdataExtDtos.get(0).getGuaraTaxSalesPer()+"\r\n");
			out.write("主要采购原料纤度" + " " + improvingdataExtDtos.get(0).getPurcherVariety()+"\r\n");
			out.write("\r\n");

			out.write("企业股权结构及高管稳定性" + " " + improvingdataExtDtos.get(0).getShareChangeNum()+"\r\n");
			out.write("近三年行政处罚及被执行次数" + " " + improvingdataExtDtos.get(0).getEnforcedNum()+"\r\n");
			out.write("企业还款付息逾期次数" + " " + improvingdataExtDtos.get(0).getZxEnforcedNum()+"\r\n");
			if (improvingdataExtDtos.get(0).getFinanInstitution()!=null) {
				out.write("合作金融机构数量" + " " +improvingdataExtDtos.get(0).getFinanInstitution().toString()+"\r\n");
			}else {
				out.write("合作金融机构数量"+"\r\n");
			}
			out.write("企业与供应商合作年限" + " " + improvingdataExtDtos.get(0).getSupplierCooperateTime()+"\r\n");
			out.write("\r\n");

			out.write("企业在平台上一年度合作供应商数量" + " " + improvingdataExtDtos.get(0).getBusinessShopNum()+"\r\n");
			out.write("企业在平台上一年度金融产品还款逾期次数" + " " + improvingdataExtDtos.get(0).getEconOverdueNum()+"\r\n");
			out.write("企业经营地点稳定性" + " " + improvingdataExtDtos.get(0).getManageStable()+"\r\n");
			out.write("企业主要生产能耗稳定性情况" + " " + improvingdataExtDtos.get(0).getConsumStable()+"\r\n");
			out.write("企业主要能耗与行业平均能耗对比情况" + " " + improvingdataExtDtos.get(0).getAverageIndustryConsumPer()+"\r\n");
			out.write("\r\n");

			out.write("上游客户稳定性" + " " + improvingdataExtDtos.get(0).getUpstreamCustomerStable()+"\r\n");
			out.write("下游客户稳定性" + " " + improvingdataExtDtos.get(0).getDownstreamCustomerStable()+"\r\n");
			out.write("上游客户交易量稳定性" + " " + improvingdataExtDtos.get(0).getUpstreamVipCustStable()+"\r\n");
			out.write("下游客户交易量稳定性" + " " + improvingdataExtDtos.get(0).getDownstreamCustSalesStable()+"\r\n");
			if (improvingdataExtDtos.get(0).getTaxLevelName()!=null) {
				out.write("上一年度国税系统纳税评级" + " " +improvingdataExtDtos.get(0).getTaxLevelName()+"\r\n");
			}else {
				out.write("上一年度国税系统纳税评级"+"\r\n");
			}
			out.flush(); // 把缓存区内容压入文件
			out.close(); // 最后记得关闭文件
		} catch (Exception e) {
			e.printStackTrace();
		}
		return destFileNamePath;
	}

	private void getDtoJson(String  insert ,Map<String, Object> map, List<B2bEconomicsImprovingdataEntity> improvingdataExtDtos,
			List<B2bEconomicsCustomerExtDto> dtos) {
		Calendar date = Calendar.getInstance();
		Integer year = Integer.parseInt(String.valueOf(date.get(Calendar.YEAR))) - 1;
		for (B2bEconomicsCustomerExtDto dto : dtos) {
			B2bEconomicsImprovingdataEntity  improvingdataDto  = new B2bEconomicsImprovingdataEntity();
			improvingdataDto.setCompanyPk(dto.getCompanyPk());
			improvingdataDto.setCompanyName(dto.getCompanyName());
			if (dto.getImprovingdataInfo()!=null && !dto.getImprovingdataInfo().equals("")) {
						improvingdataDto = JsonUtils.toBean(dto.getImprovingdataInfo(),B2bEconomicsImprovingdataEntity.class); 
						improvingdataDto.setCompanyName(dto.getCompanyName());
					if (improvingdataDto.getDeviceStatus() != null && !improvingdataDto.getDeviceStatus().equals("")) {
						Gson gson = new Gson();
						Map<String, Object> deviceStatusMap = new HashMap<String, Object>();
						deviceStatusMap = gson.fromJson(improvingdataDto.getDeviceStatus(), map.getClass());
						for (Map.Entry<String, Object> entry : deviceStatusMap.entrySet()) {
							System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
						}
						String deviceStatusStr =  deviceStatusMap.get("deviceBrand").toString();
						improvingdataDto.setDeviceStatus(deviceStatusStr);
					}
					if (improvingdataDto.getRawPurchaseAmount() != null && !improvingdataDto.getRawPurchaseAmount().equals("")) {
						String rawPurchaseAmount = "0";
						JSONArray jsonarry = new JSONArray(improvingdataDto.getRawPurchaseAmount());
						// 遍历JSON数组[{"rawPurchaseYear":"2009","rawPurchaseAmount":"4564"}]
						if (jsonarry.length() > 0) {
							for (int i = 0; i < jsonarry.length(); i++) {
								// 获得json数据
								JSONObject jsonObject = jsonarry.getJSONObject(i);
								// 根据key建取值
								if (year.toString().equals(jsonObject.getString("rawPurchaseYear"))) {
									rawPurchaseAmount = jsonObject.getString("rawPurchaseAmount");
								}
							}
						}
						improvingdataDto.setRawPurchaseAmount(rawPurchaseAmount);
					}
					
						if (improvingdataDto.getFlowOfProduction()!=null) {
							switch (improvingdataDto.getFlowOfProduction()) {
							case Constants.ONE:
								improvingdataDto.setFlowOfProductionName("一步");
								break;
							case Constants.TWO:
								improvingdataDto.setFlowOfProductionName("两步");
								break;
							case Constants.THREE:
								improvingdataDto.setFlowOfProductionName("三步及以上");
								break;
							default:
								break;
							}
						}
						
						if (improvingdataDto.getManagementModel()!=null) {
							switch (improvingdataDto.getManagementModel()) {
							case Constants.ONE:
								improvingdataDto.setManagementModelName("代加工");
								break;
							case Constants.TWO:
								improvingdataDto.setManagementModelName("自产自销");
								break;
							case Constants.THREE:
								improvingdataDto.setManagementModelName("自产自销+外发加工");
								break;
							case Constants.FOUR:
								improvingdataDto.setManagementModelName("自产自销+代加工");
								break;
							default:
								break;
							}
						}
						
						if (improvingdataDto.getTaxLevel()!=null) {
							switch (improvingdataDto.getTaxLevel()) {
							case Constants.ONE:
								improvingdataDto.setTaxLevelName("A");
								break;
							case Constants.TWO:
								improvingdataDto.setTaxLevelName("B");
								break;
							case Constants.THREE:
								improvingdataDto.setTaxLevelName("C");
								break;
							case Constants.FOUR:
								improvingdataDto.setTaxLevelName("D");
								break;
							default:
								break;
							}
						}
					improvingdataExtDtos.add(improvingdataDto);
				}
			}
			
	}

	private void setColCustomerParams(Map<String, Object> orderMap) {

		if (!CommonUtil.isExistAuthName(this.accountPk, ColAuthConstants.EM_CUST_APPLY_COL_COM_NAME)) {
			orderMap.put("colCompanyName", ColAuthConstants.EM_CUST_APPLY_COL_COM_NAME);
		}
		if (!CommonUtil.isExistAuthName(this.accountPk, ColAuthConstants.EM_CUST_APPLY_COL_CONTACTS)) {
			orderMap.put("colContacts", ColAuthConstants.EM_CUST_APPLY_COL_CONTACTS);
		}
		if (!CommonUtil.isExistAuthName(this.accountPk, ColAuthConstants.EM_CUST_APPLY_COL_CONTACTSTEL)) {
			orderMap.put("colContactsTel", ColAuthConstants.EM_CUST_APPLY_COL_CONTACTSTEL);
		}
	}

	private Map<String, Object> customerParams(CustomerDataTypeParams params, SysExcelStoreExtDto storeDto) {
		Map<String, Object> map = new HashMap<String, Object>();
		String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(storeDto.getInsertTime());
		if (CommonUtil.isNotEmpty(params.getEconomCustPk())) {
			map.put("econCustmerPk", params.getEconomCustPk());
		}/*else{
			if (CommonUtil.isNotEmpty(params.getCompanyName())) {
				map.put("companyName", params.getEconomCustPk());
			}
			if (CommonUtil.isNotEmpty(params.getAssidirPk())) {
				map.put("assidirPk", params.getAssidirPk());
			}
		}
		if (CommonUtil.isNotEmpty(params.getUpdateStartTime())) {
			map.put("updateStartTime", params.getUpdateStartTime());
		}
		
		if (CommonUtil.isNotEmpty(params.getUpdateEndTime())) {
			map.put("updateEndTime", params.getUpdateEndTime());
		}
		
		if (CommonUtil.isNotEmpty(params.getInsertStartTime())) {
			map.put("insertStartTime", params.getInsertStartTime());
		}
		
		if (CommonUtil.isNotEmpty(params.getInsertEndTime())) {
			map.put("insertEndTime", params.getInsertEndTime());
		}
		if (CommonUtil.isNotEmpty(params.getContactsTel())) {
			map.put("contactsTel", params.getContactsTel());
		}*/
		map.put("insertTime", now);
		return map;
	}

	public static String ImprovingdataParam(CustomerDataTypeParams params, SysExcelStore store) {

		String paramStr = "";
		if (CommonUtil.isNotEmpty(params.getEconomCustPk())) {
			if (CommonUtil.isNotEmpty(params.getCompanyName())) {
			paramStr = paramStr + "公司名称：" + params.getCompanyName() + ";";
			} 
		}
		
//			paramStr = CommonUtil.getDateShow(paramStr,  params.getUpdateStartTime(), params.getUpdateEndTime(),  "处理时间：");
//			paramStr = CommonUtil.getDateShow(paramStr,  params.getInsertStartTime(), params.getInsertEndTime(),  "提交时间：");
//		if (CommonUtil.isNotEmpty(params.getContactsTel())) {
//			paramStr = paramStr + "手机号码：" + params.getContactsTel() + ";";
//		}
		if (CommonUtil.isNotEmpty(params.getFileType())) {
			paramStr = paramStr + "文件类型：" + params.getFileType() + ";";
		}
		return paramStr;

	}

}
