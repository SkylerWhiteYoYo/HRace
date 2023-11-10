package org.horserace.hrace;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.horserace.hrace.HRace;
public class TimeTracker extends BukkitRunnable {
    private final HRace plugin;
    private RegionBroadcaster regionBroadcaster;
    private int raceCount = 1;

    public TimeTracker(HRace plugin) {
        this.plugin = plugin;
        this.regionBroadcaster = new RegionBroadcaster(plugin);
    }

    @Override
    public void run() {
        long time = plugin.getServer().getWorld(plugin.getWorldName()).getTime();

/*        // 매 1000 틱마다 경마 시작
        if (time % 1000 == 0) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "경마 시작");
        }
    }*/

       if (time == 2000) { // 08:00 (2000틱)에 경마 시작 알림
           regionBroadcaster.broadcastToRegion("§e[HRace] §6오늘 경마가 09:00부터 18:00까지 매 시간 시작됩니다.");
        }
        else if (time >= 3000 && time <= 12000 && (time % 1000 == 0)) { // 09:00 (3000틱)부터 18:00 (12000틱)까지 매 시간마다
            raceCount++;
           regionBroadcaster.broadcastToRegion("§e[HRace] §6오늘의 " + raceCount + "번째 경기입니다.");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "경마 시작");

            if (time == 12000) { // 18:00 (12000틱)에 마지막 경마 알림
                regionBroadcaster.broadcastToRegion("§e[HRace] §c아쉽지만 마지막 경마입니다. 내일 다시 와주세요!");
                raceCount = 1; // 경마 횟수 초기화
            }
        }
    }
}