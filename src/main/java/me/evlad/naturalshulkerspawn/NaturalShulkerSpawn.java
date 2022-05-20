package me.evlad.naturalshulkerspawn;

import org.bukkit.plugin.java.JavaPlugin;

public final class NaturalShulkerSpawn extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new SpawnListener(this), this);
        getCommand("nss").setExecutor(new ReloadCommand(this));
    }

    @Override
    public void onDisable() {
    }
}
