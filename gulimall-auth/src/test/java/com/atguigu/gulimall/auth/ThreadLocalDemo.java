package com.atguigu.gulimall.auth;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadLocalDemo {
    private static final int anInt= getVal();
    private static int i = 0;
    private static ThreadLocal<Object> threadLocal =new ThreadLocal();

    private static int getVal(){
        System.out.println("执行了。。。");
        return ++i;
    }
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, InterruptedException {
        Thread t = new Thread(()->test("abc",false));
        t.start();
        t.join();
        System.out.println("--gc后--");
        Thread t2 = new Thread(() -> test("def", true));
        t2.start();
        t2.join();
    }

    private static void test(String s,boolean isGC)  {
//        int a = anInt;
//        System.out.println("a:"+a);
        try {
//            Field nextHashCode = threadLocal.getClass().getDeclaredField("nextHashCode");
//            Field threadLocalHashCode = threadLocal.getClass().getDeclaredField("threadLocalHashCode");
//            nextHashCode.setAccessible(true);
//            threadLocalHashCode.setAccessible(true);
//            Integer threadLocalHashcode = (Integer)threadLocalHashCode.get(threadLocal);
//            AtomicInteger nextHashcode = (AtomicInteger)nextHashCode.get(threadLocal);
//            System.out.println("nextHashcode:"+nextHashcode);
//            System.out.println("threadLocalHashcode:"+threadLocalHashcode);
//            System.out.println(239350328+0x61c88647);
            threadLocal.set(s);
            if (isGC) {
                System.gc();
            }
            Thread t = Thread.currentThread();
            Class<? extends Thread> clz = t.getClass();
            Field field = clz.getDeclaredField("threadLocals");
            field.setAccessible(true);
            Object ThreadLocalMap = field.get(t);
            Class<?> tlmClass = ThreadLocalMap.getClass();
            Field tableField = tlmClass.getDeclaredField("table");
            tableField.setAccessible(true);
            Object[] arr = (Object[]) tableField.get(ThreadLocalMap);
            for (Object o : arr) {
                if (o != null) {
                    Class<?> entryClass = o.getClass();
                    Field valueField = entryClass.getDeclaredField("value");
                    Field referenceField = entryClass.getSuperclass().getSuperclass().getDeclaredField("referent");
                    valueField.setAccessible(true);
                    referenceField.setAccessible(true);
                    System.out.println(String.format("弱引用key:%s,值:%s", referenceField.get(o), valueField.get(o)));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
