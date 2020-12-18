package cn.cf.service.enconmics;

import java.util.List;

import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.entity.EconomicsBuildProperty;

public interface EconomicsDimensionBuildPropertyService {

	PageModel<EconomicsBuildProperty> getbuildProperty(QueryModel<EconomicsBuildProperty> qm);

	EconomicsBuildProperty getByPk(String pk);

	int update(EconomicsBuildProperty economicsBuildProperty);

	int insert(EconomicsBuildProperty economicsBuildProperty);

	List<EconomicsBuildProperty> getBuildPropertyList();

	int updateStatus(EconomicsBuildProperty economicsBuildProperty);

}
