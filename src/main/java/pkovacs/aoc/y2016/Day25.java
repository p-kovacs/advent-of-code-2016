package pkovacs.aoc.y2016;

import java.util.ArrayList;
import java.util.List;

import pkovacs.aoc.util.CounterMap;
import pkovacs.aoc.util.InputUtils;

public class Day25 {

    public static void main(String[] args) {
        var code = InputUtils.readLines("day25.txt");

        int limit = 10; // experimental limit: only the first few numbers are checked

        System.out.println("Part 1: " + findInitialValue(code, limit));
        System.out.println("Part 2: ");
    }

    private static int findInitialValue(List<String> code, int limit) {
        for (int k = 0; true; k++) {
            var output = solve(code, k, limit);
            if (isExpectedOutput(output)) {
                return k;
            }
        }
    }

    private static boolean isExpectedOutput(List<Long> output) {
        for (int i = 0; i < output.size(); i++) {
            if (output.get(i) != i % 2) {
                return false;
            }
        }
        return true;
    }

    private static List<Long> solve(List<String> code, long initialValue, int limit) {
        var mem = new CounterMap<String>();
        mem.put("a", initialValue);

        var output = new ArrayList<Long>();
        boolean[] toggled = new boolean[code.size()];
        for (int i = 0; i < code.size() && output.size() < limit; ) {
            var p = code.get(i).split(" ");
            var cmd = p[0];

            if (toggled[i]) {
                cmd = switch (cmd) {
                    case "inc" -> "dec";
                    case "dec" -> "inc";
                    case "tgl" -> "inc";
                    case "jnz" -> "cpy";
                    case "cpy" -> "jnz";
                    default -> cmd;
                };
            }

            switch (cmd) {
                case "cpy" -> {
                    if (isRegister(p[2])) {
                        mem.put(p[2], getValue(mem, p[1]));
                    }
                }
                case "inc" -> {
                    if (isRegister(p[1])) {
                        mem.inc(p[1]);
                    }
                }
                case "dec" -> {
                    if (isRegister(p[1])) {
                        mem.dec(p[1]);
                    }
                }
                case "jnz" -> {
                    long value = getValue(mem, p[1]);
                    if (value != 0) {
                        i += (int) getValue(mem, p[2]);
                        continue; // skip standard step
                    }
                }
                case "tgl" -> {
                    int j = i + (int) getValue(mem, p[1]);
                    if (j >= 0 && j < toggled.length) {
                        toggled[j] = !toggled[j];
                    }
                }
                case "out" -> {
                    output.add(getValue(mem, p[1]));
                }
                default -> throw new IllegalArgumentException("Unknown command: " + cmd);
            }

            i++;
        }

        return output;
    }

    private static long getValue(CounterMap<String> mem, String arg) {
        return isRegister(arg) ? mem.getValue(arg) : Long.parseLong(arg);
    }

    private static boolean isRegister(String s) {
        return s.length() == 1 && Character.isLetter(s.charAt(0));
    }

}
