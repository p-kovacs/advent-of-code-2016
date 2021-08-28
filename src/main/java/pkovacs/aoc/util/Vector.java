package pkovacs.aoc.util;

import java.util.Objects;

/**
 * Represents a 2-dimensional immutable vector with integer (long) coordinates and Manhattan distance.
 * <p>
 * The coordinates are interpreted as usual in Math: (0,1) means "north", (0,-1) means "south",
 * (1,0) means "east", and (0,1) means "west".
 */
public class Vector {

    public final long x;
    public final long y;

    public Vector(long x, long y) {
        this.x = x;
        this.y = y;
    }

    public static Vector of(long x, long y) {
        return new Vector(x, y);
    }

    public Vector add(long dx, long dy) {
        return Vector.of(x + dx, y + dy);
    }

    public Vector add(Vector v) {
        return Vector.of(x + v.x, y + v.y);
    }

    public static Vector add(Vector a, Vector b) {
        return Vector.of(a.x + b.x, a.y + b.y);
    }

    public Vector sub(long dx, long dy) {
        return add(-dx, -dy);
    }

    public Vector sub(Vector v) {
        return add(-v.x, -v.y);
    }

    public static Vector sub(Vector a, Vector b) {
        return add(a, b.neg());
    }

    public Vector neg() {
        return Vector.of(-x, -y);
    }

    public Vector rotateRight() {
        return Vector.of(y, -x);
    }

    public Vector rotateLeft() {
        return Vector.of(-y, x);
    }

    public long length() {
        return getDistance();
    }

    public long getDistance() {
        return Math.abs(x) + Math.abs(y);
    }

    public long getDistance(Vector v) {
        return getDistance(this, v);
    }

    public static long getDistance(Vector a, Vector b) {
        return sub(a, b).length();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Vector other = (Vector) o;
        return x == other.x && y == other.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

}
