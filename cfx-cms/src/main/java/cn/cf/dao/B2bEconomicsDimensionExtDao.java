package cn.cf.dao;

import cn.cf.model.B2bEconomicsDimensionModel;

public interface B2bEconomicsDimensionExtDao {
    B2bEconomicsDimensionModel getByItem(String item);

    int insert(B2bEconomicsDimensionModel b2bEconomicsDimensionModel);

    int update(B2bEconomicsDimensionModel b2bEconomicsDimensionModel);


}
