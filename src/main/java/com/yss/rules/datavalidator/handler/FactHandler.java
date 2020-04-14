package com.yss.rules.datavalidator.handler;

import com.google.common.collect.Lists;
import com.yss.rules.datavalidator.model.FactCompute;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * @author daomingzhu
 */
public class FactHandler<T extends Map,R>  extends AbstractHandler<T,R>{
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
    public R doHandler(T sourceMap) {
        factComputeMap.forEach((k,v)-> sourceMap.put(k,getExpressCall().apply(sourceMap,v)));
        return (R) sourceMap;
    }

}