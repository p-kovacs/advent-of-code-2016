package com.github.pkovacs.aoc.y2016;

import java.util.List;

import com.github.pkovacs.util.InputUtils;
import com.github.pkovacs.util.Tile;

public class Day02 {

    private static final char[][] KEYPAD1 = new char[][] {
            "123".toCharArray(),
            "456".toCharArray(),
            "789".toCharArray()
    };

    private static final char[][] KEYPAD2 = new char[][] {
            "##1##".toCharArray(),
            "#234#".toCharArray(),
            "56789".toCharArray(),
            "#ABC#".toCharArray(),
            "##D##".toCharArray()
    };

    public static void main(String[] args) {
        var lines = InputUtils.readLines("day02.txt");

        System.out.println("Part 1: " + solve(KEYPAD1, new Tile(1, 1), lines));
        System.out.println("Part 2: " + solve(KEYPAD2, new Tile(2, 0), lines));
    }

    private static String solve(char[][] keypad, Tile start, List<String> lines) {
        int size = keypad.length;
        var pos = start;
        var code = new StringBuilder();
        for (String line : lines) {
            for (char ch : line.toCharArray()) {
                Tile next = pos.neighbor(ch);
                if (next.isValid(size, size) && keypad[next.row()][next.col()] != '#') {
                    pos = next;
                }
            }
            code.append(keypad[pos.row()][pos.col()]);
        }
        return code.toString();
    }

}
