package com.yss.rules.datavalidator.handler;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yss.rules.datavalidator.handler.base.AbstractHandler;
import com.yss.rules.datavalidator.model.FactFilterField;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public class FactFilterFieldHandler<T extends Collection<Map<String,Object>>,R>  extends AbstractHandler<T,R> {
    private Map<String, FactFilterField> factFilterFieldMap;
    public FactFilterFieldHandler(Map<String, FactFilterField> factFilterFieldMap, BiFunction express) {
        super(express);
        this.factFilterFieldMap = factFilterFieldMap;
    }
    @Override
    public List<String> getFieldKeys() {
        return Lists.newArrayList(factFilterFieldMap.keySet());
    }

    @Override
    public R doHandler(T sourceList) {
        Map<String,Object> rt = Maps.newHashMap();
        factFilterFieldMap.forEach((k,factFilterField)->{
            rt.put(k,getExpressCall().apply(sourceList,factFilterField));
        });
        return (R) rt;
    }
}
