package org.horserace.hrace;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.bukkit.plugin.java.JavaPlugin;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WinnerLogger {

    private JavaPlugin plugin;
    private File logFile;

    public WinnerLogger(JavaPlugin plugin) {
        this.plugin = plugin;
        this.logFile = new File(plugin.getDataFolder(), "winners.txt");
        if (!logFile.exists()) {
            try {
                plugin.getDataFolder().mkdirs(); // 플러그인 폴더가 없다면 생성
                logFile.createNewFile(); // winners.txt 파일이 없다면 생성
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void logWinner(String winnerName) {
        // 날짜와 시간 포맷을 생성합니다.
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String timestamp = simpleDateFormat.format(new Date());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true))) {
            writer.write(timestamp + " - " + winnerName);
            writer.newLine(); // 줄바꿈을 추가하여 다음 우승자와 구분
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}