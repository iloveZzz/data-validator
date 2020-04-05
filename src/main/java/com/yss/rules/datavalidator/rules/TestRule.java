package com.yss.rules.datavalidator.rules;

import org.jeasy.rules.api.*;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.core.InferenceRulesEngine;
import org.jeasy.rules.core.RuleBuilder;
import org.jeasy.rules.support.UnitRuleGroup;

/**
 * @author daomingzhu
 * @date 2020/4/3 15:08
 */
public class TestRule {
    public static void main(String[] args) {
        Facts f = new Facts();
        f.put("rain",true);
        f.put("ttt",true);
        f.put("eee",true);

        Rule a = new RuleBuilder()
                .name("a rule")
                .when(facts -> facts.get("rain").equals(true))
                .then(facts -> System.out.println("It, take an 111!"))
                .priority(3)
                .build();
        Rule b = new RuleBuilder()
                .name("b rule")
                .when(facts -> facts.get("ttt").equals(true))
                .then(facts -> System.out.println("It, take an 222!"))
                .then(facts -> System.out.println("It, take an 5555!"))
                .priority(2)
                .build();
        Rule c = new RuleBuilder()
                .name("c rule")
                .when(facts -> facts.get("eee")!=null&&facts.get("eee").equals(true))
                .then(facts -> System.out.println("It, take an 333!"))
                .then(facts -> System.out.println("It, take an 444!"))
                .priority(1)
                .build();
        try {

            UnitRuleGroup myUnitRuleGroup =
                    new UnitRuleGroup("myUnitRuleGroup", "测试啊");
            myUnitRuleGroup.addRule(b);
            myUnitRuleGroup.addRule(a);
            myUnitRuleGroup.addRule(c);
            Rules rules = new Rules();
            rules.register(a);
            rules.register(b);
            rules.register(c);

            DefaultRulesEngine rulesEngine = new DefaultRulesEngine();
            rulesEngine.registerRuleListener(new RuleListener(){

                @Override
                public boolean beforeEvaluate(Rule rule, Facts facts) {
                    return true;
                }

                @Override
                public void afterEvaluate(Rule rule, Facts facts, boolean evaluationResult) {
                    System.out.println(111);
                }

                @Override
                public void beforeExecute(Rule rule, Facts facts) {
                    System.out.println(222);
                }

                @Override
                public void onSuccess(Rule rule, Facts facts) {
                    System.out.println(333);
                }

                @Override
                public void onFailure(Rule rule, Facts facts, Exception exception) {
                    System.out.println(444);
                }
            });
            rulesEngine.fire(rules,f);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
