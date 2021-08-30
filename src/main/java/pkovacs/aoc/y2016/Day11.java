package pkovacs.aoc.y2016;

import java.util.ArrayList;
import java.util.Arrays;
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
        var startState = new State(lines);

        var targetState = new State(startState);
        targetState.elevator = TARGET_FLOOR;
        IntStream.range(0, targetState.getItemCount()).forEach(i -> targetState.setFloor(i, TARGET_FLOOR));

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
                    nextState1.setFloor(a, newElevatorPos);
                    if (nextState1.isSafe()) {
                        nextStates.add(nextState1);
                    }

                    for (int b = a + 1; b < itemCount; b++) {
                        if (st.getFloor(b) != st.elevator) {
                            continue;
                        }
                        var nextState2 = new State(base);
                        nextState2.setFloor(a, newElevatorPos);
                        nextState2.setFloor(b, newElevatorPos);
                        if (nextState2.isSafe()) {
                            nextStates.add(nextState2);
                        }
                    }
                }
            }

            return nextStates;
        }, targetState::equals).get();

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
        final byte[] items; // floor ID for items

        State(State st) {
            elevator = st.elevator;
            items = st.items.clone();
        }

        State(List<String> lines) {
            elevator = 0;

            var types = getAllMatches(String.join(" ", lines), "[^ ]*-compatible")
                    .stream()
                    .map(x -> x.split("-")[0])
                    .distinct()
                    .collect(toList());

            items = new byte[types.size() * 2];
            for (int k = 0; k < FLOOR_COUNT; k++) {
                var line = lines.get(k);
                if (line.contains("nothing relevant")) {
                    continue;
                }
                for (var gen : getAllMatches(line, "[^ ]* generator")) {
                    int typeId = types.indexOf(gen.replace(" generator", ""));
                    items[typeId] = (byte) k;
                }
                for (var gen : getAllMatches(line, "[^ ]*-compatible microchip")) {
                    int typeId = types.indexOf(gen.replace("-compatible microchip", ""));
                    items[types.size() + typeId] = (byte) k;
                }
            }
        }

        int getItemCount() {
            return items.length;
        }

        int getFloor(int itemId) {
            return items[itemId];
        }

        void setFloor(int itemId, int floor) {
            items[itemId] = (byte) floor;
        }

        boolean isSafe() {
            // Check that there are no microchips in danger on any floor
            for (int k = 0; k < FLOOR_COUNT; k++) {
                if (!containsGenerator(k)) {
                    continue;
                }
                for (int i = 0, offset = items.length / 2; i < offset; i++) {
                    if (items[i] == k && items[offset + i] != k) {
                        return false; // this microchip would be destroyed
                    }
                }
            }
            return true;
        }

        private boolean containsGenerator(int floor) {
            for (int i = items.length / 2; i < items.length; i++) {
                if (items[i] == floor) {
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
            return String.format("E: %d, Items: %s", elevator, Arrays.toString(items));
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
            return elevator == state.elevator && Arrays.equals(items, state.items);
        }

        @Override
        public int hashCode() {
            return 31 * elevator + Arrays.hashCode(items);
        }

    }

}
