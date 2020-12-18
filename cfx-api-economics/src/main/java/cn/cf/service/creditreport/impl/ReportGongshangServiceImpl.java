package cn.cf.service.creditreport.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import cn.cf.common.creditreport.gongshang.CsiRisknameResponse;
import cn.cf.common.creditreport.gongshang.PostUtils;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.entity.RayxDownLoad;
import cn.cf.service.creditreport.ReportGongshangService;
import cn.cf.util.DateUtil;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Service
public class ReportGongshangServiceImpl implements ReportGongshangService {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	
	@Override
	public JSONObject search(B2bCompanyDto company) {
		CsiRisknameResponse resp = null;
		JSONObject reqJson = new JSONObject();
		JSONObject respJson = new JSONObject();
		reqJson.put("keyType", "32");//查询条件 03-身份证；31-组织机构代码；32-企业名称；33-统一社会代码；34-工商注册号
		reqJson.put("key", company.getName());//查询条件内容
		//查询情报
		resp = PostUtils.post("/EFRS/INFOSERVICE2/companydetails/V3", "7", reqJson);
		respJson = getAllDate(resp, "intelligence", reqJson);		
		//查询风险---风险名单 type：1
		resp = PostUtils.post("/csi/riskname/list/search/V4", "1", reqJson);
		respJson = getAllDate(resp, "risk", reqJson);
		//查询风险---风险明细 type：3
		resp = PostUtils.post("/csi/riskname/list/search/V4", "3", reqJson);
		respJson = getAllDate(resp, "riskDetail", reqJson);
		//查询风险---涉诉信息 type：12
		respJson.put("model", "ENTERPRISEROLE|ROLEINFORS|ADJUDICATORY|CASECAUSECOUNT|CASETYPECOUNT|JUDGETDATECOUNT|ROLENAMECOUNT|HEARINGANNOUNCEMENT|HEARINGANNOUNCEDETAIL|COURTANNOUNCEMENT|COURTANNOUNCEDETAIL");
		resp = PostUtils.post("/csi/riskname/list/search/V4", "12", reqJson);
		respJson = getAllDate(resp, "appeal", reqJson);
		//查询风险---监管处罚 type：23
//		resp = PostUtils.post("/csi/riskname/list/search/V4", "23", reqJson);
//		respJson = getAllDate(resp, "supervise", reqJson);
		//查询报告
		reqJson.put("model", "BASICRISK|EQUITYANDRELATION|EXECUTIVEANDINVESTMENT|FULLVIEW");
		resp = PostUtils.post("/EFRS/RISKREPORT/search/V2", "10", reqJson);
		insertRayxDownLoad(company.getPk(), company.getName());
		//查询关联---企业族谱 model: FAMILYTREE
		reqJson.put("model", "FAMILYTREE");
		reqJson.put("filter", "{\"layer\":\"" + 3 + "\"}");
		resp = PostUtils.post("/EFRS/INFOSERVICE1/companyrelation/V3", "11", reqJson);
		respJson = getAllDate(resp, "familytree", reqJson);
		//查询关联---多节点关联  model: MULTINODES
		reqJson.put("model", "MULTINODES");
		resp = PostUtils.post("/EFRS/INFOSERVICE1/companyrelation/V3", "11", reqJson);
		respJson = getAllDate(resp, "multinodes", reqJson);
		//查询关联---控制路径 model: ROUTECONTROL
		reqJson.put("model", "ROUTECONTROL");
		resp = PostUtils.post("/EFRS/INFOSERVICE1/companyrelation/V3", "11", reqJson);
		respJson = getAllDate(resp, "rontecontrol", reqJson);
		//查询关联---实质关联  model: ACTUALRELATION
		reqJson.put("model", "ACTUALRELATION");
		resp = PostUtils.post("/EFRS/INFOSERVICE1/companyrelation/V3", "11", reqJson);
		respJson = getAllDate(resp, "actualrelation", reqJson);
		
		return respJson;
	}

	private JSONObject getAllDate(CsiRisknameResponse resp,String key,JSONObject object){
		if(null != resp && null != resp.getData() && !"".equals(resp.getData())){
			object.put(key, resp.getData());
		}
		return object;
	}
	
	
	
	private void insertRayxDownLoad(String companyPk,String companyName){
		Criteria c = new Criteria();
		c.and("companyPk").is(companyPk);
		List<RayxDownLoad> downList = mongoTemplate.find(new Query(c),
				RayxDownLoad.class);
		if(null == downList || downList.size()==0){
			RayxDownLoad downLoad = new RayxDownLoad();
			downLoad.setCompanyPk(companyPk);
			downLoad.setInsertTime(DateUtil.formatDateAndTime(new Date()));
			downLoad.setDownLoadStatus(1);
			mongoTemplate.save(downLoad);
		}else{
			Update updatec = new Update();
			updatec.set("downLoadStatus", 1);
			mongoTemplate.upsert(new Query(c), updatec, RayxDownLoad.class);//定时任务改状态
		}
		
	}

	@Override
	public JSONArray searchReport(String companyName) {
		JSONArray array = null;
		JSONObject json = new JSONObject();
		json.put("keyType", "32");//查询条件 03-身份证；31-组织机构代码；32-企业名称；33-统一社会代码；34-工商注册号
		json.put("key", companyName);//查询条件内容
		json.put("model", "BASICRISK|EQUITYANDRELATION|EXECUTIVEANDINVESTMENT|FULLVIEW");
		CsiRisknameResponse resp = PostUtils.post("/EFRS/RISKREPORT/search/V2", "10", json);
		if(null != resp && null != resp.getData()){
			array =  JSONArray.parseArray(resp.getData());
		}
		return array;
	}
}
