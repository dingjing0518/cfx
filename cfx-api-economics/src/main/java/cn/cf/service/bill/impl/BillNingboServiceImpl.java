package cn.cf.service.bill.impl;

import java.util.ArrayList;
import java.util.Date;
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
import cn.cf.common.bill.PostUtils;
import cn.cf.dto.B2bBillCusgoodsDto;
import cn.cf.dto.B2bBillCustomerDto;
import cn.cf.dto.B2bBillGoodsDto;
import cn.cf.dto.B2bBillInventoryDto;
import cn.cf.dto.B2bBillOrderDto;
import cn.cf.dto.B2bBillSigncardDto;
import cn.cf.dto.SysCompanyBankcardDto;
import cn.cf.entity.B2bOrderDtoMa;
import cn.cf.entry.BankBaseResp;
import cn.cf.entry.BillInfo;
import cn.cf.json.JsonUtils;
import cn.cf.property.PropertyConfig;
import cn.cf.service.bill.BillNingboService;
import cn.cf.util.DateUtil;
import cn.cf.util.KeyUtils;
import cn.cf.util.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Service
public class BillNingboServiceImpl implements BillNingboService {
@Autowired
private MongoTemplate mongoTemplate;

private final Logger logger = LoggerFactory.getLogger(this.getClass());
 
	@Override
	public BankBaseResp bindBillPft(String companyPk,String companyName,
			String organizationCode, List<B2bBillSigncardDto> cards,String bindType) {
			BankBaseResp resp = new BankBaseResp();
			try {
				JSONObject oprInf = new JSONObject();//申请信息
				if(null !=bindType && "1".equals(bindType)){
					oprInf.put("BndOprtTp", "BD01");//绑定
				}else{
					oprInf.put("BndOprtTp", "BD02");//解绑
				}
				JSONObject bndOprInf = new JSONObject();//绑定信息
				bndOprInf.put("EntrprsUnfdSclCrdtCd", organizationCode);//企业统一社会信用代码
				bndOprInf.put("EntrprsNm", companyName);//企业名称
				if("1".equals(bindType)){
						JSONObject entrprsPlafrmAcct = new JSONObject();
						String[] ids = new String[]{organizationCode};
						entrprsPlafrmAcct.put("Id", ids);
						bndOprInf.put("EntrprsPlafrmAcct", entrprsPlafrmAcct);//企业平台用户  绑定时必填；解绑时不得填写
				}
				JSONArray accts = new JSONArray();
				if(null !=cards && cards.size()>0){
					for(B2bBillSigncardDto card : cards){
						JSONObject acct = new JSONObject();//电票账户信息
						acct.put("Id", card.getBankAccount());//企业电票开户账号
						acct.put("AcctSvcr", card.getBankNo());//企业电票开户行号
						accts.add(acct);
					}
				}
				bndOprInf.put("Acct", accts);
				JSONObject json = new JSONObject();
				json.put("OprInf", oprInf);
				json.put("BndOprInf", bndOprInf);
				String id = this.saveBillInfo("bnbOdpsIB001", null, json.toJSONString(), null, "apply");
				String  rest=PostUtils.post("/bnbOdpsIB001", json);
				this.saveBillInfo(null, id, null, rest, null);
				//返回结果
				JSONObject body  = getBody(rest);
				if(null != body){
					if(body.containsKey("errorCode")&&"000000".equals(body.getString("errorCode"))){
						resp.setCode(RestCode.CODE_0000.getCode());
					}else{
						resp.setCode(RestCode.CODE_Z000.getCode());
						resp.setMsg(null!=body.get("errorMsg")?body.get("errorMsg").toString():"银行返回数据异常");
					} 
				}else{
					resp.setCode(RestCode.CODE_Z000.getCode());
					resp.setMsg("银行返回数据异常");
				}
			} catch (Exception e) {
				logger.error("errorBindBillPft",e);
				resp.setCode(RestCode.CODE_S999.getCode());
				resp.setMsg(RestCode.CODE_S999.getMsg());
			}
			return resp;
	}
	private JSONObject getBody(String str){
		if(null != str && !"".equals(str)){
			JSONObject j = JsonUtils.toJSONObject(str);
			return j.getJSONObject("body");
		}else{
			return null;
		}
	}
	@Override
	public BankBaseResp searchBillPft(String organizationCode,Integer status) {
		BankBaseResp resp = null;
		JSONObject queryCondition = new JSONObject();//申请信息
		queryCondition.put("EntrprsUnfdSclCrdtCd",organizationCode);
		if(null != status ){
			queryCondition.put("BndState", 2==status?"BD01":"BD00");//BD01 绑定 BD00解绑
		}
		JSONObject json = new JSONObject();
		json.put("QueryCondition", queryCondition);
		String id = this.saveBillInfo("bnbOdpsIB005", null, json.toJSONString(), null, "customer");
		String rest=PostUtils.post("/bnbOdpsIB005", json);//解绑
		this.saveBillInfo(null, id, null, rest, null);
		//返回结果
		JSONObject body  = getBody(rest);
		if(null != body && body.containsKey("errorCode") && "000000".equals(body.getString("errorCode"))){
			resp = new BankBaseResp();
			resp.setCode(RestCode.CODE_0000.getCode());
			resp.setBillcardList(getBillCusGoods(body));
			
		}
		return resp;
	}
	

	private List<B2bBillSigncardDto> getBillCusGoods(JSONObject body) {
		List<B2bBillSigncardDto> list =null;
		JSONObject bndOprInf = body.getJSONObject("BndOprInf");
		if( null !=  bndOprInf ){
			JSONArray acct=bndOprInf.getJSONArray("Acct");
			if(null != acct && acct.size()>0){
				list = new ArrayList<B2bBillSigncardDto>();
				for (int i = 0; i < acct.size(); i++) { 
					JSONObject ob = (JSONObject) acct.get(i);
					if(null == ob.get("Id") || null == ob.get("AcctSvcr") 
							|| null == ob.get("BndState")){
						continue;
					}
					String id= ob.getString ("Id"); 
					String acctSvcr= ob.getString("AcctSvcr"); 
					String status= ob.getString("BndState"); 
					if(null!= id&&!"".equals( id)&&null!=acctSvcr&&!"".equals(acctSvcr)){
						B2bBillSigncardDto card = new B2bBillSigncardDto();
						card.setBankAccount(id);
						card.setBankNo(acctSvcr);
						card.setStatus("BD01".equals(status)?2:0);//2绑定 0解绑
						list.add(card);
					}
				}
			}
		}
		return list;
	}



	@Override
	public BankBaseResp payPft(B2bOrderDtoMa order, B2bBillCusgoodsDto goods,
			SysCompanyBankcardDto card, Integer billType,Integer payType,String mesgid) {
		//订单信息
		JSONObject ordrInf = new JSONObject();
		ordrInf.put("OrdrId", order.getOrderNumber());//订单编号
		ordrInf.put("OrdrDscrb", "原料采购");//订单描述
		ordrInf.put("OrdrAmt", order.getActualAmount());//订单交易金额
		ordrInf.put("WitnessPymntFlag", payType+"");//0非见票支付  1见票支付
		//支付信息
		JSONObject pymntInf = new JSONObject();
		pymntInf.put("FnclInstsPyMntId", KeyUtils.getFlowNumber());//机构端支付识别码
		String type = "DT00";//无限制
		if(null != billType && billType == 1){
			type = "DT01";//商承票
		}
		if(null != billType && billType == 2){
			type = "DT02";//银承票
		}
		pymntInf.put("DftType", type);//票据类型
		//付款人信息
		JSONObject endrsr = new JSONObject();
		endrsr.put("EntrprsNm", order.getPurchaser().getPurchaserName());//企业名称
		endrsr.put("EntrprsUnfdSclCrdtCd", order.getPurchaser().getOrganizationCode());//付款人企业信用代码
		endrsr.put("Acct", goods.getAccount());//账户
		endrsr.put("AcctSvcr", goods.getBankNo());//开户行行号
		//收款人信息
		JSONObject endrsee = new JSONObject();
		endrsee.put("EntrprsNm", order.getSupplier().getSupplierName());//企业名称
		endrsee.put("EntrprsUnfdSclCrdtCd", order.getSupplier().getOrganizationCode());//收款人企业信用代码
		endrsee.put("Acct", card.getBankAccount());//账户
		endrsee.put("AcctSvcr", card.getBankNo());//开户行行号
		JSONObject json = new JSONObject();
		json.put("OrdrInf", ordrInf);
		json.put("PymntInf", pymntInf);
		json.put("Endrsr", endrsr);
		json.put("Endrsee", endrsee);
		String id = this.saveBillInfo("tradePay", null, json.toJSONString(), null, "pay");
		String rest = PostUtils.pay(json,mesgid);
		this.saveBillInfo(null, id, null, rest, null);
		BankBaseResp resp = null;
		if(null != rest && !"".equals(rest)){
			resp = new BankBaseResp();
			resp.setCode(RestCode.CODE_0000.getCode());
			resp.setHtml(rest);
		}
		return resp;
	}

	@Override
	public BankBaseResp searchOrderPft(String orderNumber) {
		JSONObject queryCondition = new JSONObject();//申请信息
		queryCondition.put("OrdrId", orderNumber);
		JSONObject json = new JSONObject();
		json.put("QueryCondition", queryCondition);
		String id = this.saveBillInfo("bnbOdpsIE004", null, json.toJSONString(), null, "order");
		String rest = PostUtils.post("/bnbOdpsIE004", json);
		this.saveBillInfo(null, id, null, rest, null);
		BankBaseResp resp =new BankBaseResp();
		if(null != rest && !"".equals(rest)){
			JSONObject body  = getBody(rest);
			//判断code 是否是000000
			if(null !=body  && "000000".equals(body.get("errorCode"))){
				//查询是否有支付流水号
				if(null != body.getJSONArray("PymntInf") && body.getJSONArray("PymntInf").size() > 0
						&& null != body.getJSONArray("PymntInf").getJSONObject(0).get("PymntId")){
					resp = new BankBaseResp();
					JSONObject payinfo = body.getJSONArray("PymntInf").getJSONObject(0);
					resp.setSerialNumber(payinfo.getString("PymntId"));
					switch (payinfo.getString("PymnIdSts")) {
					case "PIS00"://已取消
						resp.setBillPayStatus(3);
						break;
					case "PIS01"://已完成
						resp.setBillPayStatus(2);
						break;
					case "PIS02"://已发起
						resp.setBillPayStatus(1);
						break;	
					default:
						break;
					}
					resp.setInventoryList(getInventoryListPft(payinfo.getJSONArray("DrftLst")));
//					//支付流水下没有票据信息的支付流水清空
//					if(null == resp.getInventoryList() || resp.getInventoryList().size()==0){
//						resp.setSerialNumber(null);
//					}
				}
			}
		}
		return resp;
	}
	
	@Override
	public BankBaseResp searchPayinfoPft(String orderNumber) {
		JSONObject queryCondition = new JSONObject();//申请信息
		queryCondition.put("OrdrId", orderNumber);
		JSONObject json = new JSONObject();
		json.put("QueryCondition", queryCondition);
		String id = this.saveBillInfo("bnbOdpsIB006", null, json.toJSONString(), null, "payInfo");
		String rest = PostUtils.post("/bnbOdpsIB006", json);
		this.saveBillInfo(null, id, null, rest, null);
		BankBaseResp resp =null;
		if(null != rest && !"".equals(rest)){
			JSONObject body  = getBody(rest);
			//判断code 是否是000000
			if(null !=body  && "000000".equals(body.get("errorCode"))){
				resp = new BankBaseResp();
				//查询是否有订单状态
				JSONObject object = body.getJSONObject("OrdrInf");
				if(null != object && null != object.get("OrdrSts")){
					switch (object.getString("OrdrSts")) {
					case "OS00"://已取消
						resp.setBillOrderStatus(3);
						break;
					case "OS01"://已完成
						resp.setBillOrderStatus(2);
						break;
					case "OS09"://已发起
						resp.setBillOrderStatus(1);
						break;	
					default:
						break;
					} 
				}
				//查询是否有支付状态
				JSONArray array = body.getJSONArray("PymntInf");
				if(null != array && array.size() > 0){
					JSONObject payinfo = array.getJSONObject(0);
					switch (payinfo.getString("PymnIdSts")) {
					case "PIS00"://已取消
						resp.setBillPayStatus(3);
						break;
					case "PIS01"://已完成
						resp.setBillPayStatus(2);
						break;
					case "PIS02"://已发起
						resp.setBillPayStatus(1);
						break;	
					default:
						break;
					}
				}
			}
		}
		return resp;
	}
	
	@Override
	public BankBaseResp unlockOrderPft(String paymentNo,List<B2bBillInventoryDto> list,Integer type) {
		BankBaseResp resp =new BankBaseResp();
		try {
			if(null != list && list.size()>0){
				JSONObject queryCondition = new JSONObject();//申请信息
				queryCondition.put("PymntOprMd", "2");//0-操作订单 1-操作支付流水 2-操作票据
				queryCondition.put("PymntId", paymentNo);
				queryCondition.put("PymntOprTp", type+"");//0-解锁签收 1-解锁取消
				JSONArray drftList = new JSONArray();
				for(B2bBillInventoryDto i : list){
					JSONObject id = new JSONObject();
					JSONObject drft = new JSONObject();
					id.put("IdNb", i.getBillNumber());
					drft.put("ComrclDrft", id);
					drftList.add(drft);
				}	
				JSONObject json = new JSONObject();
				json.put("OprInf", queryCondition);
				json.put("DrftLst", drftList);
				String id = this.saveBillInfo("bnbOdpsIB003", null, json.toJSONString(), null, "unlock");
				String rest = PostUtils.post("/bnbOdpsIB003", json);
				this.saveBillInfo(null, id, null, rest, null);
				if(null != rest &&  !"".equals(rest)){
					JSONObject body  = getBody(rest);
					//判断code 是否是000000
					if(null != body ){
						if("000000".equals(body.get("errorCode"))){
							resp.setCode(RestCode.CODE_0000.getCode());
						}else{
							resp.setCode(RestCode.CODE_Z000.getCode());
							resp.setMsg(null!=body.get("errorMsg")?body.getString("errorMsg"):"银行返回信息异常");
						}
					}else{
						resp.setCode(RestCode.CODE_Z000.getCode());
						resp.setMsg("银行返回信息异常");
					}
				}
			}
		} catch (Exception e) {
			resp.setCode(RestCode.CODE_Z000.getCode());
			resp.setMsg("调用银行服务异常");
			logger.error("errorUnlockOrderPft",e);
		}
		return resp;
	}
	
	@Override
	public BankBaseResp updateSerialNumberPft(B2bBillOrderDto odto,Integer status) {
		BankBaseResp resp =null;
		try {
			JSONObject updtInf = new JSONObject();
			updtInf.put("PymntId", odto.getSerialNumber());
			updtInf.put("UpdtTp", "UT00");//UT00 状态变更 UT01 信息变更
			updtInf.put("PymnIdSts", status == 1?"PIS01":"PIS00");//PIS00 已取消 PIS01 已完成
			JSONObject json = new JSONObject();
			json.put("UpdtInf", updtInf);
			String id = this.saveBillInfo("bnbOdpsIB002", null, json.toJSONString(), null, "updatePay");
			String rest = PostUtils.post("/bnbOdpsIB002", json);
			this.saveBillInfo(null, id, null, rest, null);
			if(null != rest &&  !"".equals(rest)){
				JSONObject body  = getBody(rest);
				if(null != body ){
					resp = new BankBaseResp();
					//判断code 是否是000000(成功) PYMT000101(状态已变更过)
					if(("000000".equals(body.get("errorCode")) ||
							"PYMT000101".equals(body.get("errorCode")))){
						resp.setCode(RestCode.CODE_0000.getCode());
					}else{
						resp.setCode(RestCode.CODE_Z000.getCode());
						resp.setMsg(null!=body.get("errorMsg")?body.getString("errorMsg"):"银行返回信息异常");
					}
				}
			}
		} catch (Exception e) {
			logger.error("errorUpdateSerialNumberPft",e);
		}
		return resp;
	}

	@Override
	public BankBaseResp updateOrderInfoPft(B2bBillOrderDto odto,Integer status) {
		BankBaseResp resp =null;
		try {
			JSONObject ordrInf = new JSONObject();
			ordrInf.put("OrdrId", odto.getOrderNumber());
			ordrInf.put("OrdrSts", status==1?"OS01":"OS00");//OS00 已取消OS01 已完成
			JSONObject json = new JSONObject();
			json.put("OrdrInf", ordrInf);
			String id = this.saveBillInfo("bnbOdpsIB004", null, json.toJSONString(), null, "completePft");
			String rest = PostUtils.post("/bnbOdpsIB004", json);
			this.saveBillInfo(null, id, null, rest, null);
			if(null != rest &&  !"".equals(rest)){
				JSONObject body  = getBody(rest);
				//判断code 是否是000000
				if(null != body ){
					resp = new BankBaseResp();
					if("000000".equals(body.get("errorCode"))){
						resp.setCode(RestCode.CODE_0000.getCode());
					}else{
						resp.setCode(RestCode.CODE_Z000.getCode());
						resp.setMsg(null!=body.get("errorMsg")?body.getString("errorMsg"):"银行返回信息异常");
					}
				}
			}
		} catch (Exception e) {
			logger.error("errorUpdateOrderInfoPft",e);
		}
		return resp;
	}
	
	
	@Override
	public BankBaseResp payTx(B2bOrderDtoMa order,
			SysCompanyBankcardDto card, B2bBillCustomerDto customer,Integer status) {
		BankBaseResp resp = new BankBaseResp();
		try {
			//订单信息
			JSONObject ordrInf = new JSONObject();
			ordrInf.put("OrdrId", order.getOrderNumber());//订单编号
			ordrInf.put("OrdrOprMd", status+"");//订单编号1-新增2-修改3-取消4完成
			ordrInf.put("OrdrDscrb", "原料采购");//订单描述
			ordrInf.put("OrdrAmt", order.getActualAmount());//订单交易金额
			ordrInf.put("OrdrDueDt", DateUtil.formatYearMonthDay(new Date())+" 23:59:59");//订单最晚有效时间
			if( null != order.getActualAmount() && order.getActualAmount() > 0d){
				ordrInf.put("ReqPattern", "0");//基于订单申请模式
			}else{
				ordrInf.put("ReqPattern", "1");//基于贴现申请模式
			}
			//付款人信息
			JSONObject endrsr = new JSONObject();
			if(null != order.getPurchaser()){
				endrsr.put("EntrprsNm", order.getPurchaser().getPurchaserName());//企业名称
				endrsr.put("CmonId",StringUtils.splitOrganization(order.getPurchaser().getOrganizationCode()));//付款人企业信用代码
			}else{
				endrsr.put("EntrprsNm", "");//企业名称
				endrsr.put("CmonId","");//付款人企业信用代码
			}
			//付款人联系人信息
			JSONObject endrsrContact = new JSONObject();
			endrsrContact.put("Name", customer.getContacts());//名称
			endrsrContact.put("Telephone", customer.getContactsTel());//电话	
			JSONArray contacts = new JSONArray();
			contacts.add(endrsrContact);
			//收款人信息
			JSONObject endrsee = new JSONObject();
			if(null == card || null == card.getBankAccount() || "".equals(card.getBankAccount())){
				endrsee.put("EntrprsNm", "");//企业名称
				endrsee.put("EntrprsUnfdSclCrdtCd", "");//收款人企业信用代码
				endrsee.put("Acct", "");//账户
				endrsee.put("AcctSvcr", "");//开户行行号
			}else{
				endrsee.put("EntrprsNm", order.getSupplier().getSupplierName());//企业名称
				endrsee.put("EntrprsUnfdSclCrdtCd", StringUtils.splitOrganization(order.getSupplier().getOrganizationCode()));//收款人企业信用代码
				endrsee.put("Acct", card.getBankAccount());//账户
				endrsee.put("AcctSvcr", card.getBankNo());//开户行行号
			}
			JSONObject json = new JSONObject();
			json.put("OrdrInf", ordrInf);
			json.put("Endrsr", endrsr);
			json.put("EndrsrContact", contacts);
			json.put("Endrsee", endrsee);
			String hxhCode = null;
			if(status== 1){
				hxhCode = "payTx";
			}else if(status == 4){
				hxhCode = "completeTx";
			}else{
				hxhCode = "cancelTx";
			}
			String id = this.saveBillInfo("order/maintain", null, json.toJSONString(), null, hxhCode);
			String rest = PostUtils.postTx("/order/maintain", json);
			this.saveBillInfo(null, id, null, rest, null);
			if(null != rest &&  !"".equals(rest)){
				JSONObject body  = getBody(rest);
				//判断code 是否是000000
				if(null != body){
					if("000000".equals(body.get("errorCode"))){
						resp.setCode(RestCode.CODE_0000.getCode());
						resp.setSerialNumber(body.getJSONObject("RplyInf").getString("PymntId"));
					}else{
						resp.setCode(RestCode.CODE_Z000.getCode());
						resp.setMsg(null!=body.get("errorMsg")?body.getString("errorMsg"):"银行返回信息异常");
					}
				}else{
					resp.setCode(RestCode.CODE_Z000.getCode());
					resp.setMsg("银行返回信息异常");
				}
			}else{
				resp.setCode(RestCode.CODE_Z000.getCode());
				resp.setMsg("银行返回信息异常");
			}
		} catch (Exception e) {
			logger.error("errorPayTx",e);
			resp.setCode(RestCode.CODE_Z000.getCode());
			resp.setMsg("调用银行服务异常");
		}
		return resp;
	}

	@Override
	public BankBaseResp searchOrderTx(String orderNumber) {
		BankBaseResp resp =new BankBaseResp();
		try {
			JSONObject queryCondition = new JSONObject();
			queryCondition.put("OrdrId", orderNumber);//订单号
			JSONObject json = new JSONObject();
			json.put("QueryCondition", queryCondition);
			String id = this.saveBillInfo("order/queryinfo", null, json.toJSONString(), null, "orderTx");
			String rest = PostUtils.postTx("/order/queryinfo", json);
			this.saveBillInfo(null, id, null, rest, null);
			if(null != rest &&  !"".equals(rest)){
				JSONObject body  = getBody(rest);
				//判断code 是否是000000
				if(null != body && "000000".equals(body.get("errorCode"))){
					resp.setCode(RestCode.CODE_0000.getCode());
					//1-已发起 2-已完成 3-已取消 4-已终结
					resp.setBillPayStatus(null!=body.get("OrdrInfo")?body.getJSONObject("OrdrInfo").getInteger("OrdrSts"):null);
					if(null != resp.getBillPayStatus() && 4 == resp.getBillPayStatus()){
						resp.setBillPayStatus(2);
					}
					resp.setInventoryList(this.searchOrderDetailsTx(orderNumber).getInventoryList());
				}
			}
		} catch (Exception e) {
			logger.error("errorOrderTx",e);
		}
		return resp;
	}

	@Override
	public BankBaseResp searchOrderDetailsTx(String orderNumber) {
		BankBaseResp resp =new BankBaseResp();
		try {
			JSONObject queryCondition = new JSONObject();
			queryCondition.put("OrdrId", orderNumber);//订单号
			JSONObject json = new JSONObject();
			json.put("QueryCondition", queryCondition);
			String id = this.saveBillInfo("order/queryinfodetail", null, json.toJSONString(), null, "txDetails");
			String rest = PostUtils.postTx("/order/queryinfodetail", json);
			this.saveBillInfo(null, id, null, rest, null);
			if(null != rest &&  !"".equals(rest)){
				JSONObject body  = getBody(rest);
				//判断code 是否是000000
				if(null != body && "000000".equals(body.get("errorCode"))){
					resp.setCode(RestCode.CODE_0000.getCode());
					resp.setInventoryList(getInventoryListTx(null != body.get("DrftInf")?body.getJSONArray("DrftInf"):null));
				}
			}
		} catch (Exception e) {
			logger.error("errorTxDetails:"+orderNumber,e);
		}
		return resp;
	}
	@Override
	public BankBaseResp searchAmountTx() {
		JSONObject queryCondition = new JSONObject();
		queryCondition.put("CmonId", PropertyConfig.getProperty("nb_shopno"));//平台编号
		JSONObject json = new JSONObject();
		json.put("QueryCondition", queryCondition);
		String id = this.saveBillInfo("platform/queryquota", null, json.toJSONString(), null, "txAmount");
		String rest = PostUtils.postTx("/platform/queryquota", json);
		this.saveBillInfo(null, id, null, rest, null);
		BankBaseResp resp =null;
		if(null != rest &&  !"".equals(rest)){
			JSONObject body  = getBody(rest);
			//判断code 是否是000000
			if(null != body && "000000".equals(body.get("errorCode")) && null !=body.getJSONObject("QuotaInf")){
				resp = new BankBaseResp();
				resp.setCode(RestCode.CODE_0000.getCode());
				JSONObject info = body.getJSONObject("QuotaInf");
				//QuotaSts:1-生效 2-失效
				if("1".equals(info.getString("QuotaSts"))){
					B2bBillGoodsDto dto = new B2bBillGoodsDto();
					dto.setPlatformAmount(body.getJSONObject("QuotaInf").getDouble("TotalQuota"));
					dto.setPlatformUseAmount(body.getJSONObject("QuotaInf").getDouble("UsedQuota"));
					resp.setB2bBillGoodsDto(dto);
				}
			}
		}
		return resp;
	}

	@Override
	public BankBaseResp searchOrderFlowTx(String payerAmount,
			String payerBankNo, String payeeAmount, String payeeBankNo) {
		// TODO Auto-generated method stub
		return null;
	}


	private List<B2bBillInventoryDto> getInventoryListPft(JSONArray array) {
		List<B2bBillInventoryDto> list = null;
		if(null != array && array.size()>0){
			list = new ArrayList<B2bBillInventoryDto>();
			for (int i = 0; i < array.size(); i++) {
				B2bBillInventoryDto invent = new B2bBillInventoryDto();
				invent.setBillNumber(array.getJSONObject(i).getString("IdNb"));
				switch (array.getJSONObject(i).getString("PymnSts")) {
				case "1111"://已完成
					invent.setPayStatus(3);
					break;
				case "0206"://已取消
					invent.setPayStatus(-1);
					break;
				case "0201"://已取消待确认
					invent.setPayStatus(-2);
					break;
				case "0101"://预锁定
					invent.setPayStatus(0);
					break;
				case "0106"://预锁定待确认
					invent.setPayStatus(2);
					break;
				case "0501"://锁定待确认
					invent.setPayStatus(2);
					break;
				case "0506"://锁定
					invent.setPayStatus(1);
					break;
				case "0306"://解锁（接收人可签收）
					invent.setPayStatus(2);
					break;
				case "0301"://解锁（接收人可签收）待确认
					invent.setPayStatus(2);
					break;
				case "0406"://解锁（发起人可撤销）
					invent.setPayStatus(-2);
					break;
				case "0401"://解锁（发起人可撤销）待确认
					invent.setPayStatus(-2);
					break;
				default:
					break;
				}
				if(null == invent.getPayStatus()){
					continue;
				}
				invent.setStatus(invent.getPayStatus());
				invent.setTransfer("EM00".equals(array.getJSONObject(i).getString("BanEndrsmtMk"))?0:1);//是否可以转让
				//票据要素
				if(null != array.getJSONObject(i).get("DrftDtl")){
					JSONObject json = array.getJSONObject(i).getJSONObject("DrftDtl");
					invent.setBillCode(json.getString("Tp"));
					invent.setAmount(json.getDouble("IsseAmt"));
					invent.setStartDate(json.getDate("IsseDt"));
					invent.setEndDate(json.getDate("DueDt"));
					invent.setDrawer(json.getString("DrwrNm"));
					invent.setDrawerCode(json.getString("DrwrCmonId"));
					invent.setPayee(json.getString("PyeeNm"));
					invent.setPayeeCode(json.getString("PyeeCmonId"));
					invent.setAcceptor(json.getString("AccptrNm"));
					invent.setAcceptorCode(json.getString("AccptrCmonId"));
					invent.setAcceptorAccount(json.getString("AccptrAcct"));
					invent.setAcceptorBankNo(json.getString("AccptrAcctSvcr"));
				}
				list.add(invent);
			}
		}
		return list;
	}
	
	private List<B2bBillInventoryDto> getInventoryListTx(JSONArray array) {
		JSONObject json = new  JSONObject();
		List<B2bBillInventoryDto> list = null;
		if(null != array && array.size()>0){
			list = new ArrayList<B2bBillInventoryDto>();
			for (int j = 0; j < array.size(); j++) {
				json = array.getJSONObject(j);
				B2bBillInventoryDto invent = new B2bBillInventoryDto();
				invent.setBillNumber(json.getString("IdNb"));//票据编号
				invent.setAmount(json.getDouble("IsseAmt"));//票据金额
				invent.setStartDate(json.getDate("IsseDt"));//出票日期
				invent.setEndDate(json.getDate("DueDt"));//票据到期日
				invent.setAcceptorBankNo(json.getString("AcctSvcr"));//承兑人开户行号
				invent.setDiscountRate(json.getDouble("DscntRate"));//贴现利率
				invent.setDiscountInterest(json.getDouble("DscntInt"));//贴现利息
				invent.setDiscountAmount(json.getDouble("DscntRAmt"));//贴现实付金额
				if(null == json.get("AFFM")){
					invent.setPayStatus(0);
				}else{
					switch (json.getString("AFFM")) {
					case "0"://已登记
						invent.setPayStatus(0);
						break;
					case "1"://申请前置拒绝
						invent.setPayStatus(-1);
						break;
					case "2"://已申请
						invent.setPayStatus(0);
						break;
					case "3"://待签收
						invent.setPayStatus(0);
						break;
					case "5"://已签收
						invent.setPayStatus(3);
						break;
					case "6"://票交系统拒绝
						invent.setPayStatus(-1);
						break;
					case "7"://对手拒绝
						invent.setPayStatus(-1);
						break;
					case "9"://申请发送前置超时
						invent.setPayStatus(-1);
						break;
					case "A"://撤销已申请
						invent.setPayStatus(0);
						break;
					case "B"://撤销成功
						invent.setPayStatus(-1);
						break;
					case "C"://撤销申请票交拒绝
						invent.setPayStatus(-1);
						break;
					case "E"://清分失败
						invent.setPayStatus(-1);
						break;
					default:
						invent.setPayStatus(0);//为空的
						break;
					}
				}
				invent.setStatus(invent.getPayStatus());
				list.add(invent);
			}
		}
		return list;
	}
	 
	public String saveBillInfo(String code, String id, String requestValue,
			 String responseValue,String hxhCode) {
		BillInfo bank = new BillInfo();
		if (id == null) {
			String infoId = KeyUtils.getUUID();
			bank.setId(infoId);
			bank.setCode(code);
			bank.setRequestValue(requestValue);
			bank.setInsertTime(DateUtil.formatDateAndTime(new Date()));
			bank.setHxhCode(hxhCode);
			mongoTemplate.save(bank);
			return infoId;
		} else {
			Update update = new Update();
			update.set("responseValue", responseValue);
			mongoTemplate.upsert(new Query(Criteria.where("id").is(id)),
					update, BillInfo.class);
			return id;
		}

	}
}
