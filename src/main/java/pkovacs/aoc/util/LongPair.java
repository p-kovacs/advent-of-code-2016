package pkovacs.aoc.util;

import java.util.Objects;

/**
 * Represents an immutable pair of long values.
 * <p>
 * This class can be slightly faster or more convenient than using {@link org.apache.commons.lang3.tuple.Pair}.
 */
public class LongPair {

    public final long a;
    public final long b;

    private LongPair(long a, long b) {
        this.a = a;
        this.b = b;
    }

    public static LongPair of(long a, long b) {
        return new LongPair(a, b);
    }

    public long a() {
        return a;
    }

    public long b() {
        return b;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LongPair other = (LongPair) o;
        return a == other.a && b == other.b;
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b);
    }

    @Override
    public String toString() {
        return "(" + a + "," + b + ")";
    }

}
