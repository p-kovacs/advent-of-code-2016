package pkovacs.aoc.y2016;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import pkovacs.aoc.alg.Bfs;
import pkovacs.aoc.util.InputUtils;

import static java.util.stream.Collectors.toList;

public class Day11 {

    private static final int FLOOR_COUNT = 4;

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
            for (int move : new int[] { +1, -1 }) {
                if (st.elevator + move < 0 || st.elevator + move >= FLOOR_COUNT) {
                    continue;
                }

                State base = new State(st);
                base.elevator = st.elevator + move;

                // Bring one item
                for (var item : st.items.get(st.elevator)) {
                    var nextState = new State(base);
                    nextState.items.get(nextState.elevator).add(item);
                    nextState.items.get(st.elevator).remove(item);
                    if (nextState.isValid()) {
                        nextStates.add(nextState);
                    }
                }

                // Bring two items
                for (var item1 : st.items.get(st.elevator)) {
                    for (var item2 : st.items.get(st.elevator)) {
                        if (item1.compareTo(item2) <= 0) {
                            continue;
                        }
                        var nextState = new State(base);
                        nextState.items.get(nextState.elevator).add(item1);
                        nextState.items.get(nextState.elevator).add(item2);
                        nextState.items.get(st.elevator).remove(item1);
                        nextState.items.get(st.elevator).remove(item2);
                        if (nextState.isValid()) {
                            nextStates.add(nextState);
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

//        for (var st : result.getPath()) {
//            System.out.println(st);
//        }

        return (int) result.getDist();
    }

    private static class State {

        int elevator;
        final List<Set<String>> items;

        State() {
            elevator = 0;
            items = new ArrayList<>();
            for (int i = 0; i <= FLOOR_COUNT; i++) {
                items.add(new HashSet<>());
            }
        }

        State(State st) {
            elevator = st.elevator;
            items = new ArrayList<>();
            for (int i = 0; i <= FLOOR_COUNT; i++) {
                items.add(new HashSet<>(st.items.get(i)));
            }
        }

        State(List<String> lines) {
            this();

            for (int i = 0; i < FLOOR_COUNT; i++) {
                var line = lines.get(i);
                if (line.contains("nothing relevant")) {
                    continue;
                }
                for (var gen : getAllMatches(line, "[^ ]* generator")) {
                    items.get(i).add(gen.replace(" generator", "G"));
                }
                for (var gen : getAllMatches(line, "[^ ]*-compatible microchip")) {
                    items.get(i).add(gen.replace("-compatible microchip", "M"));
                }
            }
        }

        boolean isTerminal() {
            return IntStream.range(0, FLOOR_COUNT - 1).allMatch(i -> items.get(i).isEmpty());
        }

        @Override
        public String toString() {
            var sb = new StringBuilder();
            for (int i = FLOOR_COUNT - 1; i >= 0; i--) {
                sb.append(String.format("F%d %s %s\n", i + 1, (i == elevator ? 'E' : ' '),
                        String.join(", ", items.get(i))));
            }
            return sb.toString();
        }

        private static List<String> getAllMatches(String str, String regex) {
            List<String> matches = new ArrayList<String>();
            Matcher m = Pattern.compile("(" + regex + ")").matcher(str);
            while (m.find()) {
                matches.add(m.group(1));
            }
            return matches;
        }

        boolean isValid() {
            // Check that there are no microchips in danger on any floor
            for (int i = 0; i < FLOOR_COUNT; i++) {
                var itemsInFloor = items.get(i);
                if (itemsInFloor.stream().noneMatch(x -> x.charAt(x.length() - 1) == 'G')) {
                    continue;
                }
                var microchipInDanger = itemsInFloor.stream()
                        .filter(x -> x.charAt(x.length() - 1) == 'M')
                        .anyMatch(mc -> !itemsInFloor.contains(mc.replace('M', 'G')));
                if (microchipInDanger) {
                    return false;
                }
            }
            return true;
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
            return elevator == state.elevator && Objects.equals(items, state.items);
        }

        @Override
        public int hashCode() {
            return Objects.hash(elevator, items);
        }

    }

}
