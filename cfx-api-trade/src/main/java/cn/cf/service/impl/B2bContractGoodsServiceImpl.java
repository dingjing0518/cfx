package cn.cf.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.constant.Block;
import cn.cf.dao.B2bContractGoodsDaoEx;
import cn.cf.dao.B2bGoodsDaoEx;
import cn.cf.dto.B2bContractGoodsDtoEx;
import cn.cf.entity.B2bContractGoodsDtoMa;
import cn.cf.entity.CfGoods;
import cn.cf.service.B2bContractGoodsService;
import cn.cf.util.ArithUtil;

@Service
public class B2bContractGoodsServiceImpl implements B2bContractGoodsService {
	
	@Autowired
	private B2bContractGoodsDaoEx b2bContractGoodsDaoEx;
	
	@Autowired
	private B2bGoodsDaoEx b2bGoodsDaoEx;
	
	@Override
	public List<B2bContractGoodsDtoEx> searchListByContractNo(String contractNo,Boolean flag) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("contractNo",contractNo);
		if(!flag){
			map.put("flag", "no");
		}
		return searchListByMap(map);
	}
	
	private List<B2bContractGoodsDtoEx> searchListByMap(Map<String,Object> map){
		List<B2bContractGoodsDtoEx> list = b2bContractGoodsDaoEx.searchContractGoodsList(map);
		List<B2bContractGoodsDtoEx> newList = new ArrayList<B2bContractGoodsDtoEx>();;
		if(null != list && list.size()>0){
			for(B2bContractGoodsDtoEx dtoEx : list){
				B2bContractGoodsDtoMa ma = new  B2bContractGoodsDtoMa();
				ma.UpdateMine(dtoEx);
				dtoEx.setBasicTotalPrice(ArithUtil.round(ArithUtil.mul(dtoEx.getBasicPrice(), dtoEx.getWeight()), 2));
				dtoEx.setContractTotalPrice(ArithUtil.round(ArithUtil.mul(dtoEx.getContractPrice(), dtoEx.getWeight()), 2));
				if(Block.SX.getValue().equals(dtoEx.getBlock())){
					dtoEx.setTotalPrice(ArithUtil.round(ArithUtil.mul(ArithUtil.add(ma.getContractPrice(), ma.getFreight()), ma.getWeight()),2));
					dtoEx.setTotalFreight(ArithUtil.round(ArithUtil.mul(dtoEx.getFreight(), ArithUtil.div(dtoEx.getWeight(), 1000)), 2));
				}else{
					CfGoods cfGoods = ma.getCfGoods();
					dtoEx.setTotalPrice(ArithUtil.round(ArithUtil.mul(ma.getContractPrice()+(null==cfGoods.getAdminFee()?0d:cfGoods.getAdminFee())
							+(null==cfGoods.getLoadFee()?0d:cfGoods.getLoadFee())
							+(null==cfGoods.getPackageFee()?0d:cfGoods.getPackageFee()), ma.getWeight()),2));
					dtoEx.setTotalFreight(ArithUtil.round(ArithUtil.mul(dtoEx.getFreight(), dtoEx.getWeight()), 2));
				}
//				B2bGoodsDto goods = b2bGoodsDaoEx.getByPk(dtoEx.getGoodsPk());
//				if(null != goods){
//					dtoEx.setTareWeight(goods.getTareWeight());
//				}
				newList.add(dtoEx);
			}
		}
		return newList;
	}

 
}
