package cn.cf.service.creditpay.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import cn.cf.common.RestCode;
import cn.cf.common.creditpay.zheshang.PostUtils;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bCreditGoodsDto;
import cn.cf.dto.B2bLoanNumberDto;
import cn.cf.dto.SysCompanyBankcardDto;
import cn.cf.entity.B2bCreditGoodsDtoMa;
import cn.cf.entity.B2bOrderDtoMa;
import cn.cf.entry.BankBaseResp;
import cn.cf.entry.BankInfo;
import cn.cf.property.PropertyConfig;
import cn.cf.service.creditpay.BankZheshangService;
import cn.cf.util.ArithUtil;
import cn.cf.util.DateUtil;
import cn.cf.util.KeyUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruimin.intfin.sdk.inf.ApiRspData;

@Service
public class BankZheshangServiceImpl implements BankZheshangService {
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public BankBaseResp searchBankCreditAmount(B2bCompanyDto company,
			B2bCreditGoodsDto dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BankBaseResp searchloan(B2bLoanNumberDto loanNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BankBaseResp searchRepayment(B2bLoanNumberDto loanNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BankBaseResp payOrder(B2bOrderDtoMa order, B2bCompanyDto company,
			SysCompanyBankcardDto card, B2bCreditGoodsDtoMa cgDto) {
		BankBaseResp resp = null;
		String id = null;
		try {
			//金融产品支付比例
			Double proportion = cgDto.getProportion();
			if(null == proportion || proportion == 0d){
				proportion = 1d;
			}
			Double totalAmount = ArithUtil.round(ArithUtil.mul(order.getActualAmount(), proportion), 2);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("platfId", PropertyConfig.getProperty("zs_shopid"));
			map.put("transType", "02");
			JSONObject js = new JSONObject();
			js.put("businessNum", order.getOrderNumber());//业务编号，一笔应收账款记录对应一个编号
			js.put("payerType", "1");//付款人身份类型
			js.put("payerCertType", "2");//付款人证件类型
			js.put("payerCertNo", order.getPurchaser().getOrganizationCode());//付款人证件号码
			js.put("payeeType", "1");//收款人身份类型
			js.put("payeeCertType", "2");//收款人证件类型
			js.put("payeeCertNo", order.getSupplier().getOrganizationCode());//收款人证件号码
			js.put("payeeAcc", card.getBankAccount());//收款人账号
			js.put("currency", "01");//应收账款币种
			js.put("amount", ArithUtil.twoZeroByDouble(totalAmount));//应收账款金额，保留小数点后2位
			js.put("dueDate", DateUtil.formatYearMonthDay(DateUtil.getDaysForDate(new Date(),cgDto.getTerm())));//应收账款账期截止日
			JSONArray array = new JSONArray();
			array.add(js);
			map.put("recDetail", array.toJSONString());
			id = this.saveBankInfo(null, null,
					map.toString(), null,
					"active","pay");
			ApiRspData data = PostUtils.post("/api/receivSync",map);
			System.out.println("data------------"+JSON.toJSONString(data));
			if(null != data){
				this.saveBankInfo(null, id, map.toString(),
						JSON.toJSONString(data), "active","pay");
				resp = new BankBaseResp();
				if(null != data.getBody() && null != data.getBody().get("errCode")
						&& "000000".equals(data.getBody().get("errCode").toString())){
					if(null != data.getBody().get("recDetail")){
						JSONArray arrays = JSONArray.parseArray(data.getBody().get("recDetail").toString());
						//syncFlag = 0 表示成功
						if(null !=arrays && null !=arrays.getJSONObject(0).get("syncFlag")
								&& "0".equals(arrays.getJSONObject(0).get("syncFlag").toString())){
							resp.setCode(RestCode.CODE_0000.getCode());
						}else{
							resp.setCode(RestCode.CODE_Z000.getCode());
							resp.setMsg(null !=arrays.getJSONObject(0).get("note")?
									arrays.getJSONObject(0).get("note").toString():"");
						}
					}else{
						resp.setCode(RestCode.CODE_Z000.getCode());
						resp.setMsg("银行信息数据异常");
					}	
				}else{
					resp.setCode(RestCode.CODE_Z000.getCode());
					resp.setMsg("银行信息数据异常");
				}
			}
		} catch (Exception e) {
			logger.error("payOrder",e);
		}
		return resp;
	
	}

	@Override
	public BankBaseResp cancelOrder(B2bLoanNumberDto loanNumber) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	private String saveBankInfo(String code, String id, String requestValue,
			 String responseValue, String requestMode,String hxhCode) {
		BankInfo bank = new BankInfo();
		if (id == null) {
			String infoId = KeyUtils.getUUID();
			bank.setId(infoId);
			bank.setCode(code);
			bank.setBankName("zheshangBank");
			bank.setRequestValue(requestValue);
			bank.setInsertTime(DateUtil.formatDateAndTime(new Date()));
			bank.setRequestMode(requestMode);
			bank.setHxhCode(hxhCode);
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
