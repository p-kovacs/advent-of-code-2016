package pkovacs.aoc.y2016;

import java.util.List;

import pkovacs.aoc.util.CounterMap;
import pkovacs.aoc.util.InputUtils;

public class Day12 {

    public static void main(String[] args) {
        var lines = InputUtils.readLines("y2016/day12.txt");

        var registers = new CounterMap<String>();

        var registers2 = new CounterMap<String>();
        registers2.put("c", 1L);

        System.out.println("Part 1: " + solve(lines, registers));
        System.out.println("Part 2: " + solve(lines, registers2));
    }

    private static long solve(List<String> code, CounterMap<String> registers) {
        for (int i = 0; i < code.size(); ) {
            var p = code.get(i).split(" ");
            switch (p[0]) {
                case "cpy" -> registers.put(p[2], getValue(registers, p[1]));
                case "inc" -> registers.inc(p[1]);
                case "dec" -> registers.dec(p[1]);
                case "jnz" -> {
                    long value = getValue(registers, p[1]);
                    if (value != 0) {
                        i += Integer.parseInt(p[2]);
                        continue; // skip standard step
                    }
                }
            }
            i++;
        }
        return registers.get("a");
    }

    private static long getValue(CounterMap<String> registers, String arg) {
        return Character.isDigit(arg.charAt(0)) ? Long.parseLong(arg) : registers.get(arg);
    }

}
