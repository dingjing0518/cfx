package cn.cf.dao;

import cn.cf.dto.B2bBillCusgoodsDto;
import cn.cf.dto.B2bBillCusgoodsExtDto;

import java.util.List;
import java.util.Map;

public interface B2bBillCusgoodsExtDao  extends B2bBillCusgoodsDao{

	void deleteByCompanyPk(String companyPk);

	List<B2bBillCusgoodsExtDto> searchGridExt(Map<String, Object> map);
	int searchGridExtCount(Map<String, Object> map);

	int updateObj(B2bBillCusgoodsExtDto dto);

	B2bBillCusgoodsDto getCusGoodsByMap(Map<String, Object> param);


}
