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
     * 通过一个变量控制线程中断
     *
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
