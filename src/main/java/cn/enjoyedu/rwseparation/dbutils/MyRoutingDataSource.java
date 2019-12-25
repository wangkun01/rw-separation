package cn.enjoyedu.rwseparation.dbutils;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;


public class MyRoutingDataSource extends AbstractRoutingDataSource {
    /*在运行时, 根据某种规则，比如key值，比如当前线程的id
来动态切换到真正的DataSource上*/
    @Override
    protected Object determineCurrentLookupKey() {
        return DBContextHolder.get();
    }
}
