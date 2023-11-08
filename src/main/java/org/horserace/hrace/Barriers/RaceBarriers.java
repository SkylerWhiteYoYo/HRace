package org.horserace.hrace.Barriers;

import org.bukkit.Bukkit;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.horserace.hrace.HRace;

import java.util.Random;

public class RaceBarriers {
    private final Random random = new Random();
    private HRace plugin; // HRace 플러그인 인스턴스를 저장할 변수
    private World world; // World 객체를 저장할 변수

    // RaceBarriers 클래스의 생성자입니다.
    public RaceBarriers(HRace plugin) {
        this.plugin = plugin; // HRace 인스턴스를 저장
        this.world = Bukkit.getWorld(plugin.getWorldName()); // HRace 클래스에서 설정한 worldName을 사용하여 World 객체를 가져옵니다.

        if (this.world == null) {
            throw new IllegalStateException("The world '" + plugin.getWorldName() + "' could not be found!");
        }
    }


    public void removeBarriers(int startX, int y, int z, int length) {
        for (int x = startX; x < startX + length; x++) {
            Block block = world.getBlockAt(x, y, z);
            if (block.getType() == Material.OAK_FENCE) {
                block.setType(Material.AIR);
            }
        }
    }//울타리 제거
    public void removeAllBarriers() {
        // 모든 구역의 울타리 제거를 위한 로직 구현
        // 예를 들어, 모든 울타리 구역의 시작 Z 좌표와 길이에 대한 정보를 사용하여 반복문을 돌면서 모두 제거
        int[][] allBarrierInfo = {
                // 각 구역의 시작 Z 좌표와 길이
                {-179, 25}, // 구역 1: 13개의 구멍
                {-172, 25}, // 구역 2: 10개의 구멍
                {-165, 25},  // 구역 3: 7개의 구멍
                {-159, 25},  // 구역 4: 6개의 구멍
                {-151, 25}   // 구역 5: 3개의 구멍
                // 나머지 구역들의 정보도 이런 식으로 추가...
        };

        int x = 165; // 예시 X 좌표
        int y = 64;  // 예시 높이 좌표

        for (int[] info : allBarrierInfo) {
            int startZ = info[0];
            int length = info[1];
            removeBarriers(x, y, startZ, length);
        }
    } // 모든 구역 울타리 제거(removeBarriers참조)

    public void createBarrierSection(int xStart, int y, int z, int length) {
        for (int x = xStart; x < xStart + length; x++) {
            Block block = world.getBlockAt(x, y, z);
            block.setType(Material.OAK_FENCE);
        }
    } //울타리 설치

    public void createHolesInBarrier(int startX, int y, int z, int length, int holes) {
        boolean[] holePositions = new boolean[length];
        // 구멍 위치를 랜덤하게 정합니다.
        for (int i = 0; i < holes; i++) {
            int position;
            do {
                position = random.nextInt(length);
            } while (holePositions[position]);
            holePositions[position] = true;
        }
        // 구멍 위치에 해당하는 울타리를 제거합니다.
        for (int x = startX; x < startX + length; x++) {
            if (holePositions[x - startX]) {
                Block block = world.getBlockAt(x, y, z);
                block.setType(Material.AIR);
            }
        }
    }
    //구역별 울타리에 구멍 생성
    public void createAllBarriers() {
        // 구역 0은 구멍 없이 생성
        int x0 = 165; // 예시 X 좌표
        int y0 = 64; // 예시 높이 좌표
        int startZ0 = -185; // 구역 0의 시작 Z 좌표
        int length0 = 25; // 구역 0의 울타리 길이

        createBarrierSection(x0, y0, startZ0, length0);

        // 나머지 구역에 대한 정보 (시작 Z 좌표와 구멍의 수)
        int[][] barrierInfo = {
                {-179, 13}, // 구역 1: 13개의 구멍
                {-172, 10}, // 구역 2: 10개의 구멍
                {-165, 8},  // 구역 3: 7개의 구멍
                {-159, 7},  // 구역 4: 6개의 구멍
                {-151, 6}   // 구역 5: 3개의 구멍
        };

        int x = 165; // 예시 X 좌표
        int y = 64; // 예시 높이 좌표
        int length = 25; // 울타리 길이

        for (int[] info : barrierInfo) {
            int startZ = info[0];
            int holes = info[1];

            // 울타리 생성
            createBarrierSection(x, y, startZ, length);
            // 구멍 생성
            createHolesInBarrier(x, y, startZ, length, holes);
        }
    }// 모든 구역 구멍 뚫린 울타리 생성





}
