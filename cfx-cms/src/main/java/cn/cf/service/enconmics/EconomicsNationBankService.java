package cn.cf.service.enconmics;

import java.util.List;

import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.entity.EconomicsBank;

public interface EconomicsNationBankService {

	List<EconomicsBank> getNationBankList();

	EconomicsBank getByPk(String pk);

	PageModel<EconomicsBank> getNationBank(QueryModel<EconomicsBank> qm);

	int updateStatus(EconomicsBank economicsBank);

	int update(EconomicsBank economicsBank);

	int insert(EconomicsBank economicsBank);

}
