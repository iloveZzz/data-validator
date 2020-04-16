@Grab('org.hsqldb:hsqldb:2.3.2')
@Grab('commons-dbcp:commons-dbcp:1.4')
import groovy.sql.Sql
import com.alibaba.druid.pool.DruidDataSource

def dbSettings = [
            'druid.url':'jdbc:oracle:thin:@192.168.128.220:1521:kforcl',
            'druid.driver':'oracle.jdbc.OracleDriver',
            'druid.user':'indbadmin',
            'druid.password':'indbadmin'
]
def dataSource = new DruidDataSource()
Properties properties = new Properties(dbSettings)
dataSource.configFromPropety(properties)

def sql = new Sql(dataSource)
sql.execute("select * from aaac")
sql.close()