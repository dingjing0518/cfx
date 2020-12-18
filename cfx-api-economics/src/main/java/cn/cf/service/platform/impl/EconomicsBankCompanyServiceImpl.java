package cn.cf.service.platform.impl;

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
import org.springframework.stereotype.Service;

import cn.cf.dao.B2bCreditDaoEx;
import cn.cf.dao.B2bCreditGoodsDaoEx;
import cn.cf.dao.B2bEconomicsBankCompanyDaoEx;
import cn.cf.dto.B2bCreditGoodsDto;
import cn.cf.dto.B2bEconomicsBankCompanyDto;
import cn.cf.entry.BankInfo;
import cn.cf.service.platform.B2bEconomicsBankCompanyService;
import cn.cf.util.ArithUtil;
import cn.cf.util.ListUtil;

@Service
public class EconomicsBankCompanyServiceImpl implements
		B2bEconomicsBankCompanyService {
	
	@Autowired
	private B2bEconomicsBankCompanyDaoEx economicsBankCompanyDao;
	
	@Autowired
	private B2bCreditDaoEx b2bCreditDao;
	
	@Autowired
	private B2bCreditGoodsDaoEx b2bCreditGoodsDaoEx;
	@Autowired
	private MongoTemplate mongoTemplate;
	

	@Override
	public List<B2bEconomicsBankCompanyDto> searchList(String companyPk) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("companyPk", companyPk);
		List<B2bEconomicsBankCompanyDto> list =  economicsBankCompanyDao.searchList(map);
		return list;
	}

	@Override
	public void updateCredit(String companyPk,String bankPk, List<B2bEconomicsBankCompanyDto> list) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("companyPk",companyPk);
		map.put("bankPk",bankPk);
//		List<Integer> typeList = new ArrayList<Integer>();
//		typeList.add(1);
//		typeList.add(2);
//		map.put("type",typeList);
		//更新银行授信表
		economicsBankCompanyDao.delByCompanyAndBank(map);
		if(null != list && list.size() >0){
			List<B2bEconomicsBankCompanyDto> newList = getNewEconomicsBankCompanyList(list);
			//根据产品类型  合同结束时间排序 排除产品类型相同的数据
			if(null != newList && newList.size()>0){
				economicsBankCompanyDao.insertBatch(newList);
				//更新金融产品已使用额度
				for (B2bEconomicsBankCompanyDto economicCompany : newList) {
					B2bCreditGoodsDto o = new B2bCreditGoodsDto();
					o.setCompanyPk(economicCompany.getCompanyPk());
					Double useAmount = ArithUtil.sub(economicCompany.getContractAmount(), economicCompany.getAvailableAmount());
					o.setPledgeUsedAmount(ArithUtil.round(useAmount, 2));
					o.setGoodsType(economicCompany.getType());
					o.setBankPk(economicCompany.getBankPk());
					if(null != economicCompany.getCustomerNumber() && !"".equals(economicCompany.getCustomerNumber())){
						o.setBankAccountNumber(economicCompany.getCustomerNumber());
					}
					b2bCreditGoodsDaoEx.updateByCreditGoods(o);
				}
			}
		}
	}
	
	private static List<B2bEconomicsBankCompanyDto> getNewEconomicsBankCompanyList(List<B2bEconomicsBankCompanyDto> list){
		List<B2bEconomicsBankCompanyDto> newList = new ArrayList<B2bEconomicsBankCompanyDto>();
		if(null != list && list.size()>0){
			ListUtil.sort(list, false, "type","creditEndTime");
			for (int i = 0; i < list.size(); i++) {
				B2bEconomicsBankCompanyDto dto = list.get(i);
				if(i>0 && null != dto.getType() && null != list.get(i-1).getType() && dto.getType() == list.get(i-1).getType()){
					continue;
				}
				newList.add(dto);
			}
		}
		return newList;
	}

	@Override
	public B2bEconomicsBankCompanyDto searchGoods(String companyPk,
			String bankPk, Integer goodsType) {
		B2bEconomicsBankCompanyDto dto = null;
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("companyPk", companyPk);
		map.put("bankPk", bankPk);
		map.put("type", goodsType);
		List<B2bEconomicsBankCompanyDto> list = economicsBankCompanyDao.searchGrid(map);
		if(null != list && list.size()>0){
			dto = list.get(0);
		}
		return dto;
	}

	@Override
	public void deleteBankInfo() {
		Date dNow = new Date();   //当前时间
		Calendar calendar = Calendar.getInstance(); //得到日历
		calendar.setTime(dNow);//把当前时间赋给日历
		calendar.add(Calendar.MONTH, -3);  //设置为前3月
	    Date	dBefore = calendar.getTime();   //得到前3月的时间
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //设置时间格式
		String defaultStartDate = sdf.format(dBefore);    //格式化前3月的时间
		Query query = new Query(
								Criteria.where("insertTime").lte(defaultStartDate));
	   List<BankInfo> list=	mongoTemplate.find(query,BankInfo.class);
	   if (list.size()>0) {
		   for(BankInfo info : list){
			   mongoTemplate.remove(info);
		   }
	   }
	}
}
