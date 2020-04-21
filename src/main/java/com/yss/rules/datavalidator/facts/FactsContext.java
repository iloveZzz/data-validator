package com.yss.rules.datavalidator.facts;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yss.rules.datavalidator.facts.base.AbstractHandler;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class FactsContext {
    private final List<AbstractHandler> handlers = Lists.newLinkedList();

    public FactsContext _self(){
        return this;
    }
    public void insertHandler(AbstractHandler handler){
        handlers.add(handler);
    }

    public FactsContext option(){
        return _self();
    }
    public void initHandler(Consumer<FactsContext> function){
        function.accept(this);
    }

    public void execute() {
        handlers.forEach(AbstractHandler::doHandler);
    }

    public Map<Object, Object> result() {
        Map<Object, Object> rts = Maps.newHashMap();
        handlers.forEach(abstractHandler -> rts.put(abstractHandler.getHandlerName(),abstractHandler.result));
        return rts;
    }
}
