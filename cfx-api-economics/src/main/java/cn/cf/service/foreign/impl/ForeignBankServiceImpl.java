package cn.cf.service.foreign.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bOrderDto;
import cn.cf.service.creditpay.*;
import cn.cf.service.platform.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import cn.cf.common.BanksType;
import cn.cf.common.RestCode;
import cn.cf.dao.B2bLoanNumberDao;
import cn.cf.dto.B2bEconomicsGoodsDto;
import cn.cf.dto.B2bLoanNumberDto;
import cn.cf.dto.SysCompanyBankcardDto;
import cn.cf.entity.B2bCreditGoodsDtoMa;
import cn.cf.entry.BankBaseResp;
import cn.cf.service.foreign.ForeignBankService;

@Service
public class ForeignBankServiceImpl   implements ForeignBankService {
	
	@Autowired
	private BankGuangdaService bankGuangdaService;
	
	@Autowired
	private BankSuzhouService bankSuzhouService;
	
	@Autowired
	private BankXingyeService bankXingyeService;
	
	@Autowired
	private BankGongshangService bankGongshangService;

	@Autowired
	private BankShanghaiService bankShanghaiService;
	
	@Autowired
	private B2bEconomicsBankCompanyService economicsBankCompanyService;
	
	@Autowired
	private B2bCreditGoodsService b2bCreditGoodsService;
	
	@Autowired
	private B2bEconomicsGoodsService economicsGoodsService;

	@Autowired
	private B2bLoanNumberService b2bLoanNumberService;
	@Autowired
	private B2bLoanNumberDao b2bLoanNumberDao;
	@Autowired
	private BankSunongService sunongService;
	@Autowired
	private B2bCompanyBankcardService b2bCompanyBankcardService;
	@Autowired
	private BankJiansheService bankJiansheService;

	@Autowired
	private BankZhonghangService bankZhonghangService;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public Map<String,Object> delLoanOrder(String orderNumber) {
		//先根据公司查询授信的银行
		Map<String,Object> map = new HashMap<String,Object>();
		B2bLoanNumberDto loanNumber =  b2bLoanNumberService.getB2bLoanNumberDto(orderNumber);
		if(null != loanNumber ){
			if(loanNumber.getLoanStatus() == 2){
				//调用不同的银行返回结果
				BankBaseResp resp = null;
				switch (loanNumber.getBankName()) {
				case BanksType.bank_guangda:
					resp = 	bankGuangdaService.cancelOrder(loanNumber);
					break;
				case BanksType.bank_suzhou:
					resp = bankSuzhouService.cancelOrder(loanNumber);
					break;
				case BanksType.bank_xingye:
					resp = bankXingyeService.cancelOrder(loanNumber);
					break;
				case BanksType.bank_gongshang:
					resp = bankGongshangService.cancelOrder(loanNumber);
					break;	
				case BanksType.bank_sunong:
					resp = sunongService.cancelOrder(loanNumber);
					break;
				case BanksType.bank_zheshang:
					resp = new BankBaseResp();
					resp.setCode(RestCode.CODE_0000.getCode());
					break;
				case BanksType.bank_shanghai:
					resp = bankShanghaiService.cancelOrder(loanNumber);
					break;
				case BanksType.bank_jianshe:
					resp = bankJiansheService.cancelOrder(loanNumber);
					break;
				case BanksType.bank_zhonghang:
					resp = bankZhonghangService.cancelOrder(loanNumber);
					break;
				default:
					break;
				}
				//订单取消成功 修改交易记录状态
				if(null != resp && RestCode.CODE_0000.getCode().equals(resp.getCode())){
					map.put("code", "0000");
					try {
						b2bLoanNumberService.updateBackCancalOrder(orderNumber);
					} catch (Exception e) {
						logger.error("errorCancelEconomicOrder",e);
					}
				}else{
					map.put("code", "z000");
					if (null != resp && null!=resp.getMsg() && !"".equals(resp.getMsg())) {
						map.put("msg", resp.getMsg());
					}else {
						map.put("msg", "银行处理失败");
					}
				}
			}else if(loanNumber.getLoanStatus() == 4){
				map.put("code", "0000");
			} else{
				map.put("code", "O012");
				map.put("msg", RestCode.CODE_O012.getMsg());
			}
		}else{
			map.put("code", "c002");
			map.put("msg", RestCode.CODE_C002.getMsg());
		}
		return map;
	}



	@Override
	public List<B2bCreditGoodsDtoMa> searchCreditGoodsAndBankCard(String purchaserPk,String supplierPk) {
		List<B2bCreditGoodsDtoMa> dtoList = b2bCreditGoodsService.searchCreditGoodsByCompany(purchaserPk);
		if(null != dtoList && dtoList.size() >0 && null !=supplierPk && !"".equals(supplierPk)){
			for (B2bCreditGoodsDtoMa dto : dtoList) {
				SysCompanyBankcardDto card = b2bCompanyBankcardService.getCompanyBankCard(supplierPk, dto.getBank());
				if(null != card){
					dto.setBankAccount(card.getBankAccount());
					dto.setBankName(card.getBankName());
				}
			}
		}
		return dtoList;
	}



	@Override
	public B2bEconomicsGoodsDto getEconomicsGoodsByPk(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return economicsGoodsService.searchOne(map);
	}



	@Override
	public B2bLoanNumberDto getB2bLoanNumberDto(String orderNumber) {
		// TODO Auto-generated method stub
		return b2bLoanNumberDao.getByOrderNumber(orderNumber);
	}
}
