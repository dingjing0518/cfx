package cn.cf.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import cn.cf.PageModel;
import cn.cf.common.RestCode;
import cn.cf.dto.B2bBrandDto;
import cn.cf.dto.B2bGoodsBrandDto;

public interface B2bBrandService  {

	List<B2bBrandDto> searchBrand(Map<String, Object> map);
	List<B2bGoodsBrandDto> searchGoodsBrand(Map<String, Object> map);
	
	/**
	 * 添加品牌
	 * @param brandName
	 * @param storePk
	 * @param storeName
	 * @param memberPk 
	 * @return
	 */
	@Transactional
	RestCode addBrand(String brandName, String storePk,String companyPk, String storeName, String memberPk);
	
	/**
	 * 我的厂家品牌
	 * @param map
	 * @return
	 */
	PageModel<B2bGoodsBrandDto> searchMyGoodsBrandList(Map<String, Object> map);
	
	void updateGoodsBrand(B2bGoodsBrandDto dto);
	PageModel<B2bBrandDto> searchBrandPage(Map<String, Object> map);
	List<B2bBrandDto> searchBrandNameByBrandPks(String brandPks);

}
