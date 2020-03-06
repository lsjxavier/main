package seedu.foodiebot.logic.parser;

import static seedu.foodiebot.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.foodiebot.logic.parser.CliSyntax.PREFIX_DATE_BY_MONTH;
import static seedu.foodiebot.logic.parser.CliSyntax.PREFIX_DATE_BY_YEAR;
import static seedu.foodiebot.logic.parser.CliSyntax.PREFIX_FROM_DATE;
import static seedu.foodiebot.logic.parser.CliSyntax.PREFIX_TO_DATE;

import java.time.LocalDate;
import java.time.Month;
import java.util.stream.Stream;

import seedu.foodiebot.logic.commands.ReportCommand;
import seedu.foodiebot.logic.parser.exceptions.ParseException;

/** Parses a date range into a Stream of LocalDate objects*/
public class DateRangeFormatter {
    private static final LocalDate TODAY = LocalDate.now();
    private static final LocalDate MIN_DATE = LocalDate.of(1970, 1, 1);
    private static final LocalDate MAX_DATE = LocalDate.of(2999, 12, 31);

    /**
     * Takes in an ArgumentMultimap object, extracts the date field(s) based on the prefix supplied
     * and returns a Stream of LocalDate objects. If an invalid combination of prefixes are supplied,
     * throws a ParseException instead.
     * @param argMultimap A map of arguments from the command parser.
     * @return A Stream of LocalDate objects from the start date to the end date specified.
     * @throws ParseException If an invalid combination of prefixes are supplied, or if an invalid date format.
     * @throws IllegalArgumentException If the date range is invalid, i.e. end date is before start date.
     * is supplied.
     */
    public static Stream<LocalDate> getDateRange(ArgumentMultimap argMultimap)
            throws ParseException, IllegalArgumentException {

        // If no parameters are specified
        if (argMultimap.getPreamble().isEmpty()) {
            return getDateRange(MIN_DATE, MAX_DATE);
        }

        if (arePrefixesPresent(argMultimap, PREFIX_DATE_BY_MONTH)
                && !areAnyPrefixesPresent(argMultimap, PREFIX_DATE_BY_YEAR, PREFIX_FROM_DATE, PREFIX_TO_DATE)) {
            String month = getArgString(argMultimap, PREFIX_DATE_BY_MONTH);
            return getDateRangeInMonth(month);

        } else if (arePrefixesPresent(argMultimap, PREFIX_DATE_BY_YEAR)
                && !areAnyPrefixesPresent(argMultimap, PREFIX_DATE_BY_MONTH, PREFIX_FROM_DATE, PREFIX_TO_DATE)) {
            String year = getArgString(argMultimap, PREFIX_DATE_BY_YEAR);
            return getDateRangeInYear(year);

        } else if (arePrefixesPresent(argMultimap, PREFIX_FROM_DATE, PREFIX_TO_DATE)
                && !areAnyPrefixesPresent(argMultimap, PREFIX_DATE_BY_MONTH, PREFIX_DATE_BY_YEAR)) {
            String start = getArgString(argMultimap, PREFIX_FROM_DATE);
            String end = getArgString(argMultimap, PREFIX_TO_DATE);
            return getDateRange(start, end);

        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReportCommand.MESSAGE_USAGE));
        }
    }

    public static Stream<LocalDate> getDateRange(String start, String end)
            throws ParseException, IllegalArgumentException {

        LocalDate startDate = DateFormatter.formatDate(start);
        LocalDate endDate = DateFormatter.formatDate(end);
        return startDate.datesUntil(endDate.plusDays(1));
    }

    public static Stream<LocalDate> getDateRange(LocalDate startDate, LocalDate endDate)
            throws IllegalArgumentException {

        return startDate.datesUntil(endDate.plusDays(1));
    }

    public static Stream<LocalDate> getDateRangeInYear(String year)
            throws ParseException, IllegalArgumentException {

        int yearValue = DateFormatter.formatYear(year);
        LocalDate startDate = LocalDate.of(yearValue, 1, 1);
        LocalDate endDate = LocalDate.of(yearValue, 12, 31);
        return startDate.datesUntil(endDate.plusDays(1));
    }

    public static Stream<LocalDate> getDateRangeInMonth(String month)
            throws ParseException, IllegalArgumentException {

        int monthValue = DateFormatter.formatMonth(month);
        LocalDate startDate = LocalDate.of(TODAY.getYear(), monthValue, 1);
        LocalDate endDate = LocalDate.of(TODAY.getYear(), monthValue, Month.of(monthValue).length(TODAY.isLeapYear()));
        return startDate.datesUntil(endDate.plusDays(1));
    }

    /** Extracts the argument tagged to the given prefix. Throws {@code} ParseException if no value is present.*/
    public static String getArgString(ArgumentMultimap argMultimap, Prefix prefix) throws ParseException {
        return argMultimap.getValue(prefix)
                .orElseThrow(() -> new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReportCommand.MESSAGE_USAGE)));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes)
                .allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Returns true if at least one of the prefixes contains an {@code Optional} value in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean areAnyPrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes)
                .anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
