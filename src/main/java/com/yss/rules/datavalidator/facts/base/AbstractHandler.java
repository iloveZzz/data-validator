package com.yss.rules.datavalidator.facts.base;

import lombok.Data;

import java.util.List;
import java.util.function.BiFunction;

/**
 * @author daomingzhu
 * @date 2020/4/13 15:56
 */
@Data
public abstract class AbstractHandler {
    public AbstractHandler(BiFunction expressCall) {
        this.expressCall = expressCall;
    }
    public abstract List<String> getFieldKeys();
    public abstract <R,P> R doHandler(P p);
    private BiFunction expressCall;
    private Object result;

}
