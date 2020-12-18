package cn.cf.service.enconmics;

import java.util.List;

import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.entity.EconomicsBank;

public interface EconomicsCommericalBankService {

	List<EconomicsBank> getCommericalBankList();

	EconomicsBank getByPk(String pk);

	PageModel<EconomicsBank> getCommericalBank(QueryModel<EconomicsBank> qm);

	int updateStatus(EconomicsBank economicsCommericalBank);

	int update(EconomicsBank economicsCommericalBank);

	int insert(EconomicsBank economicsCommericalBank);


}
