package cn.cf.service.enconmics;

import java.util.List;

import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.entity.EconCreditOrginze;

public interface EconomicsCreditOrginzeService {

	List<EconCreditOrginze> getCreditOrginzeList();

	EconCreditOrginze getByPk(String pk);

	PageModel<EconCreditOrginze> getCreditOrginze(QueryModel<EconCreditOrginze> qm);

	int updateStatus(EconCreditOrginze econCreditOrginze);

	int update(EconCreditOrginze econCreditOrginze);

	int insert(EconCreditOrginze econCreditOrginze);

}
