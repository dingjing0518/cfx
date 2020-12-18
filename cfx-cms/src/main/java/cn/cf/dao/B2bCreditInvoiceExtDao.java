/*
 * Powered By [9client]
 * Web Site: http://www.9client.com
 * Since 2012 - 2019
 */

package cn.cf.dao;

import cn.cf.dto.B2bCreditInvoiceDto;
import cn.cf.dto.B2bCreditInvoiceExtDto;
import cn.cf.model.B2bCreditInvoice;

import java.util.List;
import java.util.Map;

/**
 *
 * @author 
 * @version 1.0
 * @since 1.0
 * */
public interface B2bCreditInvoiceExtDao extends B2bCreditInvoiceDao {
	List<B2bCreditInvoiceExtDto> searchGridExt(Map<String, Object> map);
	int searchGridExtCount(Map<String, Object> map);
	List<B2bCreditInvoiceExtDto> searchExportList(Map<String, Object> map);
}
