package pkovacs.aoc.y2016;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map.Entry;

import pkovacs.aoc.util.InputUtils;

public class Day06 {

    public static void main(String[] args) {
        var lines = InputUtils.readLines("y2016/day06.txt");

        int length = lines.get(0).length();

        var msg1 = new StringBuilder();
        var msg2 = new StringBuilder();
        for (int k = 0; k < length; k++) {
            var counts = new HashMap<Character, Integer>();
            for (var line : lines) {
                counts.merge(line.charAt(k), 1, Integer::sum);
            }
            char mostCommon = counts.entrySet().stream()
                    .max(Comparator.comparing(Entry::getValue))
                    .get().getKey();
            char leastCommon = counts.entrySet().stream()
                    .min(Comparator.comparing(Entry::getValue))
                    .get().getKey();
            msg1.append(mostCommon);
            msg2.append(leastCommon);
        }

        System.out.println("Part 1: " + msg1);
        System.out.println("Part 2: " + msg2);
    }

}
