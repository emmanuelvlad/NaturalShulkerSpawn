package me.evlad.naturalshulkerspawn;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {
    private final NaturalShulkerSpawn plugin;

    public ReloadCommand(NaturalShulkerSpawn naturalShulkerSpawn) {
        this.plugin = naturalShulkerSpawn;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("naturalshulkerspawn.*")) {
            plugin.reloadConfig();
            sender.sendMessage("Natural Shulker Spawn: §areloaded");
            return true;
        }
        return false;
    }
}
