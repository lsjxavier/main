package seedu.foodiebot.commons.core.date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.foodiebot.testutil.Assert.assertThrows;

import java.time.LocalDate;
import java.time.Month;

import org.junit.jupiter.api.Test;

import seedu.foodiebot.logic.parser.exceptions.ParseException;

public class DateFormatterTest {

    @Test
    public void formatDates() {

        // format date with valid pattern '-' -> equals
        assertEquals(LocalDate.of(2019, 6, 15), formatValidDate("15-6-2019"));

        // format date with valid pattern '.' -> equals
        assertEquals(LocalDate.of(2008, 1, 23), formatValidDate("23.1.2008"));

        // format date with valid pattern '/' -> equals
        assertEquals(LocalDate.of(1884, 2, 6), formatValidDate("6/2/1884"));

        // format date with invalid pattern -> throw
        assertThrows(ParseException.class, () -> formatInvalidDate("13,5,2000"));

        // format date with valid 2-digit date, compare with 4-digit version -> equals
        assertEquals(LocalDate.of(2020, 5, 19), formatValidDate("19/5/20"));

        // format date with 4-digit date (but in m/d/y), compare with 4-digit version (d/m/y) -> not equals
        assertNotEquals(LocalDate.of(2020, 3, 4), formatValidDate("3/4/2020"));

        // format invalid date (31/6/2020) -> throws
        assertThrows(ParseException.class, () -> formatInvalidDate("31/6/2020"));

        // format invalid date (29/2/2019) -> throws
        assertThrows(ParseException.class, () -> formatInvalidDate("29/2/2019"));

        // format invalid date (44/22/2020) -> throws
        assertThrows(ParseException.class, () -> formatInvalidDate("44/22/2020"));

    }

    @Test
    public void formatMonths() {

        // format month "jan" -> equals
        assertEquals(Month.JANUARY.getValue(), formatValidMonth("jan"));

        // format month "may" -> equals
        assertEquals(Month.MAY.getValue(), formatValidMonth("May"));

        // format month "september" -> equals
        assertEquals(Month.SEPTEMBER.getValue(), formatValidMonth("september"));

        // format month "test" -> throws
        assertThrows(ParseException.class, () -> formatInvalidMonth("test"));

        // format month "maybe" -> throws
        assertThrows(ParseException.class, () -> formatInvalidMonth("maybe"));

    }

    /** Format a valid date. */
    private static LocalDate formatValidDate(String string) {
        try {
            return DateFormatter.formatDate(string);
        } catch (ParseException pe) {
            return null;
        }
    }

    /** Attempt to format an invalid date. */
    private static LocalDate formatInvalidDate(String string) throws ParseException {
        return DateFormatter.formatDate(string);
    }

    /** Format a valid month. */
    private static int formatValidMonth(String string) {
        try {
            return DateFormatter.formatMonth(string);
        } catch (ParseException pe) {
            return 0;
        }
    }

    /** Attempt to format an invalid month. */
    private static int formatInvalidMonth(String string) throws ParseException {
        return DateFormatter.formatMonth(string);
    }




}
