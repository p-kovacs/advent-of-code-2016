package com.github.pkovacs.aoc.y2016;

import java.util.List;
import java.util.Optional;

import com.github.pkovacs.util.InputUtils;

public class Day20 {

    public static void main(String[] args) {
        var lines = InputUtils.readLines("day20.txt");

        var ranges = lines.stream().map(Range::new).toList();

        long first = -1;
        long cnt = 0;
        for (long i = 0; i <= 4294967295L; i++) {
            var range = findRange(ranges, i);
            if (range.isPresent()) {
                i = range.get().max;
            } else {
                cnt++;
                if (first == -1) {
                    first = i;
                }
            }
        }

        System.out.println("Part 1: " + first);
        System.out.println("Part 2: " + cnt);
    }

    private static Optional<Range> findRange(List<Range> ranges, long i) {
        return ranges.stream().filter(r -> r.contains(i)).findAny();
    }

    private static record Range(long min, long max) {

        private Range(String line) {
            this(Long.parseLong(line.split("-")[0]), Long.parseLong(line.split("-")[1]));
        }

        private boolean contains(long i) {
            return i >= min && i <= max;
        }

    }

}
