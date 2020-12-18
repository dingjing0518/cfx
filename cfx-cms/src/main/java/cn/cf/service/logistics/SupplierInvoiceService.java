package cn.cf.service.logistics;

import java.util.Map;

import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.dto.SupplierInvoiceDto;
import cn.cf.model.SearchInvoice;

public interface SupplierInvoiceService {
    /**
     * 供应商发票管理
     * 
     * @param qm
     * @param i
     * @return
     */
    PageModel<SupplierInvoiceDto> searchSupplierInvoiceList(QueryModel<SearchInvoice> qm, int i);

    /**
     * 编辑/新增供应商发票
     * 
     * @param map
     * @return
     */
    int updateSupplierInvoice(Map<String, Object> map);

}
