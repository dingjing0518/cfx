package cn.cf.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.cf.common.BanksType;
import cn.cf.common.RestCode;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bTokenDto;
import cn.cf.entry.BankInfo;
import cn.cf.jedis.JedisUtils;
import cn.cf.json.JsonUtils;
import cn.cf.service.common.HuaxianhuiBankService;
import cn.cf.util.DateUtil;
import cn.cf.util.KeyUtils;
import cn.cf.util.ServletUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * 银行通知接口类(新) 目前用于建设银行
 * @description:
 * @author FJM
 * @date 2019-10-22 上午9:50:35
 */
@RestController
@RequestMapping("economics")
public class NoticeEconomicsController {
	
	@Autowired
	private HuaxianhuiBankService huaxianhuiBankService;
	
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	/**
	 * 客户信息推送接口
	 * 
	 * @param appId
	 * @param appSecret
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "applyresult", method = RequestMethod.POST)
	public String customerjNotice(HttpServletRequest request) {
		String returnCode = "{\"success\":\"true\",\"errorCode\":\"0000\",\"errorMsg\":\"成功\"}";
		String rest =null;
		String encodeData = ServletUtils.getStringParameter(request, "jsonData");
		String token = ServletUtils.getStringParameter(request, "token");
		B2bTokenDto tokenDto = JedisUtils.get(token, B2bTokenDto.class);
		String id = this.saveBankInfo("applyresult", null,
				encodeData, null,
				"passive");
		//验证可用token
		if(null != tokenDto && null !=tokenDto.getAccType() && 
				(tokenDto.getAccType() == 3)){
			//验证字符串是否有效
			if (encodeData != null && !"".equals(encodeData)) {
				System.out.println("-----------------------jsonData:"+encodeData);
				switch (tokenDto.getAccType()) {
				case 3:
					rest = huaxianhuiBankService.integrationCreditInfo(encodeData,BanksType.bank_jianshe);
					break;
				default:
					break;
				}
				
			} else {
				rest = RestCode.CODE_A001.toJson();
			}
		}else{
			rest = RestCode.CODE_S001.toJson();
		}
		JSONObject json = JsonUtils.toJSONObject(rest);
		if(RestCode.CODE_0000.getCode().equals(json.getString("code"))){
			huaxianhuiBankService.updateCreditAmount(JsonUtils.toBean(json.getString("data"), B2bCompanyDto.class));
		}else{
			returnCode = "{\"success\":\"false\",\"errorCode\":\"9999\",\"errorMsg\":\""+json.getString("msg")+"\"}";
		}
		this.saveBankInfo("applyresult", id,
				encodeData, returnCode,
				"passive");
		return returnCode;
	}
	
	/**
	 * 订单支付推送接口
	 * 
	 * @param appId
	 * @param appSecret
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "notify4paymentResult", method = RequestMethod.POST)
	public String orderPayjNotice(HttpServletRequest request) {
		RestCode code = RestCode.CODE_0000;
		String rest = "{\"success\":\"true\",\"errorCode\":\"0000\",\"errorMsg\":\"成功\"}";
		String encodeData = ServletUtils.getStringParameter(request, "jsonData");
		String token = ServletUtils.getStringParameter(request, "token");
		B2bTokenDto tokenDto = JedisUtils.get(token, B2bTokenDto.class);
		String id = this.saveBankInfo("notify4paymentResult", null,
				encodeData, null,
				"passive");
		//验证可用token
		if(null != tokenDto && null !=tokenDto.getAccType() && 
				(tokenDto.getAccType() == 3)){
			//验证字符串是否有效
			if (encodeData != null && !"".equals(encodeData)) {
				System.out.println("-----------------------jsonData:"+encodeData);
				switch (tokenDto.getAccType()) {
				case 3:
					code = huaxianhuiBankService.orderPayResult(encodeData,BanksType.bank_jianshe);
					break;
				default:
					break;
				}
				
			} else {
				code = RestCode.CODE_A001;
			}
		}else{
			code = RestCode.CODE_S001;
		}
		if(!RestCode.CODE_0000.getCode().equals(code.getCode())){
			rest = "{\"success\":\"false\",\"errorCode\":\"9999\",\"errorMsg\":\""+code.getMsg()+"\"}";
		}
		this.saveBankInfo("applyresult", id,
				encodeData, rest,
				"passive");
		return rest;
	}
	
	private String saveBankInfo(String code, String id, String requestValue,
			 String responseValue,
			 String requestMode) {
		BankInfo bank = new BankInfo();
		if (id == null) {
			String infoId = KeyUtils.getUUID();
			bank.setId(infoId);
			bank.setCode(code);
			bank.setBankName("jiansheBank");
			bank.setRequestValue(requestValue);
			bank.setInsertTime(DateUtil.formatDateAndTime(new Date()));
			bank.setRequestMode(requestMode);
			mongoTemplate.save(bank);
			return infoId;
		} else {
			Update update = new Update();
			update.set("responseValue", responseValue);
			mongoTemplate.upsert(new Query(Criteria.where("id").is(id)),
					update, BankInfo.class);
			return id;
		}
	}
}
