package main.java.threadPoolExecutor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @version 1.1.0
 * @author：cp
 * @time：2021-1-15
 * @Description: todo
 */
public class Demo1 {
    static ThreadPoolExecutor executor = new ThreadPoolExecutor(
            3,
            5,
            10,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(10),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy());

    public static void main(String[] args) {
        for (int i = 0; i < 10 ; i++) {
            int j = i;
            String taskName = "task" + j;
            executor.execute(() ->{
                try {
                    TimeUnit.SECONDS.sleep(j);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + taskName + "处理完毕");
            });
        }
        executor.shutdown();
    }

}
