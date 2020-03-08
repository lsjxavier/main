package seedu.foodiebot.testutil;

import java.time.LocalDate;

import seedu.foodiebot.commons.core.date.DateRange;
import seedu.foodiebot.logic.parser.exceptions.ParseException;

/** Utility class with date ranges. */
public class TypicalDateRanges {
    public static final DateRange DATE_RANGE_YEAR_2020 = createDateRange(
            LocalDate.of(2020, 1, 1),
            LocalDate.of(2020, 12, 31));

    public static final DateRange DATE_RANGE_MONTH_MAY_2020 = createDateRange(
            LocalDate.of(2020, 5, 1),
            LocalDate.of(2020, 5, 31));

    public static final DateRange DATE_RANGE_SHORT = createDateRange(
            LocalDate.of(2020, 5, 3),
            LocalDate.of(2020, 7, 9));

    public static final DateRange DATE_RANGE_LONG = createDateRange(
            LocalDate.of(2020, 1, 1),
            LocalDate.of(2150, 3, 3));

    public static final DateRange DATE_RANGE_START_IN_DISTANT_FUTURE = createDateRange(
            LocalDate.of(2150, 1, 1),
            LocalDate.of(2150, 3, 3));

    public static final DateRange DATE_RANGE_END_TODAY = createDateRange(
            LocalDate.of(1999, 12, 31),
            LocalDate.now());

    /** Creates a date range */
    private static DateRange createDateRange(LocalDate start, LocalDate end) {
        try {
            return DateRange.of(start, end);
        } catch (ParseException pe) {
            pe.printStackTrace();
            return null;
        }
    }

}
