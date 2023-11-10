package org.horserace.hrace;


import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class RegionBroadcaster {
    private Plugin plugin;
    private static final String REGION_NAME = "horse_race";

    public RegionBroadcaster(Plugin plugin) {
        this.plugin = plugin;
    }

    public void broadcastToRegion(String message) {
        WorldGuard wg = WorldGuard.getInstance();
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            World world = BukkitAdapter.adapt(player.getWorld());
            RegionManager regionManager = wg.getPlatform().getRegionContainer().get(world);
            if (regionManager == null) continue;

            Location loc = player.getLocation();
            ApplicableRegionSet set = regionManager.getApplicableRegions(BukkitAdapter.asBlockVector(loc));
            if (set.getRegions().stream().anyMatch(region -> region.getId().equalsIgnoreCase(REGION_NAME))) {
                player.sendMessage(message);
            }
        }
    }
}