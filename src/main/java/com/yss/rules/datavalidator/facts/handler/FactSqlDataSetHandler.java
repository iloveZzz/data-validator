package com.yss.rules.datavalidator.facts.handler;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yss.rules.datavalidator.facts.base.AbstractHandler;
import com.yss.rules.datavalidator.model.FactSqlDataSet;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public class FactSqlDataSetHandler extends AbstractHandler {
    private final Map<String, FactSqlDataSet> factSqlDataSetMap;
    private final Map<String,Object> handlerParam;
    public FactSqlDataSetHandler(Map<String, FactSqlDataSet> factSqlDataSetMap,Map<String,Object> handlerParam,BiFunction expressCall) {
        super("sqlDataSet",expressCall);
        this.factSqlDataSetMap = factSqlDataSetMap;
        this.handlerParam = handlerParam;
    }

    @Override
    public List<String> getFieldKeys() {
        return Lists.newArrayList(factSqlDataSetMap.keySet());
    }

    @Override
    public  void doHandler() {
        Map<String,Object> rt = Maps.newHashMap();
        factSqlDataSetMap.forEach((k,v)-> rt.put(k,getExpressCall().apply(handlerParam,v)));
        super.result = rt;
    }
}
