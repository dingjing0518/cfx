package cn.cf.service.bill.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.common.RestCode;
import cn.cf.dao.B2bBillCusgoodsApplyDaoEx;
import cn.cf.dao.B2bBillCusgoodsDaoEx;
import cn.cf.dao.B2bBillCustomerApplyDaoEx;
import cn.cf.dao.B2bBillCustomerDao;
import cn.cf.dao.B2bBillGoodsDao;
import cn.cf.dto.B2bBillCusgoodsDto;
import cn.cf.dto.B2bBillCusgoodsDtoEx;
import cn.cf.dto.B2bBillCustomerApplyDto;
import cn.cf.dto.B2bBillCustomerApplyDtoEx;
import cn.cf.dto.B2bBillCustomerDto;
import cn.cf.dto.B2bBillGoodsDto;
import cn.cf.dto.B2bCompanyDto;
import cn.cf.model.B2bBillCusgoodsApply;
import cn.cf.model.B2bBillCustomerApply;
import cn.cf.service.bill.BillCusGoodsService;
import cn.cf.util.KeyUtils;

@Service
public class BillCusGoodsServiceImpl implements BillCusGoodsService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private B2bBillCustomerApplyDaoEx billCustomerApplyDaoEx;
	@Autowired
	private B2bBillCusgoodsApplyDaoEx billCusgoodsApplyDaoEx;
	@Autowired
	private B2bBillGoodsDao billGoodsDao;
	@Autowired
	private B2bBillCusgoodsDaoEx cusgoodsDaoEx;
	@Autowired
	private B2bBillCustomerDao customerDao;

	@Override
	public B2bBillCusgoodsDto getByPk(String pk) {
		// TODO Auto-generated method stub
		return cusgoodsDaoEx.getByPk(pk);
	}

	@Override
	public String applyForBill(B2bBillCustomerApplyDtoEx billCus,
			B2bCompanyDto company) {
		String rest = RestCode.CODE_0000.toJson();
		try {   //检查是否处于审核中
				int count=billCustomerApplyDaoEx.searchCountByStatus(company.getPk());
				if(count>0){
					return RestCode.CODE_C0014.toJson();
				}else{
					int result=0;
					String customerPk=KeyUtils.getUUID();
					B2bBillCustomerApply cus=new B2bBillCustomerApply();
					cus.UpdateDTO(billCus);
					cus.setStatus(1);
					cus.setIsDelete(1);
					//是否已有申请记录
					B2bBillCustomerApplyDto entityCus=billCustomerApplyDaoEx.getByCompanyPk(company.getPk());
					if(null!=entityCus){
						customerPk=entityCus.getPk();
						cus.setPk(customerPk);
						cus.setUpdateTime(new Date());
						cus.setInsertTime(new Date());
						result=billCustomerApplyDaoEx.updateCustomer(cus);
					}else{
						cus.setPk(customerPk);
						cus.setCompanyPk(company.getPk());
						cus.setCompanyName(company.getName());
						cus.setAddress(company.getRegAddress());
						cus.setInsertTime(new Date());
						 result=billCustomerApplyDaoEx.insert(cus);
					}
						if (result != 1) {
							rest = RestCode.CODE_S999.toJson();
						}else{
							addCusGood(customerPk,company.getPk(),billCus.getGoodsPk());
							
						}
				}
			return rest;
		} catch (Exception e) {
			rest = RestCode.CODE_S999.toJson();
			logger.error("errorapplyForBill",e);
		}
		return null;
	}

	private void addCusGood(String customerPk, String companyPk, String goodsPk) {
		//根据公司删除b2b_bill_cusgoods_apply数据
		billCusgoodsApplyDaoEx.deleteByCompanyPk(companyPk);
		//票付产品信息
		String[]  goodsPks=goodsPk.split(",");
		for(int i=0,len=goodsPks.length;i<len;i++){
		    B2bBillGoodsDto good=billGoodsDao.getByPk(goodsPks[i].toString());
			if(good.getIsDelete()==1&&good.getIsVisable()==1){
				B2bBillCusgoodsApply cusgood=new B2bBillCusgoodsApply();
				 cusgood.setPk(KeyUtils.getUUID());
				 cusgood.setCustomerPk(customerPk);
				 cusgood.setCompanyPk(companyPk);
				 cusgood.setGoodsPk(goodsPks[i].toString());
				 cusgood.setGoodsName(good.getName());
				 cusgood.setGoodsShotName(good.getShotName());
				 cusgood.setStatus(1);
				 cusgood.setIsVisable(1);
				 billCusgoodsApplyDaoEx.insert(cusgood);
			}
		}
		
		 
		
	}

	@Override
	public List<B2bBillCusgoodsDtoEx> searchBillCusGoodsByCompany(String companyPk) {
		if(null != companyPk){
			B2bBillCustomerDto cus=customerDao.getByCompanyPk(companyPk);
			if(null!=cus&&cus.getStatus()==2){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("companyPk", companyPk);
				map.put("isVisable", 1);
				return cusgoodsDaoEx.searchBillCusGoodsList(map);
			}
		}
	return null;
	}

	@Override
	public B2bBillCustomerApplyDto searchBillCusApply(B2bCompanyDto company) {
		B2bBillCustomerApplyDto cusApply= billCustomerApplyDaoEx.getByCompanyPk(company.getPk());
		if(null != cusApply){
			B2bBillCustomerDto cus=customerDao.getByCompanyPk(company.getPk());
			if(null!=cus){
				if(cus.getStatus()==2&&cusApply.getStatus()==3){//审核表审核通过且申请表审核通过
					cusApply.setStatus(3);//则为通过
				}
				if(cus.getStatus()==3){//审核表审核不通过
					if(cusApply.getStatus()==1||cusApply.getStatus()==2){
						cusApply.setStatus(2);//则为审核中
					}else{
						cusApply.setStatus(4);//则为不通过
					}
				}
				if(cus.getStatus()==1){//审核表未审核
					cusApply.setStatus(2);//则为审核中
				}
			}else{
				if(4!=cusApply.getStatus()){//申请表未驳回的
					cusApply.setStatus(2);//则为审核中
				}
			}
		}
		return cusApply;
	}

	@Override
	public void  updateBindStatus(String companyPk,String shotName,Integer status) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("companyPk", companyPk);
		map.put("goodsShotName", shotName);
		 List<B2bBillCusgoodsDtoEx>list= 	cusgoodsDaoEx.searchBillCusGoodsList(map);
		if(list.size()>0){
			B2bBillCusgoodsDto cusg=list.get(0);
			cusg.setBindStatus(status);
			 cusgoodsDaoEx.updateBillCusgoods(cusg);
		}
		  
	}

	@Override
	public void updateUseAmount(B2bBillCusgoodsDto gdto) {
		cusgoodsDaoEx.updateBillCusgoods(gdto);
		
	}

	@Override
	public B2bBillCusgoodsDto getByCompanyPk(String goodsk, String companyPk) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("goodsPk", goodsk);
		map.put("companyPk", companyPk);
		List<B2bBillCusgoodsDto> list =cusgoodsDaoEx.searchList(map);
		if(null != list && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

	@Override
	public List<B2bBillCusgoodsDtoEx> searchBillCusGoodsList(
			Map<String, Object> map) {
		return cusgoodsDaoEx.searchBillCusGoodsList(map);
	}

	@Override
	public void cleanUseAmount() {
		cusgoodsDaoEx.updateBillCusgoodsAmt();
		
	}

}
