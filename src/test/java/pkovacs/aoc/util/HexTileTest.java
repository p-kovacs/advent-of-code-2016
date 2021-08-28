package pkovacs.aoc.util;

import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class HexTileTest {

    @Test
    public void test() {
        var c = HexTile.of(12, 42);
        var d = HexTile.of(42, 12);
        var e = HexTile.of(42, 12);

        assertEquals(12, c.row);
        assertEquals(42, c.col);
        assertNotEquals(c, d);
        assertEquals(d, e);

        assertEquals(c, c.getTile("nwwswee"));
        assertEquals(HexTile.of(13, 42), c.getTile("esew"));

        assertEquals(List.of(HexTile.of(12, 41), HexTile.of(12, 43),
                HexTile.of(11, 42), HexTile.of(11, 43),
                HexTile.of(13, 41), HexTile.of(13, 42)), c.getNeighbors());
        assertEquals(List.of(HexTile.of(12, 42), HexTile.of(12, 41), HexTile.of(12, 43),
                HexTile.of(11, 42), HexTile.of(11, 43),
                HexTile.of(13, 41), HexTile.of(13, 42)), c.getNeighbors(true));
    }

}
