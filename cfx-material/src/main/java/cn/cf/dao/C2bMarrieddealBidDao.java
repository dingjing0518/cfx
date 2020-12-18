/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2018
 */

package cn.cf.dao;

import cn.cf.model.C2bMarrieddealBid;
import cn.cf.dto.C2bMarrieddealBidDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface C2bMarrieddealBidDao {
	int insert(C2bMarrieddealBid model);
	int update(C2bMarrieddealBid model);
	int delete(String id);
	List<C2bMarrieddealBidDto> searchGrid(Map<String, Object> map);
	List<C2bMarrieddealBidDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 C2bMarrieddealBidDto getByPk(java.lang.String pk); 
	 C2bMarrieddealBidDto getByMarrieddealPk(java.lang.String marrieddealPk); 
	 C2bMarrieddealBidDto getByMemberPk(java.lang.String memberPk); 
	 C2bMarrieddealBidDto getByMemberName(java.lang.String memberName); 
	 C2bMarrieddealBidDto getByMeno(java.lang.String meno); 
	 C2bMarrieddealBidDto getBySupplierPk(java.lang.String supplierPk); 
	 C2bMarrieddealBidDto getBySupplierName(java.lang.String supplierName); 
	 C2bMarrieddealBidDto getByContacts(java.lang.String contacts); 
	 C2bMarrieddealBidDto getByContactsTel(java.lang.String contactsTel); 
	 C2bMarrieddealBidDto getByPackNumber(java.lang.String packNumber); 
	 C2bMarrieddealBidDto getByBatchNumber(java.lang.String batchNumber); 
	 C2bMarrieddealBidDto getByGoodsPk(java.lang.String goodsPk); 
	 C2bMarrieddealBidDto getByGoodsName(java.lang.String goodsName); 
	 C2bMarrieddealBidDto getByStorePk(java.lang.String storePk); 
	 C2bMarrieddealBidDto getByStoreName(java.lang.String storeName); 

}
