package com.yss.rules.datavalidator.facts.handler;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yss.rules.datavalidator.facts.base.AbstractHandler;
import com.yss.rules.datavalidator.model.FactFilterField;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * @author daomingzhu
 */
public class FactFilterFieldHandler  extends AbstractHandler {
    private Map<String, FactFilterField> factFilterFieldMap;
    private final List<Map<String, Object>> handlerParam;
    public FactFilterFieldHandler(Map<String, FactFilterField> factFilterFieldMap,List<Map<String, Object>> handlerParam, BiFunction express) {
        super("filterField",express);
        this.factFilterFieldMap = factFilterFieldMap;
        this.handlerParam = handlerParam;
    }
    @Override
    public List<String> getFieldKeys() {
        return Lists.newArrayList(factFilterFieldMap.keySet());
    }

    @Override
    public void doHandler() {
        Map<String,Object> rt = Maps.newHashMap();
        factFilterFieldMap.forEach((k,factFilterField)->{
            rt.put(k,getExpressCall().apply(handlerParam,factFilterField));
        });
        super.result = rt;
    }

}
