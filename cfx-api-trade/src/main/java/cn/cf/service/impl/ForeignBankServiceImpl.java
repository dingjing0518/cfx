package cn.cf.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import cn.cf.common.EncodeUtil;
import cn.cf.dto.B2bBillInventoryDto;
import cn.cf.dto.B2bBillOrderDto;
import cn.cf.dto.B2bEconomicsGoodsDto;
import cn.cf.dto.B2bLoanNumberDto;
import cn.cf.dto.B2bOnlinepayRecordDto;
import cn.cf.entity.B2bBillCusgoodsDtoMa;
import cn.cf.entity.B2bCreditGoodsDtoMa;
import cn.cf.http.HttpHelper;
import cn.cf.json.JsonUtils;
import cn.cf.property.PropertyConfig;
import cn.cf.service.CommonService;
import cn.cf.service.ForeignBankService;

@Service
public class ForeignBankServiceImpl implements ForeignBankService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CommonService commonService;
	
	@Override
	public List<B2bCreditGoodsDtoMa> searchList(String purchaserPk,String supplierPk,String storePk) {
		List<B2bCreditGoodsDtoMa> list = null;
		try {
			list =	commonService.searchCreditGoodsAndBankCard(purchaserPk, supplierPk,storePk);
		} catch (Exception e) {
			logger.error("searchList",e);
		}
		return list;
	}

	@Override
	public Map<String, Object> delLoanOrder(String orderNumber) {
		Map<String,String> map = new HashMap<String,String>();
		Map<String,Object> resultMap = new HashMap<String,Object>();
		map.put("orderNumber", orderNumber);
		String result="";
		try {
			result = HttpHelper.doPost(PropertyConfig.getProperty("api_economics_url") + "economics/delLoanOrder", map);
			if (null!=result && !"".equals(result)) {
				result = EncodeUtil.des3Decrypt3Base64(result)[1];
				resultMap = JsonUtils.stringToCollect(result);
			}else {
				resultMap.put("code", "z000");
				resultMap.put("msg", "银行处理失败");
			}
		} catch (Exception e) {
			resultMap.put("code", "s999");
			logger.error("searchList",e);
		}
		return resultMap;
	}

	@Override
	public B2bEconomicsGoodsDto getEconomicsGoodsByPk(String name,Integer type) {
		  
		return commonService.getEconomicsGoodsByPk(name, type);
	}

	@Override
	public B2bLoanNumberDto getB2bLoanNumberDto(String orderNumber) {
		return commonService.getB2bLoanNumberDto(orderNumber);
	}

	@Override
	public Map<String, Object> delOnlinePayOrder(String orderNumber) {
		Map<String,String> map = new HashMap<String,String>();
		Map<String,Object> resultMap = new HashMap<String,Object>();
		map.put("orderNumber", orderNumber);
		String result="";
		try {
			result = HttpHelper.doPost(PropertyConfig.getProperty("api_economics_url") + "economics/cancelOnlineRecord", map);
			if (null!=result && !"".equals(result)) {
				result = EncodeUtil.des3Decrypt3Base64(result)[1];
				resultMap = JsonUtils.stringToCollect(result);
			}else {
				resultMap.put("code", "z000");
				resultMap.put("msg", "银行处理失败");
			}
		} catch (Exception e) {
			resultMap.put("code", "s999");
			logger.error("searchList",e);
		}
		return resultMap;
	}

	@Override
	public B2bOnlinepayRecordDto getOnlinepayRecordDto(String orderNumber) {
		// TODO Auto-generated method stub
		return commonService.getB2bOnlinepayRecordDto(orderNumber);
	}

	@Override
	public List<B2bBillCusgoodsDtoMa> searchBillGoodsList(String companyPk,
			String supplierPk) {
		// TODO Auto-generated method stub
//		List<B2bBillCusgoodsDtoMa> list = commonService.searchBillGoodsAndBankCard(companyPk, supplierPk);
//		List<B2bBillCusgoodsDtoMa> nlist = new ArrayList<B2bBillCusgoodsDtoMa>();
//		if(null != list && list.size()>0){
//			for(B2bBillCusgoodsDtoMa ma : list){
//				if(BillType.PFT.equals(ma.getGoodsShotName())){
//					B2bBillCusgoodsDtoMa mb = new B2bBillCusgoodsDtoMa();
//					mb.setPk(ma.getPk());
//					mb.setGoodsName((ma.getGoodsName()));
//					mb.setBankAccount(ma.getBankAccount());
//					mb.setBankName(ma.getBankName());
//					mb.setGoodsShotName(ma.getGoodsShotName());
//					mb.setImgUrl(ma.getImgUrl());
//					nlist.add(mb);
//					ma.setPk(ma.getPk()+BillType.PFT_T);
//					ma.setGoodsName(BillType.PFT_1_NAME);
//					ma.setImgUrl(null);
//				}
//				nlist.add(ma);
//			}
//		}
//		return nlist;
		return commonService.searchBillGoodsAndBankCard(companyPk, supplierPk);
	}

	@Override
	public Map<String, Object> delBillPayOrder(String orderNumber) {
		Map<String,String> map = new HashMap<String,String>();
		Map<String,Object> resultMap = new HashMap<String,Object>();
		map.put("orderNumber", orderNumber);
		String result="";
		try {
			result = HttpHelper.doPost(PropertyConfig.getProperty("api_economics_url") + "economics/cancelBillOrder", map);
			if (null!=result && !"".equals(result)) {
				result = EncodeUtil.des3Decrypt3Base64(result)[1];
				resultMap = JsonUtils.stringToCollect(result);
			}else {
				resultMap.put("code", "z000");
				resultMap.put("msg", "银行处理失败");
			}
		} catch (Exception e) {
			resultMap.put("code", "s999");
			logger.error("searchList",e);
		}
		return resultMap;
	}

	@Override
	public B2bBillOrderDto getBillOrder(String orderNumber) {
		// TODO Auto-generated method stub
		return commonService.getBillOrder(orderNumber);
	}

	@Async
	@Override
	public void completeBillOrder(String orderNumber) {
		B2bBillOrderDto order = this.getBillOrder(orderNumber);
		if(null != order && order.getStatus() == 4){
			Map<String,String> map = new HashMap<String,String>();
			map.put("orderNumber", orderNumber);
			try {
				HttpHelper.doPost(PropertyConfig.getProperty("api_economics_url") + "economics/completeBillOrder", map);
			} catch (Exception e) {
				logger.error("completeBillOrder",e);
			}
		}
	}

	@Override
	public List<B2bBillInventoryDto> searchBillInventoryList(String orderNumber) {
		// TODO Auto-generated method stub
		return commonService.searchBillInventoryList(orderNumber);
	}

}
