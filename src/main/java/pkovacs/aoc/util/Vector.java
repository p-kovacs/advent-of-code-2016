package pkovacs.aoc.util;

/**
 * Represents a 2-dimensional immutable vector with long integer coordinates and Manhattan distance.
 * <p>
 * The coordinates are interpreted as usual in Math: (0,1) means "north", (0,-1) means "south",
 * (1,0) means "east", and (0,1) means "west".
 */
public record Vector(long x, long y) {

    public Vector add(long dx, long dy) {
        return new Vector(x + dx, y + dy);
    }

    public Vector add(Vector v) {
        return new Vector(x + v.x, y + v.y);
    }

    public static Vector add(Vector a, Vector b) {
        return new Vector(a.x + b.x, a.y + b.y);
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
        return new Vector(-x, -y);
    }

    public Vector rotateRight() {
        return new Vector(y, -x);
    }

    public Vector rotateLeft() {
        return new Vector(-y, x);
    }

    public long length() {
        return Math.abs(x) + Math.abs(y);
    }

    public long getDistance(Vector v) {
        return getDistance(this, v);
    }

    public static long getDistance(Vector a, Vector b) {
        return sub(a, b).length();
    }

}
