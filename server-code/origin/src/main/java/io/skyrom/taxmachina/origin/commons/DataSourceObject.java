package io.skyrom.taxmachina.origin.commons;

import org.apache.commons.dbcp.BasicDataSource;

/**
 *
 * @author pk
 */
public abstract class DataSourceObject {

    BasicDataSource dataSource;

    public BasicDataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource( BasicDataSource dataSource ) {
        this.dataSource = dataSource;
    }
}
