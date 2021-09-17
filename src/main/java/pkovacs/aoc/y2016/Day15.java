package pkovacs.aoc.y2016;

import java.util.ArrayList;
import java.util.List;

import pkovacs.aoc.util.InputUtils;

public class Day15 {

    private record Disc(int posCount, int startPos) {
    }

    public static void main(String[] args) {
        var lines = InputUtils.readLines("y2016/day15.txt");

        var discs = new ArrayList<Disc>();
        for (String line : lines) {
            var parts = InputUtils.scan(line,
                    "Disc #%d has %d positions; at time=0, it is at position %d.");
            discs.add(new Disc(parts.get(1).asInt(), parts.get(2).asInt()));
        }

        int solution1 = solve(discs);

        discs.add(new Disc(11, 0));
        int solution2 = solve(discs);

        System.out.println("Part 1: " + solution1);
        System.out.println("Part 2: " + solution2);
    }

    private static int solve(List<Disc> discs) {
        for (int start = 0, step = 1, i = 0; true; start += step) {
            while (i < discs.size()) {
                int time = start + i + 1;
                if ((discs.get(i).startPos() + time) % discs.get(i).posCount == 0) {
                    step *= discs.get(i).posCount;
                    i++;
                } else {
                    break;
                }
            }
            if (i == discs.size()) {
                return start;
            }
        }
    }

}
