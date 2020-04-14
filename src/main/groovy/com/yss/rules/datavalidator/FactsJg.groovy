package com.yss.rules.datavalidator

import com.google.common.collect.Lists
import com.google.common.collect.Maps
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.yss.rules.datavalidator.cache.CacheManager
import com.yss.rules.datavalidator.cache.ObjectCache
import com.yss.rules.datavalidator.domain.GenerateFactsService
import com.yss.rules.datavalidator.dto.User
import com.yss.rules.datavalidator.engine.FactEngine
import com.yss.rules.datavalidator.model.FactModel
import com.yss.rules.datavalidator.util.FileUtil

import java.time.LocalDateTime

/**
 * @author daomingzhu* @date 2020/4/9 11:35
 */
class FactsJg {
    static void main(String[] args) {
        ObjectCache<Script> objectCache = CacheManager.configCacheType(ObjectCache.class)
        def result = FileUtil.readFileContent("src/main/resources/facts/User.json")

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
        FactEngine factCall = new FactEngine()
        factModel.data = uu
        factModel.computeFunc = factCall.computeFunc
        factModel.aggFunction = factCall.aggFunction
        def start = System.currentTimeMillis()
        def cc = new GenerateFactsService().generateFact(factModel)
        def end = System.currentTimeMillis()
        println('执行时间：'+(end-start)/1000+'秒')
//        println(bf.年龄.defaultVal)
//        println(bf.生日)
//        println(bf['生日(YYYY-MM-DD)'])
//        println(result)

    }
}
