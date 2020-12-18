package cn.cf.service.platform;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bCreditGoodsDto;
import cn.cf.entity.B2bCreditGoodsDtoMa;

public interface B2bCreditGoodsService {
	
	/**
	 * 根据公司查询已审核通过的金融产品
	 * @param companyPk
	 * @return
	 */
	List<B2bCreditGoodsDtoMa> searchCreditGoodsByCompany(String companyPk);
	/**
	 * 根据公司查询所有的金融产品
	 * @param companyPk
	 * @return
	 */
	List<B2bCreditGoodsDtoMa> searchAllCreditGoodsByCompany(String companyPk);
	/**
	 * 根据pk查询公司授信产品
	 * @param pk
	 * @return
	 */
	B2bCreditGoodsDtoMa searchCreditGoodsByPk(String pk);
	
	List<B2bCreditGoodsDto> searchList(Map<String,Object> map);
	
	void updateByCreditGoods(B2bCreditGoodsDto dto);
	/**
	 * 根据客户查询授信银行
	 * @param companyPk
	 * @return
	 */
	List<B2bCreditGoodsDto> searchListGroupBank(String companyPk);
	
	
}
