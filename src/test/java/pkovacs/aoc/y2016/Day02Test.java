package pkovacs.aoc.y2016;

import org.junit.jupiter.api.Test;

class Day02Test extends DayTestBase {

    @Test
    public void test() {
        Day02.main(null);
        assertSolution1("65556");
        assertSolution2("CB779");
    }

}
