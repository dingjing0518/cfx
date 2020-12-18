package cn.cf.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.common.RestCode;
import cn.cf.constant.Block;
import cn.cf.constant.GoodsType;
import cn.cf.dao.B2bCartDaoEx;
import cn.cf.dao.B2bGoodsDaoEx;
import cn.cf.dto.B2bCartDto;
import cn.cf.dto.B2bCartDtoEx;
import cn.cf.dto.B2bGoodsDto;
import cn.cf.entity.CollectionGoods;
import cn.cf.entity.FirmOrder;
import cn.cf.entity.Sessions;
import cn.cf.model.B2bCart;
import cn.cf.service.B2bBindGoodsService;
import cn.cf.service.B2bCartService;
import cn.cf.service.B2bGoodsService;
import cn.cf.util.ArithUtil;
import cn.cf.util.KeyUtils;
import cn.cf.util.StringUtils;

@Service
public class B2bCartServiceImpl implements B2bCartService {

	@Autowired
	private B2bCartDaoEx b2bCartDaoEx;

	@Autowired
	private B2bGoodsDaoEx b2bGoodsDaoEx;
	
	@Autowired
	private B2bBindGoodsService b2bBindGoodsService;
	
	@Autowired
	private B2bGoodsService b2bGoodsService;
	

	@Override
	public synchronized List<B2bCartDtoEx> searchCartList(String memberPk) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberPk", memberPk);
		map.put("cartType", 1);
		// 查询所有供应商信息
		List<B2bCartDtoEx> list = b2bCartDaoEx.searchCompanyList(map);
		if (null != list && list.size() > 0) {
			for (B2bCartDtoEx dto : list) {
				map.put("supplierPk", dto.getSupplierPk());
				List<B2bCartDtoEx> childList = b2bCartDaoEx.searchGoodsList(map);
				if(null != childList && childList.size()>0){
					dto.setStartTime(childList.get(0).getStartTime());
					dto.setEndTime(childList.get(0).getEndTime());
					dto.setIsOpen(childList.get(0).getIsOpen());
					dto.setShowPricebfOpen(childList.get(0).getShowPricebfOpen());
					//查询是否收藏过该商品
					for (B2bCartDtoEx b2bCartDtoEx : childList) {
						if(null == b2bCartDtoEx.getTotalWeight() || 0d == b2bCartDtoEx.getTotalWeight()){
							b2bCartDtoEx.setTotalWeight(ArithUtil.mul(b2bCartDtoEx.getTareWeight(), b2bCartDtoEx.getBoxes()));
							if(Block.CF.getValue().equals(b2bCartDtoEx.getBlock())){
								b2bCartDtoEx.setTotalWeight(ArithUtil.div(b2bCartDtoEx.getTotalWeight(), 1000));
							}
						}
						b2bCartDtoEx.setTotalPrice(ArithUtil.mul(b2bCartDtoEx.getSalePrice(), b2bCartDtoEx.getTotalWeight()));
						List<CollectionGoods> collections = b2bGoodsService.iscollected(b2bCartDtoEx.getGoodsPk(), memberPk);
						if(null != collections && collections.size()>0){
							b2bCartDtoEx.setIsCollected(2);
							b2bCartDtoEx.setCollectionPrice(collections.get(0).getSalePrice());
						}else{
							b2bCartDtoEx.setIsCollected(1);
						}
					}
					dto.setChildList(childList);
				}
			}
		}
		return list;
	}

	@Override
	public synchronized List<B2bCartDtoEx> searchCartSpList(String memberPk) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberPk", memberPk);
		map.put("cartType", 2);
		// 查询所有采购商信息
		List<B2bCartDtoEx> list = b2bCartDaoEx.searchCompanyList(map);
		if (null != list && list.size() > 0) {
			for (B2bCartDtoEx dto : list) {
				map.put("purchaserPk", dto.getPurchaserPk());
				map.put("bindPk", dto.getBindPk()==null?"":dto.getBindPk());
				List<B2bCartDtoEx> carts=b2bCartDaoEx.searchGoodsList(map);
				if(null !=  carts && carts.size()>0){
					dto.setStartTime(carts.get(0).getStartTime());
					dto.setEndTime(carts.get(0).getEndTime());
					dto.setIsOpen(carts.get(0).getIsOpen());
					dto.setShowPricebfOpen(carts.get(0).getShowPricebfOpen());
					if(null!=dto.getBindPk()&&!"".equals(dto.getBindPk())){
						  carts=b2bBindGoodsService.searchBindGoodsBoxes(carts);
					}
					//查询是否收藏过该商品
					for (B2bCartDtoEx b2bCartDtoEx : carts) {
						if(null == b2bCartDtoEx.getTotalWeight() || 0d == b2bCartDtoEx.getTotalWeight()){
							b2bCartDtoEx.setTotalWeight(ArithUtil.mul(b2bCartDtoEx.getTareWeight(), b2bCartDtoEx.getBoxes()));
							if(Block.CF.getValue().equals(b2bCartDtoEx.getBlock())){
								b2bCartDtoEx.setTotalWeight(ArithUtil.div(b2bCartDtoEx.getTotalWeight(), 1000));
							}
						}
						b2bCartDtoEx.setTotalPrice(ArithUtil.mul(b2bCartDtoEx.getSalePrice(), b2bCartDtoEx.getTotalWeight()));
						List<CollectionGoods> collections = b2bGoodsService.iscollected(b2bCartDtoEx.getGoodsPk(), memberPk);
						if(null != collections && collections.size()>0){
							b2bCartDtoEx.setIsCollected(2);
							b2bCartDtoEx.setCollectionPrice(collections.get(0).getSalePrice());
						}else{
							b2bCartDtoEx.setIsCollected(1);
						}
					}
				}
				dto.setChildList(carts);
			}
		}
		return list;
	}

	@Override
	public synchronized int searchCartCounts(String memberPk, int cartType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberPk", memberPk);
		map.put("cartType", cartType);
		return b2bCartDaoEx.searchCounts(map);
	}

	@Override
	public String addCart(B2bCartDtoEx dtoEx, Sessions session) {
		int result = 0;
		boolean flag = true;
		String rest = RestCode.CODE_0000.toJson();
		B2bGoodsDto g = b2bGoodsDaoEx.getByPk(dtoEx.getGoodsPk());
		if (null == g || g.getIsUpdown() != 2) {
			// 商品已下架
			rest = RestCode.CODE_G003.toJson();
			flag = false;
		}
		if(flag && (null == dtoEx.getWeight() || 0d == dtoEx.getWeight())){
			if(Block.CF.getValue().equals(g.getBlock())){
				dtoEx.setWeight(ArithUtil.div(ArithUtil.mul(null==dtoEx.getBoxes()?0:dtoEx.getBoxes(), null==g.getTareWeight()?0d:g.getTareWeight()), 1000));
			}else{
				dtoEx.setWeight(ArithUtil.mul(null==dtoEx.getBoxes()?0:dtoEx.getBoxes(), null==g.getTareWeight()?0d:g.getTareWeight()));
			}
		}
		if (flag && !GoodsType.PRESALE.getValue().equals(g.getType())&& dtoEx.getWeight() > g.getTotalWeight()) {
			// 商品库存不足
			rest = RestCode.CODE_G004.toJson();
			flag = false;
		}
		if (flag) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("memberPk", session.getMemberPk());
			map.put("goodsPk", g.getPk());
			map.put("block",g.getBlock());
			if (null != dtoEx.getPurchaserPk()) {
				map.put("purchaserPk", dtoEx.getPurchaserPk());
			}
			List<B2bCartDto> cartList = b2bCartDaoEx.searchList(map);
			B2bCart model = new B2bCart();
			if (null != cartList && cartList.size() > 0) {
				model.UpdateDTO(cartList.get(0));
				model.setBindPk(dtoEx.getBindPk());
				
				int num = model.getBoxes() + dtoEx.getBoxes();
				Double weight = ArithUtil.add(model.getWeight(), dtoEx.getWeight());
				if (num > 0) {
					model.setBoxes(num);
					model.setWeight(weight);
				} else {
					model.setBoxes(0);
					model.setWeight(0d);
				}
				result = b2bCartDaoEx.update(model);
			} else {
				model.UpdateDTO(dtoEx);
				model.setPk(KeyUtils.getUUID());
				model.setInsertTime(new Date());
				model.setMemberPk(session.getMemberPk());
				model.setSupplierPk(g.getCompanyPk());
				model.setSupplierName(g.getCompanyName());
				model.setBlock(g.getBlock());

				// 自采添加购物车
				if (null == dtoEx.getPurchaserPk()) {
					model.setPurchaserPk(session.getCompanyPk());
					model.setPurchaserName(session.getCompanyName());
					model.setCartType(1);
					// 代采添加购物车
				} else {
					model.setCartType(2);
				}
				result = b2bCartDaoEx.insert(model);
			}
			if (result != 1) {
				rest = RestCode.CODE_S999.toJson();
			}
		}
		return rest;
	}

	@Override
	public synchronized String updateCart(String pk, int boxes,Double weight) {
		B2bCartDto cdto = b2bCartDaoEx.getByPk(pk);
		String rest = RestCode.CODE_0000.toJson();
		boolean flag = true;
		if (null == cdto) {
			rest = RestCode.CODE_A001.toJson();
			flag = false;
		}
		// 判断库存
		if (flag) {
			B2bGoodsDto g = b2bGoodsDaoEx.getByPk(cdto.getGoodsPk());
			if(null == weight || weight==0d){
				if(Block.CF.getValue().equals(g.getBlock())){
					weight = ArithUtil.div((null==g.getTareWeight()?0d:g.getTareWeight()) * boxes, 1000);
				}else{
					weight = (null==g.getTareWeight()?0d:g.getTareWeight()) * boxes;
				}
			}
			if (!GoodsType.PRESALE.getValue().equals(g.getType())
					&& weight > g.getTotalWeight()) {
				rest = RestCode.CODE_G004.toJson();
				flag = false;
			}
		}
		if (flag) {
			B2bCart cart = new B2bCart();
			cart.UpdateDTO(cdto);
			cart.setBoxes(boxes);
			cart.setWeight(weight);
			int result = b2bCartDaoEx.update(cart);
			if (result != 1) {
				rest = RestCode.CODE_S999.toJson();
			}
		}
		return rest;
	}

	@Override
	public String delCart(String pks) {
		String rest = RestCode.CODE_0000.toJson();
		if (null != pks && !"".equals(pks)) {
			String[] pk = pks.split(",");
			for (int i = 0; i < pk.length; i++) {
				b2bCartDaoEx.delete(pk[i]);
			}
		} else {
			rest = RestCode.CODE_A001.toJson();
		}
		return rest;
	}

	@Override
	public B2bCartDto getCart(String pk) {
		return b2bCartDaoEx.getByPk(pk);
	}

 

	@Override
	public List<FirmOrder> searchFirmOrder(String pks) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pk", StringUtils.splitStrs(pks));
		return b2bCartDaoEx.searchFirmOrder(map);
	}

	/**
	 * 根据goodsPk删除购物车
	 */
	@Override
	public int delByGoodsPk(String goodsPk) {
		return b2bCartDaoEx.delByGoodsPk(goodsPk);
	}

}
