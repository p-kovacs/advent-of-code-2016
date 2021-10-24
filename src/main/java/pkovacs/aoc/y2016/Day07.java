package pkovacs.aoc.y2016;

import pkovacs.aoc.util.InputUtils;

public class Day07 {

    public static void main(String[] args) {
        var lines = InputUtils.readLines("y2016/day07.txt");

        long validCount1 = lines.stream().filter(Day07::isValid1).count();
        long validCount2 = lines.stream().filter(Day07::isValid2).count();

        System.out.println("Part 1: " + validCount1);
        System.out.println("Part 2: " + validCount2);
    }

    private static boolean isValid1(String str) {
        int bracketLevel = 0;
        boolean foundOuter = false;
        boolean foundInner = false;
        for (int i = 0; i < str.length() - 3; i++) {
            char ch1 = str.charAt(i);
            if (ch1 == '[') {
                bracketLevel++;
            } else if (ch1 == ']') {
                bracketLevel--;
            } else {
                char ch2 = str.charAt(i + 1);
                char ch3 = str.charAt(i + 2);
                char ch4 = str.charAt(i + 3);
                if (ch1 == ch4 && ch2 == ch3 && ch1 != ch2 && ch2 != '[' && ch2 != ']') {
                    if (bracketLevel == 0) {
                        foundOuter = true;
                    } else {
                        foundInner = true;
                    }
                }
            }
        }

        return foundOuter && !foundInner;
    }

    private static boolean isValid2(String str) {
        int bracketLevel = 0;
        for (int i = 0; i < str.length() - 2; i++) {
            char ch1 = str.charAt(i);
            if (ch1 == '[') {
                bracketLevel++;
            } else if (ch1 == ']') {
                bracketLevel--;
            } else {
                char ch2 = str.charAt(i + 1);
                char ch3 = str.charAt(i + 2);
                if (ch1 == ch3 && ch1 != ch2 && ch2 != '[' && ch2 != ']') {
                    if (bracketLevel == 0) {
                        if (str.matches(".*\\[[^]]*" + ch2 + ch1 + ch2 + ".*")) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

}
