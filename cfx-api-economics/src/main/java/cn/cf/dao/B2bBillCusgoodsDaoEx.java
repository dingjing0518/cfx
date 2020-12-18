/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2019
 */

package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bBillCusgoodsDto;
import cn.cf.dto.B2bBillCusgoodsDtoEx;


/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bBillCusgoodsDaoEx  extends B2bBillCusgoodsDao{

	List<B2bBillCusgoodsDtoEx> searchBillCusGoodsList(Map<String, Object> map);

	int  updateBillCusgoods(B2bBillCusgoodsDto cusg);
	
	int updateBillCusgoodsAmt();
 

}
