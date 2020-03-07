package seedu.foodiebot.commons.core.date;

import static seedu.foodiebot.commons.core.Messages.MESSAGE_INVALID_DATE;
import static seedu.foodiebot.commons.core.Messages.MESSAGE_INVALID_PREFIX;
import static seedu.foodiebot.logic.parser.CliSyntax.PREFIX_FROM_DATE;
import static seedu.foodiebot.logic.parser.CliSyntax.PREFIX_TO_DATE;

import java.time.LocalDate;
import java.time.Month;
import java.util.Objects;

import seedu.foodiebot.logic.parser.Prefix;
import seedu.foodiebot.logic.parser.exceptions.ParseException;

/** An encapsulation of a date range represented by a LinkedList of LocalDate objects. */
public class DateRange {

    private final LocalDate startDate;
    private final LocalDate endDate;


    /** Constructor of the DateRange object. */
    private DateRange(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Generates a new DateRange object. Also handles invalid date range, e.g. if the end date is
     * before the start date, or if the start date is after the local system date.
     * @param startDate The start date of the range.
     * @param endDate The end date of the range.
     * @return a DateRange object.
     * @throws ParseException If the end date occurs before the start date.
     */
    public static DateRange of(LocalDate startDate, LocalDate endDate) throws ParseException {
        if (endDate.isBefore(startDate) || startDate.isAfter(Dates.TODAY)) {
            throw new ParseException(MESSAGE_INVALID_DATE);
        }
        if (endDate.isAfter(Dates.TODAY)) {
            endDate = Dates.TODAY;
        }
        return new DateRange(startDate, endDate);
    }

    /** Overloaded constructor to generate a new DateRange object if the start and end dates
     * are represented as a String. {@code throws} a ParseException if any date supplied is
     * in an invalid format as described in DateFormatter.*/
    public static DateRange of(String startString, String endString) throws ParseException {
        LocalDate startDate = DateFormatter.formatDate(startString);
        LocalDate endDate = DateFormatter.formatDate(endString);
        return DateRange.of(startDate, endDate);
    }

    /** Overloaded constructor to generate a new DateRange object if only one one date is supplied,
     * with the type whether it is a start or an end date.*/
    public static DateRange of(String dateString, Prefix prefix) throws ParseException {
        if (prefix.equals(PREFIX_FROM_DATE)) {
            LocalDate startDate = DateFormatter.formatDate(dateString);
            return DateRange.of(startDate, Dates.TODAY);
        } else if (prefix.equals(PREFIX_TO_DATE)) {
            LocalDate endDate = DateFormatter.formatDate(dateString);
            return DateRange.of(Dates.MIN_DATE, endDate);
        }
        throw new ParseException(MESSAGE_INVALID_PREFIX);
    }

    /**
     * Generates a new DateRange object with range between the first and last day of the supplied month,
     * with the year as the current system year. {@code throws} ParseException if the end date occurs
     * before the start date.
     */
    public static DateRange ofMonth(int month) throws ParseException {
        int year = Dates.TODAY.getYear();
        return DateRange.ofMonth(month, year);
    }

    /**
     * Generates a new DateRange object with range between the first and last day of the supplied month and year.
     * @param month The month supplied.
     * @param year The year supplied.
     * @return A DateRange object.
     * @throws ParseException If the end date occurs before the start date.
     */
    public static DateRange ofMonth(int month, int year) throws ParseException {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = LocalDate.of(year, month, Month.of(month).length(startDate.isLeapYear()));
        return DateRange.of(startDate, endDate);
    }

    /**
     * Generates a new DateRange object with range between the first and last day of the supplied year.
     * @param year The year of the range.
     * @return A DateRange object.
     * @throws ParseException If the end date occurs before the start date.
     */
    public static DateRange ofYear(int year) throws ParseException {
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year, 12, 31);
        return DateRange.of(startDate, endDate);
    }

    /**
     * Generates a new DateRange object representing an extended period of time.
     * @return a DateRange object.
     */
    public static DateRange generate() throws ParseException {
        return DateRange.of(Dates.MIN_DATE, Dates.TODAY);
    }

    /** Gets the start date of the DateRange. */
    public LocalDate getStartDate() {
        return this.startDate;
    }

    /** Gets the end date of the DateRange. */
    public LocalDate getEndDate() {
        return this.endDate;
    }

    /** Returns a boolean value if the supplied date is present in the DateRange object. */
    public boolean contains(LocalDate otherDate) {
        boolean isEqual = otherDate.isEqual(this.startDate) || otherDate.isEqual(this.endDate);
        boolean isInBetween = otherDate.isAfter(this.startDate) && otherDate.isBefore(this.endDate);
        return isEqual || isInBetween;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DateRange)) {
            return false;
        }

        DateRange otherDateRange = (DateRange) other;
        return otherDateRange.getStartDate().equals(startDate)
                && otherDateRange.getEndDate().equals(endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate);
    }

}
