package pkovacs.aoc.y2016;

import org.junit.jupiter.api.Test;

class Day17Test extends DayTestBase {

    @Test
    public void test() {
        Day17.main(null);
        assertSolution1("DDURRLRRDD");
        assertSolution2("436");
    }

}
