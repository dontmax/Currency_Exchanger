package db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DataSourceUtil {
	private static HikariDataSource dataSource;
	
	static {
		
        try {
            Class.forName("org.sqlite.JDBC"); // Регистрация драйвера
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("SQLite JDBC driver not found", e);
        }

		
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl("jdbc:sqlite:C:/Users/79174/eclipse-DBs/Currency_DB.sqlite");
		config.setMaximumPoolSize(10);
		config.setMinimumIdle(2);
		config.setIdleTimeout(30000);
		config.setMaxLifetime(1800000);
		config.setConnectionTimeout(30000);
		
		dataSource = new HikariDataSource(config);
	}
	
	public static Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}
	
	public static DataSource getDataSource() {
		return dataSource;
	}
	
}
