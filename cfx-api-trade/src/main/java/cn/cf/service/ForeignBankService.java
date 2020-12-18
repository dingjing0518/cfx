package cn.cf.service;

import java.util.List;
import java.util.Map;

import cn.cf.dto.B2bBillInventoryDto;
import cn.cf.dto.B2bBillOrderDto;
import cn.cf.dto.B2bEconomicsGoodsDto;
import cn.cf.dto.B2bLoanNumberDto;
import cn.cf.dto.B2bOnlinepayRecordDto;
import cn.cf.entity.B2bBillCusgoodsDtoMa;
import cn.cf.entity.B2bCreditGoodsDtoMa;

/**
 * 
 * @description:此service由金融系统提供 具体实现见金融系统
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
	 * 查询金额产品
	 * @param companyPk
	 * @return
	 */
	List<B2bCreditGoodsDtoMa> searchList(String companyPk,String supplierPk,String storePk);
	
	/**
	 * 获取单个金融产品
	 * @param pk
	 * @return
	 */
	B2bEconomicsGoodsDto getEconomicsGoodsByPk(String name,Integer type);
	/**
     * 返回借据状态
     * @return
     */
	B2bLoanNumberDto getB2bLoanNumberDto(String orderNumber);
	
	/**
	 * 取消线上支付订单
	 * @return
	 */
	Map<String,Object> delOnlinePayOrder(String orderNumber);
	/**
	 * 返回线上支付状态
	 * @param orderNumber
	 * @return
	 */
	B2bOnlinepayRecordDto getOnlinepayRecordDto(String orderNumber);
	/**
	 * 查询票据产品
	 * @param companyPk
	 * @return
	 */
	List<B2bBillCusgoodsDtoMa> searchBillGoodsList(String companyPk,String supplierPk);
	
	/**
	 * 取消线上支付订单
	 * @return
	 */
	Map<String,Object> delBillPayOrder(String orderNumber);
	/**
	 * 返回票据订单支付状态
	 * @param orderNumber
	 * @return
	 */
	B2bBillOrderDto getBillOrder(String orderNumber);
	/**
	 * 完成票据
	 * @param orderNumber
	 */
	void completeBillOrder(String orderNumber);
	
	/**
	 * 返回票据订单支付状态
	 * @param orderNumber
	 * @return
	 */
	List<B2bBillInventoryDto> searchBillInventoryList(String orderNumber);
}
