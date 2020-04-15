package com.yss.rules.datavalidator.facts;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import com.yss.rules.datavalidator.dto.FactDTO;
import com.yss.rules.datavalidator.facts.base.AbstractHandler;
import com.yss.rules.datavalidator.model.FactCompute;
import com.yss.rules.datavalidator.model.FactFieldFilterAgg;
import com.yss.rules.datavalidator.model.FactFilterField;
import com.yss.rules.datavalidator.model.FactModel;
import com.yss.rules.datavalidator.model.base.FactField;
import org.springframework.cglib.beans.BeanMap;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

/**
 * 生成事实对象服务
 * @author daomingzhu
 * @date 2020/4/9 17:46
 */
public class GenerateFactsContext {
    private FactModel factModel;
    private List<Map<String,Object>> sourceData;
    private ConcurrentLinkedQueue<AbstractHandler> handlerExecuteQueue = Queues.newConcurrentLinkedQueue();
    public GenerateFactsContext(FactModel factModel){
        //事实接入的字段
        this.sourceData = getSourceDataMap(
                factModel.getData(),
                factModel.getFactField().stream().collect(Collectors.toMap(FactField::getField,v->v, (o, n) -> n, HashMap::new)));
        this.factModel = factModel;
        //执行初始化上下文
        init();
    }
    /**
     * 生成事实业务数据对象
     * @return FactDTO
     */
    public void init(){
        //需要预处理和计算的字段
        handlerExecuteQueue.add(new FactHandler(
                factModel.getFactCompute().stream().collect(Collectors.toMap(FactCompute::getField, v -> v, (o, n) -> n, HashMap::new)),
                factModel.getComputeFunc()));
        handlerExecuteQueue.add(new FactFieldFilterAggHandler(
                factModel.getFactFieldFilterAgg().stream().collect(Collectors.toMap(FactFieldFilterAgg::getField, v -> v, (o, n) -> n, HashMap::new)),
                factModel.getAggFunction()));
        handlerExecuteQueue.add(new FactFilterFieldHandler(
                factModel.getFactFilterField().stream().collect(Collectors.toMap(FactFilterField::getField, v -> v, (o, n) -> n, HashMap::new)),
                factModel.getFilterFieldFunc()));
    }

    /**
     * 执行字段数据处理的句柄
     * @return FactDTO
     */
    public FactDTO generateFact(){
        //事实字段预处理句柄
        final AbstractHandler factHandler = handlerExecuteQueue.poll();
        List<Map<String, Object>> rt = sourceData.stream().map(s-> factHandler.<Map<String, Object>, Map<String, Object>>doHandler(s)).collect(Collectors.toList());
        //事实字段过滤句柄
        Object filterField = handlerExecuteQueue.poll().doHandler(rt);
        //事实字段聚合句柄
        Object var = handlerExecuteQueue.poll().doHandler(rt);

        return new FactDTO(rt,filterField,var);
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
