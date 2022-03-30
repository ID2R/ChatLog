package dev.id2r.chatlog.common.connection.database.sql;

import dev.id2r.chatlog.common.connection.database.Database;
import dev.id2r.chatlog.common.user.LoggedMessage;
import dev.id2r.chatlog.common.user.User;
import lombok.Data;

import java.util.List;
import java.util.UUID;

/**
 * @author Akram Louze
 * @since 1.0-BETA
 */
@Data
public class SQLDatabase implements Database {
    private final SQLConnection connection;
    @Override
    public List<LoggedMessage> getMessages(User user) {
        return null;
    }

    @Override
    public void addMessage(UUID uuid, LoggedMessage message) {

    }

    @Override
    public void removeMessages(User user) {

    }

    @Override
    public void removeMessages(User user, long before) {

    }
}
