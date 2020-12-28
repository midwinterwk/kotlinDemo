package com.dimsum.creationalpattern.singleton;

public class SCSingleton {
    private static class SingletonHolder {
        private static final SCSingleton INSTANCE = new SCSingleton();
    }
    private SCSingleton() {}

    public static final SCSingleton getInstance() {
        return  SingletonHolder.INSTANCE;
    }
    public void showMessage() {
        System.out.println("Hello SCSingleton!");
    }
}
