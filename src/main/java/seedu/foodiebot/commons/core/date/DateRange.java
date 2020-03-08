package seedu.foodiebot.commons.core.date;

import static seedu.foodiebot.commons.core.Messages.MESSAGE_INVALID_DATE_RANGE;
import static seedu.foodiebot.commons.core.Messages.MESSAGE_INVALID_PREFIX;

import java.time.LocalDate;
import java.time.Month;
import java.util.Objects;

import seedu.foodiebot.logic.parser.exceptions.ParseException;

/** An abstraction of a date range represented as a tuple. */
public class DateRange {

    private final LocalDate startDate;
    private final LocalDate endDate;

    /** Constructor of the DateRange object. */
    private DateRange(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Generates a new DateRange object with the specified style.
     *
     * If the style is LENIENT, this will return a valid date range between the two supplied dates regardless
     * of its position when passed into the method.
     *
     * If the style is SMART, this will return a valid date range only if the following conditions are met:
     *     - The end date must not be before the start date.
     *
     * If the style is STRICT, this will return a valid date range only if the following conditions are met:
     *     - The end date must not be before the start date.
     *     - The start date must not be after the current system date.
     * In addition, if the end date occurs after the current system date, the system date is used as the end
     * date instead.
     *
     * @param startDate The start date of the range.
     * @param endDate The end date of the range.
     * @param style The style to set the range to.
     * @return a DateRange object.
     * @throws ParseException If the attempt to create a date range does not conform to the requirements
     * of the specified style.
     */
    public static DateRange of(LocalDate startDate, LocalDate endDate, DateRangeStyle style)
            throws ParseException {

        switch(style) {
        case LENIENT:
            if (endDate.isBefore(startDate)) {
                return new DateRange(endDate, startDate);
            } else {
                return new DateRange(startDate, endDate);
            }

        case SMART:
            if (endDate.isBefore(startDate)) {
                break;
            }
            return new DateRange(startDate, endDate);

        case STRICT:
            if (endDate.isBefore(startDate) || startDate.isAfter(DefiniteDate.TODAY)) {
                break;
            }

            if (endDate.isAfter(DefiniteDate.TODAY)) {
                return new DateRange(startDate, DefiniteDate.TODAY);
            } else {
                return new DateRange(startDate, endDate);
            }

        default:
            break;
        }

        throw new ParseException(MESSAGE_INVALID_DATE_RANGE);
    }

    /** Generates a new DateRange object. */
    public static DateRange of(LocalDate startDate, LocalDate endDate) throws ParseException {
        return DateRange.of(startDate, endDate, DateRangeStyle.SMART);
    }

    /** Overloaded constructor to generate a new DateRange object if the start and end dates
     * are represented as a String. {@code throws} a ParseException if any date supplied is
     * in an invalid format as described in DateFormatter.*/
    public static DateRange of(String startString, String endString, DateRangeStyle style) throws ParseException {
        LocalDate startDate = DateFormatter.formatDate(startString);
        LocalDate endDate = DateFormatter.formatDate(endString);
        return DateRange.of(startDate, endDate, style);
    }

    /** Overloaded constructor to generate a new DateRange object if the start and end dates
     * are represented as a String. {@code throws} a ParseException if any date supplied is
     * in an invalid format as described in DateFormatter.*/
    public static DateRange of(String startString, String endString) throws ParseException {
        return DateRange.of(startString, endString, DateRangeStyle.SMART);
    }

    /** Overloaded constructor to generate a new DateRange object if only one one date is supplied,
     * with the type whether it is a start or an end date.*/
    public static DateRange of(String dateString, ConceptualDate conceptualDate, DateRangeStyle style)
            throws ParseException {

        if (conceptualDate.equals(ConceptualDate.START_DATE)) {
            LocalDate startDate = DateFormatter.formatDate(dateString);
            return DateRange.of(startDate, DefiniteDate.TODAY, style);

        } else if (conceptualDate.equals(ConceptualDate.END_DATE)) {
            LocalDate endDate = DateFormatter.formatDate(dateString);
            return DateRange.of(DefiniteDate.MIN_DATE, endDate, style);
        }
        throw new ParseException(MESSAGE_INVALID_PREFIX);
    }

    /** Overloaded constructor to generate a new DateRange object if only one one date is supplied,
     * with the type whether it is a start or an end date.*/
    public static DateRange of(String dateString, ConceptualDate conceptualDate) throws ParseException {
        return DateRange.of(dateString, conceptualDate, DateRangeStyle.SMART);
    }

    /**
     * Generates a new DateRange object with range between the first and last day of the supplied month and year.
     */
    public static DateRange ofMonth(int month, int year, DateRangeStyle style) throws ParseException {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = LocalDate.of(year, month, Month.of(month).length(startDate.isLeapYear()));
        return DateRange.of(startDate, endDate, style);
    }

    /**
     * Generates a new DateRange object with range between the first and last day of the supplied month and year.
     */
    public static DateRange ofMonth(int month, int year) throws ParseException {
        return DateRange.ofMonth(month, year, DateRangeStyle.SMART);
    }

    /**
     * Generates a new DateRange object with range between the first and last day of the supplied month,
     * with the year as the current system year.
     */
    public static DateRange ofMonth(int month, DateRangeStyle style) throws ParseException {
        int year = DefiniteDate.TODAY.getYear();
        return DateRange.ofMonth(month, year, style);
    }

    /**
     * Generates a new DateRange object with range between the first and last day of the supplied month,
     * with the year as the current system year.
     */
    public static DateRange ofMonth(int month) throws ParseException {
        return DateRange.ofMonth(month, DateRangeStyle.SMART);
    }

    /**
     * Generates a new DateRange object with range between the first and last day of the supplied year.
     */
    public static DateRange ofYear(int year, DateRangeStyle style) throws ParseException {
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year, 12, 31);
        return DateRange.of(startDate, endDate, style);
    }

    /**
     * Generates a new DateRange object with range between the first and last day of the supplied year.
     */
    public static DateRange ofYear(int year) throws ParseException {
        return DateRange.ofYear(year, DateRangeStyle.SMART);
    }

    /** Generates a new DateRange object with range as a single day. */
    public static DateRange ofSingle(LocalDate date) throws ParseException {
        return DateRange.of(date, date, DateRangeStyle.SMART);
    }

    /**
     * Generates a new DateRange object representing an extended period of time.
     * @return a DateRange object.
     */
    public static DateRange generate() throws ParseException {
        return DateRange.of(DefiniteDate.MIN_DATE, DefiniteDate.MAX_DATE);
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
