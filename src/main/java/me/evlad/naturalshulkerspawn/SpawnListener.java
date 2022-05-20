package me.evlad.naturalshulkerspawn;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class SpawnListener implements Listener {
    private final NaturalShulkerSpawn plugin;

    public SpawnListener(NaturalShulkerSpawn plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onSpawn(CreatureSpawnEvent event) {
        if (event.getEntity() instanceof Enderman && event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.NATURAL) {
            FileConfiguration config = plugin.getConfig();
            Location spawnLocation = event.getLocation();
            World spawnLocationWorld = spawnLocation.getWorld();
            if (spawnLocationWorld.getEnvironment() != World.Environment.THE_END
                    || new Random().nextDouble() > config.getDouble("spawning-chance") / 100D)
                return;

            List<Material> spawnableSurfaces = config.getStringList("spawnable-surfaces")
                    .stream()
                    .map(Material::valueOf)
                    .collect(Collectors.toList());
            List<Material> spawnableSurfacesBlacklist = config.getStringList("spawnable-surfaces-blacklist")
                    .stream()
                    .map(Material::valueOf)
                    .collect(Collectors.toList());
            Block spawnedOnBlock = spawnLocationWorld.getBlockAt(spawnLocation.getBlockX(), spawnLocation.getBlockY() - 1, spawnLocation.getBlockZ());
            Material spawnedOnMaterial = spawnedOnBlock.getBlockData().getMaterial();
            if ((!spawnableSurfaces.contains(spawnedOnMaterial) && spawnableSurfaces.size() > 0) || spawnableSurfacesBlacklist.contains(spawnedOnMaterial))
                return;

            event.setCancelled(true);
            spawnLocationWorld.spawnEntity(spawnLocation, EntityType.SHULKER);
            if (config.getBoolean("break-surface-on-spawn")) {
                spawnLocationWorld.setBlockData(spawnedOnBlock.getLocation(), Material.AIR.createBlockData());
            }
        }
    }
}
