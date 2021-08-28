package pkovacs.aoc.y2016;

import org.junit.jupiter.api.Test;

class Day01Test extends DayTestBase {

    @Test
    public void test() {
        Day01.main(null);
        assertSolution1("241");
        assertSolution2("116");
    }

}
