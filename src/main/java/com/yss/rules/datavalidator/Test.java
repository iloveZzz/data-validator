package com.yss.rules.datavalidator;

import com.yss.rules.datavalidator.cache.CacheManager;
import com.yss.rules.datavalidator.cache.ObjectCache;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author daomingzhu
 * @date 2020/4/9 13:51
 */
public class Test {
    public static void main(String[] args) {
        ObjectCache<Integer> objectCache = CacheManager.configCacheType(ObjectCache.class);
        if (objectCache != null) {
            System.out.println(objectCache.get("aa"));
            objectCache.set("aa",12);
            System.out.println(objectCache.get("aa")+33);
        }
        LocalDateTime dateTime = LocalDateTime.now();
        LocalDate localDate = LocalDate.of(dateTime.getYear(), dateTime.getMonth(), dateTime.getDayOfMonth());
        DateTimeFormatter dateTimeFormatter =   DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String s3 = localDate.format(dateTimeFormatter);

        System.out.println(s3);
        System.out.println(dateTime.format(dateTimeFormatter));
    }
}
