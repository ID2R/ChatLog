package dev.id2r.chatlog.spigot;

import dev.id2r.chatlog.common.dependency.MavenDependency;
import dev.id2r.chatlog.common.plugin.PlatformPlugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

@MavenDependency(groupId = "com.zaxxer", artifactId = "HikariCP", version = "4.0.3")
public class CLSpigotPlugin extends PlatformPlugin<JavaPlugin> {

    public CLSpigotPlugin(JavaPlugin loader) {
        super(loader);
    }

    @Override
    public Logger getLogger() {
        return getLoader().getLogger();
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}