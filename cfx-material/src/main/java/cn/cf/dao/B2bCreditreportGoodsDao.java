/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2019
 */

package cn.cf.dao;

import cn.cf.model.B2bCreditreportGoods;
import cn.cf.dto.B2bCreditreportGoodsDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bCreditreportGoodsDao {
	int insert(B2bCreditreportGoods model);
	int update(B2bCreditreportGoods model);
	int delete(String id);
	List<B2bCreditreportGoodsDto> searchGrid(Map<String, Object> map);
	List<B2bCreditreportGoodsDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bCreditreportGoodsDto getByPk(java.lang.String pk); 
	 B2bCreditreportGoodsDto getByName(java.lang.String name);
	B2bCreditreportGoodsDto getByShotName(java.lang.String shotName);

}
