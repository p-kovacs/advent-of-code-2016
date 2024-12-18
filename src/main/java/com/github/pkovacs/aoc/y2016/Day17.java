package com.github.pkovacs.aoc.y2016;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.google.common.hash.Hashing;
import com.github.pkovacs.util.Bfs;
import com.github.pkovacs.util.InputUtils;
import com.github.pkovacs.util.PathResult;
import com.github.pkovacs.util.Tile;

public class Day17 {

    private static record State(Tile tile, String path) {}

    private static final char[] DIRS = { 'U', 'D', 'L', 'R' };

    private static final int SIZE = 4;
    private static final Tile START_TILE = new Tile(0, 0);
    private static final Tile TARGET_TILE = new Tile(3, 3);

    public static void main(String[] args) {
        String passCode = InputUtils.readLines("day17.txt").get(0);

        var resultMap = Bfs.run(new State(START_TILE, ""),
                st -> getNextFeasibleStates(st, passCode));

        String shortestPath = resultMap.values().stream()
                .filter(res -> res.getNode().tile().equals(TARGET_TILE))
                .min(Comparator.comparing(PathResult::getDist))
                .orElseThrow()
                .getNode().path();

        long lengthOfLongestPath = resultMap.values().stream()
                .filter(res -> res.getNode().tile().equals(TARGET_TILE))
                .mapToLong(PathResult::getDist)
                .max().orElseThrow();

        System.out.println("Part 1: " + shortestPath);
        System.out.println("Part 2: " + lengthOfLongestPath);
    }

    private static List<State> getNextFeasibleStates(State state, String passCode) {
        if (state.tile().equals(TARGET_TILE)) {
            return List.of();
        }

        String hash = getMd5Hash(passCode + state.path());

        var result = new ArrayList<State>(4);
        for (int i = 0; i < 4; i++) {
            var neighbor = state.tile().neighbor(DIRS[i]);
            boolean doorIsOpen = neighbor.isValid(SIZE, SIZE)
                    && hash.charAt(i) >= 'b' && hash.charAt(i) <= 'f';
            if (doorIsOpen) {
                result.add(new State(neighbor, state.path + DIRS[i]));
            }
        }

        return result;
    }

    @SuppressWarnings("deprecation")
    private static String getMd5Hash(String s) {
        return Hashing.md5().hashString(s, StandardCharsets.UTF_8).toString();
    }

}
