package com.w4eret1ckrtb1tch.app34;

final public class Singleton {

    private static volatile Singleton instance;
    public String value;

    private Singleton(String value) {
        this.value = value;
    }

    public Singleton getInstance(String value) {

        Singleton result = instance;
        if (result != null) return result;

        synchronized (SingletonKotlin.class) {
            if (instance == null) instance = new Singleton(value);
        }
        return instance;
    }

}
