# juc
java.util .concurrent 
### synchronized的局限性
synchronized的获取和释放锁由jvm实现
- 当线程尝试获取锁的时候，如果获取不到锁会一直阻塞，这个阻塞的过程，用户无法控制
  
- 如果获取锁的线程进入休眠或者阻塞，除非当前线程异常，否则其他线程尝试获取锁必须一直等待
### synchronized关键字
- 要么获取到锁然后继续后面的操作
- 要么一直等待，直到其他线程释放锁为止
ReentrantLock提供了另外一种可能，就是在等的获取锁的过程中（发起获取锁请求到还未获取到锁这段时间内）是可以被中断的，也就是说在等待锁的过程中，程序可以根据需要取消获取锁的请求
### condition
- 任何一个java对象都天然继承于Object类，在线程间实现通信的往往会应用到Object的几个方法，比如wait()、wait(long timeout)、wait(long timeout, int nanos)与notify()、notifyAll()几个方法实现等待/通知机制，同样的， 在java Lock体系下依然会有同样的方法实现等待/通知机制。
- Object的wait和notify/notify是与对象监视器配合完成线程间的等待/通知机制，而Condition与Lock配合完成等待通知机制，前者是java底层级别的，后者是语言级别的，具有更高的可控制性和扩展性
#### 区别
- Condition能够支持不响应中断，而通过使用Object方式不支持
- Condition能够支持多个等待队列（new 多个Condition对象），而Object方式只能支持一个
- Condition能够支持超时时间的设置，而Object不支持
#### condition使用方式
- Condition由ReentrantLock对象创建，并且可以同时创建多个，
- Condition接口在使用前必须先调用ReentrantLock的lock()方法获得锁，
- 之后调用Condition接口的await()将释放锁，并且在该Condition上等待，直到有其他线程调用Condition的signal()方法唤醒线程，使用方式和wait()、notify()类似。