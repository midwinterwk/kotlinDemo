package com.dimsum.creationalpattern.singleton;

public enum EnumSingleton {
    INSTANCE;
    public void whateverMethod() {
        System.out.println("Hello EnumSingleton!");
    }
}
