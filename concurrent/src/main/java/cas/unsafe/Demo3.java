package main.java.cas.unsafe;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @version 1.1.0
 * @author：cp
 * @time：2021-1-19
 * @Description: ABA问题
 * 输出结果：
 * 当前账户余额: 10,小于20，充值20元成功，余额：30 元
 * 当前账户余额: 30,大于20，消费20元成功,余额：10 元
 * 当前账户余额: 10,小于20，充值20元成功，余额：30 元
 * 当前账户余额: 30,大于20，消费20元成功,余额：10 元
 * 当前账户余额: 10,小于20，充值20元成功，余额：30 元
 * 当前账户余额: 30,大于20，消费20元成功,余额：10 元
 * 当前账户余额: 10,小于20，充值20元成功，余额：30 元
 */
public class Demo3 {
    static int accoutMoney = 10;
    static AtomicReference<Integer> money = new AtomicReference<>(accoutMoney);

    /**
     * 模拟2个线程同时更新后台数据库，为用户充值
     */
    static void recharge() {
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                for (int j = 0; j < 5; j++) {
                    Integer m = money.get();
                    if (m == accoutMoney) {
                        if (money.compareAndSet(m, m + 20)) {
                            System.out.println("当前账户余额: " + m + ",小于20，充值20元成功，余额：" + money.get() + " 元");
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
            Integer m = money.get();
            if (m > 20) {
                if (money.compareAndSet(m, m - 20)) {
                    System.out.println("当前账户余额: " + m + ",大于20，消费20元成功,余额：" + money.get() + " 元");
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
