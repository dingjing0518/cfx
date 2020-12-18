package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bPaymentDto;
import cn.cf.entity.B2bContractDtoMa;
import cn.cf.entity.B2bOrderDtoMa;
import cn.cf.entity.B2bOrderGoodsDtoMa;
import cn.cf.model.B2bContract;
import cn.cf.model.B2bContractGoods;
import cn.cf.model.B2bOrder;
import cn.cf.model.B2bOrderGoods;

public interface TradeDao {

	B2bOrderDtoMa getByOrderNumber(String orderNumber);
	
	B2bContractDtoMa getByContractNo(String contractNo);
	
	List<B2bOrderGoodsDtoMa> searchOrderGoodsList(Map<String,Object> map);
	
	void updateOrder(B2bOrder o);
	
	void updateChildOrderStatus(Map<String,Object> map);

	void updateContract(B2bContract contract);
	
	void updateContractGoods(B2bContractGoods contractGoods);
	
	B2bOrderDtoMa getContractToOrder(String contractNo);
	
	List<B2bOrderGoodsDtoMa> getContractGoodsToOrder(String contractNo);
	
	B2bPaymentDto searchPayment(Integer type);
	
	void updateOrderGoods(B2bOrderGoods orderGoods);
}
