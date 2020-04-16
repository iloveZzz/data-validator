package com.yss.rules.datavalidator;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import com.yss.rules.datavalidator.cache.ObjectCache;

import java.util.HashMap;
import java.util.Map;

public class AviatorTest {
    public static void main(String[] args) {
        String expression = "a-(b-c) ";
        String expression1 = "a+(b-c) ";
        String expression2 = "d=a*(b-c);d+20 ";
        String expression3 = "d=a*(b-c) ";
        Expression compiledExp = AviatorEvaluator.compile(expression);
        Expression cc = AviatorEvaluator.compile("a+(b-c) ");
        Expression dd = AviatorEvaluator.compile("a*(b-c) ");
//        List<Map<String,Object>> tt = Lists.newArrayList();
//        List<User> uu = Lists.newArrayList();
//        for (int i = 0; i < 10000; i++) {
//            Map d = Maps.newHashMap();
//            d.put("name","dmz"+i);
//            d.put("age",(15+i));
//            d.put("birthday", LocalDateTime.now());
//            tt.add(d);
//
//            uu.add(new User("dmz"+i,(15+i),LocalDateTime.now()));
//        }
//        Map<String, Object> env = new HashMap<String, Object>();
//        env.put("uu", uu);
//        Expression ttaa = AviatorEvaluator.compile("uu[0]['name']",true);
//        Object execute = ttaa.execute(env);


        for (int c = 0; c < 15; c++) {
            long start = System.currentTimeMillis();
            for (int i = 0; i < 1000000; i++) {
                Map<String, Object> env = new HashMap<String, Object>();
                env.put("a", 200);
                env.put("b", 11);
                env.put("c", 22);
                Expression ifNull = ObjectCache.getIfNull(expression, () -> AviatorEvaluator.compile(expression));
                Expression expression1s = ObjectCache.getIfNull(expression1, () -> AviatorEvaluator.compile(expression1));
                Expression expression31 = ObjectCache.getIfNull(expression3, () -> AviatorEvaluator.compile(expression3));
                Expression expression21 = ObjectCache.getIfNull(expression2, () -> AviatorEvaluator.compile(expression2));
                ifNull.execute(env);
                expression1s.execute(env);
                expression21.execute(env);
                expression31.execute(env);
                ifNull.execute(env);
                ifNull.execute(env);
            }
            long end = System.currentTimeMillis();
            System.out.println(("执行时间："+(end-start)/100+"毫秒"));
        }




    }
}
