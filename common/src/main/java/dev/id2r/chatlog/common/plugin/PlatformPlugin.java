package dev.id2r.chatlog.common.plugin;

import dev.id2r.chatlog.common.dependency.LibraryLoader;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.logging.Logger;

/**
 * @author AkramL
 * @param <L> Loader (or Plugin) superclass
 * @since 1.0-BETA
 */
@RequiredArgsConstructor
public abstract class PlatformPlugin<L> {

    @Getter private final L loader;
    @Getter private final LibraryLoader libraryLoader = new LibraryLoader(this);

    public abstract Logger getLogger();

    public void onEnable() {
        libraryLoader.loadDependencies(getClass());
    }

    public void onDisable() {
    }

}