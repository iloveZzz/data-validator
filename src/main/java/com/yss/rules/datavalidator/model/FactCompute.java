package com.yss.rules.datavalidator.model;

import com.yss.rules.datavalidator.model.base.FactField;

/**
 * 事实字段计算
 * @author daomingzhu
 * @date 2020/4/13 10:50
 */
public class FactCompute extends FactField {
    private String expression;
    public FactCompute(String field, String fieldName, String type, Object defaultVal) {
        super(field, fieldName, type, defaultVal);
    }
}
