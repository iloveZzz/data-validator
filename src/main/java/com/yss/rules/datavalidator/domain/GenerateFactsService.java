package com.yss.rules.datavalidator.domain;

import com.yss.rules.datavalidator.handler.AbstractHandler;
import com.yss.rules.datavalidator.handler.FactFieldFilterAggHandler;
import com.yss.rules.datavalidator.handler.FactHandler;
import com.yss.rules.datavalidator.model.FactCompute;
import com.yss.rules.datavalidator.model.FactFieldFilterAgg;
import com.yss.rules.datavalidator.model.FactModel;
import com.yss.rules.datavalidator.model.base.FactField;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 生成事实对象服务
 * @author daomingzhu
 * @date 2020/4/9 17:46
 */
@Service
public class GenerateFactsService {
    public List<Map<String,Object>> generateFactMap(AbstractHandler<Map<String,Object>,Map<String,Object>> factHandler,
                                                    AbstractHandler<List<Map<String,Object>>,Object> factFilterAggHandler,
                                                    List<Map<String,Object>> sourceData){
        List<Map<String, Object>> rt = sourceData.stream().map(factHandler::doHandler).collect(Collectors.toList());
        Object o = factFilterAggHandler.doHandler(rt);
        System.out.println(o);
        return rt;
    }
    public List<Map<String,Object>> generateFact(FactModel factModel){

        //事实字段
        List<FactField> factFields = factModel.getFactField();
        //需要预处理和计算的字段
        List<FactCompute> factComputes = factModel.getFactCompute();
        //需要过滤和聚合函数处理的字段
        List<FactFieldFilterAgg> factFieldFilterAgs = factModel.getFactFieldFilterAgg();
        //字段操作元数据
        Map<String,FactField> fieldMap = factFields.stream().collect(Collectors.toMap(FactField::getField,v->v, (o, n) -> n, LinkedHashMap::new));
        Map<String, FactCompute> factComputeMap = factComputes.stream().collect(Collectors.toMap(FactCompute::getField, v -> v, (o, n) -> n, LinkedHashMap::new));
        //聚合操作元数据
        Map<String, FactFieldFilterAgg> factAggMap =factFieldFilterAgs.stream().collect(Collectors.toMap(FactFieldFilterAgg::getField, v -> v, (o, n) -> n, LinkedHashMap::new));
        //元数据转换
        List<Map<String,Object>> sourceData = factModel.getData().stream().map(t -> (Map<String,Object>)BeanMap.create(t)).collect(Collectors.toList());
        //配置句柄
        AbstractHandler<Map<String,Object>,Map<String,Object>> factHandler = new FactHandler(fieldMap, factComputeMap,factModel.getComputeFunc());
        AbstractHandler<List<Map<String,Object>>,Object> factFilterAggHandler = new FactFieldFilterAggHandler(factAggMap,factModel.getAggFunction());

        return generateFactMap(factHandler,factFilterAggHandler,sourceData);
    }



}
