package com.yss.rules.datavalidator.engine.rule

import com.yss.rules.datavalidator.engine.facts.FactBuild
import org.jeasy.rules.api.Facts
import org.jeasy.rules.api.Rules

/**
 * @author daomingzhu* @date 2020/4/7 16:49
 */
class RuleConfig {
    def ruleRegister = new Rules()
    def facts = new Facts()
    def 规则(@DelegatesTo(strategy=Closure.DELEGATE_ONLY,value = NewRuleBuilder) Closure rule){
        def ruleCode = rule.rehydrate(new NewRuleBuilder(), this, this)
        ruleCode.resolveStrategy = Closure.DELEGATE_ONLY
        ruleRegister.register(ruleCode())
        this
    }
    def 业务对象(@DelegatesTo(strategy=Closure.DELEGATE_ONLY,value = FactBuild) Closure fact){
        def factCode = fact.rehydrate(new FactBuild(), this, this)
        factCode.resolveStrategy = Closure.DELEGATE_ONLY
        facts = factCode()
        facts
    }
}
