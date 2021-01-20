package main.java.countDownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @version 1.1.0
 * @author：cp
 * @time：2021-1-20
 * @Description: AtomicLong实现
 * AtomicLong内部采用CAS的方式实现，并发量大的情况下，CAS失败率比较高，导致性能比synchronized还低一些。并发量不是太大的情况下，CAS性能还是可以的
 */
public class Demo5 {
    static AtomicLong count = new AtomicLong(0);

    public static void incr() {
        count.incrementAndGet();
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            count.set(0);
            m1();
        }
    }

    private static void m1() throws InterruptedException {
        long t1 = System.currentTimeMillis();
        int threadCount = 50;
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            new Thread(() -> {
                try {
                    for (int j = 0; j < 1000000; j++) {
                        incr();
                    }
                } finally {
                    countDownLatch.countDown();
                }
            }).start();
        }
        countDownLatch.await();
        long t2 = System.currentTimeMillis();
        System.out.println(String.format("结果：%s,耗时(ms)：%s", count, (t2 - t1)));
    }
}
