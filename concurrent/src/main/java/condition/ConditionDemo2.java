package main.java.condition;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @version 1.1.0
 * @author：caopu
 * @time：2021-1-15
 * @Description: 通过condition等待和唤醒线程示例
 */
public class ConditionDemo2 {
    static ReentrantLock lock = new ReentrantLock();

    static Condition condition = lock.newCondition();

    public static class T1 extends Thread {
        @Override
        public void run() {
            System.out.println(System.currentTimeMillis() + "," + this.getName() + "准备获取锁");
            lock.lock();
            try {
                System.out.println(System.currentTimeMillis() + "," + this.getName() + "获取锁成功");
                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
            System.out.println(System.currentTimeMillis() + "," + this.getName() + "释放锁成功");
        }
    }

    public static class T2 extends Thread {
        @Override
        public void run() {
            System.out.println(System.currentTimeMillis() + "," + this.getName() + "准备获取锁");
            lock.lock();
            try {
                System.out.println(System.currentTimeMillis() + "," + this.getName() + "获取锁成功");
                condition.signal();
                System.out.println(System.currentTimeMillis() + "," + this.getName() + " signal success");
                try {
                    TimeUnit.SECONDS.sleep(5);
                    System.out.println(System.currentTimeMillis() + "," + this.getName() + "准备释放锁");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } finally {
                lock.unlock();
            }
            System.out.println(System.currentTimeMillis() + "," + this.getName() + "释放锁成功");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        T1 t1 = new T1();
        t1.setName("t1");
        t1.start();
        TimeUnit.SECONDS.sleep(5);
        T2 t2 = new T2();
        t2.setName("t2");
        t2.start();

    }
}
