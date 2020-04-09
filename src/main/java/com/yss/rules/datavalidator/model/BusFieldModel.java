package com.yss.rules.datavalidator.model;

import lombok.Data;
import lombok.ToString;

/**
 * @author daomingzhu
 * @date 2020/4/9 12:56
 */
@Data
@ToString
public class BusFieldModel {
    private String field;
    private String type;
    private Object defaultVal;
    private String expression;
}


