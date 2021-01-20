package main.java.condition;

import java.util.concurrent.TimeUnit;

/**
 * @version 1.1.0
 * @author：caopu
 * @time：2021-1-15
 * @Description: synchronized中等待和唤醒线程示例,object的wait和notify方法必须要在同步代码块中才能调用
 */
public class ConditionDemo {
    static Object lock = new Object();

    public static class T1 extends Thread {
        @Override
        public void run() {
            System.out.println(System.currentTimeMillis() + "," + this.getName() + "准备获取锁");
            synchronized (lock) {
                System.out.println(System.currentTimeMillis() + "," + this.getName() + "获取锁成功");
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            System.out.println(System.currentTimeMillis() + "," + this.getName() + "释放锁成功");
        }
    }

    public static class T2 extends Thread {
        @Override
        public void run() {
            System.out.println(System.currentTimeMillis() + "," + this.getName() + "准备获取锁");
            synchronized (lock) {
                System.out.println(System.currentTimeMillis() + "," + this.getName() + "获取锁成功");
                lock.notifyAll();
                System.out.println(System.currentTimeMillis() + "," + this.getName() + " notify success");
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(System.currentTimeMillis() + "," + this.getName() + "准备释放锁");
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
