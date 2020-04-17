import groovy.sql.Sql
import com.alibaba.druid.pool.DruidDataSource

def dbSettings = [
            'druid.url':'jdbc:oracle:thin:@192.168.128.220:1521:kforcl',
            'druid.driver':'oracle.jdbc.OracleDriver',
            'druid.username':'indbadmin',
            'druid.password':'indbadmin'
]
def dataSource = new DruidDataSource()
Properties properties = new Properties(dbSettings)
dataSource.configFromPropety(properties)

def sql = new Sql(dataSource)
def execute = sql.execute("select * from TP_REP_BASIC_PARAM")
println execute
sql.close()