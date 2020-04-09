package com.yss.rules.datavalidator.cache.base;

import java.lang.reflect.Constructor;

public class ReflectiveFactory<T extends BaseCache> {

    private final Constructor<? extends T> constructor;

    public ReflectiveFactory(Class<? extends T> clazz) {
        try {
            this.constructor = clazz.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("Class does not have a public non-arg constructor", e);
        }
    }

    public T newChannel() throws Exception {
        try {
            return constructor.newInstance();
        } catch (Throwable t) {
            throw new Exception("Unable to create Channel from class " + constructor.getDeclaringClass(), t);
        }
    }

}