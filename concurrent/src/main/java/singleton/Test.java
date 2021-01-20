package main.java.singleton;

/**
 * @version 1.1.0
 * @author：cp
 * @time：2021-1-20
 * @Description: todo
 */

public class Test {
    public static void main(String[] args) {
        Thread[] threads = new Thread[10];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new TestThread();
        }

        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }
    }

}

class TestThread extends Thread {
    @Override
    public void run() {
        // 对于不同单例模式的实现，只需更改相应的单例类名及其公有静态工厂方法名即可
/*        int hash1 = Singleton1.getSingleton1().hashCode();
        System.out.println(hash1);
        System.out.println("-------------");*/
/*        int hash2 = Singleton2.getSingleton2().hashCode();
        System.out.println(hash2);*/

/*        int hash3 = Singleton3.getSingleton3().hashCode();
        System.out.println(hash3);*/

        int hash4 = Singleton4.getSingleton4().hashCode();
        System.out.println(hash4);
    }
}

