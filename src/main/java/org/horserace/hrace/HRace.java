package org.horserace.hrace;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.World;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.horserace.hrace.Ticket.HorseTicket;
import org.horserace.hrace.Ticket.HorseTicketCommand;
import org.horserace.hrace.Ticket.HorseTicketPurchaseCommand;
import org.horserace.hrace.Ticket.HorseTicketWinCommand;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public final class HRace extends JavaPlugin {
    String worldName = "world";
    // 설정된 월드 이름을 가져오는 메서드입니다.
    public String getWorldName() {
        return this.worldName;
    }
    public boolean canPurchaseTickets() {
        World world = getServer().getWorld(worldName);
        if (world == null) {
            getLogger().warning(" 월드를 찾을 수 없습니다!");
            return false; // city2 월드가 없는 경우 마권 구매 불가
        }
        long time = world.getTime(); // city2 월드의 현재 시간을 가져옵니다.
        return time >= 2000 && time <= 12000; // 마권 구매 가능 시간: 08:00 - 18:00 (2000 - 12000 틱)
    }
    private boolean raceInProgress = false;
    public boolean isRaceInProgress() {
        return raceInProgress;
    }
    private final HashMap<UUID, List<HorseTicket>> tickets = new HashMap<>();
    public HashMap<UUID, List<HorseTicket>> getTickets() {
        return tickets;
    }
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
        getCommand("마권").setExecutor(new HorseTicketCommand(this));
        getCommand("마권구매").setExecutor(new HorseTicketPurchaseCommand(this));
        this.getCommand("마권당첨").setExecutor(new HorseTicketWinCommand(this));
        // Vault successfully initialized. Continue with enabling the plugin...
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
