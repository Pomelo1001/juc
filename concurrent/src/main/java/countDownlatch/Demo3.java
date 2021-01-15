package main.java.countDownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @version 1.1.0
 * @author：caopu
 * @time：2021-1-15
 * @Description: todo
 */
public class Demo3 {
    public static class T extends Thread {
        int sleepSeconds;
        CountDownLatch countDown;
        CountDownLatch commandercd;

        public T(String name, int sleepSeconds, CountDownLatch countDown, CountDownLatch commandercd) {
            super(name);
            this.sleepSeconds = sleepSeconds;
            this.countDown = countDown;
            this.commandercd = commandercd;
        }

        @Override
        public void run() {
            //等待发令枪响
            try {
                commandercd.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Thread ct = Thread.currentThread();
            long startTime = System.currentTimeMillis();
            System.out.println(startTime + "," + ct.getName() + "，开始跑步");
            try {
                TimeUnit.SECONDS.sleep(this.sleepSeconds);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                countDown.countDown();
            }
            long endTime = System.currentTimeMillis();
            System.out.println(endTime + "," + ct.getName() + "，跑步结束,耗时:" + (endTime - startTime));
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println(System.currentTimeMillis() + "," + Thread.currentThread().getName() + " 线程start!");
        CountDownLatch commandercd = new CountDownLatch(1);
        CountDownLatch countDownLatch = new CountDownLatch(2);
        long startTime = System.currentTimeMillis();
        T t1 = new T("张三", 2, countDownLatch,commandercd);
        t1.start();
        T t2 = new T("李四", 6, countDownLatch,commandercd);
        t2.start();

        T t3 = new T("王五", 10, countDownLatch,commandercd);
        t3.start();
        //主线程休眠5s,模拟发令员准备发枪耗时时间
        TimeUnit.SECONDS.sleep(5);
        System.out.println(System.currentTimeMillis() + ",枪响了，大家开始跑！");
        commandercd.countDown();

        countDownLatch.await();

        long endTime = System.currentTimeMillis();
        System.out.println("所有人跑步结束，总耗时：" + (endTime - startTime));
    }
}
