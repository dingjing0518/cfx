package cn.cf.service.logistics;

import cn.cf.PageModel;
import cn.cf.common.QueryModel;
import cn.cf.dto.LogisticsRouteDto;
import cn.cf.entity.DeliveryForm;
import cn.cf.entity.LogisticsOrderAddress;
import cn.cf.model.LgOrderGridModel;
import cn.cf.model.LgOrderSearchModel;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface LogisticsOrderService {

    /**
     * 正常/异常运货单列表
     * 
     * @param qm
     * @param i
     * @param accountPk 
     * @return
     */
    PageModel<LgOrderGridModel> getlgOrder(QueryModel<LgOrderSearchModel> qm, int i, String accountPk);

    /**
     * 运货单详情
     * 
     * @param pk
     * @param accountPk 
     * @return
     */
    HashMap<String, Object> selectDetailByDeliveryPk(String pk, String accountPk);

    /**
     * 推送运货单
     *
     * @param request
     * @param response
     * @return
     */
    @Transactional
    int savePushLgCompanyName(Map<String, Object> map);

    /**
     * 财务确认
     * 
     * @param map
     * @return
     */
    @Transactional
    int sureMoney(Map<String, Object> map);

    /**
     * 异常反馈
     * 
     * @param pk
     * @param imgs
     * @param abnormalRemark
     * @return
     */
    @Transactional
    int submitFeedBack(String pk, String imgs, String abnormalRemark);

    /**
     * 运货单地址详情
     * 
     * @param pk
     * @return
     */
    LogisticsOrderAddress selectAddress(String pk);

    /**
     * 修改运货单地址
     * 
     * @param logisticsOrderAddress
     * @return
     */
    int saveOrder(LogisticsOrderAddress logisticsOrderAddress);

    /**
     * 运货单异常反馈详情
     * 
     * @param pk
     * @param accountPk 
     * @return
     */
    HashMap<String, Object> lgOrderFeedBackDetail(String pk, String accountPk);

    /**
     * 异常确认
     * 
     * @param pk
     * @return
     */
    int sureFeedback(String pk);

    /**
     * 打印提货单
     * 
     * @param pk
     * @return
     */
    DeliveryForm exportDeliveryForm(String pk);

    /**
     * 匹配订单线路
     * 
     * @return
     */
    @Transactional
    Integer selectByStatus();

    /**
     * 匹配公司线路
     * 
     * @param pk
     * @param logisticsCompanyPk
     * @return
     */
    List<LogisticsRouteDto> matchLgCompanyRoute(String pk, String logisticsCompanyPk);
}
