package main.java.thread;

import java.util.concurrent.*;

/**
 * @version 1.1.0
 * @author：cp
 * @time：2021-1-20
 * @Description: ExecutorService.submit方法实现
 */
public class Demo5 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //创建一个线程池
        ExecutorService executorService = Executors.newCachedThreadPool();
        System.out.println(System.currentTimeMillis());
        Future<Integer> future = executorService.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 10;
        });
        //关闭线程池
        executorService.shutdown();
        System.out.println(System.currentTimeMillis());
        Integer result = future.get();
        System.out.println(System.currentTimeMillis() + ":" + result);
    }
}
