package org.horserace.hrace;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class RaceCommandExecutor implements CommandExecutor {

    private final HRace plugin;

    public RaceCommandExecutor(HRace plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("사용법: /경마 시작");
            return true;
        }

        if (args[0].equalsIgnoreCase("시작")) {
            plugin.startRace(); // HRace 인스턴스를 통해 경마 시작 메서드 호출
        } else if (args[0].equalsIgnoreCase("종료")) {
            plugin.endRace(); // HRace 인스턴스를 통해 경마 종료 메서드 호출
        } else {
            sender.sendMessage("알 수 없는 명령어입니다.");
        }
        return true;
    }

}
