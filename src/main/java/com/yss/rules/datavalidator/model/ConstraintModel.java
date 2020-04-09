package com.yss.rules.datavalidator.model;

import lombok.Data;
import lombok.ToString;

/**
 * 完整性校验 5大约束
 * @author daomingzhu
 * @date 2020/4/9 14:25
 */
@Data
@ToString
public class ConstraintModel {
    private Boolean notNull;
    private Boolean pk;
    private Boolean unique;
    private Boolean fk;
    private String check;
}
