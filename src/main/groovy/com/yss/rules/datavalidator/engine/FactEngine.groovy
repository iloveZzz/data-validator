package com.yss.rules.datavalidator.engine;

import com.yss.rules.datavalidator.cache.CacheManager;
import com.yss.rules.datavalidator.cache.ObjectCache
import com.yss.rules.datavalidator.engine.shell.FactsScriptShell

import java.util.stream.Collectors;

/**
 * @author daomingzhu
 * @date 2020/4/14 16:29
 */
class FactEngine {
    static ObjectCache<Script> objectCache = CacheManager.configCacheType(ObjectCache.class)
    static FactsScriptShell factsScriptShell = new FactsScriptShell(new Binding())
    static void test(express,Map<String, Object> env){
        Script script = objectCache.getIfNull(express,{ -> factsScriptShell.parse(express) })
        env.forEach({k,v->script.setProperty(k,v)})
        script.run()
    }
    def filterFieldFunc = { sourceList, factFilterField ->
        //过滤数据
        if (factFilterField.filterExpress){
            if (factFilterField.type == 'List'){
                return sourceList.stream().filter({ m ->
                    Script script = objectCache.getIfNull(factFilterField.filterExpress,{ -> factsScriptShell.parse(factFilterField.filterExpress) })
                    m.forEach({k,v->script.setProperty(k,v)})
                    script.run()
                }).collect(Collectors.toList())
            }

            Optional o = sourceList.stream().filter({ m ->
                Script script = objectCache.getIfNull(factFilterField.filterExpress,{ -> factsScriptShell.parse(factFilterField.filterExpress) })
                m.forEach({k,v->script.setProperty(k,v)})
                script.run()
            }).findAny()
            if (o.isPresent()){
                o.get()
            }
        }
    }
    def computeFunc = { sourceList, allField ->
        Script script = objectCache.getIfNull(allField.expression,{ -> factsScriptShell.parse(allField.expression) })
        sourceList.forEach({k,v->script.setProperty(k,v)});
        script.setProperty("source",sourceList);
        script.run()
    }
    def aggFunction = { sourceList, fieldFilterAgg ->
        //过滤数据
        if (fieldFilterAgg.filterExpress){
            sourceList = sourceList.stream().filter({ m ->
                Script script = objectCache.getIfNull(fieldFilterAgg.filterExpress,{ -> factsScriptShell.parse(fieldFilterAgg.filterExpress) })
                m.forEach({k,v->script.setProperty(k,v)})
                script.run()
            }).collect(Collectors.toList())
        }

        if (fieldFilterAgg.aggFunc=='SUM'){
            return sourceList.stream().map({ m -> m[fieldFilterAgg.aggField] })
                    .filter(Objects.&nonNull)
                    .map({ o -> Objects.nonNull(o) ? BigDecimal.valueOf(o.toString() as double) : BigDecimal.ZERO })
                    .reduce(BigDecimal.ZERO,{a,v->a.add(v)})
        }

        if (fieldFilterAgg.aggFunc=='AVG'){
            return sourceList.stream().map({ m -> m[fieldFilterAgg.aggField] })
                    .filter(Objects.&nonNull)
                    .mapToDouble({ o -> Objects.nonNull(o) ?(o.toString() as double) :0d }).average().getAsDouble()
        }

        if (fieldFilterAgg.aggFunc=='COUNT'){
            return sourceList.stream().map({ m -> m[fieldFilterAgg.aggField] })
                    .filter(Objects.&nonNull)
                    .count();
        }
    }


}
