package cn.cf.service;

import cn.cf.entity.BankBaseResp;

public interface BankZhonghangService {

    /**
     * 订单状态更新
     *
     * @param orderNumber
     * @param orderStatus
     * @return
     */
    BankBaseResp orderStatusUpd(String orderNumber, Integer orderStatus);

}
