package com.yss.rules.datavalidator;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yss.rules.datavalidator.cache.CacheManager;
import com.yss.rules.datavalidator.cache.ObjectCache;
import com.yss.rules.datavalidator.dto.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @author daomingzhu
 * @date 2020/4/9 13:51
 */
public class Test {
    public static void main(String[] args) {
//        ObjectCache<Integer> objectCache = CacheManager.configCacheType(ObjectCache.class);
//        if (objectCache != null) {
//            System.out.println(objectCache.get("aa"));
//            objectCache.set("aa",12);
//            System.out.println(objectCache.get("aa")+33);
//        }
//        LocalDateTime dateTime = LocalDateTime.now();
//        LocalDate localDate = LocalDate.of(dateTime.getYear(), dateTime.getMonth(), dateTime.getDayOfMonth());
//        DateTimeFormatter dateTimeFormatter =   DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        String s3 = localDate.format(dateTimeFormatter);
//
//        System.out.println(s3);
//        System.out.println(dateTime.format(dateTimeFormatter));
        List<User> uu = Lists.newArrayList();
        for (int i = 0; i < 10000; i++) {
            uu.add(new User("dmz"+i,(15+i),LocalDateTime.now()));
        }
        BigDecimal v = uu.stream().map(User::getAge).map(BigDecimal::valueOf).reduce(BigDecimal.ZERO, BigDecimal::add);
        OptionalDouble average = uu.stream().map(u->BigDecimal.valueOf(u.getAge())).mapToDouble(BigDecimal::doubleValue).average();

        System.out.println(v);
        System.out.println(average.getAsDouble());
    }
}
