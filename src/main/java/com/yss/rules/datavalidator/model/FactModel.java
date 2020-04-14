package com.yss.rules.datavalidator.model;

import com.yss.rules.datavalidator.model.base.FactField;
import lombok.Data;

import java.util.List;
import java.util.function.BiFunction;

/**
 * @author daomingzhu
 * @date 2020/4/13 13:50
 */
@Data
public class FactModel {
    private List<FactField> factField;
    private List<FactCompute> factCompute;
    private List<FactFieldFilterAgg> factFieldFilterAgg;
    private List<FactFilterField> factFilterField;
    private List<Object> data;
    private BiFunction computeFunc;
    private BiFunction aggFunction;
    private BiFunction filterFieldFunc;
}
