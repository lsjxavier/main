package seedu.foodiebot.commons.core.date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.foodiebot.testutil.Assert.assertThrows;
import static seedu.foodiebot.testutil.TypicalDateRanges.DATE_RANGE_END_TODAY;
import static seedu.foodiebot.testutil.TypicalDateRanges.DATE_RANGE_LONG;
import static seedu.foodiebot.testutil.TypicalDateRanges.DATE_RANGE_MONTH_MAY_2020;
import static seedu.foodiebot.testutil.TypicalDateRanges.DATE_RANGE_SHORT;
import static seedu.foodiebot.testutil.TypicalDateRanges.DATE_RANGE_START_IN_DISTANT_FUTURE;
import static seedu.foodiebot.testutil.TypicalDateRanges.DATE_RANGE_YEAR_2020;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.foodiebot.logic.parser.exceptions.ParseException;

public class DateRangeTest {

    @Test
    public void testDateRange() {

        // check simple creation of DateRange
        assertEquals(DATE_RANGE_SHORT,
                createValidDateRange(LocalDate.of(2020, 5, 3),
                LocalDate.of(2020, 7, 9)));

        // invalid DateRange with default DateRangeStyle
        assertThrows(ParseException.class, () -> createInvalidDateRange(
                LocalDate.of(2020, 12, 31),
                LocalDate.of(2019, 1, 1)));

        // check conversion of invalid DateRange to valid DateRange
        assertEquals(DATE_RANGE_SHORT,
                createValidDateRangeWithStyle(LocalDate.of(2020, 7, 9),
                        LocalDate.of(2020, 5, 3), DateRangeStyle.LENIENT));

        // check simple creation of DateRange
        assertEquals(DATE_RANGE_START_IN_DISTANT_FUTURE,
                createValidDateRange(LocalDate.of(2150, 1, 1),
                        LocalDate.of(2150, 3, 3)));

        // invalid DateRange with strict DateRangeStyle, if start date is after system date
        assertThrows(ParseException.class, () -> createInvalidDateRangeWithStyle(
                LocalDate.of(2150, 1, 1),
                LocalDate.of(2150, 3, 3), DateRangeStyle.STRICT));

        // check strict DateRangeStyle truncating range to current system date.
        assertEquals(DATE_RANGE_END_TODAY,
                createValidDateRangeWithStyle(
                        LocalDate.of(1999, 12, 31),
                        LocalDate.of(2150, 12, 31),
                        DateRangeStyle.STRICT));

        // check creation of year
        assertEquals(DATE_RANGE_YEAR_2020, createValidDateRangeYear(2020));

        // check creation of month
        assertEquals(DATE_RANGE_MONTH_MAY_2020, createValidDateRangeMonthYear(5, 2020));

        // check creation of month
        assertEquals(LocalDate.of(2020, 2, 29),
                createValidDateRangeMonthYear(2, 2020).getEndDate());

        // check creation of month
        assertEquals(LocalDate.of(2019, 2, 28),
                createValidDateRangeMonthYear(2, 2019).getEndDate());
    }

    @Test
    public void rangeContainsDate() {
        // system date falls within DateRange -> returns true
        assertTrue(DATE_RANGE_LONG.contains(LocalDate.now()));

        // system date falls within DateRange starting from distant future -> returns false
        assertFalse(DATE_RANGE_START_IN_DISTANT_FUTURE.contains(LocalDate.now()));

        // system date falls on the end date of a DateRange -> returns true
        assertTrue(DATE_RANGE_END_TODAY.contains(LocalDate.now()));

        // a date falls on the start date of a DateRange -> returns true
        assertTrue(DATE_RANGE_YEAR_2020.contains(LocalDate.of(2020, 1, 1)));
    }


    /** Create date range with specified style. */
    private static DateRange createValidDateRangeWithStyle(LocalDate start, LocalDate end, DateRangeStyle style) {
        try {
            return DateRange.of(start, end, style);
        } catch (ParseException pe) {
            return null;
        }
    }

    /** Create date range with default style. */
    private static DateRange createValidDateRange(LocalDate start, LocalDate end) {
        return createValidDateRangeWithStyle(start, end, DateRangeStyle.SMART);
    }

    /** Attempt to create an invalid date range with specified style. */
    private static DateRange createInvalidDateRangeWithStyle(LocalDate start, LocalDate end, DateRangeStyle style)
            throws ParseException {
        return DateRange.of(start, end, style);
    }

    /** Attempt to create an invalid date range with default style. */
    private static DateRange createInvalidDateRange(LocalDate start, LocalDate end) throws ParseException {
        return createInvalidDateRangeWithStyle(start, end, DateRangeStyle.SMART);
    }

    /** Create date range of month in year with specified style. */
    private static DateRange createValidDateRangeMonthYearWithStyle(int month, int year, DateRangeStyle style) {
        try {
            return DateRange.ofMonth(month, year, style);
        } catch (ParseException pe) {
            pe.printStackTrace();
            return null;
        }
    }

    /** Create date range of month in year with default style. */
    private static DateRange createValidDateRangeMonthYear(int month, int year) {
        return createValidDateRangeMonthYearWithStyle(month, year, DateRangeStyle.SMART);
    }

    /** Create date range of month in year with specified style. */
    private static DateRange createValidDateRangeYearWithStyle(int year, DateRangeStyle style) {
        try {
            return DateRange.ofYear(year, style);
        } catch (ParseException pe) {
            pe.printStackTrace();
            return null;
        }
    }

    /** Create date range of month in year with default style. */
    private static DateRange createValidDateRangeYear(int year) {
        return createValidDateRangeYearWithStyle(year, DateRangeStyle.SMART);
    }

}
