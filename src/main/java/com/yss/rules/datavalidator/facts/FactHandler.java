package com.yss.rules.datavalidator.facts;

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
        factComputeMap.forEach((k,v)-> ((Map)sourceMap).put(k,getExpressCall().apply(sourceMap,v)));
        return (R) sourceMap;
    }
}