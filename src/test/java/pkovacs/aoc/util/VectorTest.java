package pkovacs.aoc.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VectorTest {

    @Test
    public void test() {
        var a = Vector.of(0, 0);
        var b = Vector.of(42, 12);

        assertEquals(b, a.add(b));

        a = a.add(b).sub(2, 2);
        assertEquals(Vector.of(40, 10), a);
        assertEquals(Vector.of(-40, -10), a.neg());

        assertEquals(Vector.of(10, -40), a.rotateRight());
        assertEquals(Vector.of(-40, -10), a.rotateRight().rotateRight());
        assertEquals(Vector.of(-10, 40), a.rotateRight().rotateRight().rotateRight());
        assertEquals(Vector.of(40, 10), a.rotateRight().rotateRight().rotateRight().rotateRight());

        assertEquals(Vector.of(-10, 40), a.rotateLeft());
        assertEquals(Vector.of(-40, -10), a.rotateLeft().rotateLeft());
        assertEquals(Vector.of(10, -40), a.rotateLeft().rotateLeft().rotateLeft());
        assertEquals(Vector.of(40, 10), a.rotateLeft().rotateLeft().rotateLeft().rotateLeft());

        var c = Vector.of(42, 12);
        var d = Vector.of(42, 12);
        assertEquals(0, Vector.getDistance(c, d));
        d = d.rotateRight();
        assertEquals(42 + 12 + 30, Vector.getDistance(c, d));
        c = c.rotateLeft();
        assertEquals(c.length() + d.length(), Vector.getDistance(c, d));
        c = c.neg();
        assertEquals(0, Vector.getDistance(c, d));
    }

}
