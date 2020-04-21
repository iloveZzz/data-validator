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

    public FactsContext option(){
        return _self();
    }
    public void initHandler(Consumer<List<AbstractHandler>> function){
        function.accept(handlers);
    }

    public void execute() {
        handlers.forEach(AbstractHandler::doHandler);
    }

    public Map<String, Object> result() {
        Map<String, Object> rts = Maps.newHashMap();
        handlers.forEach(abstractHandler -> rts.put(abstractHandler.getHandlerName(),abstractHandler.result));
        return rts;
    }
}
