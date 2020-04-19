package com.yss.rules.datavalidator.facts.handler;

import com.google.common.collect.Lists;
import com.yss.rules.datavalidator.facts.base.AbstractHandler;
import com.yss.rules.datavalidator.model.FactCompute;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * @author daomingzhu
 */
public class FactHandler  extends AbstractHandler {
    private Map<String, FactCompute> factComputeMap;

    public FactHandler( Map<String, FactCompute> factComputeMap, BiFunction express) {
        super(express);
        this.factComputeMap = factComputeMap;
    }
    @Override
    public List<String> getFieldKeys(){
        return Lists.newArrayList(factComputeMap.keySet());
    }

    @Override
    public <R, P> R doHandler(P sourceMap) {
        List<Map<String,Object>> sourceData = ((List<Map<String,Object>>)sourceMap);
        sourceData.forEach(sm->
                        factComputeMap.forEach((k,v)-> sm.put(k,getExpressCall().apply(sm,v)))
                );
        return (R) sourceMap;
    }
}