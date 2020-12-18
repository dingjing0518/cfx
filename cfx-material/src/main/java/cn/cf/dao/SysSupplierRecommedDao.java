/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2019
 */

package cn.cf.dao;

import cn.cf.model.SysSupplierRecommed;
import cn.cf.dto.SysSupplierRecommedDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author
 * @version 1.0
 * @since 1.0
 * */
public interface SysSupplierRecommedDao {
	int insert(SysSupplierRecommed model);
	int update(SysSupplierRecommed model);
	int delete(String id);
	List<SysSupplierRecommedDto> searchGrid(Map<String, Object> map);
	List<SysSupplierRecommedDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	SysSupplierRecommedDto getByPk(java.lang.String pk);
	SysSupplierRecommedDto getByStoreName(java.lang.String storeName);
	SysSupplierRecommedDto getByStorePk(java.lang.String storePk);
	SysSupplierRecommedDto getByUrl(java.lang.String url);
	SysSupplierRecommedDto getByPosition(java.lang.String position);
	SysSupplierRecommedDto getByLinkUrl(java.lang.String linkUrl);
	SysSupplierRecommedDto getByBlock(java.lang.String block);
	SysSupplierRecommedDto getByBrandPk(java.lang.String brandPk);
	SysSupplierRecommedDto getByBrandName(java.lang.String brandName);
	SysSupplierRecommedDto getByPlatform(java.lang.String platform);
	SysSupplierRecommedDto getByRegion(java.lang.String region);

}
