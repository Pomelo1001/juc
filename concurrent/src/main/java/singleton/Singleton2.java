package main.java.singleton;

/**
 * @version 1.1.0
 * @author：cp
 * @time：2021-1-20
 * @Description: 双重检查锁实现单例
 * 当我们写了 new 操作，JVM 到底会发生什么？
 *
 *首先，我们要明白的是： new Singleton3() 是一个非原子操作。代码行singleton3 = new Singleton3(); 的执行过程可以形象地用如下3行伪代码来表示：
 *
 * memory = allocate();        //1:分配对象的内存空间
 * ctorInstance(memory);       //2:初始化对象
 * singleton3 = memory;        //3:使singleton3指向刚分配的内存地址
 *
 * 但实际上，这个过程可能发生无序写入(指令重排序)，也就是说上面的3行指令可能会被重排序导致先执行第3行后执行第2行，也就是说其真实执行顺序可能是下面这种：
 * memory = allocate();        //1:分配对象的内存空间
 * singleton3 = memory;        //3:使singleton3指向刚分配的内存地址
 * ctorInstance(memory);       //2:初始化对象
 * 这段伪代码演示的情况不仅是可能的，而且是一些 JIT 编译器上真实发生的现象。
 *
 * 下面是程序可能的一组执行步骤：
 *
 * 　　1、线程 1 进入 getSingleton2() 方法；
 * 　　2、由于 singleton2 为 null，线程 1 在 //1 处进入 synchronized 块；
 * 　　3、同样由于 singleton2 为 null，线程 1 直接前进到 //3 处，但在构造函数执行之前，使实例成为非 null，并且该实例是未初始化的；
 * 　　4、线程 1 被线程 2 预占；
 * 　　5、线程 2 检查实例是否为 null。因为实例不为 null，线程 2 得到一个不完整（未初始化）的 Singleton 对象；
 * 　　6、线程 2 被线程 1 预占。
 * 　　7、线程 1 通过运行 Singleton3 对象的构造函数来完成对该对象的初始化。
 */
public class Singleton2 {
    private Singleton2() {
    }
    //使用volatile关键字防止重排序，因为 new Instance()是一个非原子操作，可能创建一个不完整的实例
    private static volatile Singleton2 singleton2;

    public static Singleton2 getSingleton2() {
        if (singleton2 == null){
            synchronized (Singleton2.class){  //1
                if (singleton2 == null){        //2
                    singleton2 = new Singleton2();  //3
                }
            }
        }
        return singleton2;
    }
}
