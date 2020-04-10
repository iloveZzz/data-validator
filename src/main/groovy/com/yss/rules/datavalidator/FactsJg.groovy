package com.yss.rules.datavalidator

import com.google.common.collect.Lists
import com.google.common.collect.Maps
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.yss.rules.datavalidator.cache.CacheManager
import com.yss.rules.datavalidator.cache.ObjectCache
import com.yss.rules.datavalidator.domain.FactsService
import com.yss.rules.datavalidator.model.BusFieldModel
import com.yss.rules.datavalidator.shell.FactsScriptEngine
import com.yss.rules.datavalidator.util.FileUtil

import java.time.LocalDateTime

/**
 * @author daomingzhu* @date 2020/4/9 11:35
 */
class FactsJg {
    static void main(String[] args) {
        ObjectCache<Script> objectCache = CacheManager.configCacheType(ObjectCache.class)
        def sharedData = new Binding()
        sharedData.setVariable("thant",1000)
        def factsScriptEngine = new FactsScriptEngine(sharedData)

        def result = FileUtil.readFileContent("src/main/resources/facts/User.json")
//        String result = factsScriptEngine.evaluate(warpBusDataModel(content))

        Map<String,BusFieldModel> bf = new Gson().fromJson(result, new TypeToken<Map<String,BusFieldModel>>() {}.getType())

        List<Map<String,Object>> tt = Lists.newArrayList()
        for (int i = 0; i < 10000; i++) {
            def d = Maps.newHashMap();
            d.put("name","dmz"+i)
            d.put("age",(15+i))
            d.put("birthday", LocalDateTime.now())
            tt.add(d)
        }

        def start = System.currentTimeMillis()
        def map = new FactsService().generateFactMap(bf, tt, { source, fieldModel ->
            Script script = objectCache.getIfNull(fieldModel.getExpression(),
                    { -> factsScriptEngine.parse(fieldModel.getExpression()) })
            source.forEach({k,v->script.setProperty(k,v)})
            script.setProperty("source",source)
            script.setProperty("fieldModel",fieldModel)
            script.run()
        })
        def end = System.currentTimeMillis()
        println('执行时间：'+(end-start)/1000+'秒')
//        println(bf.年龄.defaultVal)
//        println(bf.生日)
//        println(bf['生日(YYYY-MM-DD)'])
//        println(result)

    }
}
