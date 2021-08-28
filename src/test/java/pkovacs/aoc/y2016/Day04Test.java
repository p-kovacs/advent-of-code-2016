package pkovacs.aoc.y2016;

import org.junit.jupiter.api.Test;

class Day04Test extends DayTestBase {

    @Test
    public void test() {
        Day04.main(null);
        assertSolution1("361724");
        assertSolution2("482");
    }

}
