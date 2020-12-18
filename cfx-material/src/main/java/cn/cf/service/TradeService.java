package cn.cf.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.cf.dto.B2bPaymentDto;
import cn.cf.dto.SysCompanyBankcardDto;
import cn.cf.entity.B2bContractDtoMa;
import cn.cf.entity.B2bOrderDtoMa;
import cn.cf.entity.B2bOrderGoodsDtoMa;
import cn.cf.model.B2bContract;
import cn.cf.model.B2bOrder;

public interface TradeService {
    
    /**
     * 订单修改支付状态
     * @param order
     * @param bankName
     * @param bankAccount
     * @return
     */
    @Transactional
    B2bOrderDtoMa updateOrderStatus(B2bOrder order,SysCompanyBankcardDto card);

    
    /**
     * 合同修改支付状态
     * @param order
     * @param bankName
     * @param bankAccount
     * @return
     */
    @Transactional
    B2bContractDtoMa updateContractStatus(B2bContract contract,SysCompanyBankcardDto card);

    /**
     * 查询订单商品
     *
     * @param orderNumber
     * @return
     */
    List<B2bOrderGoodsDtoMa> searchOrderGoodsList(String orderNumber);
    /**
     * 查询订单信息
     * @param orderNumber
     * @return
     */
    B2bOrderDtoMa getOrder(String orderNumber);
    /**
     * 查询合同订单信息
     * @param orderNumber
     * @return
     */
    B2bOrderDtoMa getContract(String contractNo);

    /**
     * 获取合同详细内容
     * @param contractNo
     * @return
     */
    List<B2bOrderGoodsDtoMa> getContractGoods(String contractNo);
    
    /**
     * 查询支付方式
     * @param type
     * @return
     */
    B2bPaymentDto searchPayment(Integer type);
}
