package com.yss.rules.datavalidator.handler;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yss.rules.datavalidator.handler.base.AbstractHandler;
import com.yss.rules.datavalidator.model.FactFieldFilterAgg;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * @author daomingzhu
 */
public class FactFieldFilterAggHandler<T extends Collection<Map<String,Object>>,R>  extends AbstractHandler<T,R> {
    private Map<String, FactFieldFilterAgg> factFieldFilterAgg;
    public FactFieldFilterAggHandler(Map<String, FactFieldFilterAgg> factFieldFilterAgg, BiFunction express) {
        super(express);
        this.factFieldFilterAgg = factFieldFilterAgg;
    }

    @Override
    public List<String> getFieldKeys() {
        return Lists.newArrayList(factFieldFilterAgg.keySet());
    }

    @Override
    public R doHandler(T listMap) {
        Map<String,Object> rt = Maps.newHashMap();
        factFieldFilterAgg.forEach((k,v)->{
            rt.put(k,getExpressCall().apply(listMap,v));
        });
        return (R)rt;
    }

}