package pkovacs.aoc.y2016;

import java.util.ArrayList;
import java.util.List;

import pkovacs.aoc.util.InputUtils;

public class Day15 {

    private record Disc(int posCount, int startPos) {}

    public static void main(String[] args) {
        var lines = InputUtils.readLines("y2016/day15.txt");

        var discs = new ArrayList<Disc>();
        for (String line : lines) {
            var parts = InputUtils.scan(line,
                    "Disc #%d has %d positions; at time=0, it is at position %d.");
            discs.add(new Disc(parts.get(1).asInt(), parts.get(2).asInt()));
        }

        long solution1 = solve(discs);

        discs.add(new Disc(11, 0));
        long solution2 = solve(discs);

        System.out.println("Part 1: " + solution1);
        System.out.println("Part 2: " + solution2);
    }

    /**
     * Applies a simple CRT (Chinese remainder theorem) algorithm to find the appriopriate start time:
     * https://en.wikipedia.org/wiki/Chinese_remainder_theorem#Search_by_sieving
     */
    private static long solve(List<Disc> discs) {
        long startTime = 0;
        long step = 1;
        for (int i = 0; i < discs.size(); i++) {
            // Find the first start time that is appropriate for disc i
            Disc disc = discs.get(i);
            while (((disc.startPos() + startTime + i + 1) % disc.posCount) != 0) {
                startTime += step;
            }
            step *= disc.posCount; // position counts are primes, so they are obviously pairwise co-primes
        }
        return startTime;
    }

}
