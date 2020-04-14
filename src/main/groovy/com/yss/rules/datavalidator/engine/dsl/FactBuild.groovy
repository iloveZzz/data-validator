package com.yss.rules.datavalidator.engine.dsl

import org.jeasy.rules.api.Facts

/**
 * @author daomingzhu* @date 2020/4/8 10:32
 */
class FactBuild {
    def facts = new Facts()

    FactBuild 添加(String key,fact){
        facts.put(key,fact)
        this
    }
    FactBuild 删除(String key){
        facts.remove(key)
        this
    }
    Facts 结束(){
        facts
    }
}
