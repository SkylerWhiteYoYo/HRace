package org.horserace.hrace.NPC;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.CurrentLocation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.horserace.hrace.HRace;
import org.horserace.hrace.WinnerLogger;

public class NPCArrival {

    // 경주 정보를 관리하는 Race 클래스의 참조를 저장합니다.
    private HRace plugin;
    private WinnerLogger winnerLogger;
    private boolean winnerAnnounced = false;
    private int lastWinnerNumber = 0;

    // NPCArrival 클래스의 생성자입니다.
    public NPCArrival(HRace plugin) {
        this.plugin = plugin;
        this.winnerLogger = new WinnerLogger(plugin);
    }

    // NPC의 현재 위치를 검사하여 우승지점에 도착했는지 확인하는 메소드입니다.
    public void checkIfWinner(NPC npc) {
        if  (!plugin.isRaceInProgress() || winnerAnnounced || !npc.hasTrait(CurrentLocation.class)) {
            // 경주가 진행 중이 아니거나 CurrentLocation 트레잇이 없으면 + 승자발표를 했으면 메소드를 종료합니다.
            return;
        }

        // CurrentLocation 트레잇을 이용하여 NPC의 현재 위치를 가져옵니다.
        Location npcLocation = npc.getTrait(CurrentLocation.class).getLocation();

        // Z 좌표가 -147 이상인지 확인하여 우승지점에 도착했는지 판단합니다.
        if (npcLocation.getZ() >= -147) {
            String npcName = npc.getName();
            String winnerMessage = "";

            // NPC의 이름을 확인하여 말번호와 함께 방송합니다.
            switch (npcName) {
                case "우마무스메":
                    lastWinnerNumber = 1;
                    break;
                case "쿵푸마니아":
                    lastWinnerNumber = 2;
                    break;
                case "돼지뇨속":
                    lastWinnerNumber = 3;
                    break;
                case "고냥이":
                    lastWinnerNumber = 4;
                    break;
                case "그저GOAT":
                    lastWinnerNumber = 5;
                    break;
                default:
                    // NPC 이름이 목록에 없으면 우승자로 인정하지 않습니다.
                    return;
            }


            // 우승자를 방송하는 메소드입니다.
            winnerMessage = "§e[HRace] §6우승자는 " + lastWinnerNumber + "번, " + npcName + "입니다!";
            Bukkit.getServer().broadcastMessage(winnerMessage);
            winnerLogger.logWinner(npcName + " - " + lastWinnerNumber);
            // Bukkit의 dispatchCommand를 사용하여 마권당첨 명령어 실행
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "마권당첨 " + lastWinnerNumber);
            winnerAnnounced = true;
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"경마 종료");
        }
    }
    // 우승자가 이미 발표되었는지 확인하는 메소드
    public boolean hasWinnerBeenAnnounced() {
        return winnerAnnounced;
    }

    // 우승자 발표를 초기화하는 메소드 (새 경기를 시작할 때 사용될 수 있음)
    public void resetWinnerAnnouncement() {
        winnerAnnounced = false;
    }
    // '/마권당첨 <말번호>' 명령어 처리 메소드
}

