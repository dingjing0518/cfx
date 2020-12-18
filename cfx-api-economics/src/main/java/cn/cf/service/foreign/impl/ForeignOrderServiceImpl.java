package cn.cf.service.foreign.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.constant.Block;
import cn.cf.constant.UnitType;
import cn.cf.dto.B2bPaymentDto;
import cn.cf.dto.SysCompanyBankcardDto;
import cn.cf.entity.B2bOrderDtoMa;
import cn.cf.entity.B2bOrderGoodsDtoMa;
import cn.cf.entry.OrderGoodsDtoEx;
import cn.cf.model.B2bContract;
import cn.cf.model.B2bOrder;
import cn.cf.service.TradeService;
import cn.cf.service.foreign.ForeignOrderService;

@Service
public class ForeignOrderServiceImpl implements ForeignOrderService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private TradeService tradeService;
	@Override
	public void updateOrderStatus(B2bOrder order,SysCompanyBankcardDto card) {
		try {
			tradeService.updateOrderStatus(order,card);
		} catch (Exception e) {
			logger.error("updateOrderStatus",e);
		}
	}

	@Override
	public  void updateContractStatus(B2bContract contract,SysCompanyBankcardDto card) {
		try {
			tradeService.updateContractStatus(contract,card);
		} catch (Exception e) {
			logger.error("updateContractStatus",e);
		}
	}

	@Override
	public B2bOrderDtoMa getOrder(String orderNumber) {
		return tradeService.getOrder(orderNumber);
	}

	@Override
	public B2bOrderDtoMa getContract(String contractNo) {
			return	tradeService.getContract(contractNo);
	}

	@Override
	public List<OrderGoodsDtoEx> getOrderGoods(String orderNumber) {
		List<OrderGoodsDtoEx> list = null;
 		try {
				List<B2bOrderGoodsDtoMa> blist  = tradeService.searchOrderGoodsList(orderNumber);
				if(null != blist && blist.size()>0){
 					list = new ArrayList<OrderGoodsDtoEx>();
 					for(B2bOrderGoodsDtoMa bo : blist){
 						OrderGoodsDtoEx og = new OrderGoodsDtoEx();
 						if(Block.CF.getValue().equals(bo.getBlock())){
 							BeanUtils.copyProperties(og, bo.getCfGoods());
 							og.setUnit(UnitType.fromInt(bo.getCfGoods().getUnit()+"").toString());
 						}else{
 							BeanUtils.copyProperties(og, bo.getSxGoods());
 							og.setUnit(UnitType.fromInt(bo.getSxGoods().getUnit()+"").toString());
 						}
 						og.setAfterBoxes(bo.getAfterBoxes());
 						og.setAfterWeight(bo.getAfterWeight());
 						og.setOriginalPrice(bo.getOriginalPrice());
 						og.setPresentPrice(bo.getPresentPrice());
 						og.setGoodsType(bo.getGoodsType());
 						list.add(og);
 					}
 				}
		} catch (Exception e) {
			logger.error("getOrder",e);
		}
		return list;
	}

	@Override
	public List<OrderGoodsDtoEx> getContractGoods(String contractNo) {
		List<OrderGoodsDtoEx> list = null;
		try {
 			List<B2bOrderGoodsDtoMa> ogList  = tradeService.getContractGoods(contractNo);
 			if(null != ogList && ogList.size()>0){
					list = new ArrayList<OrderGoodsDtoEx>();
					for(B2bOrderGoodsDtoMa bo : ogList){
						OrderGoodsDtoEx og = new OrderGoodsDtoEx();
						if(Block.CF.getValue().equals(bo.getBlock())){
 							BeanUtils.copyProperties(og, bo.getCfGoods());
 							og.setUnit(UnitType.fromInt(bo.getCfGoods().getUnit()+"").toString());
 						}else{
 							BeanUtils.copyProperties(og, bo.getSxGoods());
 							og.setUnit(UnitType.fromInt(bo.getSxGoods().getUnit()+"").toString());
 						}
 						og.setAfterBoxes(bo.getAfterBoxes());
 						og.setAfterWeight(bo.getAfterWeight());
 						og.setOriginalPrice(bo.getOriginalPrice());
 						og.setPresentPrice(bo.getPresentPrice());
						list.add(og);
					}
				}
		} catch (Exception e) {
			logger.error("getOrder",e);
		}
		return list;
	}

	@Override
	public B2bPaymentDto searchPayment(Integer type) {
		// TODO Auto-generated method stub
		return tradeService.searchPayment(type);
	}
}
