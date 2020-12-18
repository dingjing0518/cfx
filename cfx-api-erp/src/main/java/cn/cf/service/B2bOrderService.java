package cn.cf.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.cf.dto.B2bOrderDtoEx;
import cn.cf.dto.B2bOrderGoodsDtoEx;
import cn.cf.dto.B2bTokenDto;
import cn.cf.entity.B2bCorrespondenceInfoEx;
import cn.cf.entity.ContractGoodsSync;
import cn.cf.entity.ContractStatusSync;
import cn.cf.entity.ContractSync;

public interface B2bOrderService {

	B2bOrderDtoEx getByOrderNumber(String orderNumber);
	
	/**
	 * 合同同步
	 * @param contractSync
	 * @return
	 * @throws Exception
	 */
	@Transactional
	String updateContract(ContractSync contractSync)throws Exception;
	
	/**
	 * 合同发货
	 * @param contractSync
	 * @return
	 * @throws Exception
	 */
	@Transactional
	String shipmentContractGoods(ContractGoodsSync contractSync)throws Exception;
	
	/**
	 * 合同审批状态
	 * @param contractSync
	 * @return
	 * @throws Exception
	 */
	String updateContractStatus(ContractStatusSync contractSync)throws Exception;

	/**
	 * 同步客户业务员信息
	 * @param b2bCorrespondenceInfoEx
	 * @param tokenDto
	 * @return
	 */
	String updateCorrespondenceInfo(B2bCorrespondenceInfoEx b2bCorrespondenceInfoEx, B2bTokenDto tokenDto);
	
	/**
	 * 编辑客户业务员信息
	 * @param b2bCorrespondenceInfoEx
	 * @param tokenDto
	 * @return
	 */
	String editCorrespondenceInfo(B2bCorrespondenceInfoEx b2bCorrespondenceInfoEx, B2bTokenDto b2btoken);
	/**
	 * 修改订单数据
	 * @param orderNumber 订单号
	 * @param orderStatus 订单状态
	 * @param newOrderGoods 新的商品列表
	 */
	void updateOrderAndOrderGoods(String orderNumber,Integer orderStatus,List<B2bOrderGoodsDtoEx> newOrderGoods);


	/**
	 * 调用CMS方法
	 * @param contractNo 合同号
	 * @param type 类型，1：化纤订单；2：纱线订单；3：化纤合同
	 * @param block 类型，cf:化纤；sx:纱线
	 */
	void afterCmsContract(String contractNo,Integer type,String block);

}
