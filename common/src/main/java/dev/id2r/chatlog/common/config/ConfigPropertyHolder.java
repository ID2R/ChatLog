package dev.id2r.chatlog.common.config;

import ch.jalu.configme.SettingsHolder;
import ch.jalu.configme.properties.*;
import dev.id2r.chatlog.common.connection.database.DatabaseType;
import lombok.Getter;

/**
 * @author AkramL
 * @since 1.0-BETA
 */
@Getter
public class ConfigPropertyHolder implements SettingsHolder {

    public static final Property<Boolean> DATABASE_ENABLED = new BooleanProperty("database.enabled", false);
    public static final Property<DatabaseType> DATABASE_TYPE = new EnumProperty<>(DatabaseType.class, "database.type", DatabaseType.MYSQL);
    public static final Property<String> DATABASE_HOST = new StringProperty("database.host", "localhost");
    public static final Property<Integer> DATABASE_PORT = new IntegerProperty("database.port", 3306);
    public static final Property<String> DATABASE_DB = new StringProperty("database.database", "chatlog");
    public static final Property<String> DATABASE_PREFIX = new StringProperty("database.table-prefix", "chatlog_");
    public static final Property<String> DATABASE_USER = new StringProperty("database.authentication.username", "dbuser");
    public static final Property<String> DATABASE_PASS = new StringProperty("database.authentication.password", "cheese");

    public static final Property<String> SERVER_NAME = new StringProperty("options.server-name", "Default");

}
