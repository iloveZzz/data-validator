package com.yss.rules.datavalidator

import org.jeasy.rules.api.Facts
import org.jeasy.rules.api.Rule
import org.jeasy.rules.api.Rules
import org.jeasy.rules.core.RuleBuilder

/**
 * @author daomingzhu* @date 2020/4/7 16:49
 */
class RuleConfig {
    def rs = new Rules()
    def 添加规则(@DelegatesTo(strategy=Closure.DELEGATE_ONLY,value = RuleBuilder) Closure rule){
        def ruleCode = rule.rehydrate(new RuleBuilder(), this, this)
        ruleCode.resolveStrategy = Closure.DELEGATE_ONLY
        rs.register(ruleCode())
        return this
    }
}
