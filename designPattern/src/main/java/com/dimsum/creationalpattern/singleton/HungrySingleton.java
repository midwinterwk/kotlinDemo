package com.dimsum.creationalpattern.singleton;

public class HungrySingleton {
    private static HungrySingleton instance = new HungrySingleton();
    private HungrySingleton() {}

    public static HungrySingleton getInstance() {
        return instance;
    }

    public void showMessage() {
        System.out.println("Hello HungrySingleton!");
    }
}
