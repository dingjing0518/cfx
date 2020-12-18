package cn.cf.service.enconmics;

import cn.cf.entity.B2bEconomicsImprovingdataEntity;

public interface EconomicsImprovingdataService {

	B2bEconomicsImprovingdataEntity getMongoByEconomCustPk(String processInstanceId);

}
