package main.java.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @version 1.1.0
 * @author：caopu
 * @time：2021-1-15
 * @Description: 公平锁
 */
public class ReentrantLockDemo1 {

    private static ReentrantLock fairLock = new ReentrantLock(true);

    public static class T extends Thread {
        public T(String name){
            super(name);
        }
        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
              fairLock.lock();
              try {
                  System.out.println(this.getName() + " acquire the lock");
              }finally {
                  fairLock.unlock();
              }

            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        T t1 = new T("t1");
        T t2 = new T("t2");
        T t3 = new T("t3");

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

    }
}
