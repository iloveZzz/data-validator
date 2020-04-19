package com.yss.rules.datavalidator.facts.handler;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yss.rules.datavalidator.facts.base.AbstractHandler;
import com.yss.rules.datavalidator.model.FactSqlDataSet;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public class FactSqlDataSetHandler extends AbstractHandler {
    private Map<String, FactSqlDataSet> factSqlDataSetMap;

    public FactSqlDataSetHandler(Map<String, FactSqlDataSet> factSqlDataSetMap,BiFunction expressCall) {
        super(expressCall);
        this.factSqlDataSetMap = factSqlDataSetMap;
    }

    @Override
    public List<String> getFieldKeys() {
        return Lists.newArrayList(factSqlDataSetMap.keySet());
    }

    @Override
    public <R, P> R doHandler(P p) {
        final Map<String,Object> bindVar = (Map<String,Object>)p;

        Map<String,Object> rt = Maps.newHashMap();
        factSqlDataSetMap.forEach((k,v)-> rt.put(k,getExpressCall().apply(bindVar,v)));
        return (R) rt;
    }
}
