package main.java.thread;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @version 1.1.0
 * @author：cp
 * @time：2021-1-20
 * @Description: 在一个线程中需要获取其他线程的执行结果，能想到几种方式？各有什么优缺点？
 * join()
 */
public class Demo3 {
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
        //用于存放子线程执行的结果
        Result<Integer> result = new Result<>();
        //创建一个子线程
        Thread thread = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
                result.setResult(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        //让主线程等待thread线程执行完毕之后再继续，join方法会让当前线程阻塞
        thread.join();

        //获取thread线程的执行结果
        Integer rs = result.getResult();
        System.out.println(System.currentTimeMillis());
        System.out.println(System.currentTimeMillis() + ":" + rs);
    }
}
