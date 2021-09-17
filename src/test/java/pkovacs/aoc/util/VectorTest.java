package pkovacs.aoc.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VectorTest {

    @Test
    public void test() {
        var a = new Vector(0, 0);
        var b = new Vector(42, 12);

        assertEquals(b, a.add(b));

        a = a.add(b).sub(2, 2);
        assertEquals(new Vector(40, 10), a);
        assertEquals(new Vector(-40, -10), a.neg());

        assertEquals(new Vector(10, -40), a.rotateRight());
        assertEquals(new Vector(-40, -10), a.rotateRight().rotateRight());
        assertEquals(new Vector(-10, 40), a.rotateRight().rotateRight().rotateRight());
        assertEquals(new Vector(40, 10), a.rotateRight().rotateRight().rotateRight().rotateRight());

        assertEquals(new Vector(-10, 40), a.rotateLeft());
        assertEquals(new Vector(-40, -10), a.rotateLeft().rotateLeft());
        assertEquals(new Vector(10, -40), a.rotateLeft().rotateLeft().rotateLeft());
        assertEquals(new Vector(40, 10), a.rotateLeft().rotateLeft().rotateLeft().rotateLeft());

        var c = new Vector(42, 12);
        var d = new Vector(42, 12);
        assertEquals(0, Vector.getDistance(c, d));
        d = d.rotateRight();
        assertEquals(42 + 12 + 30, Vector.getDistance(c, d));
        c = c.rotateLeft();
        assertEquals(c.length() + d.length(), c.getDistance(d));
        c = c.neg();
        assertEquals(0, c.getDistance(d));
    }

}
