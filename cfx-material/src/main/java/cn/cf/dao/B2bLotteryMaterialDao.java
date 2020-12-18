/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.B2bLotteryMaterial;
import cn.cf.dto.B2bLotteryMaterialDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bLotteryMaterialDao {
	int insert(B2bLotteryMaterial model);
	int update(B2bLotteryMaterial model);
	int delete(String id);
	List<B2bLotteryMaterialDto> searchGrid(Map<String, Object> map);
	List<B2bLotteryMaterialDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bLotteryMaterialDto getByPk(java.lang.String pk); 
	 B2bLotteryMaterialDto getByName(java.lang.String name); 
	 int updateMaterial(B2bLotteryMaterialDto dto);

}
