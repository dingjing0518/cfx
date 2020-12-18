/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2019
 */

package cn.cf.dao;

import cn.cf.dto.B2bBillGoodsDto;
import cn.cf.dto.B2bBillGoodsExtDto;
import cn.cf.model.B2bBillGoods;

import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bBillGoodsExtDao extends B2bBillGoodsDao {
	int updateObj(B2bBillGoodsExtDto model);
	List<B2bBillGoodsExtDto> searchGridExt(Map<String, Object> map);
	int searchGridExtCount(Map<String, Object> map);
	List<B2bBillGoodsDto> isExitGoods(Map<String, Object> map);

}
