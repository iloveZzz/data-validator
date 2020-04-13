package com.yss.rules.datavalidator.handler;

import lombok.Data;

import java.util.List;
import java.util.function.BiFunction;

/**
 * @author daomingzhu
 * @date 2020/4/13 15:56
 */
@Data
public abstract class AbstractHandler<T,R> {
    AbstractHandler(BiFunction expressCall) {
        this.expressCall = expressCall;
    }
    public abstract List<String> getFieldKeys();
    public abstract R doHandler(T t);
    private BiFunction expressCall;
    private Object result;

}
