package cn.cf.controller;

import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bLoanNumberDto;
import cn.cf.entry.BankInfo;
import cn.cf.service.common.HuaxianhuiBankService;
import cn.cf.service.foreign.ForeignCompanyService;
import cn.cf.service.platform.B2bLoanNumberService;
import cn.cf.util.DateUtil;
import cn.cf.util.KeyUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

/**
 * 苏州银行通知接口
 * @description:
 * @author FJM
 * @date 2018-6-28 下午5:21:37
 */
@RestController
@RequestMapping("economics")
public class NoticeController {
	
	@Autowired
	private B2bLoanNumberService b2bLoanNumberService;
	
	@Autowired
	private HuaxianhuiBankService huaxianhuiBankService;
	
	@Autowired
	private ForeignCompanyService companyService;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 授信审批结果通知
	 * @param req
	 */
	@RequestMapping(value = "creditResultNotice", method = RequestMethod.POST)
	public String creditResultNotice(HttpServletRequest req){
		
//		try {
//			JSONObject json = this.getJson(req,"HXH009");
//			String companyName = json.getString("KHMC");
//			String customerNumber = json.getString("KHH");
//			String startTime = json.getString("QSRQ");
//			String endTime = json.getString("DQRQ");
//			huaxianhuiBankService.updateCreditAudit(companyName, customerNumber, startTime, endTime, null);
//		} catch (Exception e) {
//			logger.error("creditResultNotice",e);
//		}
		return getResult();
	}
	
	
	/**
	 * 额度启用通知
	 * @param req
	 */
	@RequestMapping(value = "creditAmountNotice", method = RequestMethod.POST)
	public String creditAmountNotice(HttpServletRequest req){
		
		JSONObject json = this.getJson(req,"HXH011");
		String companyName = json.getString("KHMC");//公司名称
		B2bCompanyDto company =  companyService.getCompanyByName(companyName);
		if(null != company){
			huaxianhuiBankService.updateCreditAmount(company);
		}
		return getResult();
	}
	
	/**
	 * 放款结果通知
	 * @param req
	 */
	@RequestMapping(value = "loanResultNotice", method = RequestMethod.POST)
	public String loanResultNotice(HttpServletRequest req){
		try {
			JSONObject json = this.getJson(req,"HXH014");
			String orderNumber = json.getString("DDBH");
			B2bLoanNumberDto o = b2bLoanNumberService.getB2bLoanNumberDto(orderNumber);
			if(null != o){
				//只有订单在申请中的时候可以调用
				if(o.getLoanStatus() == 2){
					huaxianhuiBankService.updateLoanDetails(o);
				}
			}
		} catch (Exception e) {
			logger.error("errorLoanResultNotice",e);
		}
		return getResult();
	}
	
	
	/**
	 * 还款结果通知
	 * @param req
	 */
	@RequestMapping(value = "repaymentResultNotice", method = RequestMethod.POST)
	public String repaymentResultNotice(HttpServletRequest req){
		try {
			JSONObject json = this.getJson(req,"HXH018");
			String orderNumber = json.getString("DDBH");
			B2bLoanNumberDto o = b2bLoanNumberService.getB2bLoanNumberDto(orderNumber);
			if(null != o){
				//只有订单在部分还款或者申请成功的时候可以调用
				if(o.getLoanStatus() == 3 || o.getLoanStatus() == 6){
					huaxianhuiBankService.updateRepaymentDetails(o);
				}
			}
		} catch (Exception e) {
			logger.error("errorRepaymentResultNotice",e);
		}
		return getResult();
	}
	
	private JSONObject getJson(HttpServletRequest req,String code){
		ServletInputStream ris = null;
		JSONObject json = null;
		try {
			ris = req.getInputStream();
			StringBuilder content = new StringBuilder();
			byte[] b = new byte[1024];
			int lens = -1;
			while ((lens = ris.read(b)) > 0) {
				content.append(new String(b, 0, lens));
			}
			json = new JSONObject(content.toString());
			System.out.println(json.toString());
			if(null != code){
				this.saveBankInfo(code, null,
						json.toString(),null, null, null,
						"passive");
			}
			
		}catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}
	
	private String getResult(){
		return "{\"FLAG\":1}";
	}
	
	private String saveBankInfo(String code, String id, String requestValue,
			String requestDesValue, String responseValue,
			String responseDesValue, String requestMode) {
		BankInfo bank = new BankInfo();
		if (id == null) {
			String infoId = KeyUtils.getUUID();
			bank.setId(infoId);
			bank.setCode(code);
			bank.setBankName("suzhouBank");
			bank.setRequestValue(requestValue);
			bank.setRequestDesValue(requestDesValue);
			bank.setInsertTime(DateUtil.formatDateAndTime(new Date()));
			bank.setRequestMode(requestMode);
			mongoTemplate.save(bank);
			return infoId;
		} else {
			Update update = new Update();
			update.set("responseValue", responseValue);
			update.set("responseDesValue", responseDesValue);
			mongoTemplate.upsert(new Query(Criteria.where("id").is(id)),
					update, BankInfo.class);
			return id;
		}
	}
}
