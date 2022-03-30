package dev.id2r.chatlog.common.connection.database.sql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import dev.id2r.chatlog.common.connection.ConnectionState;
import dev.id2r.chatlog.common.connection.Credentials;
import dev.id2r.chatlog.common.connection.database.DatabaseConnection;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Locale;

/**
 * @author Akram Louze
 * @since 1.0-BETA
 */
@RequiredArgsConstructor
public class SQLConnection extends DatabaseConnection<HikariDataSource> {

    private final Credentials credentials;
    private HikariDataSource dataSource;

    @Override
    public HikariDataSource getPool() {
        return dataSource;
    }

    @Override
    public ConnectionState open() {
        try {

            Class.forName("com.zaxxer.hikari.HikariConfig");

            final HikariConfig config = new HikariConfig();

            config.setPoolName("chatlog-pool");
            config.addDataSourceProperty("serverName", credentials.getHost());
            config.addDataSourceProperty("port", credentials.getPort());
            config.addDataSourceProperty("databaseName", credentials.getDatabase());
            config.addDataSourceProperty("user", credentials.getUsername());
            config.addDataSourceProperty("password", credentials.getPassword());

            this.dataSource = new HikariDataSource(config);

            try (final Connection connection = getPool().getConnection()) {
                try (final PreparedStatement statement = connection.prepareStatement(
                        "CREATE TABLE IF NOT EXISTS chatlog_messages (id INT NOT NULL AUTO_INCREMENT, server VARCHAR(32), " +
                                "name VARCHAR(16), message VARCHAR(400), timestamp VARCHAR(50), PRIMARY KEY (id)) " +
                                "DEFAULT CHARACTER utf8 DEFAULT COLLATE utf8_general_ci"
                )) {
                    statement.executeUpdate();
                }
                try (final PreparedStatement statement = connection.prepareStatement(
                        "CREATE TABLE IF NOT EXISTS chatlog_report (id INT NOT NULL AUTO_INCREMENT, server VARCHAR(32), " +
                                "name VARCHAR(16), message VARCHAR(400), timestamp VARCHAR(50), reportid TEXT, PRIMARY KEY (id)) " +
                                "DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci"
                )) {
                    statement.executeUpdate();
                }
            }

        } catch (Exception exception) {
            if (exception instanceof ClassNotFoundException) {
                return ConnectionState.LIBRARY_MISSING;
            }
            if (exception instanceof SQLException) {
                final SQLException sqlException = (SQLException) exception;
                if (sqlException.getMessage().toLowerCase(Locale.ROOT).contains("timed out")) {
                    return ConnectionState.TIMED_OUT;
                }
            }
            exception.printStackTrace();
        }
        return ConnectionState.DUMMY;
    }
    @Override
    public Credentials getCredentials() {
        return credentials;
    }

    @Override
    public SQLDatabase provideDatabase() {
        return new SQLDatabase(this);
    }

    @Override
    public Connection getResource() {
        try (final Connection connection = getPool().getConnection()) {
            return connection;
        } catch (SQLException exception) {
            exception.printStackTrace();
            return null;
        }
    }
}