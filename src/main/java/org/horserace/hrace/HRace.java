package org.horserace.hrace;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class HRace extends JavaPlugin {
    private Economy economy = null;
    private boolean initializeVault() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return economy != null;
    }
    public Economy getEconomy() {
        return economy;
    }
    @Override
    public void onEnable() {
        if (!initializeVault()) {
            getLogger().severe("Vault dependency not found! Disabling the plugin.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // Vault successfully initialized. Continue with enabling the plugin...
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
