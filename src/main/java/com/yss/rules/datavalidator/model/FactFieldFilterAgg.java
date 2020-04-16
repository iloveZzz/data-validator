package com.yss.rules.datavalidator.model;


import com.yss.rules.datavalidator.model.base.FactField;

/**
 * 事实字段聚合
 * @author daomingzhu
 * @date 2020/4/9 14:40
 */
public class FactFieldFilterAgg  extends FactField{
    private String aggFunc;
    private String aggField;
    private String filterExpress;
    public FactFieldFilterAgg(String field, String fieldName, String type, Object defaultVal) {
        super(field, fieldName, type, defaultVal);
    }

    public String getAggFunc() {
        return aggFunc;
    }

    public void setAggFunc(String aggFunc) {
        this.aggFunc = aggFunc;
    }

    public String getAggField() {
        return aggField;
    }

    public void setAggField(String aggField) {
        this.aggField = aggField;
    }

    public String getFilterExpress() {
        return filterExpress;
    }

    public void setFilterExpress(String filterExpress) {
        this.filterExpress = filterExpress;
    }
}
