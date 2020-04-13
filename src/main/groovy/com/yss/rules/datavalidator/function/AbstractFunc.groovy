package com.yss.rules.datavalidator.function


import java.time.format.DateTimeFormatter;

/**
 * 业务数据-元数据处理的函数库
 * @author daomingzhu
 * @date 2020/4/9 16:31
 */
abstract class AbstractFunc extends Script {
    Object format(String fieldKey,String pattern){
        Map<String,Object> source = this.getProperty("source")
        if (source!=null&&source[fieldKey]!=null){
            return source[fieldKey].format(DateTimeFormatter.ofPattern(pattern))
        }
        ''
    }
    Boolean assertFilter(){

    }
}
