package pkovacs.aoc.y2016;

import pkovacs.aoc.util.InputUtils;

public class Day03 {

    public static void main(String[] args) {
        var ints = InputUtils.parseInts(InputUtils.readString("y2016/day03.txt"));

        int cnt1 = 0;
        for (int i = 0; i < ints.length; i += 3) {
            int a = ints[i];
            int b = ints[i + 1];
            int c = ints[i + 2];
            if (a + b > c && a + c > b && b + c > a) {
                cnt1++;
            }
        }

        int cnt2 = 0;
        for (int i = 0; i < ints.length; ) {
            int a = ints[i];
            int b = ints[i + 3];
            int c = ints[i + 6];
            if (a + b > c && a + c > b && b + c > a) {
                cnt2++;
            }
            i++;
            if (i % 3 == 0) {
                i += 6;
            }
        }

        System.out.println("Part 1: " + cnt1);
        System.out.println("Part 2: " + cnt2);
    }

}
