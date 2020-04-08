package com.yss.rules.datavalidator.engine.rule

import org.jeasy.rules.core.AbstractRulesEngine

/**
 * @author daomingzhu* @date 2020/4/7 17:00
 */
abstract class RulesEngine<T extends AbstractRulesEngine> extends Script{
    def ruleConfig = new RuleConfig()
    def rulesEngine
    def 规则配置(@DelegatesTo(strategy=Closure.DELEGATE_ONLY,value = RuleConfig) Closure engine){
        def engineCode = engine.rehydrate(ruleConfig, this, this)
        engineCode.resolveStrategy = Closure.DELEGATE_ONLY
        engineCode()
    }
     T  initEngine(T re){
        rulesEngine = re
    }

}
