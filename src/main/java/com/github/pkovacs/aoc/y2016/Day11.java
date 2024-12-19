package com.github.pkovacs.aoc.y2016;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import com.github.pkovacs.util.Bfs;
import com.github.pkovacs.util.InputUtils;

public class Day11 {

    private static final int FLOOR_COUNT = 4;
    private static final int TARGET_FLOOR = FLOOR_COUNT - 1;

    public static void main(String[] args) {
        var lines1 = InputUtils.readLines("day11.txt");

        var lines2 = new ArrayList<>(lines1);
        lines2.set(0, lines2.get(0) + " AND an elerium generator, an elerium-compatible microchip, "
                + "a dilithium generator, a dilithium-compatible microchip.");

        System.out.println("Part 1: " + solve(lines1));
        System.out.println("Part 2: " + solve(lines2));
    }

    private static long solve(List<String> lines) {
        return Bfs.run(new State(lines), Day11::getNextStates, State::isTerminal).orElseThrow().getDist();
    }

    private static Collection<State> getNextStates(State st) {
        var nextStates = new HashSet<State>();
        for (int offset : new int[] { +1, -1 }) {
            int newElevatorPos = st.elevator + offset;
            if (newElevatorPos < 0 || newElevatorPos >= FLOOR_COUNT) {
                continue;
            }

            // Bring one or two items
            int itemCount = st.getItemCount();
            for (int a = 0; a < itemCount; a++) {
                if (st.getFloor(a) != st.elevator) {
                    continue;
                }
                var nextState1 = st.move(a, newElevatorPos);
                if (nextState1.isSafe()) {
                    nextStates.add(nextState1);
                }

                for (int b = a + 1; b < itemCount; b++) {
                    if (st.getFloor(b) != st.elevator) {
                        continue;
                    }
                    var nextState2 = nextState1.move(b, newElevatorPos);
                    if (nextState2.isSafe()) {
                        nextStates.add(nextState2);
                    }
                }
            }
        }

        return nextStates;
    }

    private static class State {

        int elevator;
        final int[] items; // floor ID for items

        State(int elevator, int[] items) {
            this.elevator = elevator;
            this.items = items;
        }

        State(List<String> lines) {
            elevator = 0;

            var types = getAllMatches(String.join(" ", lines), "[^ ]*-compatible")
                    .stream()
                    .map(x -> x.split("-")[0])
                    .distinct()
                    .toList();

            items = new int[types.size() * 2];
            for (int k = 0; k < FLOOR_COUNT; k++) {
                var line = lines.get(k);
                if (line.contains("nothing relevant")) {
                    continue;
                }
                for (var gen : getAllMatches(line, "[^ ]* generator")) {
                    int typeId = types.indexOf(gen.replace(" generator", ""));
                    items[typeId * 2] = k;
                }
                for (var gen : getAllMatches(line, "[^ ]*-compatible microchip")) {
                    int typeId = types.indexOf(gen.replace("-compatible microchip", ""));
                    items[typeId * 2 + 1] = k;
                }
            }
        }

        int getItemCount() {
            return items.length;
        }

        int getFloor(int itemId) {
            return items[itemId];
        }

        State move(int itemId, int floor) {
            var state = new State(floor, items.clone());
            state.items[itemId] = floor;
            return state;
        }

        boolean isTerminal() {
            return elevator == TARGET_FLOOR
                    && IntStream.range(0, items.length).allMatch(i -> getFloor(i) == TARGET_FLOOR);
        }

        boolean isSafe() {
            // Check that there are no microchips in danger on any floor
            for (int k = 0; k < FLOOR_COUNT; k++) {
                if (!containsGenerator(k)) {
                    continue;
                }
                for (int i = 0; i < items.length; i += 2) {
                    if (items[i + 1] == k && items[i] != k) {
                        return false; // this microchip (stored at index i+1) would be destroyed
                    }
                }
            }
            return true;
        }

        private boolean containsGenerator(int floor) {
            for (int i = 0; i < items.length; i += 2) {
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
            State other = (State) o;

            return elevator == other.elevator
                    && Arrays.equals(getCanonicalRepresentationOfItems(), other.getCanonicalRepresentationOfItems());
        }

        @Override
        public int hashCode() {
            return elevator + Arrays.hashCode(getCanonicalRepresentationOfItems());
        }

        /**
         * Item pairs are completely interchangeable. This method returns a sorted array in which each element
         * represents an item pair by encoding the position of both the generator and the microchip.
         */
        private int[] getCanonicalRepresentationOfItems() {
            int[] canonical = new int[items.length / 2];
            for (int i = 0; i < items.length; i += 2) {
                canonical[i / 2] = (items[i] << 8) | items[i + 1];
            }
            Arrays.sort(canonical);
            return canonical;
        }

    }

}
