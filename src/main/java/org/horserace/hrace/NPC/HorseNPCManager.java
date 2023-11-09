package org.horserace.hrace.NPC;

import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.horserace.hrace.HRace;

public class HorseNPCManager {

    private HRace plugin; // HRace 타입의 plugin 변수 추가
    private NPCRegistry npcRegistry; // NPC 레지스트리 참조
    // 월드 이름을 String 형태로 가져옵니다.

    public HorseNPCManager(HRace plugin, NPCRegistry npcRegistry) {
        this.plugin = plugin;
        this.npcRegistry = npcRegistry;
    }

    public void spawnRaceHorses() {
        String worldName = plugin.getWorldName(); // 월드 이름을 가져옵니다.
        World world = Bukkit.getWorld(worldName); // 월드 객체를 가져옵니다.
        // HRace 클래스의 getWorldName 메서드를 사용하여 월드 이름을 가져옵니다.
        if (world == null) {
            // 월드가 없으면 경고를 로그에 출력하고 함수 실행을 중단합니다.
            plugin.getLogger().warning(worldName + " 월드를 찾을 수 없습니다!");
            return;
        }
        Location spawnLocation = new Location(world, 167, 64, -188);
        EntityType[] types = {EntityType.HORSE, EntityType.PANDA, EntityType.PIG, EntityType.CAT, EntityType.GOAT};
        String[] names = {"우마무스메", "쿵푸마니아", "돼지뇨속", "고냥이", "그저GOAT"};

        for (int i = 0; i < types.length; i++) {
            NPC npc = npcRegistry.createNPC(types[i], names[i]);
            npc.spawn(spawnLocation);
            spawnLocation.add(5, 0, 0);
            //spawnLocation = spawnLocation.add(5, 0, 0); // 다음 NPC 소환 위치를 X 좌표에 5 더함
        }
    }

    // 이 메소드는 각 NPC의 목적지를 설정합니다.
    public void setHorseDestination(String npcName, int index) {
        // 월드 객체를 가져옵니다.
        World world = Bukkit.getWorld(plugin.getWorldName());
        if (world == null) {
            plugin.getLogger().warning(plugin.getWorldName() + " 월드를 찾을 수 없습니다!");
            return;
        }
        // Citizens 플러그인의 NPCRegistry에서 NPC를 찾습니다.
        NPC npc = null;
        for (NPC searchNpc : npcRegistry) {
            if (searchNpc.getName().equalsIgnoreCase(npcName)) {
                npc = searchNpc;
                break;
            }
        }

        if (npc == null) {
            plugin.getLogger().warning(npcName + " NPC를 찾을 수 없습니다!");
            return;
        }

        final NPC finalNpc = npc;
        // Bukkit 스케줄러를 사용하여 1초 후에 NPC의 목적지를 설정합니다.
        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
                finalNpc.getNavigator().setTarget(new Location(world, 177 + (index * 5), 64, -142));
            }
        }, 20L); // 20 ticks = 1 second
    }
}