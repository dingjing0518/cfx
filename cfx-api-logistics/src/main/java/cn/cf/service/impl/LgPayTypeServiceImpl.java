package cn.cf.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.cf.dao.LgPayTypeDaoEx;
import cn.cf.model.LgPayType;
import cn.cf.service.LgPayTypeService;

@Service
public class LgPayTypeServiceImpl implements LgPayTypeService{
	
	@Autowired
    private LgPayTypeDaoEx lgPayTypeDaoEx;
	
	
	//查询启用的支付方式
	@Override
	public List<LgPayType> getPayTypes() {
		return lgPayTypeDaoEx.getPayTypes();
	}

	//根据pk查询支付方式名
	@Override
	public String getPayTypeNameByPK(String paymentPk) {
		return lgPayTypeDaoEx.getPayTypeNameByPK(paymentPk);
	}
	
	

}
