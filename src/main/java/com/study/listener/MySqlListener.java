package com.study.listener;

import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

@WebListener
public class MySqlListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        Driver d = null;
        while (drivers.hasMoreElements()) {
            try {
                d = drivers.nextElement();
                DriverManager.deregisterDriver(d);
            }
            catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        AbandonedConnectionCleanupThread.checkedShutdown();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
