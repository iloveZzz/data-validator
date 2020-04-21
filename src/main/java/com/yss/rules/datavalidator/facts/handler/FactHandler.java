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
    private List<Map<String,Object>> param;

    public FactHandler( Map<String, FactCompute> factComputeMap,List<Map<String,Object>> param, BiFunction express) {
        super("factField",express);
        this.factComputeMap = factComputeMap;
        this.param = param;
    }
    @Override
    public List<String> getFieldKeys(){
        return Lists.newArrayList(factComputeMap.keySet());
    }

    @Override
    public  void doHandler() {
        param.forEach(sm->
                        factComputeMap.forEach((k,v)-> sm.put(k,getExpressCall().apply(sm,v)))
                );
        super.result = param;
    }
}