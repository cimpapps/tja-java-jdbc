package database.connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public enum MysqlDataSource {

    DATA_SOURCE;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/tja";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

    private HikariDataSource hikariDataSource;

    private MysqlDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(DB_URL);
        config.setUsername(DB_USER);
        config.setPassword(DB_PASSWORD);
        hikariDataSource = new HikariDataSource(config);
    }


    public Optional<Connection> getConnection() {
        try {
            return Optional.ofNullable(hikariDataSource.getConnection());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.empty();
    }


}
