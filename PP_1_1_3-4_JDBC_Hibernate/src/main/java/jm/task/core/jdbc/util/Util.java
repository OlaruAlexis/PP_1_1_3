package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final String username = "root";
    private static final String password = "root1";
    private static final String URL = "jdbc:mysql://localhost:3306/my_db?useSSL=false";

    private static SessionFactory sessionFactory;


    public Util() {
    }

    public static Connection connection() {
        try {
            System.out.println("Соединение выполнено");
            return DriverManager.getConnection(URL, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            Configuration configuration = new Configuration();
            Properties setting = new Properties();
            setting.put(AvailableSettings.DRIVER, "com.mysql.jdbc.Driver");
            setting.put(AvailableSettings.URL, URL);
            setting.put(AvailableSettings.USER, username);
            setting.put(AvailableSettings.PASS, password);
            setting.put(AvailableSettings.DIALECT, "org.hibernate.dialect.MySQLDialect");
            setting.put(AvailableSettings.SHOW_SQL, "false");
            setting.put(AvailableSettings.POOL_SIZE, 10);
            setting.put(AvailableSettings.HBM2DDL_AUTO, "none");
            setting.put(AvailableSettings.CURRENT_SESSION_CONTEXT_CLASS, "thread");

            configuration.setProperties(setting);
            configuration.addAnnotatedClass(User.class);
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        }
        return sessionFactory;
    }
}