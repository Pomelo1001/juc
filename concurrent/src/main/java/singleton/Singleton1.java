package main.java.singleton;

/**
 * @version 1.1.0
 * @author：cp
 * @time：2021-1-20
 * @Description: 内部类方式实现单例
 */
public class Singleton1 {
    private static class Holder {
        private static Singleton1 instance = new Singleton1();
    }

    private Singleton1() {
    }

    public static Singleton1 getSingleton1() {
        return Holder.instance;
    }
}
