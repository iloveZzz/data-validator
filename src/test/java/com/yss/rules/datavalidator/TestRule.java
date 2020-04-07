package com.yss.rules.datavalidator;

import org.jeasy.rules.api.*;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.core.RuleBuilder;
import org.jeasy.rules.support.UnitRuleGroup;

/**
 * @author daomingzhu
 * @date 2020/4/3 15:08
 */
public class TestRule {
    public static void main(String[] args) {
        Facts f = new Facts();
        f.put("ran",true);
        f.put("ttt",true);
        f.put("eee",true);

        Rule a = new RuleBuilder()
                .priority(3)
                .name("a rule")
                .description("测试A规则")
                .when(facts -> facts.get("ttt")!=null&&facts.get("ttt").equals(true))
                .then(facts -> System.out.println("It, ttt an hahahahahaha!"))
                .when(facts -> facts.get("ran")!=null&&facts.get("ran").equals(true))
                .then(facts -> System.out.println("It, rain an hahahahahaha!"))
                .build();
        Rule b = new RuleBuilder()
                .priority(2)
                .name("b rule")
                .description("测试B规则")
                .when(facts -> facts.get("ttt").equals(true))
                .then(facts -> System.out.println("It, take an 222!"))
                .then(facts -> System.out.println("It, take an 5555!"))
                .build();
        Rule c = new RuleBuilder()
                .priority(1)
                .name("c rule")
                .description("测试C规则")
                .when(facts -> facts.get("eee")!=null&&facts.get("eee").equals(true))
                .then(facts -> System.out.println("It, take an 333!"))
                .then(facts -> System.out.println("It, take an 444!"))
                .build();
        try {

            UnitRuleGroup myUnitRuleGroup =
                    new UnitRuleGroup("myUnitRuleGroup", "测试啊");
            myUnitRuleGroup.addRule(b);
            myUnitRuleGroup.addRule(a);
            myUnitRuleGroup.addRule(c);

            Rules rules = new Rules();
            rules.register(myUnitRuleGroup);
//            rules.register(a);
//            rules.register(b);
//            rules.register(c);

            DefaultRulesEngine rulesEngine = new DefaultRulesEngine();

            rulesEngine.registerRuleListener(new RuleListener(){

                @Override
                public boolean beforeEvaluate(Rule rule, Facts facts) {
                    return true;
                }

                @Override
                public void afterEvaluate(Rule rule, Facts facts, boolean evaluationResult) {
                    if (!evaluationResult){
                        System.out.println(rule);
                        System.out.println("错误！！！"+evaluationResult);
                    }
                }

                @Override
                public void beforeExecute(Rule rule, Facts facts) {
                    System.out.println("beforeExecute"+rule);
                }

                @Override
                public void onSuccess(Rule rule, Facts facts) {
                    System.out.println("onSuccess"+rule);
                }

                @Override
                public void onFailure(Rule rule, Facts facts, Exception exception) {
                    System.out.println("onFailure rule"+facts);
                }
            });

            rulesEngine.fire(rules,f);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
