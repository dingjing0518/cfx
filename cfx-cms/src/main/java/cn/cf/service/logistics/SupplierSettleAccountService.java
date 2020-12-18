package cn.cf.service.logistics;

import java.util.Map;
import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.dto.SettleAccountDto;
import cn.cf.model.SearchInvoice;

public interface SupplierSettleAccountService {
    /**
     * 供应商结算列表
     * 
     * @param qm
     * @param i
     * @return
     */
    PageModel<SettleAccountDto> searchSupplierSettleAccountList(QueryModel<SearchInvoice> qm, int i);

    /**
     * 更新：结算
     * 
     * @param map
     * @return
     */
    int updateSettleFreight(Map<String, Object> map);

}
