package main.java.cas;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @version 1.1.0
 * @author：cp
 * @time：2021-1-19
 * @Description: 模仿有100个人同时访问，并且每个人对咱们的网站发起10次请求，最后总访问次数应该是1000次
 * 输出结果： //request1()方法实际一种输出结果：main,耗时：97,count=98
 *          //request2()方法实际一种输出结果  main,耗时：5081,count=1000
 */
public class Demo1 {
    //访问次数
    static int count = 0;

    public static void request1() throws InterruptedException {
        //模拟请求页面浏览时间
        TimeUnit.MILLISECONDS.sleep(5);
        count++;
    }
    public  static synchronized void request2() throws InterruptedException {
        //模拟请求页面浏览时间
        TimeUnit.MILLISECONDS.sleep(5);
        count++;
    }

    public static void main(String[] args) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        int threadSize = 100;
        CountDownLatch countDownLatch = new CountDownLatch(threadSize);
        for (int i = 0; i < threadSize; i++) {
            Thread thread = new Thread(() -> {
                try {
                    for (int j = 0; j < 10; j++) {
//                        request1();
                        request2();
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
