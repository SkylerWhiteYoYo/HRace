package org.horserace.hrace.NPC;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NPCRemoveManager {
    // 제거할 NPC 이름 목록을 정의합니다.
    private List<String> npcNamesToRemove = Arrays.asList(
            "우마무스메", "쿵푸마니아", "돼지뇨속", "고냥이", "그저GOAT"
    );


    public void removeNPCsByName(String name) {
        for (NPC npc : CitizensAPI.getNPCRegistry()) {
            if (npc.getName().equalsIgnoreCase(name)) {
                npc.despawn(); // NPC를 비활성화
                CitizensAPI.getNPCRegistry().deregister(npc); // NPC를 등록 취소
                break; // 일치하는 첫 번째 NPC를 찾았으므로 반복 중단
            }
        }
    }
}