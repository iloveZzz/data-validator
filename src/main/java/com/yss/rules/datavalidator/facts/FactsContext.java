package com.yss.rules.datavalidator.facts;

import com.google.common.collect.Queues;
import com.yss.rules.datavalidator.facts.base.AbstractHandler;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.function.Consumer;

public class FactsContext {
    private final Queue<AbstractHandler> handlerExecuteQueue = Queues.newConcurrentLinkedQueue();
    private List<Map<String,Object>> sourceData;
    private Map<String,Object> rstMsg;

    public void insertHandler(AbstractHandler handler){
        handlerExecuteQueue.add(handler);
    }

    public FactsContext rstMsg(Map<String,Object> rstMsg){
        this.rstMsg = rstMsg;
        return this;
    }
    public void initHandler(Consumer<FactsContext> function){
        function.accept(this);
    }
    public FactsContext sourceData(List<Map<String,Object>> sourceData){
        this.sourceData = sourceData;
        return this;
    }

    public Map<String,Object> execute(Map<String,Object> bindVar) {
        final AbstractHandler factSqlDataSetHandler = handlerExecuteQueue.poll();
        Map<String, List<Map>> sqlData = factSqlDataSetHandler.doHandler(bindVar);
        //事实字段预处理句柄
        final AbstractHandler factHandler = handlerExecuteQueue.poll();
        List<Map<String, Object>> sourceDataLs = factHandler.doHandler(sourceData);
        //事实字段过滤句柄
        final AbstractHandler filterHandler = handlerExecuteQueue.poll();
        Object filterField = Objects.requireNonNull(filterHandler).doHandler(sourceDataLs);
        //事实字段聚合句柄
        final AbstractHandler aggHandler = handlerExecuteQueue.poll();
        Object aggData = Objects.requireNonNull(aggHandler).doHandler(sourceDataLs);

        rstMsg.put("sqlData",sqlData);
        rstMsg.put("sourceDataLs",sourceDataLs);
        rstMsg.put("filterField",filterField);
        rstMsg.put("aggData",aggData);
        return rstMsg;
    }
}
