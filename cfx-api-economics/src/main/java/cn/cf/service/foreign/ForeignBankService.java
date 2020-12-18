package cn.cf.service.foreign;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bEconomicsGoodsDto;
import cn.cf.dto.B2bLoanNumberDto;
import cn.cf.entity.B2bCreditGoodsDtoMa;

/**
 * 
 * @description:此service提供给别的系统操作银行信息
 * @author FJM
 * @date 2018-5-17 下午7:33:31
 */
public interface ForeignBankService {
	
	/**
	 * 取消银行订单
	 * @return
	 */
	Map<String,Object> delLoanOrder(String orderNumber);
	
	/**
	 * 查询金额产品及对应的供应商名称
	 * @param purchaserPk
	 * @param supplierPk
	 * @return
	 */
	List<B2bCreditGoodsDtoMa> searchCreditGoodsAndBankCard(String purchaserPk,String supplierPk);
	
	/**
	 * 获取单个金融产品
	 * @param pk
	 * @return
	 */
	B2bEconomicsGoodsDto getEconomicsGoodsByPk(Map<String, Object> map);
	
	/**
     * 返回借据状态
     * @return
     */
	B2bLoanNumberDto getB2bLoanNumberDto(String orderNumber);
}
