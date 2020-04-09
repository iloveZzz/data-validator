package com.yss.rules.datavalidator.cache;

import com.google.common.collect.Maps;
import com.yss.rules.datavalidator.cache.base.BaseCache;
import com.yss.rules.datavalidator.dto.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {
    public static void main(String[] args) {
        BaseCache<List<User>> listCache = new ListCache<>();
        BaseCache<Map<String, String>> baseCache = new MapCache<>();
        for (int i = 0; i < 100; i++) {
            Map<String, String> objectObjectHashMap = Maps.newHashMap();
            objectObjectHashMap.put("a"+i,"aa");
            baseCache.set("ccc"+i,objectObjectHashMap);
        }

        System.out.println(baseCache.get("ccc0"));
        System.out.println(baseCache.get("ccc1"));
        System.out.println(baseCache.get("ccc2"));
        System.out.println(baseCache.get("ccc3"));
        System.out.println(baseCache.get("ccc4"));
        System.out.println(baseCache.get("ccc4asdsa"));

    }
}
