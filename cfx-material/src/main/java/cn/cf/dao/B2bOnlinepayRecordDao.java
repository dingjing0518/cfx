/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2019
 */

package cn.cf.dao;

import cn.cf.model.B2bOnlinepayRecord;
import cn.cf.dto.B2bOnlinepayRecordDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bOnlinepayRecordDao {
	int insert(B2bOnlinepayRecord model);
	int update(B2bOnlinepayRecord model);
	int delete(String id);
	List<B2bOnlinepayRecordDto> searchGrid(Map<String, Object> map);
	List<B2bOnlinepayRecordDto> searchList(Map<String, Object> map);
	int searchGridCount(Map<String, Object> map);
	 B2bOnlinepayRecordDto getByOrderNumber(java.lang.String orderNumber); 
	 B2bOnlinepayRecordDto getBySerialNumber(java.lang.String serialNumber); 
	 B2bOnlinepayRecordDto getByPurchaserPk(java.lang.String purchaserPk); 
	 B2bOnlinepayRecordDto getByPurchaserName(java.lang.String purchaserName); 
	 B2bOnlinepayRecordDto getBySupplierName(java.lang.String supplierName); 
	 B2bOnlinepayRecordDto getBySupplierPk(java.lang.String supplierPk); 
	 B2bOnlinepayRecordDto getByReceivablesAccount(java.lang.String receivablesAccount); 
	 B2bOnlinepayRecordDto getByReceivablesAccountName(java.lang.String receivablesAccountName); 
	 B2bOnlinepayRecordDto getByOnlinePayGoodsPk(java.lang.String onlinePayGoodsPk); 
	 B2bOnlinepayRecordDto getByOnlinePayGoodsName(java.lang.String onlinePayGoodsName); 
	 B2bOnlinepayRecordDto getByShotName(java.lang.String shotName);

}
