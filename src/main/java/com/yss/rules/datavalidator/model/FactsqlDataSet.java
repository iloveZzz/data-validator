package com.yss.rules.datavalidator.model;

import com.yss.rules.datavalidator.model.base.FactField;

import java.util.Map;

public class FactSqlDataSet extends FactField {
    private String db;
    private Map<String,Object> param;
    private String cache;
    private String sqlExpress;
    public FactSqlDataSet(String field, String fieldName, String type, Object defaultVal) {
        super(field, fieldName, type, defaultVal);
    }

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }

    public String getSqlExpress() {
        return sqlExpress;
    }

    public void setSqlExpress(String sqlExpress) {
        this.sqlExpress = sqlExpress;
    }


    public Map<String, Object> getParam() {
        return param;
    }

    public void setParam(Map<String, Object> param) {
        this.param = param;
    }

    public String getCache() {
        return cache;
    }

    public void setCache(String cache) {
        this.cache = cache;
    }
}
