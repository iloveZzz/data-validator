package com.yss.rules.datavalidator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author daomingzhu
 * @date 2020/4/14 16:14
 */
@Data
@AllArgsConstructor
public class FactDTO {
    private List<Map<String,Object>> data;
    private Object bindVar;
}
