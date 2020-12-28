package com.dimsum.creationalpattern.singleton;

public class DCLSingleton {
    private volatile static DCLSingleton instance;
    private DCLSingleton() {}

    public static DCLSingleton getInstance() {
        if (instance == null) {
            synchronized (DCLSingleton.class) {
                if (instance == null) {
                    instance = new DCLSingleton();
                }
            }
        }
        return instance;
    }

    public void showMessage() {
        System.out.println("Hello DCLSingleton!");
    }
}
