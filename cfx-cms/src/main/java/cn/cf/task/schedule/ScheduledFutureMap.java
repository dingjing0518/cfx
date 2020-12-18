package cn.cf.task.schedule;

import cn.cf.common.utils.CommonUtil;

import javax.smartcardio.CommandAPDU;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

public class ScheduledFutureMap {

   
    public static ConcurrentHashMap<String, ScheduledFuture> map = new ConcurrentHashMap<String, ScheduledFuture>();

    
    public static void stopFuture(ScheduledFuture future,String name) {
        if (future != null) {
            future.cancel(true);
        }
        if (CommonUtil.isNotEmpty(name)) {
            ScheduledFutureMap.map.remove(name);
        }
    }
}
