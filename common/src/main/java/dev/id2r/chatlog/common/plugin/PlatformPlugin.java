package dev.id2r.chatlog.common.plugin;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class PlatformPlugin<L> {

    @Getter private final L loader;

    public abstract void onEnable();
    public abstract void onDisable();

}