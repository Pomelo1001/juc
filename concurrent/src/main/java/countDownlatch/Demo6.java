package main.java.countDownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.LongAdder;

/**
 * @version 1.1.0
 * @author：cp
 * @time：2021-1-20
 * @Description:
 * LongAdder是JDK1.8开始出现的，所提供的API基本上可以替换掉原先的AtomicLong。LongAdder在并发量比较大的情况下，
 * 操作数据的时候，相当于把这个数字分成了很多份数字，然后交给多个人去管控，每个管控者负责保证部分数字在多线程情况下操作的正确性。
 * 当多线程访问的时，通过hash算法映射到具体管控者去操作数据，最后再汇总所有的管控者的数据，得到最终结果。相当于降低了并发情况下锁的粒度，所以效率比较高
 */
public class Demo6 {
    static LongAdder count = new LongAdder();

    public static void incr() {
        count.increment();
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        for (int i = 0; i < 10; i++) {
            count.reset();
            m1();
        }
    }

    private static void m1() throws ExecutionException, InterruptedException {
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
        System.out.println(String.format("结果：%s,耗时(ms)：%s", count.sum(), (t2 - t1)));
    }
}
