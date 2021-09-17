package pkovacs.aoc.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a hexagonal tile as a record of two int values: row index and column index.
 * Provides methods to get the immediate neighbors of a tile, as well as to traverse a path described by
 * list of directions.
 */
public record HexTile(int row, int col) {

    private static final String[] DIRECTIONS = new String[] { "w", "e", "nw", "ne", "sw", "se" };

    public HexTile getNeighbor(String dir) {
        if ("w".equals(dir)) {
            return new HexTile(row, col - 1);
        } else if ("e".equals(dir)) {
            return new HexTile(row, col + 1);
        } else if ("nw".equals(dir)) {
            return new HexTile(row - 1, col);
        } else if ("ne".equals(dir)) {
            return new HexTile(row - 1, col + 1);
        } else if ("sw".equals(dir)) {
            return new HexTile(row + 1, col - 1);
        } else if ("se".equals(dir)) {
            return new HexTile(row + 1, col);
        }
        throw new IllegalArgumentException();
    }

    public HexTile gotoTile(String path) {
        HexTile current = this;
        for (int i = 0; i < path.length(); i++) {
            if (path.charAt(i) == 'n' || path.charAt(i) == 's') {
                current = current.getNeighbor(path.substring(i, i + 2));
                i++;
            } else {
                current = current.getNeighbor(path.substring(i, i + 1));
            }
        }
        return current;
    }

    public List<HexTile> getNeighbors() {
        return getNeighbors(false);
    }

    public List<HexTile> getNeighbors(boolean includeSelf) {
        var neighbors = new ArrayList<HexTile>();
        if (includeSelf) {
            neighbors.add(this);
        }
        for (var dir : DIRECTIONS) {
            neighbors.add(getNeighbor(dir));
        }
        return neighbors;
    }

}
