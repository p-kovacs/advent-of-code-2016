package pkovacs.aoc.y2016;

import java.util.HashSet;
import java.util.stream.Collectors;

import pkovacs.aoc.alg.Bfs;
import pkovacs.aoc.util.InputUtils;
import pkovacs.aoc.util.Tile;

public class Day13 {

    public static void main(String[] args) {
        long shift = InputUtils.readLongs("y2016/day13.txt")[0];

        var start = new Tile(1, 1);
        var target = new Tile(39, 31);

        System.out.println("Part 1: " + getDistance(start, target, shift));
        System.out.println("Part 2: " + countReachableTiles(start, 50, shift));
    }

    private static long getDistance(Tile start, Tile target, long shift) {
        var result = Bfs.run(start,
                tile -> tile.getFourNeighbors().stream()
                        .filter(n -> !isWall(n.col(), n.row(), shift))
                        .collect(Collectors.toList()),
                target::equals);
        return result.get().getDist();
    }

    private static long countReachableTiles(Tile start, int maxDist, long shift) {
        record State(Tile tile, int dist) {
        }

        var set = new HashSet<Tile>();
        Bfs.run(new State(start, 0),
                state -> {
                    if (state.dist() <= maxDist) {
                        set.add(state.tile());
                    }
                    return state.tile().getFourNeighbors().stream()
                            .filter(n -> !isWall(n.col(), n.row(), shift))
                            .map(n -> new State(n, state.dist() + 1))
                            .collect(Collectors.toList());
                },
                state -> state.dist() == maxDist + 2);

        return set.size();
    }

    private static boolean isWall(long x, long y, long shift) {
        if (x < 0 || y < 0) {
            return true;
        }
        long a = x * (x + 3) + y * (x + x + y + 1) + shift;
        return (Long.bitCount(a) & 1) == 1;
    }

}
