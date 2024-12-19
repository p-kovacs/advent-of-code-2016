package com.github.pkovacs.aoc.y2016;

import java.util.List;

import com.github.pkovacs.util.CounterMap;
import com.github.pkovacs.util.InputUtils;

public class Day12 {

    public static void main(String[] args) {
        var lines = InputUtils.readLines("day12.txt");

        var mem1 = new CounterMap<String>();

        var mem2 = new CounterMap<String>();
        mem2.put("c", 1L);

        System.out.println("Part 1: " + solve(lines, mem1));
        System.out.println("Part 2: " + solve(lines, mem2));
    }

    private static long solve(List<String> code, CounterMap<String> mem) {
        for (int i = 0; i < code.size(); ) {
            var p = code.get(i).split(" ");
            switch (p[0]) {
                case "cpy" -> mem.put(p[2], getValue(mem, p[1]));
                case "inc" -> mem.inc(p[1]);
                case "dec" -> mem.dec(p[1]);
                case "jnz" -> {
                    long value = getValue(mem, p[1]);
                    if (value != 0) {
                        i += (int) getValue(mem, p[2]);
                        continue; // skip standard step
                    }
                }
            }
            i++;
        }
        return mem.getValue("a");
    }

    private static long getValue(CounterMap<String> mem, String arg) {
        return isRegister(arg) ? mem.getValue(arg) : Long.parseLong(arg);
    }

    private static boolean isRegister(String s) {
        return s.length() == 1 && Character.isLetter(s.charAt(0));
    }

}
