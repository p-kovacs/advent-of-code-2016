package com.github.pkovacs.aoc.y2016;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.IntStream;

import com.github.pkovacs.util.InputUtils;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;

public class Day06 {

    public static void main(String[] args) {
        var lines = InputUtils.readLines("day06.txt");

        System.out.println("Part 1: " + decode2(lines, false));
        System.out.println("Part 2: " + decode2(lines, true));
    }

    private static String decode1(List<String> lines, boolean reversed) {
        var msg = new StringBuilder();
        for (int k = 0; k < lines.get(0).length(); k++) {
            var counts = new HashMap<Character, Integer>();
            for (var line : lines) {
                counts.merge(line.charAt(k), 1, Integer::sum);
            }

            char ch = counts.entrySet().stream()
                    .max(comparing(e -> reversed ? -e.getValue() : e.getValue()))
                    .orElseThrow()
                    .getKey();
            msg.append(ch);
        }
        return msg.toString();
    }

    private static String decode2(List<String> lines, boolean reversed) {
        return IntStream.range(0, lines.get(0).length())
                .mapToObj(k -> lines.stream().collect(groupingBy(line -> line.charAt(k), counting())))
                .map(Map::entrySet)
                .map(Set::stream)
                .map(s -> reversed ? s.min(Entry.comparingByValue()) : s.max(Entry.comparingByValue()))
                .map(Optional::orElseThrow)
                .map(Entry::getKey)
                .map(String::valueOf)
                .collect(joining());
    }

}
