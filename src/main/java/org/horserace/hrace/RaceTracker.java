package org.horserace.hrace;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.scheduler.BukkitRunnable;
import org.horserace.hrace.NPC.NPCArrival;

public class RaceTracker extends BukkitRunnable {

    private final HRace plugin;
    private final NPCArrival npcArrival;

    public RaceTracker(HRace plugin, NPCArrival npcArrival) {
        this.plugin = plugin;
        this.npcArrival = npcArrival;
    }

    @Override
    public void run() {
        // 경주가 진행 중이 아니면 추적을 멈춥니다.
        if (!plugin.isRaceInProgress()) {
            this.cancel();
            return;
        }

        // 경주가 진행 중일 때, 모든 NPC에 대해 checkIfWinner 메소드를 호출합니다.
        for (NPC npc : CitizensAPI.getNPCRegistry()) {
            npcArrival.checkIfWinner(npc);
        }
    }
}