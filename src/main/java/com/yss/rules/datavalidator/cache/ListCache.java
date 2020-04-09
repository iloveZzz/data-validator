package com.yss.rules.datavalidator.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.Weigher;
import com.yss.rules.datavalidator.cache.base.BaseCache;

import java.util.Collection;

public class ListCache<T,E extends Collection<T>> extends BaseCache<E> {
    private final LoadingCache<String, E> lsCache;
    public ListCache(){
        lsCache  = CacheBuilder.newBuilder()
                .maximumWeight(100000)
                .weigher((Weigher<String, E>) (k, v) -> v.size())
                .build(
                        new CacheLoader<String, E>() {
                            public  E load(String key) { // no checked exception
                                return null;
                            }
                        });
    }
    public  void set(String key,E collection){
        lsCache.put(key,collection);
    }
    public  E get(String key){
        return lsCache.getIfPresent(key);
    }
}
