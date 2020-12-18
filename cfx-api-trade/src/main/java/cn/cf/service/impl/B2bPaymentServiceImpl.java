package cn.cf.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.dao.B2bContractDaoEx;
import cn.cf.dao.B2bOrderDaoEx;
import cn.cf.dao.B2bPaymentDaoEx;
import cn.cf.dto.B2bContractDto;
import cn.cf.dto.B2bOrderDtoEx;
import cn.cf.dto.B2bPaymentDto;
import cn.cf.dto.B2bPaymentDtoEx;
import cn.cf.service.B2bPaymentService;
import cn.cf.service.ForeignBankService;

@Service
public class B2bPaymentServiceImpl implements B2bPaymentService {
	
	@Autowired
	private B2bPaymentDaoEx b2bPaymentDao;
	
	@Autowired
	private ForeignBankService bankService;
	
	@Autowired
	private B2bOrderDaoEx b2bOrderDaoEx;
	
	@Autowired
	private B2bContractDaoEx b2bContractDaoEx;
	
	@Override
	public B2bPaymentDtoEx getPaymentByType(int type) {
		B2bPaymentDtoEx dto = null;
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("type", 1);
		map.put("isVisable", 1);
		 List<B2bPaymentDtoEx> dtoList = b2bPaymentDao.searchPayment(map);
		 if(null != dtoList && dtoList.size()>0){
			 dto = dtoList.get(0);
		 }
		return dto;
	}

	@Override
	public List<B2bPaymentDtoEx> searchPayment(String orderNumber,String contractNo,String companyPk) {
		String purchaserPk = null;
		String supplierPk = null;
		String storePk = null;
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("isVisable", 1);
		//没传订单号直接返回 线下支付
		if("".equals(orderNumber) && "".equals(contractNo)){
			map.put("type", 1);
		}else{
			//代采只返回线下支付
			if(!"".equals(orderNumber)){
				B2bOrderDtoEx order = b2bOrderDaoEx.getB2bOrder(orderNumber);
				if(null == order || (!order.getPurchaserPk().equals(companyPk))){
					map.put("type", 1);
				}else{
					purchaserPk = order.getPurchaserPk();
					supplierPk = order.getSupplierPk();
					storePk = order.getStorePk();
				}
			}
			//代采只返回线下支付
			if(!"".equals(contractNo)){
				B2bContractDto contract = b2bContractDaoEx.getByContractNo(contractNo);
				if(null == contract || (!contract.getPurchaserPk().equals(companyPk))){
					map.put("type", 1);
				}else{
					purchaserPk = contract.getPurchaserPk();
					supplierPk = contract.getSupplierPk();
					storePk = contract.getStorePk();
				}
			}
		}
		List<B2bPaymentDtoEx>	payments = b2bPaymentDao.searchPayment(map);
		if(null !=payments && payments.size()>0){
			for (B2bPaymentDtoEx p : payments) {
				//金融产品
				if(p.getType() == 3){
					p.setCreditGoods(bankService.searchList(purchaserPk,supplierPk,storePk));
				}
				//票据产品
				if(p.getType() == 5){
					p.setBillGoods(bankService.searchBillGoodsList(companyPk, supplierPk));
				}
			}
		}
		return payments;
	}

	@Override
	public B2bPaymentDto getPaymentPyPk(String pk) {
		// TODO Auto-generated method stub
		return b2bPaymentDao.getByPk(pk);
	}

	@Override
	public List<B2bPaymentDto> searchPaymentList(Map<String, Object> map) {
		return b2bPaymentDao.searchGrid(map);
	}

}
