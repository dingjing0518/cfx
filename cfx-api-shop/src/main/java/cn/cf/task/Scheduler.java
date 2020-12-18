package cn.cf.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.cf.service.B2bPriceMovementService;

/**
 * @author:FJM
 * @describe:定时任务类
 * @time:2017-12-15 上午12:48:54
 */
@Component
public class Scheduler {

    
    @Autowired
    private B2bPriceMovementService b2bPriceMovementService;
    
   
    /**
     * 每天记录商品价格，删除超过100天的价格数据
     * 
     */
//    @Scheduled(cron = "0 10 22 * * ?")
//    public void snapshotPrice() {
//    	try {
//    		snapshotPriceService.action();
//    		snapshotPriceService.remove();
//		} catch (Exception e) {
//		}
//    }

    /**
     * 每天夜里商品价格
     */
//    @Scheduled(cron = "0 0 23 * * ?")
//    public void updateYesterdayPrice() {
//        try {
//            //更新当天的价格
//        	b2bPriceMovementService.updateYesterdayPrice();
//        } catch (Exception e) {
//        }
//    }
}
