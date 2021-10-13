package pkovacs.aoc.y2016;

import org.junit.jupiter.api.Test;

class Day24Test extends DayTestBase {

    @Test
    public void test() {
        Day24.main(null);
        assertSolution1("448");
        assertSolution2("672");
    }

}
