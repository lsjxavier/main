package seedu.foodiebot.logic.parser;

import static seedu.foodiebot.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.foodiebot.logic.parser.CliSyntax.PREFIX_DATE_BY_MONTH;
import static seedu.foodiebot.logic.parser.CliSyntax.PREFIX_DATE_BY_YEAR;
import static seedu.foodiebot.logic.parser.CliSyntax.PREFIX_FROM_DATE;
import static seedu.foodiebot.logic.parser.CliSyntax.PREFIX_TO_DATE;

import java.util.stream.Stream;

import seedu.foodiebot.commons.core.date.DateFormatter;
import seedu.foodiebot.commons.core.date.DateRange;
import seedu.foodiebot.logic.commands.ReportCommand;
import seedu.foodiebot.logic.parser.exceptions.ParseException;

/** Parses input arguments and creates a new ReportCommand object */
public class ReportCommandParser implements Parser<ReportCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ReportCommand and returns a
     * ReportCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ReportCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_FROM_DATE, PREFIX_TO_DATE,
                PREFIX_DATE_BY_MONTH, PREFIX_DATE_BY_YEAR);

        // If no parameters are specified
        if (argMultimap.getPreamble().isEmpty()) {
            return new ReportCommand(DateRange.generate());
        }

        if (arePrefixesPresent(argMultimap, PREFIX_DATE_BY_MONTH)
                && !areAnyPrefixesPresent(argMultimap, PREFIX_DATE_BY_YEAR, PREFIX_FROM_DATE, PREFIX_TO_DATE)) {

            String monthString = getArgString(argMultimap, PREFIX_DATE_BY_MONTH);
            int month = DateFormatter.formatMonth(monthString);
            return new ReportCommand(DateRange.ofMonth(month));

        } else if (arePrefixesPresent(argMultimap, PREFIX_DATE_BY_YEAR)
                && !areAnyPrefixesPresent(argMultimap, PREFIX_DATE_BY_MONTH, PREFIX_FROM_DATE, PREFIX_TO_DATE)) {

            String yearString = getArgString(argMultimap, PREFIX_DATE_BY_YEAR);
            int year = DateFormatter.formatYear(yearString);
            return new ReportCommand(DateRange.ofYear(year));

        } else if (arePrefixesPresent(argMultimap, PREFIX_FROM_DATE, PREFIX_TO_DATE)
                && !areAnyPrefixesPresent(argMultimap, PREFIX_DATE_BY_MONTH, PREFIX_DATE_BY_YEAR)) {

            String start = getArgString(argMultimap, PREFIX_FROM_DATE);
            String end = getArgString(argMultimap, PREFIX_TO_DATE);
            return new ReportCommand(DateRange.of(start, end));

        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReportCommand.MESSAGE_USAGE));
        }
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



