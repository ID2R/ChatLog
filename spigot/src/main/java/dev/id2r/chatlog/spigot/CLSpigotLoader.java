package dev.id2r.chatlog.spigot;

import dev.id2r.chatlog.common.plugin.PlatformPlugin;
import org.bukkit.plugin.java.JavaPlugin;

public class CLSpigotLoader extends JavaPlugin {

    private final PlatformPlugin<JavaPlugin> plugin;

    public CLSpigotLoader() {
        this.plugin = new CLSpigotPlugin(this);
    }

    @Override
    public void onEnable() {
        plugin.onEnable();
    }

    @Override
    public void onDisable() {
        plugin.onDisable();
    }

}
