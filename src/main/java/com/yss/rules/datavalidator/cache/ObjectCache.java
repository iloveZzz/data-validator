package com.yss.rules.datavalidator.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.yss.rules.datavalidator.cache.base.BaseCache;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author daomingzhu
 */
public class ObjectCache<E> extends BaseCache<E> {
    private CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder();
    private final Cache<Object, Object> objCache;
    public ObjectCache(){
        objCache  = cacheBuilder.build();
    }
    public void config(Consumer<CacheBuilder<Object, Object>> consumer){
        consumer.accept(cacheBuilder);
    }
    @Override
    public void set(String key, E e) {
        objCache.put(key,e);
    }

    @Override
    public E get(String key) {
        return (E)objCache.getIfPresent(key);
    }
    public E getIfNull(String key, Supplier<E> function) {
        E ef = (E) objCache.getIfPresent(key);
        if (Objects.isNull(ef)){
            E e = function.get();
            this.set(key,e);
            return e;
        }else{
            return ef;
        }
    }
}
