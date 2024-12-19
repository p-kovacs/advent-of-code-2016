package com.github.pkovacs.aoc.y2016;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.function.Consumer;

/**
 * Verifies the solution for each day against the expected answers for my puzzle input files.
 */
public class AllDays {

    private static final List<Day> DAYS = List.of(
            new Day("Day 01", Day01::main, "241", "116"),
            new Day("Day 02", Day02::main, "65556", "CB779"),
            new Day("Day 03", Day03::main, "869", "1544"),
            new Day("Day 04", Day04::main, "361724", "482"),
            new Day("Day 05", Day05::main, "f77a0e6e", "999828ec"),
            new Day("Day 06", Day06::main, "qoclwvah", "ryrgviuv"),
            new Day("Day 07", Day07::main, "118", "260"),
            new Day("Day 08", Day08::main, "119", "ZFHFSFOGPO"),
            new Day("Day 09", Day09::main, "110346", "10774309173"),
            new Day("Day 10", Day10::main, "141", "1209"),
            new Day("Day 11", Day11::main, "47", "71"),
            new Day("Day 12", Day12::main, "318007", "9227661"),
            new Day("Day 13", Day13::main, "90", "135"),
            new Day("Day 14", Day14::main, "25427", "22045"),
            new Day("Day 15", Day15::main, "203660", "2408135"),
            new Day("Day 16", Day16::main, "10111110010110110", "01101100001100100"),
            new Day("Day 17", Day17::main, "DDURRLRRDD", "436"),
            new Day("Day 18", Day18::main, "1961", "20000795"),
            new Day("Day 19", Day19::main, "1842613", "1424135"),
            new Day("Day 20", Day20::main, "14975795", "101"),
            new Day("Day 21", Day21::main, "agcebfdh", "afhdbegc"),
            new Day("Day 22", Day22::main, "1045", "265"),
            new Day("Day 23", Day23::main, "14160", "479010720"),
            new Day("Day 24", Day24::main, "448", "672"),
            new Day("Day 25", Day25::main, "196", "0")
    );

    public static void main(String[] args) {
        String format = "%-12s%-8s%-8s%8s%n";
        System.out.printf(format, "Day", "Part 1", "Part 2", "Time");

        DAYS.stream().filter(day -> day.mainMethod != null).forEach(day -> {
            long start = System.nanoTime();
            var results = runDay(day);
            long time = (System.nanoTime() - start) / 1_000_000L;

            System.out.printf(format, day.name, evaluate(day, results, 0), evaluate(day, results, 1), time + " ms");
        });
    }

    private static String evaluate(Day day, List<String> results, int index) {
        var expected = index == 0 ? day.expected1 : day.expected2;
        return results.size() == 2 && expected.equals(results.get(index)) ? "\u2714" : "FAILED";
    }

    private static List<String> runDay(Day day) {
        var origOut = System.out;
        try {
            var out = new ByteArrayOutputStream(200);
            System.setOut(new PrintStream(out));
            day.mainMethod.accept(null);
            return out.toString(StandardCharsets.UTF_8).lines().map(l -> l.split(": ")[1]).toList();
        } catch (Exception e) {
            return List.of();
        } finally {
            System.setOut(origOut);
        }
    }

    private record Day(String name, Consumer<String[]> mainMethod, String expected1, String expected2) {}

}
