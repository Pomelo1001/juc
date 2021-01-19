### cas
#### 从网站计数器实现中一步步引出CAS操作 
demo1 实例，输出结果不符合预期
count++会有线程安全问题，count++操作实际上是由以下三步操作完成的：
- 获取count的值，记做A：A=count
- 将A的值+1，得到B：B = A+1
- 让B赋值给count：count = B
解决方案：
- 通过加锁的方式让上面3步骤同时只能被一个线程操作，从而保证结果的正确性
- 改进方案：只在第3步加锁，减少加锁的范围，第三步：
获取锁
第三步获取一下count最新的值，记做LV
判断LV是否等于A，如果相等，则将B的值赋给count，并返回true，否者返回false
释放锁——demo2实例
#### 介绍java中的CAS及CAS可能存在的问题
- CAS 操作包含三个操作数 —— 内存位置（V）、预期原值（A）和新值(B)。如果内存位置的值与预期原值相匹配，那么处理器会自动将该位置值更新为新值 。否则，处理器不做任何操作。无论哪种情况，它都会在 CAS 指令之前返回该 位置的值。（在 CAS 的一些特殊情况下将仅返回 CAS 是否成功，而不提取当前 值。）CAS 有效地说明了“我认为位置 V 应该包含值 A；如果包含该值，则将 B 放到这个位置；否则，不要更改该位置，只告诉我这个位置现在的值即可。
- 通常将 CAS 用于同步的方式是从地址 V 读取值 A，执行多步计算来获得新 值 B，然后使用 CAS 将 V 的值从 A 改为 B。如果 V 处的值尚未同时更改，则 CAS 操作成功
- java中提供了对CAS操作的支持，具体在sun.misc.Unsafe类中，声明如下：
public final native boolean compareAndSwapObject(Object var1, long var2, Object var4, Object var5);
public final native boolean compareAndSwapInt(Object var1, long var2, int var4, int var5);
public final native boolean compareAndSwapLong(Object var1, long var2, long var4, long var6);
var1：表示要操作的对象

var2：表示要操作对象中属性地址的偏移量

var4：表示需要修改数据的期望的值

var5：表示需要修改为的新值
- synchronized、ReentrantLock这种独占锁属于悲观锁，CAS属于乐观锁
- CAS问题：
ABA问题：解决思路是使用版本号。在变量前面追加上版本号，每次变量更新的时候把版本号加一，那么A-B-A 就会变成1A-2B-3A。目前在JDK的atomic包里提供了一个类AtomicStampedReference来解决ABA问题
;如果CAS不成功，则会原地循环（自旋操作），如果长时间自旋会给CPU带来非常大的执行开销。并发量比较大的情况下，CAS成功概率可能比较低，可能会重试很多次才会成功

#### 悲观锁和乐观锁的一些介绍及数据库乐观锁的一个常见示例
略
#### 使用java中的原子操作实现网站计数器功能
demo3
### unsafe类
源码：
```java
public final class Unsafe {
  // 单例对象
  private static final Unsafe theUnsafe;

  private Unsafe() {
  }
  @CallerSensitive
  public static Unsafe getUnsafe() {
    Class var0 = Reflection.getCallerClass();
    // 仅在引导类加载器`BootstrapClassLoader`加载时才合法
    if(!VM.isSystemDomainLoader(var0.getClassLoader())) {    
      throw new SecurityException("Unsafe");
    } else {
      return theUnsafe;
    }
  }
}
```
