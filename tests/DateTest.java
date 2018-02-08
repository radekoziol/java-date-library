import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DateTest {

    @Test
    void dayIsWithinMonth() {
    }

    @Test
    void isLaterThan() {

        assertTrue(new Date("2002-12-11").isLaterThan(new Date("2001-12-11")));
        assertTrue(new Date("2001-12-14").isLaterThan(new Date("2001-12-11")));
        assertTrue(new Date("2001-12-13").isLaterThan(new Date("2001-11-11")));

        //Leap Year cases
        assertTrue(new Date("2012-02-29").isLaterThan(new Date("2012-02-28")));

    }

    @Test
    void shiftDate() {

        assertTrue(new Date("2001-12-11").equals(new Date("2001-11-11").shiftDate(30)));
        assertTrue(new Date("2001-03-10").equals(new Date("2001-02-07").shiftDate(31)));
        assertTrue(new Date("2002-03-10").equals(new Date("2001-02-07").shiftDate(31 + 365)));
        assertTrue(new Date("2001-04-11").equals(new Date("2001-01-01").shiftDate(100)));

        // Leap Years cases
        assertTrue(new Date("2012-02-29").equals(new Date("2012-02-01").shiftDate(28    )));
        assertTrue(new Date("2013-12-11").equals(new Date("2012-12-11").shiftDate(366)));
        assertTrue(new Date("2014-03-10").equals(new Date("2012-02-07").shiftDate(31 + 366 + 365)));

    }

    @Test
    void getCurrentDate() {
    }

    @Test
    void dayDifference() {


        assertTrue(Date.dayDifference
                (new Date("2001-12-11"),(new Date("2001-11-11"))) == -30);
        assertTrue(Date.dayDifference
                (new Date("2002-03-10"),(new Date("2001-02-07"))) == -31 - 365);
        assertTrue(Date.dayDifference
                (new Date("2001-04-11"),(new Date("2001-01-01"))) == -100);

//        // Leap Years cases
        assertTrue(Date.dayDifference
                (new Date("2012-02-29"),(new Date("2012-02-01"))) == -28);
        assertTrue(Date.dayDifference
                (new Date("2013-02-11"),(new Date("2012-02-11"))) == -366);
        assertTrue(Date.dayDifference
                (new Date("2014-03-10"),(new Date("2012-02-07"))) == -31 - 366 - 365);


    }
}