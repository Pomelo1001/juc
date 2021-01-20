package main.java.singleton;

/**
 * @version 1.1.0
 * @author：cp
 * @time：2021-1-20
 * @Description: 通过threadlocal本地线程实现单例
 */
public class Singleton3 {
    private Singleton3() {
    }

    private static ThreadLocal<Singleton3> threadLocal = new ThreadLocal<>();
    private static Singleton3 singleton3;

    public static Singleton3 getSingleton3() {
        if (threadLocal.get() == null) {
            synchronized (Singleton3.class) {
                if (singleton3 == null) {
                    singleton3 = new Singleton3();
                }
            }
            threadLocal.set(singleton3);
        }

        return threadLocal.get();
    }

}
