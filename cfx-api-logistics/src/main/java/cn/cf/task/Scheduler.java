package cn.cf.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import cn.cf.service.LgDeliveryOrderService;
/**
 * 
 * @author gejianming
 * @date 2017年10月31日
 */
@Component
public class Scheduler {
	
	@Autowired
	private LgDeliveryOrderService deliverOrderService;
	
    /**
     * 执行时间：每天凌晨00:00执行一次 
     * 定时任务：未付款的订单当天凌晨自动关闭
     */
    @Scheduled(cron="0 0 0 * * ?")
    //@Scheduled(cron = "0 45 16 * * ?")
    public void autoClosePay() {
    	System.out.println("=========未付款的订单当天凌晨自动关闭================");
    	deliverOrderService.autoClosePay();
    }
	
    
	/**
	 * 执行时间:每天凌晨0:10执行一次 
	 * 定时任务:48小时状态为待财务确认，则系统自动进行关闭，订单状态直接为已关闭
	 */
	@Scheduled(cron = "0 10 0 * * ?")
	//@Scheduled(cron = "0 40 16 * * ?")
	public void closeConfirmOrder() {
		System.out.println("========48小时状态为待财务确认关闭===========");
		deliverOrderService.closeConfirmOrder();
	}
    
}
