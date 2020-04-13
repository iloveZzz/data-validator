package com.yss.rules.datavalidator.domain;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yss.rules.datavalidator.dto.User;
import com.yss.rules.datavalidator.model.BusFieldModel;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * 生成事实对象服务
 * @author daomingzhu
 * @date 2020/4/9 17:46
 */
@Service
public class GenerateFactsService {
    public List<Map<String,Object>> generateFactMap(Map<String, BusFieldModel> businessSchema,
                                                    List<Map<String,Object>> data,
                                                    BiFunction<Map<String, Object>,
                                                            BusFieldModel,Objects> expression){

       return data.stream().map(source->{
            Map<String,Object> r = Maps.newHashMapWithExpectedSize(businessSchema.size());
            businessSchema.forEach(
                    (fieldName,fieldModel)->
                            r.put(fieldModel.getField(),
                            getHandler().getFactVal(source,fieldModel,expression))
            );
            return r;
        }).collect(Collectors.toList());
    }

    public List<Map<String,Object>> generateFact(Map<String, BusFieldModel> businessSchema,
                                               List<Object> data,
                                               BiFunction<Map<String, Object>,BusFieldModel,Objects> expression){
        List<Map<String,Object>> mps = data.stream().map(t -> (Map<String,Object>)BeanMap.create(t)).collect(Collectors.toList());
        return generateFactMap(businessSchema,mps,expression);
    }

    private BusinessHandler handler;
    private BusinessHandler getHandler() {
        if (handler == null) {
            handler = new BusinessHandler();
        }
        return handler;
    }

    private class BusinessHandler{
        Object getFactVal(Map<String, Object> source,
                          BusFieldModel busFieldModel,
                          BiFunction<Map<String, Object>,BusFieldModel,Objects> expression){
            if (Objects.nonNull(busFieldModel.getExpression())){
                //执行表达式 获取值
                return expression.apply(source,busFieldModel);
            }
            return Optional
                    .ofNullable(source.get(busFieldModel.getField()))
                    .orElse(busFieldModel.getDefaultVal());
        }
    }
}
