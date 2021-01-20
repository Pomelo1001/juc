package main.java.locksupport;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @version 1.1.0
 * @author：cp
 * @time：2021-1-20
 * @Description: LockSupport类可以阻塞当前线程以及唤醒指定被阻塞的线程。主要是通过park()和unpark(thread)方法来实现阻塞和唤醒线程的操作的。
 * 每个线程都有一个许可(permit)，permit只有两个值1和0，默认是0。
 *
 * 当调用unpark(thread)方法，就会将thread线程的许可permit设置成1(注意多次调用unpark方法，不会累加，permit值还是1)。
 *
 * 当调用park()方法，如果当前线程的permit是1，那么将permit设置为0，并立即返回。如果当前线程的permit是0，那么当前线程就会阻塞，直到别的线程将当前线程的permit设置为1时，park方法会被唤醒，然后会将permit再次设置为0，并返回。
 *
 * 注意：因为permit默认是0，所以一开始调用park()方法，线程必定会被阻塞。调用unpark(thread)方法后，会自动唤醒thread线程，即park方法立即返回。
 */
public class Demo1 {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() ->{
            System.out.println(System.currentTimeMillis() + "," + Thread.currentThread().getName() + " start");
            LockSupport.park();
            System.out.println(System.currentTimeMillis() + "," + Thread.currentThread().getName() + " 被唤醒");

        });
        t1.setName("t1");
        t1.start();
        TimeUnit.SECONDS.sleep(5);
        LockSupport.unpark(t1);
        System.out.println(System.currentTimeMillis() + ",LockSupport.unpark();执行完毕");
    }


}
