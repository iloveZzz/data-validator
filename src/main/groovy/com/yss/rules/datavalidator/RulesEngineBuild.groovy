package com.yss.rules.datavalidator

import org.jeasy.rules.api.Facts
import org.jeasy.rules.core.DefaultRulesEngine

/**
 * @author daomingzhu* @date 2020/4/7 17:00
 */
abstract class RulesEngineBuild extends Script{
    def  ruleConfig = new RuleConfig()
    def 规则引擎(@DelegatesTo(strategy=Closure.DELEGATE_ONLY,value = RuleConfig) Closure engine){
        def engineCode = engine.rehydrate(ruleConfig, this, this)
        engineCode.resolveStrategy = Closure.DELEGATE_ONLY
        engineCode()

        DefaultRulesEngine rulesEngine = new DefaultRulesEngine();
        rulesEngine.fire(ruleConfig.rs,new Facts())
    }
}
