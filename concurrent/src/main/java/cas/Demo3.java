package main.java.cas;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @version 1.1.0
 * @author：cp
 * @time：2021-1-19
 * @Description: JUC中的AtomicInteger工具实现计数器
 * 输出结果：main,耗时：99,count=1000
 */
public class Demo3 {
   //访问次数
   static AtomicInteger count = new AtomicInteger();

    public static void request() throws InterruptedException {
        //模拟请求页面浏览时间
        TimeUnit.MILLISECONDS.sleep(5);
        //对count原子操作+1
        count.incrementAndGet();
    }

    public static void main(String[] args) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        int threadSize = 100;
        CountDownLatch countDownLatch = new CountDownLatch(threadSize);
        for (int i = 0; i < threadSize; i++) {
            Thread thread = new Thread(() -> {
                try {
                    for (int j = 0; j < 10; j++) {
                        request();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            });
            thread.start();
        }
        countDownLatch.await();
        long endTime = System.currentTimeMillis();
        System.out.println(Thread.currentThread().getName() + ",耗时：" + (endTime - startTime)+",count=" + count);
    }
}
