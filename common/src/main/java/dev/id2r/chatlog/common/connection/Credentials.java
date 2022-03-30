package dev.id2r.chatlog.common.connection;

import lombok.Getter;

/**
 * @author Akram Louze
 * @since 1.0-BETA
 */
@Getter
public class Credentials {
    private final String host;
    private final int port;
    private final String username, password, database;

    private Credentials(
            String host,
            int port,
            String username,
            String password,
            String database) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.database = database;
    }

    /**
     * To make the code more clean, we need to
     * make Credentials object buildable.
     *
     * @return Credentials builder object
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * To make the object buildable, we need
     * also to create another object called
     * builder to build Credentials.
     *
     * @since 1.0-BETA
     */
    public static class Builder {
        private String host, username, password, database;
        private int port;
        public Builder host(String host) {
            this.host = host;
            return this;
        }
        public Builder port(int port) {
            this.port = port;
            return this;
        }
        public Builder username(String username) {
            this.username = username;
            return this;
        }
        public Builder password(String password) {
            this.password = password;
            return this;
        }
        public Builder database(String database) {
            this.database = database;
            return this;
        }
        public Credentials build() {
            return new Credentials(host, port, username, password, database);
        }
    }

}
