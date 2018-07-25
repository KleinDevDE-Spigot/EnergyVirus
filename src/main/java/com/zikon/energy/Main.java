package com.zikon.energy;

import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        saveResource("MirrorMinor.a", true);
        MirrorMinor mirrorMinor = new MirrorMinor();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
