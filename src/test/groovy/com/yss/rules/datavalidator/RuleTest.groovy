package com.yss.rules.datavalidator

import com.yss.rules.datavalidator.engine.rule.RulesEngine
import org.jeasy.rules.core.DefaultRulesEngine

class RuleTest extends RulesEngine{
    def test(){
        规则配置 {
            业务对象 {
                添加("w",true)
                添加("t",true)
                结束()
            }
            规则 {
                规则名 '公式校验'
                描述 '操作符表达式计算,例如(A+B=C);自定义函数;例如'
                优先级 1
                如果 { facts ->
                    facts.get('w')
                }
                那么 { facts ->
                    println("test 1 rule!")
                }
                结束()
            }
            规则 {
                规则名 'sql校验'
                描述 '测试一个规则2'
                优先级 2
                如果 { facts -> facts.get('w') }
                那么 { facts ->
                    println("test 2 rule!")
                }
                结束()
            }
            规则 {
                规则名 'test3'
                描述 '测试一个规则3'
                优先级 3
                如果 { facts -> facts.get('w') }
                那么 { facts ->
                    println("test 3 rule!")
                }
                结束()
            }
        }
    }
    @Override
    Object run() {
        DefaultRulesEngine rulesEngine = test()
        println rulesEngine
    }
}
