package pkovacs.aoc.y2016;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
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
        var startState = new State(lines);

        long start = System.currentTimeMillis();

        var result = Bfs.run(startState, st -> {
            visitedNodeCount++;

            var nextStates = new ArrayList<State>();
            for (int offset : new byte[] { +1, -1 }) {
                int newElevatorPos = st.elevator + offset;
                if (newElevatorPos < 0 || newElevatorPos >= FLOOR_COUNT) {
                    continue;
                }

                State base = new State(st);
                base.elevator = newElevatorPos;

                // Bring one or two items
                int itemCount = st.getItemCount();
                for (int a = 0; a < itemCount; a++) {
                    if (st.getFloor(a) != st.elevator) {
                        continue;
                    }
                    var nextState1 = new State(base);
                    nextState1.move(a, newElevatorPos);
                    if (nextState1.isSafe()) {
                        nextStates.add(nextState1);
                    }

                    for (int b = a + 1; b < itemCount; b++) {
                        if (st.getFloor(b) != st.elevator) {
                            continue;
                        }
                        var nextState2 = new State(base);
                        nextState2.move(a, newElevatorPos);
                        nextState2.move(b, newElevatorPos);
                        if (nextState2.isSafe()) {
                            nextStates.add(nextState2);
                        }
                    }
                }
            }

            return nextStates;
        }, State::isTerminal).get();

//        System.out.println("DIST:      " + result.getDist());
//        System.out.println("Time (ms): " + (System.currentTimeMillis() - start));
//        System.out.println(visitedNodeCount);
//        System.out.println();
//
//        for (var st : result.getPath()) {
//            System.out.println(st);
//        }

        return (int) result.getDist();
    }

    private static class State {

        int elevator;
        final byte[] microchips; // floor ID for each microchip
        final byte[] generators; // floor ID for each generator

        State(State st) {
            elevator = st.elevator;
            microchips = st.microchips.clone();
            generators = st.generators.clone();
        }

        State(List<String> lines) {
            elevator = 0;

            var types = getAllMatches(String.join(" ", lines), "[^ ]*-compatible")
                    .stream()
                    .map(x -> x.split("-")[0])
                    .distinct()
                    .collect(toList());

            microchips = new byte[types.size()];
            generators = new byte[types.size()];
            for (int k = 0; k < FLOOR_COUNT; k++) {
                var line = lines.get(k);
                if (line.contains("nothing relevant")) {
                    continue;
                }
                for (var gen : getAllMatches(line, "[^ ]* generator")) {
                    int typeId = types.indexOf(gen.replace(" generator", ""));
                    generators[typeId] = (byte) k;
                }
                for (var gen : getAllMatches(line, "[^ ]*-compatible microchip")) {
                    int typeId = types.indexOf(gen.replace("-compatible microchip", ""));
                    microchips[typeId] = (byte) k;
                }
            }
        }

        int getItemCount() {
            return microchips.length + generators.length;
        }

        int getFloor(int item) {
            byte[] array = item < microchips.length ? microchips : generators;
            int index = item < microchips.length ? item : item - microchips.length;
            return array[index];
        }

        void move(int item, int floor) {
            byte[] array = item < microchips.length ? microchips : generators;
            int index = item < microchips.length ? item : item - microchips.length;
            array[index] = (byte) floor;
        }

        boolean isTerminal() {
            return IntStream.range(0, microchips.length)
                    .allMatch(i -> microchips[i] == TARGET_FLOOR && generators[i] == TARGET_FLOOR);
        }

        boolean isSafe() {
            // Check that there are no microchips in danger on any floor
            for (int k = 0; k < FLOOR_COUNT; k++) {
                if (!containsGenerator(k)) {
                    continue; // no generators here
                }
                for (int i = 0; i < microchips.length; i++) {
                    if (microchips[i] == k && generators[i] != k) {
                        return false; // this microchip would be destroyed
                    }
                }
            }
            return true;
        }

        private boolean containsGenerator(int floor) {
            for (byte x : generators) {
                if (x == floor) {
                    return true;
                }
            }
            return false;
        }

        private static List<String> getAllMatches(String str, String regex) {
            var matches = new ArrayList<String>();
            Matcher m = Pattern.compile("(" + regex + ")").matcher(str);
            while (m.find()) {
                matches.add(m.group(1));
            }
            return matches;
        }

        @Override
        public String toString() {
            return String.format("E: %d, M: %s, G: %s",
                    elevator, Arrays.toString(microchips), Arrays.toString(generators));
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            State state = (State) o;
            return elevator == state.elevator && Arrays.equals(microchips, state.microchips)
                    && Arrays.equals(generators, state.generators);
        }

        @Override
        public int hashCode() {
            int result = Objects.hash(elevator);
            result = 31 * result + Arrays.hashCode(microchips);
            result = 31 * result + Arrays.hashCode(generators);
            return result;
        }

    }

}
