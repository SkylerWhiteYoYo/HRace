package org.horserace.hrace.Ticket;

import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.horserace.hrace.HRace;

import java.util.ArrayList;
import java.util.List;

public class HorseTicketPurchaseCommand implements CommandExecutor {
    private final HRace plugin;

    public HorseTicketPurchaseCommand(HRace plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("이 명령어는 플레이어만 사용할 수 있습니다.");
            return true;
        }

        Player player = (Player) sender; // 중복된 선언을 제거했습니다.

        // 경마가 진행 중이거나 마권 구매 시간이 아닌 경우
        if (plugin.isRaceInProgress() || !plugin.canPurchaseTickets()) {
            player.sendMessage("현재 마권 구매가 불가능합니다.");
            return true;
        }

        if (args.length != 2) {
            player.sendMessage("사용법: /마권구매 <말번호> <구매할 금액>");
            return true;
        }
        int horseNumber;
        double amount;

        try {
            horseNumber = Integer.parseInt(args[0]);
            amount = Double.parseDouble(args[1]);
        } catch (NumberFormatException e) {
            player.sendMessage("말 번호와 금액은 숫자여야 합니다.");
            return true;
        }

        if (amount <= 0) {
            player.sendMessage("금액은 0보다 커야 합니다.");
            return true;
        }

        // Vault 경제 API를 사용하여 플레이어의 돈을 확인하고 차감합니다.
        if (plugin.getEconomy().getBalance(player) < amount) {
            player.sendMessage("돈이 부족합니다.");
            return true;
        }

        EconomyResponse response = plugin.getEconomy().withdrawPlayer(player, amount);
        if (!response.transactionSuccess()) {
            player.sendMessage("돈을 차감하는 데 실패했습니다: " + response.errorMessage);
            return true;
        }

        // 마권 구매 성공, 티켓 저장 로직
        List<HorseTicket> tickets = plugin.getTickets().getOrDefault(player.getUniqueId(), new ArrayList<>());
        tickets.add(new HorseTicket(player.getUniqueId(), horseNumber, amount));
        plugin.getTickets().put(player.getUniqueId(), tickets);

        player.sendMessage(String.format("마권을 구매했습니다. 말 번호: %d, 금액: %.2f", horseNumber, amount));
        return true;



    }
}