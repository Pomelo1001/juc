# juc
java.util .concurrent
当我们需要解析一个Excel里多个sheet的数据时，可以考虑使用多线程，每个线程解析一个sheet里的数据，等到所有的sheet都解析完之后，程序需要统计解析总耗时。分析一下：解析每个sheet耗时可能不一样，总耗时就是最长耗时的那个操作
### countDown使用场景 

- #### countDownlatch是什么？
CountDownLatch称之为闭锁，它可以使一个或一批线程在闭锁上等待，等到其他线程执行完相应操作后，闭锁打开，这些等待的线程才可以继续执行。确切的说，闭锁在内部维护了一个倒计数器。通过该计数器的值来决定闭锁的状态，从而决定是否允许等待的线程继续执行
- #### 常用方法
public CountDownLatch(int count)：构造方法，count表示计数器的值，不能小于0
public void await() throws InterruptedException：调用await()会让当前线程等待，直到计数器为0的时候，方法才会返回，此方法会响应线程中断操作
public void countDown()：让计数器减1
- #### 使用步骤
创建CountDownLatch对象

调用其实例方法 await()，让当前线程等待

调用 countDown()方法，让计数器减1

当计数器变为0的时候， await()方法会返回
### 几个示例CountDownLatch使用
#### demo1
线程的 join()方法，此方法会让当前线程等待被调用的线程完成之后才能继续。可以看一下join的源码，内部其实是在synchronized方法中调用了线程的wait方法，最后被调用的线程执行完毕之后，由jvm自动调用其notifyAll()方法，唤醒所有等待中的线程
#### demo2
效果和join实现的效果一样，代码中创建了计数器为2的 CountDownLatch
#### 2个countDown结合使用-demo3

### 手写一个并行处理任务的工具类
