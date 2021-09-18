package pkovacs.aoc.y2016;

import org.junit.jupiter.api.Test;

class Day16Test extends DayTestBase {

    @Test
    public void test() {
        Day16.main(null);
        assertSolution1("10111110010110110");
        assertSolution2("01101100001100100");
    }

}
