package pkovacs.aoc.y2016;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import pkovacs.aoc.util.Bfs;
import pkovacs.aoc.util.InputUtils;
import pkovacs.aoc.util.Tile;

public class Day22 {

    private static record Storage(int used, int avail) {}

    private static record State(Tile data, Tile hole) {}

    public static void main(String[] args) {
        var lines = InputUtils.readLines("y2016/day22.txt");

        Map<Tile, Storage> map = new HashMap<Tile, Storage>();
        for (int i = 2; i < lines.size(); i++) {
            var line = lines.get(i);
            var parts = InputUtils.scan(line, "/dev/grid/node-x%d-y%d *%dT *%dT *%dT.*");
            var tile = new Tile(parts.get(1).asInt(), parts.get(0).asInt());
            var storage = new Storage(parts.get(3).asInt(), parts.get(4).asInt());
            map.put(tile, storage);
        }

        int rowCount = map.keySet().stream().mapToInt(Tile::row).max().getAsInt() + 1;
        int colCount = map.keySet().stream().mapToInt(Tile::col).max().getAsInt() + 1;

        // For part 2, based on the analysis of the data, we can assume that there is only one "hole" (empty storage
        // node), and each feasible step moves data from and adjacent node to the hole. That is, each step moves the
        // hole to and adjacent position.
        // However, we also have "walls" to consider. The "used" amount of most nodes is not more than the minimum
        // capacity of nodes, so their data can be moved freely. The other nodes are considered "walls", and we assume
        // that their data need not be moved at all. Except for walls, however, the hole can be moved freely, so we
        // don't need to track the data amounts, only the positions of the hole and the requested data.

        // Collect walls
        int minCapacity = map.values().stream().mapToInt(st -> st.used() + st.avail()).min().getAsInt();
        Set<Tile> walls = map.entrySet().stream()
                .filter(entry -> entry.getValue().used() > minCapacity)
                .map(Entry::getKey)
                .collect(Collectors.toSet());

        // Find initial positions of the data and the hole tiles
        var dataStart = new Tile(0, colCount - 1);
        var holeStart = map.entrySet().stream()
                .filter(entry -> entry.getValue().used() == 0)
                .map(Entry::getKey)
                .findFirst().get();

        // Start BFS search to find an optimal sequence of steps
        var result = Bfs.run(new State(dataStart, holeStart),
                state -> {
                    boolean alreadyAdjacent = areAdjacent(state.data(), state.hole());
                    var nextStates = new ArrayList<State>();
                    for (var newHole : state.hole().getFourNeighbors()) {
                        if (!newHole.isValid(rowCount, colCount) || walls.contains(newHole)) {
                            continue;
                        }
                        var newData = newHole.equals(state.data()) ? state.hole() : state.data();

                        // Heuristic to prune search space: once the hole tile becomes adjacent to the data tile
                        // (i.e. one of its 8 neighbor tiles), they should remain adjacent. There is no reason to
                        // increase their distance. This heuristic results in ~130 times fewer nodes visited and
                        // more than 50 times speed-up.
                        if (alreadyAdjacent && !areAdjacent(newData, newHole)) {
                            continue;
                        }
                        nextStates.add(new State(newData, newHole));
                    }
                    return nextStates;
                },
                state -> new Tile(0, 0).equals(state.data())
        );

        System.out.println("Part 1: " + countViablePairs(map));
        System.out.println("Part 2: " + result.get().getDist());
    }

    private static boolean areAdjacent(Tile t1, Tile t2) {
        return Math.abs(t1.row() - t2.row()) <= 1 && Math.abs(t1.col() - t2.col()) <= 1;
    }

    private static int countViablePairs(Map<Tile, Storage> map) {
        int cnt = 0;
        for (Tile t1 : map.keySet()) {
            for (Tile t2 : map.keySet()) {
                if (isViablePair(map, t1, t2)) {
                    cnt++;
                }
            }
        }
        return cnt;
    }

    private static boolean isViablePair(Map<Tile, Storage> map, Tile t1, Tile t2) {
        return !t1.equals(t2) && map.get(t1).used() > 0 && map.get(t1).used() <= map.get(t2).avail();
    }

}
