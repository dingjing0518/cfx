package cn.cf.service.impl;

import cn.cf.service.BankZhonghangService;
import cn.cf.service.HuaxianhuiBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HuaxianhuiBankServiceImpl implements HuaxianhuiBankService {

    @Autowired
    private BankZhonghangService bankZhonghangService;

    @Override
    public void orderStatusUpd(String orderNumber, Integer orderStatus) {
        bankZhonghangService.orderStatusUpd(orderNumber,orderStatus);
    }
}
