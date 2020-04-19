package com.yss.rules.datavalidator.facts;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yss.rules.datavalidator.facts.handler.FactFieldFilterAggHandler;
import com.yss.rules.datavalidator.facts.handler.FactFilterFieldHandler;
import com.yss.rules.datavalidator.facts.handler.FactHandler;
import com.yss.rules.datavalidator.facts.handler.FactSqlDataSetHandler;
import com.yss.rules.datavalidator.model.*;
import com.yss.rules.datavalidator.model.base.FactField;
import org.springframework.cglib.beans.BeanMap;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 生成事实对象服务
 * @author daomingzhu
 * @date 2020/4/9 17:46
 */
public class FactsGenerator {
    private final FactsContext factsContext = new FactsContext();;
    public FactsGenerator(final FactModel factModel){
        //事实接入的字段
        Map<String, FactField> factFieldMap = factModel.getFactField().stream().collect(Collectors.toMap(FactField::getField, v -> v, (o, n) -> n, HashMap::new));
        Map<String,Object> rstMsg = Maps.newHashMap();
        factsContext
                .sourceData(getSourceDataMap(factModel.getData(),factFieldMap))
                .rstMsg(rstMsg)
                .initHandler(factsContext->{
                    //需要预处理和计算的字段
                    factsContext.insertHandler(new FactSqlDataSetHandler(
                            factModel.getFactSqlDataSets().stream().collect(Collectors.toMap(FactSqlDataSet::getField, v -> v, (o, n) -> n, HashMap::new)),
                            factModel.getFactsFun().getSqlFunc()));

                    factsContext.insertHandler(new FactHandler(
                            factModel.getFactCompute().stream().collect(Collectors.toMap(FactCompute::getField, v -> v, (o, n) -> n, HashMap::new)),
                            factModel.getFactsFun().getComputeFunc()));

                    factsContext.insertHandler(new FactFilterFieldHandler(
                            factModel.getFactFilterField().stream().collect(Collectors.toMap(FactFilterField::getField, v -> v, (o, n) -> n, HashMap::new)),
                            factModel.getFactsFun().getFilterFieldFunc()));

                    factsContext.insertHandler(new FactFieldFilterAggHandler(
                            factModel.getFactFieldFilterAgg().stream().collect(Collectors.toMap(FactFieldFilterAgg::getField, v -> v, (o, n) -> n, HashMap::new)),
                            factModel.getFactsFun().getAggFunction()));
                });

    }

    /**
     * 执行字段数据处理的句柄
     * @return FactDTO
     */
    public Map<String,Object> generateFact(Map<String,Object> bindVar){
        return factsContext.execute(bindVar);
    }

    /**
     * 获取原始数据
     * @param data 原始数据
     * @param fieldMap 配置的字段模型
     * @return List<Map<String, Object>>
     */
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
