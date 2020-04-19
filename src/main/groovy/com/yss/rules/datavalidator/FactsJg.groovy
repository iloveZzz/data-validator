package com.yss.rules.datavalidator

import com.google.common.collect.Lists
import com.google.common.collect.Maps
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.yss.rules.datavalidator.dto.User
import com.yss.rules.datavalidator.facts.FactsGenerator
import com.yss.rules.datavalidator.function.FactsFun
import com.yss.rules.datavalidator.model.FactModel
import com.yss.rules.datavalidator.util.FileUtil

import java.time.LocalDateTime
/**
 * @author daomingzhu* @date 2020/4/9 11:35
 */
class FactsJg {
    static void main(String[] args) {
        Map bindVar = Maps.newHashMap();
        def result = FileUtil.readFileContent("src/main/resources/facts/PojoFactModel.json")

        FactModel factModel = new Gson().fromJson(result, new TypeToken<FactModel>() {}.getType())

        List<Map<String,Object>> tt = Lists.newArrayList()
        List<User> uu = Lists.newArrayList()
        for (int i = 0; i < 10000; i++) {
            def d = Maps.newHashMap();
            d.put("name","dmz"+i)
            d.put("age",(15+i))
            d.put("birthday", LocalDateTime.now())
            tt.add(d)

            uu.add(new User("dmz"+i,(15+i),LocalDateTime.now()))
        }
        factModel.data = uu
        factModel.factsFun = new FactsFun()
        for (int i = 0; i <4; i++) {
            def generater = new FactsGenerator(factModel)
            def start = System.currentTimeMillis()
            def cc = generater.generateFact(bindVar)
            def end = System.currentTimeMillis()
            println('执行时间：'+(end-start)/1000+'秒')
            println cc.sqlData
            println cc.filterField
            println cc.aggData
        }

//        println(bf.年龄.defaultVal)
//        println(bf.生日)
//        println(bf['生日(YYYY-MM-DD)'])
//        println(result)

    }
}
