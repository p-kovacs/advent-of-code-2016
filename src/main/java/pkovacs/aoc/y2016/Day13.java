package pkovacs.aoc.y2016;

import pkovacs.aoc.util.Bfs;
import pkovacs.aoc.util.InputUtils;
import pkovacs.aoc.util.Tile;

public class Day13 {

    private static final int MAP_SIZE = 52;

    public static void main(String[] args) {
        long shift = InputUtils.readLongs("y2016/day13.txt")[0];

        var start = new Tile(1, 1);
        var target = new Tile(39, 31);

        var results = Bfs.run(start,
                tile -> tile.validNeighbors(MAP_SIZE, MAP_SIZE, n -> isNotWall(n, shift)));

        System.out.println("Part 1: " + results.get(target).getDist());
        System.out.println("Part 2: " + results.values().stream().filter(res -> res.getDist() <= 50).count());
    }

    private static boolean isNotWall(Tile tile, long shift) {
        return (Long.bitCount(getMagicNumber(tile.col(), tile.row(), shift)) & 1) == 0;
    }

    private static long getMagicNumber(long x, long y, long shift) {
        return x * (x + 3) + y * (x + x + y + 1) + shift;
    }

}
