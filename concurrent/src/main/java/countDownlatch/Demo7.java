package main.java.countDownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.LongAccumulator;

/**
 * @version 1.1.0
 * @author：cp
 * @time：2021-1-20
 * @Description:
 * LongAccumulator是LongAdder的功能增强版。LongAdder的API只有对数值的加减，而LongAccumulator提供了自定义的函数操作，其构造函数如下
 * /**
 *   * accumulatorFunction：需要执行的二元函数（接收2个long作为形参，并返回1个long）
 *   * identity：初始值
 *
 *  public LongAccumulator(LongBinaryOperator accumulatorFunction,long identity){
        *this.function=accumulatorFunction;
        *base=this.identity=identity;
        *}
 *
 *
 */
public class Demo7 {

    static LongAccumulator count = new LongAccumulator((x, y) -> x + y, 0L);

    public static void incr() {
        count.accumulate(1);
    }

    private static void m1() throws InterruptedException {
        long t1 = System.currentTimeMillis();
        int threadCount = 50;
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            new Thread(() -> {
                try {
                    for (int j = 0; j < 100; j++) {
                        incr();
                    }
                } finally {
                    countDownLatch.countDown();
                }
            }).start();
        }
        countDownLatch.await();
        long t2 = System.currentTimeMillis();
        System.out.println(String.format("结果：%s,耗时(ms)：%s", count.longValue(), (t2 - t1)));
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            count.reset();
            m1();
        }
    }
}
