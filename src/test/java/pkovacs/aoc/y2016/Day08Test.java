package pkovacs.aoc.y2016;

import org.junit.jupiter.api.Test;

class Day08Test extends DayTestBase {

    @Test
    public void test() {
        Day08.main(null);
        assertSolution1("119");
        assertSolution2("ZFHFSFOGPO");
    }

}
