package cn.cf.task.schedule;

import java.util.concurrent.ScheduledFuture;

import cn.cf.common.utils.BeanUtils;
import cn.cf.task.schedule.chemifiber.PriceTrendCfHistoryRunnable;
import cn.cf.util.KeyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

@Component
public class DynamicTask {

    @Autowired
    private TaskScheduler taskScheduler;

    private ScheduledFuture<?> future;

    @Bean
    public TaskScheduler taskScheduler() {

        return new ConcurrentTaskScheduler();
    }
    public String startCron(Runnable runnable,String taskName) {
       future = taskScheduler.schedule(runnable, new CronTrigger("0 0/2 * * * *"));

//        System.out.println("++++++++++++++++++DynamicTask.startCron()");
//        ScheduledFutureMap.map.put(taskName, future);
        return "startCron";
    }
    public String stopCron() {
        if (future != null) {
            future.cancel(true);
        }
        //System.out.println("++++++++++++++++++DynamicTask.stopCron()");
        return "stopCron";
    }

    public boolean isDoneCron() {
        boolean isDone = false;
        if (future != null) {
            isDone = future.isCancelled();
        }
       // System.out.println("++++++++++++++++++DynamicTask.isDoneCron:"+isDone);
        return isDone;
    }

    public static void main(String[] args){
    }
}
