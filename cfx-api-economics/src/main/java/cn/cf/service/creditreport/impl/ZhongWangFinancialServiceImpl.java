package cn.cf.service.creditreport.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.cf.common.AESUtil;
import cn.cf.common.Base64Utils;
import cn.cf.common.RestCode;
import cn.cf.dao.B2bCompanyDao;
import cn.cf.dao.B2bCreditDaoEx;
import cn.cf.dao.B2bCreditInvoiceDaoEx;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bCreditDto;
import cn.cf.dto.B2bCreditInvoiceDto;
import cn.cf.entry.ZhongWangInfo;
import cn.cf.http.HttpHelper;
import cn.cf.model.B2bCredit;
import cn.cf.model.B2bCreditInvoice;
import cn.cf.property.PropertyConfig;
import cn.cf.service.creditreport.ZhongWangFinancialService;
import cn.cf.util.DateUtil;
import cn.cf.util.KeyUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


@Service
public class ZhongWangFinancialServiceImpl implements ZhongWangFinancialService {
	
	//private final Logger logger = LoggerFactory.getLogger(this.getClass());  
	
	//private static String key="d0beab0d3c0ebe13";//密钥
	//private static String taxNo = "91320594MA1MQC1T75";//平台税号
	//private static String URL = "http://211.157.177.35:50001/api/invoice/";//请求路径
	//private static String taxAuthorityCode = "13200000000";//主管税务机关代码
	//private static String taxAuthorityName = "国家税务总局江苏省税务局";//主管税务机关名称
	//private static String taxNature = "1";//纳税人性质
	
	
	@Autowired
	private B2bCreditDaoEx creditDaoEx;
	
	@Autowired
	private B2bCreditInvoiceDaoEx b2bCreditInvoiceDaoEx;
	
	@Autowired
	private B2bCompanyDao b2bCompanyDao;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public String syncCreditInvoice(String creditPk, String companyPk, int dataType, String startDate,String endDate) {
		String result = RestCode.CODE_0000.toJson();
		//参数不能为空验证
		if (creditPk.equals("")|| companyPk.equals("")||startDate.equals("")||endDate.equals("")) {
			return RestCode.CODE_A001.toJson();
		}
		//日期合法性验证
		if (!dateValid(startDate,endDate)) {
			return RestCode.CODE_S005.toJson();
		}
		B2bCreditDto creditDto = creditDaoEx.getByPk(creditPk);
		B2bCompanyDto companyDto = b2bCompanyDao.getByPk(companyPk);
		//非空验证
		if (null == creditDto) {
			return RestCode.CODE_A002.toJson();
		}
		//验证委托文件
		if (null == creditDto.getDelegateCertUrl() || "".equals(creditDto.getDelegateCertUrl())) {
			return RestCode.CODE_A010.toJson();
		}
		//验证纳税人性质
		if (null == creditDto.getTaxNature() ) {
			return RestCode.CODE_A011.toJson();
		}
		//验证主管税务机关代码
		if (null == creditDto.getTaxAuthorityCode() || "".equals(creditDto.getTaxAuthorityCode())) {
			return RestCode.CODE_A012.toJson();
		}
		//主管税务机关名称
		if (null == creditDto.getTaxAuthorityName() || "".equals(creditDto.getTaxAuthorityName())) {
			return RestCode.CODE_A013.toJson();
		}
		//非空验证
		if (null == companyDto ) {
			return RestCode.CODE_A004.toJson();
		}
		//组织机构代码验证
		if (null == companyDto.getOrganizationCode() || "".equals(companyDto.getOrganizationCode())) {
			return RestCode.CODE_A005.toJson();
		}
		//1：该公司还没有申请证书,进行申请证书操作
		if (null!=creditDto.getCertificateStatus() && creditDto.getCertificateStatus() == 1) {
			result = certificateAccess(creditPk, companyDto);
		}
		//2：该公司证书申请中，查询申请结果
		if (null!=creditDto.getCertificateStatus() && creditDto.getCertificateStatus() == 2) {
			int certificateState = certificateResult(companyDto);
			//2.1 申请通过
			if (certificateState == 5) {
				//更新b2b_credit certificateStatus 3
				B2bCredit model = new B2bCredit();
				model.setPk(creditPk);
				model.setCertificateStatus(3);
				creditDaoEx.update(model);
				//查数据
				result = invoiceCollection(companyPk, dataType, startDate, endDate, companyDto);
			}
			//2.2 申请失败
			if (certificateState == 9) {
				//更新b2b_credit certificateStatus 4
				B2bCredit model = new B2bCredit();
				model.setPk(creditPk);
				model.setCertificateStatus(4);
				creditDaoEx.update(model);
			}
			//2.3查询结果失败
			if (certificateState == -1) {
				result = RestCode.CODE_A009.toJson();
			}
			//2.3还在申请中
			if (certificateState !=5 && certificateState != 9 && certificateState!=-1) {
				result = RestCode.CODE_A006.toJson();
			}
		}
		//3：该公司已经申请成功，直接查数据
		if (null!=creditDto.getCertificateStatus() && creditDto.getCertificateStatus() == 3) {
			result= invoiceCollection(companyPk, dataType, startDate, endDate, companyDto);
		}
		//4：证书申请失败
		if (null!=creditDto.getCertificateStatus() && creditDto.getCertificateStatus() == 4) {
			result = RestCode.CODE_A008.toJson();
		}
		return result;
	}


	/** 查询数据
	 * @param companyPk  公司pk
	 * @param dataType   发票类型：1:进项；2:销项
	 * @param startDate   开始日期  2019-01-01
	 * @param endDate    结束日期 2019-01-01
	 * @param companyDto  客户公司
	 * @return
	 */
	@Transactional
	private String invoiceCollection(String companyPk, int dataType, String startDate, String endDate,B2bCompanyDto companyDto) {
		String result = RestCode.CODE_0000.toJson();
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
		//1：请求众望进销项发票采集接口
		JSONObject object = new JSONObject();
		object.put("taxNumber", companyDto.getOrganizationCode());
		object.put("dataType", dataType);
		object.put("startDate", startDate);
		object.put("endDate", endDate);
		object.put("page", 1);
		try {
			String decyptString = zwHttpRequest("invoiceCollection", object.toJSONString());
			this.saveZhongWangInfo("invoiceCollection", companyDto.getOrganizationCode(), object.toJSONString(), decyptString, null);
			JSONObject jsonObject = JSONObject.parseObject(decyptString);
			//接口返回成功code：0
			if (null!=jsonObject.getString("code") && !jsonObject.getString("code").equals("")&&jsonObject.getString("code").equals("0")) {
				//将数据库中原来的数据删除
				b2bCreditInvoiceDaoEx.deleteEx(companyPk, startDate, endDate,dataType);
				int pages = jsonObject.getJSONObject("data").getInteger("pages");//总页数
				if (pages>0) {
					List<B2bCreditInvoiceDto> list = new ArrayList<>();
					JSONArray jsonArray =  jsonObject.getJSONObject("data").getJSONArray("invoices");
					List<B2bCreditInvoiceDto> tempList = JSONObject.parseArray(jsonArray.toJSONString(), B2bCreditInvoiceDto.class);
					list.addAll(tempList);
					//该接口是分页返回的，我们需要所有的数据，所以多次请求
					for (int i = 2; i <=  pages; i++) {
						object.put("page", i);
						decyptString = zwHttpRequest("invoiceCollection", object.toJSONString());
						this.saveZhongWangInfo("invoiceCollection", companyDto.getOrganizationCode(), object.toJSONString(), decyptString, null);
						jsonObject = JSONObject.parseObject(decyptString);
						jsonArray = jsonObject.getJSONObject("data").getJSONArray("invoices");
						tempList = JSONObject.parseArray(jsonArray.toJSONString(), B2bCreditInvoiceDto.class);
						list.addAll(tempList);
					}
					//对得到的list做二次处理
					if (list.size()>0) {
						JSONObject paramObject = new JSONObject();
						JSONObject checkInvoiceObject = null;
						JSONArray itemsArray =null;
						List<B2bCreditInvoiceDto> itemsList = new ArrayList<>();
						for (B2bCreditInvoiceDto dto : list) {
							B2bCreditInvoice model = new B2bCreditInvoice();
							model.UpdateDTO(dto);
							model.setCompanyPk(companyPk);
							model.setDataType(dataType);
							paramObject.put("invoiceCode", dto.getInvoiceCode());
							paramObject.put("invoiceNumber", dto.getInvoiceNumber());
							paramObject.put("billingDate", formater.format(dto.getBillingDate()));
							paramObject.put("totalAmount",dto.getTotalAmount());
							decyptString = zwHttpRequest("checkInvoice", paramObject.toJSONString());
							this.saveZhongWangInfo("checkInvoice", companyDto.getOrganizationCode(), paramObject.toJSONString(), decyptString, null);
							checkInvoiceObject = JSONObject.parseObject(decyptString);
							if (null!=checkInvoiceObject.getString("code") && !checkInvoiceObject.getString("code").equals("")&&checkInvoiceObject.getString("code").equals("0")) {
								itemsArray = checkInvoiceObject.getJSONObject("data").getJSONArray("items");
								itemsList = JSONObject.parseArray(itemsArray.toJSONString(), B2bCreditInvoiceDto.class);
								for (B2bCreditInvoiceDto  itemDto: itemsList) {
									model.setPk(KeyUtils.getUUID());
									model.setSalesTaxName(checkInvoiceObject.getJSONObject("data").getString("salesName"));
									model.setTaxRate(itemDto.getTaxRate());
									model.setCommodityName(itemDto.getCommodityName());
									model.setQuantity(itemDto.getQuantity());
									model.setUnitPrice(itemDto.getUnitPrice());
									b2bCreditInvoiceDaoEx.insert(model);
								}
							}else{
								model.setPk(KeyUtils.getUUID());
								b2bCreditInvoiceDaoEx.insert(model);
							}
						}
					}
				}
			}else {
				result = RestCode.CODE_S999.toJson();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}


	/**
	 * 证书结果查询
	 * 
	 * @param pk  b2b_credit表pk
	 * @param companyDto   客户公司
	 * @return 
	 */
	private int certificateResult(B2bCompanyDto companyDto) {
		int certificateState = -1;//0.待处理 1.未提交 2.待提交 3.审核中 4.制作中 5.制作完成 6.未通过 7.审核完成 8.众望方申请中 9.失败
		String decyptString = null;
		decyptString = zwHttpRequest("certificateResult", companyDto.getOrganizationCode());
		this.saveZhongWangInfo("certificateResult", companyDto.getOrganizationCode(), companyDto.getOrganizationCode(), decyptString, null);
		JSONObject jsonObject = JSONObject.parseObject(decyptString);
		if (null!=jsonObject.getString("code") && !jsonObject.getString("code").equals("")&&jsonObject.getString("code").equals("0")) {
			certificateState = jsonObject.getJSONObject("data").getInteger("certificateState");
		}
		return certificateState;
	}
	
	
	
	/**
	 * 众望请求
	 * @param method  请求的方法
	 * @param param  未加密的参数
	 * @return
	 */
	private String zwHttpRequest(String method,String param){
		String zw_key =  PropertyConfig.getProperty("wzsy_zw_key");
		String zw_taxNo = PropertyConfig.getProperty("wzsy_zw_taxNo");
		String zw_url = PropertyConfig.getProperty("wzsy_zw_url");
		Map<String, String> map = new HashMap<>();
		String result = null;
		try {
			map.put("taxNo",zw_taxNo );//平台税号
			map.put("param",AESUtil.encrypt(param,zw_key));
			result = AESUtil.decrypt(HttpHelper.doPost(zw_url+method, map), zw_key);// 对结果解密
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	
	/**
	 * 申请证书
	 * 
	 * @param pk  b2b_credit表pk
	 * @param companyDto   客户公司
	 * @return
	 */
	private String certificateAccess(String creditPk, B2bCompanyDto companyDto) {
		B2bCreditDto creditDto = creditDaoEx.getByPk(creditPk);
		Map<String, String> certificate = new HashMap<>();
		certificate.put("taxNo", companyDto.getOrganizationCode());//客户税号
		certificate.put("taxName", companyDto.getName());
		certificate.put("areaName", provinceSplit(companyDto.getProvinceName()));
		JSONObject object = new JSONObject();
		object.put("taxAuthorityCode",null==creditDto.getTaxAuthorityCode()?"":creditDto.getTaxAuthorityCode());
		object.put("taxAuthorityName",null==creditDto.getTaxAuthorityName()?"":creditDto.getTaxAuthorityName());
		object.put("taxNature",null==creditDto.getTaxNature()?"":creditDto.getTaxNature());
		object.put("certificate", certificate);
		object.put("delegateCert", Base64Utils.fileToBase64(creditDto.getDelegateCertUrl()));
		object.put("fileType", 1);//文件类型：1:pdf 2:zip
		//1：执行申请
		String decyptString = zwHttpRequest("certificateAccess", object.toJSONString());
		this.saveZhongWangInfo("certificateAccess", companyDto.getOrganizationCode(), object.toJSONString(), decyptString, null);
		JSONObject jsonObject = JSONObject.parseObject(decyptString);
		//2：b2b_credit表  certificateStatus字段状态改成2
		if (null!=jsonObject.getString("code") && !jsonObject.getString("code").equals("")&&jsonObject.getString("code").equals("0")) {
			B2bCredit model = new B2bCredit();
			model.setPk(creditPk);
			model.setCertificateStatus(2);
			creditDaoEx.update(model);
			return RestCode.CODE_A006.toJson();//证书申请中
		}else {
			return RestCode.CODE_A007.toJson();//申请证书失败
		}
	}
	
	
	
	/**
	 * 截取省份名称的"省"字
	 * 
	 * @param provinceName
	 * @return  返回结果
	 */
	private String provinceSplit(String provinceName) {
		String result=null;
		if (provinceName.substring(provinceName.length()-1).equals("省")
				|| provinceName.substring(provinceName.length()-1).equals("市")) {
			result =  provinceName.substring(0, provinceName.length() -1);
		}else {
			result = provinceName;
		}
		return result;
	}
	
    
	/**
	 * 验证日期的合法性
	 * @param startDate 开始日期  
	 * @param endDate 结束日期
	 * @return ture:合法；false:不合法
	 */
    public boolean dateValid(String startDate, String endDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateFirst = null;
        Date dateLast = null;
		try {
			dateFirst = dateFormat.parse(startDate);
			dateLast = dateFormat.parse(endDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
        Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(dateFirst);
		rightNow.add(Calendar.MONTH, 6);
		dateFirst = rightNow.getTime();
		SimpleDateFormat dateFormatTemp = new SimpleDateFormat("yyyyMMdd");
        String dateFirstTemp = dateFormatTemp.format(dateFirst);
        String dateLastTemp = dateFormatTemp.format(dateLast);
        int dateFirstIntVal = Integer.parseInt(dateFirstTemp);
        int dateLastIntVal = Integer.parseInt(dateLastTemp);
        if (dateFirstIntVal > dateLastIntVal) {
            return true;
        } else {
            return false;
        }
    }
    
    
	
	/**
	 * 保存调用众望接口的日志
	 * @param APIName  接口名称
	 * @param taxNo   客户税号
	 * @param param  参数
	 * @param responseValue  返回结果
	 * @param id
	 * @return
	 */
	private String saveZhongWangInfo(String APIName,String taxNo,String param,String responseValue, String id) {
		ZhongWangInfo info = new ZhongWangInfo();
		if (id == null) {
			String infoId = KeyUtils.getUUID();
			info.setId(infoId);
			info.setAPIName(APIName);
			info.setTaxNo(taxNo);
			info.setParam(param);
			info.setResponseValue(responseValue);
			info.setInsertTime(DateUtil.formatDateAndTime(new Date()));
			mongoTemplate.save(info);
			return infoId;
		} else {
			Update update = new Update();
			update.set("responseValue", responseValue);
			mongoTemplate.upsert(new Query(Criteria.where("id").is(id)),update, ZhongWangInfo.class);
			return id;
		}
	}
	
}
