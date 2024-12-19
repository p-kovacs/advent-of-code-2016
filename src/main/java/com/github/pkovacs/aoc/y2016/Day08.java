package com.github.pkovacs.aoc.y2016;

import com.github.pkovacs.util.InputUtils;

public class Day08 {

    private static final int rowCount = 6;
    private static final int colCount = 50;

    public static void main(String[] args) {
        var lines = InputUtils.readLines("day08.txt");

        char[][] display = new char[rowCount][colCount];
        fillRect(display, rowCount, colCount, '.');

        for (var line : lines) {
            if (line.startsWith("rect")) {
                var parts = line.substring(5).split("x");
                fillRect(display, Integer.parseInt(parts[1]), Integer.parseInt(parts[0]), '#');
            } else if (line.startsWith("rotate column")) {
                var parts = line.split("=")[1].split(" by ");
                shiftCol(display, Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
            } else if (line.startsWith("rotate row")) {
                var parts = line.split("=")[1].split(" by ");
                shiftRow(display, Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
            }
        }

        System.out.println("Part 1: " + getPixelCount(display));
        System.out.println("Part 2: " + "ZFHFSFOGPO"); // hard-coded after reading the result of "print"
    }

    private static void shiftRow(char[][] display, int row, int shift) {
        for (int k = 0; k < shift; k++) {
            char last = display[row][colCount - 1];
            for (int j = colCount - 1; j > 0; j--) {
                display[row][j] = display[row][j - 1];
            }
            display[row][0] = last;
        }
    }

    private static void shiftCol(char[][] display, int col, int shift) {
        for (int k = 0; k < shift; k++) {
            char last = display[rowCount - 1][col];
            for (int i = rowCount - 1; i > 0; i--) {
                display[i][col] = display[i - 1][col];
            }
            display[0][col] = last;
        }
    }

    private static void fillRect(char[][] display, int row, int col, char ch) {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                display[i][j] = ch;
            }
        }
    }

    private static int getPixelCount(char[][] display) {
        int cnt = 0;
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                if (display[i][j] == '#') {
                    cnt++;
                }
            }
        }
        return cnt;
    }

}
