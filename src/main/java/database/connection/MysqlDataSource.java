package database.connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public enum MysqlDataSource {

    DATA_SOURCE;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/tja";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

    private final HikariDataSource hikariDataSource;

    MysqlDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(DB_URL);
        config.setUsername(DB_USER);
        config.setPassword(DB_PASSWORD);
        hikariDataSource = new HikariDataSource(config);
    }


    public Connection getConnection() throws SQLException {
        return hikariDataSource.getConnection();
    }

    public void rollback(Connection connection) {
        try {
            connection.rollback();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public void closeConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}
