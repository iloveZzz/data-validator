package com.yss.rules.datavalidator.model.base;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FieldFilter {
    private String filterFieldPre;
    private String operator;
    private String filterFieldPost;
    private String connector;
}
