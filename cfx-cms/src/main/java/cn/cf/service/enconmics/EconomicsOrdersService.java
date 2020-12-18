package cn.cf.service.enconmics;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.dto.B2bContractExtDto;
import cn.cf.dto.B2bContractGoodsExtDto;
import cn.cf.dto.B2bLoanNumberDto;
import cn.cf.dto.B2bLoanNumberExtDto;
import cn.cf.dto.B2bOrderExtDto;
import cn.cf.dto.B2bOrderGoodsExtDto;
import cn.cf.dto.B2bPaymentDto;
import cn.cf.entity.B2bRepaymentRecord;
import cn.cf.entity.EconomicsProductOrder;
import cn.cf.entity.OrderDataTypeParams;
import cn.cf.entity.OrderRecord;
import cn.cf.model.B2bLoanNumber;
import cn.cf.model.ManageAccount;

public interface EconomicsOrdersService {

    /**
     * 付款方式
     */
    List<B2bPaymentDto> getPaymentList();

    /**
     * 授信订单列表
     * 
     * @param qm
     * @param limitFlag
     * @return
     */
    PageModel<B2bLoanNumberExtDto> creditOrderList(QueryModel<B2bLoanNumberExtDto> qm, Integer limitFlag);


    /**
     * 保存导出授信订单列表记录
     *
     * @return
     */
    int saveCreditOrderToOss(OrderDataTypeParams params,ManageAccount account);


    /**
     * 合同管理列表
     * 
     * @param qm
     * @return
     */
    PageModel<B2bContractExtDto> contractList(QueryModel<B2bContractExtDto> qm, ManageAccount accout);

    /**
     * 合同管理订单追踪
     *
     * @param contractNo
     * @return
     */
    List<OrderRecord> contractTrackList(String contractNo);

    /**
     * 合同商品列表
     * 
     * @param qm
     * @return
     */
    PageModel<B2bContractGoodsExtDto> contractGoodsList(QueryModel<B2bContractGoodsExtDto> qm);

    /**
     * 更新合同订单
     * 
     * @param dto
     * @return
     */
    int updateContract(B2bContractExtDto dto);

    /**
     * 查询还款记录
     * 
     * @param qm
     * @return
     */
    PageModel<B2bRepaymentRecord> getB2bRepaymentRecordList(QueryModel<B2bRepaymentRecord> qm);

    /**
     * 化纤贷订单列表
     * 
     * @param qm
     * @param limitFlag
     * @return
     */
    PageModel<B2bOrderExtDto> fiberLoanOrderList(QueryModel<B2bOrderExtDto> qm, int limitFlag);

    /**
     * 授信管理-订单管理 订单列表
     * 
     * @param qm
     * @param account 
     * @return
     */
    PageModel<B2bOrderExtDto> searchOrderList(QueryModel<B2bOrderExtDto> qm, ManageAccount account);

    /**
     * 授信管理-订单管理 订单商品
     * 
     * @param qm
     * @return
     */
    PageModel<B2bOrderGoodsExtDto> getOrderGoods(QueryModel<B2bOrderGoodsExtDto> qm,ManageAccount account);

    /**
     * 授信管理-订单管理 导出订单
     * 
     * @param qm
     * @param account 
     * @return
     */
    List<B2bOrderExtDto> exportOrderListNew(QueryModel<B2bOrderExtDto> qm, ManageAccount account);

    /**
     * 根据订单号查询金融产品
     * 
     * @param orderNumber
     * @return
     */
    B2bOrderExtDto getEconProductByOrderNumber(String orderNumber);

    /**
     * 查询金融订单
     * 
     * @param map
     * @return
     */
    List<EconomicsProductOrder> searchEconomicsOrders(Map<String, Object> map);

    /**
     * 编辑银行客户号
     * 
     * @param loanNumber
     * @return
     */
    int updateLoanNumber(B2bLoanNumber loanNumber);

    /**
     * 合同下载
     * 
     * @param contractNo
     * @param req
     * @param resp
     */
    void downContractOrder(String contractNo, HttpServletRequest req, HttpServletResponse resp,String block);
    /**
     * 计算未还利息
     * @param amount
     * @param loanStartTime
     * @return
     */
    Map<String, Object>  getRepayLoan( String orderNumber,double amount);
    
    /**
     * 营销合同订单
     * @param qm
     * @param accout
     * @return
     */
	PageModel<B2bContractExtDto> searchMContractList(QueryModel<B2bContractExtDto> qm, ManageAccount accout);

	
}
