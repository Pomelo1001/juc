package main.java.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @version 1.1.0
 * @author：cp
 * @time：2021-1-20
 * @Description: countdownlatch
 *
 */
public class Demo4 {
    static class Result<T> {
        T result;

        public T getResult() {
            return result;
        }

        public void setResult(T result) {
            this.result = result;
        }
    }
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println(System.currentTimeMillis());
        CountDownLatch countDownLatch = new CountDownLatch(1);
        //用于存放子线程执行的结果
        Demo3.Result<Integer> result = new Demo3.Result<>();
        //创建一个子线程
        Thread thread = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
                result.setResult(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                countDownLatch.countDown();
            }
        });
        thread.start();
        //countDownLatch.await()会让当前线程阻塞，当countDownLatch中的计数器变为0的时候，await方法会返回
        countDownLatch.await();

        //获取thread线程的执行结果
        Integer rs = result.getResult();
        System.out.println(System.currentTimeMillis());
        System.out.println(System.currentTimeMillis() + ":" + rs);
    }
}
