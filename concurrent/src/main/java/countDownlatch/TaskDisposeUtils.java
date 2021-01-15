package main.java.countDownlatch;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @version 1.1.0
 * @author：caopu
 * @time：2021-1-15
 * @Description:任务分解工具类
 */
public class TaskDisposeUtils {

    public static final int POOL_SIZE;

    static {
        POOL_SIZE = Integer.max(Runtime.getRuntime().availableProcessors(), 5);
    }

    public static <T> void dispose(List<T> taskList, Consumer<T> consumer) {
        dispose(true, POOL_SIZE, taskList, consumer);
    }


    public static <T> void dispose(boolean moreThread, int poolSize, List<T> taskList, Consumer<T> consumer) {
        if (taskList == null || taskList.size() == 0) {
            return;
        }
        if (moreThread && poolSize > 1) {
            poolSize = Math.min(poolSize, taskList.size());
            ExecutorService executorService = null;
            try {
                executorService = Executors.newFixedThreadPool(poolSize);
                CountDownLatch countDownLatch = new CountDownLatch(poolSize);
                for (T t : taskList) {
                    executorService.execute(() -> {
                        try {
                            consumer.accept(t);
                        } finally {
                            countDownLatch.countDown();
                        }
                    });
                }
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if (executorService != null) {
                    executorService.shutdown();
                }
            }

        } else {
            for (T t : taskList) {
                consumer.accept(t);
            }
        }
    }

    public static void main(String[] args) {
        //生成10个数字，放在list中，模拟10个任务
        List<Integer> list = Stream.iterate(1, a -> a + 1).limit(10).collect(Collectors.toList());
        //启动多线程处理list中每个任务，每个任务的休眠时间为list中对应的数值
        TaskDisposeUtils.dispose(list, item -> {
            try {
                long startTime = System.currentTimeMillis();
                TimeUnit.SECONDS.sleep(item);
                long endTime = System.currentTimeMillis();
                System.out.println("任务" + item + ",执行完毕，耗时:" + (endTime - startTime));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(list + "中所有任务处理完毕!");

    }

}
