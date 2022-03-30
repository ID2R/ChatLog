package dev.id2r.chatlog.common.connection.database;

import dev.id2r.chatlog.common.connection.IConnection;

/**
 * @author Akram Louze
 * @param <P> The type of the pool.
 */
public abstract class DatabaseConnection<P extends AutoCloseable> implements IConnection<P> {
    /**
     * We need to provide the type of database
     * @return the provided database
     */
    public abstract Database provideDatabase();

    /**
     * To make connections more efficient, we can
     * use a pool of connections
     * @return resource connection.
     */
    public abstract AutoCloseable getResource();
}
