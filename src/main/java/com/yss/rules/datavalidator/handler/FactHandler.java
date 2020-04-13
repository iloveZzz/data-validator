package com.yss.rules.datavalidator.handler;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yss.rules.datavalidator.model.FactCompute;
import com.yss.rules.datavalidator.model.base.FactField;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;

/**
 * @author daomingzhu
 */
public class FactHandler<T extends Map,R>  extends AbstractHandler<T,R>{
    private Map<String, FactField> fieldMap;
    private Map<String, FactCompute> factComputeMap;

    public FactHandler(Map<String, FactField> fieldMap, Map<String, FactCompute> factComputeMap, BiFunction express) {
        super(express);
        this.fieldMap = fieldMap;
        this.factComputeMap = factComputeMap;
    }
    @Override
    public List<String> getFieldKeys(){
        List<String> fieldKeys = Lists.newArrayList(fieldMap.keySet());
        fieldKeys.addAll(factComputeMap.keySet());
        return fieldKeys;
    }

    @Override
    public R doHandler(T sourceMap) {
        Map<String,Object> newSourceData = Maps.newHashMapWithExpectedSize(20);
        fieldMap.forEach((k,v)->
                newSourceData.put(k,Objects.nonNull(sourceMap.get(k))?sourceMap.get(k):v.getDefaultVal()));
        factComputeMap.forEach((k,v)-> newSourceData.put(k,getExpressCall().apply(sourceMap,v)));
        return (R) newSourceData;
    }

}