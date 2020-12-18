package cn.cf.service.logistics;

import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.dto.PurchaserInvoiceDto;
import cn.cf.model.SearchInvoice;

public interface PurchaserInvoiceService {
    /**
     * 采购商发票列表
     * 
     * @param qm
     * @param i
     * @return
     */
    PageModel<PurchaserInvoiceDto> searchPurchaserInvoiceList(QueryModel<SearchInvoice> qm, int i);

    /**
     * 新增/编辑发票
     * 
     * @param map
     * @return
     */
    @Transactional
    int updateInvoice(Map<String, Object> map);

}
