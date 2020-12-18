package cn.cf.service.common.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import cn.cf.common.RestCode;
import cn.cf.constant.BillType;
import cn.cf.dto.B2bBillCusgoodsDto;
import cn.cf.dto.B2bBillCusgoodsDtoEx;
import cn.cf.dto.B2bBillCustomerDto;
import cn.cf.dto.B2bBillGoodsDto;
import cn.cf.dto.B2bBillInventoryDto;
import cn.cf.dto.B2bBillOrderDto;
import cn.cf.dto.B2bBillSigncardDto;
import cn.cf.dto.B2bBillSigningDto;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.dto.B2bTokenDto;
import cn.cf.dto.SysCompanyBankcardDto;
import cn.cf.entity.B2bOrderDtoMa;
import cn.cf.entity.ContractSyncToMongo;
import cn.cf.entity.PurchaserInfo;
import cn.cf.entry.BankBaseResp;
import cn.cf.entry.UnsynErpOrder;
import cn.cf.service.bill.BillCusGoodsService;
import cn.cf.service.bill.BillCustomerService;
import cn.cf.service.bill.BillGoodsService;
import cn.cf.service.bill.BillInventoryService;
import cn.cf.service.bill.BillNingboService;
import cn.cf.service.bill.BillOrderService;
import cn.cf.service.bill.BillSigncardService;
import cn.cf.service.bill.BillSingingService;
import cn.cf.service.common.HuaxianhuiBillService;
import cn.cf.service.common.HuaxianhuiService;
import cn.cf.service.foreign.B2bMemberService;
import cn.cf.service.foreign.B2bTokenService;
import cn.cf.service.foreign.ForeignCompanyService;
import cn.cf.service.foreign.ForeignOrderService;
import cn.cf.service.platform.B2bCompanyBankcardService;
import cn.cf.util.ArithUtil;
import cn.cf.util.DateUtil;
import cn.cf.util.KeyUtils;

@Service
public class HuaxianhuiBillServiceImpl implements HuaxianhuiBillService {
	
	@Autowired
	private ForeignOrderService foreignOrderService;
	
	@Autowired
	private ForeignCompanyService companyService;
	
	@Autowired
	private B2bCompanyBankcardService companyBankcardService;
	
	@Autowired
	private BillNingboService billNingboService;
	
	@Autowired
	private B2bTokenService b2bTokenService;
	
	@Autowired
	private BillGoodsService billGoodsService;
	
	@Autowired
	private BillCusGoodsService billCusGoodsService;
	
	@Autowired
	private HuaxianhuiService huaxianhuiService;
	
	@Autowired
	private BillOrderService billOrderService;
	
	@Autowired
	private BillCustomerService billCustomerService;
	
	@Autowired
	private B2bMemberService b2bMemberService;
	
	@Autowired
	private BillSingingService billSingingService;
	
	@Autowired
	private BillSigncardService billSigncardService;
	
	@Autowired
	private BillInventoryService billInventoryService;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
 
	@Override
	public String bindBill(String companyPk,String companyName,String organizationCode,String account,String bankNo,String bindType) {
		String rest = RestCode.CODE_0000.toJson();
		List<B2bBillSigncardDto> cards = new ArrayList<B2bBillSigncardDto>();
		B2bBillSigncardDto card = new B2bBillSigncardDto();
		card.setBankAccount(account);
		card.setBankNo(bankNo);
		cards.add(card);
		BankBaseResp resp =	billNingboService.bindBillPft(companyPk, companyName, organizationCode,cards,bindType);
		//根据返回进行回调
		if(null != resp.getCode() && "0000".equals(resp.getCode())){
			if("1".equals(bindType)){
				//绑定
//				huaxianhuiService.customerBindPftAccount(companyPk, cards);
				billCusGoodsService.updateBindStatus(companyPk, BillType.PFT, 1);
				//解绑
			}else if("2".equals(bindType)){
//				huaxianhuiService.unBindPftAccount(companyPk, cards);
				billCusGoodsService.updateBindStatus(companyPk, BillType.PFT, 1);
			}
		}else{
			RestCode.CODE_Z000.setMsg(resp.getMsg());
			rest = RestCode.CODE_Z000.toJson();
		}
		return rest;
	}

	@Override
	public String searchBillPft(B2bBillCusgoodsDtoEx dto) {
		//调用查询接口
		if(null != dto && null != dto.getCompanyPk()){
			B2bCompanyDto company = companyService.getCompany(dto.getCompanyPk());
			BankBaseResp resp = null;
			if(null !=  company){
				resp = billNingboService.searchBillPft(company.getOrganizationCode(),null);
			}
			//根据返回进行回调
			if(null != resp && null!=resp.getBillcardList() && resp.getBillcardList().size()>0){
				huaxianhuiService.customerSearchPftAccount(dto, resp.getBillcardList());
			}
		}
		return RestCode.CODE_0000.toJson();
	}
	
	@Override
	public String searchBillSignPft(B2bBillSigningDto sign) {
		//调用查询接口
		BankBaseResp resp =	billNingboService.searchBillPft(sign.getOrganizationCode(),2);
		//根据返回进行回调
		if(null != resp && null!=resp.getBillcardList() && resp.getBillcardList().size()>0){
			huaxianhuiService.supplierSearchPftAccount(sign, resp.getBillcardList());
		}
				return RestCode.CODE_0000.toJson();
	}

	@Override
	public String payOrder(String orderNumber, String contractNo,
			 Integer paymentType, String paymentName,
			String billGoodsPk) {

		String rest = null;
		Map<String, Object> map = new HashMap<String,Object>();
		boolean flag = true;
		B2bOrderDtoMa odto = null;
		SysCompanyBankcardDto card = null;
		B2bBillCusgoodsDto cgdto = null;
		B2bBillGoodsDto bgdto = null;
		B2bBillCustomerDto customer = null;
		Integer payType = 1;
		try {
			if(!"".equals(orderNumber)){
				odto = foreignOrderService.getOrder(orderNumber);
			}
			if(!"".equals(contractNo)){
				odto = foreignOrderService.getContract(contractNo);
			}
		} catch (Exception e) {
			logger.error("errorOrder",e);
		}
		//验证订单
		if(null == odto){
			flag =false;
			rest = RestCode.CODE_O001.toJson();
		}
		if(flag && odto.getOrderStatus() >=2){
			flag =false;
			rest = RestCode.CODE_O002.toJson();
		}
		//订单已取消
		if(flag && -1 == odto.getOrderStatus()){
			flag =false;
			rest = RestCode.CODE_O010.toJson();
		}
		//验证客户时候申请通过
		if(flag){
			 customer = billCustomerService.getByCompanyPk(odto.getPurchaserPk());
			 if(null == customer || null == customer.getStatus() || customer.getStatus() != 2){
				 flag =false;
				 rest = RestCode.CODE_B003.toJson();
			 }
		}
		//支付产品不存在
		if(flag){
			//非见票支付
			if(billGoodsPk.contains(BillType.PFT_T)){
				payType = 0;
				billGoodsPk = billGoodsPk.split(BillType.PFT_T)[0];
			}
			cgdto = billCusGoodsService.getByPk(billGoodsPk);
			if(null == cgdto){
				flag =false;
				rest = RestCode.CODE_B001.toJson();
			}else{
				bgdto = billGoodsService.getByPk(cgdto.getGoodsPk());
				if(null == bgdto){
					flag =false;
					rest = RestCode.CODE_B001.toJson();
				}
			}
		}
		if(flag && BillType.PFT.equals(bgdto.getShotName()) 
				&& (null ==cgdto.getBindStatus() || cgdto.getBindStatus() !=2)){
			flag =false;
			rest = RestCode.CODE_B002.toJson();
		}
		//贴现验证额度是否可用
		if(flag && BillType.TX.equals(bgdto.getShotName())){
			if(null == bgdto.getPlatformAmount() || bgdto.getPlatformAmount() == 0d){
				flag =false;
				rest = RestCode.CODE_C003.toJson();
			}
			if(odto.getActualAmount() >ArithUtil.sub(null==cgdto.getPlatformAmount()?0d:
				cgdto.getPlatformAmount(), null==cgdto.getUseAmount()?0d:cgdto.getUseAmount())){
				flag =false;
				rest = RestCode.CODE_C003.toJson();
			}
			
		}
		if(flag){
			//验证供应商银行卡号
			if(BillType.TX.equals(bgdto.getShotName())){
				//贴现的查询供应商所有绑定的卡号
				card = companyBankcardService.getCompanyBankCard(odto.getSupplierPk(),bgdto.getBankPk());
			}else{
				//票付通查询供应商签约的卡号
				B2bBillSigncardDto signcard = billSigncardService.getCompanyBankCard(odto.getSupplierPk(),bgdto.getBankPk());
				if(null != signcard){
					card = new SysCompanyBankcardDto();
					card.setBankAccount(signcard.getBankAccount());
					card.setBankName(signcard.getBankName());
					card.setBankNo(signcard.getBankNo());
				}
			}
			if(null == card){
				flag =false;
				rest = RestCode.CODE_C001.toJson();
			}
		}
		if(flag){
				String mesgid = KeyUtils.getUUID();
				//调用不同的银行返回结果
				BankBaseResp resp = null;
				switch (bgdto.getShotName()) {
				case BillType.PFT:
					resp = billNingboService.payPft(odto, cgdto, card, bgdto.getType(),payType,mesgid);
					break;
				case BillType.TX:
					resp = billNingboService.payTx(odto, card, customer,1);
					break;	
				default:
					break;
				}
				//成功回调hxh业务
				if(null != resp && resp.getCode().equals(RestCode.CODE_0000.getCode())){
					//更新订单 交易记录等信息
					huaxianhuiService.updateBackBillOrder(odto, contractNo, paymentName, paymentType, resp.getSerialNumber(),resp.getHtml(), card, cgdto,mesgid,payType);
					switch (bgdto.getShotName()) {
					case BillType.PFT:
						map.put("html", resp.getHtml());
						break;
					case BillType.TX:
						map.put("gateway", bgdto.getGateway());
						break;
					default:
						break;
					}
					rest = RestCode.CODE_0000.toJson(map);
					// 订单推送给erp
					sendToCrm(odto,contractNo);
				}else{
					if(null ==resp || null == resp.getMsg()){
						rest = RestCode.CODE_C0011.toJson();
					}else{
						RestCode code = RestCode.CODE_Z000;
						code.setMsg(resp.getMsg());
						rest = code.toJson();
					}
				}
		}
		return rest;
	
	}
	
	private void sendToCrm(B2bOrderDtoMa odto,String contractNo) {
		B2bTokenDto token =  b2bTokenService.getByCompanyPk(odto.getStorePk());
		if(null != token){
			//合同订单推送给erp 合同来源为4的不推送给erp
			if(null != contractNo && !"".equals(contractNo) && 4 != odto.getSource()){
				try {	
					ContractSyncToMongo syncToMongo = new ContractSyncToMongo();
					syncToMongo.setId(contractNo);
					syncToMongo.setIsPush(1);//
					syncToMongo.setStorePk(odto.getStorePk());
					syncToMongo.setInsertTime(DateUtil.formatYearMonthDay(new Date()));
					syncToMongo.setContractNumber(contractNo);
					syncToMongo.setDetail("");
					mongoTemplate.save(syncToMongo);
				} catch (Exception e) {
					// 如果失败存数据做定时任务推送
					logger.error("errorErp--------orderNumber:" + contractNo, e);
					UnsynErpOrder erpOrder = new UnsynErpOrder();
					erpOrder.setOrderNumber(contractNo);
					erpOrder.setInsertTime(DateUtil.formatDateAndTime(new Date()));
					erpOrder.setStorePk(odto.getStorePk());
					erpOrder.setType(2);
					mongoTemplate.save(erpOrder);
				}
			}
		}
	}

	@Override
	public String searchBillInfo(String orderNumber) {
		B2bBillOrderDto order = billOrderService.getBillOrder(orderNumber);
		if(null != order && (order.getStatus() ==1 || order.getStatus() ==4)){
			BankBaseResp resp = null;
			switch (order.getGoodsShotName()) {
			case BillType.PFT:
				 resp =	billNingboService.searchOrderPft(orderNumber);
				break;
			case BillType.PFT_1:
				 resp =	billNingboService.searchOrderPft(orderNumber);
				break;
			case BillType.TX:
				 resp =	billNingboService.searchOrderTx(orderNumber);
				break;
			default:
				break;
			}
			//根据返回进行回调
			if(null != resp && ((null != resp.getSerialNumber() && !"".equals(resp.getSerialNumber()))
					|| order.getGoodsShotName().equals(BillType.TX))){
					switch (order.getGoodsShotName()) {
					case BillType.PFT://见证
						pftOne(orderNumber,resp.getSerialNumber(), order, resp);
						break;
					case BillType.PFT_1://非见证
						pftOne(orderNumber,resp.getSerialNumber(), order, resp);
						break;
					case BillType.TX://贴现
						txOne(orderNumber, order, resp);
						break;
					default:
						break;
					}
					huaxianhuiService.updateBackBillOrderInventory(order, resp.getInventoryList());
			}
		}
		return RestCode.CODE_0000.toJson();
	}
	
	
	/**
	 * 票付通业务
	 * @param orderNumber
	 * @param order
	 * @param resp
	 */
	private void pftOne(String orderNumber,String paymentNo, B2bBillOrderDto order,
			BankBaseResp resp) {
		//默认0不执行   1解锁票据  2完成票据订单
		Integer status = 0;
		//0预锁定 1锁定  2解锁 3已完成 -1已取消 -2 已取消待确认
		List<B2bBillInventoryDto> list = null;
		if(null != resp.getInventoryList() && resp.getInventoryList().size()>0){
			for(B2bBillInventoryDto i : resp.getInventoryList()){
				if(null == i.getPayStatus()){
					status =0;
					break;
				}
				if(i.getPayStatus() ==0 || i.getPayStatus() == -2){
					status =0;
					break;
				}
				if(i.getPayStatus() == 1){
					if(null == list){
						list = new ArrayList<B2bBillInventoryDto>();
					}
					list.add(i);
					status = 1;
				}
				if(((i.getPayStatus() ==3 || i.getPayStatus() ==-1)) && status != 1){
					status = 2;
				}
				if(status == 2 && i.getPayStatus() ==2){
					status = 0;
				}
			}
		}
		order.setSerialNumber(resp.getSerialNumber());
		order.setStatus(1);
			//如果票据全部都是"锁定"状态(status:1)则调票据解锁接口
			if(status == 1){
				billNingboService.unlockOrderPft(paymentNo,list,0);
			}
			//如果票据都是已完成则调用支付流水更新和订单附加信息上传接口
			if(status == 2){
				//支付流水更新
				billNingboService.updateSerialNumberPft(order,1);
				//订单附加信息上传
				billNingboService.updateOrderInfoPft(order,1);
				order.setStatus(5);//成功待处理中
			}
	}
	
	
	/**
	 * 贴现业务
	 * @param orderNumber
	 * @param order
	 * @param resp
	 */
	private void txOne(String orderNumber, B2bBillOrderDto order,
			BankBaseResp resp) {
		if(null != resp && null !=resp.getBillPayStatus()){
			order.setStatus(resp.getBillPayStatus());
			if(resp.getBillPayStatus() == 1 && null !=resp.getInventoryList() && resp.getInventoryList().size()>0){
				Boolean completeFalg = false;
				for (int i = 0; i < resp.getInventoryList().size(); i++) {
					if(resp.getInventoryList().get(i).getStatus() == 0){
						break;
					}
					if(resp.getInventoryList().get(i).getStatus() == 3){
						completeFalg = true;
					}
					//如果票据全部为已完成状态 调用发完成申请
					if(completeFalg && i == (resp.getInventoryList().size()-1)){
						B2bOrderDtoMa om = null;
						if(order.getType()==1){
							om = foreignOrderService.getOrder(orderNumber);
						}else if(order.getType()==2){
							om = foreignOrderService.getContract(orderNumber);
						}else{
							om = new B2bOrderDtoMa();
							om.setOrderNumber(orderNumber);
							om.setActualAmount(0d);
						}
						billNingboService.payTx(om, new SysCompanyBankcardDto(), new B2bBillCustomerDto(), 4);
					}
				}
				
			}
		}
	}
	

	@Override
	public Map<String, Object> billPayCancel(B2bBillOrderDto order) {
		Map<String,Object> map = new HashMap<String,Object>();
		if(null != order && null != order.getGoodsShotName()){
			BankBaseResp resp = null;
			switch (order.getGoodsShotName()) {
			case BillType.PFT_1:
				resp = cancelPft(order, map);
				break;
			case BillType.PFT:
				resp = cancelPft(order, map);
				break;
			case BillType.TX:
				resp = cancelTx(order, map);
				break;
			default:
				break;
			}
			if(null != map.get("code") && RestCode.CODE_0000.getCode().equals(map.get("code").toString())){
				huaxianhuiService.updateBackBillOrderInventory(order, resp.getInventoryList());
			}
		}
		return map;
	}

	private BankBaseResp cancelPft(B2bBillOrderDto order, Map<String, Object> map) {
		//调用查询订单接口
		BankBaseResp resp =	billNingboService.searchOrderPft(order.getOrderNumber());
		//根据返回进行回调
		if(null != resp && null != resp.getSerialNumber() && !"".equals(resp.getSerialNumber())){
			order.setSerialNumber(resp.getSerialNumber());
			//根据获取到的所有票据信息状态进行相关业务操作
			if(null != resp.getInventoryList() && resp.getInventoryList().size()>0){
				//更新票据信息
//				huaxianhuiService.updateBackBillOrderInventory(order, resp.getInventoryList());
				//默认0不执行   -1取消票据  1解锁取消
				Integer status = -1;
				//0预锁定 1锁定   2处理中  3已完成 -1已取消 -2取消处理中
				List<B2bBillInventoryDto> list= new ArrayList<B2bBillInventoryDto>();
				for(B2bBillInventoryDto i : resp.getInventoryList()){
					if(null == i.getPayStatus()){
						status =0;
						break;
					}
					if(i.getPayStatus() == 2 || i.getPayStatus() == 3){
						status =0;
						break;
					}
					if(i.getPayStatus() == 0 || i.getPayStatus() == 1){
						status =1;
						list.add(i);
					}
				}
				//如果票据都是已完成则调用支付流水更新和订单附加信息上传接口
				if(status == -1){
					billNingboService.updateSerialNumberPft(order,2);
					billNingboService.updateOrderInfoPft(order,2);
					order.setStatus(-1);
					map.put("code", RestCode.CODE_0000.getCode());
				//票据解锁取消
				}else if(status == 1){
					BankBaseResp cancellock = billNingboService.unlockOrderPft(resp.getSerialNumber(),list,1);
					if(null != cancellock && RestCode.CODE_0000.getCode().equals(cancellock.getCode())){
						order.setStatus(-1);
						map.put("code", RestCode.CODE_0000.getCode());
					}else{
						map.put("code", RestCode.CODE_Z000.getCode());
						map.put("msg", cancellock.getMsg());
					}
				//无法取消
				}else{
					map.put("code", RestCode.CODE_Z000.getCode());
					map.put("msg", "当前票据状态无法取消");
				}
			//可以取消	
			}else{
				billNingboService.updateSerialNumberPft(order,2);
				billNingboService.updateOrderInfoPft(order,2);
				order.setStatus(-1);
				map.put("code", RestCode.CODE_0000.getCode());
			}
		//可以取消	
		}else{
			order.setStatus(3);
			map.put("code", RestCode.CODE_0000.getCode());
		}
		return resp;
	}
	
	
	private BankBaseResp cancelTx(B2bBillOrderDto order, Map<String, Object> map) {
		B2bBillCustomerDto customer = billCustomerService.getByCompanyPk(order.getPurchaserPk());
		SysCompanyBankcardDto card = new SysCompanyBankcardDto();
		B2bOrderDtoMa om = null;
		BankBaseResp resp = null;
		if(null != order.getType() && order.getType() == 1){
			om = foreignOrderService.getOrder(order.getOrderNumber());
		}else if(null != order.getType() && order.getType() == 2){
			om = foreignOrderService.getContract(order.getOrderNumber());
		}else if(null != order.getType() && order.getType() == 3){
			om = new B2bOrderDtoMa();
			om.setOrderNumber(order.getOrderNumber());
			om.setActualAmount(0d);
		}
		if(null != om){
			resp = billNingboService.payTx(om, card, customer, 3);
			if(RestCode.CODE_0000.getCode().equals(resp.getCode())){
				order.setStatus(3);
				map.put("code", resp.getCode());
				BankBaseResp nResp = billNingboService.searchOrderDetailsTx(order.getOrderNumber());
				if(null != nResp && null != nResp.getInventoryList()){
					resp.setInventoryList(nResp.getInventoryList());
				}
			}else{
				map.put("code", resp.getCode());
				map.put("msg", resp.getMsg());
			}
		}else{
			map.put("code", RestCode.CODE_Z000.getCode());
			map.put("msg", "订单不存在");
		}
		return resp;
	}
	@Override
	public String searchAmountTx() {
		BankBaseResp resp = billNingboService.searchAmountTx();
		if(null != resp && null != resp.getB2bBillGoodsDto()){
			huaxianhuiService.updateAmount(resp.getB2bBillGoodsDto());
		}
		return RestCode.CODE_0000.toJson();
	}

	@Override
	public String billSinging(String companyPk) {
		String rest = RestCode.CODE_S999.toJson();
		B2bBillSigningDto sing = billSingingService.getByCompanyPk(companyPk);
		if(null != sing){
			//未签约账户发起签约
			List<B2bBillSigncardDto> cards = billSigncardService.getByCompanyAndCard(companyPk,0, null, null);
//			Map<String, Object> map=new HashMap<String, Object>();
//			map.put("companyPk", companyPk);
//			List<B2bMemberDto> members = b2bMemberService.searchList(map);
			BankBaseResp resp =billNingboService.bindBillPft(companyPk, sing.getCompanyName(), sing.getOrganizationCode(), cards,"1");
			if(null != resp && RestCode.CODE_0000.getCode().equals(resp.getCode())){
				huaxianhuiService.supplierBindPftAccount(sing, cards);
				rest = RestCode.CODE_0000.toJson();
			}else{
				RestCode code = RestCode.CODE_Z000;
				code.setMsg(null!=resp?resp.getMsg():"银行返回数据异常");
				rest = code.toJson();
			}
		}else{
			rest = RestCode.CODE_A001.toJson();
		}
		return rest;
	}

	@Override
	public String searchPayInfoPft(String orderNumber) {
		B2bBillOrderDto order = billOrderService.getBillOrder(orderNumber);
		if(null != order){
			//成功处理中的操作
			if(null != order.getStatus() && 5 == order.getStatus()){
				BankBaseResp resp = billNingboService.searchPayinfoPft(orderNumber);
				if(null != resp && null != resp.getBillPayStatus() && null != resp.getBillOrderStatus()){
					//状态全部完成操作票据订单状态
					if(resp.getBillPayStatus() == 2 && resp.getBillOrderStatus() == 2){
						order.setStatus(2);
						huaxianhuiService.updateBackBillOrderInventory(order, billInventoryService.searchByMap(orderNumber, null));
					//未完成发送完成操作
					}else{
						if(resp.getBillPayStatus() == 1){
							billNingboService.updateSerialNumberPft(order, 1);
						}
						if(resp.getBillOrderStatus() == 1){
							billNingboService.updateOrderInfoPft(order, 1);
						}
					}
				}
			}
			//失败处理中的操作
			if(null != order.getStatus() && -1 == order.getStatus()){
				BankBaseResp resp = billNingboService.searchPayinfoPft(orderNumber);
				if(null != resp && null != resp.getBillPayStatus() && null != resp.getBillOrderStatus()){
					//状态全部取消操作票据订单状态
					if(resp.getBillPayStatus() == 3 && resp.getBillOrderStatus() == 3){
						order.setStatus(3);
						huaxianhuiService.updateBackBillOrderInventory(order, null);
					//未完成发送取消操作
					}else{
						if(resp.getBillPayStatus() == 1){
							billNingboService.updateSerialNumberPft(order, 0);
						}
						if(resp.getBillOrderStatus() == 1){
							billNingboService.updateOrderInfoPft(order, 0);
						}
					}
				}
			}
			//锁定中的操作
			if(null != order.getStatus() && 4 == order.getStatus()){
				BankBaseResp resp = billNingboService.searchOrderPft(orderNumber);
				if(null != resp && null != resp.getSerialNumber()){
					order.setSerialNumber(resp.getSerialNumber());
					huaxianhuiService.updateBackBillOrderInventory(order, resp.getInventoryList());
				}
			}
		}
		return RestCode.CODE_0000.toJson();
	}

	@Override
	public String acceptTx(String companyPk, String billGoodsPk) {
		Boolean flag = true;
		String rest = null;
		B2bBillCustomerDto customer = null;
		B2bBillCusgoodsDto goods = null;
		B2bBillGoodsDto bgdto =null;
		B2bCompanyDto company = companyService.getCompany(companyPk);
		if(null == company){
			flag = false;
			rest = RestCode.CODE_A001.toJson();
		}
		if(flag){
			customer = billCustomerService.getByCompanyPk(companyPk);
			if(null == customer){
				flag = false;
				rest = RestCode.CODE_B003.toJson();
			}
		}
		if(flag){
			goods = billCusGoodsService.getByPk(billGoodsPk);
			if(null == goods || null == goods.getCompanyPk() || !goods.getCompanyPk().equals(companyPk)){
				flag = false;
				rest = RestCode.CODE_B003.toJson();
			}else if(0d >=ArithUtil.sub(null==goods.getPlatformAmount()?0d:
				goods.getPlatformAmount(), null==goods.getUseAmount()?0d:goods.getUseAmount())){
				flag = false;
				rest = RestCode.CODE_C003.toJson();
			}
		}
		if(flag){
			bgdto = billGoodsService.getByPk(goods.getGoodsPk());
			if(null == bgdto){
				flag =false;
				rest = RestCode.CODE_B001.toJson();
			}
		}
		if(flag){
			B2bOrderDtoMa om = new B2bOrderDtoMa();
			om.setActualAmount(0d);
			om.setOrderNumber(BillType.TX_NO+KeyUtils.getOrderNumber());
			PurchaserInfo p = new PurchaserInfo();
			om.setPurchaserPk(companyPk);
			p.setPurchaserName(company.getName());
			p.setOrganizationCode(company.getOrganizationCode());
			om.setPurchaser(p);
			BankBaseResp resp = billNingboService.payTx(om, null, customer, 1);
			if(null != resp && resp.getCode().equals(RestCode.CODE_0000.getCode())){
				huaxianhuiService.updateBackTx(om, goods, resp.getSerialNumber());
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("gateway", bgdto.getGateway());
				rest = RestCode.CODE_0000.toJson(map);
			}else{
				if(null ==resp || null == resp.getMsg()){
					rest = RestCode.CODE_C0011.toJson();
				}else{
					RestCode code = RestCode.CODE_Z000;
					code.setMsg(resp.getMsg());
					rest = code.toJson();
				}
			}
		}
		return rest;
	}


}
