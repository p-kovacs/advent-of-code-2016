package pkovacs.aoc.y2016;

import java.util.List;

import pkovacs.aoc.util.CounterMap;
import pkovacs.aoc.util.InputUtils;

public class Day23 {

    // Original code for a = b * d; c = 0; d = 0; (7 commands)
    private static final String MULTIPLICATION_ORIG =
            "cpy 0 a\ncpy b c\ninc a\ndec c\njnz c -2\ndec d\njnz d -5";

    // Optimized code for a = b * d; c = 0; d = 0; using a new command "mul" (7 commands, the last one multiplied)
    private static final String MULTIPLICATION_OPTIMIZED =
            "cpy b a\nmul a d\ncpy 0 c\ncpy 0 d\ncpy 0 d\ncpy 0 d\ncpy 0 d";

    public static void main(String[] args) {
        var code = InputUtils.readString("y2016/day23.txt");

        // Hard-coded optimization for multiplication. We assume that exactly the same series of commands are used
        // in each personalized input (it seems to be the case anyway).
        code = code.replace(MULTIPLICATION_ORIG, MULTIPLICATION_OPTIMIZED);

        var lines = List.of(code.split("\n"));
        System.out.println("Part 1: " + solve(lines, 7));
        System.out.println("Part 2: " + solve(lines, 12));
    }

    private static long solve(List<String> code, long initialValue) {
        var mem = new CounterMap<String>();
        mem.put("a", initialValue);

        boolean[] toggled = new boolean[code.size()];
        for (int i = 0; i < code.size(); ) {
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
                case "mul" -> {
                    if (isRegister(p[1])) {
                        mem.put(p[1], mem.get(p[1]) * getValue(mem, p[2]));
                    }
                }
                default -> throw new IllegalArgumentException("Unknown command: " + cmd);
            }

            i++;
        }

        return mem.get("a");
    }

    private static boolean isRegister(String s) {
        return s.length() == 1 && Character.isLetter(s.charAt(0));
    }

    private static long getValue(CounterMap<String> mem, String arg) {
        return isRegister(arg) ? mem.get(arg) : Long.parseLong(arg);
    }

}
