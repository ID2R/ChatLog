package dev.id2r.chatlog.common.connection;

/**
 * @author AkramL
 * @param <P> Connection pool
 * @since 1.0-BETA
 */
public interface IConnection<P extends AutoCloseable> {

    /**
     * We need to assign a pool class to make
     * connection closing easier
     *
     * @return Connection pool
     */
    P getPool();

    /**
     * When you open a connection, there's
     * variety scenarios might happen, so
     * we need to make an identifier for it
     *
     * @return state of the connection
     */
    ConnectionState open();

    Credentials getCredentials();

    default void close() throws Exception {
        getPool().close();
    }
}
