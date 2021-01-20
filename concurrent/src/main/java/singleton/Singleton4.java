package main.java.singleton;

/**
 * @version 1.1.0
 * @author：cp
 * @time：2021-1-20
 * @Description: 饿汉模式
 */
public class Singleton4 {
    private Singleton4() {
    }

    private static Singleton4 singleton4 = new Singleton4();

    public static Singleton4 getSingleton4() {
        return singleton4;
    }
}
