package pkovacs.aoc.y2016;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import pkovacs.aoc.alg.Bfs;
import pkovacs.aoc.util.InputUtils;

import static java.util.stream.Collectors.toList;

public class Day11 {

    private static final int FLOOR_COUNT = 4;
    private static final int TARGET_FLOOR = FLOOR_COUNT - 1;

    static int visitedNodeCount = 0;

    public static void main(String[] args) {
        var lines1 = InputUtils.readLines("y2016/day11.txt");

        var lines2 = new ArrayList<>(lines1);
        lines2.set(0, lines2.get(0) + " AND an elerium generator, an elerium-compatible microchip, "
                + "a dilithium generator, a dilithium-compatible microchip.");

        System.out.println("Part 1: " + solve(lines1));
        System.out.println("Part 2: " + solve(lines2));
    }

    private static int solve(List<String> lines) {

        final var types = getAllMatches(String.join(" ", lines), "[^ ]*-compatible")
                .stream()
                .map(x -> x.split("-")[0])
                .distinct()
                .collect(toList());
        final int itemCount = types.size() * 2;

        var startState = new State(lines, types);

        var targetState = new State(startState);
        targetState.setElevator(TARGET_FLOOR);
        IntStream.range(0, itemCount).forEach(i -> targetState.set(i, TARGET_FLOOR));

        long start = System.currentTimeMillis();

        var result = Bfs.run(startState, st -> {
            visitedNodeCount++;

            var nextStates = new ArrayList<State>();
            for (int offset : new byte[] { +1, -1 }) {
                int elevator = st.getElevator();
                int newElevator = elevator + offset;
                if (newElevator < 0 || newElevator >= FLOOR_COUNT) {
                    continue;
                }
//                if (offset < 0 && IntStream.range(0, st.microchips.length)
//                        .allMatch(i -> st.microchips[i] >= st.elevator && st.generators[i] >= st.elevator)) {
//                    continue;
//                }

                State base = new State(st);
                base.setElevator(newElevator);

                // Bring one or two items
                for (int a = 0; a < itemCount; a++) {
                    if (st.get(a) != elevator) {
                        continue;
                    }
                    var nextState1 = new State(base);
                    nextState1.set(a, newElevator);
                    if (nextState1.isSafe(itemCount)) {
                        nextStates.add(nextState1);
                    }

                    for (int b = a + 1; b < itemCount; b++) {
                        if (st.get(b) != elevator) {
                            continue;
                        }
                        var nextState2 = new State(base);
                        nextState2.set(a, newElevator);
                        nextState2.set(b, newElevator);
                        if (nextState2.isSafe(itemCount)) {
                            nextStates.add(nextState2);
                        }
                    }
                }
            }

            return nextStates;
        }, st -> st.equals(targetState)).get();

        System.out.println("DIST:      " + result.getDist());
        System.out.println("Time (ms): " + (System.currentTimeMillis() - start));
        System.out.println(visitedNodeCount);
        System.out.println();
//
//        for (var st : result.getPath()) {
//            System.out.println(st);
//        }

        return (int) result.getDist();
    }

    private static List<String> getAllMatches(String str, String regex) {
        var matches = new ArrayList<String>();
        Matcher m = Pattern.compile("(" + regex + ")").matcher(str);
        while (m.find()) {
            matches.add(m.group(1));
        }
        return matches;
    }

    private static class State {

        // Last 2 bits: elevator position (floor ID)
        // Previous 2*k bits: positions of microchips
        // Previous 2*k bits: positions of generators
        long data;

        State(State st) {
            data = st.data;
        }

        State(List<String> lines, List<String> types) {
            data = 0;

            for (int k = 0; k < FLOOR_COUNT; k++) {
                var line = lines.get(k);
                if (line.contains("nothing relevant")) {
                    continue;
                }
                for (var gen : getAllMatches(line, "[^ ]* generator")) {
                    int typeId = types.indexOf(gen.replace(" generator", ""));
                    set(typeId, k);
                }
                for (var gen : getAllMatches(line, "[^ ]*-compatible microchip")) {
                    int typeId = types.indexOf(gen.replace("-compatible microchip", ""));
                    set(types.size() + typeId, k);
                }
            }
        }

        int getElevator() {
            return get(-1);
        }

        void setElevator(int floor) {
            set(-1, floor);
        }

        int get(int item) {
            int offset = (item + 1) << 1;
            return (int) ((data >> offset) & 0x3L);
        }

        void set(int item, int floor) {
            int offset = (item + 1) << 1;
            data = data & ~(0x3L << offset) | ((floor & 0x3L) << offset);
        }

        boolean isSafe(int itemCount) {
            // Check that there are no microchips in danger on any floor
            int generatorOffset = itemCount / 2;
            for (int k = 0; k < FLOOR_COUNT; k++) {
                if (!containsGenerator(k, itemCount)) {
                    continue; // no generators here
                }
                for (int i = 0; i < generatorOffset; i++) {
                    if (get(i) == k && get(generatorOffset + i) != k) {
                        return false; // this microchip would be destroyed
                    }
                }
            }
            return true;
        }

        private boolean containsGenerator(int floor, int itemCount) {
            return IntStream.range(itemCount / 2, itemCount).anyMatch(i -> get(i) == floor);
        }

        @Override
        public boolean equals(Object other) {
            return other instanceof State && ((State) other).data == data;
        }

        @Override
        public int hashCode() {
            return (int) data;
        }

    }

}
