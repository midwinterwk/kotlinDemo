package com.dimsum.creationalpattern.singleton;

public class LazySingleton {
    private static LazySingleton instance;
    private LazySingleton(){}

    public static LazySingleton getInstance() {
        if (instance == null) {
            instance = new LazySingleton();
        }
        return instance;
    }

    public void showMessage() {
        System.out.println("Hello LazySingleObject!");
    }
}
