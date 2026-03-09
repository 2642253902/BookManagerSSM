package com.study.listener;

import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
import jakarta.annotation.PreDestroy;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataSourceCleanup {
    @Autowired
    PooledDataSource dataSource;

    @PreDestroy
    public void close() {
        if (dataSource != null) {
            dataSource.forceCloseAll();
        }
        AbandonedConnectionCleanupThread.checkedShutdown();
    }
}
