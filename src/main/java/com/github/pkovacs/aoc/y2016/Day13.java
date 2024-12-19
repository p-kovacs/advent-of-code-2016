package com.github.pkovacs.aoc.y2016;

import com.github.pkovacs.util.Bfs;
import com.github.pkovacs.util.InputUtils;
import com.github.pkovacs.util.Point;

public class Day13 {

    public static void main(String[] args) {
        long shift = Long.parseLong(InputUtils.readSingleLine("day13.txt"));

        var start = new Point(1, 1);
        var target = new Point(31, 39);
        var mapSize = 52;

        var results = Bfs.run(start,
                point -> point.validNeighbors(mapSize, mapSize, p -> isNotWall(p, shift)));

        System.out.println("Part 1: " + results.get(target).getDist());
        System.out.println("Part 2: " + results.values().stream().filter(res -> res.getDist() <= 50).count());
    }

    private static boolean isNotWall(Point point, long shift) {
        return (Long.bitCount(getMagicNumber(point.x(), point.y(), shift)) & 1) == 0;
    }

    private static long getMagicNumber(long x, long y, long shift) {
        return x * (x + 3) + y * (x + x + y + 1) + shift;
    }

}
