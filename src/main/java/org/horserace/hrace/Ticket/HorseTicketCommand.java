package org.horserace.hrace.Ticket;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.horserace.hrace.HRace;

import java.util.List;

public class HorseTicketCommand implements CommandExecutor {

    private final HRace plugin;


    public HorseTicketCommand(HRace plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            List<HorseTicket> playerTickets = plugin.getTickets().get(player.getUniqueId());
            if (playerTickets != null && !playerTickets.isEmpty()) {
                for (HorseTicket ticket : playerTickets) {
                    player.sendMessage(String.format("말 번호: %d, 금액: %.2f", ticket.getHorseNumber(), ticket.getAmount()));
                }
            } else {
                player.sendMessage("구매한 마권이 없습니다.");
            }
        } else {
            sender.sendMessage("이 명령어는 플레이어만 사용할 수 있습니다.");
        }
        return true;
    }
}

