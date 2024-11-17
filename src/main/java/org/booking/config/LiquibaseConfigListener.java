package org.booking.config;

import com.zaxxer.hikari.HikariDataSource;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.database.jvm.JdbcConnection;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

@WebListener
public class LiquibaseConfigListener implements ServletContextListener {

    private HikariDataSource dataSource;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            Properties properties = new Properties();

            try (InputStream inputStream = LiquibaseConfigListener.class.getClassLoader().getResourceAsStream("liquibase.properties")) {
                if (inputStream == null) {
                    System.out.println("Error: Unable to find liquibase config file.");
                    return;
                }
                properties.load(inputStream);

                dataSource = HikariCPConfig.getDataSource();

                try (Connection connection = dataSource.getConnection()) {
                    Database database = DatabaseFactory.getInstance()
                            .findCorrectDatabaseImplementation(new JdbcConnection(connection));

                    String changeLogFile = "db/changelog-master.yaml";

                    Liquibase liquibase = new Liquibase(changeLogFile, new ClassLoaderResourceAccessor(), database);
                    liquibase.update(new Contexts(), new LabelExpression());
                    System.out.println("Liquibase update completed successfully.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error occurred during Liquibase initialization: " + e.getMessage());
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (dataSource != null) {
            dataSource.close();
            System.out.println("HikariCP connection pool closed successfully.");
        }
    }
}
