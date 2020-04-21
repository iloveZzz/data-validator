package com.yss.rules.datavalidator.facts.handler;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yss.rules.datavalidator.facts.base.AbstractHandler;
import com.yss.rules.datavalidator.model.FactFieldFilterAgg;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * @author daomingzhu  sourceDataLs
 */
public class FactFieldFilterAggHandler  extends AbstractHandler {
    private final Map<String, FactFieldFilterAgg> factFieldFilterAgg;
    private final List<Map<String, Object>> handlerParam;
    public FactFieldFilterAggHandler(Map<String, FactFieldFilterAgg> factFieldFilterAgg,List<Map<String, Object>> handlerParam,BiFunction express) {
        super("fieldFilterAgg",express);
        this.factFieldFilterAgg = factFieldFilterAgg;
        this.handlerParam = handlerParam;
    }

    @Override
    public List<String> getFieldKeys() {
        return Lists.newArrayList(factFieldFilterAgg.keySet());
    }

    @Override
    public void doHandler() {
        Map<String,Object> rt = Maps.newHashMap();
        factFieldFilterAgg.forEach((k,v)->{
            rt.put(k,getExpressCall().apply(handlerParam,v));
        });
        super.result = rt;
    }
}