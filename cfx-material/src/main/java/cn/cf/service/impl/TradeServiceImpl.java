package cn.cf.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.dao.TradeDao;
import cn.cf.dto.B2bPaymentDto;
import cn.cf.dto.SysCompanyBankcardDto;
import cn.cf.entity.B2bContractDtoMa;
import cn.cf.entity.B2bOrderDtoMa;
import cn.cf.entity.B2bOrderGoodsDtoMa;
import cn.cf.model.B2bContract;
import cn.cf.model.B2bContractGoods;
import cn.cf.model.B2bOrder;
import cn.cf.service.TradeService;

import com.alibaba.fastjson.JSON;


@Service
public class TradeServiceImpl implements TradeService {
   @Autowired
   private TradeDao tradeDao;

    @Override
    public List<B2bOrderGoodsDtoMa> searchOrderGoodsList(String orderNumber) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("orderNumber", orderNumber);
        return tradeDao.searchOrderGoodsList(map);
    }

    @Override
    public B2bOrderDtoMa getOrder(String orderNumber) {
        return tradeDao.getByOrderNumber(orderNumber);
    }

    @Override
    public B2bOrderDtoMa getContract(String contractNo) {
        return tradeDao.getContractToOrder(contractNo);
    }

    @Override
    public List<B2bOrderGoodsDtoMa> getContractGoods(String contractNo) {
        return tradeDao.getContractGoodsToOrder(contractNo);
    }

	@Override
	public B2bPaymentDto searchPayment(Integer type) {
		// TODO Auto-generated method stub
		return tradeDao.searchPayment(type);
	}

	@Override
	public B2bOrderDtoMa updateOrderStatus(B2bOrder o,SysCompanyBankcardDto card) {
		 B2bOrderDtoMa order = tradeDao.getByOrderNumber(o.getOrderNumber());
		// 订单已是要做操作的状态 不做处理
		if (null == order || o.getOrderStatus() == order.getOrderStatus()) {
			order = null;
		} else {
			if(null != card){
				order.getSupplier().setBankName(card.getBankName());
				order.getSupplier().setBankAccount(card.getBankAccount());
				order.getSupplier().setBankNo(card.getBankNo());
				o.setSupplierInfo(JSON.toJSONString(order.getSupplier()));
			}
			tradeDao.updateOrder(o);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("orderNumber", o.getOrderNumber());
			map.put("orderStatus", o.getOrderStatus());
			tradeDao.updateChildOrderStatus(map);
		}
		return order;
	
	}

	@Override
	public B2bContractDtoMa updateContractStatus(B2bContract contract,
			SysCompanyBankcardDto card) {
		B2bContractDtoMa ma = tradeDao.getByContractNo(contract.getContractNo());
		
		if (null == ma || ma.getContractStatus() == contract.getContractStatus()) {
			ma = null;
		} else {
			if(null != card){
				ma.getSupplier().setBankName(card.getBankName());
				ma.getSupplier().setBankAccount(card.getBankAccount());
				ma.getSupplier().setBankNo(card.getBankNo());
				contract.setSupplierInfo(JSON.toJSONString(ma.getSupplier()));
			}
			B2bContractGoods contractGoods = new B2bContractGoods();
			contractGoods.setContractNo(contract.getContractNo());
			if(null != contract.getContractStatus()){
				contractGoods.setContractStatus(contract.getContractStatus());
			}
			tradeDao.updateContract(contract);
			tradeDao.updateContractGoods(contractGoods);
		}
		return ma;
	
	}
}
