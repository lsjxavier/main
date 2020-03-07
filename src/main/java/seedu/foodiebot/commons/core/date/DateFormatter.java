package seedu.foodiebot.commons.core.date;

import static seedu.foodiebot.commons.core.Messages.MESSAGE_INVALID_DATE;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import seedu.foodiebot.logic.parser.exceptions.ParseException;

/** Formats an input String into a LocalDate object */
public class DateFormatter {

    /** A list of valid date formats. */
    public static Stream<DateTimeFormatter> dateFormats() {
        return Stream.of(DateTimeFormatter.ofPattern("d.M.uu"),
                DateTimeFormatter.ofPattern("d.M.uuuu"),
                DateTimeFormatter.ofPattern("d-M-uu"),
                DateTimeFormatter.ofPattern("d-M-uuuu"),
                DateTimeFormatter.ofPattern("d/M/uu"),
                DateTimeFormatter.ofPattern("d/M/uuuu")
        );
    }

    /** A list of valid month formats. */
    public static List<String> monthFormats() {
        return List.of("JAN", "FEB", "MAR",
                "APR", "MAY", "JUN",
                "JUL", "AUG", "SEP",
                "OCT", "NOV", "DEC");
    }

    /**
     * Converts a String representation of a date into a LocalDate object.
     * @param dateString the input String to format.
     * @return a LocalDate representation of the input String.
     * @throws ParseException if all possible DateTimeFormatter patterns are unable to format
     * the String into a LocalDate object.
     */
    public static LocalDate formatDate(String dateString) throws ParseException {
        return dateFormats()
                .map(formatter -> formatDateHelper(dateString, formatter.withResolverStyle(ResolverStyle.STRICT)))
                .reduce((a, b) -> a.equals(Optional.empty()) ? b : a)
                .get()
                .orElseThrow(() -> new ParseException(MESSAGE_INVALID_DATE));
    }

    /** A helper function for formatDate() to parse a string to a LocalDate object with the given formatter.
     * Returns a LocalDate object encapsulated in an Optional if successful, otherwise an empty Optional
     * if the parser is unable to parse the date with the given formatter. */
    public static Optional<LocalDate> formatDateHelper(String date, DateTimeFormatter formatter) {
        try {
            LocalDate localDate = LocalDate.parse(date, formatter);
            return Optional.ofNullable(localDate);
        } catch (DateTimeParseException dtpe) {
            return Optional.empty();
        }
    }

    /** Formats the given String month into an integer. Throws a ParseException if the month is not valid. */
    public static int formatMonth(String month) throws ParseException {
        List<String> monthFormats = monthFormats();
        int monthIndex = monthFormats.indexOf(month.toUpperCase().substring(0, 3));
        if (monthIndex > -1) {
            return monthIndex + 1;
        }
        throw new ParseException(MESSAGE_INVALID_DATE);
    }

    /** Formats the given String year into an integer. Throws a ParseException if the year is not valid. */
    public static int formatYear(String year) throws ParseException {
        try {
            return Integer.parseInt(year);
        } catch (NumberFormatException nfe) {
            throw new ParseException(MESSAGE_INVALID_DATE);
        }
    }
}
