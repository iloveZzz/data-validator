package com.yss.rules.datavalidator.facts;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yss.rules.datavalidator.facts.base.AbstractHandler;
import com.yss.rules.datavalidator.model.FactFilterField;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public class FactFilterFieldHandler  extends AbstractHandler {
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
    public <R, P> R doHandler(P p) {
        Map<String,Object> rt = Maps.newHashMap();
        factFilterFieldMap.forEach((k,factFilterField)->{
            rt.put(k,getExpressCall().apply(p,factFilterField));
        });
        return (R) rt;
    }

}
