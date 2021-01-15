package main.java.countDownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @version 1.1.0
 * @author：caopu
 * @time：2021-1-15
 * @Description: todo
 */
public class Demo2 {
    public static class T extends Thread {
        int sleepSeconds;
        CountDownLatch countDownLatch;

        public T(String name, int sleepSeconds, CountDownLatch countDownLatch) {
            super(name);
            this.sleepSeconds = sleepSeconds;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            Thread ct = Thread.currentThread();
            long startTime = System.currentTimeMillis();
            System.out.println(startTime + "," + ct.getName() + "，开始处理");
            try {
                TimeUnit.SECONDS.sleep(this.sleepSeconds);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                countDownLatch.countDown();
            }
            long endTime = System.currentTimeMillis();
            System.out.println(endTime + "," + ct.getName() + "，处理完毕,耗时:" + (endTime - startTime));
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println(System.currentTimeMillis() + "," + Thread.currentThread().getName() + " 线程start!");
        CountDownLatch countDownLatch = new CountDownLatch(2);
        long startTime = System.currentTimeMillis();
        T t1 = new T("解析sheet1的线程", 2, countDownLatch);
        t1.start();
        T t2 = new T("解析sheet2的线程", 6, countDownLatch);
        t2.start();

        countDownLatch.await();

        System.out.println(System.currentTimeMillis() + "," + Thread.currentThread().getName() + "线程end!");
        long endTime = System.currentTimeMillis();
        System.out.println("总耗时：" + (endTime - startTime));
    }
}
