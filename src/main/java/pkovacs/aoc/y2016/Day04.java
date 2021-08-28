package pkovacs.aoc.y2016;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import pkovacs.aoc.util.InputUtils;

public class Day04 {

    public static void main(String[] args) {
        var lines = InputUtils.readLines("y2016/day04.txt");

        long sum = 0;
        long storageId = -1;
        for (String line : lines) {
            var parts = InputUtils.scan(line, "%s-%d\\[%s\\]");
            var name = parts.get(0).get();
            var id = parts.get(1).asInt();
            var checksum = parts.get(2).get();

            var counts = new HashMap<Character, Integer>();
            for (int i = 0; i < name.length(); i++) {
                char ch = name.charAt(i);
                if (ch == '-') {
                    continue;
                }
                counts.merge(ch, 1, Integer::sum);
            }

            var expectedChecksum = counts.entrySet().stream()
                    .sorted(Comparator.comparing(e -> ((long) -e.getValue()) << 32 | e.getKey()))
                    .limit(5)
                    .map(Entry::getKey)
                    .map(String::valueOf)
                    .collect(Collectors.joining(""));
            if (!expectedChecksum.equals(checksum)) {
                continue;
            }

            sum += id;
            var sb = new StringBuilder();
            for (int i = 0; i < name.length(); i++) {
                char ch = name.charAt(i);
                char realCh = ch == '-'
                        ? ' '
                        : (char) ('a' + (((ch - 'a') + id) % 26));
                sb.append(realCh);
            }
            if (sb.toString().equals("northpole object storage")) {
                storageId = id;
            }
        }

        System.out.println("Part 1: " + sum);
        System.out.println("Part 2: " + storageId);
    }

}
