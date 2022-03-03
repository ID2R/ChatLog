package dev.id2r.chatlog.common.connection;

import lombok.Data;

@Data
public class Credentials {
    private final String host;
    private final int port;
    private final String username, password, database;
}
