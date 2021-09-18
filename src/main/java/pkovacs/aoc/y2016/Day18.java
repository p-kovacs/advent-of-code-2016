package pkovacs.aoc.y2016;

import java.util.stream.IntStream;

import pkovacs.aoc.util.InputUtils;

public class Day18 {

    public static void main(String[] args) {
        var input = InputUtils.readLine("y2016/day18.txt");

        System.out.println("Part 1: " + solve(input, 40));
        System.out.println("Part 2: " + solve(input, 400000));
    }

    private static int solve(String input, int rowCount) {
        int cnt = getSafeTileCount(input);

        String row = input;
        for (int i = 0; i < rowCount - 1; i++) {
            String prev = "." + row + ".";
            var sb = new StringBuilder();
            for (int j = 0, length = row.length(); j < length; j++) {
                String parents = prev.substring(j, j + 3);
                boolean isTrap = "^^.".equals(parents)
                        || ".^^".equals(parents)
                        || "..^".equals(parents)
                        || "^..".equals(parents);
                sb.append(isTrap ? '^' : '.');
            }
            row = sb.toString();
            cnt += getSafeTileCount(row);
        }

        return cnt;
    }

    private static int getSafeTileCount(String row) {
        return (int) IntStream.range(0, row.length()).filter(i -> row.charAt(i) == '.').count();
    }

}
