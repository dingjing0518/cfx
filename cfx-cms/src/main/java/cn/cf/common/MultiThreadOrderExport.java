package cn.cf.common;

import cn.cf.common.utils.CommonUtil;
import cn.cf.dto.B2bOrderExtDto;
import cn.cf.model.ManageAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class MultiThreadOrderExport {

    private static Logger logger = LoggerFactory.getLogger(MultiThreadOrderExport.class);

    /**
     * 多线程处理list
     *
     * @param data
     *            数据LinkedList,线程安全
     * @param threadNum
     *            线程数
     * @throws InterruptedException
     */
    public static synchronized void handleList(LinkedList<B2bOrderExtDto> data, int threadNum, Map<String,String> checkMap, String block) {
        logger.error("handleList_Start+++++++++++++++++++++++++++++++++++++++++++++");
        int length = data.size();
        int tl = length % threadNum == 0 ? length / threadNum : (length / threadNum + 1);
        CountDownLatch latch = new CountDownLatch(threadNum);// 多少协作
        long a = System.currentTimeMillis();
        for (int i = 0; i < threadNum; i++) {
            int end = (i + 1) * tl;
            if ((i * tl) <= length) {
                logger.error("threadNum_Start+++++++++++++++++++++++++++++++++++++++++++++"+i);
                // 实现Runnable启动线程
                MultiThreadOrderExport.RunnableThread thread = new MultiThreadOrderExport.RunnableThread(data, i * tl, end > length ? length : end, latch,checkMap,block);
                Thread runable = new Thread(thread);
                runable.start();
            }
        }
        try {
            latch.await();// 等待所有工人完成工作
        }catch (InterruptedException ex){
            logger.error("InterruptedException_ERROR++++++++++++++++",ex);
        }
        long b = System.currentTimeMillis();
        logger.error("时间:" + (b - a) + "毫秒***********************");
    }

    // 实现Runnable
    static class RunnableThread implements Runnable {
        private List<B2bOrderExtDto> data;
        private int start;
        private int end;
        private CountDownLatch latch;
        private Map<String,String> checkMap;
        private String block;

        public RunnableThread(List<B2bOrderExtDto> data, int start, int end, CountDownLatch latch,Map<String,String> checkMap,String block) {
            this.data = data;
            this.start = start;
            this.end = end;
            this.latch = latch;
            this.checkMap = checkMap;
            this.block = block;
        }

        public void run() {
            // TODO 这里处理数据
            List<B2bOrderExtDto> l = data.subList(start, end);
            for (B2bOrderExtDto order:l){
                logger.error("B2bOrderExtDto_run+++++++++++++++++++++++++++++++++++++++++++++");
                // 单个线程中的数据
                OrderExportUtil.setOrderJsonInfoCheckMap(order,checkMap,block,null);
                if (Constants.BLOCK_CF.equals(block)) {
                    OrderExportUtil.setExportParams(order);
                }
                if (Constants.BLOCK_SX.equals(block)) {
                    OrderExportUtil.setExportSxParams(order);
                }
            }
            latch.countDown();// 工人完成工作，计数器减一
        }
    }
}
