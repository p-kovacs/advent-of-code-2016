package com.github.pkovacs.aoc.y2016;

import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.github.pkovacs.util.InputUtils;

import static java.util.stream.Collectors.joining;

public class Day04 {

    public static void main(String[] args) {
        var lines = InputUtils.readLines("day04.txt");

        long sum = 0;
        long storageId = -1;
        for (String line : lines) {
            var parts = InputUtils.scan(line, "%s-%d\\[%s\\]");
            var name = parts.get(0).get();
            var id = parts.get(1).asInt();
            var checksum = parts.get(2).get();

            var counts = charStream(name)
                    .filter(c -> c != '-')
                    .collect(Collectors.groupingBy(c -> c, Collectors.counting()));

            var expectedChecksum = counts.entrySet().stream()
                    .sorted(Entry.<Character, Long>comparingByValue().reversed().thenComparing(Entry::getKey))
                    .limit(5)
                    .map(Entry::getKey)
                    .map(String::valueOf)
                    .collect(joining(""));
            if (!expectedChecksum.equals(checksum)) {
                continue;
            }

            sum += id;

            var realName = charStream(name)
                    .map(c -> c == '-' ? ' ' : (char) ('a' + (((c - 'a') + id) % 26)))
                    .map(String::valueOf)
                    .collect(joining());
            if (realName.equals("northpole object storage")) {
                storageId = id;
            }
        }

        System.out.println("Part 1: " + sum);
        System.out.println("Part 2: " + storageId);
    }

    private static Stream<Character> charStream(String str) {
        return str.chars().mapToObj(i -> (char) i);
    }

}
