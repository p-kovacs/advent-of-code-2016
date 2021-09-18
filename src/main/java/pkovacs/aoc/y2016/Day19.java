package pkovacs.aoc.y2016;

import pkovacs.aoc.util.InputUtils;

public class Day19 {

    public static void main(String[] args) {
        int playerCount = Integer.parseInt(InputUtils.readLine("y2016/day19.txt"));

        System.out.println("Part 1: " + play(playerCount, false));
        System.out.println("Part 2: " + play(playerCount, true));
    }

    public static int play(int playerCount, boolean advanced) {
        // Build a double-linked list of the player IDs
        int[] next = new int[playerCount];
        int[] prev = new int[playerCount];
        for (int i = 0; i < next.length - 1; i++) {
            next[i] = i + 1;
            prev[i + 1] = i;
        }
        next[next.length - 1] = 0;
        prev[0] = next.length - 1;

        // Play until only one player is left
        int i = advanced ? playerCount / 2 : 1; // denotes the next player to steal from
        for (int playingCount = playerCount; playingCount > 1; playingCount--) {
            next[prev[i]] = next[i];
            prev[next[i]] = prev[i];
            i = advanced && (playingCount % 2 == 0) ? next[i] : next[next[i]];
        }

        return i + 1;
    }

}
