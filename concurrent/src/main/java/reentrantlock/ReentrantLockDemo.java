package main.java.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @version 1.1.0
 * @author：caopu
 * @time：2021-1-15
 * @Description: lock()方法和unlock()方法需要成对出现，锁了几次，也要释放几次
 */
public class ReentrantLockDemo {
    private static int num = 0;
    private static ReentrantLock lock = new ReentrantLock();

    private static void add() {
        lock.lock();
        lock.lock();
        try {
            num++;
        } finally {
            lock.unlock();
            lock.unlock();
        }

    }
    public static class T extends Thread{
        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                ReentrantLockDemo.add();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        T t1 = new T();
        T t2 = new T();
        T t3 = new T();
        t1.start();
        t2.start();
        t3.start();
        t1.join();
        t2.join();
        t3.join();
        System.out.println(ReentrantLockDemo.num);

    }

}
