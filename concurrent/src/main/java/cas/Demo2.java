package main.java.cas;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @version 1.1.0
 * @author：cp
 * @time：2021-1-19
 * @Description: 输出结果：main,耗时：96,count=1000
 */
public class Demo2 {
    //访问次数
    static volatile int count = 0;

    public static void request() throws InterruptedException {
        //模拟请求页面浏览时间
        TimeUnit.MILLISECONDS.sleep(5);
        int expectCount;
        do {
            expectCount = getCount();
        } while (!compareAndSwap(expectCount, expectCount + 1));
    }

    /**
     * 返回当前的计数器值
     * @return
     */
    public static int getCount() {
        return count;
    }

    /**
     * cas方法
     * @param expect
     * @param newCount
     * @return
     */
    public static synchronized boolean compareAndSwap(int expect, int newCount) {
        if (getCount() == expect) {
            count = newCount;
            return true;
        }
        return false;
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
