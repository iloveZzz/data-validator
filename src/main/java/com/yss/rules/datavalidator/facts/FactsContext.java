package com.yss.rules.datavalidator.facts;

import com.google.common.collect.Lists;
import com.yss.rules.datavalidator.facts.base.AbstractHandler;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class FactsContext {
    private final List<AbstractHandler> handlerExecuteQueue = Lists.newLinkedList();
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

    public void execute() {
        handlerExecuteQueue.forEach(AbstractHandler::doHandler);
    }

    public List<Object> getResult() {
        return handlerExecuteQueue.stream().map(abstractHandler -> abstractHandler.result).collect(Collectors.toList());
    }
}
