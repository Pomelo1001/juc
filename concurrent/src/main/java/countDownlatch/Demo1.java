package main.java.countDownlatch;

import java.util.concurrent.TimeUnit;

/**
 * @version 1.1.0
 * @author：caopu
 * @time：2021-1-15
 * @Description:
 */
public class Demo1 {
    public static class T extends Thread {
        int sleepSeconds;

        public T(String name, int sleepSeconds) {
            super(name);
            this.sleepSeconds = sleepSeconds;
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
            }
            long endTime = System.currentTimeMillis();
            System.out.println(endTime + "," + ct.getName() + "，处理完毕,耗时:" + (endTime - startTime));
        }
    }

    public static void main(String[] args) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        T t1 = new T("解析sheet1的线程", 2);
        t1.start();
        T t2 = new T("解析sheet2的线程", 6);
        t2.start();

        t1.join();
        t2.join();
        long endTime = System.currentTimeMillis();
        System.out.println("总耗时：" + (endTime - startTime));
    }
}
