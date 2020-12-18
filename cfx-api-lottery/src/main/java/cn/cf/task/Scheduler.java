package cn.cf.task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.cf.service.B2bLotteryActivityService;

/**
 * @author:zlb
 * @describe:定时任务类
 * @time:2019-03-06  
 */
@Component
public class Scheduler {

    @Autowired
    private B2bLotteryActivityService activityService;
    
     
    /**
     * 执行时间:每天凌程0:30执行一次
     * 发布白条活动 订单累计金额，推送站内信
     */
    @Scheduled(cron = "0 30 0 * * ?")
    public void sendMailByLotteryActivity() {

        activityService.sendMailByLotteryActivity();
    }
 
    
    /**
     * 执行时间:每天凌程0:50执行一次
     * 尊享礼 期间白条累计金额
     */
    @Scheduled(cron = "0 50 0 * * ?")
    public void sendCardByActivity () {

        activityService.sendCardByActivity();
    }
    
    /**
	 * 执行时间:每10分钟执行一次 
	 * 定时任务: 白条支付首次用户
	 */
	@Scheduled(cron = "0 */10 * * * ?")
	public void creditResultSearch() {
		 activityService.sendDayDayCardByActivity();
	}
	
}
