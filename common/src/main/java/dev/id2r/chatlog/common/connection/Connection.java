package dev.id2r.chatlog.common.connection;

public interface Connection<P extends AutoCloseable> {
    P getPool();
    ConnectionState open();
    Credentials getCredentials();
}
