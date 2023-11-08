package org.horserace.hrace.Ticket;

import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.horserace.hrace.HRace;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class HorseTicketWinCommand implements CommandExecutor {

    private final HRace plugin;
    private final HashMap<Integer, Double> oddsMap;

    public HorseTicketWinCommand(HRace plugin) {
        this.plugin = plugin;
        // 여기에서 배당률을 초기화합니다.
        oddsMap = new HashMap<>();
        oddsMap.put(1, 2.0); // 1번 말의 배당률
        oddsMap.put(2, 2.0); // 2번 말의 배당률
        oddsMap.put(3, 2.0); // 3번 말의 배당률
        oddsMap.put(4, 2.0);
        oddsMap.put(5, 2.0);
        // 여기에 다른 말들의 배당률을 계속 추가할 수 있습니다.
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("horserace.win")) {
            sender.sendMessage("이 명령어를 사용할 권한이 없습니다.");
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage("사용법: /마권당첨 <말번호>");
            return true;
        }

        int winningHorseNumber;
        try {
            winningHorseNumber = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            sender.sendMessage("말 번호는 숫자여야 합니다.");
            return true;
        }

        Double odds = oddsMap.getOrDefault(winningHorseNumber, 2.0); // 기본 배당률은 2로 설정

        // 디버그 메시지를 추가하여 콘솔에 배당률을 출력합니다.
        plugin.getLogger().info("Winning horse number: " + winningHorseNumber);
        plugin.getLogger().info("Odds for winning horse: " + odds);

        for (Map.Entry<UUID, List<HorseTicket>> entry : plugin.getTickets().entrySet()) {
            List<HorseTicket> playerTickets = entry.getValue();
            for (HorseTicket ticket : playerTickets) {
                if (ticket.getHorseNumber() == winningHorseNumber) {
                    Player winner = Bukkit.getPlayer(entry.getKey());
                    if (winner != null && winner.isOnline()) {
                        double payout = ticket.getAmount() * odds;
                        EconomyResponse r = plugin.getEconomy().depositPlayer(Bukkit.getOfflinePlayer(entry.getKey()), payout);
                        if (r.transactionSuccess()) {
                            winner.sendMessage(String.format("축하합니다! 당신은 %s을(를) 당첨되었습니다!", plugin.getEconomy().format(payout)));
                        } else {
                            sender.sendMessage("상금을 지급하는 데 문제가 발생했습니다: " + r.errorMessage);
                        }
                    }
                }
            }
        }

        plugin.getTickets().clear();
        sender.sendMessage("모든 당첨 처리가 완료되었습니다.");

        return true;
    }
}

