package main.java.thread;

import java.util.concurrent.TimeUnit;

/**
 * @version 1.1.0
 * @author：cp
 * @time：2021-1-20
 * @Description:
 * 通过interrupt()方式进行中断, 同时运用了volatile，保证了flag变量在主线程与T1线程可见性
 *      * sleep方法不会释放掉线程持有的锁，而wait()则会释放掉线程的锁
 */
public class Demo2 {
    public static class T1 extends Thread {
        @Override
        public void run() {
            while (true){
                //处理业务
                if (this.isInterrupted()){
                    break;
                }
            }

        }
    }

    public static void main(String[] args) throws InterruptedException {
        StopThreadTest.T1 cp = new StopThreadTest.T1("cp");
        cp.start();
        TimeUnit.SECONDS.sleep(3);
        cp.interrupt();


    }
}
