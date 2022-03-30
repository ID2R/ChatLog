package dev.id2r.chatlog.common.connection.database;

import dev.id2r.chatlog.common.user.LoggedMessage;
import dev.id2r.chatlog.common.user.User;

import java.util.List;
import java.util.UUID;

/**
 * @author Akram Louze
 * @since 1.0-BETA
 */
public interface Database {
    /**
     * It will load all messages of a player.
     *
     * @param user returns to the object of user
     * @return player's logged messages
     */
    List<LoggedMessage> getMessages(User user);
    void addMessage(UUID uuid, LoggedMessage message);
    void removeMessages(User user);
    void removeMessages(User user, long before);
}
