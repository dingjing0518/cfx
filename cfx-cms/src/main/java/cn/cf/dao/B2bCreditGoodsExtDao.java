package cn.cf.dao;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bCreditGoodsDto;
import cn.cf.dto.B2bCreditGoodsDtoExt;
import cn.cf.dto.B2bCreditGoodsExtDto;
import cn.cf.model.B2bCreditGoods;

public interface B2bCreditGoodsExtDao {
    List<B2bCreditGoodsExtDto> searchGrid(Map<String, Object> map);

    List<B2bCreditGoodsExtDto> searchList(Map<String, Object> map);

    int searchGridCount(Map<String, Object> map);

    int updatePlatformAmount(B2bCreditGoodsExtDto creditGoodsExtDto);


	int deleteByCompanyPk(String companyPk);

	int searchCreditGoodsCount(Map<String, Object> map);

	List<B2bCreditGoodsDtoExt> searchCreditGoodsGridExt(Map<String, Object> map);

	B2bCreditGoods getByCondition(Map<String, Object> maps);

	int updateB2bCreditGoods(B2bCreditGoods b2bCreditGoods);

	int updateCreditGoodsStatus(Map<String, Object> map);
	
	List<B2bCreditGoodsDto> searchListByCreditPk(Map<String, Object> map);

	List<B2bCreditGoodsExtDto> searchCreditGoodsByCreditPk(Map<String, Object> map);

	void insert(B2bCreditGoods b2bCreditGoods);

	List<B2bCreditGoodsDto> getByCompanyPk(String companyPk);

	int updateCreditIsVisable(B2bCreditGoodsExtDto cExtDto);

	B2bCreditGoodsDto getByPk(String pk);

	B2bCreditGoodsDto getByMap(Map<String, Object> map);
	
	void deleteByPk(String pk);


	
}
