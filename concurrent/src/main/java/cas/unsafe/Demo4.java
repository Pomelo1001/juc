package main.java.cas.unsafe;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @version 1.1.0
 * @author：cp
 * @time：2021-1-19
 * @Description: todo
 */
public class Demo4 {
    static int accoutMoney = 10;
    static AtomicStampedReference<Integer> money = new AtomicStampedReference<>(accoutMoney,0);

    /**
     * 模拟2个线程同时更新后台数据库，为用户充值
     */
    static void recharge() {
        for (int i = 0; i < 2; i++) {
            int stamp = money.getStamp();
            new Thread(() -> {
                for (int j = 0; j < 50; j++) {
                    Integer m = money.getReference();
                    if (m == accoutMoney) {
                        if (money.compareAndSet(m, m + 20,stamp,stamp+1)) {
                            System.out.println("当前时间戳："+ stamp + "当前账户余额: " + m + ",小于20，充值20元成功，余额：" + money.getReference() + " 元");
                        }
                    }
                    try {
                        TimeUnit.MILLISECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }).start();

        }
    }
    /**
     * 模拟用户消费
     */
    static void consume() {
        for (int j = 0; j < 5; j++) {
            Integer stamp = money.getStamp();
            Integer m = money.getReference();
            if (m > 20) {
                if (money.compareAndSet(m, m - 20,stamp,stamp+1)) {
                    System.out.println("当前时间戳："+ stamp +"当前账户余额: " + m + ",大于20，消费20元成功,余额：" + money.getReference() + " 元");
                }
            }
            try {
                TimeUnit.MILLISECONDS.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
        recharge();
        consume();
    }
}
