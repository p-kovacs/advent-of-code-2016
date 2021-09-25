package pkovacs.aoc.y2016;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.google.common.hash.Hashing;
import pkovacs.aoc.alg.Bfs;
import pkovacs.aoc.alg.PathResult;
import pkovacs.aoc.util.InputUtils;
import pkovacs.aoc.util.Tile;

public class Day17 {

    private static record State(Tile tile, String path) {}

    private static final char[] DIRS = { 'U', 'D', 'L', 'R' };

    private static final int SIZE = 4;
    private static final Tile START_TILE = new Tile(0, 0);
    private static final Tile TARGET_TILE = new Tile(3, 3);

    public static void main(String[] args) {
        String passCode = InputUtils.readLines("y2016/day17.txt").get(0);

        var resultMap = Bfs.run(new State(START_TILE, ""),
                st -> getNextFeasibleStates(st, passCode));

        String shortestPath = resultMap.values().stream()
                .filter(res -> res.getNode().tile().equals(TARGET_TILE))
                .min(Comparator.comparing(PathResult::getDist))
                .get().getNode().path();

        long lengthOfLongestPath = resultMap.values().stream()
                .filter(res -> res.getNode().tile().equals(TARGET_TILE))
                .mapToLong(PathResult::getDist)
                .max().getAsLong();

        System.out.println("Part 1: " + shortestPath);
        System.out.println("Part 2: " + lengthOfLongestPath);
    }

    private static List<State> getNextFeasibleStates(State state, String passCode) {
        if (state.tile().equals(TARGET_TILE)) {
            return List.of();
        }

        String hash = getMd5Hash(passCode + state.path());

        var result = new ArrayList<State>(4);
        var neighbors = state.tile().getFourNeighbors();
        for (int i = 0; i < neighbors.size(); i++) {
            // Exploit the order of neighbors U, L, R, D: the i-th nieghbor corresponds to the j-th character of the hash
            int j = switch (i) {
                case 1 -> 2;
                case 2 -> 3;
                case 3 -> 1;
                default -> 0;
            };
            boolean doorIsOpen = neighbors.get(i).isValid(SIZE, SIZE)
                    && hash.charAt(j) >= 'b' && hash.charAt(j) <= 'f';
            if (doorIsOpen) {
                result.add(new State(neighbors.get(i), state.path + DIRS[j]));
            }
        }

        return result;
    }

    @SuppressWarnings({ "deprecated", "UnstableApiUsage" })
    private static String getMd5Hash(String s) {
        return Hashing.md5().hashString(s, StandardCharsets.UTF_8).toString();
    }

}
