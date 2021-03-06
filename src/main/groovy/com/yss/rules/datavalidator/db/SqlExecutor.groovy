package com.yss.rules.datavalidator.db

import com.alibaba.druid.pool.DruidDataSource
import groovy.sql.Sql
import groovy.transform.CompileStatic

@CompileStatic
class SqlExecutor {
    static Map<String, Sql> sqlMap = [:]
    static {
        def dbMap = [
                'local': [
                        'druid.url'         : 'jdbc:oracle:thin:@localhost:1521:DBZHU',
                        'druid.driver'      : 'oracle.jdbc.OracleDriver',
                        'druid.username'    : 'INDBADMIN',
                        'druid.password'    : 'INDBADMIN',
                        'druid.initialSize' : 5,
                        'druid.minIdle'     : 3,
                        'druid.maxActive'   : 10,
                        'druid.testOnBorrow': true,
                ],
                'local_SOFA': [
                        'druid.url'         : 'jdbc:oracle:thin:@localhost:1521:DBZHU',
                        'druid.driver'      : 'oracle.jdbc.OracleDriver',
                        'druid.username'    : 'SOFAREPORT',
                        'druid.password'    : 'SOFAREPORT',
                        'druid.initialSize' : 5,
                        'druid.minIdle'     : 3,
                        'druid.maxActive'   : 10,
                        'druid.testOnBorrow': true,
                ],
                'report_220': [
                        'druid.url'         : 'jdbc:oracle:thin:@192.168.128.220:1521:kforcl',
                        'druid.driver'      : 'oracle.jdbc.OracleDriver',
                        'druid.username'    : 'indbadmin',
                        'druid.password'    : 'indbadmin',
                        'druid.initialSize' : 5,
                        'druid.minIdle'     : 3,
                        'druid.maxActive'   : 10,
                        'druid.testOnBorrow': true,
                ]
        ]
        dbMap.forEach((k, Map v) -> {
            def dataSource = new DruidDataSource()
            def properties = new Properties()
            properties.putAll(v)
            dataSource.configFromPropety(properties)
            def sql = new Sql(dataSource)
            sqlMap.put(k, sql)
        })

    }

    static Sql getDb(dbKey) {
        sqlMap.get(dbKey)
    }
    static def query(String dbKey,String sql) {
        def localSql = getDb(dbKey)
        localSql.rows(sql)
    }

    static def query(String dbKey,String sql, Map param) {
        def localSql = getDb(dbKey)
        localSql.rows(sql, param, 0, 10000)
    }

    static def query(dbKey,String sql, Map param,int offset,int maxRows) {
        def localSql = getDb(dbKey)
        localSql.rows(sql, param, offset, maxRows)
    }

}
