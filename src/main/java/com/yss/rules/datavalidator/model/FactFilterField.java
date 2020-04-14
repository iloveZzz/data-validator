package com.yss.rules.datavalidator.model;

import com.yss.rules.datavalidator.model.base.FactField;

public class FactFilterField  extends FactField {
    private String filterExpress;
    public FactFilterField(String field, String fieldName, String type, Object defaultVal) {
        super(field, fieldName, type, defaultVal);
    }

    public String getFilterExpress() {
        return filterExpress;
    }

    public void setFilterExpress(String filterExpress) {
        this.filterExpress = filterExpress;
    }
}
