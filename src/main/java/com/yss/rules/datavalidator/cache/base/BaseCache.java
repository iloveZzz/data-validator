package com.yss.rules.datavalidator.cache.base;

public abstract class BaseCache<E> {
    protected final int maximumWeight = 5000;

    public abstract void set(String key,E e);
    public abstract E get(String key);
}
