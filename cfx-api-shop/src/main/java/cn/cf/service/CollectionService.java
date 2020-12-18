package cn.cf.service;

import java.util.Map;

import cn.cf.PageModel;
import cn.cf.common.RestCode;
import cn.cf.dto.B2bGoodsDtoEx;
import cn.cf.entity.CollectionCompany;
import cn.cf.entity.CollectionGoods;
import cn.cf.entity.Sessions;

public interface CollectionService {
	
	/**
	 * 收藏商品
	 * @param goodsPk
	 * @param session
	 * @return
	 */
	RestCode collectGoods(String goodsPk, Sessions session);

	/**
	 * 收藏公司
	 * @param companyPk
	 * @param session
	 * @return
	 */
	RestCode collectCompany(String companyPk, Sessions session);

	/**
	 * 收藏店铺
	 * @param storePk
	 * @param session
	 * @return
	 */
	RestCode collectStore(String storePk, Sessions session);


	/**
	 * 是否收藏店铺
	 */
	Boolean isCollection(String memberPk,String storePk);

	/**
	 * 公司收藏列表
	 */
	PageModel<CollectionCompany> searchCompanyList(Map<String, Object> map);


	/**
	 * 商品收藏列表
	 */
	PageModel<B2bGoodsDtoEx> searchGoodList(Map<String, Object> map);

	void removeByUserId(String sessionId) throws Exception;

	RestCode removeByGoodsId(Sessions session, CollectionGoods dto);

	RestCode removeByCompanyIds(CollectionCompany dto, Sessions session);
}
