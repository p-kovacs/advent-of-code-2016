package pkovacs.aoc.y2016;

import java.util.ArrayList;
import java.util.List;

import pkovacs.aoc.util.Backtracking;
import pkovacs.aoc.util.Bfs;
import pkovacs.aoc.util.InputUtils;
import pkovacs.aoc.util.Tile;

public class Day24 {

    public static void main(String[] args) {
        var map = InputUtils.readCharMatrix("day24.txt");

        // Find target tiles
        var targetTiles = findTargetTiles(map);
        int targetCount = targetTiles.size();

        // Find shortest-path distances between target tiles
        int[][] dist = new int[targetCount][targetCount];
        for (int i = 0; i < targetCount; i++) {
            var startTile = targetTiles.get(i);
            var result = Bfs.run(startTile,
                    tile -> tile.neighbors(n -> map[n.row()][n.col()] != '#'));
            for (int j = 0; j < targetCount; j++) {
                dist[i][j] = (int) result.get(targetTiles.get(j)).getDist();
            }
        }

        // Find the best routes considering all permutations of the target tiles (brute-force TSP)
        var permutations = Backtracking.findAll(targetCount - 1, Backtracking::distinct);
        int min1 = Integer.MAX_VALUE;
        int min2 = Integer.MAX_VALUE;
        for (int[] order : permutations) {
            int length = 0;
            int prev = 0;
            for (int k : order) {
                int next = k + 1;
                length += dist[prev][next];
                prev = next;
            }

            min1 = Math.min(min1, length);
            min2 = Math.min(min2, length + dist[prev][0]);
        }

        System.out.println("Part 1: " + min1);
        System.out.println("Part 2: " + min2);
    }

    private static List<Tile> findTargetTiles(char[][] map) {
        List<Tile> tiles = new ArrayList<>();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (Character.isDigit(map[i][j])) {
                    int index = map[i][j] - '0';
                    while (index >= tiles.size()) {
                        tiles.add(null);
                    }
                    tiles.set(index, new Tile(i, j));
                }
            }
        }
        return tiles;
    }

}
