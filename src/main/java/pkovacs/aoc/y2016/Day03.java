package pkovacs.aoc.y2016;

import pkovacs.aoc.util.InputUtils;

public class Day03 {

    public static void main(String[] args) {
        var lines = InputUtils.readLines("day03.txt");

        int[][] ints = new int[lines.size()][];
        for (int i = 0; i < lines.size(); i++) {
            ints[i] = InputUtils.parseInts(lines.get(i));
        }

        int cnt1 = 0;
        for (int[] x : ints) {
            if (canBeTriangle(x[0], x[1], x[2])) {
                cnt1++;
            }
        }

        int cnt2 = 0;
        for (int i = 0; i < ints.length; i += 3) {
            for (int j = 0; j < 3; j++) {
                if (canBeTriangle(ints[i][j], ints[i + 1][j], ints[i + 2][j])) {
                    cnt2++;
                }
            }
        }

        System.out.println("Part 1: " + cnt1);
        System.out.println("Part 2: " + cnt2);
    }

    private static boolean canBeTriangle(int a, int b, int c) {
        return a + b > c && a + c > b && b + c > a;
    }

}
