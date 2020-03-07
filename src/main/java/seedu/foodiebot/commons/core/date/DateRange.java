package seedu.foodiebot.commons.core.date;

import static seedu.foodiebot.commons.core.Messages.MESSAGE_INVALID_DATE;

import java.time.LocalDate;
import java.time.Month;
import java.util.stream.Stream;

import seedu.foodiebot.logic.parser.exceptions.ParseException;

/** An encapsulation of a date range represented by a Stream of LocalDate objects. */
public class DateRange {
    private static final LocalDate TODAY = LocalDate.now();
    private static final LocalDate MIN_DATE = LocalDate.of(1970, 1, 1);
    private static final LocalDate MAX_DATE = LocalDate.of(2199, 12, 31);

    private final Stream<LocalDate> dateRange;

    /** Constructor of the DateRange object. */
    public DateRange(Stream<LocalDate> dateRange) {
        this.dateRange = dateRange;
    }

    /**
     * Generates a new DateRange object with range between the start and end dates supplied.
     * @param startDate The start date of the range.
     * @param endDate The end date of the range.
     * @return a DateRange object.
     * @throws ParseException If the end date occurs before the start date.
     */
    public static DateRange of(LocalDate startDate, LocalDate endDate) throws ParseException {
        try {
            return new DateRange(startDate.datesUntil(endDate.plusDays(1)));
        } catch (IllegalArgumentException iae) {
            throw new ParseException(MESSAGE_INVALID_DATE);
        }
    }

    /** Overloaded constructor to generate a new DateRange object if the start and end dates
     * are represented as a String. {@code throws} a ParseException if any date supplied is
     * in an invalid format as described in DateFormatter.*/
    public static DateRange of(String startString, String endString) throws ParseException {
        LocalDate startDate = DateFormatter.formatDate(startString);
        LocalDate endDate = DateFormatter.formatDate(endString);
        return DateRange.of(startDate, endDate);
    }

    /**
     * Generates a new DateRange object with range between the first and last day of the supplied month.
     * @param month The month of the range.
     * @return a DateRange object.
     * @throws ParseException If the end date occurs before the start date.
     */
    public static DateRange ofMonth(int month) throws ParseException {
        try {
            int year = TODAY.getYear();
            LocalDate startDate = LocalDate.of(year, month, 1);
            LocalDate endDate = LocalDate.of(year, month, Month.of(month).length(TODAY.isLeapYear()));
            return new DateRange(startDate.datesUntil(endDate.plusDays(1)));
        } catch (IllegalArgumentException iae) {
            throw new ParseException(MESSAGE_INVALID_DATE);
        }
    }

    /**
     * Generates a new DateRange object with range between the first and last day of the supplied year.
     * @param year The year of the range.
     * @return a DateRange object.
     * @throws ParseException If the end date occurs before the start date.
     */
    public static DateRange ofYear(int year) throws ParseException {
        try {
            LocalDate startDate = LocalDate.of(year, 1, 1);
            LocalDate endDate = LocalDate.of(year, 12, 31);
            return new DateRange(startDate.datesUntil(endDate.plusDays(1)));
        } catch (IllegalArgumentException iae) {
            throw new ParseException(MESSAGE_INVALID_DATE);
        }
    }

    /**
     * Generates a new DateRange object representing an extended period of time.
     * @return a DateRange object.
     */
    public static DateRange generate() {
        return new DateRange(MIN_DATE.datesUntil(MAX_DATE.plusDays(1)));
    }

    /** Gets the start date of the DateRange. */
    public LocalDate getStartDate() {
        return dateRange.findFirst().get();
    }

    /** Gets the end date of the DateRange. */
    public LocalDate getEndDate() {
        return dateRange.reduce((a, b) -> b).get();
    }

}
