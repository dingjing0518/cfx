package cn.cf.service;


/**
 * 
 * @description:银行与化纤汇中间业务桥接层
 * @author FJM
 * @date 2018-5-17 下午8:10:42
 */

public interface HuaxianhuiBankService {

    void orderStatusUpd(String orderNumber, Integer orderStatus);
}
