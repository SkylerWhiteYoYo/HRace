package org.horserace.hrace.Ticket;

import java.util.UUID;

public class HorseTicket {

    private final UUID player;
    private final int horseNumber;
    private final double amount;

    public HorseTicket(UUID player, int horseNumber, double amount) {
        this.player = player;
        this.horseNumber = horseNumber;
        this.amount = amount;
    }
    // 플레이어의 UUID를 반환하는 메서드
    public UUID getPlayer() {
        return player;
    }

    // 말 번호를 반환하는 메서드
    public int getHorseNumber() {
        return horseNumber;
    }

    // 건 돈의 액수를 반환하는 메서드
    public double getAmount() {
        return amount;
    }

    // 여기에 필요한 다른 메서드나 로직을 추가할 수 있습니다.
}
