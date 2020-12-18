package cn.cf.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.cf.PageModel;
import cn.cf.dao.B2bBindGoodsDaoEx;
import cn.cf.dto.B2bBindGoodsDto;
import cn.cf.dto.B2bBindGoodsDtoEx;
import cn.cf.dto.B2bCartDtoEx;
import cn.cf.dto.B2bGoodsDtoEx;
import cn.cf.entity.FirmOrder;
import cn.cf.model.B2bBindGoods;
import cn.cf.service.B2bBindGoodsService;
import cn.cf.util.ArithUtil;
import cn.cf.util.KeyUtils;

@Service
public class B2bBindGoodsServiceImpl implements B2bBindGoodsService {

	@Autowired
	private B2bBindGoodsDaoEx b2bBindGoodsDaoEx;

	
	/**
	 * 分页查询拼团商品的列表
	 * 
	 * @param map
	 * @return
	 */
	@Override
	public PageModel<B2bBindGoodsDtoEx> searchBindGoodList(
			Map<String, Object> map) {
		PageModel<B2bBindGoodsDtoEx> pm = new PageModel<B2bBindGoodsDtoEx>();
		if (null == map) {
			map = new HashMap<String, Object>();
		}
		List<B2bBindGoodsDtoEx> list = b2bBindGoodsDaoEx.searchListEx(map);
		// 计算善品销售进度
		if (null != list && list.size() > 0) {
			B2bBindGoodsDtoEx temp = null;
			for (int i = 0; i < list.size(); i++) {
				temp = list.get(i);
				if (null != temp.getTotalBoxes() && null != temp.getBoxes()&& temp.getTotalBoxes() != 0) {
					temp.setProgress(ArithUtil.div((temp.getTotalBoxes() - temp.getBoxes())*100, temp.getTotalBoxes(),2));
				}
			}
		}
		int counts = b2bBindGoodsDaoEx.searchGridCount(map);
		pm.setDataList(list);
		pm.setTotalCount(counts);
		if (null != map.get("start")) {
			pm.setStartIndex(Integer.parseInt(map.get("start").toString()));
			pm.setPageSize(Integer.parseInt(map.get("limit").toString()));
		}
		return pm;
	}

	/**
	 * 设置为拼团商品
	 */
	@Override
	public boolean upToBindGoods(String bindPk, B2bGoodsDtoEx goodsDtoEx) {
		boolean result = true;
		B2bBindGoods model = new B2bBindGoods();
		model.setPk(KeyUtils.getUUID());
		model.setBindPk(bindPk);
		model.setGoodsPk(goodsDtoEx.getPk());
		model.setUpdateTime(new Date());
		model.setInsertTime(new Date());
		model.setWeight(goodsDtoEx.getTotalWeight());
		model.setBoxes(goodsDtoEx.getTotalBoxes());
		model.setTotalBoxes(goodsDtoEx.getTotalBoxes());
		if (b2bBindGoodsDaoEx.insert(model) > 0) {
			result = true;
		} else {
			result = false;
		}
		return result;
	}

	/**
	 * 根据bindPk查询拼团活动商品列表
	 */
	@Override
	public List<B2bBindGoodsDto> getBindGoodList(Map<String, Object> map) {
		return b2bBindGoodsDaoEx.searchGrid(map);
	}

	/**
	 * 根据bindPk删除拼团商品
	 */
	@Override
	public boolean delBindGoodsByBindPk(String bindPk) {
		if (b2bBindGoodsDaoEx.delBindGoodsByBindPk(bindPk) > 0) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public List<FirmOrder> searchFirmOrder(String bindPk, double price) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bindPk", bindPk);
		map.put("price", price);
		return b2bBindGoodsDaoEx.searchFirmOrder(map);
	}

	@Override
	public B2bBindGoodsDtoEx getByGoodPkAndBindPk(String goodsPk, String bindPk) {
		return b2bBindGoodsDaoEx.getByGoodPkAndBindPk(goodsPk, bindPk);
	}

	
	
	@Override
	public boolean delBindGoodsByGoodsPk(String goodsPk) {
		if (b2bBindGoodsDaoEx.delBindGoodsByGoodsPk(goodsPk) > 0) {
			return true;
		} else {
			return false;
		}
	}

	
	/**
	 * 拼团中商品
	 */
	@Override
	public List<B2bBindGoodsDtoEx> searchGoodsListOnBind(Map<String, Object> map) {
		map.put("isDelete", 1);
		if (null!=map.get("productPk") && !"".equals(map.get("productPk").toString())) {
			String[] productPk = map.get("productPk").toString().split(",");
			map.put("productPk", productPk);
		}
		if (null!=map.get("specPk") && !"".equals(map.get("specPk").toString())) {
			String[] specPk = map.get("specPk").toString().split(",");
			map.put("specPk", specPk);
		}
		if (null!=map.get("seriesPk") && !"".equals(map.get("seriesPk").toString())) {
			String[] seriesPk = map.get("seriesPk").toString().split(",");
			map.put("seriesPk", seriesPk);
		}
		if (null!=map.get("gradePk") && !"".equals(map.get("gradePk").toString())) {
			String[] gradePk = map.get("gradePk").toString().split(",");
			map.put("gradePk", gradePk);
		}
		if (null!=map.get("varietiesPk") && !"".equals(map.get("varietiesPk").toString())) {
			String[] varietiesPk = map.get("varietiesPk").toString().split(",");
			map.put("varietiesPk", varietiesPk);
		}
		if (null!=map.get("seedvarietyPk") && !"".equals(map.get("seedvarietyPk").toString())) {
			String[] seedvarietyPk = map.get("seedvarietyPk").toString().split(",");
			map.put("seedvarietyPk", seedvarietyPk);
		}
		return b2bBindGoodsDaoEx.searchGoodsListOnBind(map);
	}

	@Override
	public int searchGoodsListOnBindCount(Map<String, Object> map) {
		map.put("isDelete", 1);
		return b2bBindGoodsDaoEx.searchGoodsListOnBindCount(map);
	}

	@Override
	public List<B2bCartDtoEx> searchBindGoodsBoxes(List<B2bCartDtoEx> carts) {
		 for(B2bCartDtoEx c:carts){
			 B2bBindGoodsDtoEx bg=b2bBindGoodsDaoEx.getByGoodPkAndBindPk( c.getGoodsPk(),c.getBindPk());
			 if(null!=bg){
				 c.setTotalBoxes(bg.getBoxes());
//				 c.setSalePrice(bg.getBindProductPrice()==null?"":bg.getBindProductPrice().toString());
//				 c.setTotalPrice(ArithUtil.div(ArithUtil.mul(ArithUtil.mul(Double.parseDouble(c.getSalePrice()),Double.parseDouble(c.getTareWeight()) ), c.getBoxes()), 1000));
				 
				 
				
			 }
		 }
		return carts;
	}
	
	/**
	 * 检查商品是否在处于上架的拼团活动中
	 */
	@Override
	public boolean checkGoodsOnUpBinding(String goodsPk) {
		if (b2bBindGoodsDaoEx.checkGoodsOnUpBinding(goodsPk)>0) {
			return true;
		} else {
			return false;
		}
		
	}

	 
	
	
	@Override
	public void updateEx(B2bBindGoods model) {
		b2bBindGoodsDaoEx.updateEx(model);
	}


}
