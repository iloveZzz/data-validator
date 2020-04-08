package com.yss.rules.datavalidator.engine.rule

import org.jeasy.rules.core.DefaultRulesEngine

/**
 * @author daomingzhu* @date 2020/4/7 17:00
 */
abstract class RulesEngine extends Script{
    def ruleConfig = new RuleConfig()

    def 规则配置(@DelegatesTo(strategy=Closure.DELEGATE_ONLY,value = RuleConfig) Closure engine){
        def engineCode = engine.rehydrate(ruleConfig, this, this)
        engineCode.resolveStrategy = Closure.DELEGATE_ONLY
        engineCode()

        DefaultRulesEngine rulesEngine = new DefaultRulesEngine();
        rulesEngine.fire(ruleConfig.ruleRegister,ruleConfig.facts)
        rulesEngine
    }
}
