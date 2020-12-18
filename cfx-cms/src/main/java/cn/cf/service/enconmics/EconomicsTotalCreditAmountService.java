package cn.cf.service.enconmics;

import java.util.List;

import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.entity.EconTotalCreditAmount;

public interface EconomicsTotalCreditAmountService {

	List<EconTotalCreditAmount> getTotalCreditAmountList();

	EconTotalCreditAmount getByPk(String pk);

	PageModel<EconTotalCreditAmount> getTotalCreditAmount(QueryModel<EconTotalCreditAmount> qm);

	int updateStatus(EconTotalCreditAmount econTotalCreditAmount);

	int update(EconTotalCreditAmount econTotalCreditAmount);

	int insert(EconTotalCreditAmount econTotalCreditAmount);

}
