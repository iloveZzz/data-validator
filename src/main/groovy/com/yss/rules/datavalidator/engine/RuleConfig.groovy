package com.yss.rules.datavalidator.engine

import org.jeasy.rules.api.Facts
import org.jeasy.rules.api.Rule
import org.jeasy.rules.api.Rules
import org.jeasy.rules.core.RuleBuilder

/**
 * @author daomingzhu* @date 2020/4/7 16:49
 */
class RuleConfig {
    def rs = new Rules()
    def 添加规则(@DelegatesTo(strategy=Closure.DELEGATE_ONLY,value = RuleBuilderCc) Closure rule){
        def ruleCode = rule.rehydrate(new RuleBuilderCc(), this, this)
        ruleCode.resolveStrategy = Closure.DELEGATE_ONLY
        rs.register(ruleCode())
        return this
    }
}
