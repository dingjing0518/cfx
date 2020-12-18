package cn.cf.service.common.impl;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import cn.cf.common.RestCode;
import cn.cf.common.RiskControlType;
import cn.cf.common.creditreport.gongshang.PostUtils;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.entity.CreditReportInfo;
import cn.cf.entity.RayxDownLoad;
import cn.cf.entity.WzDownLoad;
import cn.cf.entry.BankBaseResp;
import cn.cf.property.PropertyConfig;
import cn.cf.service.common.HuaxianhuiReportService;
import cn.cf.service.common.HuaxianhuiService;
import cn.cf.service.creditpay.BankSuzhouService;
import cn.cf.service.creditreport.ReportGongshangService;
import cn.cf.util.Constants;
import cn.cf.util.DateUtil;
import cn.cf.util.FileUtil;
import cn.cf.util.KeyUtils;
import cn.cf.util.OSSUtils;
import cn.cf.util.SavaZipUtil;
import cn.cf.utils.SftpUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Service
public class HuaxianhuiReportServiceImpl implements HuaxianhuiReportService {

	@Autowired
	private BankSuzhouService bankSuzhouService;

	@Autowired
	private ReportGongshangService reportGongshangService;

	@Autowired
	private HuaxianhuiService huaxianhuiService;

	@Autowired
	private MongoTemplate mongoTemplate;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public String searchWzFile(B2bCompanyDto company) {
		String rest = null;
		Criteria c = new Criteria();
		c.and("downLoadStatus").lt(3);
		c.and("companyPk").is(company.getPk());
		//查询是否有未上传完的文件
		List<WzDownLoad> downList = mongoTemplate.find(new Query(c),
				WzDownLoad.class);
		if(null == downList || downList.size()==0){
			BankBaseResp resp  = bankSuzhouService.wzSearch(company);
			//取到数据存mongoDB
			if(null != resp){
				String	fileName = resp.getWxFileName();
				rest = huaxianhuiService.updateWz(company.getPk(),fileName);
				//没有取到调申请
			}else{
				resp = bankSuzhouService.wzApplication(company);
				if(null != resp){
					RestCode.CODE_Z000.setMsg(resp.getMsg());
					rest = RestCode.CODE_Z000.toJson();
				}
			}
			if(null == resp){
				rest = RestCode.CODE_S999.toJson();
			}
		}else{
			RestCode.CODE_Z000.setMsg("文件生成中,无法操作");
			rest = RestCode.CODE_Z000.toJson();
		}

		return rest;

	}


	@Override
	public void downLoadWzFile() {

		//上传文件
		//1查询已经调用过下载接口成功的数据
		Query qu = new Query(Criteria.where("downLoadStatus").is(
				2));
		List<WzDownLoad> upList = mongoTemplate.find(qu,
				WzDownLoad.class);
		if(null != upList && upList.size()>0){
			//2上传文件
			for(WzDownLoad dto : upList){
				huaxianhuiService.upLoadWz(dto);
			}
		}
		//下载文件
		//1查询大于一个小时以后的未下载的记录
		Criteria c = new Criteria();
		c.and("downLoadStatus").is(1);
		c.and("insertTime").lte(DateUtil.timeOfBeforeMin(-10));
		List<WzDownLoad> downList = mongoTemplate.find(new Query(c),
				WzDownLoad.class);
		if(null != downList && downList.size()>0){
			for(WzDownLoad wz : downList){
				//2调用苏州银行下载接口
				BankBaseResp resp = bankSuzhouService.wzDownload(wz.getFileName());
				if(null != resp &&RestCode.CODE_0000.getCode().equals(resp.getCode())){
					Update update = new Update();
					update.set("downLoadStatus", 2);//已下载
					mongoTemplate.upsert(new Query(Criteria.where("id").is(wz.getId())),
							update, WzDownLoad.class);
				}
			}
		}


	}

	@Override
	public String searchRayx(B2bCompanyDto company) {
		String rest = RestCode.CODE_0000.toJson();
	    JSONObject json = reportGongshangService.search(company);
	    if(null != json && !"".equals(json)){
	    	File file = new File(PropertyConfig.getProperty("rayx_path")+"/"+KeyUtils.getUUID()+".txt");
	    	FileUtil.writeTxtFile(json.toJSONString(),file);
	    	String uploadpath = OSSUtils.ossMangerUpload(file, Constants.REPORT);
	    	file.delete();
	    	huaxianhuiService.updateReport(company.getPk(), company.getName(), RiskControlType.RAYX.getValue(), json.toJSONString(), PropertyConfig.getStrValueByKey("oss_url")+uploadpath);
	    }else{
	    	rest = RestCode.CODE_S999.toJson();
	    }
		return rest;
	}




	@Override
	public int downLoadRayxFile()  {
		try {
			List<RayxDownLoad> rayxDownLoadList = mongoTemplate.find(queryVoucher(), RayxDownLoad.class);
			if(null != rayxDownLoadList && rayxDownLoadList.size()>0){
				Boolean flag =true;
				for (RayxDownLoad r:rayxDownLoadList) {
					//	String companyPk ="54c89240372d4748a98c0a3e444f55c3";
					Criteria c = new Criteria();
					c.and("shotName").is("rayx");
					c.and("companyPk").is(r.getCompanyPk());
					List<CreditReportInfo> info = mongoTemplate.find(new Query(c), CreditReportInfo.class);
					JSONArray array = reportGongshangService.searchReport(info.get(0).getCompanyName());
					String saveFile = PropertyConfig.getProperty("rayx_upload");//下载zip文件存放路径
					String zipFile = PropertyConfig.getProperty("rayx_download");//解压完的目标文件夹
//					String jsons = "[{\"TOTALCOUNT\":1,\"MODEL\":\"BASICRISK\",\"TDATA\":[{\"status\":\"0\",\"fileName\":\"7c6c254a-16cb-4654-b42c-5e2e5beadf44.zip\"}],\"SIZE\":1},{\"TOTALCOUNT\":1,\"MODEL\":\"EQUITYANDRELATION\",\"TDATA\":[{\"status\":\"0\",\"fileName\":\"963eab33-fb40-4e0b-b9b5-69ede5e4d08e.zip\"}],\"SIZE\":1},{\"TOTALCOUNT\":1,\"MODEL\":\"EXECUTIVEANDINVESTMENT\",\"TDATA\":[{\"status\":\"0\",\"fileName\":\"53684d51-5d51-4ee3-946b-e2d9a865aef5.zip\"}],\"SIZE\":1},{\"TOTALCOUNT\":1,\"MODEL\":\"FULLVIEW\",\"TDATA\":[{\"status\":\"0\",\"fileName\":\"fce8f66b-d755-47f4-a25e-ecbeb57449cd.zip\"}],\"SIZE\":1}]";
//					JSONArray	array =	JSONArray.parseArray(jsons);
					String[] fileName2 = new String[array.size()];
					for (int i = 0; i < array.size(); i++) {
						JSONObject job = array.getJSONObject(i);
						String o = String.valueOf(job.get("TDATA"));
						JSONArray array2 = JSONArray.parseArray(o);
						for (int a = 0; a < array2.size(); a++) {
							JSONObject job2 = array2.getJSONObject(a);
							String downLoadStatus = String.valueOf(job2.get("status"));
							String fileName = String.valueOf(job2.get("fileName"));
							if (downLoadStatus.equals("0")) {
								fileName2[i] = fileName;
							}else{
								flag = false;
								break;
							}
						}
					}
					if (flag){
						PostUtils.downLoad(fileName2);
						System.out.println(fileName2.length);
						for (int k = 0; k < fileName2.length; k++) {
							String filePath = saveFile +fileName2[k];    //本地zio文件存放路径
							SftpUtils sftpUtils = new SftpUtils();
							sftpUtils.decompressZip(filePath, zipFile);//解压本地zip文件
							System.out.println(fileName2[k]);
							
						}
						File file = new File(PropertyConfig.getProperty("rayx_download") + KeyUtils.getUUID() + ".txt");//sring转成txt文件
						FileUtil.writeTxtFile(info.get(0).getDetail(), file);
						String fileName = KeyUtils.getUUID() + ".zip";
						
						SavaZipUtil.compressToZip(PropertyConfig.getProperty("rayx_download"), PropertyConfig.getProperty("rayx_zip"), fileName);
						String zipName = PropertyConfig.getProperty("rayx_zip") + fileName;//上传的zip文件在本地的路径
						File zip = new File(zipName);//上传zip到oss
						String url = PropertyConfig.getProperty("oss_url") + OSSUtils.ossMangerUpload(zip, 9);
						Update update = new Update();
						update.set("fileUrl", url);
						mongoTemplate.upsert(new Query(c),
								update, CreditReportInfo.class);//存oss文件的url
						Query queryc = queryVoucher();
						Update updatec = new Update();
						updatec.set("downLoadStatus", 2);
						mongoTemplate.upsert(queryc, updatec, RayxDownLoad.class);//定时任务改状态
						SavaZipUtil.delAllFile(PropertyConfig.getProperty("rayx_zip"));  //清空文件夹
						SavaZipUtil.delAllFile(PropertyConfig.getProperty("rayx_upload"));
						SavaZipUtil.delAllFile(PropertyConfig.getProperty("rayx_download"));
						fileName2 = null;//清空数组
						
					}
				}
			}
		}catch (Exception e){
			logger.error("errorRayxUpLoad:"+e);
			return 0;
		}
		return 1;
	}

	@Override
	public CreditReportInfo searchCreditReport(String companyPk) {
		Query query = new Query();
		query.addCriteria(Criteria.where("companyPk").is(companyPk));
		query.addCriteria(Criteria.where("shortName").is("rayx"));
		CreditReportInfo creditReportInfo = mongoTemplate.findOne(query,
				CreditReportInfo.class);
		return creditReportInfo;
	}

	private Query queryVoucher() {
		Criteria c = new Criteria();
		c.and("downLoadStatus").is(1);
		c.and("insertTime").lte(DateUtil.timeOfBeforeMin(-10));
		return new Query(c);
	}


}
