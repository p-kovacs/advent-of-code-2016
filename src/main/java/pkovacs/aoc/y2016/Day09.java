package pkovacs.aoc.y2016;

import pkovacs.aoc.util.InputUtils;

public class Day09 {

    public static void main(String[] args) {
        var lines = InputUtils.readLines("y2016/day09.txt");
        var input = lines.get(0);

        System.out.println("Part 1: " + getDecompressedLength(input, false));
        System.out.println("Part 2: " + getDecompressedLength(input, true));
    }

    private static long getDecompressedLength(String str, boolean recursive) {
        long total = 0;
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (ch == ' ') {
                continue;
            }
            if (ch == '(') {
                var endIndex = str.indexOf(')', i);
                var marker = str.substring(i + 1, endIndex).split("x");

                int charCount = Integer.parseInt(marker[0]);
                int count = Integer.parseInt(marker[1]);
                var toRepeat = str.substring(endIndex + 1, endIndex + charCount + 1);
                var length = recursive ? getDecompressedLength(toRepeat, true) : toRepeat.length();
                total += length * count;

                i = endIndex + charCount;
            } else {
                total++;
            }
        }
        return total;
    }

}
