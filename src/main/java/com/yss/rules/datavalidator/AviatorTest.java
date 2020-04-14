package com.yss.rules.datavalidator;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import com.yss.rules.datavalidator.dto.User;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AviatorTest {
    public static void main(String[] args) {
        String expression = "a-(b-c) > 100";
        Expression compiledExp = AviatorEvaluator.compile(expression);
        List<Map<String,Object>> tt = Lists.newArrayList();
        List<User> uu = Lists.newArrayList();
        for (int i = 0; i < 10000; i++) {
            Map d = Maps.newHashMap();
            d.put("name","dmz"+i);
            d.put("age",(15+i));
            d.put("birthday", LocalDateTime.now());
            tt.add(d);

            uu.add(new User("dmz"+i,(15+i),LocalDateTime.now()));
        }
        Map<String, Object> env = new HashMap<String, Object>();
        env.put("uu", uu);
        Expression ttaa = AviatorEvaluator.compile("uu[0]['name']",true);
        Object execute = ttaa.execute(env);
        System.out.println(11);
    }
}
