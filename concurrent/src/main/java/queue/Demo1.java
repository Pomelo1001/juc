package main.java.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @version 1.1.0
 * @author：cp
 * @time：2021-1-20
 * @Description: 有界阻塞队列，内部使用数组存储元素
 */
public class Demo1 {
    static ArrayBlockingQueue<String> pushQueue = new ArrayBlockingQueue<>(1000);
    //启动一个线程做真实推送
    static {
        new Thread(
                () -> {
                    while (true) {
                        String msg;
                        try {
                            long startTime = System.currentTimeMillis();
                            //获取一条推送消息，此方法会进行阻塞，直到返回结果
                            msg = pushQueue.take();
                            //模拟推送耗时
                            TimeUnit.MILLISECONDS.sleep(500);
                            long endTime = System.currentTimeMillis();
                            System.out.println(String.format("[%s,%s,take耗时:%s],%s,发送消息:%s", startTime, endTime, (endTime - startTime), Thread.currentThread().getName(), msg));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).start();
    }
    //推送消息，需要发送推送消息的调用该方法，会将推送信息先加入推送队列
    public static void pushMsg(String msg) throws InterruptedException {
        pushQueue.put(msg);
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 1; i <= 5; i++) {
            String msg = "一起学习队列,第" + i + "天";
            //模拟耗时
            TimeUnit.SECONDS.sleep(i);
            Demo1.pushMsg(msg);
        }
    }
}
