package org.horserace.hrace;

import net.citizensnpcs.api.CitizensAPI;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.horserace.hrace.Barriers.RaceBarriers;
import org.horserace.hrace.NPC.HorseNPCManager;
import org.horserace.hrace.NPC.NPCArrival;
import org.horserace.hrace.NPC.NPCRemoveManager;
import org.horserace.hrace.NPC.NPCSpeedManager;
import org.horserace.hrace.Ticket.HorseTicket;
import org.horserace.hrace.Ticket.HorseTicketCommand;
import org.horserace.hrace.Ticket.HorseTicketPurchaseCommand;
import org.horserace.hrace.Ticket.HorseTicketWinCommand;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import net.citizensnpcs.api.npc.NPCRegistry;


public final class HRace extends JavaPlugin {
    private NPCRemoveManager npcRemoveManager;
    private HorseNPCManager horseNPCManager;
    private NPCArrival NPCarrival;
    private NPCSpeedManager npcSpeedManager;
    private NPCRegistry npcRegistry;
    private RaceBarriers raceBarriers;
    String worldName = "world";
    World world = Bukkit.getWorld(worldName);



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

    // 경마 시작을 위한 메서드
    public void startRace() {
        if (raceInProgress) {
            getLogger().warning("경마가 이미 진행 중입니다!");
            return;
        }
        raceInProgress = true;
        // 여기에 경마를 시작하기 위한 다른 코드 추가
        npcRemoveManager.removeNPCsByName("우마무스메");
        npcRemoveManager.removeNPCsByName("쿵푸마니아");
        npcRemoveManager.removeNPCsByName("돼지뇨속");
        npcRemoveManager.removeNPCsByName("고냥이");
        npcRemoveManager.removeNPCsByName("그저GOAT");
        raceBarriers.createAllBarriers();
        horseNPCManager.spawnRaceHorses();
        npcSpeedManager.setNPCSpeeds();
        horseNPCManager.setHorseDestination("우마무스메",0);
        horseNPCManager.setHorseDestination("쿵푸마니아",1);
        horseNPCManager.setHorseDestination("돼지뇨속",2);
        horseNPCManager.setHorseDestination("고냥이",3);
        horseNPCManager.setHorseDestination("그저GOAT",4);
        world.playSound(new Location(world, 177, 68, -187), Sound.ITEM_GOAT_HORN_SOUND_0, 3.0f, 1.0f);

        // 경마 시작 메시지
        Bukkit.broadcastMessage("§e[HRace] §6경마가 시작되었습니다!");

        NPCarrival.resetWinnerAnnouncement();
        new RaceTracker(this, NPCarrival).runTaskTimer(this, 0L, 5L);


        getLogger().info("경마가 시작되었습니다!");
    }

    // 경마 종료를 위한 메서드
    public void endRace() {
        if (!raceInProgress) {
            getLogger().warning("경마가 진행 중이지 않습니다!");
            return;
        }
        raceInProgress = false;
        // 여기에 경마를 종료하기 위한 다른 코드 추가
        Location location = new Location(world, 179, 65, -141); // 폭죽을 터뜨릴 위치
        new FireworkLauncher(this, world, location).runTask(this);
        npcRemoveManager.removeNPCsByName("우마무스메");
        npcRemoveManager.removeNPCsByName("쿵푸마니아");
        npcRemoveManager.removeNPCsByName("돼지뇨속");
        npcRemoveManager.removeNPCsByName("고냥이");
        npcRemoveManager.removeNPCsByName("그저GOAT");
        raceBarriers.removeAllBarriers();
        world.playSound(new Location(world, 177, 68, -187), Sound.ITEM_GOAT_HORN_SOUND_1, 3.0f, 1.0f);
        getLogger().info("경마가 종료되었습니다!");
    }

    // 경마 진행 상태 확인 메서드
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
        this.npcRemoveManager = new NPCRemoveManager(); // NPCRemoveManager 초기화
        //this.horseNPCManager = new HorseNPCManager(this, npcRegistry);
        this.raceBarriers = new RaceBarriers(this);// 이게 있어야 울타리 불러오기 가능
        this.horseNPCManager = new HorseNPCManager(this, CitizensAPI.getNPCRegistry());
        this.npcSpeedManager = new NPCSpeedManager();
        this.NPCarrival=new NPCArrival(this);
        new TimeTracker(this).runTaskTimer(this, 0, 1L);
        //this.npcRegistry = CitizensAPI.getNPCRegistry();
        getLogger().info("플러그인 활성화됨 (HRACE)");

        if (!initializeVault()) {
            getLogger().severe("Vault dependency not found! Disabling the plugin.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        // Vault successfully initialized. Continue with enabling the plugin...
        getCommand("마권").setExecutor(new HorseTicketCommand(this));
        getCommand("마권구매").setExecutor(new HorseTicketPurchaseCommand(this));
        this.getCommand("마권당첨").setExecutor(new HorseTicketWinCommand(this));
        this.getCommand("경마").setExecutor(new RaceCommandExecutor(this));


    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
