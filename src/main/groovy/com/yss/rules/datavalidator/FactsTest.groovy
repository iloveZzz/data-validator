package com.yss.rules.datavalidator

import org.jeasy.rules.api.Facts

/**
 * @author daomingzhu* @date 2020/4/7 20:49
 */
class FactsTest {
    static void main(String[] args) {
        Facts f = new Facts()
        f.put("ran",true)
        f.put("ttt",true)
        f.put("eee",true)
        f.put("user",new User('aa22',12))

        println(f.ran)
        println(f.user.name)
    }
    static class User{
        User(name,age){
            this.name = name
            this.age = age
        }
        def name
        def age
    }
}
