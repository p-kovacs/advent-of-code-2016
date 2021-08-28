package pkovacs.aoc.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class LongPairTest {

    @Test
    public void test() {
        var p = LongPair.of(123_456_789_000L, 42);
        var q = LongPair.of(42, 123_456_789_000L);
        var r = LongPair.of(42, 123_456_789_000L);

        assertNotEquals(p, q);
        assertEquals(q, r);

        assertEquals(123456789000L, p.a);
        assertEquals(p.a, q.b);
        assertEquals(p.b, q.a);
    }

}
