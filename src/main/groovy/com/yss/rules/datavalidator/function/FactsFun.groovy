package com.yss.rules.datavalidator.function;

import com.yss.rules.datavalidator.cache.ObjectCache
import com.yss.rules.datavalidator.engine.shell.FactsScriptShell

import java.util.stream.Collectors;

/**
 * @author daomingzhu
 * @date 2020/4/14 16:29
 */
class FactsFun {
    static FactsScriptShell factsScriptShell = new FactsScriptShell(new Binding())
    static filterFieldFunc = { sourceList, factFilterField ->
        //过滤数据
        if (factFilterField.filterExpress){
            if (factFilterField.type == 'List'){
                return sourceList.stream().filter({ m ->
                    Script script = ObjectCache.getIfNull(factFilterField.filterExpress,{ -> factsScriptShell.parse(factFilterField.filterExpress) })
                    m.forEach({k,v->script.setProperty(k,v)})
                    script.run()
                }).collect(Collectors.toList())
            }

            Optional o = sourceList.stream().filter({ m ->
                Script script = ObjectCache.getIfNull(factFilterField.filterExpress,{ -> factsScriptShell.parse(factFilterField.filterExpress) })
                m.forEach({k,v->script.setProperty(k,v)})
                script.run()
            }).findAny()
            if (o.isPresent()){
                o.get()
            }
        }
    }
    static computeFunc = { sourceList, allField ->
        Script script = ObjectCache.getIfNull(allField.expression,{ -> factsScriptShell.parse(allField.expression) })
        sourceList.forEach({k,v->script.setProperty(k,v)});
        script.setProperty("source",sourceList);
        script.run()
    }
    static aggFunction = { sourceList, fieldFilterAgg ->
        //过滤数据
        if (fieldFilterAgg.filterExpress){
            sourceList = sourceList.stream().filter({ m ->
                Script script = ObjectCache.getIfNull(fieldFilterAgg.filterExpress,{ -> factsScriptShell.parse(fieldFilterAgg.filterExpress) })
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
