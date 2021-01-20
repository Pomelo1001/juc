### 队列
重点需要了解BlockingQueue中的所有方法，以及他们的区别

重点掌握ArrayBlockingQueue、LinkedBlockingQueue、PriorityBlockingQueue、DelayQueue的使用场景

需要处理的任务有优先级的，使用PriorityBlockingQueue

处理的任务需要延时处理的，使用DelayQueue
#### 方法
BlockingQueue相关方法：

|操作类型|抛出异常|返回特殊值|一直阻塞|超时退出|
|---|---|---|---|---|
|插入|add(e)|	offer(e)|put(e)|offer(e,timeuout,unit)|
|移除|remove()|	poll()|	take()|	poll(timeout,unit)|
|检查	|element()|	peek()|	不支持|	不支持|

#### ArrayBlockingQueue
基于数组的阻塞队列实现，其内部维护一个定长的数组，用于存储队列元素。线程阻塞的实现是通过ReentrantLock来完成的，数据的插入与取出共用同一个锁，因此ArrayBlockingQueue并不能实现生产、消费同时进行。而且在创建ArrayBlockingQueue时，我们还可以控制对象的内部锁是否采用公平锁，默认采用非公平锁。
#### LinkedBlockingQueue
基于单向链表的阻塞队列实现，在初始化LinkedBlockingQueue的时候可以指定大小，也可以不指定，默认类似一个无限大小的容量（Integer.MAX_VALUE），不指队列容量大小也是会有风险的，一旦数据生产速度大于消费速度，系统内存将有可能被消耗殆尽，因此要谨慎操作。另外LinkedBlockingQueue中用于阻塞生产者、消费者的锁是两个（锁分离），因此生产与消费是可以同时进行的
#### PriorityBlockingQueue
一个支持优先级排序的无界阻塞队列，进入队列的元素会按照优先级进行排序