/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2019
 */

package cn.cf.dao;

import cn.cf.dto.B2bProductPriceIndexDto;
import cn.cf.dto.B2bProductPriceIndexExtDto;
import cn.cf.entity.ProductPriceIndexEntry;
import cn.cf.model.B2bProductPriceIndex;

import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bProductPriceIndexExtDao extends B2bProductPriceIndexDao{

	int updateObj(B2bProductPriceIndex dto);
	List<B2bProductPriceIndexExtDto> searchGridExt(Map<String, Object> map);
	int searchGridExtCount(Map<String, Object> map);

	List<ProductPriceIndexEntry> getLoanNumberInfo();
	B2bProductPriceIndexDto getExtByProductPk(String productPk);
}
