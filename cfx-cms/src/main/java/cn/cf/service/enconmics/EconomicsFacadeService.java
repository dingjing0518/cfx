package cn.cf.service.enconmics;

import cn.cf.PageModel;
import cn.cf.dto.B2bEconomicsCustomerDto;

public interface EconomicsFacadeService {
    /**
     * 审批列表
     * @param start
     * @param limit
     * @param groupId
     * @param accountPk 
     * @return
     */
	PageModel<B2bEconomicsCustomerDto> searchCustomer(int start, int limit, String groupId, String accountPk);

}
