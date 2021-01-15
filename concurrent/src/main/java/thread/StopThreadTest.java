package main.java.thread;

import java.util.concurrent.TimeUnit;

/**
 * @version 1.1.0
 * @author：caopu
 * @time：2021-1-14
 * @Description:
 */
public class StopThreadTest {

    /**
     * 通过interrupt()方式进行中断,同时运用了volatile，保证了flag变量在主线程与T1线程可见性
     * sleep方法不会释放掉线程持有的锁，而wait()则会释放掉线程的锁
     */
    public volatile static boolean flag = true;

    public static class T1 extends Thread {
        public T1(String name) {
            super(name);
        }

        @Override
        public void run() {
            System.out.println("线程 " + this.getName() + " in");
            while (flag) {
            }
            System.out.println("线程 " + this.getName() + " stop");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        T1 cp = new T1("cp");
        cp.start();
        TimeUnit.SECONDS.sleep(1);
        flag = false;
    }
}
