package org.horserace.hrace.NPC;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
public class NPCSpeedManager {
    // 필요한 속도 범위에 대한 변수를 정의합니다.
    private float minSpeedPiggy = 0.9f;
    private float maxSpeedPiggy = 1.2f;
    private float minSpeedCat = 0.55f;
    private float maxSpeedCat = 1.05f;
    private float minSpeedGoat = 0.6f;
    private float maxSpeedGoat = 1.85f;
    private float minSpeedHorse = 2.5f;
    private float maxSpeedHorse = 3.0f;
    private float minSpeedPanda = 2.0f;
    private float maxSpeedPanda = 2.5f;

    // 각 NPC의 이름에 따라 속도를 설정합니다.
    public void setNPCSpeeds() {
        setNPCSpeed("우마무스메", getRandomSpeed(minSpeedHorse, maxSpeedHorse));
        setNPCSpeed("쿵푸마니아", getRandomSpeed(minSpeedPanda, maxSpeedPanda));
        setNPCSpeed("돼지뇨속", getRandomSpeed(minSpeedPiggy, maxSpeedPiggy));
        setNPCSpeed("고냥이", getRandomSpeed(minSpeedCat, maxSpeedCat));
        setNPCSpeed("그저GOAT", getRandomSpeed(minSpeedGoat, maxSpeedGoat));
    }
    // NPC의 이름과 속도를 받아 해당 NPC의 속도를 설정하는 메서드입니다.
    public void setNPCSpeed(String npcName, float speed) {
        NPCRegistry registry = CitizensAPI.getNPCRegistry();
        for (NPC npc : registry) {
            if (npc.getName().equalsIgnoreCase(npcName)) {
                // NPC의 이동 속도를 설정합니다.
                npc.getNavigator().getDefaultParameters().baseSpeed(speed);
                break; // 이름이 일치하는 첫 번째 NPC를 찾으면 루프를 중단합니다.
            }
        }
    }
    // 최소 속도와 최대 속도를 입력받아 그 범위 내에서 무작위 속도를 생성하는 메서드입니다.
    private float getRandomSpeed(float minSpeed, float maxSpeed) {
        return minSpeed + (float)Math.random() * (maxSpeed - minSpeed);
    }
}





