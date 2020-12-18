package cn.cf.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cf.dao.B2bBindGoodsDaoEx;
import cn.cf.dto.B2bBindGoodsDto;
import cn.cf.dto.B2bBindGoodsDtoEx;
import cn.cf.dto.B2bGoodsDtoEx;
import cn.cf.model.B2bBindGoods;
import cn.cf.service.B2bBindGoodsService;
import cn.cf.util.KeyUtils;

@Service
public class B2bBindGoodsServiceImpl implements B2bBindGoodsService {

	@Autowired
	private B2bBindGoodsDaoEx b2bBindGoodsDaoEx;

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

	@Override
	public boolean delBindGoodsByGoodsPk(String goodsPk) {
		if (b2bBindGoodsDaoEx.delBindGoodsByGoodsPk(goodsPk) > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public B2bBindGoodsDtoEx getBindGoodDtoExByGoodsPk(String goodsPk) {
		return b2bBindGoodsDaoEx.getBindGoodDtoExByGoodsPk(goodsPk);
	}

}
