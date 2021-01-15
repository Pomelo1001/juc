# juc

### [线程池](https://mp.weixin.qq.com/s?__biz=MzA5MTkxMDQ4MQ==&mid=2648933151&idx=1&sn=2020066b974b5f4c0823abd419e8adae&chksm=88621b21bf159237bdacfb47bd1a344f7123aabc25e3607e78d936dd554412edce5dd825003d&cur_album_id=1318984626890915841&scene=189#rd)
咱们作为开发者，上面都有开发主管，主管下面带领几个小弟干活，CTO给主管授权说，你可以招聘5个小弟干活，新来任务，如果小弟还不到5个，立即去招聘一个来干这个新来的任务，当5个小弟都招来了，再来任务之后，将任务记录到一个表格中，表格中最多记录100个，小弟们会主动去表格中获取任务执行，如果5个小弟都在干活，并且表格中也记录满了，那你可以将小弟扩充到20个，如果20个小弟都在干活，并且存放任务的表也满了，产品经理再来任务后，是直接拒绝，还是让产品自己干，这个由你自己决定，小弟们都尽心尽力在干活，任务都被处理完了，突然公司业绩下滑，几个员工没事干，打酱油，为了节约成本，CTO主管把小弟控制到5人，其他15个人直接被干掉了。所以作为小弟们，别让自己闲着，多干活。

原理：先找几个人干活，大家都忙于干活，任务太多可以排期，排期的任务太多了，再招一些人来干活，最后干活的和排期都达到上层领导要求的上限了，那需要采取一些其他策略进行处理了。对于长时间不干活的人，考虑将其开掉，节约资源和成本。

#### 线程池
```java
public ThreadPoolExecutor(int corePoolSize,
                              int maximumPoolSize,
                              long keepAliveTime,
                              TimeUnit unit,
                              BlockingQueue<Runnable> workQueue,
                              ThreadFactory threadFactory,
                              RejectedExecutionHandler handler)
```
- corePoolSize：核心线程大小，当提交一个任务到线程池时，线程池会创建一个线程来执行任务，即使有其他空闲线程可以处理任务也会创新线程，等到工作的线程数大于核心线程数时就不会在创建了。如果调用了线程池的prestartAllCoreThreads方法，线程池会提前把核心线程都创造好，并启动
- maximumPoolSize：线程池允许创建的最大线程数。如果队列满了，并且以创建的线程数小于最大线程数，则线程池会再创建新的线程执行任务。如果我们使用了无界队列，那么所有的任务会加入队列，这个参数就没有什么效果了
- keepAliveTime 线程池的工作线程空闲后，保持存活的时间。如果没有任务处理了，有些线程会空闲，空闲的时间超过了这个值，会被回收掉。如果任务很多，并且每个任务的执行时间比较短，避免线程重复创建和回收，可以调大这个时间，提高线程的利用率
- keepAliveTIme的时间单位
- workQueue：工作队列，用于缓存待处理任务的阻塞队列，常见的有4种:

|队列名|数据结构|
|:-----|:----|
|ArrayBlockingQueue|基于数组结构的有界阻塞队列，此队列按照先进先出原则对元素进行排序|
|LinkedBlockingQueue|基于链表结构的阻塞队列，此队列按照先进先出排序元素，  吞吐量通常要高于ArrayBlockingQueue。静态工厂方法Executors.newFixedThreadPool使用了这个队列|
|SynchronousQueue|一个不存储元素的阻塞队列，每个插入操作必须等到另外一个线程调用移除操作，否则插入操作一直处理阻塞状态，吞吐量通常要高于LinkedBlockingQueue，静态工厂方法Executors.newCachedThreadPool使用这个队列|
|PriorityBlockingQueue|优先级队列，进入队列的元素按照优先级会进行排序|
- threadFactory：线程池中创建线程的工厂，可以通过线程工厂给每个创建出来的线程设置更有意义的名字
- handler：饱和策略，当线程池无法处理新来的任务了，那么需要提供一种策略处理提交的新任务，默认有4种策略  

|饱和策略|处理方式|
|:-----|:----|
|AbortPolicy|直接抛出异常|
|CallerRunsPolicy|在当前调用者的线程中运行任务，即随丢来的任务，由他自己去处理|
|DiscardOldestPolicy|丢弃队列中最老的一个任务，即丢弃队列头部的一个任务，然后执行当前传入的任务|
|DiscardPolicy|不处理，直接丢弃掉，方法内部为空|