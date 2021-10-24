package pkovacs.aoc.y2016;

import java.util.HashSet;

import pkovacs.aoc.util.InputUtils;
import pkovacs.aoc.util.Vector;

public class Day01 {

    public static void main(String[] args) {
        var lines = InputUtils.readLines("y2016/day01.txt");
        var commands = lines.get(0).split(", ");

        Vector v = Vector.ORIGIN;
        Vector dir = Vector.NORTH;
        Vector hq = null;

        var set = new HashSet<>();
        set.add(v);
        for (String cmd : commands) {
            if (cmd.charAt(0) == 'R') {
                dir = dir.rotateRight();
            } else {
                dir = dir.rotateLeft();
            }
            int steps = Integer.parseInt(cmd.substring(1));
            for (int i = 0; i < steps; i++) {
                v = v.add(dir);
                if (!set.add(v) && hq == null) {
                    hq = v;
                }
            }
        }

        System.out.println("Part 1: " + v.dist());
        System.out.println("Part 2: " + hq.dist());
    }

}
