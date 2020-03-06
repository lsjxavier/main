package seedu.foodiebot.logic.parser;

import static seedu.foodiebot.commons.core.Messages.MESSAGE_INVALID_DATE;
import static seedu.foodiebot.logic.parser.CliSyntax.PREFIX_DATE_BY_MONTH;
import static seedu.foodiebot.logic.parser.CliSyntax.PREFIX_DATE_BY_YEAR;
import static seedu.foodiebot.logic.parser.CliSyntax.PREFIX_FROM_DATE;
import static seedu.foodiebot.logic.parser.CliSyntax.PREFIX_TO_DATE;

import java.time.LocalDate;
import java.util.stream.Stream;

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
        try {
            Stream<LocalDate> dateRange = DateRangeFormatter.getDateRange(argMultimap);
            return new ReportCommand(dateRange);
        } catch (IllegalArgumentException iae) {
            throw new ParseException(MESSAGE_INVALID_DATE);
        }
    }
}



