package com.github.pkovacs.aoc.y2016;

import java.util.HashSet;

import com.github.pkovacs.util.InputUtils;
import com.github.pkovacs.util.Point;

public class Day01 {

    public static void main(String[] args) {
        var line = InputUtils.readSingleLine("day01.txt");
        var commands = line.split(", ");

        var p = Point.ORIGIN;
        var dir = new Point(0, 1);
        Point hq = null;

        var set = new HashSet<>();
        set.add(p);
        for (String cmd : commands) {
            dir = cmd.charAt(0) == 'R' ? new Point(dir.y(), -dir.x()) : new Point(-dir.y(), dir.x());
            int steps = Integer.parseInt(cmd.substring(1));
            for (int i = 0; i < steps; i++) {
                p = new Point(p.x() + dir.x(), p.y() + dir.y());
                if (!set.add(p) && hq == null) {
                    hq = p;
                }
            }
        }

        System.out.println("Part 1: " + p.dist());
        System.out.println("Part 2: " + hq.dist());
    }

}
