package com.yss.rules.datavalidator.model.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * 事实字段对象
 * @author daomingzhu
 * @date 2020/4/9 12:56
 */
@Data
@ToString
@AllArgsConstructor
public class FactField {
    private String field;
    private String fieldName;
    private String type;
    private Object defaultVal;
}


