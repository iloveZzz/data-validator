package com.yss.rules.datavalidator.domain;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yss.rules.datavalidator.dto.FactDTO;
import com.yss.rules.datavalidator.handler.AbstractHandler;
import com.yss.rules.datavalidator.handler.FactFieldFilterAggHandler;
import com.yss.rules.datavalidator.handler.FactFilterFieldHandler;
import com.yss.rules.datavalidator.handler.FactHandler;
import com.yss.rules.datavalidator.model.FactCompute;
import com.yss.rules.datavalidator.model.FactFieldFilterAgg;
import com.yss.rules.datavalidator.model.FactFilterField;
import com.yss.rules.datavalidator.model.FactModel;
import com.yss.rules.datavalidator.model.base.FactField;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 生成事实对象服务
 * @author daomingzhu
 * @date 2020/4/9 17:46
 */
@Service
public class GenerateFactsService {

    public FactDTO generateFact(FactModel factModel){

        //事实字段
        List<FactField> factFields = factModel.getFactField();
        //需要预处理和计算的字段
        List<FactCompute> factComputes = factModel.getFactCompute();
        //需要过滤和聚合函数处理的字段
        List<FactFieldFilterAgg> factFieldFilterAgs = factModel.getFactFieldFilterAgg();
        List<FactFilterField> factFilterFields = factModel.getFactFilterField();
        //字段操作元数据
        Map<String,FactField> fieldMap = factFields.stream().collect(Collectors.toMap(FactField::getField,v->v, (o, n) -> n, LinkedHashMap::new));
        //元数据转换
        List<Object> data = factModel.getData();
        List<Map<String, Object>> sourceData = getSourceDataMap(data,fieldMap);
        //ss
        Map<String, FactCompute> factComputeMap = factComputes.stream().collect(Collectors.toMap(FactCompute::getField, v -> v, (o, n) -> n, LinkedHashMap::new));
        Map<String, FactFilterField> factFilterFieldsMap = factFilterFields.stream().collect(Collectors.toMap(FactFilterField::getField, v -> v, (o, n) -> n, LinkedHashMap::new));
        //聚合操作元数据
        Map<String, FactFieldFilterAgg> factAggMap =factFieldFilterAgs.stream().collect(Collectors.toMap(FactFieldFilterAgg::getField, v -> v, (o, n) -> n, LinkedHashMap::new));
         //配置句柄
        AbstractHandler<Map<String,Object>,Map<String,Object>> factHandler = new FactHandler<>(factComputeMap,factModel.getComputeFunc());
        AbstractHandler<List<Map<String,Object>>,Object> factFilterAggHandler = new FactFieldFilterAggHandler<>(factAggMap,factModel.getAggFunction());
        AbstractHandler<List<Map<String, Object>>,Object> factFilterFieldHandler = new FactFilterFieldHandler(factFilterFieldsMap,factModel.getFilterFieldFunc());

        return generateFactMap(factHandler,factFilterFieldHandler,factFilterAggHandler,sourceData);
    }
    private FactDTO generateFactMap(AbstractHandler<Map<String,Object>,Map<String,Object>> factHandler,
                                    AbstractHandler<List<Map<String, Object>>,Object> factFilterFieldHandler,
                                    AbstractHandler<List<Map<String,Object>>,Object> factFilterAggHandler,
                                                    List<Map<String,Object>> sourceData){
        List<Map<String, Object>> rt = sourceData.stream().map(factHandler::doHandler).collect(Collectors.toList());
        Object filterField = factFilterFieldHandler.doHandler(rt);
        Object var = factFilterAggHandler.doHandler(rt);
        return new FactDTO(rt,filterField,var);
    }
    private List<Map<String, Object>> getSourceDataMap(List<Object> data,Map<String,FactField> fieldMap) {

        if (Objects.nonNull(data)){
            Optional<Object> any = data.stream().findAny();
            if (any.isPresent()&&!(any.get() instanceof Map)){

                return data.stream().map(t ->{
                    BeanMap beanMap = BeanMap.create(t);
                    Map<String,Object> newSourceData = Maps.newHashMapWithExpectedSize(20);
                    fieldMap.forEach((k,v)->
                            newSourceData.put(k, Objects.nonNull(beanMap.get(k))?beanMap.get(k):v.getDefaultVal()));
                    return newSourceData;
                }).collect(Collectors.toList());
            }
            return  data.stream().map(t -> {
                Map<String,Object> newSourceData = Maps.newHashMapWithExpectedSize(20);
                Map<String,Object> m = (Map<String,Object>)t;
                fieldMap.forEach((k,v)->
                        newSourceData.put(k, Objects.nonNull(m.get(k))?m.get(k):v.getDefaultVal()));
                return newSourceData;
            }).collect(Collectors.toList());
        }
        return Lists.newArrayList();
    }


}
