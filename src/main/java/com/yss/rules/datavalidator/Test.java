package com.yss.rules.datavalidator;

import com.google.common.collect.Maps;
import com.yss.rules.datavalidator.db.SqlExecuter;

import java.util.List;
import java.util.Map;

/**
 * @author daomingzhu
 * @date 2020/4/9 13:51
 */
public class Test {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Map m = Maps.newHashMap();
        m.put("id1", "报表通用列2");
        m.put("id2", "报表通用列3");
        List<Map> local = (List<Map>) SqlExecuter.query("local", "select t.* from TP_REP_BASIC_PARAM t where param_name in (:id1,:id2)",m);
        System.out.println(local);
        List<Map> localSofa = (List<Map>) SqlExecuter.query("local_SOFA", "select t.* from T_R_REPORT t ");
        System.out.println(localSofa);
        long end = System.currentTimeMillis();
        System.out.println(("执行时间："+(end-start)/100+"毫秒"));
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
//        List<User> uu = Lists.newArrayList();
//        for (int i = 0; i < 10000; i++) {
//            uu.add(new User("dmz"+i,(15+i),LocalDateTime.now()));
//        }
//        System.out.println(uu instanceof List);
//        BigDecimal v = uu.stream().map(User::getAge).filter(Objects::nonNull).map(BigDecimal::valueOf).reduce(BigDecimal.ZERO, BigDecimal::add);
//        OptionalDouble average = uu.stream().map(u->BigDecimal.valueOf(u.getAge())).mapToDouble(BigDecimal::doubleValue).average();
//        long count = uu.stream().map(u -> BigDecimal.valueOf(u.getAge())).count();
//        System.out.println(v);
//        System.out.println(count);
//        System.out.println(average.getAsDouble());
    }
}
