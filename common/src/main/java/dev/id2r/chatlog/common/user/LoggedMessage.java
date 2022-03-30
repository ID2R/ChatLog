package dev.id2r.chatlog.common.user;

import lombok.Data;

/**
 * @author Akram Louze
 * @since 1.0-BETA
 */
@Data
public class LoggedMessage {
    private final int id;
    private String message, server, reportId;
    private User issuer;
    private long timestamp;
}
