package com.yss.rules.datavalidator.model;


import lombok.Data;

/**
 * 业务字段聚合模型
 * @author daomingzhu
 * @date 2020/4/9 14:40
 */
@Data
public class BusFieldAggregationModel {
    private Object measure;
    private String aggFunction;
    private String desc;
    private BusFieldModel field;
    private String fieldFilter;
}
