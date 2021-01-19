package main.java.cas.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @version 1.1.0
 * @author：cp
 * @time：2021-1-19
 * @Description: 获取unsafe类
 */
public class Demo1 {
    static Unsafe unsafe;

    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println(unsafe);
    }
}
